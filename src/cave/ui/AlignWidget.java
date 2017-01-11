
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

public class AlignWidget extends CustomContainerWidget
{
	private static class MyChildEntry
	{
		public android.view.View widget = null;
		public double alignX = 0.00;
		public double alignY = 0.00;
	}

	public static AlignWidget forWidget(cave.GuiApplicationContext context, android.view.View widget, double alignX, double alignY, int margin) {
		AlignWidget v = new AlignWidget(context);
		v.widgetMarginLeft = margin;
		v.widgetMarginRight = margin;
		v.widgetMarginTop = margin;
		v.widgetMarginBottom = margin;
		v.addWidget(widget, alignX, alignY);
		return(v);
	}

	public static AlignWidget forWidget(cave.GuiApplicationContext context, android.view.View widget, double alignX, double alignY) {
		return(cave.ui.AlignWidget.forWidget(context, widget, alignX, alignY, 0));
	}

	public static AlignWidget forWidget(cave.GuiApplicationContext context, android.view.View widget, double alignX) {
		return(cave.ui.AlignWidget.forWidget(context, widget, alignX, 0.50));
	}

	public static AlignWidget forWidget(cave.GuiApplicationContext context, android.view.View widget) {
		return(cave.ui.AlignWidget.forWidget(context, widget, 0.50));
	}

	private java.util.ArrayList<MyChildEntry> children = null;
	private int widgetMarginLeft = 0;
	private int widgetMarginRight = 0;
	private int widgetMarginTop = 0;
	private int widgetMarginBottom = 0;

	public AlignWidget(cave.GuiApplicationContext ctx) {
		super(ctx);
		children = new java.util.ArrayList<MyChildEntry>();
	}

	public AlignWidget setWidgetMargin(int margin) {
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

	public AlignWidget setWidgetMarginLeft(int value) {
		widgetMarginLeft = value;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetMarginRight() {
		return(widgetMarginRight);
	}

	public AlignWidget setWidgetMarginRight(int value) {
		widgetMarginRight = value;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetMarginTop() {
		return(widgetMarginTop);
	}

	public AlignWidget setWidgetMarginTop(int value) {
		widgetMarginTop = value;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public int getWidgetMarginBottom() {
		return(widgetMarginBottom);
	}

	public AlignWidget setWidgetMarginBottom(int value) {
		widgetMarginBottom = value;
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	@Override
	public void onWidgetHeightChanged(int height) {
		super.onWidgetHeightChanged(height);
		updateChildWidgetLocations();
	}

	private void updateChildWidgetLocations() {
		if(children != null) {
			int n = 0;
			int m = children.size();
			for(n = 0 ; n < m ; n++) {
				MyChildEntry child = children.get(n);
				if(child != null) {
					handleEntry(child);
				}
			}
		}
	}

	@Override
	public void computeWidgetLayout(int widthConstraint) {
		int wc = -1;
		if(widthConstraint >= 0) {
			wc = (widthConstraint - widgetMarginLeft) - widgetMarginRight;
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
					cave.ui.Widget.layout(child, -1);
					int cw = cave.ui.Widget.getWidth(child);
					if((wc >= 0) && (cw > wc)) {
						cave.ui.Widget.layout(child, wc);
						cw = cave.ui.Widget.getWidth(child);
					}
					if(cw > mw) {
						mw = cw;
					}
					int ch = cave.ui.Widget.getHeight(child);
					if(ch > mh) {
						mh = ch;
					}
				}
			}
		}
		int mywidth = (mw + widgetMarginLeft) + widgetMarginRight;
		if(widthConstraint >= 0) {
			mywidth = widthConstraint;
		}
		cave.ui.Widget.setLayoutSize((android.view.View)this, mywidth, (mh + widgetMarginTop) + widgetMarginBottom);
		updateChildWidgetLocations();
	}

	private void handleEntry(MyChildEntry entry) {
		int w = (cave.ui.Widget.getWidth((android.view.View)this) - widgetMarginLeft) - widgetMarginRight;
		int h = (cave.ui.Widget.getHeight((android.view.View)this) - widgetMarginTop) - widgetMarginBottom;
		int cw = cave.ui.Widget.getWidth(entry.widget);
		int ch = cave.ui.Widget.getHeight(entry.widget);
		if((cw > w) || (ch > h)) {
			if(cw > w) {
				cw = w;
			}
			if(ch > h) {
				ch = h;
			}
		}
		int dx = (int)(widgetMarginLeft + ((w - cw) * entry.alignX));
		int dy = (int)(widgetMarginTop + ((h - ch) * entry.alignY));
		cave.ui.Widget.move(entry.widget, dx, dy);
	}

	@Override
	public void onChildWidgetRemoved(android.view.View widget) {
		super.onChildWidgetRemoved(widget);
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

	public AlignWidget addWidget(android.view.View widget, double alignX, double alignY) {
		MyChildEntry ee = new MyChildEntry();
		ee.widget = widget;
		ee.alignX = alignX;
		ee.alignY = alignY;
		children.add(ee);
		cave.ui.Widget.addChild((android.view.View)this, widget);
		if(hasSize()) {
			handleEntry(ee);
		}
		return(this);
	}

	public void setAlignForIndex(int index, double alignX, double alignY) {
		MyChildEntry child = cape.Vector.get(children, index);
		if(child == null) {
			return;
		}
		if((child.alignX != alignX) || (child.alignY != alignY)) {
			child.alignX = alignX;
			child.alignY = alignY;
			cave.ui.Widget.onChanged((android.view.View)this);
		}
	}
}
