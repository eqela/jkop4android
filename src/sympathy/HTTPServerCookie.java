
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

public class HTTPServerCookie implements cape.StringObject
{
	public HTTPServerCookie(java.lang.String key, java.lang.String value) {
		this.key = key;
		this.value = value;
	}

	private java.lang.String key = null;
	private java.lang.String value = null;
	private int maxAge = -1;
	private java.lang.String path = null;
	private java.lang.String domain = null;

	public java.lang.String toString() {
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append(key);
		sb.append('=');
		sb.append(value);
		if(maxAge >= 0) {
			sb.append("; Max-Age=");
			sb.append(cape.String.forInteger(maxAge));
		}
		if(cape.String.isEmpty(path) == false) {
			sb.append("; Path=");
			sb.append(path);
		}
		if(cape.String.isEmpty(domain) == false) {
			sb.append("; Domain=");
			sb.append(domain);
		}
		return(sb.toString());
	}

	public java.lang.String getKey() {
		return(key);
	}

	public HTTPServerCookie setKey(java.lang.String v) {
		key = v;
		return(this);
	}

	public java.lang.String getValue() {
		return(value);
	}

	public HTTPServerCookie setValue(java.lang.String v) {
		value = v;
		return(this);
	}

	public int getMaxAge() {
		return(maxAge);
	}

	public HTTPServerCookie setMaxAge(int v) {
		maxAge = v;
		return(this);
	}

	public java.lang.String getPath() {
		return(path);
	}

	public HTTPServerCookie setPath(java.lang.String v) {
		path = v;
		return(this);
	}

	public java.lang.String getDomain() {
		return(domain);
	}

	public HTTPServerCookie setDomain(java.lang.String v) {
		domain = v;
		return(this);
	}
}
