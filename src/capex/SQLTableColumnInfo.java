
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

public class SQLTableColumnInfo
{
	public static SQLTableColumnInfo instance(java.lang.String name, int type) {
		return(new SQLTableColumnInfo().setName(name).setType(type));
	}

	public static SQLTableColumnInfo forInteger(java.lang.String name) {
		return(new SQLTableColumnInfo().setName(name).setType(TYPE_INTEGER));
	}

	public static SQLTableColumnInfo forString(java.lang.String name) {
		return(new SQLTableColumnInfo().setName(name).setType(TYPE_STRING));
	}

	public static SQLTableColumnInfo forStringKey(java.lang.String name) {
		return(new SQLTableColumnInfo().setName(name).setType(TYPE_STRING_KEY));
	}

	public static SQLTableColumnInfo forText(java.lang.String name) {
		return(new SQLTableColumnInfo().setName(name).setType(TYPE_TEXT));
	}

	public static SQLTableColumnInfo forIntegerKey(java.lang.String name) {
		return(new SQLTableColumnInfo().setName(name).setType(TYPE_INTEGER_KEY));
	}

	public static SQLTableColumnInfo forDouble(java.lang.String name) {
		return(new SQLTableColumnInfo().setName(name).setType(TYPE_DOUBLE));
	}

	public static SQLTableColumnInfo forBlob(java.lang.String name) {
		return(new SQLTableColumnInfo().setName(name).setType(TYPE_BLOB));
	}

	public static final int TYPE_INTEGER = 0;
	public static final int TYPE_STRING = 1;
	public static final int TYPE_TEXT = 2;
	public static final int TYPE_INTEGER_KEY = 3;
	public static final int TYPE_DOUBLE = 4;
	public static final int TYPE_BLOB = 5;
	public static final int TYPE_STRING_KEY = 6;
	private java.lang.String name = null;
	private int type = 0;

	public java.lang.String getName() {
		return(name);
	}

	public SQLTableColumnInfo setName(java.lang.String v) {
		name = v;
		return(this);
	}

	public int getType() {
		return(type);
	}

	public SQLTableColumnInfo setType(int v) {
		type = v;
		return(this);
	}
}
