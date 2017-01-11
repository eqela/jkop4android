
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

public class RGBAPixelIntegerBuffer
{
	private byte[] buffer = null;
	private byte[] pointer = null;
	private int width = 0;
	private int height = 0;
	private int[] cache = null;

	public static RGBAPixelIntegerBuffer create(byte[] b, int w, int h) {
		RGBAPixelIntegerBuffer v = new RGBAPixelIntegerBuffer();
		v.buffer = b;
		v.width = w;
		v.height = h;
		v.pointer = b;
		return(v);
	}

	public int getWidth() {
		return(width);
	}

	public int getHeight() {
		return(height);
	}

	public int[] getRgbaPixel(int x, int y, boolean newbuffer) {
		if((cache == null) && (newbuffer == false)) {
			cache = new int[4];
		}
		int[] v = cache;
		if(newbuffer) {
			v = new int[4];
		}
		int i = 0;
		if((((x < 0) || (x >= width)) || (y < 0)) || (y >= height)) {
			return(v);
		}
		for(i = 0 ; i < 4 ; i++) {
			v[i] = (int)cape.Buffer.getByte(pointer, (long)((((y * width) + x) * 4) + i));
		}
		return(v);
	}

	public int[] getRgbaPixel(int x, int y) {
		return(getRgbaPixel(x, y, false));
	}
}
