
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

public class FontDescription
{
	public static FontDescription forDefault() {
		return(new FontDescription());
	}

	public FontDescription forFile(cape.File file, Length size) {
		FontDescription v = new FontDescription();
		v.setFile(file);
		if(size != null) {
			v.setSize(size);
		}
		return(v);
	}

	public FontDescription forFile(cape.File file) {
		return(forFile(file, null));
	}

	public FontDescription forName(java.lang.String name, Length size) {
		FontDescription v = new FontDescription();
		v.setName(name);
		if(size != null) {
			v.setSize(size);
		}
		return(v);
	}

	public FontDescription forName(java.lang.String name) {
		return(forName(name, null));
	}

	private cape.File file = null;
	private java.lang.String name = null;
	private boolean bold = false;
	private boolean italic = false;
	private boolean underline = false;
	private Length size = null;

	public FontDescription() {
		file = null;
		name = "Sans";
		size = cave.Length.forMicroMeters((double)2500);
		bold = false;
		italic = false;
		underline = false;
	}

	public FontDescription dup() {
		FontDescription v = new FontDescription();
		v.file = file;
		v.name = name;
		v.bold = bold;
		v.italic = italic;
		v.underline = underline;
		v.size = size;
		return(v);
	}

	public cape.File getFile() {
		return(file);
	}

	public FontDescription setFile(cape.File v) {
		file = v;
		return(this);
	}

	public java.lang.String getName() {
		return(name);
	}

	public FontDescription setName(java.lang.String v) {
		name = v;
		return(this);
	}

	public boolean getBold() {
		return(bold);
	}

	public FontDescription setBold(boolean v) {
		bold = v;
		return(this);
	}

	public boolean getItalic() {
		return(italic);
	}

	public FontDescription setItalic(boolean v) {
		italic = v;
		return(this);
	}

	public boolean getUnderline() {
		return(underline);
	}

	public FontDescription setUnderline(boolean v) {
		underline = v;
		return(this);
	}

	public Length getSize() {
		return(size);
	}

	public FontDescription setSize(Length v) {
		size = v;
		return(this);
	}
}
