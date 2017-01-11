
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

public class CanvasWidget extends android.view.View implements WidgetWithLayout
{
	public static CanvasWidget forColor(cave.GuiApplicationContext context, cave.Color color) {
		CanvasWidget v = new CanvasWidget(context);
		v.setWidgetColor(color);
		return(v);
	}

	private cave.GuiApplicationContext widgetContext = null;
	private cave.Color widgetColor = null;
	private boolean widgetRounded = false;
	private java.lang.String widgetRoundingRadius = null;
	private cave.Color widgetOutlineColor = null;
	private java.lang.String widgetOutlineWidth = null;

	public CanvasWidget(cave.GuiApplicationContext context) {
		super(((cave.GuiApplicationContextForAndroid)context).getActivityContext());
		widgetContext = context;
		widgetColor = cave.Color.black();
		widgetOutlineColor = cave.Color.black();
	}

	public boolean layoutWidget(int widthConstraint, boolean force) {
		cave.ui.Widget.setLayoutSize((android.view.View)this, widthConstraint, 0);
		return(true);
	}

	public void setWidgetColor(cave.Color color) {
		widgetColor = color;
		if(widgetColor == null) {
			setBackgroundColor(0);
		}
		else {
			setBackgroundColor(widgetColor.toARGBInt32());
		}
	}

	public cave.Color getWidgetColor() {
		return(widgetColor);
	}

	public void setWidgetOutlineColor(cave.Color color) {
		widgetOutlineColor = color;
		System.out.println("[cave.ui.CanvasWidget.setWidgetOutlineColor] (CanvasWidget.sling:134:2): Not implemented");
	}

	public cave.Color getWidgetOutlineColor() {
		return(widgetOutlineColor);
	}

	public void setWidgetOutlineWidth(java.lang.String width) {
		widgetOutlineWidth = width;
		System.out.println("[cave.ui.CanvasWidget.setWidgetOutlineWidth] (CanvasWidget.sling:150:2): Not implemented");
	}

	public java.lang.String getWidgetOutlineWidth() {
		return(widgetOutlineWidth);
	}

	public void setWidgetRounded(boolean rounded) {
		widgetRounded = rounded;
		System.out.println("[cave.ui.CanvasWidget.setWidgetRounded] (CanvasWidget.sling:174:2): Not implemented");
	}

	public boolean getWidgetRounded() {
		return(widgetRounded);
	}

	public void setWidgetRoundingRadius(java.lang.String radius) {
		widgetRoundingRadius = radius;
		System.out.println("[cave.ui.CanvasWidget.setWidgetRoundingRadius] (CanvasWidget.sling:195:2): Not implemented");
	}

	public java.lang.String getWidgetRoundingRadius() {
		return(widgetRoundingRadius);
	}
}
