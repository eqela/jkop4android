
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

public class Color
{
	private static Color colorBlack = null;
	private static Color colorWhite = null;

	public static Color black() {
		if(colorBlack == null) {
			colorBlack = instance("black");
		}
		return(colorBlack);
	}

	public static Color white() {
		if(colorWhite == null) {
			colorWhite = instance("white");
		}
		return(colorWhite);
	}

	public static Color asColor(java.lang.String o) {
		return(new Color(o));
	}

	public static Color asColor(java.lang.Object o) {
		if(o == null) {
			return(null);
		}
		if(o instanceof Color) {
			return((Color)o);
		}
		return(null);
	}

	public static Color instance(java.lang.String str) {
		if(android.text.TextUtils.equals(str, "none")) {
			return(null);
		}
		Color v = new Color();
		if(!(android.text.TextUtils.equals(str, null))) {
			if(v.parse(str) == false) {
				v = null;
			}
		}
		return(v);
	}

	public static Color instance() {
		return(cave.Color.instance(null));
	}

	public static Color forString(java.lang.String str) {
		if(android.text.TextUtils.equals(str, "none")) {
			return(null);
		}
		Color v = new Color();
		if(!(android.text.TextUtils.equals(str, null))) {
			if(v.parse(str) == false) {
				v = null;
			}
		}
		return(v);
	}

	public static Color forRGBDouble(double r, double g, double b) {
		Color v = new Color();
		v.setRed(r);
		v.setGreen(g);
		v.setBlue(b);
		v.setAlpha(1.00);
		return(v);
	}

	public static Color forRGBADouble(double r, double g, double b, double a) {
		Color v = new Color();
		v.setRed(r);
		v.setGreen(g);
		v.setBlue(b);
		v.setAlpha(a);
		return(v);
	}

	public static Color forRGB(int r, int g, int b) {
		Color v = new Color();
		v.setRed((double)(r / 255.00));
		v.setGreen((double)(g / 255.00));
		v.setBlue((double)(b / 255.00));
		v.setAlpha(1.00);
		return(v);
	}

	public static Color forRGBA(int r, int g, int b, int a) {
		Color v = new Color();
		v.setRed((double)(r / 255.00));
		v.setGreen((double)(g / 255.00));
		v.setBlue((double)(b / 255.00));
		v.setAlpha((double)(a / 255.00));
		return(v);
	}

	private double red = 0.00;
	private double green = 0.00;
	private double blue = 0.00;
	private double alpha = 0.00;

	public Color() {
	}

	public Color(java.lang.String str) {
		parse(str);
	}

	public boolean isWhite() {
		if(((red + green) + blue) >= 3.00) {
			return(true);
		}
		return(false);
	}

	public boolean isBlack() {
		if(((red + green) + blue) <= 0) {
			return(true);
		}
		return(false);
	}

	public boolean isLightColor() {
		if(((red + green) + blue) >= 1.50) {
			return(true);
		}
		return(false);
	}

	public boolean isDarkColor() {
		return(!isLightColor());
	}

	private int hexCharToInt(char c) {
		if((c >= '0') && (c <= '9')) {
			return(((int)c) - ((int)'0'));
		}
		if((c >= 'a') && (c <= 'f')) {
			return((10 + ((int)c)) - ((int)'a'));
		}
		if((c >= 'A') && (c <= 'F')) {
			return((10 + ((int)c)) - ((int)'A'));
		}
		return(0);
	}

	private int hexDigitToInt(java.lang.String hx) {
		if(cape.String.isEmpty(hx)) {
			return(0);
		}
		int c0 = hexCharToInt(cape.String.charAt(hx, 0));
		if(cape.String.getLength(hx) < 2) {
			return(c0);
		}
		return((c0 * 16) + hexCharToInt(cape.String.charAt(hx, 1)));
	}

	public boolean parse(java.lang.String s) {
		if(android.text.TextUtils.equals(s, null)) {
			red = 0.00;
			green = 0.00;
			blue = 0.00;
			alpha = 1.00;
			return(true);
		}
		boolean v = true;
		alpha = 1.00;
		if(cape.String.charAt(s, 0) == '#') {
			int slength = cape.String.getLength(s);
			if((slength == 7) || (slength == 9)) {
				red = (double)(hexDigitToInt(cape.String.getSubString(s, 1, 2)) / 255.00);
				green = (double)(hexDigitToInt(cape.String.getSubString(s, 3, 2)) / 255.00);
				blue = (double)(hexDigitToInt(cape.String.getSubString(s, 5, 2)) / 255.00);
				if(slength == 9) {
					alpha = (double)(hexDigitToInt(cape.String.getSubString(s, 7, 2)) / 255.00);
				}
				v = true;
			}
			else {
				red = green = blue = 0.00;
				v = false;
			}
		}
		else if(android.text.TextUtils.equals(s, "black")) {
			red = 0.00;
			green = 0.00;
			blue = 0.00;
		}
		else if(android.text.TextUtils.equals(s, "white")) {
			red = 1.00;
			green = 1.00;
			blue = 1.00;
		}
		else if(android.text.TextUtils.equals(s, "red")) {
			red = 1.00;
			green = 0.00;
			blue = 0.00;
		}
		else if(android.text.TextUtils.equals(s, "green")) {
			red = 0.00;
			green = 1.00;
			blue = 0.00;
		}
		else if(android.text.TextUtils.equals(s, "blue")) {
			red = 0.00;
			green = 0.00;
			blue = 1.00;
		}
		else if(android.text.TextUtils.equals(s, "lightred")) {
			red = 0.60;
			green = 0.40;
			blue = 0.40;
		}
		else if(android.text.TextUtils.equals(s, "lightgreen")) {
			red = 0.40;
			green = 0.60;
			blue = 0.40;
		}
		else if(android.text.TextUtils.equals(s, "lightblue")) {
			red = 0.40;
			green = 0.40;
			blue = 0.60;
		}
		else if(android.text.TextUtils.equals(s, "yellow")) {
			red = 1.00;
			green = 1.00;
			blue = 0.00;
		}
		else if(android.text.TextUtils.equals(s, "cyan")) {
			red = 0.00;
			green = 1.00;
			blue = 1.00;
		}
		else if(android.text.TextUtils.equals(s, "orange")) {
			red = 1.00;
			green = 0.50;
			blue = 0.00;
		}
		else {
			v = false;
		}
		return(v);
	}

	private int decimalStringToInteger(java.lang.String str) {
		if(cape.String.isEmpty(str)) {
			return(0);
		}
		int v = 0;
		int m = cape.String.getLength(str);
		int n = 0;
		for(n = 0 ; n < m ; n++) {
			char c = cape.String.charAt(str, n);
			if((c >= '0') && (c <= '9')) {
				v = v * 10;
				v += (int)(c - '0');
			}
			else {
				break;
			}
		}
		return(v);
	}

	public Color dup(java.lang.String arg) {
		double f = 1.00;
		if(!(android.text.TextUtils.equals(arg, null))) {
			if(android.text.TextUtils.equals(arg, "light")) {
				f = 1.20;
			}
			else if(android.text.TextUtils.equals(arg, "dark")) {
				f = 0.80;
			}
			else if(cape.String.endsWith(arg, "%")) {
				f = ((double)decimalStringToInteger(arg)) / 100.00;
			}
		}
		Color v = new Color();
		if(f > 1.00) {
			v.setRed(red + ((1.00 - red) * (f - 1.00)));
			v.setGreen(green + ((1.00 - green) * (f - 1.00)));
			v.setBlue(blue + ((1.00 - blue) * (f - 1.00)));
		}
		else if(f < 1.00) {
			v.setRed(red * f);
			v.setGreen(green * f);
			v.setBlue(blue * f);
		}
		else {
			v.setRed(red);
			v.setGreen(green);
			v.setBlue(blue);
		}
		v.setAlpha(alpha);
		return(v);
	}

	public Color dup() {
		return(dup(null));
	}

	public int toRGBAInt32() {
		int v = (int)0;
		v |= ((int)(((int)(red * 255)) & 255)) << 24;
		v |= ((int)(((int)(green * 255)) & 255)) << 16;
		v |= ((int)(((int)(blue * 255)) & 255)) << 8;
		v |= (int)(((int)(alpha * 255)) & 255);
		return(v);
	}

	public int toARGBInt32() {
		int v = (int)0;
		v |= ((int)(((int)(alpha * 255)) & 255)) << 24;
		v |= ((int)(((int)(red * 255)) & 255)) << 16;
		v |= ((int)(((int)(green * 255)) & 255)) << 8;
		v |= (int)(((int)(blue * 255)) & 255);
		return(v);
	}

	public java.lang.String toString() {
		return(toRgbaString());
	}

	public java.lang.String toRgbString() {
		java.lang.String r = cape.String.forIntegerHex((int)(red * 255));
		java.lang.String g = cape.String.forIntegerHex((int)(green * 255));
		java.lang.String b = cape.String.forIntegerHex((int)(blue * 255));
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append("#");
		to2Digits(r, sb);
		to2Digits(g, sb);
		to2Digits(b, sb);
		return(sb.toString());
	}

	public java.lang.String toRgbaString() {
		java.lang.String a = cape.String.forIntegerHex((int)(alpha * 255));
		return(toRgbString() + a);
	}

	public void to2Digits(java.lang.String val, cape.StringBuilder sb) {
		if(cape.String.getLength(val) == 1) {
			sb.append('0');
		}
		sb.append(val);
	}

	public double getRed() {
		return(red);
	}

	public Color setRed(double v) {
		red = v;
		return(this);
	}

	public double getGreen() {
		return(green);
	}

	public Color setGreen(double v) {
		green = v;
		return(this);
	}

	public double getBlue() {
		return(blue);
	}

	public Color setBlue(double v) {
		blue = v;
		return(this);
	}

	public double getAlpha() {
		return(alpha);
	}

	public Color setAlpha(double v) {
		alpha = v;
		return(this);
	}
}
