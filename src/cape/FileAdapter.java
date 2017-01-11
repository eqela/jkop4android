
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

abstract public class FileAdapter implements File
{
	public abstract File entry(java.lang.String name);
	public abstract Iterator<File> entries();
	public abstract boolean move(File dest, boolean replace);
	public abstract boolean rename(java.lang.String newname, boolean replace);
	public abstract boolean touch();
	public abstract FileReader read();
	public abstract FileWriter write();
	public abstract FileWriter append();
	public abstract FileInfo stat();
	public abstract boolean exists();
	public abstract boolean isExecutable();
	public abstract boolean createFifo();
	public abstract boolean createDirectory();
	public abstract boolean createDirectoryRecursive();
	public abstract boolean removeDirectory();
	public abstract java.lang.String getPath();
	public abstract boolean remove();
	public abstract int compareModificationTime(File bf);
	public abstract java.lang.String directoryName();
	public abstract boolean isIdentical(File file);
	public abstract boolean makeExecutable();
	public abstract boolean isNewerThan(File bf);
	public abstract boolean isOlderThan(File bf);
	public abstract boolean writeFromReader(Reader reader, boolean append);

	private static class ReadLineIterator implements Iterator<java.lang.String>
	{
		private PrintReader reader = null;

		public java.lang.String next() {
			if(reader == null) {
				return(null);
			}
			java.lang.String v = reader.readLine();
			if(android.text.TextUtils.equals(v, null)) {
				reader.close();
				reader = null;
			}
			return(v);
		}

		public PrintReader getReader() {
			return(reader);
		}

		public ReadLineIterator setReader(PrintReader v) {
			reader = v;
			return(this);
		}
	}

	public Iterator<java.lang.String> readLines() {
		FileReader rd = read();
		if(rd == null) {
			return(null);
		}
		return((Iterator<java.lang.String>)new ReadLineIterator().setReader(new PrintReader((Reader)rd)));
	}

	public boolean hasChangedSince(long originalTimeStamp) {
		long nts = getLastModifiedTimeStamp();
		if(nts > originalTimeStamp) {
			return(true);
		}
		return(false);
	}

	public long getLastModifiedTimeStamp() {
		if(isFile() == false) {
			return((long)0);
		}
		FileInfo st = stat();
		if(st == null) {
			return((long)0);
		}
		return((long)st.getModifyTime());
	}

	public boolean isSame(File file) {
		if(file == null) {
			return(false);
		}
		java.lang.String path = getPath();
		if(!(android.text.TextUtils.equals(path, null)) && (android.text.TextUtils.equals(path, file.getPath()))) {
			return(true);
		}
		return(false);
	}

	public boolean removeRecursive() {
		FileInfo finfo = stat();
		if(finfo == null) {
			return(true);
		}
		if((finfo.isDirectory() == false) || (finfo.isLink() == true)) {
			return(remove());
		}
		Iterator<File> it = entries();
		while(it != null) {
			File f = it.next();
			if(f == null) {
				break;
			}
			if(f.removeRecursive() == false) {
				return(false);
			}
		}
		return(removeDirectory());
	}

	public boolean isFile() {
		FileInfo st = stat();
		if(st == null) {
			return(false);
		}
		return(st.isFile());
	}

	public boolean isDirectory() {
		FileInfo st = stat();
		if(st == null) {
			return(false);
		}
		return(st.isDirectory());
	}

	public boolean isLink() {
		FileInfo st = stat();
		if(st == null) {
			return(false);
		}
		return(st.isLink());
	}

	public int getSize() {
		FileInfo st = stat();
		if(st == null) {
			return(0);
		}
		return(st.getSize());
	}

	public boolean setMode(int mode) {
		return(false);
	}

	public boolean setOwnerUser(int uid) {
		return(false);
	}

	public boolean setOwnerGroup(int gid) {
		return(false);
	}

	public File asExecutable() {
		if(cape.OS.isWindows()) {
			if(((hasExtension("exe") == false) && (hasExtension("bat") == false)) && (hasExtension("com") == false)) {
				java.lang.String bn = baseName();
				File exe = getSibling(bn + ".exe");
				if(exe.isFile()) {
					return(exe);
				}
				File bat = getSibling(bn + ".bat");
				if(bat.isFile()) {
					return(bat);
				}
				File com = getSibling(bn + ".com");
				if(com.isFile()) {
					return(com);
				}
				return(exe);
			}
		}
		return((File)this);
	}

	public File getParent() {
		java.lang.String path = dirName();
		if(android.text.TextUtils.equals(path, null)) {
			return((File)new FileInvalid());
		}
		return(cape.FileInstance.forPath(path));
	}

	public File getSibling(java.lang.String name) {
		File pp = getParent();
		if(pp == null) {
			return(null);
		}
		return(pp.entry(name));
	}

	public boolean hasExtension(java.lang.String ext) {
		java.lang.String xx = extension();
		if(android.text.TextUtils.equals(xx, null)) {
			return(false);
		}
		return(cape.String.equalsIgnoreCase(xx, ext));
	}

	public java.lang.String extension() {
		java.lang.String bn = baseName();
		if(android.text.TextUtils.equals(bn, null)) {
			return(null);
		}
		int dot = cape.String.lastIndexOf(bn, '.');
		if(dot < 0) {
			return(null);
		}
		return(cape.String.subString(bn, dot + 1));
	}

	public java.lang.String baseNameWithoutExtension() {
		java.lang.String bn = baseName();
		if(android.text.TextUtils.equals(bn, null)) {
			return(null);
		}
		int dot = cape.String.lastIndexOf(bn, '.');
		if(dot < 0) {
			return(bn);
		}
		return(cape.String.subString(bn, 0, dot));
	}

	public java.lang.String dirName() {
		java.lang.String path = getPath();
		if(android.text.TextUtils.equals(path, null)) {
			return(null);
		}
		char delim = cape.Environment.getPathSeparator();
		int dp = cape.String.lastIndexOf(path, delim);
		if(dp < 0) {
			return(".");
		}
		return(cape.String.subString(path, 0, dp));
	}

	public java.lang.String baseName() {
		java.lang.String path = getPath();
		if(android.text.TextUtils.equals(path, null)) {
			return(null);
		}
		char delim = cape.Environment.getPathSeparator();
		int dp = cape.String.lastIndexOf(path, delim);
		if(dp < 0) {
			return(path);
		}
		return(cape.String.subString(path, dp + 1));
	}

	public boolean copyFileTo(File dest) {
		if(dest == null) {
			return(false);
		}
		if(this.isSame(dest)) {
			return(true);
		}
		byte[] buf = new byte[4096 * 4];
		if(buf == null) {
			return(false);
		}
		FileReader reader = this.read();
		if(reader == null) {
			return(false);
		}
		FileWriter writer = dest.write();
		if(writer == null) {
			if(reader instanceof Closable) {
				((Closable)reader).close();
			}
			return(false);
		}
		boolean v = true;
		int n = 0;
		while((n = reader.read(buf)) > 0) {
			int nr = writer.write(buf, n);
			if(nr != n) {
				v = false;
				break;
			}
		}
		if(v) {
			FileInfo fi = this.stat();
			if(fi != null) {
				int mode = fi.getMode();
				if(mode != 0) {
					dest.setMode(mode);
				}
			}
		}
		else {
			dest.remove();
		}
		if((reader != null) && (reader instanceof Closable)) {
			((Closable)reader).close();
		}
		if((writer != null) && (writer instanceof Closable)) {
			((Closable)writer).close();
		}
		return(v);
	}

	public boolean setContentsString(java.lang.String str, java.lang.String encoding) {
		if(cape.String.isEmpty(encoding)) {
			return(false);
		}
		return(setContentsBuffer(cape.String.toBuffer(str, encoding)));
	}

	public boolean setContentsBuffer(byte[] buffer) {
		if(buffer == null) {
			return(false);
		}
		FileWriter writer = write();
		if(writer == null) {
			return(false);
		}
		if(writer.write(buffer, buffer.length) < 0) {
			return(false);
		}
		writer.close();
		return(true);
	}

	public java.lang.String getContentsString(java.lang.String encoding) {
		if(cape.String.isEmpty(encoding)) {
			return(null);
		}
		return(cape.String.forBuffer(getContentsBuffer(), encoding));
	}

	public byte[] getContentsBuffer() {
		FileReader reader = read();
		if(reader == null) {
			return(null);
		}
		int sz = reader.getSize();
		byte[] b = new byte[sz];
		if(reader.read(b) < sz) {
			return(null);
		}
		reader.close();
		return(b);
	}
}
