
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

public class CustomContainerWidget extends android.view.ViewGroup implements WidgetWithLayout, HeightAwareWidget
{
	protected cave.GuiApplicationContext context = null;
	private boolean allowResize = true;

	public void onLayout(boolean changed, int left, int top, int right, int bottom) {
		setMeasuredDimension(right - left, bottom - top);
		onNativelyResized();
	}

	private boolean initialized = false;
	private boolean widgetChanged = true;
	private int lastWidthConstraint = -2;
	private int lastLayoutWidth = -1;

	public CustomContainerWidget(cave.GuiApplicationContext ctx) {
		super(((cave.GuiApplicationContextForAndroid)ctx).getActivityContext());
		setLayerType(android.view.View.LAYER_TYPE_NONE, null);
		context = ctx;
		togglePointerEventHandling(false);
	}

	public void togglePointerEventHandling(boolean active) {
		if(active) {
			;
		}
		else {
			;
		}
	}

	public void onNativelyResized() {
		if(cave.ui.Widget.isRootWidget((android.view.View)this)) {
			cave.ui.Widget.layout((android.view.View)this, cave.ui.Widget.getWidth((android.view.View)this));
			onWidgetHeightChanged(cave.ui.Widget.getHeight((android.view.View)this));
		}
	}

	public boolean hasSize() {
		if((cave.ui.Widget.getWidth((android.view.View)this) > 0) || (cave.ui.Widget.getHeight((android.view.View)this) > 0)) {
			return(true);
		}
		return(false);
	}

	public void tryInitializeWidget() {
		if(initialized) {
			return;
		}
		setInitialized(true);
		initializeWidget();
	}

	public void initializeWidget() {
	}

	public CustomContainerWidget addWidget(android.view.View widget) {
		cave.ui.Widget.addChild((android.view.View)this, widget);
		return(this);
	}

	public void onChildWidgetAdded(android.view.View widget) {
		cave.ui.Widget.onChanged((android.view.View)this);
	}

	public void onChildWidgetRemoved(android.view.View widget) {
		cave.ui.Widget.onChanged((android.view.View)this);
	}

	public void onWidgetAddedToParent() {
		cave.ui.Widget.onChanged((android.view.View)this);
	}

	public void onWidgetRemovedFromParent() {
	}

	public void onWidgetHeightChanged(int height) {
	}

	public void computeWidgetLayout(int widthConstraint) {
	}

	public boolean layoutWidget(int widthConstraint, boolean force) {
		if((force || widgetChanged) || (widthConstraint != lastWidthConstraint)) {
			if(((force == false) && (widgetChanged == false)) && ((widthConstraint >= 0) && (widthConstraint == lastLayoutWidth))) {
				;
			}
			else {
				widgetChanged = false;
				computeWidgetLayout(widthConstraint);
				lastWidthConstraint = widthConstraint;
				lastLayoutWidth = cave.ui.Widget.getWidth((android.view.View)this);
			}
		}
		return(true);
	}

	private boolean isLayoutScheduled = false;

	public void scheduleLayout() {
		if(isLayoutScheduled) {
			return;
		}
		isLayoutScheduled = true;
		context.startTimer((long)0, new samx.function.Procedure0() {
			public void execute() {
				executeLayout();
			}
		});
	}

	public void executeLayout() {
		isLayoutScheduled = false;
		int ww = cave.ui.Widget.getWidth((android.view.View)this);
		if((ww == 0) && allowResize) {
			ww = -1;
		}
		cave.ui.Widget.layout((android.view.View)this, ww);
	}

	public boolean getAllowResize() {
		return(allowResize);
	}

	public CustomContainerWidget setAllowResize(boolean v) {
		allowResize = v;
		return(this);
	}

	public boolean getInitialized() {
		return(initialized);
	}

	public CustomContainerWidget setInitialized(boolean v) {
		initialized = v;
		return(this);
	}

	public boolean getWidgetChanged() {
		return(widgetChanged);
	}

	public CustomContainerWidget setWidgetChanged(boolean v) {
		widgetChanged = v;
		return(this);
	}
}
