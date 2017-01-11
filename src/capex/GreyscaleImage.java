
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

public class GreyscaleImage
{
	public static BitmapBuffer createGreyscale(BitmapBuffer bmpbuf, double rf, double gf, double bf, double af) {
		int w = bmpbuf.getWidth();
		int h = bmpbuf.getHeight();
		byte[] srcbuf = bmpbuf.getBuffer();
		if(((srcbuf == null) || (w < 1)) || (h < 1)) {
			return(null);
		}
		byte[] desbuf = new byte[(w * h) * 4];
		if(desbuf == null) {
			return(null);
		}
		int ss = (int)cape.Buffer.getSize(srcbuf);
		byte[] srcptr = srcbuf;
		byte[] desptr = desbuf;
		int x = 0;
		int y = 0;
		for(y = 0 ; y < h ; y++) {
			for(x = 0 ; x < w ; x++) {
				double sr = (double)(capex.ImageFilterUtil.getSafeByte(srcptr, ss, (((y * w) + x) * 4) + 0) * 0.21);
				double sg = (double)(capex.ImageFilterUtil.getSafeByte(srcptr, ss, (((y * w) + x) * 4) + 1) * 0.72);
				double sb = (double)(capex.ImageFilterUtil.getSafeByte(srcptr, ss, (((y * w) + x) * 4) + 2) * 0.07);
				double sa = (double)capex.ImageFilterUtil.getSafeByte(srcptr, ss, (((y * w) + x) * 4) + 3);
				int sbnw = (int)((sr + sg) + sb);
				cape.Buffer.setByte(desptr, (long)((((y * w) + x) * 4) + 0), (byte)capex.ImageFilterUtil.clamp((double)(sbnw * rf)));
				cape.Buffer.setByte(desptr, (long)((((y * w) + x) * 4) + 1), (byte)capex.ImageFilterUtil.clamp((double)(sbnw * gf)));
				cape.Buffer.setByte(desptr, (long)((((y * w) + x) * 4) + 2), (byte)capex.ImageFilterUtil.clamp((double)(sbnw * bf)));
				cape.Buffer.setByte(desptr, (long)((((y * w) + x) * 4) + 3), (byte)capex.ImageFilterUtil.clamp(sa * af));
			}
		}
		return(capex.BitmapBuffer.create(desbuf, w, h));
	}

	public static BitmapBuffer createGreyscale(BitmapBuffer bmpbuf, double rf, double gf, double bf) {
		return(capex.GreyscaleImage.createGreyscale(bmpbuf, rf, gf, bf, 1.00));
	}

	public static BitmapBuffer createGreyscale(BitmapBuffer bmpbuf, double rf, double gf) {
		return(capex.GreyscaleImage.createGreyscale(bmpbuf, rf, gf, 1.00));
	}

	public static BitmapBuffer createGreyscale(BitmapBuffer bmpbuf, double rf) {
		return(capex.GreyscaleImage.createGreyscale(bmpbuf, rf, 1.00));
	}

	public static BitmapBuffer createGreyscale(BitmapBuffer bmpbuf) {
		return(capex.GreyscaleImage.createGreyscale(bmpbuf, 1.00));
	}

	public static BitmapBuffer createRedSepia(BitmapBuffer imgbuf) {
		return(createGreyscale(imgbuf, (110.00 / 255.00) + 1.00, (66.00 / 255.00) + 1.00, (20.00 / 255.00) + 1.00));
	}
}
