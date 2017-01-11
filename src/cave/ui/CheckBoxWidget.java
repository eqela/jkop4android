
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

public class CheckBoxWidget extends android.widget.CheckBox
{
	public static CheckBoxWidget forText(cave.GuiApplicationContext context, java.lang.String text) {
		CheckBoxWidget v = new CheckBoxWidget(context);
		v.setWidgetText(text);
		return(v);
	}

	private cave.GuiApplicationContext widgetContext = null;
	private java.lang.String widgetText = null;

	public CheckBoxWidget(cave.GuiApplicationContext context) {
		super(((cave.GuiApplicationContextForAndroid)context).getActivityContext());
		widgetContext = context;
	}

	public void setWidgetText(java.lang.String text) {
		widgetText = text;
		System.out.println("[cave.ui.CheckBoxWidget.setWidgetText] (CheckBoxWidget.sling:82:2): Not implemented");
	}

	public java.lang.String getWidgetText() {
		return(widgetText);
	}

	public boolean getWidgetChecked() {
		return(isChecked());
	}

	public void setWidgetChecked(boolean x) {
		System.out.println("[cave.ui.CheckBoxWidget.setWidgetChecked] (CheckBoxWidget.sling:117:2): Not implemented");
	}
}
