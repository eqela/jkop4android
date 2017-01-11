
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

abstract public class SSLSocket implements ConnectedSocket
{
	public static SSLSocket createInstance(ConnectedSocket cSocket, java.lang.String serverAddress, cape.LoggingContext ctx, cape.File certFile, cape.File keyFile, boolean isServer, boolean acceptInvalidCertificate) {
		if(cSocket == null) {
			return(null);
		}
		SSLSocket v = null;
		return(v);
	}

	public static SSLSocket createInstance(ConnectedSocket cSocket, java.lang.String serverAddress, cape.LoggingContext ctx, cape.File certFile, cape.File keyFile, boolean isServer) {
		return(sympathy.SSLSocket.createInstance(cSocket, serverAddress, ctx, certFile, keyFile, isServer, false));
	}

	public static SSLSocket createInstance(ConnectedSocket cSocket, java.lang.String serverAddress, cape.LoggingContext ctx, cape.File certFile, cape.File keyFile) {
		return(sympathy.SSLSocket.createInstance(cSocket, serverAddress, ctx, certFile, keyFile, false));
	}

	public static SSLSocket createInstance(ConnectedSocket cSocket, java.lang.String serverAddress, cape.LoggingContext ctx, cape.File certFile) {
		return(sympathy.SSLSocket.createInstance(cSocket, serverAddress, ctx, certFile, null));
	}

	public static SSLSocket createInstance(ConnectedSocket cSocket, java.lang.String serverAddress, cape.LoggingContext ctx) {
		return(sympathy.SSLSocket.createInstance(cSocket, serverAddress, ctx, null));
	}

	public static SSLSocket createInstance(ConnectedSocket cSocket, java.lang.String serverAddress) {
		return(sympathy.SSLSocket.createInstance(cSocket, serverAddress, null));
	}

	public static SSLSocket createInstance(ConnectedSocket cSocket) {
		return(sympathy.SSLSocket.createInstance(cSocket, null));
	}

	public static SSLSocket forClient(ConnectedSocket cSocket, java.lang.String hostAddress, cape.LoggingContext ctx, boolean acceptInvalidCertificate) {
		return(createInstance(cSocket, hostAddress, ctx, null, null, false, acceptInvalidCertificate));
	}

	public static SSLSocket forClient(ConnectedSocket cSocket, java.lang.String hostAddress, cape.LoggingContext ctx) {
		return(sympathy.SSLSocket.forClient(cSocket, hostAddress, ctx, false));
	}

	public static SSLSocket forClient(ConnectedSocket cSocket, java.lang.String hostAddress) {
		return(sympathy.SSLSocket.forClient(cSocket, hostAddress, null));
	}

	public static SSLSocket forServer(ConnectedSocket cSocket, cape.File certFile, cape.File keyFile, cape.LoggingContext ctx) {
		return(createInstance(cSocket, null, ctx, certFile, keyFile, true));
	}

	public static SSLSocket forServer(ConnectedSocket cSocket, cape.File certFile, cape.File keyFile) {
		return(sympathy.SSLSocket.forServer(cSocket, certFile, keyFile, null));
	}

	public static SSLSocket forServer(ConnectedSocket cSocket, cape.File certFile) {
		return(sympathy.SSLSocket.forServer(cSocket, certFile, null));
	}

	public static SSLSocket forServer(ConnectedSocket cSocket) {
		return(sympathy.SSLSocket.forServer(cSocket, null));
	}

	public abstract void setAcceptInvalidCertificate(boolean accept);
	public abstract void close();
	public abstract int read(byte[] buffer);
	public abstract int readWithTimeout(byte[] buffer, int timeout);
	public abstract int write(byte[] buffer, int size);
	public abstract ConnectedSocket getSocket();
}
