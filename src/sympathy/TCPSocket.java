
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

abstract public class TCPSocket implements ConnectedSocket
{
	public static TCPSocket create() {
		return((TCPSocket)new TCPSocketImpl());
	}

	public static TCPSocket createAndConnect(java.lang.String address, int port) {
		TCPSocket v = create();
		if(v == null) {
			return(null);
		}
		if(v.connect(address, port) == false) {
			v = null;
		}
		return(v);
	}

	public static TCPSocket createAndListen(int port) {
		TCPSocket v = create();
		if(v == null) {
			return(null);
		}
		if(v.listen(port) == false) {
			v = null;
		}
		return(v);
	}

	public abstract java.lang.String getRemoteAddress();
	public abstract int getRemotePort();
	public abstract java.lang.String getLocalAddress();
	public abstract int getLocalPort();
	public abstract boolean connect(java.lang.String address, int port);
	public abstract boolean listen(int port);
	public abstract TCPSocket accept();
	public abstract boolean setBlocking(boolean blocking);
	public abstract void close();
	public abstract int read(byte[] buffer);

	public int readWithTimeout(byte[] buffer, int timeout) {
		return(read(buffer));
	}

	public abstract int write(byte[] buffer, int size);
}
