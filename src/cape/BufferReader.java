
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

package cape;

public class BufferReader implements Reader, SizedReader, SeekableReader
{
	public static BufferReader forBuffer(byte[] buf) {
		return(new BufferReader().setBuffer(buf));
	}

	private byte[] buffer = null;
	private int pos = 0;

	public boolean setCurrentPosition(long n) {
		pos = (int)n;
		return(true);
	}

	public long getCurrentPosition() {
		return((long)pos);
	}

	public byte[] getBuffer() {
		return(buffer);
	}

	public BufferReader setBuffer(byte[] buf) {
		buffer = buf;
		pos = 0;
		return(this);
	}

	public void rewind() {
		pos = 0;
	}

	public int getSize() {
		if(buffer == null) {
			return(0);
		}
		return(buffer.length);
	}

	public int read(byte[] buf) {
		if((buf == null) || (buffer == null)) {
			return(0);
		}
		int buffersz = buffer.length;
		if(pos >= buffersz) {
			return(0);
		}
		int size = buf.length;
		if(size > (buffersz - pos)) {
			size = buffersz - pos;
		}
		cape.Buffer.copyFrom(buf, buffer, (long)pos, (long)0, (long)size);
		pos += size;
		return(size);
	}

	public int getPos() {
		return(pos);
	}

	public BufferReader setPos(int v) {
		pos = v;
		return(this);
	}
}
