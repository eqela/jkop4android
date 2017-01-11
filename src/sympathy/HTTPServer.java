
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

public class HTTPServer extends HTTPServerBase
{
	private samx.function.Function1<HTTPServerResponse,HTTPServerRequest> createOptionsResponseHandler = null;
	private java.util.ArrayList<samx.function.Procedure4<HTTPServerRequest,HTTPServerResponse,java.lang.Integer,java.lang.String>> requestHandlerListenerFunctions = null;
	private java.util.ArrayList<HTTPServerRequestHandlerListener> requestHandlerListenerObjects = null;
	private HTTPServerRequestHandlerStack handlerStack = null;

	public HTTPServer() {
		handlerStack = new HTTPServerRequestHandlerStack();
	}

	@Override
	public boolean initialize() {
		if(super.initialize() == false) {
			return(false);
		}
		handlerStack.initialize((HTTPServerBase)this);
		if(requestHandlerListenerObjects != null) {
			int n = 0;
			int m = requestHandlerListenerObjects.size();
			for(n = 0 ; n < m ; n++) {
				HTTPServerRequestHandlerListener listener = requestHandlerListenerObjects.get(n);
				if(listener != null) {
					if(listener instanceof HTTPServerComponent) {
						((HTTPServerComponent)listener).initialize((HTTPServerBase)this);
					}
				}
			}
		}
		return(true);
	}

	@Override
	public void onRefresh() {
		super.onRefresh();
		handlerStack.onRefresh();
		if(requestHandlerListenerObjects != null) {
			int n = 0;
			int m = requestHandlerListenerObjects.size();
			for(n = 0 ; n < m ; n++) {
				HTTPServerRequestHandlerListener listener = requestHandlerListenerObjects.get(n);
				if(listener != null) {
					if(listener instanceof HTTPServerComponent) {
						((HTTPServerComponent)listener).onRefresh();
					}
				}
			}
		}
	}

	@Override
	public void onMaintenance() {
		super.onMaintenance();
		handlerStack.onMaintenance();
		if(requestHandlerListenerObjects != null) {
			int n = 0;
			int m = requestHandlerListenerObjects.size();
			for(n = 0 ; n < m ; n++) {
				HTTPServerRequestHandlerListener listener = requestHandlerListenerObjects.get(n);
				if(listener != null) {
					if(listener instanceof HTTPServerComponent) {
						((HTTPServerComponent)listener).onMaintenance();
					}
				}
			}
		}
	}

	@Override
	public void cleanup() {
		super.cleanup();
		handlerStack.cleanup();
		if(requestHandlerListenerObjects != null) {
			int n = 0;
			int m = requestHandlerListenerObjects.size();
			for(n = 0 ; n < m ; n++) {
				HTTPServerRequestHandlerListener listener = requestHandlerListenerObjects.get(n);
				if(listener != null) {
					if(listener instanceof HTTPServerComponent) {
						((HTTPServerComponent)listener).cleanup();
					}
				}
			}
		}
	}

	public void pushRequestHandler(samx.function.Procedure2<HTTPServerRequest,samx.function.Procedure0> handler) {
		handlerStack.pushRequestHandler(handler);
	}

	public void pushRequestHandler(HTTPServerRequestHandler handler) {
		handlerStack.pushRequestHandler(handler);
	}

	public void addRequestHandlerListener(samx.function.Procedure4<HTTPServerRequest,HTTPServerResponse,java.lang.Integer,java.lang.String> handler) {
		if(requestHandlerListenerFunctions == null) {
			requestHandlerListenerFunctions = new java.util.ArrayList<samx.function.Procedure4<HTTPServerRequest,HTTPServerResponse,java.lang.Integer,java.lang.String>>();
		}
		requestHandlerListenerFunctions.add(handler);
	}

	public void addRequestHandlerListener(HTTPServerRequestHandlerListener handler) {
		if(requestHandlerListenerObjects == null) {
			requestHandlerListenerObjects = new java.util.ArrayList<HTTPServerRequestHandlerListener>();
		}
		requestHandlerListenerObjects.add(handler);
		if((handler instanceof HTTPServerComponent) && isInitialized()) {
			((HTTPServerComponent)handler).initialize((HTTPServerBase)this);
		}
	}

	@Override
	public HTTPServerResponse createOptionsResponse(HTTPServerRequest req) {
		if(createOptionsResponseHandler != null) {
			return(createOptionsResponseHandler.execute(req));
		}
		return(super.createOptionsResponse(req));
	}

	@Override
	public void onRequest(HTTPServerRequest req) {
		final HTTPServerRequest rq = req;
		handlerStack.handleRequest((HTTPServerRequest)((req instanceof HTTPServerRequest) ? req : null), new samx.function.Procedure0() {
			public void execute() {
				rq.sendResponse(sympathy.HTTPServerResponse.forHTTPNotFound());
			}
		});
	}

	@Override
	public void onRequestComplete(HTTPServerRequest request, HTTPServerResponse resp, int bytesSent, java.lang.String remoteAddress) {
		super.onRequestComplete(request, resp, bytesSent, remoteAddress);
		if(requestHandlerListenerFunctions != null) {
			int n = 0;
			int m = requestHandlerListenerFunctions.size();
			for(n = 0 ; n < m ; n++) {
				samx.function.Procedure4<HTTPServerRequest,HTTPServerResponse,java.lang.Integer,java.lang.String> handler = requestHandlerListenerFunctions.get(n);
				if(handler != null) {
					handler.execute(request, resp, bytesSent, remoteAddress);
				}
			}
		}
		if(requestHandlerListenerObjects != null) {
			int n2 = 0;
			int m2 = requestHandlerListenerObjects.size();
			for(n2 = 0 ; n2 < m2 ; n2++) {
				HTTPServerRequestHandlerListener handler = requestHandlerListenerObjects.get(n2);
				if(handler != null) {
					handler.onRequestHandled(request, resp, bytesSent, remoteAddress);
				}
			}
		}
	}

	public samx.function.Function1<HTTPServerResponse,HTTPServerRequest> getCreateOptionsResponseHandler() {
		return(createOptionsResponseHandler);
	}

	public HTTPServer setCreateOptionsResponseHandler(samx.function.Function1<HTTPServerResponse,HTTPServerRequest> v) {
		createOptionsResponseHandler = v;
		return(this);
	}
}
