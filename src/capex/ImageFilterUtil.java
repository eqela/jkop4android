
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

public class ImageFilterUtil
{
	public static int clamp(double v) {
		if(v > 255) {
			return(255);
		}
		if(v < 0) {
			return(0);
		}
		return((int)v);
	}

	public static int getSafeByte(byte[] p, int sz, int idx) {
		int i = idx;
		if(i >= sz) {
			i = sz - 1;
		}
		else if(i < 0) {
			i = 0;
		}
		return((int)(cape.Buffer.getByte(p, (long)i) & 255));
	}

	public static BitmapBuffer createForArrayFilter(BitmapBuffer bmpbuf, double[] filterArray, int fw, int fh, double factor, double bias) {
		byte[] srcbuf = bmpbuf.getBuffer();
		int w = bmpbuf.getWidth();
		int h = bmpbuf.getHeight();
		if((w < 1) || (h < 1)) {
			return(null);
		}
		byte[] desbuf = new byte[(w * h) * 4];
		int x = 0;
		int y = 0;
		byte[] srcptr = srcbuf;
		byte[] desptr = desbuf;
		int sz = (int)cape.Buffer.getSize(srcbuf);
		for(x = 0 ; x < w ; x++) {
			for(y = 0 ; y < h ; y++) {
				double sr = 0.00;
				double sg = 0.00;
				double sb = 0.00;
				double sa = 0.00;
				int fx = 0;
				int fy = 0;
				for(fy = 0 ; fy < fh ; fy++) {
					for(fx = 0 ; fx < fw ; fx++) {
						int ix = (x - (fw / 2)) + fx;
						int iy = (y - (fh / 2)) + fy;
						sr += (double)(getSafeByte(srcptr, sz, (((iy * w) + ix) * 4) + 0) * filterArray[(fy * fw) + fx]);
						sg += (double)(getSafeByte(srcptr, sz, (((iy * w) + ix) * 4) + 1) * filterArray[(fy * fw) + fx]);
						sb += (double)(getSafeByte(srcptr, sz, (((iy * w) + ix) * 4) + 2) * filterArray[(fy * fw) + fx]);
						sa += (double)(getSafeByte(srcptr, sz, (((iy * w) + ix) * 4) + 3) * filterArray[(fy * fw) + fx]);
					}
				}
				cape.Buffer.setByte(desptr, (long)((((y * w) + x) * 4) + 0), (byte)clamp((factor * sr) + bias));
				cape.Buffer.setByte(desptr, (long)((((y * w) + x) * 4) + 1), (byte)clamp((factor * sg) + bias));
				cape.Buffer.setByte(desptr, (long)((((y * w) + x) * 4) + 2), (byte)clamp((factor * sb) + bias));
				cape.Buffer.setByte(desptr, (long)((((y * w) + x) * 4) + 3), (byte)clamp((factor * sa) + bias));
			}
		}
		return(capex.BitmapBuffer.create(desbuf, w, h));
	}

	public static BitmapBuffer createForArrayFilter(BitmapBuffer bmpbuf, double[] filterArray, int fw, int fh, double factor) {
		return(capex.ImageFilterUtil.createForArrayFilter(bmpbuf, filterArray, fw, fh, factor, 1.00));
	}

	public static BitmapBuffer createForArrayFilter(BitmapBuffer bmpbuf, double[] filterArray, int fw, int fh) {
		return(capex.ImageFilterUtil.createForArrayFilter(bmpbuf, filterArray, fw, fh, 1.00));
	}
}
