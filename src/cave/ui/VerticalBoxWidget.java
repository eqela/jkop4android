
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

public class VerticalBoxWidget extends CustomContainerWidget
{
	private static class MyChildEntry
	{
		public android.view.View widget = null;
		public double weight = 0.00;
	}

	public static VerticalBoxWidget forContext(cave.GuiApplicationContext context, int widgetMargin, int widgetSpacing) {
		VerticalBoxWidget v = new VerticalBoxWidget(context);
		v.widgetMarginLeft = widgetMargin;
		v.widgetMarginRight = widgetMargin;
		v.widgetMarginTop = widgetMargin;
		v.widgetMarginBottom = widgetMargin;
		v.widgetSpacing = widgetSpacing;
		return(v);
	}

	public static VerticalBoxWidget forContext(cave.GuiApplicationContext context, int widgetMargin) {
		return(cave.ui.VerticalBoxWidget.forContext(context, widgetMargin, 0));
	}

	public static VerticalBoxWidget forContext(cave.GuiApplicationContext context) {
		return(cave.ui.VerticalBoxWidget.forContext(context, 0));
	}

	private java.util.ArrayList<MyChildEntry> children = null;
	private int widgetSpacing = 0;
	protected int widgetMarginLeft = 0;
	protected int widgetMarginRight = 0;
	protected int widgetMarginTop = 0;
	protected int widgetMarginBottom = 0;
	private int widgetWidthRequest = 0;
	private int widgetMaximumWidthRequest = 0;

	public VerticalBoxWidget(cave.GuiApplicationContext ctx) {
		super(ctx);
		children = new java.util.ArrayList<MyChildEntry>();
	}

	public VerticalBoxWidget setWidgetMargin(int margin) {
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

	public VerticalBoxWidget setWidgetMarginLeft(int value) {
		widgetMarginLeft = value;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetMarginRight() {
		return(widgetMarginRight);
	}

	public VerticalBoxWidget setWidgetMarginRight(int value) {
		widgetMarginRight = value;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetMarginTop() {
		return(widgetMarginTop);
	}

	public VerticalBoxWidget setWidgetMarginTop(int value) {
		widgetMarginTop = value;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetMarginBottom() {
		return(widgetMarginBottom);
	}

	public VerticalBoxWidget setWidgetMarginBottom(int value) {
		widgetMarginBottom = value;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public VerticalBoxWidget setWidgetWidthRequest(int request) {
		widgetWidthRequest = request;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetWidthRequest() {
		return(widgetWidthRequest);
	}

	public VerticalBoxWidget setWidgetMaximumWidthRequest(int width) {
		widgetMaximumWidthRequest = width;
		if(cave.ui.Widget.getWidth((android.view.View)this) > width) {
			cave.ui.Widget.onChanged((android.view.View)this);
		}
		return(this);
	}

	public int getWidgetMaximumWidthRequest() {
		return(widgetMaximumWidthRequest);
	}

	@Override
	public void computeWidgetLayout(int widthConstraint) {
		int wc = -1;
		if((widthConstraint < 0) && (widgetWidthRequest > 0)) {
			wc = (widgetWidthRequest - widgetMarginLeft) - widgetMarginRight;
		}
		if((wc < 0) && (widthConstraint >= 0)) {
			wc = (widthConstraint - widgetMarginLeft) - widgetMarginRight;
			if(wc < 0) {
				wc = 0;
			}
		}
		int widest = 0;
		int childCount = 0;
		int y = widgetMarginTop;
		if(wc < 0) {
			if(children != null) {
				int n = 0;
				int m = children.size();
				for(n = 0 ; n < m ; n++) {
					MyChildEntry child = children.get(n);
					if(child != null) {
						cave.ui.Widget.layout(child.widget, -1);
						int ww = cave.ui.Widget.getWidth(child.widget);
						if(ww > wc) {
							wc = ww;
						}
					}
				}
			}
		}
		if(children != null) {
			int n2 = 0;
			int m2 = children.size();
			for(n2 = 0 ; n2 < m2 ; n2++) {
				MyChildEntry child = children.get(n2);
				if(child != null) {
					if(childCount > 0) {
						y += widgetSpacing;
					}
					childCount++;
					cave.ui.Widget.layout(child.widget, wc);
					cave.ui.Widget.move(child.widget, widgetMarginLeft, y);
					int ww = cave.ui.Widget.getWidth(child.widget);
					if(((wc < 0) && (widgetMaximumWidthRequest > 0)) && (((ww + widgetMarginLeft) + widgetMarginRight) > widgetMaximumWidthRequest)) {
						cave.ui.Widget.layout(child.widget, (widgetMaximumWidthRequest - widgetMarginLeft) - widgetMarginRight);
						ww = cave.ui.Widget.getWidth(child.widget);
					}
					if(ww > widest) {
						widest = ww;
					}
					y += cave.ui.Widget.getHeight(child.widget);
				}
			}
		}
		y += widgetMarginBottom;
		int mywidth = (widest + widgetMarginLeft) + widgetMarginRight;
		if(widthConstraint >= 0) {
			mywidth = widthConstraint;
		}
		cave.ui.Widget.setLayoutSize((android.view.View)this, mywidth, y);
		onWidgetHeightChanged(y);
	}

	@Override
	public void onWidgetHeightChanged(int height) {
		super.onWidgetHeightChanged(height);
		double totalWeight = 0.00;
		int availableHeight = (height - widgetMarginTop) - widgetMarginBottom;
		int childCount = 0;
		if(children != null) {
			int n = 0;
			int m = children.size();
			for(n = 0 ; n < m ; n++) {
				MyChildEntry child = children.get(n);
				if(child != null) {
					childCount++;
					if(child.weight > 0.00) {
						totalWeight += child.weight;
					}
					else {
						availableHeight -= cave.ui.Widget.getHeight(child.widget);
					}
				}
			}
		}
		if((childCount > 1) && (widgetSpacing > 0)) {
			availableHeight -= (childCount - 1) * widgetSpacing;
		}
		if(availableHeight < 0) {
			availableHeight = 0;
		}
		int y = widgetMarginTop;
		if(children != null) {
			int n2 = 0;
			int m2 = children.size();
			for(n2 = 0 ; n2 < m2 ; n2++) {
				MyChildEntry child = children.get(n2);
				if(child != null) {
					cave.ui.Widget.move(child.widget, widgetMarginLeft, y);
					if(child.weight > 0.00) {
						int hh = (int)((availableHeight * child.weight) / totalWeight);
						cave.ui.Widget.resizeHeight(child.widget, hh);
					}
					int hh = cave.ui.Widget.getHeight(child.widget);
					y += hh;
					y += widgetSpacing;
				}
			}
		}
	}

	@Override
	public void onChildWidgetRemoved(android.view.View widget) {
		if(widget != null) {
			if(children != null) {
				int n = 0;
				int m = children.size();
				for(n = 0 ; n < m ; n++) {
					MyChildEntry child = children.get(n);
					if(child != null) {
						if(child.widget == widget) {
							cape.Vector.removeValue(children, child);
							break;
						}
					}
				}
			}
		}
		super.onChildWidgetRemoved(widget);
	}

	public VerticalBoxWidget addWidget(android.view.View widget, double weight) {
		if(widget != null) {
			MyChildEntry ee = new MyChildEntry();
			ee.widget = widget;
			ee.weight = weight;
			children.add(ee);
			cave.ui.Widget.addChild((android.view.View)this, widget);
		}
		return(this);
	}

	public VerticalBoxWidget addWidget(android.view.View widget) {
		return(addWidget(widget, 0.00));
	}

	public int getWidgetSpacing() {
		return(widgetSpacing);
	}

	public VerticalBoxWidget setWidgetSpacing(int v) {
		widgetSpacing = v;
		return(this);
	}
}
