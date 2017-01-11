
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

package cave;

public class ImageSheet
{
	private Image sheet = null;
	private int cols = -1;
	private int rows = -1;
	private int sourceSkipX = 0;
	private int sourceSkipY = 0;
	private int sourceImageWidth = -1;
	private int sourceImageHeight = -1;
	private int maxImages = -1;

	public java.util.ArrayList<Image> toImages(int resizeToWidth, int resizeToHeight) {
		if(sheet == null) {
			return(null);
		}
		int cols = this.cols;
		int rows = this.rows;
		int fwidth = sourceImageWidth;
		if(fwidth < 1) {
			fwidth = (sheet.getPixelWidth() - sourceSkipX) / cols;
		}
		else {
			cols = (sheet.getPixelWidth() - sourceSkipX) / fwidth;
		}
		int fheight = sourceImageHeight;
		if(fheight < 1) {
			fheight = (sheet.getPixelHeight() - sourceSkipY) / rows;
		}
		else {
			rows = (sheet.getPixelHeight() - sourceSkipY) / fheight;
		}
		java.util.ArrayList<Image> frames = new java.util.ArrayList<Image>();
		int x = 0;
		int y = 0;
		for(y = 0 ; y < rows ; y++) {
			for(x = 0 ; x < cols ; x++) {
				Image img = sheet.crop(x * fwidth, y * fheight, fwidth, fheight);
				if(resizeToWidth > 0) {
					img = img.scaleToSize(resizeToWidth, resizeToHeight);
				}
				frames.add(img);
				if((maxImages > 0) && (cape.Vector.getSize(frames) >= maxImages)) {
					return(frames);
				}
			}
		}
		return(frames);
	}

	public java.util.ArrayList<Image> toImages(int resizeToWidth) {
		return(toImages(resizeToWidth, -1));
	}

	public java.util.ArrayList<Image> toImages() {
		return(toImages(-1));
	}

	public Image getSheet() {
		return(sheet);
	}

	public ImageSheet setSheet(Image v) {
		sheet = v;
		return(this);
	}

	public int getCols() {
		return(cols);
	}

	public ImageSheet setCols(int v) {
		cols = v;
		return(this);
	}

	public int getRows() {
		return(rows);
	}

	public ImageSheet setRows(int v) {
		rows = v;
		return(this);
	}

	public int getSourceSkipX() {
		return(sourceSkipX);
	}

	public ImageSheet setSourceSkipX(int v) {
		sourceSkipX = v;
		return(this);
	}

	public int getSourceSkipY() {
		return(sourceSkipY);
	}

	public ImageSheet setSourceSkipY(int v) {
		sourceSkipY = v;
		return(this);
	}

	public int getSourceImageWidth() {
		return(sourceImageWidth);
	}

	public ImageSheet setSourceImageWidth(int v) {
		sourceImageWidth = v;
		return(this);
	}

	public int getSourceImageHeight() {
		return(sourceImageHeight);
	}

	public ImageSheet setSourceImageHeight(int v) {
		sourceImageHeight = v;
		return(this);
	}

	public int getMaxImages() {
		return(maxImages);
	}

	public ImageSheet setMaxImages(int v) {
		maxImages = v;
		return(this);
	}
}
