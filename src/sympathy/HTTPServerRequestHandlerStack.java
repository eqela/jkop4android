
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

public class HTTPServerRequestHandlerStack extends HTTPServerRequestHandlerContainer
{
	private static class FunctionRequestHandler implements HTTPServerRequestHandler
	{
		private samx.function.Procedure2<HTTPServerRequest,samx.function.Procedure0> handler = null;

		public void handleRequest(HTTPServerRequest req, samx.function.Procedure0 next) {
			handler.execute(req, next);
		}

		public samx.function.Procedure2<HTTPServerRequest,samx.function.Procedure0> getHandler() {
			return(handler);
		}

		public FunctionRequestHandler setHandler(samx.function.Procedure2<HTTPServerRequest,samx.function.Procedure0> v) {
			handler = v;
			return(this);
		}
	}

	private static class RequestProcessor
	{
		private java.util.ArrayList<HTTPServerRequestHandler> requestHandlers = null;
		private HTTPServerRequest request = null;
		private samx.function.Procedure0 last = null;
		private int current = 0;

		public void start() {
			current = -1;
			next();
		}

		public void next() {
			current++;
			HTTPServerRequestHandler handler = cape.Vector.get(requestHandlers, current);
			if(handler == null) {
				if(last == null) {
					defaultLast();
				}
				else {
					last.execute();
				}
				return;
			}
			handler.handleRequest(request, new samx.function.Procedure0() {
				public void execute() {
					next();
				}
			});
			request.resetResources();
		}

		public void defaultLast() {
			request.sendResponse(sympathy.HTTPServerResponse.forHTTPNotFound());
		}

		public java.util.ArrayList<HTTPServerRequestHandler> getRequestHandlers() {
			return(requestHandlers);
		}

		public RequestProcessor setRequestHandlers(java.util.ArrayList<HTTPServerRequestHandler> v) {
			requestHandlers = v;
			return(this);
		}

		public HTTPServerRequest getRequest() {
			return(request);
		}

		public RequestProcessor setRequest(HTTPServerRequest v) {
			request = v;
			return(this);
		}

		public samx.function.Procedure0 getLast() {
			return(last);
		}

		public RequestProcessor setLast(samx.function.Procedure0 v) {
			last = v;
			return(this);
		}
	}

	protected java.util.ArrayList<HTTPServerRequestHandler> requestHandlers = null;

	@Override
	public cape.Iterator<HTTPServerRequestHandler> iterateRequestHandlers() {
		if(requestHandlers == null) {
			return(null);
		}
		return((cape.Iterator<HTTPServerRequestHandler>)cape.Vector.iterate(requestHandlers));
	}

	public void pushRequestHandler(samx.function.Procedure2<HTTPServerRequest,samx.function.Procedure0> handler) {
		if(handler == null) {
			return;
		}
		pushRequestHandler((HTTPServerRequestHandler)new FunctionRequestHandler().setHandler(handler));
	}

	public void pushRequestHandler(HTTPServerRequestHandler handler) {
		if(handler == null) {
			return;
		}
		if(requestHandlers == null) {
			requestHandlers = new java.util.ArrayList<HTTPServerRequestHandler>();
		}
		requestHandlers.add(handler);
		if((handler instanceof HTTPServerComponent) && isInitialized()) {
			((HTTPServerComponent)handler).initialize(getServer());
		}
	}

	@Override
	public void handleRequest(HTTPServerRequest req, samx.function.Procedure0 next) {
		RequestProcessor rp = new RequestProcessor();
		rp.setRequestHandlers(requestHandlers);
		rp.setRequest(req);
		rp.setLast(next);
		rp.start();
	}
}
