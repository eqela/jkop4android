
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

public class URL implements cape.StringObject
{
	public static URL forString(java.lang.String str, boolean normalizePath) {
		URL v = new URL();
		v.parse(str, normalizePath);
		return(v);
	}

	public static URL forString(java.lang.String str) {
		return(sympathy.URL.forString(str, false));
	}

	private java.lang.String scheme = null;
	private java.lang.String username = null;
	private java.lang.String password = null;
	private java.lang.String host = null;
	private java.lang.String port = null;
	private java.lang.String path = null;
	private java.lang.String fragment = null;
	private cape.KeyValueList<java.lang.String, java.lang.String> rawQueryParameters = null;
	private java.util.HashMap<java.lang.String,java.lang.String> queryParameters = null;
	private java.lang.String original = null;
	private boolean percentOnly = false;
	private boolean encodeUnreservedChars = true;

	public URL dup() {
		URL v = new URL();
		v.setScheme(scheme);
		v.setUsername(username);
		v.setPassword(password);
		v.setHost(host);
		v.setPort(port);
		v.setPath(path);
		v.setFragment(fragment);
		if(rawQueryParameters != null) {
			v.setRawQueryParameters(rawQueryParameters.dup());
		}
		if(queryParameters != null) {
			v.setQueryParameters(cape.Map.dup(queryParameters));
		}
		return(v);
	}

	public java.lang.String toString() {
		return(toStringDo(true));
	}

	public java.lang.String toStringNohost() {
		return(toStringDo(false));
	}

	private java.lang.String toStringDo(boolean userhost) {
		cape.StringBuilder sb = new cape.StringBuilder();
		if(userhost) {
			if(!(android.text.TextUtils.equals(scheme, null))) {
				sb.append(scheme);
				sb.append("://");
			}
			if(!(android.text.TextUtils.equals(username, null))) {
				sb.append(username);
				if(!(android.text.TextUtils.equals(password, null))) {
					sb.append(':');
					sb.append(password);
				}
				sb.append('@');
			}
			if(!(android.text.TextUtils.equals(host, null))) {
				sb.append(host);
				if(!(android.text.TextUtils.equals(port, null))) {
					sb.append(':');
					sb.append(port);
				}
			}
		}
		if(!(android.text.TextUtils.equals(path, null))) {
			sb.append(cape.String.replace(path, ' ', '+'));
		}
		if((rawQueryParameters != null) && (rawQueryParameters.count() > 0)) {
			boolean first = true;
			cape.Iterator<java.lang.String> it = cape.Map.iterateKeys(queryParameters);
			while(it != null) {
				java.lang.String tkey = it.next();
				java.lang.String key = null;
				if(tkey instanceof java.lang.String) {
					key = (java.lang.String)tkey;
				}
				if(android.text.TextUtils.equals(key, null)) {
					break;
				}
				if(first) {
					sb.append('?');
					first = false;
				}
				else {
					sb.append('&');
				}
				sb.append(key);
				java.lang.String val = cape.Map.get(queryParameters, key);
				if(!(android.text.TextUtils.equals(val, null))) {
					sb.append('=');
					sb.append(capex.URLEncoder.encode(val, percentOnly, encodeUnreservedChars));
				}
			}
		}
		if(!(android.text.TextUtils.equals(fragment, null))) {
			sb.append('#');
			sb.append(fragment);
		}
		return(sb.toString());
	}

	private java.lang.String normalizePath(java.lang.String path) {
		if(android.text.TextUtils.equals(path, null)) {
			return(null);
		}
		cape.StringBuilder v = new cape.StringBuilder();
		java.util.ArrayList<java.lang.String> comps = cape.String.split(path, '/');
		if(comps != null) {
			int n = 0;
			int m = comps.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.String comp = comps.get(n);
				if(comp != null) {
					if(cape.String.isEmpty(comp)) {
						;
					}
					else if(android.text.TextUtils.equals(comp, ".")) {
						;
					}
					else if(android.text.TextUtils.equals(comp, "..")) {
						java.lang.String str = v.toString();
						v.clear();
						if(!(android.text.TextUtils.equals(str, null))) {
							int slash = cape.String.lastIndexOf(str, '/');
							if(slash > 0) {
								v.append(cape.String.getSubString(str, 0, slash));
							}
						}
					}
					else {
						v.append('/');
						v.append(comp);
					}
				}
			}
		}
		if(v.count() < 2) {
			return("/");
		}
		if(cape.String.endsWith(path, "/")) {
			v.append('/');
		}
		return(v.toString());
	}

	public void parse(java.lang.String astr, boolean doNormalizePath) {
		setOriginal(astr);
		if(android.text.TextUtils.equals(astr, null)) {
			return;
		}
		java.util.ArrayList<java.lang.String> fsp = cape.String.split(astr, '#', 2);
		java.lang.String str = cape.Vector.get(fsp, 0);
		fragment = cape.Vector.get(fsp, 1);
		java.util.ArrayList<java.lang.String> qsplit = cape.String.split(str, '?', 2);
		str = cape.Vector.get(qsplit, 0);
		java.lang.String queryString = cape.Vector.get(qsplit, 1);
		if(cape.String.isEmpty(queryString) == false) {
			java.util.ArrayList<java.lang.String> qss = cape.String.split(queryString, '&');
			if(qss != null) {
				int n = 0;
				int m = qss.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.String qs = qss.get(n);
					if(qs != null) {
						if(rawQueryParameters == null) {
							rawQueryParameters = new cape.KeyValueList<java.lang.String, java.lang.String>();
						}
						if(queryParameters == null) {
							queryParameters = new java.util.HashMap<java.lang.String,java.lang.String>();
						}
						if(cape.String.indexOf(qs, '=') < 0) {
							cape.Map.set(queryParameters, qs, null);
							rawQueryParameters.add((java.lang.String)qs, null);
							continue;
						}
						java.util.ArrayList<java.lang.String> qsps = cape.String.split(qs, '=', 2);
						java.lang.String key = cape.Vector.get(qsps, 0);
						java.lang.String val = cape.Vector.get(qsps, 1);
						if(cape.String.isEmpty(key) == false) {
							if(android.text.TextUtils.equals(val, null)) {
								val = "";
							}
							java.lang.String udv = capex.URLDecoder.decode(val);
							cape.Map.set(queryParameters, key, udv);
							rawQueryParameters.add((java.lang.String)key, (java.lang.String)udv);
						}
					}
				}
			}
		}
		int css = cape.String.indexOf(str, "://");
		if(css >= 0) {
			scheme = cape.String.subString(str, 0, css);
			if((cape.String.indexOf(scheme, ':') >= 0) || (cape.String.indexOf(scheme, '/') >= 0)) {
				scheme = null;
			}
			else {
				str = cape.String.subString(str, css + 3);
			}
		}
		java.lang.String pp = null;
		if(cape.String.charAt(str, 0) == '/') {
			pp = capex.URLDecoder.decode(str);
		}
		else {
			if(cape.String.indexOf(str, '/') >= 0) {
				java.util.ArrayList<java.lang.String> sssplit = cape.String.split(str, '/', 2);
				str = cape.Vector.get(sssplit, 0);
				pp = cape.Vector.get(sssplit, 1);
				if(android.text.TextUtils.equals(pp, null)) {
					pp = "";
				}
				if(cape.String.charAt(pp, 0) != '/') {
					pp = cape.String.append("/", pp);
				}
				pp = capex.URLDecoder.decode(pp);
			}
			if(cape.String.indexOf(str, '@') >= 0) {
				java.util.ArrayList<java.lang.String> asplit = cape.String.split(str, '@', 2);
				java.lang.String auth = cape.Vector.get(asplit, 0);
				str = cape.Vector.get(asplit, 1);
				if(cape.String.indexOf(auth, ':') >= 0) {
					java.util.ArrayList<java.lang.String> acsplit = cape.String.split(auth, ':', 2);
					username = capex.URLDecoder.decode(cape.Vector.get(acsplit, 0));
					password = capex.URLDecoder.decode(cape.Vector.get(acsplit, 1));
				}
				else {
					username = auth;
				}
			}
			if(cape.String.indexOf(str, ':') >= 0) {
				java.util.ArrayList<java.lang.String> hcsplit = cape.String.split(str, ':', 2);
				str = cape.Vector.get(hcsplit, 0);
				port = cape.Vector.get(hcsplit, 1);
			}
			host = str;
		}
		if(doNormalizePath) {
			path = normalizePath(pp);
		}
		else {
			path = pp;
		}
	}

	public int getPortInt() {
		if(android.text.TextUtils.equals(port, null)) {
			return(0);
		}
		return(cape.String.toInteger(port));
	}

	public java.lang.String getQueryParameter(java.lang.String key) {
		if(queryParameters == null) {
			return(null);
		}
		return(cape.Map.get(queryParameters, key));
	}

	public java.lang.String getScheme() {
		return(scheme);
	}

	public URL setScheme(java.lang.String v) {
		scheme = v;
		return(this);
	}

	public java.lang.String getUsername() {
		return(username);
	}

	public URL setUsername(java.lang.String v) {
		username = v;
		return(this);
	}

	public java.lang.String getPassword() {
		return(password);
	}

	public URL setPassword(java.lang.String v) {
		password = v;
		return(this);
	}

	public java.lang.String getHost() {
		return(host);
	}

	public URL setHost(java.lang.String v) {
		host = v;
		return(this);
	}

	public java.lang.String getPort() {
		return(port);
	}

	public URL setPort(java.lang.String v) {
		port = v;
		return(this);
	}

	public java.lang.String getPath() {
		return(path);
	}

	public URL setPath(java.lang.String v) {
		path = v;
		return(this);
	}

	public java.lang.String getFragment() {
		return(fragment);
	}

	public URL setFragment(java.lang.String v) {
		fragment = v;
		return(this);
	}

	public cape.KeyValueList<java.lang.String, java.lang.String> getRawQueryParameters() {
		return(rawQueryParameters);
	}

	public URL setRawQueryParameters(cape.KeyValueList<java.lang.String, java.lang.String> v) {
		rawQueryParameters = v;
		return(this);
	}

	public java.util.HashMap<java.lang.String,java.lang.String> getQueryParameters() {
		return(queryParameters);
	}

	public URL setQueryParameters(java.util.HashMap<java.lang.String,java.lang.String> v) {
		queryParameters = v;
		return(this);
	}

	public java.lang.String getOriginal() {
		return(original);
	}

	public URL setOriginal(java.lang.String v) {
		original = v;
		return(this);
	}

	public boolean getPercentOnly() {
		return(percentOnly);
	}

	public URL setPercentOnly(boolean v) {
		percentOnly = v;
		return(this);
	}

	public boolean getEncodeUnreservedChars() {
		return(encodeUnreservedChars);
	}

	public URL setEncodeUnreservedChars(boolean v) {
		encodeUnreservedChars = v;
		return(this);
	}
}
