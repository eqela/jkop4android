
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

public class WordWrap
{
	public static java.util.ArrayList<java.lang.String> wrapToLines(java.lang.String text, int charactersPerLine) {
		if(android.text.TextUtils.equals(text, null)) {
			return(null);
		}
		java.util.ArrayList<java.lang.String> v = new java.util.ArrayList<java.lang.String>();
		cape.CharacterIterator it = cape.String.iterate(text);
		cape.StringBuilder lineBuilder = null;
		cape.StringBuilder wordBuilder = null;
		while(it != null) {
			char c = it.getNextChar();
			if((((c == ' ') || (c == '\t')) || (c == '\n')) || (c < 1)) {
				if(wordBuilder != null) {
					java.lang.String word = wordBuilder.toString();
					wordBuilder = null;
					if(lineBuilder == null) {
						lineBuilder = new cape.StringBuilder();
					}
					int cc = lineBuilder.count();
					if(cc > 0) {
						cc++;
					}
					cc += cape.String.getLength(word);
					if(cc > charactersPerLine) {
						v.add(lineBuilder.toString());
						lineBuilder = new cape.StringBuilder();
					}
					if(lineBuilder.count() > 0) {
						lineBuilder.append(' ');
					}
					lineBuilder.append(word);
				}
				if(c < 1) {
					break;
				}
				continue;
			}
			if(wordBuilder == null) {
				wordBuilder = new cape.StringBuilder();
			}
			wordBuilder.append(c);
		}
		if(lineBuilder != null) {
			v.add(lineBuilder.toString());
		}
		return(v);
	}
}
