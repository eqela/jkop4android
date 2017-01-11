
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

public class LayerWidget extends CustomContainerWidget
{
	public static LayerWidget findTopMostLayerWidget(android.view.View widget) {
		LayerWidget v = null;
		android.view.View pp = widget;
		while(pp != null) {
			if(pp instanceof LayerWidget) {
				v = (LayerWidget)pp;
			}
			pp = cave.ui.Widget.getParent(pp);
		}
		return(v);
	}

	public static LayerWidget forContext(cave.GuiApplicationContext context) {
		LayerWidget v = new LayerWidget(context);
		return(v);
	}

	public static LayerWidget forMargin(cave.GuiApplicationContext context, int margin) {
		LayerWidget v = new LayerWidget(context);
		v.setWidgetMargin(margin);
		return(v);
	}

	public static LayerWidget forWidget(cave.GuiApplicationContext context, android.view.View widget, int margin) {
		LayerWidget v = new LayerWidget(context);
		v.setWidgetMargin(margin);
		v.addWidget(widget);
		return(v);
	}

	public static LayerWidget forWidget(cave.GuiApplicationContext context, android.view.View widget) {
		return(cave.ui.LayerWidget.forWidget(context, widget, 0));
	}

	public static LayerWidget forWidgetAndWidth(cave.GuiApplicationContext context, android.view.View widget, int width) {
		LayerWidget v = new LayerWidget(context);
		v.addWidget(widget);
		v.setWidgetWidthRequest(width);
		return(v);
	}

	public static LayerWidget forWidgets(cave.GuiApplicationContext context, android.view.View[] widgets, int margin) {
		LayerWidget v = new LayerWidget(context);
		v.setWidgetMargin(margin);
		if(widgets != null) {
			int n = 0;
			int m = widgets.length;
			for(n = 0 ; n < m ; n++) {
				android.view.View widget = widgets[n];
				if(widget != null) {
					v.addWidget(widget);
				}
			}
		}
		return(v);
	}

	public static LayerWidget forWidgets(cave.GuiApplicationContext context, android.view.View[] widgets) {
		return(cave.ui.LayerWidget.forWidgets(context, widgets, 0));
	}

	protected int widgetMarginLeft = 0;
	protected int widgetMarginRight = 0;
	protected int widgetMarginTop = 0;
	protected int widgetMarginBottom = 0;
	private int widgetWidthRequest = 0;
	private int widgetHeightRequest = 0;
	private int widgetMinimumHeightRequest = 0;
	private int widgetMaximumWidthRequest = 0;

	public LayerWidget setWidgetMaximumWidthRequest(int width) {
		widgetMaximumWidthRequest = width;
		if(cave.ui.Widget.getWidth((android.view.View)this) > width) {
			cave.ui.Widget.onChanged((android.view.View)this);
		}
		return(this);
	}

	public int getWidgetMaximumWidthRequest() {
		return(widgetMaximumWidthRequest);
	}

	public LayerWidget setWidgetWidthRequest(int request) {
		widgetWidthRequest = request;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetWidthRequest() {
		return(widgetWidthRequest);
	}

	public LayerWidget setWidgetHeightRequest(int request) {
		widgetHeightRequest = request;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetHeightRequest() {
		return(widgetHeightRequest);
	}

	public LayerWidget setWidgetMinimumHeightRequest(int request) {
		widgetMinimumHeightRequest = request;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetMinimumHeightRequest() {
		return(widgetMinimumHeightRequest);
	}

	public LayerWidget setWidgetMargin(int margin) {
		widgetMarginLeft = margin;
		widgetMarginRight = margin;
		widgetMarginTop = margin;
		widgetMarginBottom = margin;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetMarginLeft() {
		return(widgetMarginLeft);
	}

	public LayerWidget setWidgetMarginLeft(int value) {
		widgetMarginLeft = value;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetMarginRight() {
		return(widgetMarginRight);
	}

	public LayerWidget setWidgetMarginRight(int value) {
		widgetMarginRight = value;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetMarginTop() {
		return(widgetMarginTop);
	}

	public LayerWidget setWidgetMarginTop(int value) {
		widgetMarginTop = value;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetMarginBottom() {
		return(widgetMarginBottom);
	}

	public LayerWidget setWidgetMarginBottom(int value) {
		widgetMarginBottom = value;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public LayerWidget(cave.GuiApplicationContext ctx) {
		super(ctx);
	}

	@Override
	public void onWidgetHeightChanged(int height) {
		super.onWidgetHeightChanged(height);
		int mh = (height - widgetMarginTop) - widgetMarginBottom;
		java.util.ArrayList<android.view.View> array = cave.ui.Widget.getChildren((android.view.View)this);
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				android.view.View child = array.get(n);
				if(child != null) {
					cave.ui.Widget.resizeHeight(child, mh);
				}
			}
		}
	}

	@Override
	public void computeWidgetLayout(int widthConstraint) {
		int wc = widthConstraint;
		if((wc < 0) && (widgetWidthRequest > 0)) {
			wc = widgetWidthRequest;
		}
		if(wc >= 0) {
			wc = (wc - widgetMarginLeft) - widgetMarginRight;
			if(wc < 0) {
				wc = 0;
			}
		}
		int mw = 0;
		int mh = 0;
		java.util.ArrayList<android.view.View> array = cave.ui.Widget.getChildren((android.view.View)this);
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				android.view.View child = array.get(n);
				if(child != null) {
					cave.ui.Widget.layout(child, wc);
					cave.ui.Widget.move(child, widgetMarginLeft, widgetMarginTop);
					int cw = cave.ui.Widget.getWidth(child);
					if(((wc < 0) && (widgetMaximumWidthRequest > 0)) && (((cw + widgetMarginLeft) + widgetMarginRight) > widgetMaximumWidthRequest)) {
						cave.ui.Widget.layout(child, (widgetMaximumWidthRequest - widgetMarginLeft) - widgetMarginRight);
						cw = cave.ui.Widget.getWidth(child);
					}
					int ch = cave.ui.Widget.getHeight(child);
					if(cw > mw) {
						mw = cw;
					}
					if(ch > mh) {
						mh = ch;
					}
				}
			}
		}
		int fw = widthConstraint;
		if(fw < 0) {
			fw = (mw + widgetMarginLeft) + widgetMarginRight;
		}
		int fh = (mh + widgetMarginTop) + widgetMarginBottom;
		if(widgetHeightRequest > 0) {
			fh = widgetHeightRequest;
		}
		if((widgetMinimumHeightRequest > 0) && (fh < widgetMinimumHeightRequest)) {
			fh = widgetMinimumHeightRequest;
		}
		cave.ui.Widget.setLayoutSize((android.view.View)this, fw, fh);
		mw = (cave.ui.Widget.getWidth((android.view.View)this) - widgetMarginLeft) - widgetMarginRight;
		mh = (cave.ui.Widget.getHeight((android.view.View)this) - widgetMarginTop) - widgetMarginBottom;
		java.util.ArrayList<android.view.View> array2 = cave.ui.Widget.getChildren((android.view.View)this);
		if(array2 != null) {
			int n2 = 0;
			int m2 = array2.size();
			for(n2 = 0 ; n2 < m2 ; n2++) {
				android.view.View child = array2.get(n2);
				if(child != null) {
					if(wc < 0) {
						cave.ui.Widget.layout(child, mw);
					}
					cave.ui.Widget.resizeHeight(child, mh);
				}
			}
		}
	}

	public void removeAllChildren() {
		cave.ui.Widget.removeChildrenOf((android.view.View)this);
	}

	public android.view.View getChildWidget(int index) {
		java.util.ArrayList<android.view.View> children = cave.ui.Widget.getChildren((android.view.View)this);
		if(children == null) {
			return(null);
		}
		return(cape.Vector.get(children, index));
	}
}
