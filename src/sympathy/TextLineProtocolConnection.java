
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

abstract public class TextLineProtocolConnection extends TextProtocolConnection
{
	private boolean useCRLF = false;

	public void sendLine(java.lang.String text) {
		if(useCRLF) {
			sendText(text + "\r\n");
		}
		else {
			sendText(text + "\n");
		}
	}

	@Override
	public void onTextReceived(java.lang.String data) {
		if(android.text.TextUtils.equals(data, null)) {
			return;
		}
		java.lang.String str = data;
		if(cape.String.endsWith(str, "\r\n")) {
			str = cape.String.subString(str, 0, cape.String.getLength(str) - 2);
		}
		else if(cape.String.endsWith(str, "\n")) {
			str = cape.String.subString(str, 0, cape.String.getLength(str) - 1);
		}
		int nn = cape.String.indexOf(str, '\n');
		if(nn < 0) {
			onLineReceived(str);
			return;
		}
		java.util.ArrayList<java.lang.String> array = cape.String.split(str, '\n');
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.String line = array.get(n);
				if(line != null) {
					if(cape.String.endsWith(line, "\r")) {
						line = cape.String.subString(line, 0, cape.String.getLength(line) - 1);
					}
					onLineReceived(line);
				}
			}
		}
	}

	public abstract void onLineReceived(java.lang.String data);

	public boolean getUseCRLF() {
		return(useCRLF);
	}

	public TextLineProtocolConnection setUseCRLF(boolean v) {
		useCRLF = v;
		return(this);
	}
}
