
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

public class Integer
{
	private static class MyIntegerObject implements IntegerObject
	{
		private int integer = 0;

		public int toInteger() {
			return(integer);
		}

		public int getInteger() {
			return(integer);
		}

		public MyIntegerObject setInteger(int v) {
			integer = v;
			return(this);
		}
	}

	public static IntegerObject asObject(int integer) {
		MyIntegerObject v = new MyIntegerObject();
		v.setInteger(integer);
		return((IntegerObject)v);
	}

	public static int asInteger(java.lang.String str) {
		if(android.text.TextUtils.equals(str, null)) {
			return(0);
		}
		return(cape.String.toInteger(str));
	}

	public static int asInteger(java.lang.Object obj) {
		if(obj == null) {
			return(0);
		}
		if(obj instanceof IntegerObject) {
			return(((IntegerObject)obj).toInteger());
		}
		if(obj instanceof java.lang.String) {
			return(cape.String.toInteger((java.lang.String)obj));
		}
		if(obj instanceof StringObject) {
			return(cape.String.toInteger(((StringObject)obj).toString()));
		}
		if(obj instanceof DoubleObject) {
			return((int)((DoubleObject)obj).toDouble());
		}
		if(obj instanceof BooleanObject) {
			if(((BooleanObject)obj).toBoolean()) {
				return(1);
			}
			return(0);
		}
		if(obj instanceof CharacterObject) {
			return((int)((CharacterObject)obj).toCharacter());
		}
		return(0);
	}
}
