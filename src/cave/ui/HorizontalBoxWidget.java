
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

public class HorizontalBoxWidget extends CustomContainerWidget
{
	private static class MyChildEntry
	{
		public android.view.View widget = null;
		public double weight = 0.00;
	}

	public static HorizontalBoxWidget forContext(cave.GuiApplicationContext context, int widgetMargin, int widgetSpacing) {
		HorizontalBoxWidget v = new HorizontalBoxWidget(context);
		v.widgetMarginLeft = widgetMargin;
		v.widgetMarginRight = widgetMargin;
		v.widgetMarginTop = widgetMargin;
		v.widgetMarginBottom = widgetMargin;
		v.widgetSpacing = widgetSpacing;
		return(v);
	}

	public static HorizontalBoxWidget forContext(cave.GuiApplicationContext context, int widgetMargin) {
		return(cave.ui.HorizontalBoxWidget.forContext(context, widgetMargin, 0));
	}

	public static HorizontalBoxWidget forContext(cave.GuiApplicationContext context) {
		return(cave.ui.HorizontalBoxWidget.forContext(context, 0));
	}

	private java.util.ArrayList<MyChildEntry> children = null;
	private int widgetSpacing = 0;
	protected int widgetMarginLeft = 0;
	protected int widgetMarginRight = 0;
	protected int widgetMarginTop = 0;
	protected int widgetMarginBottom = 0;
	private int fixedWidgetHeight = 0;

	public HorizontalBoxWidget(cave.GuiApplicationContext ctx) {
		super(ctx);
		children = new java.util.ArrayList<MyChildEntry>();
	}

	public HorizontalBoxWidget setWidgetMargin(int margin) {
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

	public HorizontalBoxWidget setWidgetMarginLeft(int value) {
		widgetMarginLeft = value;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetMarginRight() {
		return(widgetMarginRight);
	}

	public HorizontalBoxWidget setWidgetMarginRight(int value) {
		widgetMarginRight = value;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetMarginTop() {
		return(widgetMarginTop);
	}

	public HorizontalBoxWidget setWidgetMarginTop(int value) {
		widgetMarginTop = value;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetMarginBottom() {
		return(widgetMarginBottom);
	}

	public HorizontalBoxWidget setWidgetMarginBottom(int value) {
		widgetMarginBottom = value;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	@Override
	public void computeWidgetLayout(int widthConstraint) {
		double totalWeight = 0.00;
		int highest = 0;
		int availableWidth = (widthConstraint - widgetMarginLeft) - widgetMarginRight;
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
						cave.ui.Widget.layout(child.widget, -1);
						availableWidth -= cave.ui.Widget.getWidth(child.widget);
						int height = cave.ui.Widget.getHeight(child.widget);
						if(height > highest) {
							highest = height;
						}
					}
				}
			}
		}
		if((childCount > 1) && (widgetSpacing > 0)) {
			availableWidth -= (childCount - 1) * widgetSpacing;
		}
		if(children != null) {
			int n2 = 0;
			int m2 = children.size();
			for(n2 = 0 ; n2 < m2 ; n2++) {
				MyChildEntry child = children.get(n2);
				if(child != null) {
					if(child.weight > 0.00) {
						int ww = (int)((availableWidth * child.weight) / totalWeight);
						cave.ui.Widget.layout(child.widget, ww);
						int height = cave.ui.Widget.getHeight(child.widget);
						if(height > highest) {
							highest = height;
						}
					}
				}
			}
		}
		int realHighest = highest;
		highest += widgetMarginTop + widgetMarginBottom;
		if(fixedWidgetHeight > 0) {
			highest = fixedWidgetHeight;
		}
		if(widthConstraint < 0) {
			int totalWidth = widthConstraint - availableWidth;
			cave.ui.Widget.setLayoutSize((android.view.View)this, totalWidth, highest);
		}
		else {
			cave.ui.Widget.setLayoutSize((android.view.View)this, widthConstraint, highest);
		}
		if(availableWidth < 0) {
			availableWidth = 0;
		}
		int x = widgetMarginLeft;
		if(children != null) {
			int n3 = 0;
			int m3 = children.size();
			for(n3 = 0 ; n3 < m3 ; n3++) {
				MyChildEntry child = children.get(n3);
				if(child != null) {
					int ww = 0;
					if(child.weight > 0.00) {
						ww = (int)((availableWidth * child.weight) / totalWeight);
						cave.ui.Widget.move(child.widget, x, widgetMarginTop);
						cave.ui.Widget.layout(child.widget, ww);
						cave.ui.Widget.resizeHeight(child.widget, realHighest);
					}
					else {
						ww = cave.ui.Widget.getWidth(child.widget);
						cave.ui.Widget.move(child.widget, x, widgetMarginTop);
						cave.ui.Widget.layout(child.widget, ww);
						cave.ui.Widget.resizeHeight(child.widget, realHighest);
					}
					x += ww;
					x += widgetSpacing;
				}
			}
		}
	}

	@Override
	public void onWidgetHeightChanged(int height) {
		super.onWidgetHeightChanged(height);
		java.util.ArrayList<android.view.View> array = cave.ui.Widget.getChildren((android.view.View)this);
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				android.view.View child = array.get(n);
				if(child != null) {
					cave.ui.Widget.resizeHeight(child, (height - widgetMarginTop) - widgetMarginBottom);
				}
			}
		}
	}

	@Override
	public void onChildWidgetRemoved(android.view.View widget) {
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
		super.onChildWidgetRemoved(widget);
	}

	public HorizontalBoxWidget addWidget(android.view.View widget, double weight) {
		MyChildEntry ee = new MyChildEntry();
		ee.widget = widget;
		ee.weight = weight;
		children.add(ee);
		cave.ui.Widget.addChild((android.view.View)this, widget);
		return(this);
	}

	public HorizontalBoxWidget addWidget(android.view.View widget) {
		return(addWidget(widget, 0.00));
	}

	public int getWidgetSpacing() {
		return(widgetSpacing);
	}

	public HorizontalBoxWidget setWidgetSpacing(int v) {
		widgetSpacing = v;
		return(this);
	}

	public int getFixedWidgetHeight() {
		return(fixedWidgetHeight);
	}

	public HorizontalBoxWidget setFixedWidgetHeight(int v) {
		fixedWidgetHeight = v;
		return(this);
	}
}
