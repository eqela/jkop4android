
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

public class FileInvalid extends FileAdapter
{
	@Override
	public File entry(java.lang.String name) {
		return((File)new FileInvalid());
	}

	@Override
	public boolean makeExecutable() {
		return(false);
	}

	@Override
	public boolean move(File dest, boolean replace) {
		return(false);
	}

	@Override
	public boolean rename(java.lang.String newname, boolean replace) {
		return(false);
	}

	@Override
	public boolean touch() {
		return(false);
	}

	@Override
	public FileReader read() {
		return(null);
	}

	@Override
	public FileWriter write() {
		return(null);
	}

	@Override
	public FileWriter append() {
		return(null);
	}

	@Override
	public FileInfo stat() {
		return(null);
	}

	@Override
	public boolean exists() {
		return(false);
	}

	@Override
	public boolean isExecutable() {
		return(false);
	}

	@Override
	public boolean createFifo() {
		return(false);
	}

	@Override
	public boolean createDirectory() {
		return(false);
	}

	@Override
	public boolean createDirectoryRecursive() {
		return(false);
	}

	@Override
	public boolean removeDirectory() {
		return(false);
	}

	@Override
	public java.lang.String getPath() {
		return(null);
	}

	@Override
	public boolean isSame(File file) {
		return(false);
	}

	@Override
	public boolean remove() {
		return(false);
	}

	@Override
	public boolean removeRecursive() {
		return(false);
	}

	@Override
	public int compareModificationTime(File file) {
		return(0);
	}

	@Override
	public java.lang.String directoryName() {
		return(null);
	}

	@Override
	public java.lang.String baseName() {
		return(null);
	}

	@Override
	public boolean isIdentical(File file) {
		return(false);
	}

	@Override
	public byte[] getContentsBuffer() {
		return(null);
	}

	@Override
	public java.lang.String getContentsString(java.lang.String encoding) {
		return(null);
	}

	@Override
	public boolean setContentsBuffer(byte[] buffer) {
		return(false);
	}

	@Override
	public boolean setContentsString(java.lang.String str, java.lang.String encoding) {
		return(false);
	}

	@Override
	public boolean isNewerThan(File bf) {
		return(false);
	}

	@Override
	public boolean isOlderThan(File bf) {
		return(false);
	}

	@Override
	public boolean writeFromReader(Reader reader, boolean append) {
		return(false);
	}

	@Override
	public Iterator<File> entries() {
		return(null);
	}
}
