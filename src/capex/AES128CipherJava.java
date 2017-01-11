
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

public class AES128CipherJava extends BlockCipher
{
	java.security.Key key;

	public static AES128CipherJava create(java.lang.Object k) {
		AES128CipherJava v = new AES128CipherJava();
		if(v.setKey(k)) {
			return(v);
		}
		return(null);
	}

	public boolean setKey(java.lang.Object o) {
		byte[] key = null;
		if(o == null) {
			;
		}
		else if(o instanceof byte[]) {
			key = (byte[])((o instanceof byte[]) ? o : null);
		}
		else if(o instanceof java.lang.String) {
			java.lang.String keyhash = capex.MD5Encoder.encode(o);
			key = cape.Buffer.forHexString(keyhash);
		}
		if(key != null) {
			byte[] kptr = key;
			if(kptr != null) {
				this.key = new javax.crypto.spec.SecretKeySpec(kptr, 0, kptr.length, "AES");
			}
			return(true);
		}
		return(false);
	}

	@Override
	public int getBlockSize() {
		return(16);
	}

	public void doCipher(byte[] src, byte[] dest, int mode) {
		if((((src == null) || (dest == null)) || (cape.Buffer.getSize(src) != 16)) || (cape.Buffer.getSize(dest) != 16)) {
			return;
		}
		try {
			javax.crypto.Cipher aes = javax.crypto.Cipher.getInstance("AES/ECB/NoPadding");
			aes.init(mode, key);
			aes.doFinal(src, 0, (int)cape.Buffer.getSize(src), dest, 0);
		}
		catch(java.lang.Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void encryptBlock(byte[] src, byte[] dest) {
		doCipher(src, dest, javax.crypto.Cipher.ENCRYPT_MODE);
	}

	@Override
	public void decryptBlock(byte[] src, byte[] dest) {
		doCipher(src, dest, javax.crypto.Cipher.DECRYPT_MODE);
	}
}
