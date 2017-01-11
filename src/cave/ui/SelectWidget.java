
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

public class SelectWidget extends android.widget.Spinner implements WidgetWithValue, android.widget.AdapterView.OnItemSelectedListener
{
	public static SelectWidget forKeyValueList(cave.GuiApplicationContext context, cape.KeyValueList<java.lang.String, java.lang.String> options) {
		SelectWidget v = new SelectWidget(context);
		v.setWidgetItemsAsKeyValueList(options);
		return(v);
	}

	public static SelectWidget forKeyValueStrings(cave.GuiApplicationContext context, java.lang.String[] options) {
		SelectWidget v = new SelectWidget(context);
		v.setWidgetItemsAsKeyValueStrings(options);
		return(v);
	}

	public static SelectWidget forKeyValueStrings(cave.GuiApplicationContext context, java.util.ArrayList<java.lang.String> options) {
		SelectWidget v = new SelectWidget(context);
		v.setWidgetItemsAsKeyValueStrings(options);
		return(v);
	}

	public static SelectWidget forStringList(cave.GuiApplicationContext context, java.lang.String[] options) {
		SelectWidget v = new SelectWidget(context);
		v.setWidgetItemsAsStringList(options);
		return(v);
	}

	public static SelectWidget forStringList(cave.GuiApplicationContext context, java.util.ArrayList<java.lang.String> options) {
		SelectWidget v = new SelectWidget(context);
		v.setWidgetItemsAsStringList(options);
		return(v);
	}

	private int selectedWidgetIndex = -1;

	public void requestLayout() {
		cave.ui.Widget.onChanged((android.view.View)this);
		super.requestLayout();
	}

	public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
		selectedWidgetIndex = position;
		onWidgetSelectionChanged();
		android.widget.TextView sview = (android.widget.TextView)parent.getChildAt(0);
		if(sview != null) {
			cave.Color textColor = getActualWidgetTextColor();
			sview.setTextColor(textColor.toARGBInt32());
			sview.setEllipsize(android.text.TextUtils.TruncateAt.END);
			sview.setSingleLine();
		}
	}
	public void onNothingSelected(android.widget.AdapterView<?> parent) {
		selectedWidgetIndex = -1;
		onWidgetSelectionChanged();
	}

	private cave.GuiApplicationContext widgetContext = null;
	private cape.KeyValueList<java.lang.String, java.lang.String> widgetItems = null;
	private samx.function.Procedure0 widgetValueChangeHandler = null;
	private cave.Color widgetBackgroundColor = null;
	private cave.Color widgetTextColor = null;
	private int widgetPadding = 0;
	private java.lang.String widgetFontFamily = null;
	private double widgetFontSize = 0.00;

	public SelectWidget(cave.GuiApplicationContext context) {
		super(((cave.GuiApplicationContextForAndroid)context).getActivityContext(), android.widget.Spinner.MODE_DROPDOWN);
		widgetContext = context;
		widgetFontFamily = "Arial";
		widgetFontSize = (double)context.getHeightValue("3mm");
		setOnItemSelectedListener(this);
		updateWidgetAppearance();
	}

	public SelectWidget setWidgetFontFamily(java.lang.String family) {
		widgetFontFamily = family;
		updateWidgetFont();
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	public SelectWidget setWidgetFontSize(double fontSize) {
		widgetFontSize = fontSize;
		updateWidgetFont();
		cave.ui.Widget.onChanged((android.view.View)this);
		return(this);
	}

	private void updateWidgetFont() {
	}

	public SelectWidget setWidgetPadding(int value) {
		widgetPadding = value;
		updateWidgetAppearance();
		return(this);
	}

	public int getWidgetPadding() {
		return(widgetPadding);
	}

	public SelectWidget setWidgetTextColor(cave.Color color) {
		widgetTextColor = color;
		updateWidgetAppearance();
		return(this);
	}

	public cave.Color getWidgetTextColor() {
		return(widgetTextColor);
	}

	public SelectWidget setWidgetBackgroundColor(cave.Color color) {
		widgetBackgroundColor = color;
		updateWidgetAppearance();
		return(this);
	}

	public cave.Color getWidgetBackgroundColor() {
		return(widgetBackgroundColor);
	}

	public cave.Color getActualWidgetTextColor() {
		cave.Color textColor = widgetTextColor;
		if(textColor == null) {
			if(widgetBackgroundColor != null) {
				if(widgetBackgroundColor.isLightColor()) {
					textColor = cave.Color.black();
				}
				else {
					textColor = cave.Color.white();
				}
			}
			else {
				textColor = cave.Color.black();
			}
		}
		return(textColor);
	}

	private void updateWidgetAppearance() {
		cave.Color textColor = getActualWidgetTextColor();
		if(widgetBackgroundColor != null) {
			setBackgroundColor(widgetBackgroundColor.toARGBInt32());
		}
		else {
			setBackgroundColor(0);
		}
		setPadding(widgetPadding, widgetPadding, widgetPadding, widgetPadding);
		cave.ui.Widget.onChanged((android.view.View)this);
	}

	public void setWidgetItemsAsKeyValueList(cape.KeyValueList<java.lang.String, java.lang.String> items) {
		int selectedIndex = getSelectedWidgetIndex();
		widgetItems = items;
		java.util.ArrayList<java.lang.String> list = new java.util.ArrayList<java.lang.String>();
		if(widgetItems != null) {
			cape.Iterator<cape.KeyValuePair<java.lang.String, java.lang.String>> it = widgetItems.iterate();
			while(it != null) {
				cape.KeyValuePair<java.lang.String, java.lang.String> item = it.next();
				if(item == null) {
					break;
				}
				list.add(item.value);
			}
		}
		android.widget.ArrayAdapter<java.lang.String> adapter = new android.widget.ArrayAdapter<java.lang.String>(((cave.GuiApplicationContextForAndroid)widgetContext).getAndroidActivityContext(), android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		super.setAdapter(adapter);
		setSelectedWidgetIndex(selectedIndex);
	}

	public void setWidgetItemsAsKeyValueStrings(java.util.ArrayList<java.lang.String> options) {
		cape.KeyValueList<java.lang.String, java.lang.String> list = new cape.KeyValueList<java.lang.String, java.lang.String>();
		if(options != null) {
			if(options != null) {
				int n = 0;
				int m = options.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.String option = options.get(n);
					if(option != null) {
						java.util.ArrayList<java.lang.String> comps = cape.String.split(option, ':', 2);
						java.lang.String kk = cape.Vector.get(comps, 0);
						java.lang.String vv = cape.Vector.get(comps, 1);
						if(android.text.TextUtils.equals(vv, null)) {
							vv = kk;
						}
						list.add((java.lang.String)kk, (java.lang.String)vv);
					}
				}
			}
		}
		setWidgetItemsAsKeyValueList(list);
	}

	public void setWidgetItemsAsKeyValueStrings(java.lang.String[] options) {
		cape.KeyValueList<java.lang.String, java.lang.String> list = new cape.KeyValueList<java.lang.String, java.lang.String>();
		if(options != null) {
			if(options != null) {
				int n = 0;
				int m = options.length;
				for(n = 0 ; n < m ; n++) {
					java.lang.String option = options[n];
					if(option != null) {
						java.util.ArrayList<java.lang.String> comps = cape.String.split(option, ':', 2);
						java.lang.String kk = cape.Vector.get(comps, 0);
						java.lang.String vv = cape.Vector.get(comps, 1);
						if(android.text.TextUtils.equals(vv, null)) {
							vv = kk;
						}
						list.add((java.lang.String)kk, (java.lang.String)vv);
					}
				}
			}
		}
		setWidgetItemsAsKeyValueList(list);
	}

	public void setWidgetItemsAsStringList(java.util.ArrayList<java.lang.String> options) {
		cape.KeyValueList<java.lang.String, java.lang.String> list = new cape.KeyValueList<java.lang.String, java.lang.String>();
		if(options != null) {
			if(options != null) {
				int n = 0;
				int m = options.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.String option = options.get(n);
					if(option != null) {
						list.add((java.lang.String)option, (java.lang.String)option);
					}
				}
			}
		}
		setWidgetItemsAsKeyValueList(list);
	}

	public void setWidgetItemsAsStringList(java.lang.String[] options) {
		cape.KeyValueList<java.lang.String, java.lang.String> list = new cape.KeyValueList<java.lang.String, java.lang.String>();
		if(options != null) {
			if(options != null) {
				int n = 0;
				int m = options.length;
				for(n = 0 ; n < m ; n++) {
					java.lang.String option = options[n];
					if(option != null) {
						list.add((java.lang.String)option, (java.lang.String)option);
					}
				}
			}
		}
		setWidgetItemsAsKeyValueList(list);
	}

	public int adjustSelectedWidgetItemIndex(int index) {
		if((widgetItems == null) || (widgetItems.count() < 1)) {
			return(-1);
		}
		if(index < 0) {
			return(0);
		}
		if(index >= widgetItems.count()) {
			return(widgetItems.count() - 1);
		}
		return(index);
	}

	private int findIndexForWidgetValue(java.lang.String id) {
		int v = -1;
		if(widgetItems != null) {
			int n = 0;
			cape.Iterator<cape.KeyValuePair<java.lang.String, java.lang.String>> it = widgetItems.iterate();
			while(it != null) {
				cape.KeyValuePair<java.lang.String, java.lang.String> item = it.next();
				if(item == null) {
					break;
				}
				if(android.text.TextUtils.equals(item.key, id)) {
					v = n;
					break;
				}
				n++;
			}
		}
		return(v);
	}

	private int getWidgetItemCount() {
		if(widgetItems == null) {
			return(0);
		}
		return(widgetItems.count());
	}

	private java.lang.String getWidgetTextForIndex(int index) {
		if(widgetItems == null) {
			return(null);
		}
		return(widgetItems.getValue(index));
	}

	public int getSelectedWidgetIndex() {
		return(selectedWidgetIndex);
	}

	public void setSelectedWidgetIndex(int index) {
		int v = adjustSelectedWidgetItemIndex(index);
		selectedWidgetIndex = v;
		super.setSelection(v);
	}

	public void setSelectedWidgetValue(java.lang.String id) {
		setSelectedWidgetIndex(findIndexForWidgetValue(id));
	}

	public java.lang.String getSelectedWidgetValue() {
		int index = getSelectedWidgetIndex();
		if((widgetItems == null) || (index < 0)) {
			return(null);
		}
		return(widgetItems.getKey(index));
	}

	public void setWidgetValue(java.lang.Object value) {
		setSelectedWidgetValue(cape.String.asString(value));
	}

	public java.lang.Object getWidgetValue() {
		return((java.lang.Object)getSelectedWidgetValue());
	}

	public void setWidgetValueChangeHandler(samx.function.Procedure0 handler) {
		widgetValueChangeHandler = handler;
	}

	public void onWidgetSelectionChanged() {
		if(widgetValueChangeHandler != null) {
			widgetValueChangeHandler.execute();
		}
	}
}
