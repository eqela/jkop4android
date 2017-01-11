
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

abstract public class SQLResultSetIterator implements cape.Iterator<cape.DynamicMap>
{
	public abstract cape.DynamicMap next();
	public abstract boolean nextValues(java.util.ArrayList<java.lang.Object> values);
	public abstract boolean step();
	public abstract int getColumnCount();
	public abstract java.lang.String getColumnName(int n);
	public abstract java.util.ArrayList<java.lang.String> getColumnNames();
	public abstract java.lang.Object getColumnObject(int n);
	public abstract int getColumnInt(int n);
	public abstract double getColumnDouble(int n);
	public abstract byte[] getColumnBuffer(int n);
	public abstract void close();

	public cape.DynamicVector toVector() {
		cape.DynamicVector v = new cape.DynamicVector();
		while(true) {
			cape.DynamicMap o = next();
			if(o == null) {
				break;
			}
			v.append((java.lang.Object)o);
		}
		return(v);
	}
}
