
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

public class BlockCipherWriter implements cape.Writer, cape.SeekableWriter
{
	public static BlockCipherWriter create(cape.Writer writer, BlockCipher cipher) {
		if((writer == null) || (cipher == null)) {
			return(null);
		}
		BlockCipherWriter v = new BlockCipherWriter();
		v.writer = writer;
		v.cipher = cipher;
		v.bsize = cipher.getBlockSize();
		v.bcurr = 0;
		v.bdata = cape.Buffer.allocate((long)cipher.getBlockSize());
		v.outbuf = cape.Buffer.allocate((long)cipher.getBlockSize());
		return(v);
	}

	private BlockCipher cipher = null;
	private cape.Writer writer = null;
	private int bsize = 0;
	private int bcurr = 0;
	private byte[] bdata = null;
	private byte[] outbuf = null;

	protected void finalize() throws java.lang.Throwable {
		super.finalize();
		close();
	}

	public void close() {
		if((writer != null) && (bdata != null)) {
			byte[] bb = cape.Buffer.allocate((long)1);
			byte[] bbptr = bb;
			if(bcurr > 0) {
				int n = 0;
				for(n = bcurr ; n < bsize ; n++) {
					cape.Buffer.setByte(bdata, (long)n, (byte)0);
				}
				writeCompleteBlock(bdata);
				cape.Buffer.setByte(bbptr, (long)0, (byte)(bsize - bcurr));
				writer.write(bb, -1);
			}
			else {
				cape.Buffer.setByte(bbptr, (long)0, (byte)0);
				writer.write(bb, -1);
			}
		}
		writer = null;
		cipher = null;
		bdata = null;
	}

	public boolean setCurrentPosition(long n) {
		if((writer != null) && (writer instanceof cape.SeekableWriter)) {
			return(((cape.SeekableWriter)writer).setCurrentPosition(n));
		}
		return(false);
	}

	public long getCurrentPosition() {
		if((writer != null) && (writer instanceof cape.SeekableWriter)) {
			return(((cape.SeekableWriter)writer).getCurrentPosition());
		}
		return((long)-1);
	}

	public boolean writeCompleteBlock(byte[] buf) {
		cipher.encryptBlock(buf, outbuf);
		if(writer.write(outbuf, -1) == cape.Buffer.getSize(outbuf)) {
			return(true);
		}
		return(false);
	}

	public int writeBlock(byte[] buf) {
		long size = cape.Buffer.getSize(buf);
		if((bcurr + size) < bsize) {
			byte[] bufptr = buf;
			cape.Buffer.copyFrom(bufptr, bdata, (long)0, (long)bcurr, size);
			bcurr += (int)size;
			return((int)size);
		}
		if(bcurr > 0) {
			byte[] bufptr = buf;
			int x = bsize - bcurr;
			cape.Buffer.copyFrom(bufptr, bdata, (long)0, (long)bcurr, (long)x);
			if(writeCompleteBlock(bdata) == false) {
				return(0);
			}
			bcurr = 0;
			if(x == size) {
				return(x);
			}
			return(x + writeBlock(cape.Buffer.getSubBuffer(buf, (long)x, size - x)));
		}
		if(writeCompleteBlock(buf) == false) {
			return(0);
		}
		return(bsize);
	}

	public int write(byte[] buf, int asize) {
		if(buf == null) {
			return(0);
		}
		byte[] bufptr = buf;
		if(bufptr == null) {
			return(0);
		}
		int size = asize;
		if(size < 0) {
			size = (int)cape.Buffer.getSize(buf);
		}
		if(size < 1) {
			return(0);
		}
		int v = 0;
		int n = 0;
		for(n = 0 ; n < size ; n += bsize) {
			int x = bsize;
			if((n + x) > size) {
				x = size - n;
			}
			v += writeBlock(cape.Buffer.getSubBuffer(buf, (long)n, (long)x));
		}
		return(v);
	}

	public int write(byte[] buf) {
		return(write(buf, -1));
	}
}
