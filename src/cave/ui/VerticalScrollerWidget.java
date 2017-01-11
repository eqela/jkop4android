
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

public class VerticalScrollerWidget extends android.widget.ScrollView implements WidgetWithLayout, HeightAwareWidget
{
	public static VerticalScrollerWidget forWidget(cave.GuiApplicationContext context, android.view.View widget) {
		VerticalScrollerWidget v = new VerticalScrollerWidget(context);
		v.addWidget(widget);
		return(v);
	}

	private int layoutHeight = -1;
	private boolean heightChanged = false;

	public VerticalScrollerWidget(cave.GuiApplicationContext context) {
		super(((cave.GuiApplicationContextForAndroid)context).getActivityContext());
		android.content.Context xx = ((cave.GuiApplicationContextForAndroid)context).getActivityContext();
		setFillViewport(true);
		setDescendantFocusability(android.view.ViewGroup.FOCUS_BEFORE_DESCENDANTS);
		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	public void onWidgetHeightChanged(int height) {
		java.util.ArrayList<android.view.View> array = cave.ui.Widget.getChildren((android.view.View)this);
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				android.view.View child = array.get(n);
				if(child != null) {
					if(height > layoutHeight) {
						cave.ui.Widget.resizeHeight(child, height);
					}
					else {
						cave.ui.Widget.resizeHeight(child, layoutHeight);
					}
				}
			}
		}
		heightChanged = true;
	}

	public boolean layoutWidget(int widthConstraint, boolean force) {
		int mw = 0;
		int mh = 0;
		java.util.ArrayList<android.view.View> array = cave.ui.Widget.getChildren((android.view.View)this);
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				android.view.View child = array.get(n);
				if(child != null) {
					cave.ui.Widget.move(child, 0, 0);
					cave.ui.Widget.layout(child, widthConstraint, heightChanged);
					int cw = cave.ui.Widget.getWidth(child);
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
		heightChanged = false;
		layoutHeight = mh;
		cave.ui.Widget.setLayoutSize((android.view.View)this, mw, mh);
		return(true);
	}

	public VerticalScrollerWidget addWidget(android.view.View widget) {
		cave.ui.Widget.addChild((android.view.View)this, widget);
		return(this);
	}
}
