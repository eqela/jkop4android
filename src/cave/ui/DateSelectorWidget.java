
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

public class DateSelectorWidget extends LayerWidget implements WidgetWithValue
{
	public static DateSelectorWidget forContext(cave.GuiApplicationContext context) {
		DateSelectorWidget v = new DateSelectorWidget(context);
		return(v);
	}

	public DateSelectorWidget(cave.GuiApplicationContext ctx) {
		super(ctx);
		widgetContext = ctx;
	}

	private cave.GuiApplicationContext widgetContext = null;
	private SelectWidget dayBox = null;
	private SelectWidget monthBox = null;
	private SelectWidget yearBox = null;
	private java.lang.String value = null;
	private int skipYears = 0;

	@Override
	public void initializeWidget() {
		super.initializeWidget();
		dayBox = cave.ui.SelectWidget.forStringList(context, new java.lang.String[] {
			"1",
			"2",
			"3",
			"4",
			"5",
			"6",
			"7",
			"8",
			"9",
			"10",
			"11",
			"12",
			"13",
			"14",
			"15",
			"16",
			"17",
			"18",
			"19",
			"20",
			"21",
			"22",
			"23",
			"24",
			"25",
			"26",
			"27",
			"28",
			"29",
			"30",
			"31"
		});
		monthBox = cave.ui.SelectWidget.forKeyValueStrings(context, new java.lang.String[] {
			"1:January",
			"2:February",
			"3:March",
			"4:April",
			"5:May",
			"6:June",
			"7:July",
			"8:August",
			"9:September",
			"10:October",
			"11:November",
			"12:December"
		});
		int cy = cape.DateTime.forNow().getYear();
		if(cy < 1) {
			cy = 2016;
		}
		cy -= skipYears;
		cape.KeyValueList<java.lang.String, java.lang.String> yearOptions = new cape.KeyValueList<java.lang.String, java.lang.String>();
		for(int i = cy ; i >= 1920 ; i--) {
			java.lang.String str = cape.String.forInteger(i);
			yearOptions.add((java.lang.String)str, (java.lang.String)str);
		}
		yearBox = cave.ui.SelectWidget.forKeyValueList(context, yearOptions);
		HorizontalBoxWidget box = cave.ui.HorizontalBoxWidget.forContext(context);
		box.setWidgetSpacing(context.getHeightValue("1mm"));
		box.addWidget((android.view.View)dayBox, (double)1);
		box.addWidget((android.view.View)monthBox, (double)2);
		box.addWidget((android.view.View)yearBox, (double)1);
		addWidget((android.view.View)box);
		applyValueToWidgets();
	}

	private void applyValueToWidgets() {
		if(((dayBox == null) || (monthBox == null)) || (yearBox == null)) {
			return;
		}
		if(android.text.TextUtils.equals(value, null)) {
			return;
		}
		java.lang.String year = cape.String.getSubString(value, 0, 4);
		java.lang.String month = cape.String.getSubString(value, 4, 2);
		java.lang.String day = cape.String.getSubString(value, 6, 2);
		if(cape.String.startsWith(day, "0")) {
			day = cape.String.getSubString(day, 1);
		}
		if(cape.String.startsWith(month, "0")) {
			month = cape.String.getSubString(month, 1);
		}
		yearBox.setWidgetValue((java.lang.Object)year);
		monthBox.setWidgetValue((java.lang.Object)month);
		dayBox.setWidgetValue((java.lang.Object)day);
	}

	private void getValueFromWidgets() {
		if(((dayBox == null) || (monthBox == null)) || (yearBox == null)) {
			return;
		}
		java.lang.String year = cape.String.asString(yearBox.getWidgetValue());
		java.lang.String month = cape.String.asString(monthBox.getWidgetValue());
		java.lang.String day = cape.String.asString(dayBox.getWidgetValue());
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append(year);
		if(cape.String.getLength(month) == 1) {
			sb.append('0');
		}
		sb.append(month);
		if(cape.String.getLength(day) == 1) {
			sb.append('0');
		}
		sb.append(day);
		value = sb.toString();
	}

	public void setWidgetValue(java.lang.Object value) {
		this.value = (java.lang.String)((value instanceof java.lang.String) ? value : null);
		applyValueToWidgets();
	}

	public java.lang.Object getWidgetValue() {
		getValueFromWidgets();
		return((java.lang.Object)value);
	}

	public int getSkipYears() {
		return(skipYears);
	}

	public DateSelectorWidget setSkipYears(int v) {
		skipYears = v;
		return(this);
	}
}
