
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

public class PrintWriterWrapper implements Writer, PrintWriter, Closable, FlushableWriter
{
	public static PrintWriter forWriter(Writer writer) {
		if(writer == null) {
			return(null);
		}
		if(writer instanceof PrintWriter) {
			return((PrintWriter)writer);
		}
		PrintWriterWrapper v = new PrintWriterWrapper();
		v.setWriter(writer);
		return((PrintWriter)v);
	}

	private Writer writer = null;

	public boolean print(java.lang.String str) {
		if(android.text.TextUtils.equals(str, null)) {
			return(false);
		}
		byte[] buffer = cape.String.toUTF8Buffer(str);
		if(buffer == null) {
			return(false);
		}
		int sz = (int)cape.Buffer.getSize(buffer);
		if(writer.write(buffer, -1) != sz) {
			return(false);
		}
		return(true);
	}

	public boolean println(java.lang.String str) {
		return(print(str + "\n"));
	}

	public int write(byte[] buf, int size) {
		if(writer == null) {
			return(-1);
		}
		return(writer.write(buf, size));
	}

	public int write(byte[] buf) {
		return(write(buf, -1));
	}

	public void close() {
		Closable cw = (Closable)((writer instanceof Closable) ? writer : null);
		if(cw != null) {
			cw.close();
		}
	}

	public void flush() {
		FlushableWriter cw = (FlushableWriter)((writer instanceof FlushableWriter) ? writer : null);
		if(cw != null) {
			cw.flush();
		}
	}

	public Writer getWriter() {
		return(writer);
	}

	public PrintWriterWrapper setWriter(Writer v) {
		writer = v;
		return(this);
	}
}
