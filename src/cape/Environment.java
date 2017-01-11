
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

public class Environment
{
	public static char getPathSeparator() {
		return(java.io.File.separatorChar);
	}

	public static boolean isAbsolutePath(java.lang.String path) {
		if(android.text.TextUtils.equals(path, null)) {
			return(false);
		}
		char sep = getPathSeparator();
		char c0 = cape.String.getChar(path, 0);
		if(c0 == sep) {
			return(true);
		}
		if(((cape.Character.isAlpha(c0) && cape.OS.isWindows()) && (cape.String.getChar(path, 1) == ':')) && (cape.String.getChar(path, 2) == '\\')) {
			return(true);
		}
		return(false);
	}

	public static java.util.HashMap<java.lang.String,java.lang.String> getVariables() {
		System.out.println("[cape.Environment.getVariables] (Environment.sling:60:1): Not implemented");
		return(null);
	}

	public static java.lang.String getVariable(java.lang.String key) {
		if(android.text.TextUtils.equals(key, null)) {
			return(null);
		}
		java.lang.String v = null;
		v = java.lang.System.getenv(key);
		return(v);
	}

	public static void setVariable(java.lang.String key, java.lang.String val) {
		System.out.println("[cape.Environment.setVariable] (Environment.sling:94:1): Not implemented");
	}

	public static void unsetVariable(java.lang.String key) {
		System.out.println("[cape.Environment.unsetVariable] (Environment.sling:99:1): Not implemented");
	}

	public static void setCurrentDirectory(File dir) {
		System.out.println("[cape.Environment.setCurrentDirectory] (Environment.sling:104:1): Not implemented");
	}

	public static File getCurrentDirectory() {
		System.out.println("[cape.Environment.getCurrentDirectory] (Environment.sling:113:2): Not implemented");
		return((File)new FileInvalid());
	}

	static public File findInPath(java.lang.String command) {
		java.lang.String path = getVariable("PATH");
		if(cape.String.isEmpty(path)) {
			return(null);
		}
		char separator = ':';
		if(cape.OS.isWindows()) {
			separator = ';';
		}
		java.util.ArrayList<java.lang.String> array = cape.String.split(path, separator);
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.String dir = array.get(n);
				if(dir != null) {
					File pp = cape.FileInstance.forPath(dir).entry(command).asExecutable();
					if(pp.isFile()) {
						return(pp);
					}
				}
			}
		}
		return(null);
	}

	public static File findCommand(java.lang.String command) {
		if(android.text.TextUtils.equals(command, null)) {
			return(null);
		}
		return(findInPath(command));
	}

	public static File getTemporaryDirectory() {
		System.out.println("[cape.Environment.getTemporaryDirectory] (Environment.sling:154:2): Not implemented");
		return((File)new FileInvalid());
	}

	public static File getHomeDirectory() {
		System.out.println("[cape.Environment.getHomeDirectory] (Environment.sling:201:2): Not implemented");
		return((File)new FileInvalid());
	}

	public static File getAppDirectory() {
		System.out.println("[cape.Environment.getAppDirectory] (Environment.sling:222:2): Not implemented");
		return((File)new FileInvalid());
	}
}
