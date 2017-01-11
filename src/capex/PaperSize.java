
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

public class PaperSize implements cape.StringObject
{
	public static java.util.ArrayList<PaperSize> getAll() {
		java.util.ArrayList<PaperSize> v = new java.util.ArrayList<PaperSize>();
		int n = 0;
		for(n = 0 ; n < COUNT ; n++) {
			cape.Vector.append(v, forValue(n));
		}
		return(v);
	}

	public static boolean matches(PaperSize sz, int value) {
		if((sz != null) && (sz.getValue() == value)) {
			return(true);
		}
		return(false);
	}

	public static PaperSize forValue(int value) {
		return(new PaperSize().setValue(value));
	}

	public static final int LETTER = 0;
	public static final int LEGAL = 1;
	public static final int A3 = 2;
	public static final int A4 = 3;
	public static final int A5 = 4;
	public static final int B4 = 5;
	public static final int B5 = 6;
	public static final int COUNT = 7;
	private int value = 0;

	public java.lang.String toString() {
		if(value == LETTER) {
			return("US Letter");
		}
		if(value == LEGAL) {
			return("US Legal");
		}
		if(value == A3) {
			return("A3");
		}
		if(value == A4) {
			return("A4");
		}
		if(value == A5) {
			return("A5");
		}
		if(value == B4) {
			return("B4");
		}
		if(value == B5) {
			return("B5");
		}
		return("Unknown paper size");
	}

	public int getValue() {
		return(value);
	}

	public PaperSize setValue(int v) {
		value = v;
		return(this);
	}
}
