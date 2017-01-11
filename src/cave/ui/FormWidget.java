
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

public class FormWidget extends LayerWidget
{
	private static class Action
	{
		public java.lang.String label = null;
		public samx.function.Procedure0 handler = null;
	}

	private static class MyStringListInputWidget extends TextInputWidget
	{
		public MyStringListInputWidget(cave.GuiApplicationContext context) {
			super(context);
		}

		@Override
		public void setWidgetValue(java.lang.Object value) {
			cape.DynamicVector vv = (cape.DynamicVector)((value instanceof cape.DynamicVector) ? value : null);
			if(vv == null) {
				return;
			}
			cape.StringBuilder sb = new cape.StringBuilder();
			java.util.ArrayList<java.lang.Object> array = vv.toVector();
			if(array != null) {
				int n = 0;
				int m = array.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.Object tv = array.get(n);
					java.lang.String v = null;
					if(tv instanceof java.lang.String) {
						v = (java.lang.String)tv;
					}
					if(v != null) {
						if(sb.count() > 0) {
							sb.append(", ");
						}
						sb.append(v);
					}
				}
			}
			setWidgetText(sb.toString());
		}

		@Override
		public java.lang.Object getWidgetValue() {
			cape.DynamicVector v = new cape.DynamicVector();
			java.util.ArrayList<java.lang.String> array = cape.String.split(getWidgetText(), ',');
			if(array != null) {
				int n = 0;
				int m = array.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.String string = array.get(n);
					if(string != null) {
						v.append((java.lang.Object)cape.String.strip(string));
					}
				}
			}
			return((java.lang.Object)v);
		}
	}

	private static class StaticTextWidget extends LayerWidget implements WidgetWithValue
	{
		public static StaticTextWidget forText(cave.GuiApplicationContext context, java.lang.String text) {
			StaticTextWidget v = new StaticTextWidget(context);
			v.setWidgetValue((java.lang.Object)text);
			return(v);
		}

		public StaticTextWidget(cave.GuiApplicationContext context) {
			super(context);
		}

		private cave.Color backgroundColor = null;
		private cave.Color textColor = null;
		private LabelWidget label = null;

		@Override
		public void initializeWidget() {
			super.initializeWidget();
			label = new LabelWidget(context);
			cave.Color color = textColor;
			if(color == null) {
				if(backgroundColor.isLightColor()) {
					color = cave.Color.forRGB(0, 0, 0);
				}
				else {
					color = cave.Color.forRGB(255, 255, 255);
				}
			}
			label.setWidgetTextColor(color);
			addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, backgroundColor));
			addWidget((android.view.View)forWidget(context, (android.view.View)label, context.getHeightValue("1500um")));
		}

		public void setWidgetValue(java.lang.Object value) {
			if(label != null) {
				label.setWidgetText(cape.String.asString(value));
			}
		}

		public java.lang.Object getWidgetValue() {
			if(label == null) {
				return(null);
			}
			return((java.lang.Object)label.getWidgetText());
		}

		public cave.Color getBackgroundColor() {
			return(backgroundColor);
		}

		public StaticTextWidget setBackgroundColor(cave.Color v) {
			backgroundColor = v;
			return(this);
		}

		public cave.Color getTextColor() {
			return(textColor);
		}

		public StaticTextWidget setTextColor(cave.Color v) {
			textColor = v;
			return(this);
		}
	}

	public static FormWidget forDeclaration(cave.GuiApplicationContext context, FormDeclaration form) {
		FormWidget v = new FormWidget(context);
		v.setFormDeclaration(form);
		return(v);
	}

	private FormDeclaration formDeclaration = null;
	private java.util.HashMap<java.lang.String,android.view.View> fieldsById = null;
	private java.util.ArrayList<Action> actions = null;
	private int elementSpacing = 0;
	private int formMargin = 0;
	private AlignWidget alignWidget = null;
	private boolean enableFieldLabels = true;
	private int formWidth = 0;
	private int fieldLabelFontSize = 0;
	private LayerWidget customFooterWidget = null;
	private cape.DynamicMap queueData = null;
	private cave.Color widgetBackgroundColor = null;
	private boolean enableScrolling = true;
	private boolean fillContainerWidget = false;

	public FormWidget(cave.GuiApplicationContext context) {
		super(context);
		fieldsById = new java.util.HashMap<java.lang.String,android.view.View>();
		formMargin = context.getHeightValue("1mm");
		formWidth = context.getWidthValue("120mm");
		fieldLabelFontSize = context.getHeightValue("2000um");
		elementSpacing = formMargin;
		customFooterWidget = new LayerWidget(context);
		widgetBackgroundColor = cave.Color.forString("#EEEEEE");
	}

	public LayerWidget getCustomFooterWidget() {
		return(customFooterWidget);
	}

	public FormDeclaration getFormDeclaration() {
		return(formDeclaration);
	}

	public FormWidget setFormDeclaration(FormDeclaration value) {
		formDeclaration = value;
		return(this);
	}

	public void addActions() {
	}

	public void addAction(java.lang.String label, samx.function.Procedure0 handler) {
		if(android.text.TextUtils.equals(label, null)) {
			return;
		}
		if(actions == null) {
			actions = new java.util.ArrayList<Action>();
		}
		Action v = new Action();
		v.label = label;
		v.handler = handler;
		actions.add(v);
	}

	public void setStyleForTextInputWidget(TextInputWidget widget) {
		widget.setWidgetBackgroundColor(cave.Color.white());
		widget.setWidgetPadding(context.getHeightValue("1500um"));
		widget.setWidgetFontSize((double)context.getHeightValue("3000um"));
	}

	public void setStyleForTextButtonWidget(TextButtonWidget widget) {
		widget.setWidgetBackgroundColor(cave.Color.forString("blue"));
		cave.ui.Widget.setAlpha((android.view.View)widget, 0.90);
	}

	public void setStyleForSelectWidget(SelectWidget widget) {
		widget.setWidgetBackgroundColor(cave.Color.white());
		widget.setWidgetPadding(context.getHeightValue("1500um"));
		widget.setWidgetFontSize((double)context.getHeightValue("3000um"));
	}

	public void setStyleForTextAreaWidget(TextAreaWidget widget) {
		widget.setWidgetBackgroundColor(cave.Color.white());
		widget.setWidgetPadding(context.getHeightValue("1500um"));
		widget.setWidgetFontSize((double)context.getHeightValue("3000um"));
	}

	public void setStyleForWidget(android.view.View widget) {
		if(widget instanceof TextInputWidget) {
			setStyleForTextInputWidget((TextInputWidget)widget);
		}
		else if(widget instanceof TextButtonWidget) {
			setStyleForTextButtonWidget((TextButtonWidget)widget);
		}
		else if(widget instanceof SelectWidget) {
			setStyleForSelectWidget((SelectWidget)widget);
		}
		else if(widget instanceof TextAreaWidget) {
			setStyleForTextAreaWidget((TextAreaWidget)widget);
		}
		else {
			java.util.ArrayList<android.view.View> array = cave.ui.Widget.getChildren(widget);
			if(array != null) {
				int n = 0;
				int m = array.size();
				for(n = 0 ; n < m ; n++) {
					android.view.View child = array.get(n);
					if(child != null) {
						setStyleForWidget(child);
					}
				}
			}
		}
	}

	public java.lang.String asPlaceHolder(java.lang.String str) {
		if(enableFieldLabels) {
			return(null);
		}
		return(str);
	}

	public cave.Color getBackgroundColorForElement(cave.ui.FormDeclaration.Element element) {
		if(element instanceof cave.ui.FormDeclaration.Group) {
			return(cave.Color.black());
		}
		return(cave.Color.white());
	}

	public cave.Color getForegroundColorForElement(cave.ui.FormDeclaration.Element element) {
		return(null);
	}

	public cave.Color getAdjustedForegroundColor(cave.ui.FormDeclaration.Element element, cave.Color backgroundColor) {
		cave.Color v = getForegroundColorForElement(element);
		if(v != null) {
			return(v);
		}
		if(backgroundColor == null) {
			return(cave.Color.black());
		}
		if(backgroundColor.isWhite()) {
			return(cave.Color.forRGB(100, 100, 100));
		}
		if(backgroundColor.isDarkColor()) {
			return(cave.Color.white());
		}
		return(cave.Color.black());
	}

	public android.view.View createWidgetForElement(cave.ui.FormDeclaration.Element element) {
		if(element == null) {
			return(null);
		}
		if(element instanceof cave.ui.FormDeclaration.DynamicElement) {
			System.out.println("[cave.ui.FormWidget.createWidgetForElement] (FormWidget.sling:289:2): FIXME: DynamicElement");
		}
		else if(element instanceof cave.ui.FormDeclaration.TextInput) {
			cave.ui.FormDeclaration.TextInput ti = (cave.ui.FormDeclaration.TextInput)element;
			return((android.view.View)cave.ui.TextInputWidget.forType(context, cave.ui.TextInputWidget.TYPE_DEFAULT, asPlaceHolder(ti.getLabel())));
		}
		else if(element instanceof cave.ui.FormDeclaration.RawTextInput) {
			cave.ui.FormDeclaration.RawTextInput ti = (cave.ui.FormDeclaration.RawTextInput)element;
			return((android.view.View)cave.ui.TextInputWidget.forType(context, cave.ui.TextInputWidget.TYPE_NONASSISTED, asPlaceHolder(ti.getLabel())));
		}
		else if(element instanceof cave.ui.FormDeclaration.PasswordInput) {
			cave.ui.FormDeclaration.PasswordInput ti = (cave.ui.FormDeclaration.PasswordInput)element;
			return((android.view.View)cave.ui.TextInputWidget.forType(context, cave.ui.TextInputWidget.TYPE_PASSWORD, asPlaceHolder(ti.getLabel())));
		}
		else if(element instanceof cave.ui.FormDeclaration.NameInput) {
			cave.ui.FormDeclaration.NameInput ti = (cave.ui.FormDeclaration.NameInput)element;
			return((android.view.View)cave.ui.TextInputWidget.forType(context, cave.ui.TextInputWidget.TYPE_NAME, asPlaceHolder(ti.getLabel())));
		}
		else if(element instanceof cave.ui.FormDeclaration.EmailAddressInput) {
			cave.ui.FormDeclaration.EmailAddressInput ti = (cave.ui.FormDeclaration.EmailAddressInput)element;
			return((android.view.View)cave.ui.TextInputWidget.forType(context, cave.ui.TextInputWidget.TYPE_EMAIL_ADDRESS, asPlaceHolder(ti.getLabel())));
		}
		else if(element instanceof cave.ui.FormDeclaration.PhoneNumberInput) {
			cave.ui.FormDeclaration.PhoneNumberInput ti = (cave.ui.FormDeclaration.PhoneNumberInput)element;
			return((android.view.View)cave.ui.TextInputWidget.forType(context, cave.ui.TextInputWidget.TYPE_PHONE_NUMBER, asPlaceHolder(ti.getLabel())));
		}
		else if(element instanceof cave.ui.FormDeclaration.StreetAddressInput) {
			cave.ui.FormDeclaration.StreetAddressInput ti = (cave.ui.FormDeclaration.StreetAddressInput)element;
			return((android.view.View)cave.ui.TextInputWidget.forType(context, cave.ui.TextInputWidget.TYPE_STREET_ADDRESS, asPlaceHolder(ti.getLabel())));
		}
		else if(element instanceof cave.ui.FormDeclaration.TextAreaInput) {
			cave.ui.FormDeclaration.TextAreaInput ta = (cave.ui.FormDeclaration.TextAreaInput)element;
			return((android.view.View)cave.ui.TextAreaWidget.forPlaceholder(context, asPlaceHolder(ta.getLabel()), ta.getRows()));
		}
		else if(element instanceof cave.ui.FormDeclaration.CodeInput) {
			cave.ui.FormDeclaration.TextAreaInput ta = (cave.ui.FormDeclaration.TextAreaInput)element;
			TextAreaWidget v = cave.ui.TextAreaWidget.forPlaceholder(context, asPlaceHolder(ta.getLabel()), ta.getRows());
			v.configureMonospaceFont();
			return((android.view.View)v);
		}
		else if(element instanceof cave.ui.FormDeclaration.StaticTextElement) {
			cave.ui.FormDeclaration.StaticTextElement ti = (cave.ui.FormDeclaration.StaticTextElement)element;
			StaticTextWidget st = cave.ui.FormWidget.StaticTextWidget.forText(context, ti.getLabel());
			cave.Color bgc = getBackgroundColorForElement((cave.ui.FormDeclaration.Element)ti);
			cave.Color fgc = getAdjustedForegroundColor((cave.ui.FormDeclaration.Element)ti, bgc);
			st.setBackgroundColor(bgc);
			st.setTextColor(fgc);
			return((android.view.View)st);
		}
		else if(element instanceof cave.ui.FormDeclaration.MultipleChoiceInput) {
			cave.ui.FormDeclaration.MultipleChoiceInput si = (cave.ui.FormDeclaration.MultipleChoiceInput)element;
			return((android.view.View)cave.ui.SelectWidget.forKeyValueList(context, si.getValues()));
		}
		else if(element instanceof cave.ui.FormDeclaration.DateInput) {
			cave.ui.FormDeclaration.DateInput ds = (cave.ui.FormDeclaration.DateInput)element;
			DateSelectorWidget v = cave.ui.DateSelectorWidget.forContext(context);
			v.setSkipYears(ds.getSkipYears());
			return((android.view.View)v);
		}
		else if(element instanceof cave.ui.FormDeclaration.StringListInput) {
			cave.ui.FormDeclaration.StringListInput ti = (cave.ui.FormDeclaration.StringListInput)element;
			MyStringListInputWidget v = new MyStringListInputWidget(context);
			v.setWidgetPlaceholder(ti.getLabel());
			return((android.view.View)v);
		}
		else if(element instanceof cave.ui.FormDeclaration.Container) {
			CustomContainerWidget v = null;
			if(element instanceof cave.ui.FormDeclaration.VerticalContainer) {
				VerticalBoxWidget vb = cave.ui.VerticalBoxWidget.forContext(context);
				if(formWidth > 0) {
					vb.setWidgetWidthRequest(formWidth);
				}
				vb.setWidgetSpacing(elementSpacing);
				v = (CustomContainerWidget)vb;
			}
			else if(element instanceof cave.ui.FormDeclaration.HorizontalContainer) {
				HorizontalBoxWidget hb = cave.ui.HorizontalBoxWidget.forContext(context);
				hb.setWidgetSpacing(elementSpacing);
				v = (CustomContainerWidget)hb;
			}
			else if(element instanceof cave.ui.FormDeclaration.Group) {
				cave.ui.FormDeclaration.Group g = (cave.ui.FormDeclaration.Group)element;
				VerticalBoxWidget vb = cave.ui.VerticalBoxWidget.forContext(context);
				if(formWidth > 0) {
					vb.setWidgetWidthRequest(formWidth);
				}
				vb.setWidgetSpacing(elementSpacing);
				LayerWidget wlayer = forContext(context);
				cave.Color bgc = getBackgroundColorForElement((cave.ui.FormDeclaration.Element)g);
				wlayer.addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, bgc));
				LabelWidget groupLabel = cave.ui.LabelWidget.forText(context, g.getLabel());
				groupLabel.setWidgetTextColor(getAdjustedForegroundColor((cave.ui.FormDeclaration.Element)g, bgc));
				wlayer.addWidget((android.view.View)forWidget(context, (android.view.View)groupLabel, context.getHeightValue("2mm")));
				vb.addWidget((android.view.View)wlayer);
				v = (CustomContainerWidget)vb;
			}
			else if(element instanceof cave.ui.FormDeclaration.Tab) {
				System.out.println("[cave.ui.FormWidget.createWidgetForElement] (FormWidget.sling:387:3): FIXME");
				return(null);
			}
			else {
				System.out.println("[cave.ui.FormWidget.createWidgetForElement] (FormWidget.sling:391:3): Unsupported form declaration container encountered.");
				return(null);
			}
			java.util.ArrayList<cave.ui.FormDeclaration.Element> array = ((cave.ui.FormDeclaration.Container)element).getChildren();
			if(array != null) {
				int n = 0;
				int m = array.size();
				for(n = 0 ; n < m ; n++) {
					cave.ui.FormDeclaration.Element child = array.get(n);
					if(child != null) {
						android.view.View ww = createAndRegisterWidget(child);
						if(ww != null) {
							if(enableFieldLabels && (child instanceof cave.ui.FormDeclaration.InputElement)) {
								LayerWidget wlayer = forContext(context);
								cave.Color bgc = getBackgroundColorForElement(child);
								wlayer.addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, bgc));
								VerticalBoxWidget wbox = cave.ui.VerticalBoxWidget.forContext(context);
								java.lang.String txt = ((cave.ui.FormDeclaration.InputElement)child).getLabel();
								if(cape.String.isEmpty(txt) == false) {
									LabelWidget lw = cave.ui.LabelWidget.forText(context, txt);
									lw.setWidgetTextColor(getAdjustedForegroundColor(child, bgc));
									lw.setWidgetFontSize((double)fieldLabelFontSize);
									int ss = context.getHeightValue("1mm");
									wbox.addWidget((android.view.View)forWidget(context, (android.view.View)lw).setWidgetMarginLeft(ss).setWidgetMarginRight(ss).setWidgetMarginTop(ss));
								}
								wbox.addWidget(ww, child.getWeight());
								wlayer.addWidget((android.view.View)wbox);
								addToContainerWithWeight(v, (android.view.View)wlayer, child.getWeight());
							}
							else {
								addToContainerWithWeight(v, ww, child.getWeight());
							}
						}
					}
				}
			}
			return((android.view.View)v);
		}
		else {
			System.out.println("[cave.ui.FormWidget.createWidgetForElement] (FormWidget.sling:425:2): FIXME");
		}
		return(null);
	}

	public void addToContainerWithWeight(CustomContainerWidget container, android.view.View child, double weight) {
		if(weight <= 0.00) {
			container.addWidget(child);
		}
		else if(container instanceof HorizontalBoxWidget) {
			((HorizontalBoxWidget)container).addWidget(child, weight);
		}
		else if(container instanceof VerticalBoxWidget) {
			((VerticalBoxWidget)container).addWidget(child, weight);
		}
		else {
			System.out.println("[cave.ui.FormWidget.addToContainerWithWeight] (FormWidget.sling:442:2): Tried to add a widget with weight to a container that is not a box. Ignoring weight.");
			container.addWidget(child);
		}
	}

	public android.view.View createAndRegisterWidget(cave.ui.FormDeclaration.Element element) {
		android.view.View v = createWidgetForElement(element);
		if(v == null) {
			return(null);
		}
		setStyleForWidget(v);
		java.lang.String id = element.getId();
		if(cape.String.isEmpty(id) == false) {
			fieldsById.put(id, v);
		}
		return(v);
	}

	@Override
	public void computeWidgetLayout(int widthConstraint) {
		if(alignWidget != null) {
			if(widthConstraint >= context.getWidthValue("120mm")) {
				alignWidget.setAlignForIndex(0, 0.50, 0.50);
			}
			else {
				alignWidget.setAlignForIndex(0, 0.50, (double)0);
			}
		}
		super.computeWidgetLayout(widthConstraint);
	}

	@Override
	public void initializeWidget() {
		super.initializeWidget();
		FormDeclaration declaration = getFormDeclaration();
		if(declaration == null) {
			return;
		}
		cave.ui.FormDeclaration.Container root = declaration.getRoot();
		if(root == null) {
			return;
		}
		if(widgetBackgroundColor != null) {
			addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, widgetBackgroundColor));
		}
		VerticalBoxWidget box = cave.ui.VerticalBoxWidget.forContext(context);
		box.setWidgetMargin(formMargin);
		box.setWidgetSpacing(formMargin);
		android.view.View topWidget = createAndRegisterWidget((cave.ui.FormDeclaration.Element)root);
		if(topWidget != null) {
			box.addWidget(topWidget, 1.00);
		}
		if(queueData != null) {
			setFormData(queueData);
		}
		if(actions == null) {
			addActions();
		}
		if(actions != null) {
			HorizontalBoxWidget hbox = cave.ui.HorizontalBoxWidget.forContext(context);
			hbox.setWidgetSpacing(context.getHeightValue("1mm"));
			if(actions != null) {
				int n = 0;
				int m = actions.size();
				for(n = 0 ; n < m ; n++) {
					Action action = actions.get(n);
					if(action != null) {
						TextButtonWidget button = cave.ui.TextButtonWidget.forText(context, action.label, action.handler);
						setStyleForTextButtonWidget(button);
						hbox.addWidget((android.view.View)button, (double)1);
					}
				}
			}
			box.addWidget((android.view.View)hbox);
		}
		box.addWidget((android.view.View)customFooterWidget);
		android.view.View finalWidget = null;
		if(fillContainerWidget) {
			finalWidget = (android.view.View)box;
		}
		else {
			alignWidget = cave.ui.AlignWidget.forWidget(context, (android.view.View)box, 0.50, 0.50, 0);
			finalWidget = (android.view.View)alignWidget;
		}
		if(enableScrolling) {
			VerticalScrollerWidget scroller = cave.ui.VerticalScrollerWidget.forWidget(context, finalWidget);
			addWidget((android.view.View)scroller);
		}
		else {
			addWidget(finalWidget);
		}
	}

	public void setFormData(cape.DynamicMap data) {
		if(cape.Map.count(fieldsById) < 1) {
			queueData = data;
		}
		else {
			java.util.ArrayList<java.lang.String> keys = cape.Map.getKeys(fieldsById);
			if(keys != null) {
				int n = 0;
				int m = keys.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.String key = keys.get(n);
					if(key != null) {
						java.lang.Object value = null;
						if(data != null) {
							value = data.get(key);
						}
						setFieldData(key, value);
					}
				}
			}
		}
	}

	public void setFieldData(java.lang.String id, java.lang.Object value) {
		android.view.View twidget = cape.Map.get(fieldsById, id);
		WidgetWithValue widget = null;
		if(twidget instanceof WidgetWithValue) {
			widget = (WidgetWithValue)twidget;
		}
		if(widget == null) {
			return;
		}
		widget.setWidgetValue(value);
	}

	public void getFormDataTo(cape.DynamicMap data) {
		if(data == null) {
			return;
		}
		java.util.ArrayList<java.lang.String> keys = cape.Map.getKeys(fieldsById);
		if(keys != null) {
			int n = 0;
			int m = keys.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.String key = keys.get(n);
				if(key != null) {
					android.view.View twidget = cape.Map.get(fieldsById, key);
					WidgetWithValue widget = null;
					if(twidget instanceof WidgetWithValue) {
						widget = (WidgetWithValue)twidget;
					}
					if(widget == null) {
						continue;
					}
					data.set(key, widget.getWidgetValue());
				}
			}
		}
	}

	public cape.DynamicMap getFormData() {
		cape.DynamicMap v = new cape.DynamicMap();
		getFormDataTo(v);
		return(v);
	}

	public void clearFormData() {
		cape.DynamicMap clearData = new cape.DynamicMap();
		java.util.ArrayList<java.lang.String> keys = cape.Map.getKeys(fieldsById);
		if(keys != null) {
			int n = 0;
			int m = keys.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.String key = keys.get(n);
				if(key != null) {
					clearData.set(key, null);
				}
			}
		}
		setFormData(clearData);
	}

	public int getElementSpacing() {
		return(elementSpacing);
	}

	public FormWidget setElementSpacing(int v) {
		elementSpacing = v;
		return(this);
	}

	public int getFormMargin() {
		return(formMargin);
	}

	public FormWidget setFormMargin(int v) {
		formMargin = v;
		return(this);
	}

	public boolean getEnableFieldLabels() {
		return(enableFieldLabels);
	}

	public FormWidget setEnableFieldLabels(boolean v) {
		enableFieldLabels = v;
		return(this);
	}

	public int getFormWidth() {
		return(formWidth);
	}

	public FormWidget setFormWidth(int v) {
		formWidth = v;
		return(this);
	}

	public int getFieldLabelFontSize() {
		return(fieldLabelFontSize);
	}

	public FormWidget setFieldLabelFontSize(int v) {
		fieldLabelFontSize = v;
		return(this);
	}

	public cave.Color getWidgetBackgroundColor() {
		return(widgetBackgroundColor);
	}

	public FormWidget setWidgetBackgroundColor(cave.Color v) {
		widgetBackgroundColor = v;
		return(this);
	}

	public boolean getEnableScrolling() {
		return(enableScrolling);
	}

	public FormWidget setEnableScrolling(boolean v) {
		enableScrolling = v;
		return(this);
	}

	public boolean getFillContainerWidget() {
		return(fillContainerWidget);
	}

	public FormWidget setFillContainerWidget(boolean v) {
		fillContainerWidget = v;
		return(this);
	}
}
