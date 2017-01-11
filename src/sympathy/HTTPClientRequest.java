
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

public class HTTPClientRequest implements cape.StringObject
{
	public static HTTPClientRequest forGET(java.lang.String url) {
		HTTPClientRequest v = new HTTPClientRequest();
		v.setMethod("GET");
		v.setUrl(url);
		return(v);
	}

	public static HTTPClientRequest forPOST(java.lang.String url, java.lang.String mimeType, cape.SizedReader data) {
		HTTPClientRequest v = new HTTPClientRequest();
		v.setMethod("POST");
		v.setUrl(url);
		if(cape.String.isEmpty(mimeType) == false) {
			v.addHeader("Content-Type", mimeType);
		}
		if(data != null) {
			v.setBody(data);
		}
		return(v);
	}

	public static HTTPClientRequest forPOST(java.lang.String url, java.lang.String mimeType, byte[] data) {
		HTTPClientRequest v = new HTTPClientRequest();
		v.setMethod("POST");
		v.setUrl(url);
		if(cape.String.isEmpty(mimeType) == false) {
			v.addHeader("Content-Type", mimeType);
		}
		if(data != null) {
			v.setBody((cape.SizedReader)cape.BufferReader.forBuffer(data));
		}
		return(v);
	}

	private java.lang.String method = null;
	private java.lang.String protocol = null;
	private java.lang.String username = null;
	private java.lang.String password = null;
	private java.lang.String serverAddress = null;
	private int serverPort = 0;
	private java.lang.String requestPath = null;
	private cape.SizedReader body = null;
	private cape.KeyValueList<java.lang.String, java.lang.String> queryParams = null;
	private cape.KeyValueListForStrings rawHeaders = null;
	private java.util.HashMap<java.lang.String,java.lang.String> headers = null;

	public HTTPClientRequest() {
		protocol = "http";
		serverPort = 80;
		requestPath = "/";
		method = "GET";
	}

	public void setUrl(java.lang.String url) {
		URL uu = sympathy.URL.forString(url);
		setProtocol(uu.getScheme());
		setUsername(uu.getUsername());
		setPassword(uu.getPassword());
		setServerAddress(uu.getHost());
		int pp = cape.String.toInteger(uu.getPort());
		if(pp < 1) {
			if(android.text.TextUtils.equals(protocol, "https")) {
				pp = 443;
			}
			else if(android.text.TextUtils.equals(protocol, "http")) {
				pp = 80;
			}
		}
		setServerPort(pp);
		setRequestPath(uu.getPath());
		queryParams = uu.getRawQueryParameters();
	}

	public void addHeader(java.lang.String key, java.lang.String value) {
		if(rawHeaders == null) {
			rawHeaders = new cape.KeyValueListForStrings();
		}
		if(headers == null) {
			headers = new java.util.HashMap<java.lang.String,java.lang.String>();
		}
		rawHeaders.add(key, value);
		headers.put(cape.String.toLowerCase(key), value);
	}

	public java.lang.String getHeader(java.lang.String key) {
		if(headers == null) {
			return(null);
		}
		return(cape.Map.get(headers, key));
	}

	public void setUserAgent(java.lang.String agent) {
		addHeader("User-Agent", agent);
	}

	public java.lang.String toString(java.lang.String defaultUserAgent) {
		cape.StringBuilder rq = new cape.StringBuilder();
		cape.SizedReader body = getBody();
		java.lang.String path = getRequestPath();
		if(cape.String.isEmpty(path)) {
			path = "/";
		}
		rq.append(getMethod());
		rq.append(' ');
		rq.append(path);
		boolean first = true;
		if(queryParams != null) {
			cape.Iterator<cape.KeyValuePair<java.lang.String, java.lang.String>> it = queryParams.iterate();
			while(it != null) {
				cape.KeyValuePair<java.lang.String, java.lang.String> kv = it.next();
				if(kv == null) {
					break;
				}
				if(first) {
					rq.append('?');
					first = false;
				}
				else {
					rq.append('&');
				}
				rq.append(kv.key);
				java.lang.String val = kv.value;
				if(!(android.text.TextUtils.equals(val, null))) {
					rq.append('=');
					rq.append(capex.URLEncoder.encode(val, false, false));
				}
			}
		}
		rq.append(' ');
		rq.append("HTTP/1.1\r\n");
		boolean hasUserAgent = false;
		boolean hasHost = false;
		boolean hasContentLength = false;
		if(rawHeaders != null) {
			cape.Iterator<cape.KeyValuePair<java.lang.String, java.lang.String>> it = ((cape.KeyValueList<java.lang.String, java.lang.String>)rawHeaders).iterate();
			while(true) {
				cape.KeyValuePair<java.lang.String, java.lang.String> kvp = it.next();
				if(kvp == null) {
					break;
				}
				java.lang.String key = kvp.key;
				if(cape.String.equalsIgnoreCase(key, "user-agent")) {
					hasUserAgent = true;
				}
				else if(cape.String.equalsIgnoreCase(key, "host")) {
					hasHost = true;
				}
				else if(cape.String.equalsIgnoreCase(key, "content-length")) {
					hasContentLength = true;
				}
				rq.append(key);
				rq.append(": ");
				rq.append(kvp.value);
				rq.append("\r\n");
			}
		}
		if((hasUserAgent == false) && !(android.text.TextUtils.equals(defaultUserAgent, null))) {
			rq.append("User-Agent: ");
			rq.append(defaultUserAgent);
			rq.append("\r\n");
		}
		if(hasHost == false) {
			rq.append(("Host: " + getServerAddress()) + "\r\n");
		}
		if((body != null) && (hasContentLength == false)) {
			int bs = body.getSize();
			java.lang.String bss = cape.String.forInteger(bs);
			rq.append(("Content-Length: " + bss) + "\r\n");
		}
		rq.append("\r\n");
		return(rq.toString());
	}

	public java.lang.String toString() {
		return(toString(null));
	}

	public java.lang.String getMethod() {
		return(method);
	}

	public HTTPClientRequest setMethod(java.lang.String v) {
		method = v;
		return(this);
	}

	public java.lang.String getProtocol() {
		return(protocol);
	}

	public HTTPClientRequest setProtocol(java.lang.String v) {
		protocol = v;
		return(this);
	}

	public java.lang.String getUsername() {
		return(username);
	}

	public HTTPClientRequest setUsername(java.lang.String v) {
		username = v;
		return(this);
	}

	public java.lang.String getPassword() {
		return(password);
	}

	public HTTPClientRequest setPassword(java.lang.String v) {
		password = v;
		return(this);
	}

	public java.lang.String getServerAddress() {
		return(serverAddress);
	}

	public HTTPClientRequest setServerAddress(java.lang.String v) {
		serverAddress = v;
		return(this);
	}

	public int getServerPort() {
		return(serverPort);
	}

	public HTTPClientRequest setServerPort(int v) {
		serverPort = v;
		return(this);
	}

	public java.lang.String getRequestPath() {
		return(requestPath);
	}

	public HTTPClientRequest setRequestPath(java.lang.String v) {
		requestPath = v;
		return(this);
	}

	public cape.SizedReader getBody() {
		return(body);
	}

	public HTTPClientRequest setBody(cape.SizedReader v) {
		body = v;
		return(this);
	}

	public cape.KeyValueListForStrings getRawHeaders() {
		return(rawHeaders);
	}

	public HTTPClientRequest setRawHeaders(cape.KeyValueListForStrings v) {
		rawHeaders = v;
		return(this);
	}

	public java.util.HashMap<java.lang.String,java.lang.String> getHeaders() {
		return(headers);
	}

	public HTTPClientRequest setHeaders(java.util.HashMap<java.lang.String,java.lang.String> v) {
		headers = v;
		return(this);
	}
}
