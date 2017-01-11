
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

public class ActionBarWidget extends LayerWidget implements ScreenAwareWidget
{
	public ActionBarWidget(cave.GuiApplicationContext ctx) {
		super(ctx);
	}

	private cave.Color widgetBackgroundColor = null;
	private cave.Color widgetTextColor = null;
	private java.lang.String widgetTitle = null;
	private java.lang.String widgetLeftIconResource = null;
	private samx.function.Procedure0 widgetLeftAction = null;
	private java.lang.String widgetRightIconResource = null;
	private samx.function.Procedure0 widgetRightAction = null;
	private LabelWidget label = null;
	private ImageButtonWidget leftButton = null;
	private ImageButtonWidget rightButton = null;
	private HorizontalBoxWidget box = null;

	public ActionBarWidget setWidgetTitle(java.lang.String value) {
		widgetTitle = value;
		if(label != null) {
			label.setWidgetText(widgetTitle);
		}
		return(this);
	}

	public java.lang.String getWidgetTitle() {
		return(widgetTitle);
	}

	public void configureLeftButton(java.lang.String iconResource, samx.function.Procedure0 action) {
		widgetLeftIconResource = iconResource;
		widgetLeftAction = action;
		updateLeftButton();
	}

	public void configureRightButton(java.lang.String iconResource, samx.function.Procedure0 action) {
		widgetRightIconResource = iconResource;
		widgetRightAction = action;
		updateRightButton();
	}

	private void updateLeftButton() {
		if(leftButton == null) {
			return;
		}
		if(cape.String.isEmpty(widgetLeftIconResource) == false) {
			leftButton.setWidgetImageResource(widgetLeftIconResource);
			leftButton.setWidgetClickHandler(widgetLeftAction);
		}
		else {
			leftButton.setWidgetImageResource(null);
			leftButton.setWidgetClickHandler(null);
		}
	}

	private void updateRightButton() {
		if(rightButton == null) {
			return;
		}
		if(cape.String.isEmpty(widgetRightIconResource) == false) {
			rightButton.setWidgetImageResource(widgetRightIconResource);
			rightButton.setWidgetClickHandler(widgetRightAction);
		}
		else {
			rightButton.setWidgetImageResource(null);
			rightButton.setWidgetClickHandler(null);
		}
	}

	public cave.Color getWidgetTextColor() {
		cave.Color wtc = widgetTextColor;
		if(wtc == null) {
			wtc = cave.Color.forRGB(255, 255, 255);
		}
		return(wtc);
	}

	public void onWidgetAddedToScreen(ScreenForWidget screen) {
	}

	public void onWidgetRemovedFromScreen(ScreenForWidget screen) {
	}

	@Override
	public void initializeWidget() {
		super.initializeWidget();
		cave.Color bgc = widgetBackgroundColor;
		if(bgc != null) {
			addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, bgc));
		}
		TopMarginLayerWidget tml = new TopMarginLayerWidget(context);
		label = cave.ui.LabelWidget.forText(context, widgetTitle);
		label.setWidgetFontFamily("Arial");
		cave.Color wtc = getWidgetTextColor();
		label.setWidgetTextColor(wtc);
		tml.addWidget((android.view.View)cave.ui.AlignWidget.forWidget(context, (android.view.View)label, 0.50, 0.50));
		box = cave.ui.HorizontalBoxWidget.forContext(context);
		box.setWidgetMargin(context.getWidthValue("1mm"));
		box.setWidgetSpacing(context.getWidthValue("1mm"));
		leftButton = new ImageButtonWidget(context);
		leftButton.setWidgetButtonHeight(context.getHeightValue("6mm"));
		box.addWidget((android.view.View)leftButton);
		updateLeftButton();
		box.addWidget((android.view.View)new LayerWidget(context), 1.00);
		rightButton = new ImageButtonWidget(context);
		rightButton.setWidgetButtonHeight(context.getHeightValue("6mm"));
		box.addWidget((android.view.View)rightButton);
		updateRightButton();
		tml.addWidget((android.view.View)box);
		addWidget((android.view.View)tml);
	}

	public cave.Color getWidgetBackgroundColor() {
		return(widgetBackgroundColor);
	}

	public ActionBarWidget setWidgetBackgroundColor(cave.Color v) {
		widgetBackgroundColor = v;
		return(this);
	}

	public ActionBarWidget setWidgetTextColor(cave.Color v) {
		widgetTextColor = v;
		return(this);
	}
}
