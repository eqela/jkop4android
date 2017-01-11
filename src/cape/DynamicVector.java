
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

public class DynamicVector implements Duplicateable, Iterateable<java.lang.Object>, VectorObject<java.lang.Object>, ObjectWithSize
{
	public static DynamicVector asDynamicVector(java.lang.Object object) {
		if(object == null) {
			return(null);
		}
		if(object instanceof DynamicVector) {
			return((DynamicVector)object);
		}
		if(object instanceof java.util.ArrayList) {
			return(forObjectVector((java.util.ArrayList<java.lang.Object>)object));
		}
		return(null);
	}

	public static DynamicVector forStringVector(java.util.ArrayList<java.lang.String> vector) {
		DynamicVector v = new DynamicVector();
		if(vector != null) {
			int n = 0;
			int m = vector.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.String item = vector.get(n);
				if(item != null) {
					v.append((java.lang.Object)item);
				}
			}
		}
		return(v);
	}

	public static DynamicVector forObjectVector(java.util.ArrayList<java.lang.Object> vector) {
		DynamicVector v = new DynamicVector();
		if(vector != null) {
			int n = 0;
			int m = vector.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.Object item = vector.get(n);
				if(item != null) {
					v.append(item);
				}
			}
		}
		return(v);
	}

	private java.util.ArrayList<java.lang.Object> vector = null;

	public DynamicVector() {
		vector = new java.util.ArrayList<java.lang.Object>();
	}

	public java.util.ArrayList<java.lang.Object> toVector() {
		return(vector);
	}

	public java.util.ArrayList<java.lang.String> toVectorOfStrings() {
		java.util.ArrayList<java.lang.String> v = new java.util.ArrayList<java.lang.String>();
		if(vector != null) {
			int n = 0;
			int m = vector.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.Object to = vector.get(n);
				java.lang.String o = null;
				if(to instanceof java.lang.String) {
					o = (java.lang.String)to;
				}
				if(o != null) {
					v.add(o);
				}
			}
		}
		return(v);
	}

	public java.util.ArrayList<DynamicMap> toVectorOfDynamicMaps() {
		java.util.ArrayList<DynamicMap> v = new java.util.ArrayList<DynamicMap>();
		if(vector != null) {
			int n = 0;
			int m = vector.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.Object to = vector.get(n);
				DynamicMap o = null;
				if(to instanceof DynamicMap) {
					o = (DynamicMap)to;
				}
				if(o != null) {
					v.add(o);
				}
			}
		}
		return(v);
	}

	public java.lang.Object duplicate() {
		DynamicVector v = new DynamicVector();
		Iterator<java.lang.Object> it = iterate();
		while(it != null) {
			java.lang.Object o = it.next();
			if(o == null) {
				break;
			}
			v.append(o);
		}
		return((java.lang.Object)v);
	}

	public DynamicVector append(java.lang.Object object) {
		vector.add(object);
		return(this);
	}

	public DynamicVector append(int value) {
		vector.add(cape.Integer.asObject(value));
		return(this);
	}

	public DynamicVector append(boolean value) {
		vector.add(cape.Boolean.asObject(value));
		return(this);
	}

	public DynamicVector append(double value) {
		vector.add(cape.Double.asObject(value));
		return(this);
	}

	public DynamicVector set(int index, java.lang.Object object) {
		cape.Vector.set(vector, index, object);
		return(this);
	}

	public DynamicVector set(int index, int value) {
		cape.Vector.set(vector, index, cape.Integer.asObject(value));
		return(this);
	}

	public DynamicVector set(int index, boolean value) {
		cape.Vector.set(vector, index, cape.Boolean.asObject(value));
		return(this);
	}

	public DynamicVector set(int index, double value) {
		cape.Vector.set(vector, index, cape.Double.asObject(value));
		return(this);
	}

	public java.lang.Object get(int index) {
		return(cape.Vector.getAt(vector, index));
	}

	public java.lang.String getString(int index, java.lang.String defval) {
		java.lang.String v = cape.String.asString(get(index));
		if(android.text.TextUtils.equals(v, null)) {
			return(defval);
		}
		return(v);
	}

	public java.lang.String getString(int index) {
		return(getString(index, null));
	}

	public int getInteger(int index, int defval) {
		java.lang.Object vv = get(index);
		if(vv == null) {
			return(defval);
		}
		return(cape.Integer.asInteger(vv));
	}

	public int getInteger(int index) {
		return(getInteger(index, 0));
	}

	public boolean getBoolean(int index, boolean defval) {
		java.lang.Object vv = get(index);
		if(vv == null) {
			return(defval);
		}
		return(cape.Boolean.asBoolean(vv));
	}

	public boolean getBoolean(int index) {
		return(getBoolean(index, false));
	}

	public double getDouble(int index, double defval) {
		java.lang.Object vv = get(index);
		if(vv == null) {
			return(defval);
		}
		return(cape.Double.asDouble(vv));
	}

	public double getDouble(int index) {
		return(getDouble(index, 0.00));
	}

	static public DynamicMap objectAsCapeDynamicMap(java.lang.Object o) {
		if(o instanceof DynamicMap) {
			return((DynamicMap)o);
		}
		return(null);
	}

	public DynamicMap getMap(int index) {
		return(objectAsCapeDynamicMap((java.lang.Object)get(index)));
	}

	static public DynamicVector objectAsCapeDynamicVector(java.lang.Object o) {
		if(o instanceof DynamicVector) {
			return((DynamicVector)o);
		}
		return(null);
	}

	public DynamicVector getVector(int index) {
		return(objectAsCapeDynamicVector((java.lang.Object)get(index)));
	}

	public Iterator<java.lang.Object> iterate() {
		Iterator<java.lang.Object> v = cape.Vector.iterate(vector);
		return(v);
	}

	public void remove(int index) {
		cape.Vector.remove(vector, index);
	}

	public void clear() {
		cape.Vector.clear(vector);
	}

	public int getSize() {
		return(cape.Vector.getSize(vector));
	}

	public void sort() {
		cape.Vector.sort(vector, new samx.function.Function2<java.lang.Integer, java.lang.Object, java.lang.Object>() {
			public java.lang.Integer execute(java.lang.Object a, java.lang.Object b) {
				return(cape.String.compare(cape.String.asString(a), cape.String.asString(b)));
			}
		});
	}

	public void sort(samx.function.Function2<java.lang.Integer,java.lang.Object,java.lang.Object> comparer) {
		if(comparer == null) {
			sort();
			return;
		}
		cape.Vector.sort(vector, comparer);
	}

	public void sortReverse() {
		cape.Vector.sortReverse(vector, new samx.function.Function2<java.lang.Integer, java.lang.Object, java.lang.Object>() {
			public java.lang.Integer execute(java.lang.Object a, java.lang.Object b) {
				return(cape.String.compare(cape.String.asString(a), cape.String.asString(b)));
			}
		});
	}

	public void sortReverse(samx.function.Function2<java.lang.Integer,java.lang.Object,java.lang.Object> comparer) {
		if(comparer == null) {
			sortReverse();
			return;
		}
		cape.Vector.sortReverse(vector, comparer);
	}
}
