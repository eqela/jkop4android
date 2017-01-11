
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

public class PaperConfiguration
{
	public static class Size
	{
		public double width = 0.00;
		public double height = 0.00;

		public Size() {
			width = 0.00;
			height = 0.00;
		}

		public Size(double w, double h) {
			width = w;
			height = h;
		}

		public double getHeight() {
			return(height);
		}

		public double getWidth() {
			return(width);
		}
	}

	public static PaperConfiguration forDefault() {
		return(forA4Portrait());
	}

	public static PaperConfiguration forA4Portrait() {
		PaperConfiguration v = new PaperConfiguration();
		v.setSize(capex.PaperSize.forValue(capex.PaperSize.A4));
		v.setOrientation(capex.PaperOrientation.forValue(capex.PaperOrientation.PORTRAIT));
		return(v);
	}

	public static PaperConfiguration forA4Landscape() {
		PaperConfiguration v = new PaperConfiguration();
		v.setSize(capex.PaperSize.forValue(capex.PaperSize.A4));
		v.setOrientation(capex.PaperOrientation.forValue(capex.PaperOrientation.LANDSCAPE));
		return(v);
	}

	private PaperSize size = null;
	private PaperOrientation orientation = null;

	public Size getSizeInches() {
		Size sz = getRawSizeInches();
		if(capex.PaperOrientation.matches(orientation, capex.PaperOrientation.LANDSCAPE)) {
			return(new Size(sz.getHeight(), sz.getWidth()));
		}
		return(sz);
	}

	public Size getRawSizeInches() {
		if(capex.PaperSize.matches(size, capex.PaperSize.LETTER)) {
			return(new Size(8.50, (double)11));
		}
		if(capex.PaperSize.matches(size, capex.PaperSize.LEGAL)) {
			return(new Size(8.50, (double)14));
		}
		if(capex.PaperSize.matches(size, capex.PaperSize.A3)) {
			return(new Size(11.70, 16.50));
		}
		if(capex.PaperSize.matches(size, capex.PaperSize.A4)) {
			return(new Size(8.27, 11.70));
		}
		if(capex.PaperSize.matches(size, capex.PaperSize.A5)) {
			return(new Size(5.80, 8.30));
		}
		if(capex.PaperSize.matches(size, capex.PaperSize.B4)) {
			return(new Size(9.80, 13.90));
		}
		if(capex.PaperSize.matches(size, capex.PaperSize.B5)) {
			return(new Size(6.90, 9.80));
		}
		return(new Size(8.27, 11.70));
	}

	public Size getSizeDots(int dpi) {
		Size szi = getSizeInches();
		return(new Size(szi.getWidth() * dpi, szi.getHeight() * dpi));
	}

	public PaperSize getSize() {
		return(size);
	}

	public PaperConfiguration setSize(PaperSize v) {
		size = v;
		return(this);
	}

	public PaperOrientation getOrientation() {
		return(orientation);
	}

	public PaperConfiguration setOrientation(PaperOrientation v) {
		orientation = v;
		return(this);
	}
}
