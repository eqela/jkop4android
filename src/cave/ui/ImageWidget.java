
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

public class ImageWidget extends android.widget.ImageView implements WidgetWithLayout
{
	public static ImageWidget forImage(cave.GuiApplicationContext context, cave.Image image) {
		ImageWidget v = new ImageWidget(context);
		v.setWidgetImage(image);
		return(v);
	}

	public static ImageWidget forImageResource(cave.GuiApplicationContext context, java.lang.String resName) {
		ImageWidget v = new ImageWidget(context);
		v.setWidgetImageResource(resName);
		return(v);
	}

	public static final int STRETCH = 0;
	public static final int FIT = 1;
	public static final int FILL = 2;
	private cave.GuiApplicationContext widgetContext = null;
	private cave.Image widgetImage = null;
	private int widgetImageWidth = 0;
	private int widgetImageHeight = 0;
	private int widgetImageScaleMethod = 0;

	public ImageWidget(cave.GuiApplicationContext context) {
		super(((cave.GuiApplicationContextForAndroid)context).getActivityContext());
		widgetContext = context;
		setWidgetImageScaleMethod(STRETCH);
	}

	public void setWidgetImageScaleMethod(int method) {
		widgetImageScaleMethod = method;
		if(method == FIT) {
			setScaleType(android.widget.ImageView.ScaleType.CENTER_INSIDE);
		}
		else if(method == FILL) {
			setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
		}
		else {
			if(method != STRETCH) {
				cape.Log.warning((cape.LoggingContext)widgetContext, "Unsupported image scale method: " + cape.String.forInteger(method));
			}
			setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
		}
	}

	public void setWidgetImage(cave.Image image) {
		widgetImage = image;
		cave.ImageForAndroid ifa = (cave.ImageForAndroid)((widgetImage instanceof cave.ImageForAndroid) ? widgetImage : null);
		if(ifa == null) {
			super.setImageBitmap(null);
		}
		else {
			super.setImageBitmap(ifa.getAndroidBitmap());
		}
		cave.ui.Widget.onChanged((android.view.View)this);
	}

	public void setWidgetImageResource(java.lang.String resName) {
		cave.Image img = null;
		if((cape.String.isEmpty(resName) == false) && (widgetContext != null)) {
			img = widgetContext.getResourceImage(resName);
			if(img == null) {
				cape.Log.error((cape.LoggingContext)widgetContext, ("Failed to get resource image: `" + resName) + "'");
			}
		}
		setWidgetImage(img);
	}

	public boolean layoutWidget(int widthConstraint, boolean force) {
		if(widgetImage == null) {
			cave.ui.Widget.setLayoutSize((android.view.View)this, widgetImageWidth, widgetImageHeight);
			return(true);
		}
		if(((widthConstraint < 0) && (widgetImageWidth < 1)) && (widgetImageHeight < 1)) {
			return(false);
		}
		int width = -1;
		int height = -1;
		if((widgetImageWidth > 0) && (widgetImageHeight > 0)) {
			width = widgetImageWidth;
			height = widgetImageHeight;
		}
		else if(widgetImageWidth > 0) {
			width = widgetImageWidth;
		}
		else if(widgetImageHeight > 0) {
			height = widgetImageHeight;
		}
		else {
			width = widthConstraint;
		}
		if(((width > 0) && (widthConstraint >= 0)) && (width > widthConstraint)) {
			width = widthConstraint;
			height = -1;
		}
		if(height < 0) {
			height = (widgetImage.getPixelHeight() * width) / widgetImage.getPixelWidth();
		}
		if(width < 0) {
			width = (widgetImage.getPixelWidth() * height) / widgetImage.getPixelHeight();
		}
		cave.ui.Widget.setLayoutSize((android.view.View)this, width, height);
		return(true);
	}

	public int getWidgetImageWidth() {
		return(widgetImageWidth);
	}

	public ImageWidget setWidgetImageWidth(int v) {
		widgetImageWidth = v;
		return(this);
	}

	public int getWidgetImageHeight() {
		return(widgetImageHeight);
	}

	public ImageWidget setWidgetImageHeight(int v) {
		widgetImageHeight = v;
		return(this);
	}
}
