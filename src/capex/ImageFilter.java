
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

public class ImageFilter
{
	public static final int RESIZE_TYPE_BILINEAR = 0;
	public static final int RESIZE_TYPE_BICUBIC = 1;

	public static BitmapBuffer resizeImage(BitmapBuffer bmpbuf, int nw, int nh, int type) {
		if(bmpbuf == null) {
			return(null);
		}
		if(type == RESIZE_TYPE_BICUBIC) {
			return(capex.ImageResizer.resizeBicubic(bmpbuf, nw, nh));
		}
		return(capex.ImageResizer.resizeBilinear(bmpbuf, nw, nh));
	}

	public static BitmapBuffer resizeImage(BitmapBuffer bmpbuf, int nw, int nh) {
		return(capex.ImageFilter.resizeImage(bmpbuf, nw, nh, 0));
	}

	public static BitmapBuffer resizeImage(BitmapBuffer bmpbuf, int nw) {
		return(capex.ImageFilter.resizeImage(bmpbuf, nw, -1));
	}

	public static BitmapBuffer resizeImage(BitmapBuffer bmpbuf) {
		return(capex.ImageFilter.resizeImage(bmpbuf, -1));
	}

	public static BitmapBuffer filterGreyscale(BitmapBuffer bmpbuf) {
		if(bmpbuf == null) {
			return(null);
		}
		return(capex.GreyscaleImage.createGreyscale(bmpbuf));
	}

	public static BitmapBuffer filterRedSepia(BitmapBuffer bmpbuf) {
		if(bmpbuf == null) {
			return(null);
		}
		return(capex.GreyscaleImage.createRedSepia(bmpbuf));
	}

	public static BitmapBuffer filterBlur(BitmapBuffer bmpbuf) {
		if(bmpbuf == null) {
			return(null);
		}
		double[] array = new double[] {
			0.00,
			0.00,
			1.00,
			0.00,
			0.00,
			0.00,
			1.00,
			1.00,
			1.00,
			0.00,
			1.00,
			1.00,
			1.00,
			1.00,
			1.00,
			0.00,
			1.00,
			1.00,
			1.00,
			0.00,
			0.00,
			0.00,
			1.00,
			0.00,
			0.00
		};
		return(capex.ImageFilterUtil.createForArrayFilter(bmpbuf, array, 5, 5, 1.00 / 13.00));
	}

	public static BitmapBuffer filterSharpen(BitmapBuffer bmpbuf) {
		if(bmpbuf == null) {
			return(null);
		}
		double[] array = new double[] {
			-1.00,
			-1.00,
			-1.00,
			-1.00,
			9.00,
			-1.00,
			-1.00,
			-1.00,
			-1.00
		};
		return(capex.ImageFilterUtil.createForArrayFilter(bmpbuf, array, 3, 3));
	}

	public static BitmapBuffer filterEmboss(BitmapBuffer bmpbuf) {
		if(bmpbuf == null) {
			return(null);
		}
		double[] array = new double[] {
			-2.00,
			-1.00,
			0.00,
			-1.00,
			1.00,
			1.00,
			0.00,
			1.00,
			2.00
		};
		return(capex.ImageFilterUtil.createForArrayFilter(bmpbuf, array, 3, 3, 2.00, 0.00));
	}

	public static BitmapBuffer filterMotionBlur(BitmapBuffer bmpbuf) {
		if(bmpbuf == null) {
			return(null);
		}
		double[] array = new double[] {
			1.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			1.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			1.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			1.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			1.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			1.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			1.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			1.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			1.00
		};
		return(capex.ImageFilterUtil.createForArrayFilter(bmpbuf, array, 9, 9, 1.00 / 9.00));
	}
}
