
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
 * The String class provides all the common string manipulation functions,
 * including comparisons, concatenation, transformations to upper and lowercase,
 * getting substrings, finding characters or substrings, splitting the strings,
 * converting to byte buffers, etc.
 */

public class String
{
	/**
	 * Converts an arbitrary object to a string, if possible.
	 */

	public static java.lang.String asString(java.lang.Object obj) {
		if(obj == null) {
			return(null);
		}
		if(obj instanceof java.lang.String) {
			return((java.lang.String)obj);
		}
		if(obj instanceof StringObject) {
			return(((StringObject)obj).toString());
		}
		if(obj instanceof IntegerObject) {
			return(forInteger(((IntegerObject)obj).toInteger()));
		}
		if(obj instanceof DoubleObject) {
			return(forDouble(((DoubleObject)obj).toDouble()));
		}
		if(obj instanceof BooleanObject) {
			return(forBoolean(((BooleanObject)obj).toBoolean()));
		}
		if(obj instanceof CharacterObject) {
			return(forCharacter(((CharacterObject)obj).toCharacter()));
		}
		java.lang.String js = cape.JSONEncoder.encode(obj);
		if(!(android.text.TextUtils.equals(js, null))) {
			return(js);
		}
		return(null);
	}

	/**
	 * Converts an integer to a string
	 */

	public static java.lang.String asString(int value) {
		return(forInteger(value));
	}

	/**
	 * Converts a double to a string
	 */

	public static java.lang.String asString(double value) {
		return(forDouble(value));
	}

	/**
	 * Converts a buffer to a string
	 */

	public static java.lang.String asString(byte[] value) {
		return(forUTF8Buffer(value));
	}

	/**
	 * Converts a boolean to a string
	 */

	public static java.lang.String asString(boolean value) {
		return(forBoolean(value));
	}

	/**
	 * Converts a character to a string
	 */

	public static java.lang.String asString(char value) {
		return(forCharacter(value));
	}

	/**
	 * Converts a float to a string
	 */

	public static java.lang.String asString(float value) {
		return(forFloat(value));
	}

	/**
	 * Checks if a string is empty (ie., either the object is a null pointer or the
	 * length of the string is less than one character).
	 */

	public static boolean isEmpty(java.lang.String str) {
		if(android.text.TextUtils.equals(str, null)) {
			return(true);
		}
		if(getLength(str) < 1) {
			return(true);
		}
		return(false);
	}

	/**
	 * Constructs a new string, given a buffer of bytes and the name of the encoding.
	 * Supported encodings: UTF8, UCS2 and ASCII
	 */

	public static java.lang.String forBuffer(byte[] data, java.lang.String encoding) {
		if(data == null) {
			return(null);
		}
		if(equalsIgnoreCase("UTF8", encoding) || equalsIgnoreCase("UTF-8", encoding)) {
			return(forUTF8Buffer(data));
		}
		if(equalsIgnoreCase("UCS2", encoding) || equalsIgnoreCase("UCS-2", encoding)) {
			return(forUCS2Buffer(data));
		}
		if(equalsIgnoreCase("ASCII", encoding)) {
			return(forASCIIBuffer(data));
		}
		return(null);
	}

	/**
	 * Constructs a new string for a buffer of bytes, encoded using the ASCII character
	 * set.
	 */

	public static java.lang.String forASCIIBuffer(byte[] data) {
		if(data == null) {
			return(null);
		}
		java.lang.String v = null;
		try {
			v = new java.lang.String(data, "US-ASCII");
		}
		catch(Exception e) {
			System.err.println(e.toString());
		}
		return(v);
	}

	/**
	 * Constructs a new string for a buffer of bytes, encoded using the UTF8 character
	 * encoding.
	 */

	public static java.lang.String forUTF8Buffer(byte[] data) {
		if(data == null) {
			return(null);
		}
		java.lang.String v = null;
		try {
			v = new java.lang.String(data, "UTF-8");
		}
		catch(Exception e) {
			System.err.println(e.toString());
		}
		return(v);
	}

	/**
	 * Constructs a new string for a buffer of bytes, encoded using the UCS2 character
	 * encoding.
	 */

	public static java.lang.String forUCS2Buffer(byte[] data) {
		if(data == null) {
			return(null);
		}
		java.lang.String v = null;
		try {
			v = new java.lang.String(data, "UCS-2");
		}
		catch(Exception e) {
			System.err.println(e.toString());
		}
		return(v);
	}

	/**
	 * Constructs a new string for an array or characters.
	 */

	public static java.lang.String forCharArray(char[] chars, int offset, int count) {
		if(chars == null) {
			return(null);
		}
		return(new java.lang.String(chars, offset, count));
	}

	/**
	 * Converts a boolean value to a string. The resulting string will be either "true"
	 * or "false", depending of the boolean value.
	 */

	public static java.lang.String forBoolean(boolean vv) {
		if(vv == true) {
			return("true");
		}
		return("false");
	}

	/**
	 * Converts an integer value to a string. The resulting string will be a string
	 * representation of the value of the integer using the "base-10" decimal notation.
	 */

	public static java.lang.String forInteger(int vv) {
		return(java.lang.String.valueOf(vv));
	}

	/**
	 * Converts a long integer value to a string. The resulting string will be a string
	 * representation of the value of the integer using the "base-10" decimal notation.
	 */

	public static java.lang.String forLong(long vv) {
		return(java.lang.String.valueOf(vv));
	}

	/**
	 * Converts an integer value to a string while ensuring that the length of the
	 * resulting string will reach or exceed the given "length". If the length of the
	 * string naturally is less than the given length, then "padding" characters will
	 * be prepended to the string in order to make the string long enough. The default
	 * padding character is "0", but can be customized with the "paddingString"
	 * parameter. eg. String.forIntegerWithPadding(9, 3, "0") would yield "009". eg.
	 * String.forIntegerWithPadding(10, 4, " ") would yield " 10".
	 */

	public static java.lang.String forIntegerWithPadding(int vv, int length, java.lang.String paddingString) {
		java.lang.String r = forInteger(vv);
		if(android.text.TextUtils.equals(r, null)) {
			return(null);
		}
		int ll = getLength(r);
		if(ll >= length) {
			return(r);
		}
		java.lang.String ps = paddingString;
		if(android.text.TextUtils.equals(ps, null)) {
			ps = "0";
		}
		StringBuilder sb = new StringBuilder();
		int n = 0;
		for(n = 0 ; n < (length - ll) ; n++) {
			sb.append(ps);
		}
		sb.append(r);
		return(sb.toString());
	}

	public static java.lang.String forIntegerWithPadding(int vv, int length) {
		return(cape.String.forIntegerWithPadding(vv, length, null));
	}

	/**
	 * Converts an integer value to a string using the "base-16" or hexadecimal
	 * notation.
	 */

	public static java.lang.String forIntegerHex(int vv) {
		return(java.lang.Integer.toHexString(vv));
	}

	/**
	 * Converts a character to a string. The result will be a single-character string.
	 */

	public static java.lang.String forCharacter(char vv) {
		return(java.lang.String.valueOf(vv));
	}

	/**
	 * Converts a floating point value to a string.
	 */

	public static java.lang.String forFloat(float vv) {
		return(java.lang.String.valueOf(vv));
	}

	/**
	 * Converts a double-precision floating point value to a string.
	 */

	public static java.lang.String forDouble(double vv) {
		return(java.lang.String.valueOf(vv));
	}

	/**
	 * Converts a string to a buffer of bytes, encoded using the UTF8 encoding.
	 */

	public static byte[] toUTF8Buffer(java.lang.String str) {
		return(toBuffer(str, "UTF8"));
	}

	/**
	 * Converts a string to a buffer of bytes, encoded using the specified "charset" as
	 * the character set or encoding. Vaid options for "charset" are platform
	 * dependent, and would includes values such as UTF8, UTF16, UCS2 and ASCII. UTF8
	 * encoding, however, is implemented for all platforms, and is therefore the
	 * recommended character set to use.
	 */

	public static byte[] toBuffer(java.lang.String str, java.lang.String charset) {
		if((android.text.TextUtils.equals(str, null)) || (android.text.TextUtils.equals(charset, null))) {
			return(null);
		}
		byte[] bytes = getBytesUnsigned(str, charset);
		if(bytes == null) {
			return(null);
		}
		int c = bytes.length;
		byte[] bb = new byte[c];
		int n = 0;
		for(n = 0 ; n < c ; n++) {
			cape.Buffer.setByte(bb, (long)n, bytes[n]);
		}
		return(bb);
	}

	static public byte[] getBytesUnsigned(java.lang.String str) {
		return(getBytesUnsigned(str, "UTF-8"));
	}

	static public byte[] getBytesUnsigned(java.lang.String str, java.lang.String charset) {
		if((android.text.TextUtils.equals(str, null)) || (android.text.TextUtils.equals(charset, null))) {
			return(null);
		}
		byte[] v = null;
		try {
			v = str.getBytes(charset);
		}
		catch(Exception e) {
			System.err.println(e.toString());
		}
		return(v);
	}

	static public byte[] getBytesSigned(java.lang.String str) {
		return(getBytesSigned(str, "UTF-8"));
	}

	static public byte[] getBytesSigned(java.lang.String str, java.lang.String charset) {
		if((android.text.TextUtils.equals(str, null)) || (android.text.TextUtils.equals(charset, null))) {
			return(null);
		}
		byte[] v = null;
		try {
			v = str.getBytes(charset);
		}
		catch(Exception e) {
			System.err.println(e.toString());
		}
		return(v);
	}

	/**
	 * Gets the length of a string, representing the number of characters composing the
	 * string (note: This is not the same as the number of bytes allocated to hold the
	 * memory for the string).
	 */

	public static int getLength(java.lang.String str) {
		if(android.text.TextUtils.equals(str, null)) {
			return(0);
		}
		return(str.length());
	}

	/**
	 * Appends a string "str2" to the end of another string "str1". Either one of the
	 * values may be null: If "str1" is null, the value of "str2" is returned, and if
	 * "str2" is null, the value of "str1" is returned.
	 */

	public static java.lang.String append(java.lang.String str1, java.lang.String str2) {
		if(android.text.TextUtils.equals(str1, null)) {
			return(str2);
		}
		if(android.text.TextUtils.equals(str2, null)) {
			return(str1);
		}
		return(str1.concat(str2 != null ? str2 : ""));
	}

	/**
	 * Appends an integer value "intvalue" to the end of string "str". If the original
	 * string is null, then a new string is returned, representing only the integer
	 * value.
	 */

	public static java.lang.String append(java.lang.String str, int intvalue) {
		return(cape.String.append(str, forInteger(intvalue)));
	}

	/**
	 * Appends a character value "charvalue" to the end of string "str". If the
	 * original string is null, then a new string is returned, representing only the
	 * character value.
	 */

	public static java.lang.String append(java.lang.String str, char charvalue) {
		return(cape.String.append(str, forCharacter(charvalue)));
	}

	/**
	 * Appends a floating point value "floatvalue" to the end of string "str". If the
	 * original string is null, then a new string is returned, representing only the
	 * floating point value.
	 */

	public static java.lang.String append(java.lang.String str, float floatvalue) {
		return(cape.String.append(str, forFloat(floatvalue)));
	}

	/**
	 * Appends a double-precision floating point value "doublevalue" to the end of
	 * string "str". If the original string is null, then a new string is returned,
	 * representing only the floating point value.
	 */

	public static java.lang.String append(java.lang.String str, double doublevalue) {
		return(cape.String.append(str, forDouble(doublevalue)));
	}

	/**
	 * Appends a boolean value "boolvalue" to the end of string "str". If the original
	 * string is null, then a new string is returned, representing only the boolean
	 * value.
	 */

	public static java.lang.String append(java.lang.String str, boolean boolvalue) {
		return(cape.String.append(str, forBoolean(boolvalue)));
	}

	/**
	 * Converts all characters of a string to lowercase.
	 */

	public static java.lang.String toLowerCase(java.lang.String str) {
		if(android.text.TextUtils.equals(str, null)) {
			return(null);
		}
		return(str.toLowerCase());
	}

	/**
	 * Converts all characters of a string to uppercase.
	 */

	public static java.lang.String toUpperCase(java.lang.String str) {
		if(android.text.TextUtils.equals(str, null)) {
			return(null);
		}
		return(str.toUpperCase());
	}

	/**
	 * Gets a character with the specified index "index" from the given string. This
	 * method is deprecated: Use getChar() instead.
	 */

	public static char charAt(java.lang.String str, int index) {
		return(getChar(str, index));
	}

	/**
	 * Gets a character with the specified index "index" from the given string.
	 */

	public static char getChar(java.lang.String str, int index) {
		if(android.text.TextUtils.equals(str, null)) {
			return((char)0);
		}
		return(str == null || index < 0 || index >= str.length() ? (char)0 : str.charAt(index));
	}

	/**
	 * Compares two strings, and returns true if both strings contain exactly the same
	 * contents (even though they may be represented by different objects). Either of
	 * the strings may be null, in which case the comparison always results to false.
	 */

	public static boolean equals(java.lang.String str1, java.lang.String str2) {
		if((android.text.TextUtils.equals(str1, null)) || (android.text.TextUtils.equals(str2, null))) {
			return(false);
		}
		return(str1.equals(str2));
	}

	/**
	 * Compares two strings for equality (like equals()) while considering uppercase
	 * and lowercase versions of the same character as equivalent. Eg. strings
	 * "ThisIsAString" and "thisisastring" would be considered equivalent, and the
	 * result of comparison would be "true".
	 */

	public static boolean equalsIgnoreCase(java.lang.String str1, java.lang.String str2) {
		if((android.text.TextUtils.equals(str1, null)) || (android.text.TextUtils.equals(str2, null))) {
			return(false);
		}
		return(str1.equalsIgnoreCase(str2));
	}

	/**
	 * Compares two strings, and returns an integer value representing their sorting
	 * order. The return value 0 indicates that the two strings are equivalent. A
	 * negative return value (< 0) indicates that "str1" is less than "str2", and a
	 * positive return value (> 0) indicates that "str1" is greater than "str2". If
	 * either "str1" or "str2" is null, the comparison yields the value 0.
	 */

	public static int compare(java.lang.String str1, java.lang.String str2) {
		if((android.text.TextUtils.equals(str1, null)) || (android.text.TextUtils.equals(str2, null))) {
			return(0);
		}
		return(str1.compareTo(str2));
	}

	/**
	 * Compares strings exactly like compareTo(), but this method considers uppercase
	 * and lowercase versions of each character as equivalent.
	 */

	public static int compareToIgnoreCase(java.lang.String str1, java.lang.String str2) {
		if((android.text.TextUtils.equals(str1, null)) || (android.text.TextUtils.equals(str2, null))) {
			return(0);
		}
		return(str1.compareToIgnoreCase(str2));
	}

	/**
	 * Gets a hash code version of the string as an integer.
	 */

	public static int getHashCode(java.lang.String str) {
		if(android.text.TextUtils.equals(str, null)) {
			return(0);
		}
		return(str.hashCode());
	}

	/**
	 * Gets the first index of a given character "c" in string "str", starting from
	 * index "start". This method is deprecated, use getIndexOf() instead.
	 */

	public static int indexOf(java.lang.String str, char c, int start) {
		return(getIndexOf(str, c, start));
	}

	public static int indexOf(java.lang.String str, char c) {
		return(cape.String.indexOf(str, c, 0));
	}

	/**
	 * Gets the first index of a given character "c" in string "str", starting from
	 * index "start".
	 */

	public static int getIndexOf(java.lang.String str, char c, int start) {
		if(android.text.TextUtils.equals(str, null)) {
			return(-1);
		}
		return(str.indexOf((int)c, start));
	}

	public static int getIndexOf(java.lang.String str, char c) {
		return(cape.String.getIndexOf(str, c, 0));
	}

	/**
	 * Gets the first index of a given substring "s" in string "str", starting from
	 * index "start". This method is deprecated, use getIndexOf() instead.
	 */

	public static int indexOf(java.lang.String str, java.lang.String s, int start) {
		return(getIndexOf(str, s, start));
	}

	public static int indexOf(java.lang.String str, java.lang.String s) {
		return(cape.String.indexOf(str, s, 0));
	}

	/**
	 * Gets the first index of a given substring "s" in string "str", starting from
	 * index "start".
	 */

	public static int getIndexOf(java.lang.String str, java.lang.String s, int start) {
		if(android.text.TextUtils.equals(str, null)) {
			return(-1);
		}
		return(str.indexOf(s, start));
	}

	public static int getIndexOf(java.lang.String str, java.lang.String s) {
		return(cape.String.getIndexOf(str, s, 0));
	}

	/**
	 * Gets the last index of a given character "c" in string "str", starting from
	 * index "start". This method is deprecated, use getLastIndexOf() instead.
	 */

	public static int lastIndexOf(java.lang.String str, char c, int start) {
		return(getLastIndexOf(str, c, start));
	}

	public static int lastIndexOf(java.lang.String str, char c) {
		return(cape.String.lastIndexOf(str, c, -1));
	}

	/**
	 * Gets the last index of a given character "c" in string "str", starting from
	 * index "start".
	 */

	public static int getLastIndexOf(java.lang.String str, char c, int start) {
		if(android.text.TextUtils.equals(str, null)) {
			return(-1);
		}
		if(start < 0) {
			return(str.lastIndexOf((int)c));
		}
		return(str.lastIndexOf((int)c, start));
	}

	public static int getLastIndexOf(java.lang.String str, char c) {
		return(cape.String.getLastIndexOf(str, c, -1));
	}

	/**
	 * Gets the last index of a given substring "s" in string "str", starting from
	 * index "start". This method is deprecated, use getLastIndexOf() instead.
	 */

	public static int lastIndexOf(java.lang.String str, java.lang.String s, int start) {
		return(getLastIndexOf(str, s, start));
	}

	public static int lastIndexOf(java.lang.String str, java.lang.String s) {
		return(cape.String.lastIndexOf(str, s, -1));
	}

	/**
	 * Gets the last index of a given substring "s" in string "str", starting from
	 * index "start".
	 */

	public static int getLastIndexOf(java.lang.String str, java.lang.String s, int start) {
		if(android.text.TextUtils.equals(str, null)) {
			return(-1);
		}
		if(start < 0) {
			return(str.lastIndexOf(s));
		}
		return(str.lastIndexOf(s, start));
	}

	public static int getLastIndexOf(java.lang.String str, java.lang.String s) {
		return(cape.String.getLastIndexOf(str, s, -1));
	}

	/**
	 * Gets a substring of "str", starting from index "start". This method is
	 * deprecated, use getSubString() instead.
	 */

	public static java.lang.String subString(java.lang.String str, int start) {
		return(getSubString(str, start));
	}

	/**
	 * Gets a substring of "str", starting from index "start".
	 */

	public static java.lang.String getSubString(java.lang.String str, int start) {
		if(android.text.TextUtils.equals(str, null)) {
			return(null);
		}
		if(start >= getLength(str)) {
			return("");
		}
		int ss = start;
		if(ss < 0) {
			ss = 0;
		}
		return(str.substring(ss));
	}

	/**
	 * Gets a substring of "str", starting from index "start" and with a maximum length
	 * of "length". This method is deprecated, use getSubString() instead.
	 */

	public static java.lang.String subString(java.lang.String str, int start, int length) {
		return(getSubString(str, start, length));
	}

	/**
	 * Gets a substring of "str", starting from index "start" and with a maximum length
	 * of "length".
	 */

	public static java.lang.String getSubString(java.lang.String str, int start, int length) {
		if(android.text.TextUtils.equals(str, null)) {
			return(null);
		}
		int strl = getLength(str);
		if(start >= strl) {
			return("");
		}
		int ss = start;
		if(ss < 0) {
			ss = 0;
		}
		int ll = length;
		if(ll > (strl - start)) {
			ll = strl - start;
		}
		return(str.substring(ss, ss+ll));
	}

	/**
	 * Checks is the string "str1" contains a substring "str2". Returns true if the
	 * substring is found.
	 */

	public static boolean contains(java.lang.String str1, java.lang.String str2) {
		if((android.text.TextUtils.equals(str1, null)) || (android.text.TextUtils.equals(str2, null))) {
			return(false);
		}
		return(str1.contains(str2));
	}

	/**
	 * Checks if the string "str1" starts with the complete contents of "str2". If the
	 * "offset" parameter is supplied an is greater than zero, the checking does not
	 * start from the beginning of "str1" but from the given character index.
	 */

	public static boolean startsWith(java.lang.String str1, java.lang.String str2, int offset) {
		if((android.text.TextUtils.equals(str1, null)) || (android.text.TextUtils.equals(str2, null))) {
			return(false);
		}
		java.lang.String nstr = null;
		if(offset > 0) {
			nstr = getSubString(str1, offset);
		}
		else {
			nstr = str1;
		}
		return(nstr.startsWith(str2));
	}

	public static boolean startsWith(java.lang.String str1, java.lang.String str2) {
		return(cape.String.startsWith(str1, str2, 0));
	}

	/**
	 * Checks if the string "str1" ends with the complete contents of "str2".
	 */

	public static boolean endsWith(java.lang.String str1, java.lang.String str2) {
		if((android.text.TextUtils.equals(str1, null)) || (android.text.TextUtils.equals(str2, null))) {
			return(false);
		}
		return(str1.endsWith(str2));
	}

	/**
	 * Strips (or trims) the given string "str" by removing all blank characters from
	 * both ends (the beginning and the end) of the string.
	 */

	public static java.lang.String strip(java.lang.String str) {
		if(android.text.TextUtils.equals(str, null)) {
			return(null);
		}
		return(str.trim());
	}

	/**
	 * Replaces all instances of "oldChar" with the value of "newChar" in the given
	 * string "str". The return value is a new string where the changes have been
	 * effected. The original string remains unchanged.
	 */

	public static java.lang.String replace(java.lang.String str, char oldChar, char newChar) {
		if(android.text.TextUtils.equals(str, null)) {
			return(null);
		}
		return(str.replace(oldChar, newChar));
	}

	/**
	 * Replaces all instances of the substring "target" with the value of "replacement"
	 * in the given string "str". The return value is a new string where the changes
	 * have been effected. The original string remains unchanged.
	 */

	public static java.lang.String replace(java.lang.String str, java.lang.String target, java.lang.String replacement) {
		if(android.text.TextUtils.equals(str, null)) {
			return(null);
		}
		return(str.replace(target, replacement));
	}

	/**
	 * Converts the string "str" to an array of characters.
	 */

	public static char[] toCharArray(java.lang.String str) {
		if(android.text.TextUtils.equals(str, null)) {
			return(null);
		}
		return(str.toCharArray());
	}

	/**
	 * Splits the string "str" to a vector of strings, cutting the original string at
	 * each instance of the character "delim". If the value of "max" is supplied and is
	 * given a value greater than 0, then the resulting vector will only have a maximum
	 * of "max" entries. If the delimiter character appears beyond the maximum entries,
	 * the last entry in the vector will be the complete remainder of the original
	 * string, including the remaining delimiter characters.
	 */

	public static java.util.ArrayList<java.lang.String> split(java.lang.String str, char delim, int max) {
		java.util.ArrayList<java.lang.String> v = new java.util.ArrayList<java.lang.String>();
		if(android.text.TextUtils.equals(str, null)) {
			return(v);
		}
		int n = 0;
		while(true) {
			if((max > 0) && (cape.Vector.getSize(v) >= (max - 1))) {
				cape.Vector.append(v, subString(str, n));
				break;
			}
			int x = indexOf(str, delim, n);
			if(x < 0) {
				cape.Vector.append(v, subString(str, n));
				break;
			}
			cape.Vector.append(v, subString(str, n, x - n));
			n = x + 1;
		}
		return(v);
	}

	public static java.util.ArrayList<java.lang.String> split(java.lang.String str, char delim) {
		return(cape.String.split(str, delim, 0));
	}

	/**
	 * Checks if the given string is fully an integer (no other characters appear in
	 * the string).
	 */

	public static boolean isInteger(java.lang.String str) {
		if(android.text.TextUtils.equals(str, null)) {
			return(false);
		}
		CharacterIterator it = iterate(str);
		if(it == null) {
			return(false);
		}
		while(true) {
			char c = it.getNextChar();
			if(c < 1) {
				break;
			}
			if((c < '0') || (c > '9')) {
				return(false);
			}
		}
		return(true);
	}

	/**
	 * Converts the string to an integer. If the string does not represent a valid
	 * integer, than the value "0" is returned. Eg. toInteger("99") = 99 Eg.
	 * toInteger("asdf") = 0
	 */

	public static int toInteger(java.lang.String str) {
		if(isEmpty(str)) {
			return(0);
		}
		int v = 0;
		int m = getLength(str);
		int n = 0;
		for(n = 0 ; n < m ; n++) {
			char c = charAt(str, n);
			if((c >= '0') && (c <= '9')) {
				v = v * 10;
				v += (int)(c - '0');
			}
			else {
				break;
			}
		}
		return(v);
	}

	/**
	 * Converts the string to a long integer. If the string does not represent a valid
	 * integer, than the value "0" is returned. Eg. toLong("99") = 99 Eg.
	 * toLong("asdf") = 0
	 */

	public static long toLong(java.lang.String str) {
		if(isEmpty(str)) {
			return((long)0);
		}
		long v = (long)0;
		int m = getLength(str);
		int n = 0;
		for(n = 0 ; n < m ; n++) {
			char c = charAt(str, n);
			if((c >= '0') && (c <= '9')) {
				v = v * 10;
				v += (long)(c - '0');
			}
			else {
				break;
			}
		}
		return(v);
	}

	/**
	 * Converts the string to an integer, while treating the string as a hexadecimal
	 * representation of a number, eg. toIntegerFromHex("a") = 10
	 */

	public static int toIntegerFromHex(java.lang.String str) {
		if(isEmpty(str)) {
			return(0);
		}
		int v = 0;
		int m = getLength(str);
		int n = 0;
		for(n = 0 ; n < m ; n++) {
			char c = charAt(str, n);
			if((c >= '0') && (c <= '9')) {
				v = v * 16;
				v += (int)(c - '0');
			}
			else if((c >= 'a') && (c <= 'f')) {
				v = v * 16;
				v += (int)((10 + c) - 'a');
			}
			else if((c >= 'A') && (c <= 'F')) {
				v = v * 16;
				v += (int)((10 + c) - 'A');
			}
			else {
				break;
			}
		}
		return(v);
	}

	/**
	 * Converts the string to a double-precision floating point number. If the string
	 * does not contain a valid representation of a floating point number, then the
	 * value "0.0" is returned.
	 */

	public static double toDouble(java.lang.String str) {
		if(android.text.TextUtils.equals(str, null)) {
			return(0.00);
		}
		double v = 0.00;
		try {
			if(str != null) {
				v = java.lang.Double.parseDouble(str);
			}
		}
		catch(Exception e) {
			v = 0.0;
		}
		return(v);
	}

	/**
	 * Iterates the string "string" character by character by using an instance of
	 * CharacterIterator.
	 */

	public static CharacterIterator iterate(java.lang.String string) {
		return((CharacterIterator)new CharacterIteratorForString(string));
	}

	/**
	 * Creates a new string that contains the same contents as "string", but with all
	 * characters appearing in reverse order.
	 */

	public static java.lang.String reverse(java.lang.String string) {
		if(android.text.TextUtils.equals(string, null)) {
			return(null);
		}
		StringBuilder sb = new StringBuilder();
		CharacterIterator it = iterate(string);
		char c = ' ';
		while((c = it.getNextChar()) > 0) {
			sb.insert(0, c);
		}
		return(sb.toString());
	}

	/**
	 * Iterates the string "string" in reverse order (starting from the end, moving
	 * towards the beginning).
	 */

	public static CharacterIterator iterateReverse(java.lang.String string) {
		return(iterate(reverse(string)));
	}

	/**
	 * Creates a new string based on "string" that is at least "length" characters
	 * long. If the original string is shorter, it will be padded to the desired length
	 * by adding instances of paddingCharacter (default of which is the space
	 * character). The "aling" attribute may be either -1, 0 or 1, and determines if
	 * the padding characters willb e added to the end, both sides or to the beginning,
	 * respectively.
	 */

	public static java.lang.String padToLength(java.lang.String string, int length, int align, char paddingCharacter) {
		int ll = 0;
		if(android.text.TextUtils.equals(string, null)) {
			ll = 0;
		}
		else {
			ll = getLength(string);
		}
		if(ll >= length) {
			return(string);
		}
		int add = length - ll;
		int n = 0;
		StringBuilder sb = new StringBuilder();
		if(align < 0) {
			sb.append(string);
			for(n = 0 ; n < add ; n++) {
				sb.append(paddingCharacter);
			}
		}
		else if(align > 0) {
			int ff = add / 2;
			int ss = add - ff;
			for(n = 0 ; n < ff ; n++) {
				sb.append(paddingCharacter);
			}
			sb.append(string);
			for(n = 0 ; n < ss ; n++) {
				sb.append(paddingCharacter);
			}
		}
		else {
			for(n = 0 ; n < add ; n++) {
				sb.append(paddingCharacter);
			}
			sb.append(string);
		}
		return(sb.toString());
	}

	public static java.lang.String padToLength(java.lang.String string, int length, int align) {
		return(cape.String.padToLength(string, length, align, ' '));
	}

	public static java.lang.String padToLength(java.lang.String string, int length) {
		return(cape.String.padToLength(string, length, -1));
	}

	/**
	 * Splits the given string "str" to a vector of multiple strings, cutting the
	 * string at each instance of the "delim" character where the delim does not appear
	 * enclosed in either double quotes or single quotes. Eg.
	 * quotedStringToVector("first 'second third'", ' ') => [ "first", "second third" ]
	 */

	public static java.util.ArrayList<java.lang.String> quotedStringToVector(java.lang.String str, char delim) {
		java.util.ArrayList<java.lang.String> v = new java.util.ArrayList<java.lang.String>();
		if(android.text.TextUtils.equals(str, null)) {
			return(v);
		}
		boolean dquote = false;
		boolean quote = false;
		StringBuilder sb = null;
		char c = ' ';
		CharacterIterator it = iterate(str);
		while((c = it.getNextChar()) > 0) {
			if((c == '\"') && (quote == false)) {
				dquote = !dquote;
			}
			else if((c == '\'') && (dquote == false)) {
				quote = !quote;
			}
			else if(((quote == false) && (dquote == false)) && (c == delim)) {
				if(sb != null) {
					java.lang.String r = sb.toString();
					if(android.text.TextUtils.equals(r, null)) {
						r = "";
					}
					v.add(r);
				}
				sb = null;
			}
			else {
				if(sb == null) {
					sb = new StringBuilder();
				}
				sb.append(c);
			}
			if(((quote == true) || (dquote == true)) && (sb == null)) {
				sb = new StringBuilder();
			}
		}
		if(sb != null) {
			java.lang.String r = sb.toString();
			if(android.text.TextUtils.equals(r, null)) {
				r = "";
			}
			v.add(r);
		}
		return(v);
	}

	/**
	 * Parses the string "str", splitting it to multiple strings using
	 * quotedStringToVector(), and then further processing it to key/value pairs,
	 * splitting each string at the equal sign '=' and adding the entries to a map.
	 */

	public static java.util.HashMap<java.lang.String,java.lang.String> quotedStringToMap(java.lang.String str, char delim) {
		java.util.HashMap<java.lang.String,java.lang.String> v = new java.util.HashMap<java.lang.String,java.lang.String>();
		java.util.ArrayList<java.lang.String> vector = quotedStringToVector(str, delim);
		if(vector != null) {
			int n = 0;
			int m = vector.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.String c = vector.get(n);
				if(c != null) {
					java.util.ArrayList<java.lang.String> sp = split(c, '=', 2);
					java.lang.String key = sp.get(0);
					java.lang.String val = sp.get(1);
					if(isEmpty(key) == false) {
						v.put(key, val);
					}
				}
			}
		}
		return(v);
	}

	/**
	 * Combines a vector of strings to a single string, incorporating the "delim"
	 * character between each string in the vector. If the "unique" variable is set to
	 * "true", only one instance of each unique string will be appended to the
	 * resulting string.
	 */

	public static java.lang.String combine(java.util.ArrayList<java.lang.String> strings, char delim, boolean unique) {
		StringBuilder sb = new StringBuilder();
		java.util.HashMap<java.lang.String,java.lang.String> flags = null;
		if(unique) {
			flags = new java.util.HashMap<java.lang.String,java.lang.String>();
		}
		if(strings != null) {
			int n = 0;
			int m = strings.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.String o = strings.get(n);
				if(o != null) {
					if(android.text.TextUtils.equals(o, null)) {
						continue;
					}
					if(flags != null) {
						if(!(android.text.TextUtils.equals(cape.Map.get(flags, o), null))) {
							continue;
						}
						cape.Map.set(flags, o, "true");
					}
					if((delim > 0) && (sb.count() > 0)) {
						sb.append(delim);
					}
					sb.append(o);
				}
			}
		}
		return(sb.toString());
	}

	public static java.lang.String combine(java.util.ArrayList<java.lang.String> strings, char delim) {
		return(cape.String.combine(strings, delim, false));
	}

	/**
	 * Vaidates a string character-by-character, calling the supplied function to
	 * determine the validity of each character. Returns true if the entire string was
	 * validated, false if not.
	 */

	public static boolean validateCharacters(java.lang.String str, samx.function.Function1<java.lang.Boolean,java.lang.Character> validator) {
		if(android.text.TextUtils.equals(str, null)) {
			return(false);
		}
		CharacterIterator it = iterate(str);
		if(it == null) {
			return(false);
		}
		while(true) {
			char c = it.getNextChar();
			if(c < 1) {
				break;
			}
			if(validator.execute(c) == false) {
				return(false);
			}
		}
		return(true);
	}
}
