
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

public class CharacterIteratorForBuffer extends CharacterDecoder implements Duplicateable
{
	private byte[] buffer = null;
	private int currentPosition = -1;

	private CharacterIteratorForBuffer() {
	}

	public CharacterIteratorForBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	@Override
	public boolean moveToPreviousByte() {
		if(currentPosition < 1) {
			return(false);
		}
		currentPosition--;
		return(true);
	}

	@Override
	public boolean moveToNextByte() {
		if((currentPosition + 1) >= cape.Buffer.getSize(buffer)) {
			return(false);
		}
		currentPosition++;
		return(true);
	}

	@Override
	public int getCurrentByte() {
		return((int)cape.Buffer.getByte(buffer, (long)currentPosition));
	}

	public java.lang.Object duplicate() {
		CharacterIteratorForBuffer v = new CharacterIteratorForBuffer();
		super.copyTo((CharacterDecoder)v);
		v.buffer = buffer;
		v.currentPosition = currentPosition;
		return((java.lang.Object)v);
	}
}
