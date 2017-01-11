
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

public class HTTPServerConnection extends NetworkConnection
{
	private static class ParserState
	{
		public java.lang.String method = null;
		public java.lang.String uri = null;
		public java.lang.String version = null;
		public java.lang.String key = null;
		public cape.KeyValueList<java.lang.String, java.lang.String> headers = null;
		public boolean headersDone = false;
		public boolean bodyDone = false;
		public cape.StringBuilder hdr = null;
		public int contentLength = 0;
		public boolean bodyIsChunked = false;
		public int dataCounter = 0;
		public DataStream bodyStream = null;
		public byte[] savedBodyChunk = null;
		public byte[] bodyBuffer = null;
	}

	private int requests = 0;
	private int responses = 0;
	private ParserState parser = null;
	private HTTPServerRequest currentRequest = null;
	private boolean closeAfterSend = false;
	private int sendWritten = 0;
	private byte[] sendBodyBuffer = null;
	private cape.Reader sendBody = null;
	private byte[] sendBuffer = null;
	private HTTPServerResponse responseToSend = null;
	private cape.Queue<HTTPServerRequest> requestQueue = null;
	private boolean isWaitingForBodyReceiver = false;

	public HTTPServerConnection() {
		parser = new ParserState();
	}

	public boolean getIsWaitingForBodyReceiver() {
		return(isWaitingForBodyReceiver);
	}

	static public HTTPServerBase objectAsSympathyHTTPServerBase(java.lang.Object o) {
		if(o instanceof HTTPServerBase) {
			return((HTTPServerBase)o);
		}
		return(null);
	}

	public HTTPServerBase getHTTPServer() {
		return(objectAsSympathyHTTPServerBase((java.lang.Object)getManager()));
	}

	public int getWriteBufferSize() {
		HTTPServerBase server = getHTTPServer();
		if(server == null) {
			return(1024 * 512);
		}
		return(server.getWriteBufferSize());
	}

	public int getSmallBodyLimit() {
		HTTPServerBase server = getHTTPServer();
		if(server == null) {
			return(1024 * 32);
		}
		return(server.getSmallBodyLimit());
	}

	public void resetParser() {
		parser.method = null;
		parser.uri = null;
		parser.version = null;
		parser.key = null;
		parser.headers = null;
		parser.headersDone = false;
		if(parser.bodyStream != null) {
			parser.bodyStream.onDataStreamAbort();
		}
		parser.bodyStream = null;
		parser.bodyDone = false;
		parser.hdr = null;
		parser.contentLength = 0;
		parser.bodyIsChunked = false;
		parser.dataCounter = 0;
	}

	@Override
	public boolean initialize() {
		if(super.initialize() == false) {
			return(false);
		}
		updateListeningMode();
		return(true);
	}

	public void updateListeningMode() {
		boolean writeFlag = false;
		boolean readFlag = true;
		if(responseToSend != null) {
			writeFlag = true;
		}
		if(isWaitingForBodyReceiver) {
			readFlag = false;
		}
		if(readFlag && writeFlag) {
			enableReadWriteMode();
		}
		else if(readFlag) {
			enableReadMode();
		}
		else if(writeFlag) {
			enableWriteMode();
		}
		else {
			enableIdleMode();
		}
	}

	public void setBodyReceiver(DataStream stream) {
		if(isWaitingForBodyReceiver == false) {
			if(stream != null) {
				if(stream.onDataStreamStart((long)0)) {
					stream.onDataStreamEnd();
				}
			}
			return;
		}
		parser.bodyStream = stream;
		if(stream != null) {
			isWaitingForBodyReceiver = false;
			updateListeningMode();
			int ll = parser.contentLength;
			if(parser.bodyIsChunked) {
				ll = -1;
			}
			if(stream.onDataStreamStart((long)ll) == false) {
				parser.bodyStream = null;
				sendErrorResponse(sympathy.HTTPServerResponse.forHTTPInternalError());
				resetParser();
				return;
			}
			byte[] sbc = parser.savedBodyChunk;
			parser.savedBodyChunk = null;
			if(sbc != null) {
				onBodyData(sbc, 0, sbc.length);
			}
		}
	}

	public boolean isExpectingBody() {
		if(((((android.text.TextUtils.equals(parser.method, "POST")) || (android.text.TextUtils.equals(parser.method, "PUT"))) || (android.text.TextUtils.equals(parser.method, "PATCH"))) || (parser.contentLength > 0)) || parser.bodyIsChunked) {
			return(true);
		}
		return(false);
	}

	private void onHeadersDone() {
		boolean hasBody = isExpectingBody();
		if(hasBody) {
			int sbl = getSmallBodyLimit();
			if(((sbl > 0) && (parser.contentLength > 0)) && (parser.contentLength < sbl)) {
				parser.bodyBuffer = new byte[parser.contentLength];
				return;
			}
			isWaitingForBodyReceiver = true;
		}
		HTTPServerRequest req = sympathy.HTTPServerRequest.forDetails(parser.method, parser.uri, parser.version, parser.headers);
		onCompleteRequest(req);
		if(hasBody == false) {
			resetParser();
		}
		updateListeningMode();
	}

	private void onHeaderData(byte[] inputBuffer, int offset, int sz) {
		if(inputBuffer == null) {
			return;
		}
		int p = 0;
		while(p < sz) {
			char c = (char)cape.Buffer.getByte(inputBuffer, (long)(p + offset));
			p++;
			if(c == '\r') {
				continue;
			}
			if(android.text.TextUtils.equals(parser.method, null)) {
				if(c == '\n') {
					continue;
				}
				if(c == ' ') {
					if(parser.hdr != null) {
						parser.method = parser.hdr.toString();
						parser.hdr = null;
					}
					continue;
				}
			}
			else if(android.text.TextUtils.equals(parser.uri, null)) {
				if(c == ' ') {
					if(parser.hdr != null) {
						parser.uri = parser.hdr.toString();
						parser.hdr = null;
					}
					continue;
				}
				else if(c == '\n') {
					if(parser.hdr != null) {
						parser.uri = parser.hdr.toString();
						parser.hdr = null;
					}
					parser.version = "HTTP/0.9";
					parser.headersDone = true;
					onHeadersDone();
					if(p < sz) {
						onData(inputBuffer, offset + p, sz - p);
					}
					return;
				}
			}
			else if(android.text.TextUtils.equals(parser.version, null)) {
				if(c == '\n') {
					if(parser.hdr != null) {
						parser.version = parser.hdr.toString();
						parser.hdr = null;
					}
					continue;
				}
			}
			else if(android.text.TextUtils.equals(parser.key, null)) {
				if(c == ':') {
					if(parser.hdr != null) {
						parser.key = parser.hdr.toString();
						parser.hdr = null;
					}
					continue;
				}
				else if(c == '\n') {
					if(parser.hdr != null) {
						sendErrorResponse(sympathy.HTTPServerResponse.forHTTPInvalidRequest());
						resetParser();
						return;
					}
					parser.headersDone = true;
					onHeadersDone();
					if(p < sz) {
						onData(inputBuffer, offset + p, sz - p);
					}
					return;
				}
				if((c >= 'A') && (c <= 'Z')) {
					c = (char)(('a' + c) - 'A');
				}
			}
			else if((c == ' ') && (parser.hdr == null)) {
				continue;
			}
			else if(c == '\n') {
				java.lang.String value = null;
				if(parser.hdr != null) {
					value = parser.hdr.toString();
					parser.hdr = null;
				}
				if(parser.headers == null) {
					parser.headers = new cape.KeyValueList<java.lang.String, java.lang.String>();
				}
				parser.headers.add((java.lang.String)parser.key, (java.lang.String)value);
				if(cape.String.equalsIgnoreCase(parser.key, "content-length") && !(android.text.TextUtils.equals(value, null))) {
					parser.contentLength = cape.String.toInteger(value);
				}
				else if((cape.String.equalsIgnoreCase(parser.key, "transfer-encoding") && !(android.text.TextUtils.equals(value, null))) && cape.String.contains(value, "chunked")) {
					parser.bodyIsChunked = true;
				}
				parser.key = null;
				continue;
			}
			if(parser.hdr == null) {
				parser.hdr = new cape.StringBuilder();
			}
			parser.hdr.append(c);
			if(parser.hdr.count() > (32 * 1024)) {
				sendErrorResponse(sympathy.HTTPServerResponse.forHTTPInvalidRequest());
				resetParser();
				return;
			}
		}
	}

	private void onBodyData(byte[] inputBuffer, int offset, int sz) {
		if((inputBuffer == null) || (sz < 1)) {
			return;
		}
		if((parser.bodyBuffer == null) && (parser.bodyStream == null)) {
			sendErrorResponse(sympathy.HTTPServerResponse.forHTTPInvalidRequest());
			resetParser();
			return;
		}
		if(parser.contentLength > 0) {
			int p = 0;
			if((parser.dataCounter + sz) <= parser.contentLength) {
				p = sz;
			}
			else {
				p = parser.contentLength - parser.dataCounter;
			}
			if(parser.bodyBuffer != null) {
				cape.Buffer.copyFrom(parser.bodyBuffer, inputBuffer, (long)offset, (long)parser.dataCounter, (long)p);
			}
			else if(parser.bodyStream.onDataStreamContent(cape.Buffer.getSubBuffer(inputBuffer, (long)offset, (long)p), p) == false) {
				sendErrorResponse(sympathy.HTTPServerResponse.forHTTPInternalError());
				parser.bodyStream = null;
				resetParser();
				return;
			}
			parser.dataCounter += p;
			if(parser.dataCounter >= parser.contentLength) {
				parser.bodyDone = true;
				if(parser.bodyBuffer != null) {
					HTTPServerRequest req = sympathy.HTTPServerRequest.forDetails(parser.method, parser.uri, parser.version, parser.headers);
					req.setBodyBuffer(parser.bodyBuffer);
					parser.bodyBuffer = null;
					onCompleteRequest(req);
					resetParser();
				}
				else {
					if(parser.bodyStream.onDataStreamEnd() == false) {
						sendErrorResponse(sympathy.HTTPServerResponse.forHTTPInternalError());
						parser.bodyStream = null;
						resetParser();
						return;
					}
					parser.bodyStream = null;
				}
				if(p < sz) {
					onData(inputBuffer, offset + p, sz - p);
				}
			}
			return;
		}
		else if(parser.bodyIsChunked) {
			sendErrorResponse(sympathy.HTTPServerResponse.forHTTPInvalidRequest("Chunked content body is not supported."));
			resetParser();
			return;
		}
		else {
			sendErrorResponse(sympathy.HTTPServerResponse.forHTTPInvalidRequest());
			resetParser();
		}
	}

	public void onData(byte[] buffer, int offset, int asz) {
		if(buffer == null) {
			return;
		}
		int sz = asz;
		if(sz < 0) {
			sz = (int)(cape.Buffer.getSize(buffer) - offset);
		}
		if(isWaitingForBodyReceiver) {
			parser.savedBodyChunk = cape.Buffer.getSubBuffer(buffer, (long)offset, (long)sz);
			return;
		}
		if(parser.headersDone && parser.bodyDone) {
			resetParser();
		}
		if(parser.headersDone == false) {
			onHeaderData(buffer, offset, sz);
		}
		else if(parser.bodyDone == false) {
			onBodyData(buffer, offset, sz);
		}
	}

	public void onData(byte[] buffer, int offset) {
		onData(buffer, offset, -1);
	}

	public void onData(byte[] buffer) {
		onData(buffer, 0);
	}

	@Override
	public void onOpened() {
	}

	@Override
	public void onClosed() {
		resetParser();
	}

	@Override
	public void onError(java.lang.String message) {
		cape.Log.error(logContext, message);
	}

	@Override
	public void onDataReceived(byte[] data, int size) {
		onData(data, 0, size);
	}

	@Override
	public void onWriteReady() {
		sendData();
	}

	public void onCompleteRequest(HTTPServerRequest req) {
		if(req == null) {
			return;
		}
		requests++;
		req.setServer(getHTTPServer());
		req.setConnection(this);
		if(currentRequest == null) {
			currentRequest = req;
			handleCurrentRequest();
		}
		else {
			if(requestQueue == null) {
				requestQueue = new cape.Queue<HTTPServerRequest>();
			}
			requestQueue.push((HTTPServerRequest)req);
		}
	}

	private void handleNextRequest() {
		if((currentRequest != null) || (requestQueue == null)) {
			return;
		}
		HTTPServerRequest treq = requestQueue.pop();
		HTTPServerRequest req = null;
		if(treq instanceof HTTPServerRequest) {
			req = (HTTPServerRequest)treq;
		}
		if(req == null) {
			return;
		}
		currentRequest = req;
		handleCurrentRequest();
	}

	public void sendErrorResponse(HTTPServerResponse response) {
		closeAfterSend = true;
		sendResponse(null, response);
	}

	private void handleCurrentRequest() {
		HTTPServerBase server = getHTTPServer();
		if((currentRequest == null) || (server == null)) {
			return;
		}
		java.lang.String method = currentRequest.getMethod();
		java.lang.String url = currentRequest.getUrlString();
		if(cape.String.isEmpty(method) || cape.String.isEmpty(url)) {
			closeAfterSend = true;
			sendResponse(currentRequest, sympathy.HTTPServerResponse.forHTTPInvalidRequest());
			return;
		}
		if((android.text.TextUtils.equals(currentRequest.getVersion(), "HTTP/0.9")) && !(android.text.TextUtils.equals(method, "GET"))) {
			closeAfterSend = true;
			sendResponse(currentRequest, sympathy.HTTPServerResponse.forHTTPInvalidRequest());
			return;
		}
		server.handleIncomingRequest(currentRequest);
	}

	public static java.lang.String getFullStatus(java.lang.String status) {
		java.lang.String v = null;
		if(!(android.text.TextUtils.equals(status, null)) && (cape.String.indexOf(status, " ") < 1)) {
			if(cape.String.equals("200", status)) {
				v = "200 OK";
			}
			else if(cape.String.equals("301", status)) {
				v = "301 Moved Permanently";
			}
			else if(cape.String.equals("303", status)) {
				v = "303 See Other";
			}
			else if(cape.String.equals("304", status)) {
				v = "304 Not Modified";
			}
			else if(cape.String.equals("400", status)) {
				v = "400 Bad Request";
			}
			else if(cape.String.equals("401", status)) {
				v = "401 Unauthorized";
			}
			else if(cape.String.equals("403", status)) {
				v = "403 Forbidden";
			}
			else if(cape.String.equals("404", status)) {
				v = "404 Not found";
			}
			else if(cape.String.equals("405", status)) {
				v = "405 Method not allowed";
			}
			else if(cape.String.equals("500", status)) {
				v = "500 Internal server error";
			}
			else if(cape.String.equals("501", status)) {
				v = "501 Not implemented";
			}
			else if(cape.String.equals("503", status)) {
				v = "503 Service unavailable";
			}
			else {
				v = status + " Unknown";
			}
		}
		else {
			v = status;
		}
		return(v);
	}

	static public java.lang.String objectAsNativeStringDataTypeNode(java.lang.Object o) {
		if(o instanceof java.lang.String) {
			return((java.lang.String)o);
		}
		return(null);
	}

	public static java.lang.String getStatusCode(java.lang.String status) {
		if(android.text.TextUtils.equals(status, null)) {
			return(null);
		}
		cape.Iterator<java.lang.String> comps = cape.Vector.iterate(cape.String.split(status, ' '));
		if(comps != null) {
			return(objectAsNativeStringDataTypeNode((java.lang.Object)comps.next()));
		}
		return(null);
	}

	public void sendResponse(HTTPServerRequest req, HTTPServerResponse aresp) {
		if(socket == null) {
			return;
		}
		if(req != null) {
			if(currentRequest == null) {
				cape.Log.error(logContext, "Sending a response, but no current request!");
				cape.Log.error(logContext, new cape.StackTrace().toString());
				close();
				return;
			}
			if(currentRequest != req) {
				cape.Log.error(logContext, "Sending a response for an incorrect request");
				close();
				return;
			}
		}
		if(isWaitingForBodyReceiver) {
			closeAfterSend = true;
		}
		responses++;
		HTTPServerResponse resp = aresp;
		if(resp == null) {
			resp = sympathy.HTTPServerResponse.forTextString("");
		}
		java.lang.String inm = null;
		if(req != null) {
			inm = req.getETag();
		}
		if(!(android.text.TextUtils.equals(inm, null))) {
			if(cape.String.equals(inm, resp.getETag())) {
				resp = new HTTPServerResponse();
				resp.setStatus("304");
				resp.setETag(aresp.getETag());
			}
		}
		java.lang.String status = resp.getStatus();
		cape.Reader bod = resp.getBody();
		java.lang.String ver = null;
		java.lang.String met = null;
		if(req != null) {
			ver = req.getVersion();
			met = req.getMethod();
		}
		cape.KeyValueList<java.lang.String, java.lang.String> headers = resp.getHeaders();
		HTTPServerBase server = getHTTPServer();
		if(cape.String.equals("HTTP/0.9", ver)) {
			closeAfterSend = true;
		}
		else {
			if((android.text.TextUtils.equals(status, null)) || (cape.String.getLength(status) < 1)) {
				status = "200";
				resp.setStatus(status);
			}
			if((req != null) && req.getConnectionClose()) {
				closeAfterSend = true;
			}
			java.lang.String fs = getFullStatus(status);
			{
				cape.StringBuilder reply = new cape.StringBuilder();
				if((android.text.TextUtils.equals(ver, null)) || (cape.String.getLength(ver) < 1)) {
					reply.append("HTTP/1.1");
				}
				else {
					reply.append(ver);
				}
				reply.append(' ');
				reply.append(fs);
				reply.append('\r');
				reply.append('\n');
				if(cape.String.startsWith(fs, "400 ")) {
					closeAfterSend = true;
				}
				if(headers != null) {
					cape.Iterator<cape.KeyValuePair<java.lang.String, java.lang.String>> it = headers.iterate();
					while(it != null) {
						cape.KeyValuePair<java.lang.String, java.lang.String> kvp = it.next();
						if(kvp == null) {
							break;
						}
						reply.append(kvp.key);
						reply.append(':');
						reply.append(' ');
						reply.append(kvp.value);
						reply.append('\r');
						reply.append('\n');
					}
				}
				if(closeAfterSend) {
					reply.append("Connection: close\r\n");
				}
				if(server != null) {
					reply.append("Server: ");
					reply.append(server.getServerName());
				}
				reply.append('\r');
				reply.append('\n');
				reply.append("Date: ");
				reply.append(capex.VerboseDateTimeString.forNow());
				reply.append('\r');
				reply.append('\n');
				reply.append('\r');
				reply.append('\n');
				sendBuffer = cape.String.toUTF8Buffer(reply.toString());
			}
		}
		sendWritten = 0;
		if(bod != null) {
			if(cape.String.equals("HEAD", met) == false) {
				sendBody = bod;
			}
		}
		responseToSend = resp;
		updateListeningMode();
	}

	private void sendData() {
		if(socket == null) {
			return;
		}
		java.lang.String remoteAddress = getRemoteAddress();
		if(cape.Buffer.getSize(sendBuffer) == 0) {
			if(sendBody != null) {
				if(sendBody instanceof cape.BufferReader) {
					sendBuffer = ((cape.BufferReader)sendBody).getBuffer();
					sendBody = null;
				}
				else {
					if(sendBodyBuffer == null) {
						sendBodyBuffer = new byte[getWriteBufferSize()];
					}
					int n = sendBody.read(sendBodyBuffer);
					if(n < 1) {
						sendBody = null;
					}
					else if(n == sendBodyBuffer.length) {
						sendBuffer = sendBodyBuffer;
					}
					else {
						sendBuffer = cape.Buffer.getSubBuffer(sendBodyBuffer, (long)0, (long)n);
					}
				}
			}
		}
		if(cape.Buffer.getSize(sendBuffer) > 0) {
			ConnectedSocket socket = this.socket;
			int r = socket.write(sendBuffer, (int)cape.Buffer.getSize(sendBuffer));
			if(r < 0) {
				sendBuffer = null;
				sendBody = null;
				close();
				return;
			}
			else if(r == 0) {
				;
			}
			else {
				sendWritten += r;
				long osz = cape.Buffer.getSize(sendBuffer);
				if(r < osz) {
					sendBuffer = cape.Buffer.getSubBuffer(sendBuffer, (long)r, osz - r);
				}
				else {
					sendBuffer = null;
				}
			}
		}
		if((cape.Buffer.getSize(sendBuffer) == 0) && (sendBody == null)) {
			HTTPServerBase server = getHTTPServer();
			if(server != null) {
				server.onRequestComplete(currentRequest, responseToSend, sendWritten, remoteAddress);
			}
			currentRequest = null;
			responseToSend = null;
			if(closeAfterSend) {
				close();
			}
			else {
				updateListeningMode();
				handleNextRequest();
			}
		}
		lastActivity = cape.SystemClock.asSeconds();
	}

	public int getRequests() {
		return(requests);
	}

	public HTTPServerConnection setRequests(int v) {
		requests = v;
		return(this);
	}

	public int getResponses() {
		return(responses);
	}

	public HTTPServerConnection setResponses(int v) {
		responses = v;
		return(this);
	}
}
