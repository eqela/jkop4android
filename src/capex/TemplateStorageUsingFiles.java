
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

public class TemplateStorageUsingFiles implements TemplateStorage
{
	public static TemplateStorageUsingFiles forDirectory(cape.File dir) {
		TemplateStorageUsingFiles v = new TemplateStorageUsingFiles();
		v.setDirectory(dir);
		return(v);
	}

	public static TemplateStorageUsingFiles forHTMLTemplateDirectory(cape.File dir) {
		TemplateStorageUsingFiles v = new TemplateStorageUsingFiles();
		v.setDirectory(dir);
		v.setSuffix(".html.t");
		return(v);
	}

	private cape.File directory = null;
	private java.lang.String suffix = null;

	public TemplateStorageUsingFiles() {
		suffix = ".txt";
	}

	public void getTemplate(java.lang.String id, samx.function.Procedure1<java.lang.String> callback) {
		if(callback == null) {
			return;
		}
		if((((directory == null) || cape.String.isEmpty(id)) || (cape.String.indexOf(id, '/') >= 0)) || (cape.String.indexOf(id, '\\') >= 0)) {
			callback.execute(null);
			return;
		}
		cape.File ff = directory.entry(id + suffix);
		if(ff.isFile() == false) {
			callback.execute(null);
			return;
		}
		callback.execute(ff.getContentsString("UTF-8"));
	}

	public cape.File getDirectory() {
		return(directory);
	}

	public TemplateStorageUsingFiles setDirectory(cape.File v) {
		directory = v;
		return(this);
	}

	public java.lang.String getSuffix() {
		return(suffix);
	}

	public TemplateStorageUsingFiles setSuffix(java.lang.String v) {
		suffix = v;
		return(this);
	}
}
