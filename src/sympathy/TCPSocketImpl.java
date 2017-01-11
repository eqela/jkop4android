
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

public class TCPSocketImpl extends TCPSocket
{
	@Override
	public java.lang.String getRemoteAddress() {
		System.out.println("--- stub --- sympathy.TCPSocketImpl :: getRemoteAddress");
		return(null);
	}

	@Override
	public int getRemotePort() {
		System.out.println("--- stub --- sympathy.TCPSocketImpl :: getRemotePort");
		return(0);
	}

	@Override
	public java.lang.String getLocalAddress() {
		System.out.println("--- stub --- sympathy.TCPSocketImpl :: getLocalAddress");
		return(null);
	}

	@Override
	public int getLocalPort() {
		System.out.println("--- stub --- sympathy.TCPSocketImpl :: getLocalPort");
		return(0);
	}

	@Override
	public boolean connect(java.lang.String address, int port) {
		System.out.println("--- stub --- sympathy.TCPSocketImpl :: connect");
		return(false);
	}

	@Override
	public boolean listen(int port) {
		System.out.println("--- stub --- sympathy.TCPSocketImpl :: listen");
		return(false);
	}

	@Override
	public TCPSocket accept() {
		System.out.println("--- stub --- sympathy.TCPSocketImpl :: accept");
		return(null);
	}

	@Override
	public boolean setBlocking(boolean blocking) {
		System.out.println("--- stub --- sympathy.TCPSocketImpl :: setBlocking");
		return(false);
	}

	@Override
	public void close() {
		System.out.println("--- stub --- sympathy.TCPSocketImpl :: close");
	}

	@Override
	public int read(byte[] buffer) {
		System.out.println("--- stub --- sympathy.TCPSocketImpl :: read");
		return(0);
	}

	@Override
	public int write(byte[] buffer, int size) {
		System.out.println("--- stub --- sympathy.TCPSocketImpl :: write");
		return(0);
	}
}
