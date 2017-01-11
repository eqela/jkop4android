
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

public class FileFinder implements Iterator<File>
{
	public static FileFinder forRoot(File root) {
		return(new FileFinder().setRoot(root));
	}

	private static class Pattern
	{
		private java.lang.String pattern = null;
		private java.lang.String suffix = null;
		private java.lang.String prefix = null;

		public Pattern setPattern(java.lang.String pattern) {
			this.pattern = pattern;
			if(!(android.text.TextUtils.equals(pattern, null))) {
				if(cape.String.startsWith(pattern, "*")) {
					suffix = cape.String.getSubString(pattern, 1);
				}
				if(cape.String.endsWith(pattern, "*")) {
					prefix = cape.String.getSubString(pattern, 0, cape.String.getLength(pattern) - 1);
				}
			}
			return(this);
		}

		public boolean match(java.lang.String check) {
			if(android.text.TextUtils.equals(check, null)) {
				return(false);
			}
			if(android.text.TextUtils.equals(pattern, check)) {
				return(true);
			}
			if(!(android.text.TextUtils.equals(suffix, null)) && cape.String.endsWith(check, suffix)) {
				return(true);
			}
			if(!(android.text.TextUtils.equals(prefix, null)) && cape.String.startsWith(check, prefix)) {
				return(true);
			}
			return(false);
		}
	}

	private File root = null;
	private java.util.ArrayList<Pattern> patterns = null;
	private java.util.ArrayList<Pattern> excludePatterns = null;
	private Stack<Iterator<File>> stack = null;
	private boolean includeMatchingDirectories = false;
	private boolean includeDirectories = false;

	public FileFinder() {
		patterns = new java.util.ArrayList<Pattern>();
		excludePatterns = new java.util.ArrayList<Pattern>();
	}

	public FileFinder setRoot(File root) {
		this.root = root;
		stack = null;
		return(this);
	}

	public FileFinder addPattern(java.lang.String pattern) {
		patterns.add(new Pattern().setPattern(pattern));
		return(this);
	}

	public FileFinder addExcludePattern(java.lang.String pattern) {
		excludePatterns.add(new Pattern().setPattern(pattern));
		return(this);
	}

	public boolean matchPattern(File file) {
		if(file == null) {
			return(false);
		}
		if(cape.Vector.getSize(patterns) < 1) {
			return(true);
		}
		java.lang.String filename = file.baseName();
		if(patterns != null) {
			int n = 0;
			int m = patterns.size();
			for(n = 0 ; n < m ; n++) {
				Pattern pattern = patterns.get(n);
				if(pattern != null) {
					if(pattern.match(filename)) {
						return(true);
					}
				}
			}
		}
		return(false);
	}

	public boolean matchExcludePattern(File file) {
		if(file == null) {
			return(false);
		}
		if(cape.Vector.getSize(excludePatterns) < 1) {
			return(false);
		}
		java.lang.String filename = file.baseName();
		if(excludePatterns != null) {
			int n = 0;
			int m = excludePatterns.size();
			for(n = 0 ; n < m ; n++) {
				Pattern pattern = excludePatterns.get(n);
				if(pattern != null) {
					if(pattern.match(filename)) {
						return(true);
					}
				}
			}
		}
		return(false);
	}

	public File next() {
		while(true) {
			if(stack == null) {
				if(root == null) {
					break;
				}
				Iterator<File> es = root.entries();
				root = null;
				if(es == null) {
					break;
				}
				stack = new Stack<Iterator<File>>();
				stack.push((Iterator<File>)es);
			}
			Iterator<File> entries = stack.peek();
			if(entries == null) {
				stack = null;
				break;
			}
			File e = entries.next();
			if(e == null) {
				stack.pop();
			}
			else if(matchExcludePattern(e)) {
				;
			}
			else if(e.isFile()) {
				if(matchPattern(e)) {
					return(e);
				}
			}
			else if((includeMatchingDirectories && e.isDirectory()) && matchPattern(e)) {
				return(e);
			}
			else if(e.isDirectory() && (e.isLink() == false)) {
				stack.push((Iterator<File>)e.entries());
				if(includeDirectories) {
					return(e);
				}
			}
		}
		return(null);
	}

	public boolean getIncludeMatchingDirectories() {
		return(includeMatchingDirectories);
	}

	public FileFinder setIncludeMatchingDirectories(boolean v) {
		includeMatchingDirectories = v;
		return(this);
	}

	public boolean getIncludeDirectories() {
		return(includeDirectories);
	}

	public FileFinder setIncludeDirectories(boolean v) {
		includeDirectories = v;
		return(this);
	}
}
