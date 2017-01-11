
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

public class PopupDialogManager
{
	public PopupDialogManager(cave.GuiApplicationContext context, android.view.View parent) {
		this.context = context;
		this.parent = parent;
		positiveButtonColor = cave.Color.forRGB(128, 204, 128);
		negativeButtonColor = cave.Color.forRGB(204, 128, 128);
		backgroundColor = null;
		headerBackgroundColor = null;
		headerTextColor = null;
		messageTextColor = null;
	}

	private cave.GuiApplicationContext context = null;
	private android.view.View parent = null;
	private cave.Color backgroundColor = null;
	private cave.Color headerBackgroundColor = null;
	private cave.Color headerTextColor = null;
	private cave.Color messageTextColor = null;
	private cave.Color positiveButtonColor = null;
	private cave.Color negativeButtonColor = null;

	public PopupDialogManager setButtonColor(cave.Color color) {
		positiveButtonColor = color;
		negativeButtonColor = color;
		return(this);
	}

	public void showTextInputDialog(java.lang.String title, java.lang.String prompt, samx.function.Procedure1<java.lang.String> callback) {
		checkForDefaultColors();
		int mm2 = context.getWidthValue("2mm");
		int mm3 = context.getWidthValue("3mm");
		LayerWidget widget = new LayerWidget(context);
		widget.setWidgetWidthRequest(context.getWidthValue("100mm"));
		widget.addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, backgroundColor));
		LabelWidget titleLabel = cave.ui.LabelWidget.forText(context, title);
		titleLabel.setWidgetFontSize((double)mm3);
		titleLabel.setWidgetTextColor(headerTextColor);
		titleLabel.setWidgetFontBold(true);
		VerticalBoxWidget box = new VerticalBoxWidget(context);
		box.addWidget((android.view.View)new LayerWidget(context).addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, headerBackgroundColor)).addWidget((android.view.View)cave.ui.AlignWidget.forWidget(context, (android.view.View)titleLabel, (double)0, 0.50).setWidgetMargin(mm3)));
		VerticalBoxWidget sbox = new VerticalBoxWidget(context);
		sbox.setWidgetMargin(mm3);
		sbox.setWidgetSpacing(mm3);
		LabelWidget messageLabel = cave.ui.LabelWidget.forText(context, prompt);
		messageLabel.setWidgetTextAlign(cave.ui.LabelWidget.ALIGN_CENTER);
		messageLabel.setWidgetFontSize((double)mm3);
		messageLabel.setWidgetTextColor(messageTextColor);
		sbox.addWidget((android.view.View)messageLabel);
		final TextInputWidget input = new TextInputWidget(context);
		input.setWidgetBackgroundColor(cave.Color.forRGB(200, 200, 200));
		input.setWidgetPadding(context.getHeightValue("2mm"));
		input.setWidgetFontSize((double)context.getHeightValue("3000um"));
		sbox.addWidget((android.view.View)input);
		HorizontalBoxWidget buttons = new HorizontalBoxWidget(context);
		buttons.setWidgetSpacing(mm3);
		TextButtonWidget noButton = cave.ui.TextButtonWidget.forText(context, "Cancel", null);
		noButton.setWidgetBackgroundColor(positiveButtonColor);
		buttons.addWidget((android.view.View)noButton, 1.00);
		TextButtonWidget yesButton = cave.ui.TextButtonWidget.forText(context, "OK", null);
		yesButton.setWidgetBackgroundColor(positiveButtonColor);
		buttons.addWidget((android.view.View)yesButton, 1.00);
		sbox.addWidget((android.view.View)buttons);
		box.addWidget((android.view.View)sbox);
		widget.addWidget((android.view.View)box);
		final PopupWidget pp = cave.ui.PopupWidget.forContentWidget(context, (android.view.View)cave.ui.LayerWidget.forWidget(context, (android.view.View)widget, mm2));
		final samx.function.Procedure1<java.lang.String> cb = callback;
		pp.showPopup(parent);
		yesButton.setWidgetClickHandler(new samx.function.Procedure0() {
			public void execute() {
				pp.hidePopup();
				if(cb != null) {
					cb.execute(input.getWidgetText());
				}
			}
		});
		noButton.setWidgetClickHandler(new samx.function.Procedure0() {
			public void execute() {
				pp.hidePopup();
				if(cb != null) {
					cb.execute(null);
				}
			}
		});
	}

	public void showTextInputDialog(java.lang.String title, java.lang.String prompt) {
		showTextInputDialog(title, prompt, null);
	}

	public void showMessageDialog(java.lang.String title, java.lang.String message, samx.function.Procedure0 callback) {
		checkForDefaultColors();
		int mm2 = context.getWidthValue("2mm");
		int mm3 = context.getWidthValue("3mm");
		LayerWidget widget = new LayerWidget(context);
		widget.setWidgetWidthRequest(context.getWidthValue("100mm"));
		widget.addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, cave.Color.white()));
		LabelWidget titleLabel = cave.ui.LabelWidget.forText(context, title);
		titleLabel.setWidgetFontSize((double)mm3);
		titleLabel.setWidgetTextColor(cave.Color.white());
		titleLabel.setWidgetFontBold(true);
		VerticalBoxWidget box = new VerticalBoxWidget(context);
		box.addWidget((android.view.View)new LayerWidget(context).addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, cave.Color.black())).addWidget((android.view.View)cave.ui.AlignWidget.forWidget(context, (android.view.View)titleLabel, (double)0, 0.50).setWidgetMargin(mm3)));
		VerticalBoxWidget sbox = new VerticalBoxWidget(context);
		sbox.setWidgetMargin(mm3);
		sbox.setWidgetSpacing(mm3);
		LabelWidget messageLabel = cave.ui.LabelWidget.forText(context, message);
		messageLabel.setWidgetTextAlign(cave.ui.LabelWidget.ALIGN_CENTER);
		messageLabel.setWidgetFontSize((double)mm3);
		messageLabel.setWidgetTextColor(messageTextColor);
		sbox.addWidget((android.view.View)messageLabel);
		HorizontalBoxWidget buttons = new HorizontalBoxWidget(context);
		buttons.setWidgetSpacing(mm3);
		TextButtonWidget okButton = cave.ui.TextButtonWidget.forText(context, "OK", null);
		okButton.setWidgetBackgroundColor(positiveButtonColor);
		buttons.addWidget((android.view.View)okButton, 1.00);
		sbox.addWidget((android.view.View)buttons);
		box.addWidget((android.view.View)sbox);
		widget.addWidget((android.view.View)box);
		final PopupWidget pp = cave.ui.PopupWidget.forContentWidget(context, (android.view.View)cave.ui.LayerWidget.forWidget(context, (android.view.View)widget, mm2));
		final samx.function.Procedure0 cb = callback;
		pp.showPopup(parent);
		okButton.setWidgetClickHandler(new samx.function.Procedure0() {
			public void execute() {
				pp.hidePopup();
				if(cb != null) {
					cb.execute();
				}
			}
		});
	}

	public void showMessageDialog(java.lang.String title, java.lang.String message) {
		showMessageDialog(title, message, null);
	}

	public void showConfirmDialog(java.lang.String title, java.lang.String message, samx.function.Procedure0 okcallback, samx.function.Procedure0 cancelcallback) {
		checkForDefaultColors();
		int mm2 = context.getWidthValue("2mm");
		int mm3 = context.getWidthValue("3mm");
		LayerWidget widget = new LayerWidget(context);
		widget.setWidgetWidthRequest(context.getWidthValue("100mm"));
		widget.addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, cave.Color.white()));
		LabelWidget titleLabel = cave.ui.LabelWidget.forText(context, title);
		titleLabel.setWidgetFontSize((double)mm3);
		titleLabel.setWidgetTextColor(headerTextColor);
		titleLabel.setWidgetFontBold(true);
		VerticalBoxWidget box = new VerticalBoxWidget(context);
		box.addWidget((android.view.View)new LayerWidget(context).addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, headerBackgroundColor)).addWidget((android.view.View)cave.ui.AlignWidget.forWidget(context, (android.view.View)titleLabel, (double)0, 0.50).setWidgetMargin(mm3)));
		VerticalBoxWidget sbox = new VerticalBoxWidget(context);
		sbox.setWidgetMargin(mm3);
		sbox.setWidgetSpacing(mm3);
		LabelWidget messageLabel = cave.ui.LabelWidget.forText(context, message);
		messageLabel.setWidgetTextAlign(cave.ui.LabelWidget.ALIGN_CENTER);
		messageLabel.setWidgetFontSize((double)mm3);
		messageLabel.setWidgetTextColor(messageTextColor);
		sbox.addWidget((android.view.View)messageLabel);
		HorizontalBoxWidget buttons = new HorizontalBoxWidget(context);
		buttons.setWidgetSpacing(mm3);
		TextButtonWidget noButton = cave.ui.TextButtonWidget.forText(context, "NO", null);
		noButton.setWidgetBackgroundColor(positiveButtonColor);
		buttons.addWidget((android.view.View)noButton, 1.00);
		TextButtonWidget yesButton = cave.ui.TextButtonWidget.forText(context, "YES", null);
		yesButton.setWidgetBackgroundColor(positiveButtonColor);
		buttons.addWidget((android.view.View)yesButton, 1.00);
		sbox.addWidget((android.view.View)buttons);
		box.addWidget((android.view.View)sbox);
		widget.addWidget((android.view.View)box);
		final PopupWidget pp = cave.ui.PopupWidget.forContentWidget(context, (android.view.View)cave.ui.LayerWidget.forWidget(context, (android.view.View)widget, mm2));
		final samx.function.Procedure0 okcb = okcallback;
		final samx.function.Procedure0 cancelcb = cancelcallback;
		pp.showPopup(parent);
		yesButton.setWidgetClickHandler(new samx.function.Procedure0() {
			public void execute() {
				pp.hidePopup();
				if(okcb != null) {
					okcb.execute();
				}
			}
		});
		noButton.setWidgetClickHandler(new samx.function.Procedure0() {
			public void execute() {
				pp.hidePopup();
				if(cancelcb != null) {
					cancelcb.execute();
				}
			}
		});
	}

	public void showErrorDialog(java.lang.String message, samx.function.Procedure0 callback) {
		showMessageDialog("Error", message, callback);
	}

	public void showErrorDialog(java.lang.String message) {
		showErrorDialog(message, null);
	}

	public void checkForDefaultColors() {
		cave.Color bgc = backgroundColor;
		if(bgc == null) {
			backgroundColor = cave.Color.white();
		}
		cave.Color hbg = headerBackgroundColor;
		if(hbg == null) {
			headerBackgroundColor = cave.Color.black();
		}
		cave.Color htc = headerTextColor;
		if(htc == null) {
			if(headerBackgroundColor.isDarkColor()) {
				headerTextColor = cave.Color.white();
			}
			else {
				headerTextColor = cave.Color.black();
			}
		}
		cave.Color mtc = messageTextColor;
		if(mtc == null) {
			if(backgroundColor.isDarkColor()) {
				messageTextColor = cave.Color.white();
			}
			else {
				messageTextColor = cave.Color.black();
			}
		}
	}

	public cave.GuiApplicationContext getContext() {
		return(context);
	}

	public PopupDialogManager setContext(cave.GuiApplicationContext v) {
		context = v;
		return(this);
	}

	public android.view.View getParent() {
		return(parent);
	}

	public PopupDialogManager setParent(android.view.View v) {
		parent = v;
		return(this);
	}

	public cave.Color getBackgroundColor() {
		return(backgroundColor);
	}

	public PopupDialogManager setBackgroundColor(cave.Color v) {
		backgroundColor = v;
		return(this);
	}

	public cave.Color getHeaderBackgroundColor() {
		return(headerBackgroundColor);
	}

	public PopupDialogManager setHeaderBackgroundColor(cave.Color v) {
		headerBackgroundColor = v;
		return(this);
	}

	public cave.Color getHeaderTextColor() {
		return(headerTextColor);
	}

	public PopupDialogManager setHeaderTextColor(cave.Color v) {
		headerTextColor = v;
		return(this);
	}

	public cave.Color getMessageTextColor() {
		return(messageTextColor);
	}

	public PopupDialogManager setMessageTextColor(cave.Color v) {
		messageTextColor = v;
		return(this);
	}

	public cave.Color getPositiveButtonColor() {
		return(positiveButtonColor);
	}

	public PopupDialogManager setPositiveButtonColor(cave.Color v) {
		positiveButtonColor = v;
		return(this);
	}

	public cave.Color getNegativeButtonColor() {
		return(negativeButtonColor);
	}

	public PopupDialogManager setNegativeButtonColor(cave.Color v) {
		negativeButtonColor = v;
		return(this);
	}
}
