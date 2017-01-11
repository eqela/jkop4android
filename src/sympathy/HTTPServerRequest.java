
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

public class HTTPServerRequest
{
	public static HTTPServerRequest forDetails(java.lang.String method, java.lang.String url, java.lang.String version, cape.KeyValueList<java.lang.String, java.lang.String> headers) {
		HTTPServerRequest v = new HTTPServerRequest();
		v.method = method;
		v.urlString = url;
		v.version = version;
		v.setHeaders(headers);
		return(v);
	}

	private java.lang.String method = null;
	private java.lang.String urlString = null;
	private java.lang.String version = null;
	private cape.KeyValueList<java.lang.String, java.lang.String> rawHeaders = null;
	private java.util.HashMap<java.lang.String,java.lang.String> headers = null;
	private URL url = null;
	private java.lang.String cacheId = null;
	private HTTPServerConnection connection = null;
	private HTTPServerBase server = null;
	private java.lang.Object data = null;
	private java.lang.Object session = null;
	private java.util.HashMap<java.lang.String,java.lang.String> cookies = null;
	private byte[] bodyBuffer = null;
	private java.lang.String bodyString = null;
	private java.util.HashMap<java.lang.String,java.lang.String> postParameters = null;
	private java.util.ArrayList<java.lang.String> resources = null;
	private int currentResource = 0;
	private java.lang.String relativeResourcePath = null;
	private boolean responseSent = false;
	private java.util.ArrayList<HTTPServerCookie> responseCookies = null;

	public void setBodyReceiver(DataStream receiver) {
		if(receiver == null) {
			return;
		}
		if(bodyBuffer != null) {
			int sz = bodyBuffer.length;
			if(receiver.onDataStreamStart((long)sz) == false) {
				return;
			}
			if(receiver.onDataStreamContent(bodyBuffer, sz) == false) {
				return;
			}
			receiver.onDataStreamEnd();
			return;
		}
		if(connection == null) {
			return;
		}
		connection.setBodyReceiver(receiver);
	}

	public java.lang.String getCacheId() {
		if(android.text.TextUtils.equals(cacheId, null)) {
			if(android.text.TextUtils.equals(method, "GET")) {
				cacheId = (method + " ") + urlString;
			}
		}
		return(cacheId);
	}

	public void clearHeaders() {
		rawHeaders = null;
		headers = null;
	}

	public void addHeader(java.lang.String key, java.lang.String value) {
		if(android.text.TextUtils.equals(key, null)) {
			return;
		}
		if(rawHeaders == null) {
			rawHeaders = new cape.KeyValueList<java.lang.String, java.lang.String>();
		}
		if(headers == null) {
			headers = new java.util.HashMap<java.lang.String,java.lang.String>();
		}
		rawHeaders.add((java.lang.String)key, (java.lang.String)value);
		headers.put(cape.String.toLowerCase(key), value);
	}

	public void setHeaders(cape.KeyValueList<java.lang.String, java.lang.String> headers) {
		clearHeaders();
		if(headers == null) {
			return;
		}
		cape.Iterator<cape.KeyValuePair<java.lang.String, java.lang.String>> it = headers.iterate();
		if(it == null) {
			return;
		}
		while(true) {
			cape.KeyValuePair<java.lang.String, java.lang.String> kvp = it.next();
			if(kvp == null) {
				break;
			}
			addHeader(kvp.key, kvp.value);
		}
	}

	public java.lang.String getHeader(java.lang.String name) {
		if(cape.String.isEmpty(name)) {
			return(null);
		}
		if(headers == null) {
			return(null);
		}
		return(cape.Map.get(headers, name));
	}

	public cape.Iterator<cape.KeyValuePair<java.lang.String, java.lang.String>> iterateHeaders() {
		if(rawHeaders == null) {
			return(null);
		}
		return(rawHeaders.iterate());
	}

	public URL getURL() {
		if(url == null) {
			url = sympathy.URL.forString(urlString, true);
		}
		return(url);
	}

	public java.util.HashMap<java.lang.String,java.lang.String> getQueryParameters() {
		URL url = getURL();
		if(url == null) {
			return(null);
		}
		return(url.getQueryParameters());
	}

	public cape.Iterator<cape.KeyValuePair<java.lang.String, java.lang.String>> iterateQueryParameters() {
		URL url = getURL();
		if(url == null) {
			return(null);
		}
		cape.KeyValueList<java.lang.String, java.lang.String> list = url.getRawQueryParameters();
		if(list == null) {
			return(null);
		}
		return(list.iterate());
	}

	public java.lang.String getQueryParameter(java.lang.String key) {
		URL url = getURL();
		if(url == null) {
			return(null);
		}
		return(url.getQueryParameter(key));
	}

	public java.lang.String getURLPath() {
		URL url = getURL();
		if(url == null) {
			return(null);
		}
		return(url.getPath());
	}

	public java.lang.String getRemoteIPAddress() {
		java.lang.String rr = getRemoteAddress();
		if(android.text.TextUtils.equals(rr, null)) {
			return(null);
		}
		int colon = cape.String.indexOf(rr, ':');
		if(colon < 0) {
			return(rr);
		}
		return(cape.String.getSubString(rr, 0, colon));
	}

	public java.lang.String getRemoteAddress() {
		if(connection == null) {
			return(null);
		}
		return(connection.getRemoteAddress());
	}

	public boolean getConnectionClose() {
		java.lang.String hdr = getHeader("connection");
		if(android.text.TextUtils.equals(hdr, null)) {
			return(false);
		}
		if(android.text.TextUtils.equals(hdr, "close")) {
			return(true);
		}
		return(false);
	}

	public java.lang.String getETag() {
		return(getHeader("if-none-match"));
	}

	public java.util.HashMap<java.lang.String,java.lang.String> getCookieValues() {
		if(cookies == null) {
			java.util.HashMap<java.lang.String,java.lang.String> v = new java.util.HashMap<java.lang.String,java.lang.String>();
			java.lang.String cvals = getHeader("cookie");
			if(!(android.text.TextUtils.equals(cvals, null))) {
				java.util.ArrayList<java.lang.String> sp = cape.String.split(cvals, ';');
				if(sp != null) {
					int n = 0;
					int m = sp.size();
					for(n = 0 ; n < m ; n++) {
						java.lang.String ck = sp.get(n);
						if(ck != null) {
							ck = cape.String.strip(ck);
							if(cape.String.isEmpty(ck)) {
								continue;
							}
							int e = cape.String.indexOf(ck, '=');
							if(e < 0) {
								cape.Map.set(v, ck, "");
							}
							else {
								cape.Map.set(v, cape.String.getSubString(ck, 0, e), cape.String.getSubString(ck, e + 1));
							}
						}
					}
				}
			}
			cookies = v;
		}
		return(cookies);
	}

	public java.lang.String getCookieValue(java.lang.String name) {
		java.util.HashMap<java.lang.String,java.lang.String> c = getCookieValues();
		if(c == null) {
			return(null);
		}
		return(cape.Map.get(c, name));
	}

	public java.lang.String getBodyString() {
		if(android.text.TextUtils.equals(bodyString, null)) {
			byte[] buffer = getBodyBuffer();
			if(buffer != null) {
				bodyString = cape.String.forUTF8Buffer(buffer);
			}
			bodyBuffer = null;
		}
		return(bodyString);
	}

	public java.lang.Object getBodyJSONObject() {
		return(cape.JSONParser.parse(getBodyString()));
	}

	static public cape.DynamicVector objectAsCapeDynamicVector(java.lang.Object o) {
		if(o instanceof cape.DynamicVector) {
			return((cape.DynamicVector)o);
		}
		return(null);
	}

	public cape.DynamicVector getBodyJSONVector() {
		return(objectAsCapeDynamicVector((java.lang.Object)getBodyJSONObject()));
	}

	static public cape.DynamicMap objectAsCapeDynamicMap(java.lang.Object o) {
		if(o instanceof cape.DynamicMap) {
			return((cape.DynamicMap)o);
		}
		return(null);
	}

	public cape.DynamicMap getBodyJSONMap() {
		return(objectAsCapeDynamicMap((java.lang.Object)getBodyJSONObject()));
	}

	public java.lang.String getBodyJSONMapValue(java.lang.String key) {
		cape.DynamicMap map = getBodyJSONMap();
		if(map == null) {
			return(null);
		}
		return(map.getString(key));
	}

	public java.util.HashMap<java.lang.String,java.lang.String> getPostParameters() {
		if(postParameters == null) {
			java.lang.String bs = getBodyString();
			if(cape.String.isEmpty(bs)) {
				return(null);
			}
			postParameters = capex.QueryString.parse(bs);
		}
		return(postParameters);
	}

	public java.lang.String getPostParameter(java.lang.String key) {
		java.util.HashMap<java.lang.String,java.lang.String> pps = getPostParameters();
		if(pps == null) {
			return(null);
		}
		return(cape.Map.get(pps, key));
	}

	public java.lang.String getRelativeRequestPath(java.lang.String relativeTo) {
		java.lang.String path = getURLPath();
		if(android.text.TextUtils.equals(path, null)) {
			return(null);
		}
		if(!(android.text.TextUtils.equals(relativeTo, null)) && cape.String.startsWith(path, relativeTo)) {
			path = cape.String.getSubString(path, cape.String.getLength(relativeTo));
		}
		else {
			return(null);
		}
		if(cape.String.isEmpty(path)) {
			path = "/";
		}
		return(path);
	}

	public void initResources() {
		java.lang.String path = getURLPath();
		if(android.text.TextUtils.equals(path, null)) {
			return;
		}
		resources = cape.String.split(path, '/');
		cape.Vector.removeFirst(resources);
		int vsz = cape.Vector.getSize(resources);
		if(vsz > 0) {
			java.lang.String last = cape.Vector.get(resources, vsz - 1);
			if(cape.String.isEmpty(last)) {
				cape.Vector.removeLast(resources);
			}
		}
		currentResource = 0;
	}

	public boolean hasMoreResources() {
		if(resources == null) {
			initResources();
		}
		if(resources == null) {
			return(false);
		}
		if(currentResource < cape.Vector.getSize(resources)) {
			return(true);
		}
		return(false);
	}

	public int getRemainingResourceCount() {
		if(resources == null) {
			initResources();
		}
		if(resources == null) {
			return(0);
		}
		return((cape.Vector.getSize(resources) - currentResource) - 1);
	}

	public boolean acceptMethodAndResource(java.lang.String methodToAccept, java.lang.String resource, boolean mustBeLastResource) {
		if(android.text.TextUtils.equals(resource, null)) {
			return(false);
		}
		if((android.text.TextUtils.equals(methodToAccept, null)) || (android.text.TextUtils.equals(method, methodToAccept))) {
			java.lang.String cc = peekResource();
			if(android.text.TextUtils.equals(cc, null)) {
				return(false);
			}
			if(!(android.text.TextUtils.equals(cc, resource))) {
				return(false);
			}
			popResource();
			if(mustBeLastResource && hasMoreResources()) {
				unpopResource();
				return(false);
			}
			return(true);
		}
		return(false);
	}

	public boolean acceptMethodAndResource(java.lang.String methodToAccept, java.lang.String resource) {
		return(acceptMethodAndResource(methodToAccept, resource, false));
	}

	public boolean acceptResource(java.lang.String resource, boolean mustBeLastResource) {
		if(android.text.TextUtils.equals(resource, null)) {
			return(false);
		}
		java.lang.String cc = peekResource();
		if(android.text.TextUtils.equals(cc, null)) {
			return(false);
		}
		if(!(android.text.TextUtils.equals(cc, resource))) {
			return(false);
		}
		popResource();
		if(mustBeLastResource && hasMoreResources()) {
			unpopResource();
			return(false);
		}
		return(true);
	}

	public boolean acceptResource(java.lang.String resource) {
		return(acceptResource(resource, false));
	}

	public java.lang.String peekResource() {
		if(resources == null) {
			initResources();
		}
		if(resources == null) {
			return(null);
		}
		if(currentResource < cape.Vector.getSize(resources)) {
			return(resources.get(currentResource));
		}
		return(null);
	}

	public int getCurrentResource() {
		return(currentResource);
	}

	public void setCurrentResource(int value) {
		currentResource = value;
		relativeResourcePath = null;
	}

	public java.lang.String popResource() {
		if(resources == null) {
			initResources();
		}
		java.lang.String v = peekResource();
		if(!(android.text.TextUtils.equals(v, null))) {
			currentResource++;
			relativeResourcePath = null;
		}
		return(v);
	}

	public void unpopResource() {
		if(currentResource > 0) {
			currentResource--;
			relativeResourcePath = null;
		}
	}

	public void resetResources() {
		resources = null;
		currentResource = 0;
		relativeResourcePath = null;
	}

	public java.util.ArrayList<java.lang.String> getRelativeResources() {
		if(resources == null) {
			initResources();
		}
		if(resources == null) {
			return(null);
		}
		if(currentResource < 1) {
			return(resources);
		}
		java.util.ArrayList<java.lang.String> v = new java.util.ArrayList<java.lang.String>();
		int cr = currentResource;
		while(cr < cape.Vector.getSize(resources)) {
			v.add(resources.get(cr));
			cr++;
		}
		return(v);
	}

	public java.lang.String getRelativeResourcePath() {
		if(resources == null) {
			return(getURLPath());
		}
		if(android.text.TextUtils.equals(relativeResourcePath, null)) {
			java.util.ArrayList<java.lang.String> rrs = getRelativeResources();
			if(rrs != null) {
				cape.StringBuilder sb = new cape.StringBuilder();
				if(rrs != null) {
					int n = 0;
					int m = rrs.size();
					for(n = 0 ; n < m ; n++) {
						java.lang.String rr = rrs.get(n);
						if(rr != null) {
							if(cape.String.isEmpty(rr) == false) {
								sb.append('/');
								sb.append(rr);
							}
						}
					}
				}
				if(sb.count() < 1) {
					sb.append('/');
				}
				relativeResourcePath = sb.toString();
			}
		}
		return(relativeResourcePath);
	}

	public boolean isForResource(java.lang.String res) {
		if(android.text.TextUtils.equals(res, null)) {
			return(false);
		}
		java.lang.String rrp = getRelativeResourcePath();
		if(android.text.TextUtils.equals(rrp, null)) {
			return(false);
		}
		if(android.text.TextUtils.equals(rrp, res)) {
			return(true);
		}
		return(false);
	}

	public boolean isForDirectory() {
		java.lang.String path = getURLPath();
		if(!(android.text.TextUtils.equals(path, null)) && cape.String.endsWith(path, "/")) {
			return(true);
		}
		return(false);
	}

	public boolean isForPrefix(java.lang.String res) {
		if(android.text.TextUtils.equals(res, null)) {
			return(false);
		}
		java.lang.String rr = getRelativeResourcePath();
		if(!(android.text.TextUtils.equals(rr, null)) && cape.String.startsWith(rr, res)) {
			return(true);
		}
		return(false);
	}

	public boolean isGET() {
		return(android.text.TextUtils.equals(method, "GET"));
	}

	public boolean isPOST() {
		return(android.text.TextUtils.equals(method, "POST"));
	}

	public boolean isDELETE() {
		return(android.text.TextUtils.equals(method, "DELETE"));
	}

	public boolean isPUT() {
		return(android.text.TextUtils.equals(method, "PUT"));
	}

	public boolean isPATCH() {
		return(android.text.TextUtils.equals(method, "PATCH"));
	}

	public void sendJSONObject(java.lang.Object o) {
		sendResponse(sympathy.HTTPServerResponse.forJSONString(cape.JSONEncoder.encode(o)));
	}

	public void sendJSONString(java.lang.String json) {
		sendResponse(sympathy.HTTPServerResponse.forJSONString(json));
	}

	public void sendJSONError(cape.Error error) {
		sendResponse(sympathy.HTTPServerResponse.forJSONString(cape.JSONEncoder.encode((java.lang.Object)sympathy.JSONResponse.forError(error))));
	}

	public void sendJSONOK(java.lang.Object data) {
		sendResponse(sympathy.HTTPServerResponse.forJSONString(cape.JSONEncoder.encode((java.lang.Object)sympathy.JSONResponse.forOk(data))));
	}

	public void sendJSONOK() {
		sendJSONOK(null);
	}

	public void sendInternalError(java.lang.String text) {
		sendResponse(sympathy.HTTPServerResponse.forHTTPInternalError(text));
	}

	public void sendInternalError() {
		sendInternalError(null);
	}

	public void sendNotAllowed() {
		sendResponse(sympathy.HTTPServerResponse.forHTTPNotAllowed());
	}

	public void sendNotFound() {
		sendResponse(sympathy.HTTPServerResponse.forHTTPNotFound());
	}

	public void sendInvalidRequest(java.lang.String text) {
		sendResponse(sympathy.HTTPServerResponse.forHTTPInvalidRequest(text));
	}

	public void sendInvalidRequest() {
		sendInvalidRequest(null);
	}

	public void sendTextString(java.lang.String text) {
		sendResponse(sympathy.HTTPServerResponse.forTextString(text));
	}

	public void sendHTMLString(java.lang.String html) {
		sendResponse(sympathy.HTTPServerResponse.forHTMLString(html));
	}

	public void sendXMLString(java.lang.String xml) {
		sendResponse(sympathy.HTTPServerResponse.forXMLString(xml));
	}

	public void sendFile(cape.File file) {
		sendResponse(sympathy.HTTPServerResponse.forFile(file));
	}

	public void sendBuffer(byte[] buffer, java.lang.String mimeType) {
		sendResponse(sympathy.HTTPServerResponse.forBuffer(buffer, mimeType));
	}

	public void sendBuffer(byte[] buffer) {
		sendBuffer(buffer, null);
	}

	public void sendRedirect(java.lang.String url) {
		sendResponse(sympathy.HTTPServerResponse.forHTTPMovedTemporarily(url));
	}

	public void sendRedirectAsDirectory() {
		java.lang.String path = getURLPath();
		if(android.text.TextUtils.equals(path, null)) {
			path = "";
		}
		sendRedirect(path + "/");
	}

	public boolean isResponseSent() {
		return(responseSent);
	}

	public void addResponseCookie(HTTPServerCookie cookie) {
		if(cookie == null) {
			return;
		}
		if(responseCookies == null) {
			responseCookies = new java.util.ArrayList<HTTPServerCookie>();
		}
		responseCookies.add(cookie);
	}

	public void sendResponse(HTTPServerResponse resp) {
		if(responseSent) {
			return;
		}
		if(server == null) {
			return;
		}
		if(responseCookies != null) {
			int n = 0;
			int m = responseCookies.size();
			for(n = 0 ; n < m ; n++) {
				HTTPServerCookie cookie = responseCookies.get(n);
				if(cookie != null) {
					resp.addCookie(cookie);
				}
			}
		}
		responseCookies = null;
		server.sendResponse(connection, this, resp);
		responseSent = true;
	}

	public java.lang.String getMethod() {
		return(method);
	}

	public HTTPServerRequest setMethod(java.lang.String v) {
		method = v;
		return(this);
	}

	public java.lang.String getUrlString() {
		return(urlString);
	}

	public HTTPServerRequest setUrlString(java.lang.String v) {
		urlString = v;
		return(this);
	}

	public java.lang.String getVersion() {
		return(version);
	}

	public HTTPServerRequest setVersion(java.lang.String v) {
		version = v;
		return(this);
	}

	public HTTPServerConnection getConnection() {
		return(connection);
	}

	public HTTPServerRequest setConnection(HTTPServerConnection v) {
		connection = v;
		return(this);
	}

	public HTTPServerBase getServer() {
		return(server);
	}

	public HTTPServerRequest setServer(HTTPServerBase v) {
		server = v;
		return(this);
	}

	public java.lang.Object getData() {
		return(data);
	}

	public HTTPServerRequest setData(java.lang.Object v) {
		data = v;
		return(this);
	}

	public java.lang.Object getSession() {
		return(session);
	}

	public HTTPServerRequest setSession(java.lang.Object v) {
		session = v;
		return(this);
	}

	public byte[] getBodyBuffer() {
		return(bodyBuffer);
	}

	public HTTPServerRequest setBodyBuffer(byte[] v) {
		bodyBuffer = v;
		return(this);
	}
}
