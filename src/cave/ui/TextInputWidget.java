
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

public class TextInputWidget extends android.widget.EditText implements WidgetWithValue
{
	public static TextInputWidget forType(cave.GuiApplicationContext context, int type, java.lang.String placeholder) {
		TextInputWidget v = new TextInputWidget(context);
		v.setWidgetType(type);
		v.setWidgetPlaceholder(placeholder);
		return(v);
	}

	public static final int TYPE_DEFAULT = 0;
	public static final int TYPE_NONASSISTED = 1;
	public static final int TYPE_NAME = 2;
	public static final int TYPE_EMAIL_ADDRESS = 3;
	public static final int TYPE_URL = 4;
	public static final int TYPE_PHONE_NUMBER = 5;
	public static final int TYPE_PASSWORD = 6;
	public static final int TYPE_INTEGER = 7;
	public static final int TYPE_FLOAT = 8;
	public static final int TYPE_STREET_ADDRESS = 9;
	private cave.GuiApplicationContext widgetContext = null;
	private int widgetType = 0;
	private java.lang.String widgetPlaceholder = null;
	private java.lang.String widgetText = null;
	private int widgetPaddingLeft = 0;
	private int widgetPaddingTop = 0;
	private int widgetPaddingRight = 0;
	private int widgetPaddingBottom = 0;
	private java.lang.String widgetFontFamily = null;
	private double widgetFontSize = 0.00;
	private cave.Color widgetTextColor = null;
	private cave.Color widgetBackgroundColor = null;

	public TextInputWidget(cave.GuiApplicationContext context) {
		super(((cave.GuiApplicationContextForAndroid)context).getActivityContext());
		setSingleLine();
		widgetContext = context;
		widgetFontFamily = "Arial";
		widgetFontSize = (double)context.getHeightValue("3mm");
		widgetTextColor = null;
		widgetBackgroundColor = null;
		updateWidgetFont();
		updateWidgetPadding();
		updateWidgetColors();
	}

	public TextInputWidget setWidgetTextColor(cave.Color color) {
		widgetTextColor = color;
		updateWidgetColors();
		return(this);
	}

	public cave.Color getWidgetTextColor() {
		return(widgetTextColor);
	}

	public TextInputWidget setWidgetBackgroundColor(cave.Color color) {
		widgetBackgroundColor = color;
		updateWidgetColors();
		return(this);
	}

	public cave.Color getWidgetBackgroundColor() {
		return(widgetBackgroundColor);
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

	public TextInputWidget setWidgetType(int type) {
		widgetType = type;
		if(widgetType == TYPE_DEFAULT) {
			setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE |
			android.text.InputType.TYPE_TEXT_FLAG_AUTO_CORRECT | android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		}
		else if(widgetType == TYPE_NONASSISTED) {
			setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
			| android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}
		else if(widgetType == TYPE_NAME) {
			setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PERSON_NAME |
			android.text.InputType.TYPE_TEXT_FLAG_CAP_WORDS);
		}
		else if(widgetType == TYPE_EMAIL_ADDRESS) {
			setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
			| android.text.InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
		}
		else if(widgetType == TYPE_URL) {
			setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_URI);
		}
		else if(widgetType == TYPE_PHONE_NUMBER) {
			setInputType(android.text.InputType.TYPE_CLASS_PHONE);
		}
		else if(widgetType == TYPE_PASSWORD) {
			setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
		else if(widgetType == TYPE_INTEGER) {
			setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
		}
		else if(widgetType == TYPE_FLOAT) {
			setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
		}
		else if(widgetType == TYPE_STREET_ADDRESS) {
			setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS |
			android.text.InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE | android.text.InputType.TYPE_TEXT_FLAG_AUTO_CORRECT |
			android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		}
		else {
			setInputType(android.text.InputType.TYPE_CLASS_TEXT);
		}
		return(this);
	}

	public int getWidgetType() {
		return(widgetType);
	}

	public TextInputWidget setWidgetText(java.lang.String text) {
		widgetText = text;
		setText(text);
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public java.lang.String getWidgetText() {
		return(getText().toString());
	}

	public TextInputWidget setWidgetPlaceholder(java.lang.String placeholder) {
		widgetPlaceholder = placeholder;
		setHint(placeholder);
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public java.lang.String getWidgetPlaceholder() {
		return(widgetPlaceholder);
	}

	public void setWidgetPadding(int padding) {
		setWidgetPadding(padding, padding, padding, padding);
	}

	public void setWidgetPadding(int x, int y) {
		setWidgetPadding(x, y, x, y);
	}

	public TextInputWidget setWidgetPadding(int l, int t, int r, int b) {
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
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	private void updateWidgetPadding() {
		setPadding(widgetPaddingLeft, widgetPaddingTop, widgetPaddingRight, widgetPaddingBottom);
	}

	public TextInputWidget setWidgetFontFamily(java.lang.String family) {
		widgetFontFamily = family;
		updateWidgetFont();
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public TextInputWidget setWidgetFontSize(double fontSize) {
		widgetFontSize = fontSize;
		updateWidgetFont();
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	private void updateWidgetFont() {
		setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)widgetFontSize);
		// android.content.Context ctx = ((cave.GuiApplicationContextForAndroid)widgetContext).getActivityContext();
		// android.graphics.Typeface t = android.graphics.Typeface.createFromAsset(ctx.getAssets(),resName);
		setTypeface(android.graphics.Typeface.create(widgetFontFamily, android.graphics.Typeface.NORMAL));
	}

	public void setWidgetValue(java.lang.Object value) {
		setWidgetText(cape.String.asString(value));
	}

	public java.lang.Object getWidgetValue() {
		return((java.lang.Object)getWidgetText());
	}
}
