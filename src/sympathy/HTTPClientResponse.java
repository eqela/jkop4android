
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

public class HTTPClientResponse implements cape.StringObject
{
	private java.lang.String httpVersion = null;
	private java.lang.String httpStatus = null;
	private java.lang.String httpStatusDescription = null;
	private cape.KeyValueListForStrings rawHeaders = null;
	private java.util.HashMap<java.lang.String,java.lang.String> headers = null;

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

	public java.lang.String toString() {
		return(cape.String.asString((java.lang.Object)rawHeaders));
	}

	public java.lang.String getHttpVersion() {
		return(httpVersion);
	}

	public HTTPClientResponse setHttpVersion(java.lang.String v) {
		httpVersion = v;
		return(this);
	}

	public java.lang.String getHttpStatus() {
		return(httpStatus);
	}

	public HTTPClientResponse setHttpStatus(java.lang.String v) {
		httpStatus = v;
		return(this);
	}

	public java.lang.String getHttpStatusDescription() {
		return(httpStatusDescription);
	}

	public HTTPClientResponse setHttpStatusDescription(java.lang.String v) {
		httpStatusDescription = v;
		return(this);
	}

	public cape.KeyValueListForStrings getRawHeaders() {
		return(rawHeaders);
	}

	public HTTPClientResponse setRawHeaders(cape.KeyValueListForStrings v) {
		rawHeaders = v;
		return(this);
	}

	public java.util.HashMap<java.lang.String,java.lang.String> getHeaders() {
		return(headers);
	}

	public HTTPClientResponse setHeaders(java.util.HashMap<java.lang.String,java.lang.String> v) {
		headers = v;
		return(this);
	}
}
