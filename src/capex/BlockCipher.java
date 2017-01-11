
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

abstract public class BlockCipher
{
	public static byte[] encryptString(java.lang.String data, BlockCipher cipher) {
		if(android.text.TextUtils.equals(data, null)) {
			return(null);
		}
		return(encryptBuffer(cape.String.toUTF8Buffer(data), cipher));
	}

	public static java.lang.String decryptString(byte[] data, BlockCipher cipher) {
		byte[] db = decryptBuffer(data, cipher);
		if(db == null) {
			return(null);
		}
		return(cape.String.forUTF8Buffer(db));
	}

	public static byte[] encryptBuffer(byte[] data, BlockCipher cipher) {
		if((cipher == null) || (data == null)) {
			return(null);
		}
		cape.BufferWriter bw = new cape.BufferWriter();
		if(bw == null) {
			return(null);
		}
		BlockCipherWriter ww = capex.BlockCipherWriter.create((cape.Writer)bw, cipher);
		if(ww == null) {
			return(null);
		}
		int r = ww.write(data);
		ww.close();
		if(r < cape.Buffer.getSize(data)) {
			return(null);
		}
		return(bw.getBuffer());
	}

	public static byte[] decryptBuffer(byte[] data, BlockCipher cipher) {
		if((cipher == null) || (data == null)) {
			return(null);
		}
		cape.BufferReader br = cape.BufferReader.forBuffer(data);
		if(br == null) {
			return(null);
		}
		cape.SizedReader rr = capex.BlockCipherReader.create((cape.SizedReader)br, cipher);
		if(rr == null) {
			return(null);
		}
		byte[] db = cape.Buffer.allocate(cape.Buffer.getSize(data));
		if(db == null) {
			return(null);
		}
		int ll = rr.read(db);
		if(ll < 0) {
			return(null);
		}
		if(ll < cape.Buffer.getSize(db)) {
			cape.Buffer.allocate((long)ll);
		}
		return(db);
	}

	public abstract int getBlockSize();
	public abstract void encryptBlock(byte[] src, byte[] dest);
	public abstract void decryptBlock(byte[] src, byte[] dest);
}
