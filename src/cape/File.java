
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

public interface File
{
	public File entry(java.lang.String name);
	public File asExecutable();
	public File getParent();
	public File getSibling(java.lang.String name);
	public boolean hasExtension(java.lang.String ext);
	public java.lang.String extension();
	public Iterator<File> entries();
	public boolean move(File dest, boolean replace);
	public boolean rename(java.lang.String newname, boolean replace);
	public boolean touch();
	public FileReader read();
	public FileWriter write();
	public FileWriter append();
	public boolean setMode(int mode);
	public boolean setOwnerUser(int uid);
	public boolean setOwnerGroup(int gid);
	public boolean makeExecutable();
	public FileInfo stat();
	public int getSize();
	public boolean exists();
	public boolean isExecutable();
	public boolean isFile();
	public boolean isDirectory();
	public boolean isLink();
	public boolean createFifo();
	public boolean createDirectory();
	public boolean createDirectoryRecursive();
	public boolean removeDirectory();
	public java.lang.String getPath();
	public boolean isSame(File file);
	public boolean remove();
	public boolean removeRecursive();
	public boolean writeFromReader(Reader reader, boolean append);
	public boolean copyFileTo(File dest);
	public int compareModificationTime(File file);
	public boolean isNewerThan(File file);
	public boolean isOlderThan(File file);
	public java.lang.String directoryName();
	public java.lang.String baseName();
	public java.lang.String baseNameWithoutExtension();
	public boolean isIdentical(File file);
	public byte[] getContentsBuffer();
	public java.lang.String getContentsString(java.lang.String encoding);
	public boolean setContentsBuffer(byte[] buf);
	public boolean setContentsString(java.lang.String str, java.lang.String encoding);
	public boolean hasChangedSince(long originalTimeStamp);
	public long getLastModifiedTimeStamp();
	public Iterator<java.lang.String> readLines();
}
