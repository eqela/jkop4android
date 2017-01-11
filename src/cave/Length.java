
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

public class Length
{
	public static int asPoints(java.lang.String value, int ppi) {
		return(forString(value).toPoints(ppi));
	}

	public static Length forString(java.lang.String value) {
		Length v = new Length();
		v.parse(value);
		return(v);
	}

	public static Length forPoints(double value) {
		Length v = new Length();
		v.setValue(value);
		v.setUnit(UNIT_POINT);
		return(v);
	}

	public static Length forMilliMeters(double value) {
		Length v = new Length();
		v.setValue(value);
		v.setUnit(UNIT_MILLIMETER);
		return(v);
	}

	public static Length forMicroMeters(double value) {
		Length v = new Length();
		v.setValue(value);
		v.setUnit(UNIT_MICROMETER);
		return(v);
	}

	public static Length forNanoMeters(double value) {
		Length v = new Length();
		v.setValue(value);
		v.setUnit(UNIT_NANOMETER);
		return(v);
	}

	public static Length forInches(double value) {
		Length v = new Length();
		v.setValue(value);
		v.setUnit(UNIT_INCH);
		return(v);
	}

	public static Length forValue(double value, int unit) {
		Length v = new Length();
		v.setValue(value);
		v.setUnit(unit);
		return(v);
	}

	public static Length forStringAsPoints(java.lang.String value, int ppi) {
		Length v = new Length();
		v.parse(value);
		v.setValue((double)v.toPoints(ppi));
		v.setUnit(UNIT_POINT);
		return(v);
	}

	public static final int UNIT_POINT = 0;
	public static final int UNIT_MILLIMETER = 1;
	public static final int UNIT_MICROMETER = 2;
	public static final int UNIT_NANOMETER = 3;
	public static final int UNIT_INCH = 4;
	private double value = 0.00;
	private int unit = 0;

	private void parse(java.lang.String value) {
		if(android.text.TextUtils.equals(value, null)) {
			this.value = (double)0;
			unit = UNIT_POINT;
			return;
		}
		int i = 0;
		int n = 0;
		cape.CharacterIterator it = cape.String.iterate(value);
		while(true) {
			char c = it.getNextChar();
			if(c < 1) {
				break;
			}
			else if((c >= '0') && (c <= '9')) {
				i *= 10;
				i += (int)(c - '0');
			}
			else {
				break;
			}
			n++;
		}
		this.value = (double)i;
		java.lang.String suffix = cape.String.subString(value, n);
		if(cape.String.isEmpty(suffix)) {
			unit = UNIT_POINT;
		}
		else if((android.text.TextUtils.equals(suffix, "pt")) || (android.text.TextUtils.equals(suffix, "px"))) {
			unit = UNIT_POINT;
		}
		else if(android.text.TextUtils.equals(suffix, "mm")) {
			unit = UNIT_MILLIMETER;
		}
		else if(android.text.TextUtils.equals(suffix, "um")) {
			unit = UNIT_MICROMETER;
		}
		else if(android.text.TextUtils.equals(suffix, "nm")) {
			unit = UNIT_NANOMETER;
		}
		else if(android.text.TextUtils.equals(suffix, "in")) {
			unit = UNIT_INCH;
		}
		else {
			unit = UNIT_POINT;
		}
	}

	public int toPoints(int ppi) {
		if(unit == UNIT_POINT) {
			return((int)value);
		}
		if(unit == UNIT_MILLIMETER) {
			double v = (value * ppi) / 25;
			if((value > 0) && (v < 1)) {
				v = (double)1;
			}
			return((int)v);
		}
		if(unit == UNIT_MICROMETER) {
			double v = (value * ppi) / 25000;
			if((value > 0) && (v < 1)) {
				v = (double)1;
			}
			return((int)v);
		}
		if(unit == UNIT_NANOMETER) {
			double v = (value * ppi) / 25000000;
			if((value > 0) && (v < 1)) {
				v = (double)1;
			}
			return((int)v);
		}
		if(unit == UNIT_INCH) {
			double v = value * ppi;
			if((value > 0) && (v < 1)) {
				v = (double)1;
			}
			return((int)v);
		}
		return(0);
	}

	public double getValue() {
		return(value);
	}

	public Length setValue(double v) {
		value = v;
		return(this);
	}

	public int getUnit() {
		return(unit);
	}

	public Length setUnit(int v) {
		unit = v;
		return(this);
	}
}
