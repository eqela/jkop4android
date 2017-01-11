
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

public class Vector
{
	public static <T> java.util.ArrayList<T> asVector(java.lang.Object obj) {
		VectorObject<T> vo = (VectorObject<T>)((obj instanceof VectorObject) ? obj : null);
		if(vo == null) {
			return(null);
		}
		return(vo.toVector());
	}

	public static <T> java.util.ArrayList<T> forIterator(Iterator<T> iterator) {
		if(iterator == null) {
			return(null);
		}
		java.util.ArrayList<T> v = new java.util.ArrayList<T>();
		while(true) {
			T o = iterator.next();
			if(o == null) {
				break;
			}
			v.add(o);
		}
		return(v);
	}

	public static <T> java.util.ArrayList<T> forArray(T[] array) {
		if(array == null) {
			return(null);
		}
		java.util.ArrayList<T> v = new java.util.ArrayList<T>();
		for(int n = 0 ; n < array.length ; n++) {
			v.add(array[n]);
		}
		return(v);
	}

	public static <T> void append(java.util.ArrayList<T> vector, T object) {
		vector.add(object);
	}

	public static <T> int getSize(java.util.ArrayList<T> vector) {
		if(vector == null) {
			return(0);
		}
		return(vector.size());
	}

	public static <T> T getAt(java.util.ArrayList<T> vector, int index) {
		return(get(vector, index));
	}

	public static <T> T get(java.util.ArrayList<T> vector, int index) {
		if((index < 0) || (index >= getSize(vector))) {
			return((T)(null));
		}
		return(vector.get(index));
	}

	public static <T> void set(java.util.ArrayList<T> vector, int index, T val) {
		if((index < 0) || (index >= getSize(vector))) {
			return;
		}
		vector.set(index, val);
	}

	public static <T> T remove(java.util.ArrayList<T> vector, int index) {
		if((index < 0) || (index >= getSize(vector))) {
			return((T)(null));
		}
		return(vector.remove(index));
	}

	public static <T> T popFirst(java.util.ArrayList<T> vector) {
		if((vector == null) || (getSize(vector) < 1)) {
			return((T)(null));
		}
		T v = get(vector, 0);
		removeFirst(vector);
		return(v);
	}

	public static <T> void removeFirst(java.util.ArrayList<T> vector) {
		if((vector == null) || (getSize(vector) < 1)) {
			return;
		}
		remove(vector, 0);
	}

	public static <T> T popLast(java.util.ArrayList<T> vector) {
		if((vector == null) || (getSize(vector) < 1)) {
			return((T)(null));
		}
		T v = get(vector, getSize(vector) - 1);
		removeLast(vector);
		return(v);
	}

	public static <T> void removeLast(java.util.ArrayList<T> vector) {
		if(vector == null) {
			return;
		}
		int sz = getSize(vector);
		if(sz < 1) {
			return;
		}
		remove(vector, sz - 1);
	}

	public static <T> int removeValue(java.util.ArrayList<T> vector, T value) {
		int n = 0;
		for(n = 0 ; n < vector.size() ; n++) {
			if(vector.get(n) == value) {
				remove(vector, n);
				return(n);
			}
		}
		return(-1);
	}

	public static <T> void clear(java.util.ArrayList<T> vector) {
		vector.clear();
	}

	public static <T> boolean isEmpty(java.util.ArrayList<T> vector) {
		return((vector == null) || (vector.isEmpty()));
	}

	public static <T> void removeRange(java.util.ArrayList<T> vector, int index, int count) {
		vector.subList(index, index+count).clear();
	}

	private static class VectorIterator<T> implements Iterator<T>
	{
		public java.util.ArrayList<T> vector = null;
		private int index = 0;
		private int increment = 1;

		public VectorIterator(java.util.ArrayList<T> vector, int increment) {
			this.vector = vector;
			this.increment = increment;
			if((increment < 0) && (vector != null)) {
				index = getSize(vector) - 1;
			}
		}

		public T next() {
			if(vector == null) {
				return((T)(null));
			}
			if((index < 0) || (index >= getSize(vector))) {
				return((T)(null));
			}
			T v = vector.get(index);
			index += increment;
			return(v);
		}
	}

	public static <T> Iterator<T> iterate(java.util.ArrayList<T> vector) {
		return((Iterator<T>)new VectorIterator<T>(vector, 1));
	}

	public static <T> Iterator<T> iterateReverse(java.util.ArrayList<T> vector) {
		return((Iterator<T>)new VectorIterator<T>(vector, -1));
	}

	public static <T> void sort(java.util.ArrayList<T> vector, samx.function.Function2<java.lang.Integer,T,T> comparer) {
		if(vector == null) {
			return;
		}
		System.out.println("[cape.Vector.sort] (Vector.sling:394:2): Not implemented");
	}

	public static <T> void sortReverse(java.util.ArrayList<T> vector, samx.function.Function2<java.lang.Integer,T,T> comparer) {
		final samx.function.Function2<java.lang.Integer,T,T> cc = comparer;
		sort(vector, new samx.function.Function2<java.lang.Integer, T, T>() {
			public java.lang.Integer execute(T a, T b) {
				return(-cc.execute(a, b));
			}
		});
	}
}
