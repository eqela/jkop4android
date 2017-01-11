
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

package cave;

public class KeyEvent
{
	public static final int ACTION_NONE = 0;
	public static final int ACTION_DOWN = 1;
	public static final int ACTION_UP = 2;
	public static final int KEY_NONE = 0;
	public static final int KEY_SPACE = 1;
	public static final int KEY_ENTER = 2;
	public static final int KEY_TAB = 3;
	public static final int KEY_ESCAPE = 4;
	public static final int KEY_BACKSPACE = 5;
	public static final int KEY_SHIFT = 6;
	public static final int KEY_CONTROL = 7;
	public static final int KEY_ALT = 8;
	public static final int KEY_CAPSLOCK = 9;
	public static final int KEY_NUMLOCK = 10;
	public static final int KEY_LEFT = 11;
	public static final int KEY_UP = 12;
	public static final int KEY_RIGHT = 13;
	public static final int KEY_DOWN = 14;
	public static final int KEY_INSERT = 15;
	public static final int KEY_DELETE = 16;
	public static final int KEY_HOME = 17;
	public static final int KEY_END = 18;
	public static final int KEY_PAGEUP = 19;
	public static final int KEY_PAGEDOWN = 20;
	public static final int KEY_F1 = 21;
	public static final int KEY_F2 = 22;
	public static final int KEY_F3 = 23;
	public static final int KEY_F4 = 24;
	public static final int KEY_F5 = 25;
	public static final int KEY_F6 = 26;
	public static final int KEY_F7 = 27;
	public static final int KEY_F8 = 28;
	public static final int KEY_F9 = 29;
	public static final int KEY_F10 = 30;
	public static final int KEY_F11 = 31;
	public static final int KEY_F12 = 32;
	public static final int KEY_SUPER = 33;
	public static final int KEY_BACK = 34;
	private int action = 0;
	private int keyCode = 0;
	private java.lang.String stringValue = null;
	private boolean shift = false;
	private boolean control = false;
	private boolean command = false;
	private boolean alt = false;
	public boolean isConsumed = false;

	public void consume() {
		isConsumed = true;
	}

	public boolean isKeyPress(int key) {
		if((action == ACTION_DOWN) && (keyCode == key)) {
			return(true);
		}
		return(false);
	}

	public boolean isKey(int key) {
		if(keyCode == key) {
			return(true);
		}
		return(false);
	}

	public boolean isString(java.lang.String value) {
		if(android.text.TextUtils.equals(value, stringValue)) {
			return(true);
		}
		return(false);
	}

	public boolean isCharacter(char value) {
		if(!(android.text.TextUtils.equals(stringValue, null)) && (cape.String.getChar(stringValue, 0) == value)) {
			return(true);
		}
		return(false);
	}

	public void clear() {
		action = 0;
		keyCode = 0;
		stringValue = null;
		isConsumed = false;
	}

	public int getAction() {
		return(action);
	}

	public KeyEvent setAction(int v) {
		action = v;
		return(this);
	}

	public int getKeyCode() {
		return(keyCode);
	}

	public KeyEvent setKeyCode(int v) {
		keyCode = v;
		return(this);
	}

	public java.lang.String getStringValue() {
		return(stringValue);
	}

	public KeyEvent setStringValue(java.lang.String v) {
		stringValue = v;
		return(this);
	}

	public boolean getShift() {
		return(shift);
	}

	public KeyEvent setShift(boolean v) {
		shift = v;
		return(this);
	}

	public boolean getControl() {
		return(control);
	}

	public KeyEvent setControl(boolean v) {
		control = v;
		return(this);
	}

	public boolean getCommand() {
		return(command);
	}

	public KeyEvent setCommand(boolean v) {
		command = v;
		return(this);
	}

	public boolean getAlt() {
		return(alt);
	}

	public KeyEvent setAlt(boolean v) {
		alt = v;
		return(this);
	}
}
