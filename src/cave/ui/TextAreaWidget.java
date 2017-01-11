
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

public class TextAreaWidget extends android.widget.EditText implements WidgetWithValue
{
	public static TextAreaWidget forPlaceholder(cave.GuiApplicationContext context, java.lang.String placeholder, int rows) {
		TextAreaWidget v = new TextAreaWidget(context);
		v.setWidgetPlaceholder(placeholder);
		v.setWidgetRows(rows);
		return(v);
	}

	public static TextAreaWidget forPlaceholder(cave.GuiApplicationContext context, java.lang.String placeholder) {
		return(cave.ui.TextAreaWidget.forPlaceholder(context, placeholder, 1));
	}

	private cave.GuiApplicationContext widgetContext = null;
	private java.lang.String widgetPlaceholder = null;
	private int widgetPaddingLeft = 0;
	private int widgetPaddingTop = 0;
	private int widgetPaddingRight = 0;
	private int widgetPaddingBottom = 0;
	private int widgetRows = 0;
	private cave.Color widgetTextColor = null;
	private cave.Color widgetBackgroundColor = null;
	private java.lang.String widgetFontFamily = null;
	private double widgetFontSize = 0.00;

	public TextAreaWidget(cave.GuiApplicationContext context) {
		super(((cave.GuiApplicationContextForAndroid)context).getActivityContext());
		setHintTextColor(cave.Color.forRGB(154,154,154).toARGBInt32());
		setBackgroundColor(cave.Color.forRGB(255,255,255).toARGBInt32());
		setTextColor(cave.Color.forRGB(0,0,0).toARGBInt32());
		setGravity(android.view.Gravity.TOP);
		widgetFontFamily = "Arial";
		widgetFontSize = (double)context.getHeightValue("3mm");
		widgetTextColor = null;
		widgetBackgroundColor = null;
		widgetContext = context;
		updateWidgetFont();
		updateWidgetPadding();
		updateWidgetColors();
	}

	public void configureMonospaceFont() {
		setWidgetFontFamily("monospace");
	}

	private void updateWidgetColors() {
		cave.Color textColor = widgetTextColor;
		if(textColor == null) {
			if(widgetBackgroundColor != null) {
				if(widgetBackgroundColor.isLightColor()) {
					textColor = cave.Color.black();
				}
				else {
					textColor = cave.Color.white();
				}
			}
			else {
				textColor = cave.Color.black();
			}
		}
		setHintTextColor(cave.Color.forRGB(154,154,154).toARGBInt32());
		if(widgetBackgroundColor != null) {
			setBackgroundColor(widgetBackgroundColor.toARGBInt32());
		}
		else {
			setBackgroundColor(0);
		}
		setTextColor(textColor.toARGBInt32());
	}

	private void updateWidgetFont() {
		setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)widgetFontSize);
		// android.content.Context ctx = ((cave.GuiApplicationContextForAndroid)widgetContext).getActivityContext();
		// android.graphics.Typeface t = android.graphics.Typeface.createFromAsset(ctx.getAssets(),resName);
		setTypeface(android.graphics.Typeface.create(widgetFontFamily, android.graphics.Typeface.NORMAL));
	}

	public TextAreaWidget setWidgetFontFamily(java.lang.String family) {
		widgetFontFamily = family;
		updateWidgetFont();
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public TextAreaWidget setWidgetFontSize(double fontSize) {
		widgetFontSize = fontSize;
		updateWidgetFont();
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public TextAreaWidget setWidgetTextColor(cave.Color color) {
		widgetTextColor = color;
		updateWidgetColors();
		return(this);
	}

	public cave.Color getWidgetTextColor() {
		return(widgetTextColor);
	}

	public TextAreaWidget setWidgetBackgroundColor(cave.Color color) {
		widgetBackgroundColor = color;
		updateWidgetColors();
		return(this);
	}

	public cave.Color getWidgetBackgroundColor() {
		return(widgetBackgroundColor);
	}

	public TextAreaWidget setWidgetRows(int row) {
		this.widgetRows = row;
		super.setLines(row);
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public TextAreaWidget setWidgetText(java.lang.String text) {
		setText(text);
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public TextAreaWidget setWidgetPlaceholder(java.lang.String placeholder) {
		widgetPlaceholder = placeholder;
		setHint(placeholder);
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public TextAreaWidget setWidgetPadding(int padding) {
		return(setWidgetPadding(padding, padding, padding, padding));
	}

	public TextAreaWidget setWidgetPadding(int lr, int tb) {
		return(setWidgetPadding(lr, tb, lr, tb));
	}

	public TextAreaWidget setWidgetPadding(int l, int t, int r, int b) {
		if((((l < 0) || (t < 0)) || (r < 0)) || (b < 0)) {
			return(this);
		}
		if((((widgetPaddingLeft == l) && (widgetPaddingTop == t)) && (widgetPaddingRight == r)) && (widgetPaddingBottom == b)) {
			return(this);
		}
		widgetPaddingLeft = l;
		widgetPaddingTop = t;
		widgetPaddingRight = r;
		widgetPaddingBottom = b;
		updateWidgetPadding();
		return(this);
	}

	private void updateWidgetPadding() {
		setPadding(widgetPaddingLeft, widgetPaddingTop, widgetPaddingRight, widgetPaddingBottom);
	}

	public java.lang.String getWidgetText() {
		return(getText().toString());
	}

	public java.lang.String getWidgetPlaceholder() {
		return(widgetPlaceholder);
	}

	public TextAreaWidget setWidgetFontStyle(java.lang.String resName) {
		android.content.Context ctx = ((cave.GuiApplicationContextForAndroid)widgetContext).getActivityContext();
		android.graphics.Typeface t = android.graphics.Typeface.createFromAsset(ctx.getAssets(),resName);
		setTypeface(t);
		return(this);
	}

	public void setWidgetValue(java.lang.Object value) {
		setWidgetText(cape.String.asString(value));
	}

	public java.lang.Object getWidgetValue() {
		return((java.lang.Object)getWidgetText());
	}
}
