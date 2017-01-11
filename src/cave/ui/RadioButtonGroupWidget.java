
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

public class RadioButtonGroupWidget extends android.widget.RadioGroup implements WidgetWithValue, android.widget.RadioGroup.OnCheckedChangeListener
{
	public static RadioButtonGroupWidget forGroup(cave.GuiApplicationContext context, java.lang.String group, java.util.ArrayList<java.lang.String> items) {
		RadioButtonGroupWidget v = new RadioButtonGroupWidget(context);
		v.setWidgetName(group);
		for(int i = 0 ; i < cape.Vector.getSize(items) ; i++) {
			v.addWidgetItem(cape.Vector.get(items, i), i);
		}
		return(v);
	}

	public void onCheckedChanged(android.widget.RadioGroup group, int checkedId) {
		widgetSelectedValue = cape.Vector.get(widgetItems, checkedId);
		onChangeSelectedItem();
	}

	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	private cave.GuiApplicationContext widgetContext = null;
	private java.lang.String widgetName = null;
	private java.lang.String widgetSelectedValue = null;
	private java.util.ArrayList<java.lang.String> widgetItems = null;
	private samx.function.Procedure0 widgetChangeHandler = null;

	public RadioButtonGroupWidget(cave.GuiApplicationContext context) {
		super(((cave.GuiApplicationContextForAndroid)context).getActivityContext());
		setOnCheckedChangeListener(this);
		widgetContext = context;
	}

	public void addWidgetItem(java.lang.String text, int index) {
		if(widgetItems == null) {
			widgetItems = new java.util.ArrayList<java.lang.String>();
		}
		widgetItems.add(text);
		android.widget.RadioButton v = new android.widget.RadioButton(((cave.GuiApplicationContextForAndroid)widgetContext).getActivityContext());
		v.setText(text);
		v.setTextColor(cave.Color.black().toARGBInt32());
		android.content.res.ColorStateList cl = new android.content.res.ColorStateList(
		new int[][]{
			new int[]{-android.R.attr.state_enabled},
			new int[]{android.R.attr.state_enabled}
	},
	new int[] {
		android.graphics.Color.DKGRAY,
		android.graphics.Color.DKGRAY
	}
	);
	v.setButtonTintList(cl);
	v.setId(index);
	cave.ui.Widget.addChild((android.view.View)this, (android.view.View)((v instanceof android.view.View) ? v : null));
}

public void setWidgetSelectedValue(int indx) {
	check(indx);
}

public void setWidgetName(java.lang.String name) {
	widgetName = name;
}

public void setWidgetOrientation(int orientation) {
	if(orientation == HORIZONTAL) {
		setOrientation(android.widget.LinearLayout.HORIZONTAL);
	}
	else if(orientation == VERTICAL) {
		setOrientation(android.widget.LinearLayout.VERTICAL);
	}
}

public java.lang.String getWidgetSelectedValue() {
	return(widgetSelectedValue);
}

public void onChangeSelectedItem() {
	if(widgetChangeHandler != null) {
		widgetChangeHandler.execute();
	}
}

public void setWidgetValue(java.lang.Object value) {
	if(value instanceof cape.IntegerObject) {
		setWidgetSelectedValue(((cape.IntegerObject)((value instanceof cape.IntegerObject) ? value : null)).toInteger());
	}
}

public java.lang.Object getWidgetValue() {
	return((java.lang.Object)getWidgetSelectedValue());
}

public samx.function.Procedure0 getWidgetChangeHandler() {
	return(widgetChangeHandler);
}

public RadioButtonGroupWidget setWidgetChangeHandler(samx.function.Procedure0 v) {
	widgetChangeHandler = v;
	return(this);
}
}
