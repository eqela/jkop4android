
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

abstract public class NetworkServer extends NetworkConnectionManager
{
	private int port = 0;
	private TCPSocket socket = null;
	private IOManagerEntry esocket = null;

	@Override
	public boolean initialize() {
		if(super.initialize() == false) {
			return(false);
		}
		socket = sympathy.TCPSocket.createAndListen(port);
		if(socket == null) {
			cape.Log.error(logContext, "Failed to listen on TCP port " + cape.String.forInteger(port));
			cleanup();
			return(false);
		}
		esocket = ioManager.addWithReadListener((java.lang.Object)socket, new samx.function.Procedure0() {
			public void execute() {
				onIncomingConnection();
			}
		});
		if(esocket == null) {
			cape.Log.error(logContext, "Failed to register with event loop");
			cleanup();
			return(false);
		}
		cape.Log.info(logContext, "TCP server initialized, listening on port " + cape.String.forInteger(port));
		return(true);
	}

	@Override
	public void cleanup() {
		if(esocket != null) {
			esocket.remove();
			esocket = null;
		}
		if(socket != null) {
			socket.close();
			socket = null;
		}
		super.cleanup();
	}

	private void onIncomingConnection() {
		TCPSocket nn = socket.accept();
		if(nn == null) {
			cape.Log.error(logContext, "Failed to accept an incoming client.");
			return;
		}
		onNewSocket((ConnectedSocket)nn);
	}

	public int getPort() {
		return(port);
	}

	public NetworkServer setPort(int v) {
		port = v;
		return(this);
	}
}
