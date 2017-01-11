
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

public class SwitcherLayerWidget extends CustomContainerWidget
{
	public static SwitcherLayerWidget findTopMostLayerWidget(android.view.View widget) {
		SwitcherLayerWidget v = null;
		android.view.View pp = widget;
		while(pp != null) {
			if(pp instanceof SwitcherLayerWidget) {
				v = (SwitcherLayerWidget)pp;
			}
			pp = cave.ui.Widget.getParent(pp);
		}
		return(v);
	}

	public static SwitcherLayerWidget forMargin(cave.GuiApplicationContext context, int margin) {
		SwitcherLayerWidget v = new SwitcherLayerWidget(context);
		v.margin = margin;
		return(v);
	}

	public static SwitcherLayerWidget forWidget(cave.GuiApplicationContext context, android.view.View widget, int margin) {
		SwitcherLayerWidget v = new SwitcherLayerWidget(context);
		v.margin = margin;
		v.addWidget(widget);
		return(v);
	}

	public static SwitcherLayerWidget forWidget(cave.GuiApplicationContext context, android.view.View widget) {
		return(cave.ui.SwitcherLayerWidget.forWidget(context, widget, 0));
	}

	public static SwitcherLayerWidget forWidgets(cave.GuiApplicationContext context, android.view.View[] widgets, int margin) {
		SwitcherLayerWidget v = new SwitcherLayerWidget(context);
		v.margin = margin;
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

	public static SwitcherLayerWidget forWidgets(cave.GuiApplicationContext context, android.view.View[] widgets) {
		return(cave.ui.SwitcherLayerWidget.forWidgets(context, widgets, 0));
	}

	private int margin = 0;

	public SwitcherLayerWidget(cave.GuiApplicationContext ctx) {
		super(ctx);
	}

	@Override
	public void onWidgetHeightChanged(int height) {
		super.onWidgetHeightChanged(height);
		java.util.ArrayList<android.view.View> children = cave.ui.Widget.getChildren((android.view.View)this);
		if(children != null) {
			android.view.View topmost = cape.Vector.get(children, cape.Vector.getSize(children) - 1);
			if(topmost != null) {
				cave.ui.Widget.resizeHeight(topmost, (height - margin) - margin);
			}
		}
	}

	@Override
	public void computeWidgetLayout(int widthConstraint) {
		java.util.ArrayList<android.view.View> children = cave.ui.Widget.getChildren((android.view.View)this);
		if(children == null) {
			return;
		}
		int childCount = cape.Vector.getSize(children);
		int wc = -1;
		if(widthConstraint >= 0) {
			wc = (widthConstraint - margin) - margin;
			if(wc < 0) {
				wc = 0;
			}
		}
		int mw = 0;
		int mh = 0;
		int n = 0;
		java.util.ArrayList<android.view.View> array = cave.ui.Widget.getChildren((android.view.View)this);
		if(array != null) {
			int n2 = 0;
			int m = array.size();
			for(n2 = 0 ; n2 < m ; n2++) {
				android.view.View child = array.get(n2);
				if(child != null) {
					if(n == (childCount - 1)) {
						cave.ui.Widget.layout(child, wc);
						mw = cave.ui.Widget.getWidth(child);
						mh = cave.ui.Widget.getHeight(child);
						cave.ui.Widget.move(child, margin, margin);
					}
					else {
						int ww = cave.ui.Widget.getWidth(child);
						cave.ui.Widget.move(child, -ww, margin);
					}
					n++;
				}
			}
		}
		cave.ui.Widget.setLayoutSize((android.view.View)this, (mw + margin) + margin, (mh + margin) + margin);
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
