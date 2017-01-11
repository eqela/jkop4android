
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

public class SidebarWidget extends LayerWidget
{
	public SidebarWidget forItems(cave.GuiApplicationContext ctx, java.util.ArrayList<android.view.View> items, cave.Color color) {
		SidebarWidget v = new SidebarWidget(ctx);
		v.setWidgetBackgroundColor(color);
		v.setWidgetItems(items);
		return(v);
	}

	public SidebarWidget forItems(cave.GuiApplicationContext ctx, java.util.ArrayList<android.view.View> items) {
		return(forItems(ctx, items, null));
	}

	public SidebarWidget(cave.GuiApplicationContext ctx) {
		super(ctx);
	}

	private cave.Color widgetBackgroundColor = null;
	private cave.Color defaultActionItemWidgetBackgroundColor = null;
	private cave.Color defaultActionItemWidgetTextColor = null;
	private cave.Color defaultLabelItemWidgetBackgroundColor = null;
	private cave.Color defaultLabelItemWidgetTextColor = null;
	private java.util.ArrayList<android.view.View> widgetItems = null;

	public void addToWidgetItems(android.view.View widget) {
		if(widget == null) {
			return;
		}
		if(widgetItems == null) {
			widgetItems = new java.util.ArrayList<android.view.View>();
		}
		widgetItems.add(widget);
	}

	public cave.Color determineBackgroundColor() {
		cave.Color wc = widgetBackgroundColor;
		if(wc == null) {
			wc = cave.Color.white();
		}
		return(wc);
	}

	private cave.Color determineTextColor(cave.Color backgroundColor, cave.Color textColor, cave.Color defaultTextColor, cave.Color lowerBackgroundColor) {
		cave.Color tc = textColor;
		if(tc == null) {
			tc = defaultTextColor;
		}
		if(tc == null) {
			cave.Color cc = lowerBackgroundColor;
			if(cc == null) {
				cc = determineBackgroundColor();
			}
			if(cc.isDarkColor()) {
				tc = cave.Color.white();
			}
			else {
				tc = cave.Color.black();
			}
		}
		return(tc);
	}

	public android.view.View addLabelItem(java.lang.String text, boolean bold, cave.Color backgroundColor, cave.Color textColor) {
		LayerWidget v = new LayerWidget(context);
		cave.Color bgc = backgroundColor;
		if(bgc == null) {
			bgc = defaultLabelItemWidgetBackgroundColor;
		}
		cave.Color tc = determineTextColor(backgroundColor, textColor, defaultLabelItemWidgetTextColor, bgc);
		if(bgc != null) {
			v.addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, bgc));
		}
		int mm3 = context.getHeightValue("3mm");
		LabelWidget lbl = cave.ui.LabelWidget.forText(context, text);
		lbl.setWidgetFontSize((double)mm3);
		lbl.setWidgetTextColor(tc);
		lbl.setWidgetFontBold(bold);
		v.addWidget((android.view.View)forWidget(context, (android.view.View)lbl, mm3));
		addToWidgetItems((android.view.View)v);
		return((android.view.View)this);
	}

	public android.view.View addLabelItem(java.lang.String text, boolean bold, cave.Color backgroundColor) {
		return(addLabelItem(text, bold, backgroundColor, null));
	}

	public android.view.View addLabelItem(java.lang.String text, boolean bold) {
		return(addLabelItem(text, bold, null));
	}

	public android.view.View addLabelItem(java.lang.String text) {
		return(addLabelItem(text, true));
	}

	public android.view.View addActionItem(java.lang.String text, samx.function.Procedure0 handler, boolean bold, cave.Color backgroundColor, cave.Color textColor) {
		LayerWidget v = new LayerWidget(context);
		cave.Color bgc = backgroundColor;
		if(bgc == null) {
			bgc = defaultActionItemWidgetBackgroundColor;
		}
		cave.Color tc = determineTextColor(backgroundColor, textColor, defaultActionItemWidgetTextColor, bgc);
		if(bgc != null) {
			v.addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, bgc));
		}
		int mm3 = context.getHeightValue("3mm");
		LabelWidget lbl = cave.ui.LabelWidget.forText(context, text);
		lbl.setWidgetFontSize((double)mm3);
		lbl.setWidgetTextColor(tc);
		lbl.setWidgetFontBold(bold);
		v.addWidget((android.view.View)forWidget(context, (android.view.View)lbl, mm3));
		if(handler != null) {
			cave.ui.Widget.setWidgetClickHandler((android.view.View)v, handler);
		}
		addToWidgetItems((android.view.View)v);
		return((android.view.View)this);
	}

	public android.view.View addActionItem(java.lang.String text, samx.function.Procedure0 handler, boolean bold, cave.Color backgroundColor) {
		return(addActionItem(text, handler, bold, backgroundColor, null));
	}

	public android.view.View addActionItem(java.lang.String text, samx.function.Procedure0 handler, boolean bold) {
		return(addActionItem(text, handler, bold, null));
	}

	public android.view.View addActionItem(java.lang.String text, samx.function.Procedure0 handler) {
		return(addActionItem(text, handler, false));
	}

	@Override
	public void initializeWidget() {
		super.initializeWidget();
		cave.Color wc = determineBackgroundColor();
		addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, wc));
		VerticalBoxWidget vbox = cave.ui.VerticalBoxWidget.forContext(context, 0);
		if(cape.Vector.isEmpty(widgetItems) == false) {
			if(widgetItems != null) {
				int n = 0;
				int m = widgetItems.size();
				for(n = 0 ; n < m ; n++) {
					android.view.View item = widgetItems.get(n);
					if(item != null) {
						vbox.addWidget(item);
					}
				}
			}
		}
		TopMarginLayerWidget tml = new TopMarginLayerWidget(context);
		tml.addWidget((android.view.View)forWidgetAndWidth(context, (android.view.View)vbox, context.getWidthValue("50mm")));
		addWidget((android.view.View)cave.ui.VerticalScrollerWidget.forWidget(context, (android.view.View)tml));
	}

	public cave.Color getWidgetBackgroundColor() {
		return(widgetBackgroundColor);
	}

	public SidebarWidget setWidgetBackgroundColor(cave.Color v) {
		widgetBackgroundColor = v;
		return(this);
	}

	public cave.Color getDefaultActionItemWidgetBackgroundColor() {
		return(defaultActionItemWidgetBackgroundColor);
	}

	public SidebarWidget setDefaultActionItemWidgetBackgroundColor(cave.Color v) {
		defaultActionItemWidgetBackgroundColor = v;
		return(this);
	}

	public cave.Color getDefaultActionItemWidgetTextColor() {
		return(defaultActionItemWidgetTextColor);
	}

	public SidebarWidget setDefaultActionItemWidgetTextColor(cave.Color v) {
		defaultActionItemWidgetTextColor = v;
		return(this);
	}

	public cave.Color getDefaultLabelItemWidgetBackgroundColor() {
		return(defaultLabelItemWidgetBackgroundColor);
	}

	public SidebarWidget setDefaultLabelItemWidgetBackgroundColor(cave.Color v) {
		defaultLabelItemWidgetBackgroundColor = v;
		return(this);
	}

	public cave.Color getDefaultLabelItemWidgetTextColor() {
		return(defaultLabelItemWidgetTextColor);
	}

	public SidebarWidget setDefaultLabelItemWidgetTextColor(cave.Color v) {
		defaultLabelItemWidgetTextColor = v;
		return(this);
	}

	public java.util.ArrayList<android.view.View> getWidgetItems() {
		return(widgetItems);
	}

	public SidebarWidget setWidgetItems(java.util.ArrayList<android.view.View> v) {
		widgetItems = v;
		return(this);
	}
}
