
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

public class ImageButtonWidget extends LayerWidget
{
	public static ImageButtonWidget forImage(cave.GuiApplicationContext context, cave.Image image, samx.function.Procedure0 handler) {
		ImageButtonWidget v = new ImageButtonWidget(context);
		v.setWidgetImage(image);
		v.setWidgetClickHandler(handler);
		return(v);
	}

	public static ImageButtonWidget forImageResource(cave.GuiApplicationContext context, java.lang.String resName, samx.function.Procedure0 handler) {
		ImageButtonWidget v = new ImageButtonWidget(context);
		v.setWidgetImageResource(resName);
		v.setWidgetClickHandler(handler);
		return(v);
	}

	public ImageButtonWidget(cave.GuiApplicationContext ctx) {
		super(ctx);
		widgetContext = ctx;
	}

	private cave.GuiApplicationContext widgetContext = null;
	private cave.Image widgetImage = null;
	private java.lang.String widgetImageResource = null;
	private samx.function.Procedure0 widgetClickHandler = null;
	private int widgetButtonWidth = 0;
	private int widgetButtonHeight = 0;
	private ImageWidget imageWidget = null;

	public ImageButtonWidget setWidgetImage(cave.Image image) {
		widgetImage = image;
		widgetImageResource = null;
		updateImageWidget();
		return(this);
	}

	public ImageButtonWidget setWidgetImageResource(java.lang.String resName) {
		widgetImageResource = resName;
		widgetImage = null;
		updateImageWidget();
		return(this);
	}

	public ImageButtonWidget setWidgetClickHandler(samx.function.Procedure0 handler) {
		widgetClickHandler = handler;
		cave.ui.Widget.setWidgetClickHandler((android.view.View)this, handler);
		return(this);
	}

	private void updateImageWidget() {
		if(imageWidget == null) {
			return;
		}
		if(widgetImage != null) {
			imageWidget.setWidgetImage(widgetImage);
		}
		else {
			imageWidget.setWidgetImageResource(widgetImageResource);
		}
	}

	@Override
	public void initializeWidget() {
		super.initializeWidget();
		imageWidget = new ImageWidget(context);
		imageWidget.setWidgetImageWidth(widgetButtonWidth);
		imageWidget.setWidgetImageHeight(widgetButtonHeight);
		addWidget((android.view.View)imageWidget);
		updateImageWidget();
	}

	public int getWidgetButtonWidth() {
		return(widgetButtonWidth);
	}

	public ImageButtonWidget setWidgetButtonWidth(int v) {
		widgetButtonWidth = v;
		return(this);
	}

	public int getWidgetButtonHeight() {
		return(widgetButtonHeight);
	}

	public ImageButtonWidget setWidgetButtonHeight(int v) {
		widgetButtonHeight = v;
		return(this);
	}
}
