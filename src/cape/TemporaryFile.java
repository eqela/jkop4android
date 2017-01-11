
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

public class TemporaryFile
{
	public static File create(java.lang.String extension) {
		return(forDirectory(null, extension));
	}

	public static File create() {
		return(cape.TemporaryFile.create(null));
	}

	public static File forDirectory(File dir, java.lang.String extension) {
		File tmpdir = dir;
		if(tmpdir == null) {
			tmpdir = cape.Environment.getTemporaryDirectory();
		}
		if((tmpdir == null) || (tmpdir.isDirectory() == false)) {
			return(null);
		}
		File v = null;
		int n = 0;
		Random rnd = new Random();
		while(n < 100) {
			java.lang.String id = ("_tmp_" + cape.String.forInteger((int)cape.SystemClock.asSeconds())) + cape.String.forInteger((int)(rnd.nextInt() % 1000000));
			if((android.text.TextUtils.equals(extension, null)) || (cape.String.getLength(extension) < 1)) {
				id = id + extension;
			}
			v = tmpdir.entry(id);
			if(v.exists() == false) {
				v.touch();
				break;
			}
			n++;
		}
		if((v != null) && (v.isFile() == false)) {
			v = null;
		}
		return(v);
	}

	public static File forDirectory(File dir) {
		return(cape.TemporaryFile.forDirectory(dir, null));
	}
}
