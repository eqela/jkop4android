
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

public class BufferWriter implements Writer
{
	public static BufferWriter forBuffer(byte[] buf) {
		BufferWriter v = new BufferWriter();
		v.buffer = buf;
		return(v);
	}

	private byte[] buffer = null;
	private int pos = 0;

	public int getBufferSize() {
		return((int)cape.Buffer.getSize(buffer));
	}

	public int getBufferPos() {
		return(0);
	}

	public byte[] getBuffer() {
		return(buffer);
	}

	public int write(byte[] src, int ssize) {
		if(src == null) {
			return(0);
		}
		int size = ssize;
		if(size < 0) {
			size = (int)cape.Buffer.getSize(src);
		}
		if(size < 1) {
			return(0);
		}
		if(buffer == null) {
			buffer = new byte[size];
			if(buffer == null) {
				return(0);
			}
			cape.Buffer.copyFrom(buffer, src, (long)0, (long)0, (long)size);
			pos = size;
		}
		else if((pos + size) <= cape.Buffer.getSize(buffer)) {
			cape.Buffer.copyFrom(buffer, src, (long)0, (long)pos, (long)size);
			pos += size;
		}
		else {
			byte[] nb = cape.Buffer.resize(buffer, (long)(pos + size));
			if(nb == null) {
				return(0);
			}
			buffer = nb;
			cape.Buffer.copyFrom(buffer, src, (long)0, (long)pos, (long)size);
			pos += size;
		}
		return(size);
	}
}
