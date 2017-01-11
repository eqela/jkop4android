
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

public class JSONParser
{
	public static java.lang.Object parse(byte[] buffer) {
		if(buffer == null) {
			return(null);
		}
		return(new JSONParser(cape.String.forUTF8Buffer(buffer)).acceptObject());
	}

	public static java.lang.Object parse(java.lang.String str) {
		if(cape.String.isEmpty(str)) {
			return(null);
		}
		return(new JSONParser(str).acceptObject());
	}

	public static java.lang.Object parse(File file) {
		if(file == null) {
			return(null);
		}
		return(cape.JSONParser.parse(file.getContentsString("UTF-8")));
	}

	private CharacterIterator iterator = null;

	private JSONParser(java.lang.String str) {
		iterator = (CharacterIterator)new CharacterIteratorForString(str);
		iterator.moveToNextChar();
	}

	private void skipSpaces() {
		while(true) {
			if(iterator.hasEnded()) {
				break;
			}
			char c = iterator.getCurrentChar();
			if((((c == ' ') || (c == '\t')) || (c == '\r')) || (c == '\n')) {
				iterator.moveToNextChar();
				continue;
			}
			break;
		}
	}

	private boolean acceptChar(char c) {
		skipSpaces();
		if(iterator.getCurrentChar() == c) {
			iterator.moveToNextChar();
			return(true);
		}
		return(false);
	}

	private java.lang.String acceptString() {
		skipSpaces();
		char ss = iterator.getCurrentChar();
		if((ss != '\'') && (ss != '\"')) {
			return(null);
		}
		StringBuilder sb = new StringBuilder();
		while(true) {
			char c = iterator.getNextChar();
			if(c == ss) {
				iterator.moveToNextChar();
				break;
			}
			if(c == '\\') {
				c = iterator.getNextChar();
			}
			sb.append(c);
		}
		return(sb.toString());
	}

	private java.lang.Object acceptObject() {
		if(acceptChar('[')) {
			DynamicVector v = new DynamicVector();
			while(true) {
				if(acceptChar(']')) {
					break;
				}
				java.lang.Object o = acceptObject();
				if(o == null) {
					return(null);
				}
				v.append(o);
				acceptChar(',');
			}
			return((java.lang.Object)v);
		}
		if(acceptChar('{')) {
			DynamicMap v = new DynamicMap();
			while(true) {
				if(acceptChar('}')) {
					break;
				}
				java.lang.String key = acceptString();
				if(android.text.TextUtils.equals(key, null)) {
					return(null);
				}
				if(acceptChar(':') == false) {
					return(null);
				}
				java.lang.Object val = acceptObject();
				if(val == null) {
					return(null);
				}
				v.set(key, val);
				acceptChar(',');
			}
			return((java.lang.Object)v);
		}
		java.lang.String s = acceptString();
		if(!(android.text.TextUtils.equals(s, null))) {
			return((java.lang.Object)s);
		}
		return(null);
	}
}
