
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

public class SplashScreenWidget extends LayerWidget
{
	private static class Slide
	{
		public java.lang.String resource = null;
		public int delay = 0;
	}

	private cave.Color backgroundColor = null;
	private java.util.ArrayList<Slide> slides = null;
	private samx.function.Procedure0 doneHandler = null;
	private int currentSlide = -1;
	private ImageWidget currentImageWidget = null;
	private java.lang.String imageWidgetWidth = "80mm";

	public SplashScreenWidget(cave.GuiApplicationContext ctx) {
		super(ctx);
		slides = new java.util.ArrayList<Slide>();
	}

	public void addSlide(java.lang.String resource, int delay) {
		Slide slide = new Slide();
		slide.resource = resource;
		slide.delay = delay;
		slides.add(slide);
	}

	@Override
	public void initializeWidget() {
		super.initializeWidget();
		if(backgroundColor != null) {
			addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, backgroundColor));
		}
		nextImage();
	}

	public void nextImage() {
		currentSlide++;
		Slide slide = cape.Vector.get(slides, currentSlide);
		if(slide == null) {
			WidgetAnimation anim = cave.ui.WidgetAnimation.forDuration(context, (long)1000);
			anim.addFadeOut((android.view.View)currentImageWidget, true);
			anim.setEndListener(new samx.function.Procedure0() {
				public void execute() {
					onEnded();
				}
			});
			anim.start();
			return;
		}
		ImageWidget imageWidget = cave.ui.ImageWidget.forImageResource(context, slide.resource);
		cave.ui.Widget.setAlpha((android.view.View)imageWidget, (double)0);
		imageWidget.setWidgetImageWidth(context.getWidthValue(imageWidgetWidth));
		AlignWidget align = cave.ui.AlignWidget.forWidget(context, (android.view.View)imageWidget, 0.50, 0.50, context.getWidthValue("5mm"));
		addWidget((android.view.View)align);
		WidgetAnimation anim = cave.ui.WidgetAnimation.forDuration(context, (long)1000);
		anim.addCrossFade((android.view.View)currentImageWidget, (android.view.View)imageWidget, true);
		anim.start();
		currentImageWidget = imageWidget;
		context.startTimer((long)slide.delay, new samx.function.Procedure0() {
			public void execute() {
				nextImage();
			}
		});
	}

	public void onEnded() {
		if(doneHandler != null) {
			doneHandler.execute();
		}
	}

	public cave.Color getBackgroundColor() {
		return(backgroundColor);
	}

	public SplashScreenWidget setBackgroundColor(cave.Color v) {
		backgroundColor = v;
		return(this);
	}

	public samx.function.Procedure0 getDoneHandler() {
		return(doneHandler);
	}

	public SplashScreenWidget setDoneHandler(samx.function.Procedure0 v) {
		doneHandler = v;
		return(this);
	}

	public java.lang.String getImageWidgetWidth() {
		return(imageWidgetWidth);
	}

	public SplashScreenWidget setImageWidgetWidth(java.lang.String v) {
		imageWidgetWidth = v;
		return(this);
	}
}
