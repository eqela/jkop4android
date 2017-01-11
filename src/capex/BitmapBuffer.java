
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

public class BitmapBuffer
{
	private byte[] buffer = null;
	private int width = 0;
	private int height = 0;

	public static BitmapBuffer create(byte[] b, int w, int h) {
		if((((b == null) || (cape.Buffer.getSize(b) < 4)) || (w < 1)) || (h < 1)) {
			return(null);
		}
		return(new BitmapBuffer().setBuffer(b).setWidth(w).setHeight(h));
	}

	public byte[] getBuffer() {
		return(buffer);
	}

	public BitmapBuffer setBuffer(byte[] v) {
		buffer = v;
		return(this);
	}

	public int getWidth() {
		return(width);
	}

	public BitmapBuffer setWidth(int v) {
		width = v;
		return(this);
	}

	public int getHeight() {
		return(height);
	}

	public BitmapBuffer setHeight(int v) {
		height = v;
		return(this);
	}
}
