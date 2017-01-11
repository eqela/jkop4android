
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

public class Widget
{
	public static void onWidgetAddedToParent(android.view.View widget) {
		if(widget == null) {
			return;
		}
		if(widget instanceof CustomContainerWidget) {
			((CustomContainerWidget)widget).onWidgetAddedToParent();
		}
	}

	public static void onWidgetRemovedFromParent(android.view.View widget) {
		if(widget == null) {
			return;
		}
		if(widget instanceof CustomContainerWidget) {
			((CustomContainerWidget)widget).onWidgetRemovedFromParent();
		}
	}

	public static void notifyOnAddedToScreen(android.view.View widget, ScreenForWidget screen) {
		java.util.ArrayList<android.view.View> array = getChildren(widget);
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				android.view.View child = array.get(n);
				if(child != null) {
					notifyOnAddedToScreen(child, screen);
				}
			}
		}
		if(widget instanceof ScreenAwareWidget) {
			((ScreenAwareWidget)widget).onWidgetAddedToScreen(screen);
		}
	}

	public static void notifyOnRemovedFromScreen(android.view.View widget, ScreenForWidget screen) {
		if(widget instanceof ScreenAwareWidget) {
			((ScreenAwareWidget)widget).onWidgetRemovedFromScreen(screen);
		}
		java.util.ArrayList<android.view.View> array = getChildren(widget);
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				android.view.View child = array.get(n);
				if(child != null) {
					notifyOnRemovedFromScreen(child, screen);
				}
			}
		}
	}

	public static void addChild(android.view.View parent, android.view.View child) {
		if((parent == null) || (child == null)) {
			return;
		}
		CustomContainerWidget ccw = (CustomContainerWidget)((child instanceof CustomContainerWidget) ? child : null);
		if(ccw != null) {
			ccw.tryInitializeWidget();
		}
		android.view.ViewGroup vg = (android.view.ViewGroup)((parent instanceof android.view.ViewGroup) ? parent : null);
		if(vg == null) {
			return;
		}
		vg.addView(child);
		CustomContainerWidget pp = (CustomContainerWidget)((parent instanceof CustomContainerWidget) ? parent : null);
		if(pp != null) {
			pp.onChildWidgetAdded(child);
		}
		onWidgetAddedToParent(child);
		ScreenForWidget screen = cave.ui.ScreenForWidget.findScreenForWidget(child);
		if(screen != null) {
			notifyOnAddedToScreen(child, screen);
		}
	}

	public static android.view.View removeFromParent(android.view.View child) {
		if(child == null) {
			return(null);
		}
		android.view.View parentWidget = getParent(child);
		if(parentWidget == null) {
			return(null);
		}
		CustomContainerWidget pp = (CustomContainerWidget)((parentWidget instanceof CustomContainerWidget) ? parentWidget : null);
		if(parentWidget != null && parentWidget instanceof android.view.ViewGroup) {
			((android.view.ViewGroup)parentWidget).removeView(child);
		}
		if(pp != null) {
			pp.onChildWidgetRemoved(child);
		}
		ScreenForWidget screen = cave.ui.ScreenForWidget.findScreenForWidget(parentWidget);
		if(screen != null) {
			notifyOnRemovedFromScreen(child, screen);
		}
		onWidgetRemovedFromParent(child);
		return(null);
	}

	public static boolean hasParent(android.view.View widget) {
		if(getParent(widget) == null) {
			return(false);
		}
		return(true);
	}

	public static android.view.View getParent(android.view.View widget) {
		if(widget == null) {
			return(null);
		}
		android.view.View v = null;
		android.view.ViewParent vp = widget.getParent();
		if(vp != null && vp instanceof android.view.ViewGroup) {
			v = (android.view.ViewGroup)vp;
		}
		return(v);
	}

	public static java.util.ArrayList<android.view.View> getChildren(android.view.View widget) {
		java.util.ArrayList<android.view.View> v = new java.util.ArrayList<android.view.View>();
		if(widget instanceof android.view.ViewGroup) {
			android.view.ViewGroup vg = (android.view.ViewGroup)widget;
			int n;
			for(n=0; n<vg.getChildCount(); n++) {
				v.add(vg.getChildAt(n));
			}
		}
		return(v);
	}

	public static int getX(android.view.View widget) {
		if(widget == null) {
			return(0);
		}
		int v = 0;
		v = (int)widget.getX();
		return(v);
	}

	public static int getY(android.view.View widget) {
		if(widget == null) {
			return(0);
		}
		int v = 0;
		v = (int)widget.getY();
		return(v);
	}

	public static int getWidth(android.view.View widget) {
		if(widget == null) {
			return(0);
		}
		int v = 0;
		v = widget.getWidth();
		return(v);
	}

	public static int getHeight(android.view.View widget) {
		if(widget == null) {
			return(0);
		}
		int v = 0;
		v = widget.getHeight();
		return(v);
	}

	public static void move(android.view.View widget, int x, int y) {
		widget.layout(x, y, x+widget.getWidth(), y+widget.getHeight());
	}

	public static boolean isRootWidget(android.view.View widget) {
		CustomContainerWidget cw = (CustomContainerWidget)((widget instanceof CustomContainerWidget) ? widget : null);
		if(cw == null) {
			return(false);
		}
		android.view.View pp = getParent((android.view.View)cw);
		if(pp == null) {
			return(true);
		}
		if(pp instanceof WidgetWithLayout) {
			return(false);
		}
		return(true);
	}

	public static android.app.Activity findScreen(android.view.View widget) {
		android.view.View view = widget;
		android.app.Activity scr = null;
		if(view == null) {
			return(null);
		}
		android.content.Context ctx = view.getContext();
		if(ctx != null && ctx instanceof android.app.Activity) {
			scr = (android.app.Activity)ctx;
		}
		return((android.app.Activity)((scr instanceof android.app.Activity) ? scr : null));
	}

	public static CustomContainerWidget findRootWidget(android.view.View widget) {
		android.view.View v = widget;
		while(true) {
			if(v == null) {
				break;
			}
			if(isRootWidget(v)) {
				return((CustomContainerWidget)((v instanceof CustomContainerWidget) ? v : null));
			}
			v = getParent(v);
		}
		return(null);
	}

	public static boolean setLayoutSize(android.view.View widget, int width, int height) {
		if(isRootWidget(widget)) {
			CustomContainerWidget ccw = (CustomContainerWidget)((widget instanceof CustomContainerWidget) ? widget : null);
			if((ccw != null) && (ccw.getAllowResize() == false)) {
				return(false);
			}
		}
		if((getWidth(widget) == width) && (getHeight(widget) == height)) {
			return(false);
		}
		int x = (int)widget.getX();
		int y = (int)widget.getY();
		widget.layout(x, y, x+width, y+height);
		if(widget instanceof ResizeAwareWidget) {
			((ResizeAwareWidget)widget).onWidgetResized();
		}
		return(true);
	}

	public static boolean resizeHeight(android.view.View widget, int height) {
		if(setLayoutSize(widget, getWidth(widget), height) == false) {
			return(false);
		}
		if(widget instanceof HeightAwareWidget) {
			((HeightAwareWidget)widget).onWidgetHeightChanged(height);
		}
		return(true);
	}

	public static void layout(android.view.View widget, int widthConstraint, boolean force) {
		if(widget == null) {
			return;
		}
		boolean done = false;
		if(widget instanceof WidgetWithLayout) {
			done = ((WidgetWithLayout)widget).layoutWidget(widthConstraint, force);
		}
		if(done == false) {
			int srw = 0;
			int srh = 0;
			int msw = android.view.View.MeasureSpec.UNSPECIFIED;
			int msh = android.view.View.MeasureSpec.UNSPECIFIED;
			if(widthConstraint >= 0) {
				msw = android.view.View.MeasureSpec.makeMeasureSpec(widthConstraint, android.view.View.MeasureSpec.EXACTLY);
			}
			widget.measure(msw, msh);
			srw = widget.getMeasuredWidth();
			srh = widget.getMeasuredHeight();
			if((widthConstraint >= 0) && (srw < widthConstraint)) {
				srw = widthConstraint;
			}
			setLayoutSize(widget, srw, srh);
		}
	}

	public static void layout(android.view.View widget, int widthConstraint) {
		cave.ui.Widget.layout(widget, widthConstraint, false);
	}

	public static void setWidgetClickHandler(android.view.View widget, samx.function.Procedure0 handler) {
		if(widget instanceof CustomContainerWidget) {
			if(handler == null) {
				((CustomContainerWidget)widget).togglePointerEventHandling(false);
			}
			else {
				((CustomContainerWidget)widget).togglePointerEventHandling(true);
			}
		}
		final samx.function.Procedure0 hf = handler;
		if(handler == null) {
			widget.setOnClickListener(null);
		}
		else {
			widget.setOnClickListener(new android.view.View.OnClickListener() {
				public void onClick(android.view.View view) {
					hf.execute();
				}
			});
		}
	}

	public static void removeChildrenOf(android.view.View widget) {
		java.util.ArrayList<android.view.View> children = getChildren(widget);
		if(children != null) {
			int n = 0;
			int m = children.size();
			for(n = 0 ; n < m ; n++) {
				android.view.View child = children.get(n);
				if(child != null) {
					removeFromParent(child);
				}
			}
		}
	}

	public static void onChanged(android.view.View widget) {
		if(widget == null) {
			return;
		}
		CustomContainerWidget ccw = (CustomContainerWidget)((widget instanceof CustomContainerWidget) ? widget : null);
		if((ccw != null) && ccw.getWidgetChanged()) {
			return;
		}
		if(isRootWidget(widget)) {
			((CustomContainerWidget)widget).scheduleLayout();
		}
		else {
			android.view.View tpp = getParent(widget);
			android.view.View pp = null;
			if(tpp instanceof android.view.View) {
				pp = (android.view.View)tpp;
			}
			if(pp != null) {
				onChanged(pp);
			}
			else {
				CustomContainerWidget root = findRootWidget(widget);
				if(root != null) {
					root.scheduleLayout();
				}
			}
		}
		if(ccw != null) {
			ccw.setWidgetChanged(true);
		}
	}

	public static void setAlpha(android.view.View widget, double alpha) {
		if(widget == null) {
			return;
		}
		widget.setAlpha((float)alpha);
	}
}
