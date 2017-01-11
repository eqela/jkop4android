
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

public class HTTPServerStaticContentHandler implements HTTPServerRequestHandler
{
	public static HTTPServerStaticContentHandler forContent(java.lang.String content, java.lang.String mimeType) {
		HTTPServerStaticContentHandler v = new HTTPServerStaticContentHandler();
		v.setContent(content);
		v.setMimeType(mimeType);
		return(v);
	}

	public static HTTPServerStaticContentHandler forHTMLContent(java.lang.String content) {
		HTTPServerStaticContentHandler v = new HTTPServerStaticContentHandler();
		v.setContent(content);
		v.setMimeType("text/html");
		return(v);
	}

	public static HTTPServerStaticContentHandler forJSONContent(java.lang.String content) {
		HTTPServerStaticContentHandler v = new HTTPServerStaticContentHandler();
		v.setContent(content);
		v.setMimeType("application/json");
		return(v);
	}

	public static HTTPServerStaticContentHandler forRedirect(java.lang.String url) {
		HTTPServerStaticContentHandler v = new HTTPServerStaticContentHandler();
		v.setRedirectUrl(url);
		return(v);
	}

	private java.lang.String content = null;
	private java.lang.String mimeType = null;
	private java.lang.String redirectUrl = null;

	public void handleRequest(HTTPServerRequest req, samx.function.Procedure0 next) {
		if(!(android.text.TextUtils.equals(redirectUrl, null))) {
			req.sendResponse(sympathy.HTTPServerResponse.forRedirect(redirectUrl));
		}
		else {
			req.sendResponse(sympathy.HTTPServerResponse.forString(content, mimeType));
		}
	}

	public java.lang.String getContent() {
		return(content);
	}

	public HTTPServerStaticContentHandler setContent(java.lang.String v) {
		content = v;
		return(this);
	}

	public java.lang.String getMimeType() {
		return(mimeType);
	}

	public HTTPServerStaticContentHandler setMimeType(java.lang.String v) {
		mimeType = v;
		return(this);
	}

	public java.lang.String getRedirectUrl() {
		return(redirectUrl);
	}

	public HTTPServerStaticContentHandler setRedirectUrl(java.lang.String v) {
		redirectUrl = v;
		return(this);
	}
}
