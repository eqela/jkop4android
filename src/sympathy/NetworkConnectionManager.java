
/*
 * This file is part of Jkop for Android
 * Copyright (c) 2016-2017 Job and Esther Technologies, Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package sympathy;

abstract public class NetworkConnectionManager
{
	protected static class ConnectionPool
	{
		public NetworkConnection[] connections = null;
		public int nConnection = 0;

		public boolean initialize(cape.LoggingContext logContext, int maxConnections) {
			connections = new NetworkConnection[maxConnections];
			return(true);
		}

		public int add(cape.LoggingContext logContext, NetworkConnection conn) {
			if(nConnection >= connections.length) {
				cape.Log.error(logContext, "Maximum number of connections exceeded.");
				return(-1);
			}
			connections[nConnection] = conn;
			int v = nConnection;
			nConnection++;
			cape.Log.debug(logContext, ("Added connection to connection pool: Now " + cape.String.forInteger(nConnection)) + " connections");
			return(v);
		}

		public boolean remove(cape.LoggingContext logContext, int index) {
			if((index < 0) || (index >= nConnection)) {
				return(false);
			}
			if(connections[index] == null) {
				return(false);
			}
			int last = nConnection - 1;
			if(last == index) {
				connections[index] = null;
				nConnection--;
			}
			else {
				connections[index] = connections[last];
				connections[last] = null;
				NetworkConnection ci = connections[index];
				ci.setStorageIndex(index);
				nConnection--;
			}
			cape.Log.debug(logContext, ("Removed connection from connection pool: Now " + cape.String.forInteger(nConnection)) + " connections");
			return(true);
		}
	}

	private static class ConnectionIterator implements cape.Iterator<NetworkConnection>
	{
		private ConnectionPool pool = null;
		private int current = -1;

		public NetworkConnection next() {
			if(pool == null) {
				return(null);
			}
			int nn = current + 1;
			if(nn >= pool.nConnection) {
				return(null);
			}
			NetworkConnection tconnection = pool.connections[nn];
			NetworkConnection connection = null;
			if(tconnection instanceof NetworkConnection) {
				connection = (NetworkConnection)tconnection;
			}
			if(connection == null) {
				return(null);
			}
			current = nn;
			return(connection);
		}

		public ConnectionPool getPool() {
			return(pool);
		}

		public ConnectionIterator setPool(ConnectionPool v) {
			pool = v;
			return(this);
		}
	}

	protected cape.LoggingContext logContext = null;
	protected IOManager ioManager = null;
	private int maxConnections = 100000;
	private int recvBufferSize = 32768;
	protected ConnectionPool connections = null;
	private byte[] recvBuffer = null;
	public abstract NetworkConnection createConnectionObject();

	public NetworkConnectionManager setLogContext(cape.LoggingContext ctx) {
		logContext = ctx;
		return(this);
	}

	public cape.LoggingContext getLogContext() {
		return(logContext);
	}

	public NetworkConnectionManager setIoManager(IOManager io) {
		this.ioManager = io;
		return(this);
	}

	public IOManager getIoManager() {
		return(ioManager);
	}

	public cape.Iterator<NetworkConnection> iterateConnections() {
		return((cape.Iterator<NetworkConnection>)new ConnectionIterator().setPool(connections));
	}

	public void forEachConnection(samx.function.Procedure1<NetworkConnection> function) {
		cape.Iterator<NetworkConnection> it = iterateConnections();
		if(it == null) {
			return;
		}
		while(true) {
			NetworkConnection cc = it.next();
			if(cc == null) {
				break;
			}
			function.execute(cc);
		}
	}

	public boolean isInitialized() {
		if(connections == null) {
			return(false);
		}
		return(true);
	}

	public boolean initialize(IOManager ioManager, cape.LoggingContext logContext) {
		if(ioManager == null) {
			return(false);
		}
		setLogContext(logContext);
		setIoManager(ioManager);
		return(initialize());
	}

	public boolean initialize(IOManager ioManager) {
		return(initialize(ioManager, null));
	}

	public boolean initialize() {
		if(connections != null) {
			cape.Log.error(logContext, "Already initialized");
			return(false);
		}
		if(ioManager == null) {
			cape.Log.error(logContext, "No IO manager configured for connection server");
			return(false);
		}
		recvBuffer = new byte[recvBufferSize];
		if(recvBuffer == null) {
			cape.Log.error(logContext, "Failed to allocate recv buffer");
			return(false);
		}
		connections = new ConnectionPool();
		if(connections.initialize(logContext, maxConnections) == false) {
			cape.Log.error(logContext, "Failed to initialize connection pool");
			connections = null;
			recvBuffer = null;
			return(false);
		}
		return(true);
	}

	public void cleanup() {
		connections = null;
	}

	public void onNewSocket(ConnectedSocket socket) {
		if(socket == null) {
			return;
		}
		NetworkConnection connection = createConnectionObject();
		if(connection == null) {
			cape.Log.error(logContext, "Failed to create TCP server connection object instance for incoming connection");
			socket.close();
			return;
		}
		if(connection.doInitialize(logContext, socket, this) == false) {
			cape.Log.error(logContext, "Failed to initialize the new connection instance. Closing connection.");
			socket.close();
			return;
		}
		if(addConnection(connection) == false) {
			cape.Log.error(logContext, "Failed to add a new connection instance. Closing connection.");
			connection.close();
			return;
		}
		connection.onOpened();
	}

	public boolean addConnection(NetworkConnection connection) {
		if((connection == null) || (ioManager == null)) {
			return(false);
		}
		IOManagerEntry es = ioManager.add((java.lang.Object)connection.getSocket());
		if(es == null) {
			return(false);
		}
		connection.setIoEntry(es);
		int idx = connections.add(logContext, connection);
		if(idx < 0) {
			return(false);
		}
		connection.setStorageIndex(idx);
		return(true);
	}

	public void onConnectionClosed(NetworkConnection connection) {
		if(connection == null) {
			return;
		}
		IOManagerEntry es = connection.getIoEntry();
		if(es != null) {
			es.remove();
			connection.setIoEntry(null);
		}
		int si = connection.getStorageIndex();
		if(si >= 0) {
			connections.remove(logContext, si);
			connection.setStorageIndex(-1);
		}
	}

	public byte[] getReceiveBuffer() {
		return(recvBuffer);
	}

	public int getMaxConnections() {
		return(maxConnections);
	}

	public NetworkConnectionManager setMaxConnections(int v) {
		maxConnections = v;
		return(this);
	}

	public int getRecvBufferSize() {
		return(recvBufferSize);
	}

	public NetworkConnectionManager setRecvBufferSize(int v) {
		recvBufferSize = v;
		return(this);
	}
}
