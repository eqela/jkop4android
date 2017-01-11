
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

abstract public class TextProtocolConnection extends NetworkConnection
{
	private java.lang.String encoding = null;

	@Override
	public void onDataReceived(byte[] data, int size) {
		if(size < 1) {
			return;
		}
		byte[] sb = cape.Buffer.getSubBuffer(data, (long)0, (long)size);
		if(sb == null) {
			return;
		}
		java.lang.String str = null;
		if(android.text.TextUtils.equals(encoding, null)) {
			str = cape.String.forUTF8Buffer(sb);
		}
		else {
			str = cape.String.forBuffer(data, encoding);
		}
		if(android.text.TextUtils.equals(str, null)) {
			return;
		}
		onTextReceived(str);
	}

	public void sendText(java.lang.String text) {
		if(android.text.TextUtils.equals(text, null)) {
			return;
		}
		sendData(cape.String.toUTF8Buffer(text), -1);
	}

	public abstract void onTextReceived(java.lang.String data);

	public java.lang.String getEncoding() {
		return(encoding);
	}

	public TextProtocolConnection setEncoding(java.lang.String v) {
		encoding = v;
		return(this);
	}
}
