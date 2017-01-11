
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

public class StringBuilder implements StringObject
{
	private java.lang.StringBuilder builder = null;

	public StringBuilder() {
		initialize();
	}

	public StringBuilder(java.lang.String initial) {
		initialize();
		append(initial);
	}

	public StringBuilder(StringBuilder initial) {
		initialize();
		if(initial != null) {
			append(initial.toString());
		}
	}

	public void initialize() {
		builder = new java.lang.StringBuilder();
	}

	public void clear() {
		builder.setLength(0);
	}

	public int count() {
		return(builder.length());
	}

	public StringBuilder append(int c) {
		return(append(cape.String.forInteger(c)));
	}

	public StringBuilder append(char c) {
		if(c == 0) {
			return(this);
		}
		return(append(cape.String.forCharacter(c)));
	}

	public StringBuilder append(double c) {
		return(append(cape.String.forDouble(c)));
	}

	public StringBuilder append(float c) {
		return(append(cape.String.forFloat(c)));
	}

	public StringBuilder append(java.lang.String str) {
		if(android.text.TextUtils.equals(str, null)) {
			return(this);
		}
		builder.append(str);
		return(this);
	}

	public StringBuilder insert(int index, int c) {
		return(insert(index, cape.String.forInteger(c)));
	}

	public StringBuilder insert(int index, char c) {
		if(c == 0) {
			return(this);
		}
		return(insert(index, cape.String.forCharacter(c)));
	}

	public StringBuilder insert(int index, double c) {
		return(insert(index, cape.String.forDouble(c)));
	}

	public StringBuilder insert(int index, float c) {
		return(insert(index, cape.String.forFloat(c)));
	}

	public StringBuilder insert(int index, java.lang.String str) {
		if(android.text.TextUtils.equals(str, null)) {
			return(this);
		}
		builder.insert(index, str);
		return(this);
	}

	public java.lang.String toString() {
		return(builder.toString());
	}
}
