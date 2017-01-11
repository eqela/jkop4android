
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

public class Character
{
	private static class MyCharacterObject implements CharacterObject
	{
		private char character = ' ';

		public char toCharacter() {
			return(character);
		}

		public char getCharacter() {
			return(character);
		}

		public MyCharacterObject setCharacter(char v) {
			character = v;
			return(this);
		}
	}

	public static CharacterObject asObject(char character) {
		MyCharacterObject v = new MyCharacterObject();
		v.setCharacter(character);
		return((CharacterObject)v);
	}

	public static char toUppercase(char c) {
		if((c >= 'a') && (c <= 'z')) {
			return((char)((c - 'a') + 'A'));
		}
		return(c);
	}

	public static char toLowercase(char c) {
		if((c >= 'A') && (c <= 'Z')) {
			return((char)((c - 'A') + 'a'));
		}
		return(c);
	}

	public static boolean isDigit(char c) {
		return((c >= '0') && (c <= '9'));
	}

	public static boolean isLowercaseAlpha(char c) {
		return((c >= 'a') && (c <= 'z'));
	}

	public static boolean isUppercaseAlpha(char c) {
		return((c >= 'A') && (c <= 'Z'));
	}

	public static boolean isHexDigit(char c) {
		return((((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F'))) || ((c >= '0') && (c <= '9')));
	}

	public static boolean isAlnum(char c) {
		return((((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'))) || ((c >= '0') && (c <= '9')));
	}

	public static boolean isAlpha(char c) {
		return(((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')));
	}

	public static boolean isAlphaNumeric(char c) {
		return((((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'))) || ((c >= '0') && (c <= '9')));
	}

	public static boolean isLowercaseAlphaNumeric(char c) {
		return(((c >= 'a') && (c <= 'z')) || ((c >= '0') && (c <= '9')));
	}

	public static boolean isUppercaseAlphaNumeric(char c) {
		return(((c >= 'A') && (c <= 'Z')) || ((c >= '0') && (c <= '9')));
	}
}
