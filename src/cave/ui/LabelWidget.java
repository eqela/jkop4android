
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

package cave.ui;

public class LabelWidget extends android.widget.TextView
{
	public static final int ALIGN_LEFT = 0;
	public static final int ALIGN_CENTER = 1;
	public static final int ALIGN_RIGHT = 2;
	public static final int ALIGN_JUSTIFY = 3;

	public static LabelWidget forText(cave.GuiApplicationContext context, java.lang.String text) {
		LabelWidget v = new LabelWidget(context);
		v.setWidgetText(text);
		return(v);
	}

	private cave.GuiApplicationContext widgetContext = null;
	private java.lang.String widgetText = null;
	private cave.Color widgetTextColor = null;
	private double widgetFontSize = 0.00;
	private boolean widgetFontBold = false;
	private java.lang.String widgetFontFamily = null;
	private int widgetTextAlign = 0;

	public LabelWidget(cave.GuiApplicationContext context) {
		super(((cave.GuiApplicationContextForAndroid)context).getActivityContext());
		widgetContext = context;
		setWidgetTextColor(cave.Color.forRGB(0, 0, 0));
		widgetFontFamily = "Arial";
		widgetFontSize = (double)context.getHeightValue("3mm");
		widgetFontBold = false;
		setGravity(android.view.Gravity.LEFT);
		updateWidgetFont();
	}

	public LabelWidget setWidgetText(java.lang.String text) {
		widgetText = text;
		if(android.text.TextUtils.equals(widgetText, null)) {
			widgetText = "";
		}
		setText(widgetText);
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public java.lang.String getWidgetText() {
		return(widgetText);
	}

	public LabelWidget setWidgetTextAlign(int align) {
		widgetTextAlign = align;
		if(align == ALIGN_LEFT) {
			setGravity(android.view.Gravity.LEFT);
		}
		else if(align == ALIGN_CENTER) {
			setGravity(android.view.Gravity.CENTER);
		}
		else if(align == ALIGN_RIGHT) {
			setGravity(android.view.Gravity.RIGHT);
		}
		else if(align == ALIGN_JUSTIFY) {
			setGravity(android.view.Gravity.CENTER);
		}
		else {
			setGravity(android.view.Gravity.LEFT);
		}
		return(this);
	}

	public int getWidgetTextAlign() {
		return(widgetTextAlign);
	}

	public LabelWidget setWidgetTextColor(cave.Color color) {
		widgetTextColor = color;
		updateWidgetFont();
		return(this);
	}

	public cave.Color getWidgetTextColor() {
		return(widgetTextColor);
	}

	public LabelWidget setWidgetFontSize(double fontSize) {
		widgetFontSize = fontSize;
		updateWidgetFont();
		return(this);
	}

	public double getFontSize() {
		return(widgetFontSize);
	}

	public LabelWidget setWidgetFontBold(boolean bold) {
		widgetFontBold = bold;
		updateWidgetFont();
		return(this);
	}

	public LabelWidget setWidgetFontFamily(java.lang.String font) {
		widgetFontFamily = font;
		updateWidgetFont();
		return(this);
	}

	private void updateWidgetFont() {
		if(widgetTextColor != null) {
			setTextColor(widgetTextColor.toARGBInt32());
		}
		else {
			setTextColor(0);
		}
		setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)widgetFontSize);
		int style = android.graphics.Typeface.NORMAL;
		if(widgetFontBold) {
			style = android.graphics.Typeface.BOLD;
		}
		setTypeface(android.graphics.Typeface.create(widgetFontFamily, style));
		cave.ui.Widget.onChanged((android.view.View)this);
	}
}
