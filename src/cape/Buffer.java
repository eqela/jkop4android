
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

/**
 * The Buffer class provides convenience methods for dealing with data buffers
 * (arbitrary sequences of bytes).
 */

public class Buffer
{
	private static class MyBufferObject implements BufferObject
	{
		private byte[] buffer = null;

		public byte[] toBuffer() {
			return(buffer);
		}

		public byte[] getBuffer() {
			return(buffer);
		}

		public MyBufferObject setBuffer(byte[] v) {
			buffer = v;
			return(this);
		}
	}

	/**
	 * Returns the given array as a BufferObject (which is an object type) that can be
	 * used wherever an object or a class instance is required.
	 */

	public static BufferObject asObject(byte[] buffer) {
		MyBufferObject v = new MyBufferObject();
		v.setBuffer(buffer);
		return((BufferObject)v);
	}

	public static byte[] asBuffer(java.lang.Object obj) {
		if(obj == null) {
			return(null);
		}
		if(obj instanceof BufferObject) {
			return(((BufferObject)obj).toBuffer());
		}
		return(null);
	}

	public static byte[] forInt8Array(byte[] buf) {
		return(buf);
	}

	public static byte[] toInt8Array(byte[] buf) {
		return(buf);
	}

	public static byte[] getSubBuffer(byte[] buffer, long offset, long size, boolean alwaysNewBuffer) {
		if(((alwaysNewBuffer == false) && (offset == 0)) && (size < 0)) {
			return(buffer);
		}
		long bsz = getSize(buffer);
		long sz = size;
		if(sz < 0) {
			sz = bsz - offset;
		}
		if(((alwaysNewBuffer == false) && (offset == 0)) && (sz == bsz)) {
			return(buffer);
		}
		if(sz < 1) {
			return(null);
		}
		byte[] v = new byte[(int)sz];
		copyFrom(v, buffer, offset, (long)0, sz);
		return(v);
	}

	public static byte[] getSubBuffer(byte[] buffer, long offset, long size) {
		return(cape.Buffer.getSubBuffer(buffer, offset, size, false));
	}

	public static byte getInt8(byte[] buffer, long offset) {
		return(buffer[(int)offset]);
	}

	public static void copyFrom(byte[] array, byte[] src, long soffset, long doffset, long size) {
		java.lang.System.arraycopy(src, (int)soffset, array, (int)doffset, (int)size);
	}

	public static short getInt16LE(byte[] buffer, long offset) {
		System.out.println("[cape.Buffer.getInt16LE] (Buffer.sling:202:2): Not implemented");
		return((short)0);
	}

	public static short getInt16BE(byte[] buffer, long offset) {
		System.out.println("[cape.Buffer.getInt16BE] (Buffer.sling:228:2): Not implemented");
		return((short)0);
	}

	public static int getInt32LE(byte[] buffer, long offset) {
		System.out.println("[cape.Buffer.getInt32LE] (Buffer.sling:254:2): Not implemented");
		return((int)0);
	}

	public static int getInt32BE(byte[] buffer, long offset) {
		System.out.println("[cape.Buffer.getInt32BE] (Buffer.sling:280:2): Not implemented");
		return((int)0);
	}

	public static long getSize(byte[] buffer) {
		if(buffer == null) {
			return((long)0);
		}
		return((long)(buffer.length));
	}

	public static byte getByte(byte[] buffer, long offset) {
		return(getInt8(buffer, offset));
	}

	public static void setByte(byte[] buffer, long offset, byte value) {
		buffer[(int)offset] = value;
	}

	public static byte[] allocate(long size) {
		return(new byte[(int)size]);
	}

	public static byte[] resize(byte[] buffer, long newSize) {
		if(buffer == null) {
			return(allocate(newSize));
		}
		byte[] nbuf = allocate(newSize);
		System.arraycopy(buffer, 0, nbuf, 0, (int)cape.Buffer.getSize(buffer));
		return(nbuf);
	}

	public static byte[] append(byte[] original, byte[] toAppend, long size) {
		if((toAppend == null) || (size == 0)) {
			return(original);
		}
		long sz = size;
		long os = getSize(original);
		long oas = getSize(toAppend);
		if(sz >= 0) {
			oas = sz;
		}
		long nl = os + oas;
		byte[] nb = resize(original, nl);
		copyFrom(nb, toAppend, (long)0, os, oas);
		return(nb);
	}

	public static byte[] append(byte[] original, byte[] toAppend) {
		return(cape.Buffer.append(original, toAppend, (long)-1));
	}

	public static byte[] forHexString(java.lang.String str) {
		if((android.text.TextUtils.equals(str, null)) || ((cape.String.getLength(str) % 2) != 0)) {
			return(null);
		}
		StringBuilder sb = null;
		byte[] b = allocate((long)(cape.String.getLength(str) / 2));
		int n = 0;
		CharacterIterator it = cape.String.iterate(str);
		while(it != null) {
			char c = it.getNextChar();
			if(c < 1) {
				break;
			}
			if(sb == null) {
				sb = new StringBuilder();
			}
			if((((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F'))) || ((c >= '0') && (c <= '9'))) {
				sb.append(c);
				if(sb.count() == 2) {
					setByte(b, (long)n++, (byte)cape.String.toIntegerFromHex(sb.toString()));
					sb.clear();
				}
			}
			else {
				return(null);
			}
		}
		return(b);
	}

	public static byte[] readFrom(Reader reader) {
		if(reader == null) {
			return(null);
		}
		byte[] v = null;
		byte[] tmp = new byte[1024];
		while(true) {
			int r = reader.read(tmp);
			if(r < 1) {
				break;
			}
			v = append(v, tmp, (long)r);
			if(v == null) {
				break;
			}
		}
		return(v);
	}
}
