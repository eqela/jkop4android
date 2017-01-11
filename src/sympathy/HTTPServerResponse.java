
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

public class HTTPServerResponse
{
	public static HTTPServerResponse forFile(cape.File file, int maxCachedSize) {
		if((file == null) || (file.isFile() == false)) {
			return(forHTTPNotFound());
		}
		boolean bodyset = false;
		HTTPServerResponse resp = new HTTPServerResponse();
		resp.setStatus("200");
		resp.addHeader("Content-Type", capex.MimeTypeRegistry.typeForFile(file));
		cape.FileInfo st = file.stat();
		if(st != null) {
			int lm = st.getModifyTime();
			if(lm > 0) {
				java.lang.String dts = capex.VerboseDateTimeString.forDateTime(cape.DateTime.forTimeSeconds((long)lm));
				resp.addHeader("Last-Modified", dts);
				resp.setETag(capex.MD5Encoder.encode(dts));
			}
			int mcs = maxCachedSize;
			if(mcs < 0) {
				mcs = 32 * 1024;
			}
			if(st.getSize() < mcs) {
				resp.setBody(file.getContentsBuffer());
				bodyset = true;
			}
		}
		if(bodyset == false) {
			resp.setBody(file);
		}
		return(resp);
	}

	public static HTTPServerResponse forFile(cape.File file) {
		return(sympathy.HTTPServerResponse.forFile(file, -1));
	}

	public static HTTPServerResponse forBuffer(byte[] data, java.lang.String mimetype) {
		java.lang.String mt = mimetype;
		if(cape.String.isEmpty(mt)) {
			mt = "application/binary";
		}
		HTTPServerResponse resp = new HTTPServerResponse();
		resp.setStatus("200");
		resp.addHeader("Content-Type", mt);
		resp.setBody(data);
		return(resp);
	}

	public static HTTPServerResponse forBuffer(byte[] data) {
		return(sympathy.HTTPServerResponse.forBuffer(data, null));
	}

	public static HTTPServerResponse forString(java.lang.String text, java.lang.String mimetype) {
		HTTPServerResponse resp = new HTTPServerResponse();
		resp.setStatus("200");
		if(cape.String.isEmpty(mimetype) == false) {
			resp.addHeader("Content-Type", mimetype);
		}
		resp.setBody(text);
		return(resp);
	}

	public static HTTPServerResponse forTextString(java.lang.String text) {
		HTTPServerResponse resp = new HTTPServerResponse();
		resp.setStatus("200");
		resp.addHeader("Content-Type", "text/plain; charset=\"UTF-8\"");
		resp.setBody(text);
		return(resp);
	}

	public static HTTPServerResponse forHTMLString(java.lang.String html) {
		HTTPServerResponse resp = new HTTPServerResponse();
		resp.setStatus("200");
		resp.addHeader("Content-Type", "text/html; charset=\"UTF-8\"");
		resp.setBody(html);
		return(resp);
	}

	public static HTTPServerResponse forXMLString(java.lang.String xml) {
		HTTPServerResponse resp = new HTTPServerResponse();
		resp.setStatus("200");
		resp.addHeader("Content-Type", "text/xml; charset=\"UTF-8\"");
		resp.setBody(xml);
		return(resp);
	}

	public static HTTPServerResponse forJSONObject(java.lang.Object o) {
		return(forJSONString(cape.JSONEncoder.encode(o)));
	}

	public static HTTPServerResponse forJSONString(java.lang.String json) {
		HTTPServerResponse resp = new HTTPServerResponse();
		resp.setStatus("200");
		resp.addHeader("Content-Type", "application/json; charset=\"UTF-8\"");
		resp.setBody(json);
		return(resp);
	}

	static public java.lang.String stringWithMessage(java.lang.String str, java.lang.String message) {
		if(cape.String.isEmpty(message)) {
			return(str);
		}
		return((str + ": ") + message);
	}

	public static HTTPServerResponse forHTTPInvalidRequest(java.lang.String message) {
		HTTPServerResponse resp = forTextString(stringWithMessage("Invalid request", message));
		resp.setStatus("400");
		resp.addHeader("Connection", "close");
		resp.setMessage(message);
		return(resp);
	}

	public static HTTPServerResponse forHTTPInvalidRequest() {
		return(sympathy.HTTPServerResponse.forHTTPInvalidRequest(null));
	}

	public static HTTPServerResponse forHTTPInternalError(java.lang.String message) {
		HTTPServerResponse resp = forTextString(stringWithMessage("Internal server error", message));
		resp.setStatus("500");
		resp.addHeader("Connection", "close");
		resp.setMessage(message);
		return(resp);
	}

	public static HTTPServerResponse forHTTPInternalError() {
		return(sympathy.HTTPServerResponse.forHTTPInternalError(null));
	}

	public static HTTPServerResponse forHTTPNotImplemented(java.lang.String message) {
		HTTPServerResponse resp = forTextString(stringWithMessage("Not implemented", message));
		resp.setStatus("501");
		resp.addHeader("Connection", "close");
		resp.setMessage(message);
		return(resp);
	}

	public static HTTPServerResponse forHTTPNotImplemented() {
		return(sympathy.HTTPServerResponse.forHTTPNotImplemented(null));
	}

	public static HTTPServerResponse forHTTPNotAllowed(java.lang.String message) {
		HTTPServerResponse resp = forTextString(stringWithMessage("Not allowed", message));
		resp.setStatus("405");
		resp.setMessage(message);
		return(resp);
	}

	public static HTTPServerResponse forHTTPNotAllowed() {
		return(sympathy.HTTPServerResponse.forHTTPNotAllowed(null));
	}

	public static HTTPServerResponse forHTTPNotFound(java.lang.String message) {
		HTTPServerResponse resp = forTextString(stringWithMessage("Not found", message));
		resp.setStatus("404");
		resp.setMessage(message);
		return(resp);
	}

	public static HTTPServerResponse forHTTPNotFound() {
		return(sympathy.HTTPServerResponse.forHTTPNotFound(null));
	}

	public static HTTPServerResponse forHTTPForbidden(java.lang.String message) {
		HTTPServerResponse resp = forTextString(stringWithMessage("Forbidden", message));
		resp.setStatus("403");
		resp.setMessage(message);
		return(resp);
	}

	public static HTTPServerResponse forHTTPForbidden() {
		return(sympathy.HTTPServerResponse.forHTTPForbidden(null));
	}

	public static HTTPServerResponse forRedirect(java.lang.String url) {
		return(forHTTPMovedTemporarily(url));
	}

	public static HTTPServerResponse forHTTPMovedPermanently(java.lang.String url) {
		HTTPServerResponse resp = new HTTPServerResponse();
		resp.setStatus("301");
		resp.addHeader("Location", url);
		resp.setBody(url);
		return(resp);
	}

	public static HTTPServerResponse forHTTPMovedTemporarily(java.lang.String url) {
		HTTPServerResponse resp = new HTTPServerResponse();
		resp.setStatus("303");
		resp.addHeader("Location", url);
		resp.setBody(url);
		return(resp);
	}

	private cape.KeyValueList<java.lang.String, java.lang.String> headers = null;
	private java.lang.String message = null;
	private int cacheTtl = 0;
	private java.lang.String status = null;
	private boolean statusIsOk = false;
	private cape.Reader body = null;
	private java.lang.String eTag = null;

	public HTTPServerResponse setETag(java.lang.String eTag) {
		this.eTag = eTag;
		addHeader("ETag", eTag);
		return(this);
	}

	public java.lang.String getETag() {
		return(eTag);
	}

	public HTTPServerResponse setStatus(java.lang.String status) {
		this.status = status;
		if(android.text.TextUtils.equals(status, "200")) {
			statusIsOk = true;
		}
		return(this);
	}

	public java.lang.String getStatus() {
		return(status);
	}

	public int getCacheTtl() {
		if(statusIsOk) {
			return(cacheTtl);
		}
		return(0);
	}

	public HTTPServerResponse enableCaching(int ttl) {
		cacheTtl = ttl;
		return(this);
	}

	public HTTPServerResponse enableCaching() {
		return(enableCaching(3600));
	}

	public HTTPServerResponse disableCaching() {
		cacheTtl = 0;
		return(this);
	}

	public HTTPServerResponse enableCORS(HTTPServerRequest req) {
		addHeader("Access-Control-Allow-Origin", "*");
		if(req != null) {
			addHeader("Access-Control-Allow-Methods", req.getHeader("access-control-request-method"));
			addHeader("Access-Control-Allow-Headers", req.getHeader("access-control-request-headers"));
		}
		addHeader("Access-Control-Max-Age", "1728000");
		return(this);
	}

	public HTTPServerResponse enableCORS() {
		return(enableCORS(null));
	}

	public HTTPServerResponse addHeader(java.lang.String key, java.lang.String value) {
		if(headers == null) {
			headers = new cape.KeyValueList<java.lang.String, java.lang.String>();
		}
		headers.add((java.lang.String)key, (java.lang.String)value);
		return(this);
	}

	public void addCookie(HTTPServerCookie cookie) {
		if(cookie == null) {
			return;
		}
		addHeader("Set-Cookie", cookie.toString());
	}

	public HTTPServerResponse setBody(byte[] buf) {
		if(buf == null) {
			body = null;
			addHeader("Content-Length", "0");
		}
		else {
			body = (cape.Reader)cape.BufferReader.forBuffer(buf);
			addHeader("Content-Length", cape.String.forInteger(buf.length));
		}
		return(this);
	}

	public HTTPServerResponse setBody(java.lang.String str) {
		if(android.text.TextUtils.equals(str, null)) {
			body = null;
			addHeader("Content-Length", "0");
		}
		else {
			byte[] buf = cape.String.toUTF8Buffer(str);
			body = (cape.Reader)cape.BufferReader.forBuffer(buf);
			addHeader("Content-Length", cape.String.forInteger((int)cape.Buffer.getSize(buf)));
		}
		return(this);
	}

	public HTTPServerResponse setBody(cape.File file) {
		if((file == null) || (file.isFile() == false)) {
			body = null;
			addHeader("Content-Length", "0");
		}
		else {
			body = (cape.Reader)file.read();
			addHeader("Content-Length", cape.String.forInteger(file.getSize()));
		}
		return(this);
	}

	public HTTPServerResponse setBody(cape.SizedReader reader) {
		if(reader == null) {
			body = null;
			addHeader("Content-Length", "0");
		}
		else {
			body = (cape.Reader)reader;
			addHeader("Content-Length", cape.String.forInteger(reader.getSize()));
		}
		return(this);
	}

	public cape.Reader getBody() {
		return(body);
	}

	public cape.KeyValueList<java.lang.String, java.lang.String> getHeaders() {
		return(headers);
	}

	public HTTPServerResponse setHeaders(cape.KeyValueList<java.lang.String, java.lang.String> v) {
		headers = v;
		return(this);
	}

	public java.lang.String getMessage() {
		return(message);
	}

	public HTTPServerResponse setMessage(java.lang.String v) {
		message = v;
		return(this);
	}
}
