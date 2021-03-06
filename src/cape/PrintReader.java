
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

public class PrintReader implements Reader, LineReader, Closable
{
	private Reader reader = null;
	private CharacterIteratorForReader iterator = null;

	public PrintReader(Reader reader) {
		setReader(reader);
	}

	public void setReader(Reader reader) {
		this.reader = reader;
		if(reader == null) {
			this.iterator = null;
		}
		else {
			this.iterator = new CharacterIteratorForReader(reader);
		}
	}

	public java.lang.String readLine() {
		if(iterator == null) {
			return(null);
		}
		StringBuilder sb = new StringBuilder();
		while(true) {
			char c = iterator.getNextChar();
			if(c < 1) {
				if(sb.count() < 1) {
					return(null);
				}
				break;
			}
			if(c == '\r') {
				continue;
			}
			if(c == '\n') {
				break;
			}
			sb.append(c);
		}
		if(sb.count() < 1) {
			return("");
		}
		return(sb.toString());
	}

	public int read(byte[] buffer) {
		if(reader == null) {
			return(-1);
		}
		return(reader.read(buffer));
	}

	public void close() {
		Closable rc = (Closable)((reader instanceof Closable) ? reader : null);
		if(rc != null) {
			rc.close();
		}
	}
}
