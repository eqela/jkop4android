
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

public class PopupWidget extends LayerWidget
{
	public static PopupWidget forContentWidget(cave.GuiApplicationContext context, android.view.View widget) {
		PopupWidget v = new PopupWidget(context);
		v.setWidgetContent(widget);
		return(v);
	}

	private cave.GuiApplicationContext widgetContext = null;
	private CanvasWidget widgetContainerBackgroundColor = null;
	private android.view.View widgetContent = null;

	public PopupWidget(cave.GuiApplicationContext ctx) {
		super(ctx);
		widgetContext = ctx;
	}

	public void setWidgetContainerBackgroundColor(cave.Color color) {
		if(color != null) {
			widgetContainerBackgroundColor = cave.ui.CanvasWidget.forColor(widgetContext, color);
		}
	}

	public void setWidgetContent(android.view.View widget) {
		if(widget != null) {
			widgetContent = widget;
		}
	}

	public CanvasWidget getWidgetContainerBackgroundColor() {
		return(widgetContainerBackgroundColor);
	}

	public android.view.View getWidgetContent() {
		return(widgetContent);
	}

	@Override
	public void initializeWidget() {
		super.initializeWidget();
		if(widgetContainerBackgroundColor == null) {
			widgetContainerBackgroundColor = cave.ui.CanvasWidget.forColor(widgetContext, cave.Color.forRGBADouble((double)0, (double)0, (double)0, 0.80));
			cave.ui.Widget.setWidgetClickHandler((android.view.View)widgetContainerBackgroundColor, new samx.function.Procedure0() {
				public void execute() {
					;
				}
			});
		}
		addWidget((android.view.View)widgetContainerBackgroundColor);
		if(widgetContent != null) {
			addWidget((android.view.View)cave.ui.AlignWidget.forWidget(widgetContext, widgetContent, 0.50, 0.50));
		}
	}

	private int animationDestY = 0;

	@Override
	public void onWidgetHeightChanged(int height) {
		super.onWidgetHeightChanged(height);
		animationDestY = cave.ui.Widget.getY(widgetContent);
	}

	@Override
	public void computeWidgetLayout(int widthConstraint) {
		super.computeWidgetLayout(widthConstraint);
		animationDestY = cave.ui.Widget.getY(widgetContent);
	}

	public void showPopup(android.view.View widget) {
		LayerWidget parentLayer = null;
		android.view.View parent = widget;
		while(parent != null) {
			if(parent instanceof LayerWidget) {
				parentLayer = (LayerWidget)parent;
			}
			parent = cave.ui.Widget.getParent(parent);
		}
		if(parentLayer == null) {
			System.out.println("[cave.ui.PopupWidget.showPopup] (PopupWidget.sling:124:3): No LayerWidget was found in the widget tree. Cannot show popup!");
			return;
		}
		parentLayer.addWidget((android.view.View)this);
		cave.ui.Widget.setAlpha((android.view.View)widgetContainerBackgroundColor, (double)0);
		cave.ui.Widget.setAlpha(widgetContent, (double)0);
		animationDestY = cave.ui.Widget.getY(widgetContent);
		final int ay = context.getHeightValue("3mm");
		cave.ui.Widget.move(widgetContent, cave.ui.Widget.getX(widgetContent), (int)(animationDestY + ay));
		WidgetAnimation anim = cave.ui.WidgetAnimation.forDuration(context, (long)300);
		anim.addCallback(new samx.function.Procedure1<java.lang.Double>() {
			public void execute(java.lang.Double completion) {
				double bgf = completion * 1.50;
				if(bgf > 1.00) {
					bgf = 1.00;
				}
				cave.ui.Widget.setAlpha((android.view.View)widgetContainerBackgroundColor, bgf);
				cave.ui.Widget.setAlpha(widgetContent, completion);
				cave.ui.Widget.move(widgetContent, cave.ui.Widget.getX(widgetContent), (int)(animationDestY + ((1.00 - completion) * ay)));
			}
		});
		anim.setEndListener(new samx.function.Procedure0() {
			public void execute() {
				;
			}
		});
		anim.start();
	}

	public void hidePopup() {
		WidgetAnimation anim = cave.ui.WidgetAnimation.forDuration(context, (long)300);
		anim.addFadeOut((android.view.View)this, true);
		anim.start();
	}
}
