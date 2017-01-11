
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

public class WebClientForAndroid implements WebClient
{
	public void query(java.lang.String method, java.lang.String url, cape.KeyValueList<java.lang.String, java.lang.String> requestHeaders, byte[] body, samx.function.Procedure3<java.lang.String,cape.KeyValueList<java.lang.String, java.lang.String>,byte[]> callback) {
		if(cape.String.isEmpty(method) || cape.String.isEmpty(url)) {
			callback.execute(null, null, null);
			return;
		}
		if((cape.String.startsWith(url, "http://") == false) && (cape.String.startsWith(url, "https://") == false)) {
			callback.execute(null, null, null);
			return;
		}
		WebClientRequest req = new WebClientRequest().setMethod(method).setUrl(url).setRequestHeaders(requestHeaders).setBody(body);
		new WebClientTask(callback).execute(req);
	}

	private static class WebClientRequest
	{
		private java.lang.String method = null;
		private java.lang.String url = null;
		private cape.KeyValueList<java.lang.String, java.lang.String> requestHeaders = null;
		private byte[] body = null;

		public java.lang.String getMethod() {
			return(method);
		}

		public WebClientRequest setMethod(java.lang.String v) {
			method = v;
			return(this);
		}

		public java.lang.String getUrl() {
			return(url);
		}

		public WebClientRequest setUrl(java.lang.String v) {
			url = v;
			return(this);
		}

		public cape.KeyValueList<java.lang.String, java.lang.String> getRequestHeaders() {
			return(requestHeaders);
		}

		public WebClientRequest setRequestHeaders(cape.KeyValueList<java.lang.String, java.lang.String> v) {
			requestHeaders = v;
			return(this);
		}

		public byte[] getBody() {
			return(body);
		}

		public WebClientRequest setBody(byte[] v) {
			body = v;
			return(this);
		}
	}

	private static class WebClientResponse
	{
		private java.lang.String responseCode = null;
		private cape.KeyValueList<java.lang.String, java.lang.String> responseHeaders = null;
		private byte[] responseBody = null;

		public java.lang.String getResponseCode() {
			return(responseCode);
		}

		public WebClientResponse setResponseCode(java.lang.String v) {
			responseCode = v;
			return(this);
		}

		public cape.KeyValueList<java.lang.String, java.lang.String> getResponseHeaders() {
			return(responseHeaders);
		}

		public WebClientResponse setResponseHeaders(cape.KeyValueList<java.lang.String, java.lang.String> v) {
			responseHeaders = v;
			return(this);
		}

		public byte[] getResponseBody() {
			return(responseBody);
		}

		public WebClientResponse setResponseBody(byte[] v) {
			responseBody = v;
			return(this);
		}
	}

	private static class WebClientTask extends android.os.AsyncTask<WebClientRequest, Void, WebClientResponse>
	{
		private samx.function.Procedure3<java.lang.String,cape.KeyValueList<java.lang.String, java.lang.String>,byte[]> callback = null;
		private java.io.InputStream reader = null;

		public WebClientTask(samx.function.Procedure3<java.lang.String,cape.KeyValueList<java.lang.String, java.lang.String>,byte[]> cb) {
			callback = cb;
		}

		private java.net.HttpURLConnection getConnectionForURL(java.lang.String url) {
			java.net.HttpURLConnection conn = null;
			try {
				conn = (java.net.HttpURLConnection)new java.net.URL(url).openConnection();
			}
			catch(Exception e) {
				e.printStackTrace();
				conn = null;
			}
			return(conn);
		}

		private void setRequestHeaders(java.net.HttpURLConnection conn, cape.KeyValueList<java.lang.String, java.lang.String> requestHeaders) {
			if(requestHeaders != null) {
				cape.Iterator<cape.KeyValuePair<java.lang.String, java.lang.String>> iter = requestHeaders.iterate();
				while(true) {
					cape.KeyValuePair<java.lang.String, java.lang.String> nh = iter.next();
					if(nh == null) {
						break;
					}
					java.lang.String key = nh.key;
					java.lang.String value = nh.value;
					conn.setRequestProperty(key, value);
				}
			}
		}

		public void close() {
			try {
				reader.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		private WebClientResponse processResponse(java.net.HttpURLConnection conn) {
			cape.KeyValueList<java.lang.String, java.lang.String> responseHeaders = new cape.KeyValueList<java.lang.String, java.lang.String>();
			int responseCode = 0;
			try {
				java.util.Map<String, java.util.List<String>> respHeaders = conn.getHeaderFields();
				for(java.util.Map.Entry<String, java.util.List<String>> rh : respHeaders.entrySet()) {
					java.lang.String key = rh.getKey();
					if(cape.String.isEmpty(key)) {
						continue;
					}
					key = cape.String.toLowerCase(key);
					java.lang.String val = null;
					for(String s : rh.getValue()) {
						val = s;
					}
					responseHeaders.add((java.lang.String)key, (java.lang.String)val);
				}
				responseCode = conn.getResponseCode();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			try {
				reader = conn.getInputStream();
			}
			catch(Exception e) {
				e.printStackTrace();
				reader = null;
			}
			if(reader == null) {
				return(null);
			}
			byte[] buf = new byte[1024*32];
			byte[] responseBuffer = new byte[(1024 * 1024) * 2];
			int length = 0;
			while(true) {
				try {
					int r = reader.read(buf);
					if(r < 1) {
						break;
					}
					System.arraycopy(buf, 0, responseBuffer, length, r);
					length += r;
				}
				catch(Exception e) {
					e.printStackTrace();
					break;
				}
			}
			byte[] bb = new byte[length];
			int i = 0;
			while(true) {
				if(i > (length - 1)) {
					break;
				}
				int c = (int)(responseBuffer[i] & 0xFF);
				cape.Buffer.setByte(bb, (long)i, (byte)c);
				i++;
			}
			conn.disconnect();
			conn = null;
			if(responseCode == 0) {
				responseCode = 404;
			}
			WebClientResponse response = new WebClientResponse();
			response.setResponseCode(cape.String.forInteger(responseCode));
			response.setResponseHeaders(responseHeaders);
			response.setResponseBody(bb);
			return(response);
		}

		protected WebClientResponse doInBackground(WebClientRequest... req) {
			java.lang.String url = req[0].getUrl();
			java.lang.String method = req[0].getMethod();
			cape.KeyValueList<java.lang.String, java.lang.String> requestHeaders = req[0].getRequestHeaders();
			byte[] body = req[0].getBody();
			java.net.HttpURLConnection conn = getConnectionForURL(url);
			if(conn == null) {
				return(null);
			}
			setRequestHeaders(conn, requestHeaders);
			try {
				conn.setRequestMethod(method);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			if(method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT")) {
				java.io.OutputStream writer = null;
				try {
					writer = conn.getOutputStream();
					writer.write(body);
					writer.flush();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				finally {
					try {
						writer.close();
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
			return(processResponse(conn));
		}
		protected void onProgressUpdate(Void... progress) {
		}
		protected void onPostExecute(WebClientResponse res) {
			java.lang.String responseCode = "404";
			cape.KeyValueList<java.lang.String, java.lang.String> responseHeaders = null;
			byte[] body = null;
			if(res != null) {
				responseCode = res.getResponseCode();
				responseHeaders = res.getResponseHeaders();
				body = res.getResponseBody();
			}
			if(callback != null) {
				callback.execute(responseCode, responseHeaders, body);
			}
			close();
		}
	}
}
