
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

public class ButtonWidget extends android.widget.Button
{
	public static ButtonWidget forText(cave.GuiApplicationContext context, java.lang.String text, samx.function.Procedure0 handler) {
		ButtonWidget v = new ButtonWidget(context);
		v.setWidgetText(text);
		v.setWidgetClickHandler(handler);
		return(v);
	}

	private cave.GuiApplicationContext widgetContext = null;
	private java.lang.String widgetText = null;
	private cave.Color widgetTextColor = null;
	private cave.Color widgetBackgroundColor = null;
	private cave.Image widgetIcon = null;
	private samx.function.Procedure0 widgetClickHandler = null;
	private java.lang.String widgetFont = null;
	private double widgetFontSize = 0.00;

	public ButtonWidget(cave.GuiApplicationContext context) {
		super(((cave.GuiApplicationContextForAndroid)context).getActivityContext());
		widgetContext = context;
	}

	public void setWidgetText(java.lang.String text) {
		widgetText = text;
		setText(text);
	}

	public java.lang.String getWidgetText() {
		return(widgetText);
	}

	public void setWidgetTextColor(cave.Color color) {
		widgetTextColor = color;
		if(color != null) {
			setTextColor(color.toARGBInt32());
		}
		else {
			setTextColor(0xff000000);
		}
	}

	public cave.Color getWidgetTextColor() {
		return(widgetTextColor);
	}

	public void setWidgetBackgroundColor(cave.Color color) {
		widgetBackgroundColor = color;
		if(color == null) {
			setBackgroundColor(0);
		}
		else {
			setBackgroundColor(color.toARGBInt32());
		}
	}

	public cave.Color getWidgetBackgroundColor() {
		return(widgetBackgroundColor);
	}

	public void onWidgetClicked() {
		if(widgetClickHandler != null) {
			widgetClickHandler.execute();
		}
	}

	public void setWidgetClickHandler(samx.function.Procedure0 handler) {
		widgetClickHandler = handler;
		final samx.function.Procedure0 hf = handler;
		if(handler == null) {
			setOnClickListener(null);
		}
		else {
			setOnClickListener(new OnClickListener() {
				public void onClick(android.view.View view) {
					hf.execute();
				}
			});
		}
	}

	public void setWidgetIcon(cave.Image icon) {
		widgetIcon = icon;
		System.out.println("[cave.ui.ButtonWidget.setWidgetIcon] (ButtonWidget.sling:246:2): Not implemented");
	}

	public cave.Image getWidgetIcon() {
		return(widgetIcon);
	}

	public void setWidgetFont(java.lang.String font) {
		widgetFont = font;
		System.out.println("[cave.ui.ButtonWidget.setWidgetFont] (ButtonWidget.sling:268:2): Not implemented");
	}

	public void setWidgetFontSize(double fontSize) {
		widgetFontSize = fontSize;
		setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, (float)fontSize);
	}
}
