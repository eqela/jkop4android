
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

package capex;

abstract public class ZipReader
{
	public static ZipReader forFile(cape.File file) {
		return(null);
	}

	public static boolean extractZipFileToDirectory(cape.File zipFile, cape.File destDir, samx.function.Procedure1<cape.File> listener) {
		if((zipFile == null) || (destDir == null)) {
			return(false);
		}
		ZipReader zf = forFile(zipFile);
		if(zf == null) {
			return(false);
		}
		if(destDir.isDirectory() == false) {
			destDir.createDirectoryRecursive();
		}
		if(destDir.isDirectory() == false) {
			return(false);
		}
		java.util.ArrayList<ZipReaderEntry> array = zf.getEntries();
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				ZipReaderEntry entry = array.get(n);
				if(entry != null) {
					java.lang.String ename = entry.getName();
					if(cape.String.isEmpty(ename)) {
						continue;
					}
					cape.File dd = destDir;
					ename = cape.String.replace(ename, '\\', '/');
					java.util.ArrayList<java.lang.String> array2 = cape.String.split(ename, '/');
					if(array2 != null) {
						int n2 = 0;
						int m2 = array2.size();
						for(n2 = 0 ; n2 < m2 ; n2++) {
							java.lang.String comp = array2.get(n2);
							if(comp != null) {
								if((android.text.TextUtils.equals(comp, ".")) || (android.text.TextUtils.equals(comp, ".."))) {
									continue;
								}
								dd = dd.entry(comp);
							}
						}
					}
					if(listener != null) {
						listener.execute(dd);
					}
					if(entry.writeToFile(dd) == false) {
						return(false);
					}
				}
			}
		}
		return(true);
	}

	public static boolean extractZipFileToDirectory(cape.File zipFile, cape.File destDir) {
		return(capex.ZipReader.extractZipFileToDirectory(zipFile, destDir, null));
	}

	public abstract java.util.ArrayList<ZipReaderEntry> getEntries();
}
