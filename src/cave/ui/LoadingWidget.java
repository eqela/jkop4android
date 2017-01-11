
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

public class LoadingWidget extends LayerWidget
{
	private static java.lang.String displayText = null;
	private static cave.Image displayImage = null;

	public static void initializeWithText(java.lang.String text) {
		displayText = text;
		displayImage = null;
	}

	public static void initializeWithImage(cave.Image image) {
		displayText = null;
		displayImage = image;
	}

	public static LoadingWidget openPopup(cave.GuiApplicationContext context, android.view.View widget) {
		LoadingWidget v = new LoadingWidget(context);
		if(v.showPopup(widget) == false) {
			v = null;
		}
		return(v);
	}

	public static LoadingWidget closePopup(LoadingWidget widget) {
		if(widget != null) {
			widget.stop();
			cave.ui.Widget.removeFromParent((android.view.View)widget);
		}
		return(null);
	}

	private android.view.View loading = null;
	private WidgetAnimation animation = null;

	public LoadingWidget(cave.GuiApplicationContext ctx) {
		super(ctx);
	}

	@Override
	public void initializeWidget() {
		super.initializeWidget();
		addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, cave.Color.forRGBADouble((double)0, (double)0, (double)0, 0.80)));
		if(displayImage != null) {
			ImageWidget img = cave.ui.ImageWidget.forImage(context, displayImage);
			img.setWidgetImageHeight(context.getHeightValue("20mm"));
			loading = (android.view.View)img;
		}
		else {
			java.lang.String text = displayText;
			if(cape.String.isEmpty(text)) {
				text = "Loading ..";
			}
			LabelWidget lt = cave.ui.LabelWidget.forText(context, text);
			lt.setWidgetTextColor(cave.Color.white());
			lt.setWidgetFontSize((double)20);
			loading = (android.view.View)lt;
		}
		addWidget((android.view.View)cave.ui.AlignWidget.forWidget(context, loading, 0.50, 0.50));
		start();
	}

	public void start() {
		animation = cave.ui.WidgetAnimation.forDuration(context, (long)1000);
		animation.addFadeOutIn(loading);
		animation.setShouldLoop(true);
		animation.start();
	}

	public void stop() {
		if(animation != null) {
			animation.setShouldStop(true);
			animation = null;
		}
	}

	public boolean showPopup(android.view.View target) {
		LayerWidget topmost = findTopMostLayerWidget(target);
		if(topmost == null) {
			return(false);
		}
		topmost.addWidget((android.view.View)this);
		return(true);
	}
}
