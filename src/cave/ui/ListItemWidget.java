
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

abstract public class ListItemWidget extends VerticalBoxWidget implements TitledWidget, DisplayAwareWidget
{
	private VerticalBoxWidget list = null;

	public ListItemWidget(cave.GuiApplicationContext ctx) {
		super(ctx);
	}

	public java.lang.String getWidgetTitle() {
		return(null);
	}

	public void onWidgetDisplayed() {
		onGetData();
	}

	@Override
	public void initializeWidget() {
		super.initializeWidget();
		android.view.View shw = getSubHeaderWidget();
		if(shw != null) {
			addWidget(shw);
		}
		setWidgetMargin(context.getHeightValue("1mm"));
		setWidgetSpacing(context.getHeightValue("1mm"));
		list = forContext(context, 0, context.getHeightValue("1mm"));
		addWidget((android.view.View)cave.ui.VerticalScrollerWidget.forWidget(context, (android.view.View)list), 1.00);
	}

	public void onGetData() {
		cave.ui.Widget.removeChildrenOf((android.view.View)list);
		startDataQuery(new samx.function.Procedure1<java.util.ArrayList<cape.DynamicMap>>() {
			public void execute(java.util.ArrayList<cape.DynamicMap> response) {
				if((response == null) || (cape.Vector.getSize(response) < 1)) {
					onNoDataReceived();
					return;
				}
				onDataReceived(response);
			}
		});
	}

	public void onNoDataReceived() {
		list.addWidget((android.view.View)cave.ui.AlignWidget.forWidget(context, (android.view.View)cave.ui.LabelWidget.forText(context, "No data found"), 0.50, 0.50));
	}

	public void onDataReceived(java.util.ArrayList<cape.DynamicMap> data) {
		if(data != null) {
			int n = 0;
			int m = data.size();
			for(n = 0 ; n < m ; n++) {
				cape.DynamicMap record = data.get(n);
				if(record != null) {
					android.view.View widget = createWidgetForRecord(record);
					if(widget != null) {
						list.addWidget(widget);
					}
				}
			}
		}
	}

	public android.view.View getSubHeaderWidget() {
		return(null);
	}

	public android.view.View createWidgetForRecord(cape.DynamicMap record) {
		return(null);
	}

	public abstract void startDataQuery(samx.function.Procedure1<java.util.ArrayList<cape.DynamicMap>> callback);
}
