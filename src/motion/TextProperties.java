
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

package motion;

public class TextProperties
{
	public static TextProperties forText(java.lang.String tt) {
		TextProperties v = new TextProperties();
		v.setText(tt);
		return(v);
	}

	public TextProperties() {
		textColor = cave.Color.black();
	}

	private java.lang.String text = null;
	private cave.Color textColor = null;
	private cave.Color outlineColor = null;
	private cave.Color backgroundColor = null;
	private java.lang.String fontFamily = null;
	private java.lang.String fontResource = null;
	private cape.File fontFile = null;
	private boolean fontIsItalic = false;
	private boolean fontIsBold = false;
	private double fontSizeRelative = 0.00;
	private double fontSizeAbsolute = 1.00;
	private java.lang.String fontSizeDescription = null;

	public java.lang.String getText() {
		return(text);
	}

	public TextProperties setText(java.lang.String v) {
		text = v;
		return(this);
	}

	public cave.Color getTextColor() {
		return(textColor);
	}

	public TextProperties setTextColor(cave.Color v) {
		textColor = v;
		return(this);
	}

	public cave.Color getOutlineColor() {
		return(outlineColor);
	}

	public TextProperties setOutlineColor(cave.Color v) {
		outlineColor = v;
		return(this);
	}

	public cave.Color getBackgroundColor() {
		return(backgroundColor);
	}

	public TextProperties setBackgroundColor(cave.Color v) {
		backgroundColor = v;
		return(this);
	}

	public java.lang.String getFontFamily() {
		return(fontFamily);
	}

	public TextProperties setFontFamily(java.lang.String v) {
		fontFamily = v;
		return(this);
	}

	public java.lang.String getFontResource() {
		return(fontResource);
	}

	public TextProperties setFontResource(java.lang.String v) {
		fontResource = v;
		return(this);
	}

	public cape.File getFontFile() {
		return(fontFile);
	}

	public TextProperties setFontFile(cape.File v) {
		fontFile = v;
		return(this);
	}

	public boolean getFontIsItalic() {
		return(fontIsItalic);
	}

	public TextProperties setFontIsItalic(boolean v) {
		fontIsItalic = v;
		return(this);
	}

	public boolean getFontIsBold() {
		return(fontIsBold);
	}

	public TextProperties setFontIsBold(boolean v) {
		fontIsBold = v;
		return(this);
	}

	public double getFontSizeRelative() {
		return(fontSizeRelative);
	}

	public TextProperties setFontSizeRelative(double v) {
		fontSizeRelative = v;
		return(this);
	}

	public double getFontSizeAbsolute() {
		return(fontSizeAbsolute);
	}

	public TextProperties setFontSizeAbsolute(double v) {
		fontSizeAbsolute = v;
		return(this);
	}

	public java.lang.String getFontSizeDescription() {
		return(fontSizeDescription);
	}

	public TextProperties setFontSizeDescription(java.lang.String v) {
		fontSizeDescription = v;
		return(this);
	}
}
