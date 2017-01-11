
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

public class URLDecoder
{
	public static int xcharToInteger(char c) {
		if((c >= '0') && (c <= '9')) {
			return(((int)c) - '0');
		}
		else if((c >= 'a') && (c <= 'f')) {
			return((10 + c) - 'a');
		}
		else if((c >= 'A') && (c <= 'F')) {
			return((10 + c) - 'A');
		}
		return(0);
	}

	public static java.lang.String decode(java.lang.String astr) {
		if(android.text.TextUtils.equals(astr, null)) {
			return(null);
		}
		cape.StringBuilder sb = new cape.StringBuilder();
		java.lang.String str = cape.String.strip(astr);
		cape.CharacterIterator it = cape.String.iterate(str);
		while(it != null) {
			char x = it.getNextChar();
			if(x < 1) {
				break;
			}
			if(x == '%') {
				char x1 = it.getNextChar();
				char x2 = it.getNextChar();
				if((x1 > 0) && (x2 > 0)) {
					sb.append((char)((xcharToInteger(x1) * 16) + xcharToInteger(x2)));
				}
				else {
					break;
				}
			}
			else if(x == '+') {
				sb.append(' ');
			}
			else {
				sb.append(x);
			}
		}
		return(sb.toString());
	}
}
