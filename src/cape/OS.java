
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

public class OS
{
	public static boolean isWindows() {
		return(isSystemType("windows"));
	}

	public static boolean isLinux() {
		return(isSystemType("linux"));
	}

	public static boolean isOSX() {
		return(isSystemType("osx"));
	}

	public static boolean isAndroid() {
		return(isSystemType("android"));
	}

	public static boolean isIOS() {
		return(isSystemType("ios"));
	}

	public static boolean isSystemType(java.lang.String id) {
		boolean v = false;
		java.lang.String os = java.lang.System.getProperty("os.name").toLowerCase();
		if(android.text.TextUtils.equals(id, "windows")) {
			if(os.indexOf("win") >= 0) {
				v = true;
			}
			return(v);
		}
		if(android.text.TextUtils.equals(id, "osx")) {
			if(os.indexOf("mac") >= 0) {
				v = true;
			}
			if(v) {
				return(v);
			}
			if(isSystemType("posix") == false) {
				return(false);
			}
			if(cape.FileInstance.forPath("/Applications").isDirectory()) {
				return(true);
			}
			return(false);
		}
		if(((android.text.TextUtils.equals(id, "posix")) || (android.text.TextUtils.equals(id, "linux"))) || (android.text.TextUtils.equals(id, "unix"))) {
			if(os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0) {
				v = true;
			}
			return(v);
		}
		return(false);
	}
}
