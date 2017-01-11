
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

public class FileInputWidget extends android.view.View
{
	public static FileInputWidget forType(cave.GuiApplicationContext context, java.lang.String type) {
		FileInputWidget v = new FileInputWidget(context);
		v.setWidgetType(type);
		return(v);
	}

	private cave.GuiApplicationContext widgetContext = null;
	private java.lang.String widgetType = null;
	private samx.function.Procedure0 widgetListener = null;

	public FileInputWidget(cave.GuiApplicationContext context) {
		super(((cave.GuiApplicationContextForAndroid)context).getActivityContext());
		widgetContext = context;
	}

	public void setWidgetType(java.lang.String type) {
		widgetType = type;
		System.out.println("[cave.ui.FileInputWidget.setWidgetType] (FileInputWidget.sling:80:2): Not implemented");
	}

	public java.lang.String getWidgetType() {
		return(widgetType);
	}

	public void setWidgetListener(java.lang.String event, samx.function.Procedure0 listener) {
		widgetListener = listener;
		System.out.println("[cave.ui.FileInputWidget.setWidgetListener] (FileInputWidget.sling:96:2): Not implemented");
	}

	public java.lang.String getWidgetFileName() {
		System.out.println("[cave.ui.FileInputWidget.getWidgetFileName] (FileInputWidget.sling:109:2): Not implemented");
		return(null);
	}

	public java.lang.String getWidgetFileMimeType() {
		System.out.println("[cave.ui.FileInputWidget.getWidgetFileMimeType] (FileInputWidget.sling:116:1): Not implemented");
		return(null);
	}

	public byte[] getWidgetFileContents() {
		System.out.println("[cave.ui.FileInputWidget.getWidgetFileContents] (FileInputWidget.sling:122:1): Not implemented");
		return(null);
	}
}
