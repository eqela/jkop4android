
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

public class HTTPServerRequestHandlerAdapter implements HTTPServerRequestHandler, HTTPServerComponent
{
	private HTTPServerBase server = null;
	protected cape.LoggingContext logContext = null;

	public HTTPServerBase getServer() {
		return(server);
	}

	public boolean isInitialized() {
		if(server == null) {
			return(false);
		}
		return(true);
	}

	public void initialize(HTTPServerBase server) {
		this.server = server;
		if(server != null) {
			this.logContext = server.getLogContext();
		}
		else {
			this.logContext = null;
		}
	}

	public void onMaintenance() {
	}

	public void onRefresh() {
	}

	public void cleanup() {
		this.server = null;
	}

	public boolean onGET(HTTPServerRequest req) {
		return(false);
	}

	public void onGET(HTTPServerRequest req, samx.function.Procedure0 next) {
		if(onGET(req) == false) {
			next.execute();
		}
	}

	public boolean onPOST(HTTPServerRequest req) {
		return(false);
	}

	public void onPOST(HTTPServerRequest req, samx.function.Procedure0 next) {
		if(onPOST(req) == false) {
			next.execute();
		}
	}

	public boolean onPUT(HTTPServerRequest req) {
		return(false);
	}

	public void onPUT(HTTPServerRequest req, samx.function.Procedure0 next) {
		if(onPUT(req) == false) {
			next.execute();
		}
	}

	public boolean onDELETE(HTTPServerRequest req) {
		return(false);
	}

	public void onDELETE(HTTPServerRequest req, samx.function.Procedure0 next) {
		if(onDELETE(req) == false) {
			next.execute();
		}
	}

	public boolean onPATCH(HTTPServerRequest req) {
		return(false);
	}

	public void onPATCH(HTTPServerRequest req, samx.function.Procedure0 next) {
		if(onPATCH(req) == false) {
			next.execute();
		}
	}

	public void handleRequest(HTTPServerRequest req, samx.function.Procedure0 next) {
		if(req == null) {
			next.execute();
		}
		else if(req.isGET()) {
			onGET(req, next);
		}
		else if(req.isPOST()) {
			onPOST(req, next);
		}
		else if(req.isPUT()) {
			onPUT(req, next);
		}
		else if(req.isDELETE()) {
			onDELETE(req, next);
		}
		else if(req.isPATCH()) {
			onPATCH(req, next);
		}
		else {
			next.execute();
		}
	}
}
