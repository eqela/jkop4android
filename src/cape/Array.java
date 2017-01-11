
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

/**
 * The Array class provides convenience methods for dealing with static array
 * objects. For dynamic array support, use vectors instead.
 */

public class Array
{
	private static class MyArrayObject<T> implements ArrayObject<T>, ObjectWithSize
	{
		private T[] array = null;

		public T[] toArray() {
			return(array);
		}

		public int getSize() {
			return(array.length);
		}

		public T[] getArray() {
			return(array);
		}

		public MyArrayObject<T> setArray(T[] v) {
			array = v;
			return(this);
		}
	}

	/**
	 * Returns the given array as an ArrayObject (which is an object type) that can be
	 * used wherever an object is required.
	 */

	public static <T> ArrayObject<T> asObject(T[] array) {
		MyArrayObject<T> v = new MyArrayObject<T>();
		v.setArray(array);
		return((ArrayObject<T>)v);
	}

	public static <T> boolean isEmpty(T[] array) {
		if(array == null) {
			return(true);
		}
		if(array.length < 1) {
			return(true);
		}
		return(false);
	}

	/**
	 * Creates a new vector object that will be composed of all the elements in the
	 * given array. Essentially converts an array into a vector.
	 */

	public static <T> java.util.ArrayList<T> toVector(T[] array) {
		return(cape.Vector.forArray(array));
	}
}
