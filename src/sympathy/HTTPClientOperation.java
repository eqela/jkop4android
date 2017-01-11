
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

public class HTTPClientOperation extends HTTPClient
{
	private static class MyResponseParser
	{
		private byte[] receivedData = null;
		public HTTPClientResponse headers = null;
		public boolean isChunked = false;
		public int contentLength = 0;
		public int dataCounter = 0;
		private HTTPClientListener listener = null;
		private boolean endOfResponse = false;
		private boolean aborted = false;

		public void reset() {
			isChunked = false;
			headers = null;
			contentLength = 0;
			dataCounter = 0;
			endOfResponse = false;
			aborted = false;
		}

		private boolean hasEndOfHeaders(byte[] buf, long size) {
			int n = 0;
			boolean v = false;
			while(n <= (size - 4)) {
				if((((cape.Buffer.getByte(buf, (long)n) == '\r') && (cape.Buffer.getByte(buf, (long)(n + 1)) == '\n')) && (cape.Buffer.getByte(buf, (long)(n + 2)) == '\r')) && (cape.Buffer.getByte(buf, (long)(n + 3)) == '\n')) {
					v = true;
					break;
				}
				n++;
			}
			return(v);
		}

		private HTTPClientResponse parseResponse(byte[] buf) {
			int i = 0;
			byte p = (byte)'0';
			HTTPClientResponse v = null;
			boolean first = true;
			boolean isChunked = false;
			while(true) {
				cape.StringBuilder sb = new cape.StringBuilder();
				while((p = cape.Buffer.getByte(buf, (long)i)) != 0) {
					if(p == '\r') {
						;
					}
					else if(p == '\n') {
						i++;
						break;
					}
					else {
						sb.append((char)p);
					}
					i++;
				}
				java.lang.String t = sb.toString();
				if(cape.String.isEmpty(t)) {
					break;
				}
				if(first) {
					java.util.ArrayList<java.lang.String> comps = cape.String.split(t, ' ', 3);
					v = new HTTPClientResponse();
					v.setHttpVersion(cape.Vector.get(comps, 0));
					v.setHttpStatus(cape.Vector.get(comps, 1));
					v.setHttpStatusDescription(cape.Vector.get(comps, 2));
				}
				else {
					java.util.ArrayList<java.lang.String> comps = cape.String.split(t, ':', 2);
					java.lang.String key = cape.Vector.get(comps, 0);
					if(cape.String.isEmpty(key) == false) {
						java.lang.String val = cape.String.strip(cape.Vector.get(comps, 1));
						v.addHeader(key, val);
						if((isChunked == false) && cape.String.equalsIgnoreCase(key, "transfer-encoding")) {
							if(android.text.TextUtils.equals(val, "chunked")) {
								isChunked = true;
							}
						}
						else if((contentLength < 1) && cape.String.equalsIgnoreCase(key, "content-length")) {
							contentLength = cape.String.toInteger(val);
						}
					}
				}
				first = false;
			}
			long l = (long)(cape.Buffer.getSize(buf) - i);
			if(l > 0) {
				receivedData = cape.Buffer.getSubBuffer(buf, (long)i, l);
			}
			else {
				receivedData = null;
			}
			this.isChunked = isChunked;
			return(v);
		}

		private byte[] getChunk() {
			if(receivedData == null) {
				return(null);
			}
			int i = 0;
			cape.StringBuilder sb = new cape.StringBuilder();
			while(true) {
				byte p = cape.Buffer.getByte(receivedData, (long)i);
				if(p == '\r') {
					;
				}
				else if(p == '\n') {
					i++;
					break;
				}
				else {
					sb.append((char)p);
				}
				i++;
				if(sb.count() >= 16) {
					return(null);
				}
			}
			int cl = -1;
			java.lang.String t = cape.String.strip(sb.toString());
			if(cape.String.isEmpty(t) == false) {
				cl = cape.String.toIntegerFromHex(t);
			}
			byte[] v = null;
			if(cl > 0) {
				if((cape.Buffer.getSize(receivedData) - i) < cl) {
					return(null);
				}
				v = new byte[cl];
				cape.Buffer.copyFrom(v, receivedData, (long)i, (long)0, (long)cl);
				i += cl;
			}
			while((i < cape.Buffer.getSize(receivedData)) && ((cape.Buffer.getByte(receivedData, (long)i) == '\r') || (cape.Buffer.getByte(receivedData, (long)i) == '\n'))) {
				i++;
			}
			int rem = (int)(cape.Buffer.getSize(receivedData) - i);
			if(rem > 0) {
				byte[] tmp = receivedData;
				receivedData = new byte[rem];
				cape.Buffer.copyFrom(receivedData, tmp, (long)i, (long)0, (long)rem);
			}
			else {
				receivedData = null;
			}
			return(v);
		}

		public void onDataReceived(byte[] buf, long size) {
			if(size > 0) {
				receivedData = cape.Buffer.append(receivedData, buf, size);
			}
			if(headers == null) {
				if(hasEndOfHeaders(receivedData, cape.Buffer.getSize(receivedData))) {
					headers = parseResponse(receivedData);
					if(headers != null) {
						onHeadersReceived(headers);
					}
				}
			}
			if(isChunked) {
				while(true) {
					byte[] r = getChunk();
					if(r != null) {
						long sz = cape.Buffer.getSize(r);
						dataCounter += (int)sz;
						onBodyDataReceived(r, sz);
					}
					else {
						reset();
						onEndOfResponse();
						break;
					}
					if(receivedData == null) {
						break;
					}
				}
			}
			else if(contentLength > 0) {
				long rsz = cape.Buffer.getSize(receivedData);
				if(rsz > 0) {
					if((contentLength <= 0) || ((dataCounter + rsz) <= contentLength)) {
						byte[] v = receivedData;
						receivedData = null;
						dataCounter += (int)rsz;
						onBodyDataReceived(v, rsz);
					}
					else {
						int vsz = contentLength - dataCounter;
						byte[] v = cape.Buffer.getSubBuffer(receivedData, (long)0, (long)vsz);
						receivedData = cape.Buffer.getSubBuffer(receivedData, (long)vsz, rsz - vsz);
						dataCounter += vsz;
						onBodyDataReceived(v, (long)vsz);
					}
				}
				if(dataCounter >= contentLength) {
					reset();
					onEndOfResponse();
				}
			}
			else {
				reset();
				onEndOfResponse();
			}
		}

		public void onHeadersReceived(HTTPClientResponse headers) {
			if((listener != null) && (listener.onResponseReceived(headers) == false)) {
				if(listener != null) {
					listener.onAborted();
				}
				aborted = true;
			}
		}

		public void onBodyDataReceived(byte[] buffer, long size) {
			if((listener != null) && (listener.onDataReceived(buffer) == false)) {
				if(listener != null) {
					listener.onAborted();
				}
				aborted = true;
			}
		}

		public void onEndOfResponse() {
			if(listener != null) {
				listener.onResponseCompleted();
			}
			endOfResponse = true;
		}

		public HTTPClientListener getListener() {
			return(listener);
		}

		public MyResponseParser setListener(HTTPClientListener v) {
			listener = v;
			return(this);
		}

		public boolean getEndOfResponse() {
			return(endOfResponse);
		}

		public MyResponseParser setEndOfResponse(boolean v) {
			endOfResponse = v;
			return(this);
		}

		public boolean getAborted() {
			return(aborted);
		}

		public MyResponseParser setAborted(boolean v) {
			aborted = v;
			return(this);
		}
	}

	private ConnectedSocket openSocket = null;
	private java.lang.String openSocketProtocol = null;
	private java.lang.String openSocketAddress = null;
	private int openSocketPort = 0;
	private java.lang.String defaultUserAgent = null;
	private MyResponseParser parser = null;
	private byte[] receiveBuffer = null;
	private boolean acceptInvalidCertificate = false;

	public HTTPClientOperation() {
		receiveBuffer = new byte[64 * 1024];
	}

	public boolean openConnection(java.lang.String protocol, java.lang.String address, int aport, HTTPClientListener listener) {
		closeConnection(listener);
		if(cape.String.isEmpty(address)) {
			if(listener != null) {
				listener.onError("No server address");
			}
			return(false);
		}
		if(!(android.text.TextUtils.equals(protocol, "http")) && !(android.text.TextUtils.equals(protocol, "https"))) {
			if(listener != null) {
				listener.onError("Protocol must be http or https");
			}
			return(false);
		}
		int port = aport;
		if(port < 1) {
			if(android.text.TextUtils.equals(protocol, "https")) {
				port = 443;
			}
			else {
				port = 80;
			}
		}
		if(listener != null) {
			listener.onStatus(((("Connecting to server `" + address) + ":") + cape.String.forInteger(port)) + "' ..");
		}
		openSocket = (ConnectedSocket)sympathy.TCPSocket.createAndConnect(address, port);
		if(listener != null) {
			listener.onStatus(null);
		}
		if(openSocket == null) {
			if(listener != null) {
				listener.onError(((("Connection failed: `" + address) + ":") + cape.String.forInteger(port)) + "'");
			}
			return(false);
		}
		if(android.text.TextUtils.equals(protocol, "https")) {
			openSocket = (ConnectedSocket)sympathy.SSLSocket.forClient(openSocket, address, null, acceptInvalidCertificate);
			if((openSocket == null) && (listener != null)) {
				listener.onError("FAILED to create SSL socket for HTTPS");
				closeConnection(listener);
				return(false);
			}
		}
		openSocketProtocol = protocol;
		openSocketAddress = address;
		openSocketPort = port;
		parser = new MyResponseParser();
		return(true);
	}

	public boolean openConnection(HTTPClientRequest request, HTTPClientListener listener) {
		if(request == null) {
			if(listener != null) {
				listener.onError("No request");
			}
			return(false);
		}
		return(openConnection(request.getProtocol(), request.getServerAddress(), request.getServerPort(), listener));
	}

	public void closeConnection(HTTPClientListener listener) {
		if(openSocket == null) {
			return;
		}
		if(listener != null) {
			listener.onStatus("Closing connection");
		}
		openSocket.close();
		openSocket = null;
		openSocketProtocol = null;
		openSocketAddress = null;
		openSocketPort = 0;
		parser = null;
		if(listener != null) {
			listener.onStatus(null);
		}
	}

	public boolean sendRequest(HTTPClientRequest request, HTTPClientListener listener) {
		if(request == null) {
			if(listener != null) {
				listener.onError("No request");
			}
			return(false);
		}
		if((listener != null) && (listener.onStartRequest(request) == false)) {
			return(false);
		}
		if(openSocket != null) {
			if((!(android.text.TextUtils.equals(request.getServerAddress(), openSocketAddress)) || !(android.text.TextUtils.equals(request.getProtocol(), openSocketProtocol))) || (request.getServerPort() != openSocketPort)) {
				closeConnection(listener);
			}
		}
		if(openSocket == null) {
			openConnection(request, listener);
			if(openSocket == null) {
				return(false);
			}
		}
		if(listener != null) {
			listener.onStatus("Sending request headers ..");
		}
		java.lang.String rqs = request.toString(defaultUserAgent);
		cape.PrintWriter pww = cape.PrintWriterWrapper.forWriter((cape.Writer)openSocket);
		boolean whr = pww.print(rqs);
		if(listener != null) {
			listener.onStatus(null);
		}
		if(whr == false) {
			if(listener != null) {
				listener.onError("Failed to send HTTP request headers");
			}
			closeConnection(listener);
			return(false);
		}
		cape.SizedReader body = request.getBody();
		if(body != null) {
			if(listener != null) {
				listener.onStatus("Sending request body ..");
			}
			boolean rv = true;
			byte[] bf = new byte[4096 * 4];
			while(true) {
				int r = body.read(bf);
				if(r < 1) {
					break;
				}
				if(openSocket.write(bf, r) < r) {
					if(listener != null) {
						listener.onError("Failed to send request body");
					}
					closeConnection(listener);
					rv = false;
					break;
				}
			}
			if(listener != null) {
				listener.onStatus(null);
			}
			if(rv == false) {
				return(false);
			}
		}
		return(true);
	}

	public boolean readResponse(HTTPClientListener listener, int timeout) {
		if(openSocket == null) {
			if(listener != null) {
				listener.onError("No open socket");
			}
			return(false);
		}
		if(listener != null) {
			listener.onStatus("Receiving response ..");
		}
		boolean rv = true;
		parser.setListener(listener);
		while(true) {
			int r = 0;
			if(openSocket instanceof SSLSocket) {
				r = ((SSLSocket)openSocket).readWithTimeout(receiveBuffer, timeout);
			}
			else {
				r = ((TCPSocket)openSocket).readWithTimeout(receiveBuffer, timeout);
			}
			if(r == 0) {
				rv = false;
				break;
			}
			if(r < 1) {
				closeConnection(listener);
				if(listener != null) {
					listener.onAborted();
				}
				rv = false;
				break;
			}
			parser.onDataReceived(receiveBuffer, (long)r);
			if(parser.getAborted()) {
				closeConnection(listener);
				rv = false;
				break;
			}
			if(parser.getEndOfResponse()) {
				parser.reset();
				rv = true;
				break;
			}
		}
		if(parser != null) {
			parser.setListener(null);
		}
		if(listener != null) {
			listener.onStatus(null);
			if(listener.onEndRequest() == false) {
				rv = false;
			}
		}
		return(rv);
	}

	@Override
	public void executeRequest(HTTPClientRequest request, HTTPClientListener listener) {
		if(sendRequest(request, listener) == false) {
			return;
		}
		if(readResponse(listener, 30000) == false) {
			return;
		}
		if(android.text.TextUtils.equals(request.getHeader("connection"), "close")) {
			closeConnection(listener);
		}
	}

	public java.lang.String getDefaultUserAgent() {
		return(defaultUserAgent);
	}

	public HTTPClientOperation setDefaultUserAgent(java.lang.String v) {
		defaultUserAgent = v;
		return(this);
	}

	public boolean getAcceptInvalidCertificate() {
		return(acceptInvalidCertificate);
	}

	public HTTPClientOperation setAcceptInvalidCertificate(boolean v) {
		acceptInvalidCertificate = v;
		return(this);
	}
}
