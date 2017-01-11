
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

public class HTTPServerRequestHandlerMap extends HTTPServerRequestHandlerAdapter
{
	private java.util.HashMap<java.lang.String,samx.function.Procedure1<HTTPServerRequest>> getHandlerFunctions = null;
	private java.util.HashMap<java.lang.String,samx.function.Procedure1<HTTPServerRequest>> postHandlerFunctions = null;
	private java.util.HashMap<java.lang.String,samx.function.Procedure1<HTTPServerRequest>> putHandlerFunctions = null;
	private java.util.HashMap<java.lang.String,samx.function.Procedure1<HTTPServerRequest>> deleteHandlerFunctions = null;
	private java.util.HashMap<java.lang.String,samx.function.Procedure1<HTTPServerRequest>> patchHandlerFunctions = null;
	private java.util.HashMap<java.lang.String,HTTPServerRequestHandler> childObjects = null;

	@Override
	public void initialize(HTTPServerBase server) {
		super.initialize(server);
		cape.Iterator<HTTPServerRequestHandler> it = cape.Map.iterateValues(childObjects);
		while(true) {
			HTTPServerRequestHandler child = it.next();
			if(child == null) {
				break;
			}
			if(child instanceof HTTPServerComponent) {
				((HTTPServerComponent)child).initialize(server);
			}
		}
	}

	@Override
	public void onMaintenance() {
		super.onMaintenance();
		cape.Iterator<HTTPServerRequestHandler> it = cape.Map.iterateValues(childObjects);
		while(true) {
			HTTPServerRequestHandler child = it.next();
			if(child == null) {
				break;
			}
			if(child instanceof HTTPServerComponent) {
				((HTTPServerComponent)child).onMaintenance();
			}
		}
	}

	@Override
	public void onRefresh() {
		super.onRefresh();
		cape.Iterator<HTTPServerRequestHandler> it = cape.Map.iterateValues(childObjects);
		while(true) {
			HTTPServerRequestHandler child = it.next();
			if(child == null) {
				break;
			}
			if(child instanceof HTTPServerComponent) {
				((HTTPServerComponent)child).onRefresh();
			}
		}
	}

	@Override
	public void cleanup() {
		super.cleanup();
		cape.Iterator<HTTPServerRequestHandler> it = cape.Map.iterateValues(childObjects);
		while(true) {
			HTTPServerRequestHandler child = it.next();
			if(child == null) {
				break;
			}
			if(child instanceof HTTPServerComponent) {
				((HTTPServerComponent)child).cleanup();
			}
		}
	}

	public boolean onHTTPMethod(HTTPServerRequest req, java.util.HashMap<java.lang.String,samx.function.Procedure1<HTTPServerRequest>> functions) {
		java.lang.String rsc = req.peekResource();
		if(android.text.TextUtils.equals(rsc, null)) {
			rsc = "";
		}
		samx.function.Procedure1<HTTPServerRequest> handler = null;
		int rsccount = req.getRemainingResourceCount();
		if(rsccount < 1) {
			handler = cape.Map.get(functions, rsc);
		}
		else if(rsccount == 1) {
			handler = cape.Map.get(functions, rsc + "/*");
			if(handler == null) {
				handler = cape.Map.get(functions, rsc + "/**");
			}
		}
		else {
			handler = cape.Map.get(functions, rsc + "/**");
		}
		if(handler != null) {
			req.popResource();
			handler.execute(req);
			return(true);
		}
		return(false);
	}

	@Override
	public boolean onGET(HTTPServerRequest req) {
		return(onHTTPMethod(req, getHandlerFunctions));
	}

	@Override
	public boolean onPOST(HTTPServerRequest req) {
		return(onHTTPMethod(req, postHandlerFunctions));
	}

	@Override
	public boolean onPUT(HTTPServerRequest req) {
		return(onHTTPMethod(req, putHandlerFunctions));
	}

	@Override
	public boolean onDELETE(HTTPServerRequest req) {
		return(onHTTPMethod(req, deleteHandlerFunctions));
	}

	@Override
	public boolean onPATCH(HTTPServerRequest req) {
		return(onHTTPMethod(req, patchHandlerFunctions));
	}

	public boolean tryHandleRequest(HTTPServerRequest req) {
		boolean v = false;
		if(req == null) {
			;
		}
		else if(req.isGET()) {
			v = onGET(req);
		}
		else if(req.isPOST()) {
			v = onPOST(req);
		}
		else if(req.isPUT()) {
			v = onPUT(req);
		}
		else if(req.isDELETE()) {
			v = onDELETE(req);
		}
		else if(req.isPATCH()) {
			v = onPATCH(req);
		}
		return(v);
	}

	@Override
	public void handleRequest(HTTPServerRequest req, samx.function.Procedure0 next) {
		if(tryHandleRequest(req)) {
			return;
		}
		java.lang.String rsc = req.peekResource();
		if(android.text.TextUtils.equals(rsc, null)) {
			rsc = "";
		}
		HTTPServerRequestHandler sub = cape.Map.get(childObjects, rsc);
		if(sub == null) {
			sub = cape.Map.get(childObjects, rsc + "/**");
		}
		if(sub != null) {
			req.popResource();
			sub.handleRequest(req, next);
			return;
		}
		next.execute();
		return;
	}

	public HTTPServerRequestHandlerMap child(java.lang.String path, HTTPServerRequestHandler handler) {
		if(!(android.text.TextUtils.equals(path, null))) {
			if(childObjects == null) {
				childObjects = new java.util.HashMap<java.lang.String,HTTPServerRequestHandler>();
			}
			childObjects.put(path, handler);
			if(((handler != null) && (handler instanceof HTTPServerComponent)) && isInitialized()) {
				((HTTPServerComponent)handler).initialize(getServer());
			}
		}
		return(this);
	}

	public HTTPServerRequestHandlerMap get(java.lang.String path, samx.function.Procedure1<HTTPServerRequest> handler) {
		if(!(android.text.TextUtils.equals(path, null))) {
			if(getHandlerFunctions == null) {
				getHandlerFunctions = new java.util.HashMap<java.lang.String,samx.function.Procedure1<HTTPServerRequest>>();
			}
			getHandlerFunctions.put(path, handler);
		}
		return(this);
	}

	public HTTPServerRequestHandlerMap post(java.lang.String path, samx.function.Procedure1<HTTPServerRequest> handler) {
		if(!(android.text.TextUtils.equals(path, null))) {
			if(postHandlerFunctions == null) {
				postHandlerFunctions = new java.util.HashMap<java.lang.String,samx.function.Procedure1<HTTPServerRequest>>();
			}
			postHandlerFunctions.put(path, handler);
		}
		return(this);
	}

	public HTTPServerRequestHandlerMap put(java.lang.String path, samx.function.Procedure1<HTTPServerRequest> handler) {
		if(!(android.text.TextUtils.equals(path, null))) {
			if(putHandlerFunctions == null) {
				putHandlerFunctions = new java.util.HashMap<java.lang.String,samx.function.Procedure1<HTTPServerRequest>>();
			}
			putHandlerFunctions.put(path, handler);
		}
		return(this);
	}

	public HTTPServerRequestHandlerMap delete(java.lang.String path, samx.function.Procedure1<HTTPServerRequest> handler) {
		if(!(android.text.TextUtils.equals(path, null))) {
			if(deleteHandlerFunctions == null) {
				deleteHandlerFunctions = new java.util.HashMap<java.lang.String,samx.function.Procedure1<HTTPServerRequest>>();
			}
			deleteHandlerFunctions.put(path, handler);
		}
		return(this);
	}

	public HTTPServerRequestHandlerMap patch(java.lang.String path, samx.function.Procedure1<HTTPServerRequest> handler) {
		if(!(android.text.TextUtils.equals(path, null))) {
			if(patchHandlerFunctions == null) {
				patchHandlerFunctions = new java.util.HashMap<java.lang.String,samx.function.Procedure1<HTTPServerRequest>>();
			}
			patchHandlerFunctions.put(path, handler);
		}
		return(this);
	}
}
