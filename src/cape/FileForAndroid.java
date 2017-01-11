
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

class FileForAndroid extends FileAdapter
{
	private static class MyFileReader implements FileReader, SizedReader, Reader, Closable, SeekableReader
	{
		private java.io.FileInputStream stream = null;
		private java.io.File file = null;

		public MyFileReader initialize() {
			if(file != null) {
				try {
					stream = new java.io.FileInputStream(file);
				}
				catch(java.io.FileNotFoundException e) {
					System.out.printf("File.read: `%s' : `%s'\n", e.getMessage(), file.getPath());
					return(null);
				}
				return(this);
			}
			return(null);
		}

		public int read(byte[] buffer) {
			if(buffer == null) {
				return(0);
			}
			byte[] ptr = buffer;
			int sz = (int)cape.Buffer.getSize(buffer);
			int v = 0;
			try {
				v = stream.read(ptr, 0, sz);
			}
			catch(java.io.IOException e) {
				System.out.printf("Reader.read: `%s' : `%s'\n", e.getMessage(), file.getPath());
			}
			return(v);
		}

		public int getSize() {
			int v = 0;
			try {
				v = stream.available();
			}
			catch(java.io.IOException e) {
				System.out.printf("Reader.get_size: `%s' : `%s'\n", e.getMessage(), file.getPath());
			}
			return(v);
		}

		public void close() {
			try {
				stream.close();
			}
			catch(Exception e) {
			}
		}

		public boolean setCurrentPosition(long n) {
			return(false);
		}

		public long getCurrentPosition() {
			return((long)0);
		}
	}

	private static class MyFileWriter implements FileWriter, Writer, PrintWriter, Closable, SeekableWriter, FlushableWriter
	{
		private boolean append = false;
		private java.io.FileOutputStream stream = null;
		private java.io.File file = null;

		public MyFileWriter initialize() {
			if(file != null) {
				try {
					stream = new java.io.FileOutputStream(file);
				}
				catch(java.io.IOException e) {
					if(append) {
						System.out.printf("File." + (append ? "append " : "write ") + ": `%s' : `%s'\n", e.getMessage(), file.getPath());
					}
				}
				return(this);
			}
			return(null);
		}

		public int write(byte[] buffer, int size) {
			if(buffer == null) {
				return(0);
			}
			byte[] ptr = buffer;
			int sz = size;
			if(sz < 1) {
				sz = (int)cape.Buffer.getSize(buffer);
			}
			try {
				stream.write(buffer, 0, sz);
			}
			catch(java.io.IOException e) {
				System.out.printf("Writer.write: `%s' : `%s'\n", e.getMessage(), file.getPath());
			}
			return(sz);
		}

		public void close() {
			try {
				stream.close();
			}
			catch(Exception e) {
			}
		}

		public boolean print(java.lang.String str) {
			if(android.text.TextUtils.equals(str, null)) {
				return(false);
			}
			if(stream == null) {
				return(false);
			}
			byte[] buffer = cape.String.toUTF8Buffer(str);
			if(buffer == null) {
				return(false);
			}
			int sz = buffer.length;
			if(write(buffer, -1) != sz) {
				return(false);
			}
			return(true);
		}

		public boolean println(java.lang.String str) {
			return(print(str + "\n"));
		}

		public boolean setCurrentPosition(long n) {
			return(false);
		}

		public long getCurrentPosition() {
			return((long)0);
		}

		public void flush() {
			try {
				stream.flush();
			}
			catch(Exception e) {
			}
		}

		public boolean getAppend() {
			return(append);
		}

		public MyFileWriter setAppend(boolean v) {
			append = v;
			return(this);
		}
	}

	private java.lang.String path = null;

	public static File forPath(java.lang.String path) {
		if(cape.String.isEmpty(path)) {
			return(null);
		}
		FileForAndroid v = new FileForAndroid();
		v.path = path;
		return((File)v);
	}

	@Override
	public FileInfo stat() {
		FileInfo v = new FileInfo();
		java.io.File f = new java.io.File(path);
		if(f.exists() == false) {
			return(v);
		}
		v.setSize((int)f.length());
		v.setModifyTime((int)f.lastModified());
		if(f.isDirectory()) {
			v.setType(FileInfo.FILE_TYPE_DIR);
		}
		else if(f.isFile()) {
			v.setType(FileInfo.FILE_TYPE_FILE);
		}
		return(v);
	}

	@Override
	public java.lang.String getPath() {
		if(path != null) {
			return(path);
		}
		return(null);
	}

	@Override
	public boolean createDirectoryRecursive() {
		boolean v = false;
		java.io.File f = new java.io.File(path);
		v = f.mkdirs();
		return(v);
	}

	@Override
	public File entry(java.lang.String name) {
		if(cape.String.isEmpty(name)) {
			return((File)this);
		}
		FileForAndroid v = new FileForAndroid();
		v.path = path + "/" + name;
		return((File)v);
	}

	@Override
	public boolean move(File dest, boolean replace) {
		if(dest.exists()) {
			if(replace == false) {
				return(false);
			}
			dest.remove();
		}
		java.lang.String destpath = null;
		java.lang.String dp = dest.getPath();
		if(android.text.TextUtils.equals(dp, null)) {
			return(false);
		}
		destpath = dp;
		boolean v = false;
		java.io.File src = new java.io.File(path);
		java.io.File dst = new java.io.File(destpath);
		v = src.renameTo(dst);
		return(v);
	}

	@Override
	public boolean touch() {
		boolean v = false;
		System.out.println("Touches: " + path);
		java.io.File f = new java.io.File(path);
		try {
			v = f.createNewFile();
		}
		catch(java.io.IOException e) {
			System.out.printf("Touch: `%s' : `%s'\n", e.getMessage(), path);
		}
		return(v);
	}

	@Override
	public FileReader read() {
		MyFileReader v = new MyFileReader();
		v.file = new java.io.File(path);
		return((FileReader)v.initialize());
	}

	@Override
	public FileWriter write() {
		MyFileWriter v = new MyFileWriter();
		v.file = new java.io.File(path);
		return((FileWriter)v.initialize());
	}

	@Override
	public FileWriter append() {
		MyFileWriter v = new MyFileWriter().setAppend(true);
		v.file = new java.io.File(path);
		return((FileWriter)v.initialize());
	}

	@Override
	public boolean exists() {
		FileInfo fi = stat();
		return(fi.getType() != cape.FileInfo.FILE_TYPE_UNKNOWN);
	}

	@Override
	public boolean createDirectory() {
		boolean v = false;
		java.io.File f = new java.io.File(path);
		v = f.mkdir();
		return(v);
	}

	@Override
	public boolean isExecutable() {
		boolean v = false;
		java.io.File f = new java.io.File(path);
		v = f.canExecute();
		return(v);
	}

	@Override
	public boolean rename(java.lang.String newname, boolean replace) {
		System.out.println("--- stub --- cape.FileForAndroid :: rename");
		return(false);
	}

	@Override
	public boolean createFifo() {
		System.out.println("--- stub --- cape.FileForAndroid :: createFifo");
		return(false);
	}

	@Override
	public boolean removeDirectory() {
		System.out.println("--- stub --- cape.FileForAndroid :: removeDirectory");
		return(false);
	}

	@Override
	public boolean isSame(File file) {
		System.out.println("--- stub --- cape.FileForAndroid :: isSame");
		return(false);
	}

	@Override
	public boolean remove() {
		System.out.println("--- stub --- cape.FileForAndroid :: remove");
		return(false);
	}

	@Override
	public boolean removeRecursive() {
		System.out.println("--- stub --- cape.FileForAndroid :: removeRecursive");
		return(false);
	}

	@Override
	public int compareModificationTime(File bf) {
		System.out.println("--- stub --- cape.FileForAndroid :: compareModificationTime");
		return(0);
	}

	@Override
	public java.lang.String directoryName() {
		System.out.println("--- stub --- cape.FileForAndroid :: directoryName");
		return(null);
	}

	@Override
	public boolean isIdentical(File file) {
		System.out.println("--- stub --- cape.FileForAndroid :: isIdentical");
		return(false);
	}

	@Override
	public boolean writeFromReader(Reader reader, boolean append) {
		System.out.println("--- stub --- cape.FileForAndroid :: writeFromReader");
		return(false);
	}

	@Override
	public boolean makeExecutable() {
		System.out.println("--- stub --- cape.FileForAndroid :: makeExecutable");
		return(false);
	}

	@Override
	public boolean isNewerThan(File bf) {
		System.out.println("--- stub --- cape.FileForAndroid :: isNewerThan");
		return(false);
	}

	@Override
	public boolean isOlderThan(File bf) {
		System.out.println("--- stub --- cape.FileForAndroid :: isOlderThan");
		return(false);
	}

	@Override
	public Iterator<File> entries() {
		System.out.println("--- stub --- cape.FileForAndroid :: entries");
		return(null);
	}
}
