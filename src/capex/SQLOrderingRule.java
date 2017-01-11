
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

public class SQLOrderingRule
{
	public static SQLOrderingRule forDescending(java.lang.String column) {
		SQLOrderingRule v = new SQLOrderingRule();
		v.setColumn(column);
		v.setDescending(true);
		return(v);
	}

	public static SQLOrderingRule forAscending(java.lang.String column) {
		SQLOrderingRule v = new SQLOrderingRule();
		v.setColumn(column);
		v.setDescending(false);
		return(v);
	}

	private java.lang.String column = null;
	private boolean descending = false;

	public java.lang.String getColumn() {
		return(column);
	}

	public SQLOrderingRule setColumn(java.lang.String v) {
		column = v;
		return(this);
	}

	public boolean getDescending() {
		return(descending);
	}

	public SQLOrderingRule setDescending(boolean v) {
		descending = v;
		return(this);
	}
}
