
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

public class PaperOrientation implements cape.StringObject
{
	public static java.util.ArrayList<PaperOrientation> getAll() {
		java.util.ArrayList<PaperOrientation> v = new java.util.ArrayList<PaperOrientation>();
		int n = 0;
		for(n = 0 ; n < COUNT ; n++) {
			cape.Vector.append(v, forValue(n));
		}
		return(v);
	}

	public static boolean matches(PaperOrientation oo, int value) {
		if((oo != null) && (oo.getValue() == value)) {
			return(true);
		}
		return(false);
	}

	public static PaperOrientation forValue(int value) {
		return(new PaperOrientation().setValue(value));
	}

	public static final int LANDSCAPE = 0;
	public static final int PORTRAIT = 1;
	public static final int COUNT = 2;
	private int value = 0;

	public java.lang.String toString() {
		if(value == LANDSCAPE) {
			return("Landscape");
		}
		if(value == PORTRAIT) {
			return("Portrait");
		}
		return("Unknown orientation");
	}

	public int getValue() {
		return(value);
	}

	public PaperOrientation setValue(int v) {
		value = v;
		return(this);
	}
}
