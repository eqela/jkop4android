
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

public class TextButtonWidget extends LayerWidget
{
	public static TextButtonWidget forText(cave.GuiApplicationContext context, java.lang.String text, samx.function.Procedure0 handler) {
		TextButtonWidget v = new TextButtonWidget(context);
		v.setWidgetText(text);
		v.setWidgetClickHandler(handler);
		return(v);
	}

	public TextButtonWidget(cave.GuiApplicationContext ctx) {
		super(ctx);
		widgetContext = ctx;
	}

	private cave.GuiApplicationContext widgetContext = null;
	private samx.function.Procedure0 widgetClickHandler = null;
	private java.lang.String widgetText = null;
	private cave.Color widgetBackgroundColor = null;
	private cave.Color widgetTextColor = null;
	private int widgetFontSize = 0;
	private java.lang.String widgetFontFamily = "Arial";
	private int padding = -1;

	public TextButtonWidget setWidgetClickHandler(samx.function.Procedure0 handler) {
		widgetClickHandler = handler;
		cave.ui.Widget.setWidgetClickHandler((android.view.View)this, handler);
		return(this);
	}

	@Override
	public void initializeWidget() {
		super.initializeWidget();
		cave.Color bgc = widgetBackgroundColor;
		if(bgc == null) {
			bgc = cave.Color.forRGBDouble(0.60, 0.60, 0.60);
		}
		cave.Color fgc = widgetTextColor;
		if(fgc == null) {
			if(bgc.isLightColor()) {
				fgc = cave.Color.forRGB(0, 0, 0);
			}
			else {
				fgc = cave.Color.forRGB(255, 255, 255);
			}
		}
		int padding = this.padding;
		if(padding < 0) {
			padding = context.getHeightValue("2mm");
		}
		CanvasWidget canvas = cave.ui.CanvasWidget.forColor(context, bgc);
		addWidget((android.view.View)canvas);
		LabelWidget label = cave.ui.LabelWidget.forText(context, widgetText);
		label.setWidgetTextColor(fgc);
		label.setWidgetFontFamily(widgetFontFamily);
		if(widgetFontSize > 0) {
			label.setWidgetFontSize((double)widgetFontSize);
		}
		addWidget((android.view.View)cave.ui.AlignWidget.forWidget(context, (android.view.View)label, 0.50, 0.50, padding));
	}

	public java.lang.String getWidgetText() {
		return(widgetText);
	}

	public TextButtonWidget setWidgetText(java.lang.String v) {
		widgetText = v;
		return(this);
	}

	public cave.Color getWidgetBackgroundColor() {
		return(widgetBackgroundColor);
	}

	public TextButtonWidget setWidgetBackgroundColor(cave.Color v) {
		widgetBackgroundColor = v;
		return(this);
	}

	public cave.Color getWidgetTextColor() {
		return(widgetTextColor);
	}

	public TextButtonWidget setWidgetTextColor(cave.Color v) {
		widgetTextColor = v;
		return(this);
	}

	public int getWidgetFontSize() {
		return(widgetFontSize);
	}

	public TextButtonWidget setWidgetFontSize(int v) {
		widgetFontSize = v;
		return(this);
	}

	public java.lang.String getWidgetFontFamily() {
		return(widgetFontFamily);
	}

	public TextButtonWidget setWidgetFontFamily(java.lang.String v) {
		widgetFontFamily = v;
		return(this);
	}

	public int getPadding() {
		return(padding);
	}

	public TextButtonWidget setPadding(int v) {
		padding = v;
		return(this);
	}
}
