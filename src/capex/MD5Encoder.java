
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

public class MD5Encoder
{
	public static java.lang.String encode(byte[] buffer) {
		if((buffer == null) || (cape.Buffer.getSize(buffer) < 1)) {
			return(null);
		}
		java.lang.String v = null;
		java.security.MessageDigest digest;
		try {
			digest = java.security.MessageDigest.getInstance("MD5");
		}
		catch(Exception e) {
			e.printStackTrace();
			return(null);
		}
		digest.update(buffer);
		byte messageDigest[] = digest.digest();
		java.lang.StringBuffer hexString = new java.lang.StringBuffer();
		for(int i=0; i < messageDigest.length; i++) {
			java.lang.String ii = java.lang.Integer.toHexString(0xFF & messageDigest[i]);
			if(ii.length() < 2) {
				hexString.append("0");
			}
			hexString.append(ii);
		}
		v = hexString.toString();
		return(v);
	}

	public static java.lang.String encode(java.lang.String string) {
		return(capex.MD5Encoder.encode(cape.String.toUTF8Buffer(string)));
	}

	public static java.lang.String encode(java.lang.Object obj) {
		byte[] bb = cape.Buffer.asBuffer(obj);
		if(bb != null) {
			return(capex.MD5Encoder.encode(bb));
		}
		java.lang.String ss = cape.String.asString(obj);
		if(!(android.text.TextUtils.equals(ss, null))) {
			return(capex.MD5Encoder.encode(ss));
		}
		return(null);
	}
}
