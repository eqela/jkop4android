
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

package capex;

public class JSONAPIClient
{
	private java.lang.String apiUrl = null;

	public java.lang.String getFullURL(java.lang.String api) {
		java.lang.String url = apiUrl;
		if(cape.String.isEmpty(url)) {
			url = "/";
		}
		if(android.text.TextUtils.equals(url, "/")) {
			if(cape.String.startsWith(api, "/")) {
				return(api);
			}
			return(url + api);
		}
		if(cape.String.endsWith(url, "/")) {
			if(cape.String.startsWith(api, "/")) {
				return(url + cape.String.getSubString(api, 1));
			}
			return(url + api);
		}
		if(cape.String.startsWith(api, "/")) {
			return(url + api);
		}
		return((url + "/") + api);
	}

	private byte[] toUTF8Buffer(cape.DynamicMap data) {
		if(data == null) {
			return(null);
		}
		return(cape.String.toUTF8Buffer(cape.JSONEncoder.encode((java.lang.Object)data)));
	}

	public void customizeRequestHeaders(cape.KeyValueList<java.lang.String, java.lang.String> headers) {
	}

	public void onStartSendRequest() {
	}

	public void onEndSendRequest() {
	}

	public void onDefaultErrorHandler(cape.Error error) {
	}

	public boolean handleAsError(cape.DynamicMap response, samx.function.Procedure1<cape.Error> callback) {
		cape.Error error = toError(response);
		if(error != null) {
			onError(error, callback);
		}
		return(false);
	}

	public boolean handleAsError(cape.DynamicMap response) {
		return(handleAsError(response, null));
	}

	public cape.Error toError(cape.DynamicMap response) {
		if(response == null) {
			return(cape.Error.forCode("noServerResponse"));
		}
		if(android.text.TextUtils.equals(response.getString("status"), "error")) {
			cape.Error v = new cape.Error();
			v.setCode(response.getString("code"));
			v.setMessage(response.getString("message"));
			v.setDetail(response.getString("detail"));
			return(v);
		}
		return(null);
	}

	public void onError(cape.Error error, samx.function.Procedure1<cape.Error> callback) {
		if(callback != null) {
			callback.execute(error);
		}
		else {
			onDefaultErrorHandler(error);
		}
	}

	public void onError(cape.Error error) {
		onError(error, null);
	}

	public void sendRequest(java.lang.String method, java.lang.String url, cape.KeyValueList<java.lang.String, java.lang.String> headers, byte[] data, samx.function.Procedure1<cape.DynamicMap> callback, samx.function.Procedure1<cape.Error> errorCallback) {
		if(callback == null) {
			return;
		}
		cape.KeyValueList<java.lang.String, java.lang.String> hrs = headers;
		if(headers == null) {
			hrs = new cape.KeyValueList<java.lang.String, java.lang.String>();
			hrs.add((java.lang.String)"Content-Type", (java.lang.String)"application/json");
		}
		WebClient webClient = capex.NativeWebClient.instance();
		if(webClient == null) {
			onError(cape.Error.forCode("noWebClient"), errorCallback);
			return;
		}
		final samx.function.Procedure1<cape.DynamicMap> ll = callback;
		customizeRequestHeaders(hrs);
		onStartSendRequest();
		final samx.function.Procedure1<cape.Error> ecb = errorCallback;
		webClient.query(method, url, hrs, data, new samx.function.Procedure3<java.lang.String, cape.KeyValueList<java.lang.String, java.lang.String>, byte[]>() {
			public void execute(java.lang.String status, cape.KeyValueList<java.lang.String, java.lang.String> responseHeaders, byte[] data) {
				onEndSendRequest();
				if(data == null) {
					onError(cape.Error.forCode("failedToConnect"), ecb);
					return;
				}
				java.lang.Object tjsonResponseBody = cape.JSONParser.parse(cape.String.forUTF8Buffer(data));
				cape.DynamicMap jsonResponseBody = null;
				if(tjsonResponseBody instanceof cape.DynamicMap) {
					jsonResponseBody = (cape.DynamicMap)tjsonResponseBody;
				}
				if(jsonResponseBody == null) {
					onError(cape.Error.forCode("invalidServerResponse"), ecb);
					return;
				}
				ll.execute(jsonResponseBody);
			}
		});
	}

	public void sendRequest(java.lang.String method, java.lang.String url, cape.KeyValueList<java.lang.String, java.lang.String> headers, byte[] data, samx.function.Procedure1<cape.DynamicMap> callback) {
		sendRequest(method, url, headers, data, callback, null);
	}

	public void doGet(java.lang.String url, samx.function.Procedure1<cape.DynamicMap> callback, samx.function.Procedure1<cape.Error> errorCallback) {
		sendRequest("GET", getFullURL(url), null, null, callback, errorCallback);
	}

	public void doGet(java.lang.String url, samx.function.Procedure1<cape.DynamicMap> callback) {
		doGet(url, callback, null);
	}

	public void doPost(java.lang.String url, cape.DynamicMap data, samx.function.Procedure1<cape.DynamicMap> callback, samx.function.Procedure1<cape.Error> errorCallback) {
		sendRequest("POST", getFullURL(url), null, toUTF8Buffer(data), callback, errorCallback);
	}

	public void doPost(java.lang.String url, cape.DynamicMap data, samx.function.Procedure1<cape.DynamicMap> callback) {
		doPost(url, data, callback, null);
	}

	public void doPut(java.lang.String url, cape.DynamicMap data, samx.function.Procedure1<cape.DynamicMap> callback, samx.function.Procedure1<cape.Error> errorCallback) {
		sendRequest("PUT", getFullURL(url), null, toUTF8Buffer(data), callback, errorCallback);
	}

	public void doPut(java.lang.String url, cape.DynamicMap data, samx.function.Procedure1<cape.DynamicMap> callback) {
		doPut(url, data, callback, null);
	}

	public void doDelete(java.lang.String url, samx.function.Procedure1<cape.DynamicMap> callback, samx.function.Procedure1<cape.Error> errorCallback) {
		sendRequest("DELETE", getFullURL(url), null, null, callback, errorCallback);
	}

	public void doDelete(java.lang.String url, samx.function.Procedure1<cape.DynamicMap> callback) {
		doDelete(url, callback, null);
	}

	public void uploadFile(java.lang.String url, byte[] data, java.lang.String mimeType, samx.function.Procedure1<cape.DynamicMap> callback, samx.function.Procedure1<cape.Error> errorCallback) {
		java.lang.String mt = mimeType;
		if(cape.String.isEmpty(mt)) {
			mt = "application/octet-stream";
		}
		cape.KeyValueList<java.lang.String, java.lang.String> hdrs = new cape.KeyValueList<java.lang.String, java.lang.String>();
		hdrs.add((java.lang.String)"content-type", (java.lang.String)mt);
		sendRequest("POST", getFullURL(url), hdrs, data, callback, errorCallback);
	}

	public void uploadFile(java.lang.String url, byte[] data, java.lang.String mimeType, samx.function.Procedure1<cape.DynamicMap> callback) {
		uploadFile(url, data, mimeType, callback, null);
	}

	public java.lang.String getApiUrl() {
		return(apiUrl);
	}

	public JSONAPIClient setApiUrl(java.lang.String v) {
		apiUrl = v;
		return(this);
	}
}
