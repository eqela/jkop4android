
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

public class HTTPServerBase extends NetworkServer
{
	private int writeBufferSize = 1024 * 512;
	private int smallBodyLimit = 32 * 1024;
	private int timeoutDelay = 30;
	private int maintenanceTimerDelay = 60;
	private java.lang.String serverName = null;
	private boolean enableCaching = true;
	private boolean allowCORS = true;
	private ContentCache cache = null;
	private IOManagerTimer timeoutTimer = null;
	private IOManagerTimer maintenanceTimer = null;

	public HTTPServerBase() {
		setPort(8080);
		setServerName(("Jkop for Android" + "/") + "1.0.20170111");
	}

	@Override
	public NetworkConnection createConnectionObject() {
		return((NetworkConnection)new HTTPServerConnection());
	}

	public void onRefresh() {
	}

	public boolean onTimeoutTimer() {
		final java.util.ArrayList<HTTPServerConnection> cfc = new java.util.ArrayList<HTTPServerConnection>();
		final long now = cape.SystemClock.asSeconds();
		forEachConnection(new samx.function.Procedure1<NetworkConnection>() {
			public void execute(NetworkConnection connection) {
				HTTPServerConnection httpc = (HTTPServerConnection)((connection instanceof HTTPServerConnection) ? connection : null);
				if(httpc == null) {
					return;
				}
				if(((httpc.getResponses() >= httpc.getRequests()) || httpc.getIsWaitingForBodyReceiver()) && ((now - httpc.getLastActivity()) >= timeoutDelay)) {
					cfc.add(httpc);
				}
			}
		});
		if(cfc != null) {
			int n = 0;
			int m = cfc.size();
			for(n = 0 ; n < m ; n++) {
				HTTPServerConnection wsc = cfc.get(n);
				if(wsc != null) {
					wsc.close();
				}
			}
		}
		return(true);
	}

	public boolean onMaintenanceTimer() {
		if(cache != null) {
			cache.onMaintenance();
		}
		onMaintenance();
		return(true);
	}

	public void onMaintenance() {
	}

	public IOManagerTimer startTimer(long delay, samx.function.Function0<java.lang.Boolean> handler) {
		if(ioManager == null) {
			return(null);
		}
		return(ioManager.startTimer(delay, handler));
	}

	@Override
	public boolean initialize() {
		if(super.initialize() == false) {
			return(false);
		}
		if(timeoutDelay < 1) {
			cape.Log.debug(logContext, "HTTPServerBase" + ": Timeout timer disabled");
		}
		else {
			cape.Log.debug(logContext, (("HTTPServerBase" + ": Starting a timeout timer with a ") + cape.String.forInteger(timeoutDelay)) + " second delay.");
			timeoutTimer = ioManager.startTimer(((long)timeoutDelay) * 1000000, new samx.function.Function0<java.lang.Boolean>() {
				public java.lang.Boolean execute() {
					return(onTimeoutTimer());
				}
			});
			if(timeoutTimer == null) {
				cape.Log.error(logContext, "HTTPServerBase" + ": Failed to start timeout timer");
			}
		}
		if(maintenanceTimerDelay < 1) {
			cape.Log.debug(logContext, "Maintenance timer disabled");
		}
		else {
			cape.Log.debug(logContext, (("HTTPServerBase" + ": Starting a maintenance timer with a ") + cape.String.forInteger(maintenanceTimerDelay)) + " second delay.");
			maintenanceTimer = ioManager.startTimer(((long)maintenanceTimerDelay) * 1000000, new samx.function.Function0<java.lang.Boolean>() {
				public java.lang.Boolean execute() {
					return(onMaintenanceTimer());
				}
			});
			if(maintenanceTimer == null) {
				cape.Log.error(logContext, "HTTPServerBase" + ": Failed to start maintenance timer");
			}
		}
		cape.Log.info(logContext, (("HTTPServerBase" + ": initialized: `") + getServerName()) + "'");
		return(true);
	}

	@Override
	public void cleanup() {
		super.cleanup();
		if(maintenanceTimer != null) {
			maintenanceTimer.stop();
			maintenanceTimer = null;
		}
		if(timeoutTimer != null) {
			timeoutTimer.stop();
			timeoutTimer = null;
		}
	}

	public HTTPServerResponse createOptionsResponse(HTTPServerRequest req) {
		return(new HTTPServerResponse().setStatus("200").addHeader("Content-Length", "0"));
	}

	public void onRequest(HTTPServerRequest req) {
		req.sendResponse(sympathy.HTTPServerResponse.forHTTPNotFound());
	}

	public void handleIncomingRequest(HTTPServerRequest req) {
		if(req == null) {
			return;
		}
		if(cache != null) {
			java.lang.String cid = req.getCacheId();
			if(!(android.text.TextUtils.equals(cid, null))) {
				java.lang.Object tresp = cache.get(cid);
				HTTPServerResponse resp = null;
				if(tresp instanceof HTTPServerResponse) {
					resp = (HTTPServerResponse)tresp;
				}
				if(resp != null) {
					req.sendResponse(resp);
					return;
				}
			}
		}
		if(android.text.TextUtils.equals(req.getMethod(), "OPTIONS")) {
			HTTPServerResponse resp = createOptionsResponse(req);
			if(resp != null) {
				req.sendResponse(resp);
				return;
			}
		}
		onRequest(req);
	}

	public void sendResponse(HTTPServerConnection connection, HTTPServerRequest req, HTTPServerResponse resp) {
		if(connection == null) {
			return;
		}
		if(allowCORS) {
			resp.enableCORS(req);
		}
		if(enableCaching && (resp.getCacheTtl() > 0)) {
			java.lang.String cid = req.getCacheId();
			if(!(android.text.TextUtils.equals(cid, null))) {
				if(cache == null) {
					cache = new ContentCache();
				}
				cache.set(cid, (java.lang.Object)resp, resp.getCacheTtl());
			}
		}
		connection.sendResponse(req, resp);
	}

	public void onRequestComplete(HTTPServerRequest request, HTTPServerResponse resp, int bytesSent, java.lang.String remoteAddress) {
	}

	public int getWriteBufferSize() {
		return(writeBufferSize);
	}

	public HTTPServerBase setWriteBufferSize(int v) {
		writeBufferSize = v;
		return(this);
	}

	public int getSmallBodyLimit() {
		return(smallBodyLimit);
	}

	public HTTPServerBase setSmallBodyLimit(int v) {
		smallBodyLimit = v;
		return(this);
	}

	public int getTimeoutDelay() {
		return(timeoutDelay);
	}

	public HTTPServerBase setTimeoutDelay(int v) {
		timeoutDelay = v;
		return(this);
	}

	public int getMaintenanceTimerDelay() {
		return(maintenanceTimerDelay);
	}

	public HTTPServerBase setMaintenanceTimerDelay(int v) {
		maintenanceTimerDelay = v;
		return(this);
	}

	public java.lang.String getServerName() {
		return(serverName);
	}

	public HTTPServerBase setServerName(java.lang.String v) {
		serverName = v;
		return(this);
	}

	public boolean getEnableCaching() {
		return(enableCaching);
	}

	public HTTPServerBase setEnableCaching(boolean v) {
		enableCaching = v;
		return(this);
	}

	public boolean getAllowCORS() {
		return(allowCORS);
	}

	public HTTPServerBase setAllowCORS(boolean v) {
		allowCORS = v;
		return(this);
	}
}
