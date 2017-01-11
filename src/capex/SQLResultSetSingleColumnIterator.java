
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

public class SQLResultSetSingleColumnIterator implements cape.DynamicIterator, cape.StringIterator, cape.IntegerIterator, cape.DoubleIterator, cape.BooleanIterator, cape.Iterator<java.lang.Object>
{
	private SQLResultSetIterator iterator = null;
	private java.lang.String columnName = null;

	public cape.DynamicMap nextMap() {
		if(iterator == null) {
			return(null);
		}
		cape.DynamicMap r = iterator.next();
		if(r == null) {
			return(null);
		}
		return(r);
	}

	public java.lang.Object next() {
		cape.DynamicMap m = nextMap();
		if(m == null) {
			return(null);
		}
		return(m.get(columnName));
	}

	public java.lang.String nextString() {
		cape.DynamicMap m = nextMap();
		if(m == null) {
			return(null);
		}
		return(m.getString(columnName));
	}

	public int nextInteger() {
		cape.DynamicMap m = nextMap();
		if(m == null) {
			return(0);
		}
		return(m.getInteger(columnName));
	}

	public double nextDouble() {
		cape.DynamicMap m = nextMap();
		if(m == null) {
			return(0.00);
		}
		return(m.getDouble(columnName));
	}

	public boolean nextBoolean() {
		cape.DynamicMap m = nextMap();
		if(m == null) {
			return(false);
		}
		return(m.getBoolean(columnName));
	}

	public SQLResultSetIterator getIterator() {
		return(iterator);
	}

	public SQLResultSetSingleColumnIterator setIterator(SQLResultSetIterator v) {
		iterator = v;
		return(this);
	}

	public java.lang.String getColumnName() {
		return(columnName);
	}

	public SQLResultSetSingleColumnIterator setColumnName(java.lang.String v) {
		columnName = v;
		return(this);
	}
}
