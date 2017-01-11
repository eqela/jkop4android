
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
 * The Boolean class provides convenience methods for dealing with boolean values
 * (either true or false).
 */

public class Boolean
{
	private static class MyBooleanObject implements BooleanObject
	{
		private boolean value = false;

		public boolean toBoolean() {
			return(value);
		}

		public boolean getValue() {
			return(value);
		}

		public MyBooleanObject setValue(boolean v) {
			value = v;
			return(this);
		}
	}

	/**
	 * Returns the given boolean value as a BooleanObject (which is an object type)
	 * that can be used wherever an object is required.
	 */

	public static BooleanObject asObject(boolean value) {
		MyBooleanObject v = new MyBooleanObject();
		v.setValue(value);
		return((BooleanObject)v);
	}

	/**
	 * Converts the given object to a boolean value, as much as is possible. Various
	 * different objects can be supplied (including instances of BooleanObject,
	 * IntegerObject, StringObject, DoubleObject, CharacterObject, ObjectWithSize as
	 * well as strings.
	 */

	public static boolean asBoolean(java.lang.Object obj) {
		if(obj == null) {
			return(false);
		}
		if(obj instanceof BooleanObject) {
			return(((BooleanObject)obj).toBoolean());
		}
		if(obj instanceof IntegerObject) {
			if(((IntegerObject)obj).toInteger() == 0) {
				return(false);
			}
			return(true);
		}
		if(obj instanceof java.lang.String) {
			java.lang.String str = cape.String.toLowerCase((java.lang.String)obj);
			if((android.text.TextUtils.equals(str, "yes")) || (android.text.TextUtils.equals(str, "true"))) {
				return(true);
			}
			return(false);
		}
		if(obj instanceof StringObject) {
			java.lang.String str = ((StringObject)obj).toString();
			if(!(android.text.TextUtils.equals(str, null))) {
				str = cape.String.toLowerCase(str);
				if((android.text.TextUtils.equals(str, "yes")) || (android.text.TextUtils.equals(str, "true"))) {
					return(true);
				}
			}
			return(false);
		}
		if(obj instanceof DoubleObject) {
			if(((DoubleObject)obj).toDouble() == 0.00) {
				return(false);
			}
			return(true);
		}
		if(obj instanceof CharacterObject) {
			if(((int)((CharacterObject)obj).toCharacter()) == 0) {
				return(false);
			}
			return(true);
		}
		if(obj instanceof ObjectWithSize) {
			int sz = ((ObjectWithSize)obj).getSize();
			if(sz == 0) {
				return(false);
			}
			return(true);
		}
		return(false);
	}
}
