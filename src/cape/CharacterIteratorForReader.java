
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

public class CharacterIteratorForReader extends CharacterDecoder
{
	private Reader reader = null;
	private byte[] buffer = null;
	private long bufferStart = (long)-1;
	private long bufferSize = (long)0;
	private long bufferDataSize = (long)0;
	private long currentPos = (long)-1;
	private long readPos = (long)0;

	public CharacterIteratorForReader(Reader reader) {
		this.reader = reader;
		this.buffer = new byte[1024];
		this.bufferSize = (long)1024;
	}

	public CharacterIteratorForReader(Reader reader, long bufferSize) {
		this.reader = reader;
		this.buffer = new byte[(int)bufferSize];
		this.bufferSize = bufferSize;
	}

	private boolean makeDataAvailable(long n) {
		if((n >= bufferStart) && (n < (bufferStart + bufferDataSize))) {
			return(true);
		}
		if(reader instanceof SeekableReader) {
			long block = n / bufferSize;
			long blockPos = block * bufferSize;
			if(readPos != blockPos) {
				if(((SeekableReader)reader).setCurrentPosition((long)blockPos) == false) {
					return(false);
				}
				readPos = blockPos;
			}
		}
		bufferDataSize = (long)reader.read(buffer);
		bufferStart = readPos;
		readPos += bufferDataSize;
		if((n >= bufferStart) && (n < (bufferStart + bufferDataSize))) {
			return(true);
		}
		return(false);
	}

	@Override
	public boolean moveToPreviousByte() {
		if(makeDataAvailable(currentPos - 1) == false) {
			return(false);
		}
		currentPos--;
		return(true);
	}

	@Override
	public boolean moveToNextByte() {
		if(makeDataAvailable(currentPos + 1) == false) {
			return(false);
		}
		currentPos++;
		return(true);
	}

	@Override
	public int getCurrentByte() {
		return((int)cape.Buffer.getByte(buffer, currentPos - bufferStart));
	}
}
