
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

public class FormDeclaration
{
	public static class Element
	{
		private java.lang.String id = null;
		private double weight = 0.00;

		public java.lang.String getId() {
			return(id);
		}

		public Element setId(java.lang.String v) {
			id = v;
			return(this);
		}

		public double getWeight() {
			return(weight);
		}

		public Element setWeight(double v) {
			weight = v;
			return(this);
		}
	}

	public static class InputElement extends Element
	{
		private java.lang.String label = null;
		private java.lang.String description = null;

		public java.lang.String getLabel() {
			return(label);
		}

		public InputElement setLabel(java.lang.String v) {
			label = v;
			return(this);
		}

		public java.lang.String getDescription() {
			return(description);
		}

		public InputElement setDescription(java.lang.String v) {
			description = v;
			return(this);
		}
	}

	public static class DynamicElement extends Element
	{
		private java.lang.String type = null;
		private cape.DynamicMap properties = null;

		public java.lang.String getType() {
			return(type);
		}

		public DynamicElement setType(java.lang.String v) {
			type = v;
			return(this);
		}

		public cape.DynamicMap getProperties() {
			return(properties);
		}

		public DynamicElement setProperties(cape.DynamicMap v) {
			properties = v;
			return(this);
		}
	}

	public static class Container extends Element
	{
		private java.util.ArrayList<Element> children = null;

		public void addToChildren(Element element) {
			if(element == null) {
				return;
			}
			if(children == null) {
				children = new java.util.ArrayList<Element>();
			}
			children.add(element);
		}

		public java.util.ArrayList<Element> getChildren() {
			return(children);
		}

		public Container setChildren(java.util.ArrayList<Element> v) {
			children = v;
			return(this);
		}
	}

	public static class Group extends Container
	{
		private java.lang.String label = null;
		private java.lang.String description = null;

		public java.lang.String getLabel() {
			return(label);
		}

		public Group setLabel(java.lang.String v) {
			label = v;
			return(this);
		}

		public java.lang.String getDescription() {
			return(description);
		}

		public Group setDescription(java.lang.String v) {
			description = v;
			return(this);
		}
	}

	public static class Tab extends Container
	{
		private java.lang.String label = null;

		public java.lang.String getLabel() {
			return(label);
		}

		public Tab setLabel(java.lang.String v) {
			label = v;
			return(this);
		}
	}

	public static class VerticalContainer extends Container
	{
	}

	public static class HorizontalContainer extends Container
	{
	}

	public static class TextInput extends InputElement
	{
	}

	public static class RawTextInput extends InputElement
	{
	}

	public static class PasswordInput extends InputElement
	{
	}

	public static class NameInput extends InputElement
	{
	}

	public static class EmailAddressInput extends InputElement
	{
	}

	public static class PhoneNumberInput extends InputElement
	{
	}

	public static class StreetAddressInput extends InputElement
	{
	}

	public static class WithIconInput extends InputElement
	{
		private cave.Image icon = null;
		private samx.function.Procedure0 action = null;

		public cave.Image getIcon() {
			return(icon);
		}

		public WithIconInput setIcon(cave.Image v) {
			icon = v;
			return(this);
		}

		public samx.function.Procedure0 getAction() {
			return(action);
		}

		public WithIconInput setAction(samx.function.Procedure0 v) {
			action = v;
			return(this);
		}
	}

	public static class PhotoCaptureInput extends InputElement
	{
		private cave.Image defaultImage = null;

		public cave.Image getDefaultImage() {
			return(defaultImage);
		}

		public PhotoCaptureInput setDefaultImage(cave.Image v) {
			defaultImage = v;
			return(this);
		}
	}

	public static class RadioGroupInput extends InputElement
	{
		private java.util.ArrayList<java.lang.String> items = null;
		private java.lang.String groupName = null;

		public java.util.ArrayList<java.lang.String> getItems() {
			return(items);
		}

		public RadioGroupInput setItems(java.util.ArrayList<java.lang.String> v) {
			items = v;
			return(this);
		}

		public java.lang.String getGroupName() {
			return(groupName);
		}

		public RadioGroupInput setGroupName(java.lang.String v) {
			groupName = v;
			return(this);
		}
	}

	public static class MultipleChoiceInput extends InputElement
	{
		private cape.KeyValueList<java.lang.String, java.lang.String> values = null;

		public cape.KeyValueList<java.lang.String, java.lang.String> getValues() {
			return(values);
		}

		public MultipleChoiceInput setValues(cape.KeyValueList<java.lang.String, java.lang.String> v) {
			values = v;
			return(this);
		}
	}

	public static class DateInput extends InputElement
	{
		private int skipYears = 0;

		public int getSkipYears() {
			return(skipYears);
		}

		public DateInput setSkipYears(int v) {
			skipYears = v;
			return(this);
		}
	}

	public static class TextAreaInput extends InputElement
	{
		private int rows = 0;

		public int getRows() {
			return(rows);
		}

		public TextAreaInput setRows(int v) {
			rows = v;
			return(this);
		}
	}

	public static class CodeInput extends InputElement
	{
		private int rows = 0;

		public int getRows() {
			return(rows);
		}

		public CodeInput setRows(int v) {
			rows = v;
			return(this);
		}
	}

	public static class StaticTextElement extends InputElement
	{
	}

	public static class StringListInput extends InputElement
	{
	}

	private Container root = null;
	private cape.Stack<Container> stack = null;

	public FormDeclaration() {
		root = (Container)new VerticalContainer();
		stack = new cape.Stack<Container>();
		stack.push((Container)root);
	}

	public Container getRoot() {
		return(root);
	}

	public Element addElement(Element element) {
		Container current = stack.peek();
		if(current != null) {
			current.addToChildren(element);
		}
		return(element);
	}

	public Element startVerticalContainer() {
		VerticalContainer vc = new VerticalContainer();
		addElement((Element)vc);
		stack.push((Container)vc);
		return((Element)vc);
	}

	public Element endVerticalContainer() {
		Container tcc = stack.peek();
		VerticalContainer cc = null;
		if(tcc instanceof VerticalContainer) {
			cc = (VerticalContainer)tcc;
		}
		if(cc != null) {
			stack.pop();
		}
		return((Element)cc);
	}

	public Element startHorizontalContainer() {
		HorizontalContainer vc = new HorizontalContainer();
		addElement((Element)vc);
		stack.push((Container)vc);
		return((Element)vc);
	}

	public Element endHorizontalContainer() {
		Container tcc = stack.peek();
		HorizontalContainer cc = null;
		if(tcc instanceof HorizontalContainer) {
			cc = (HorizontalContainer)tcc;
		}
		if(cc != null) {
			stack.pop();
		}
		return((Element)cc);
	}

	public Element startGroup(java.lang.String id, java.lang.String label, java.lang.String description) {
		Group group = new Group();
		group.setId(id);
		group.setLabel(label);
		group.setDescription(description);
		addElement((Element)group);
		stack.push((Container)group);
		return((Element)group);
	}

	public Element endGroup() {
		Container tcc = stack.peek();
		Group cc = null;
		if(tcc instanceof Group) {
			cc = (Group)tcc;
		}
		if(cc != null) {
			stack.pop();
		}
		return((Element)cc);
	}

	public Element startTab(java.lang.String id, java.lang.String label) {
		Tab tab = new Tab();
		tab.setId(id);
		tab.setLabel(label);
		addElement((Element)tab);
		stack.push((Container)tab);
		return((Element)tab);
	}

	public Element endTab() {
		Container tcc = stack.peek();
		Tab cc = null;
		if(tcc instanceof Tab) {
			cc = (Tab)tcc;
		}
		if(cc != null) {
			stack.pop();
		}
		return((Element)cc);
	}

	public Element addDynamicElement(java.lang.String type, cape.DynamicMap properties) {
		DynamicElement v = new DynamicElement();
		v.setType(type);
		v.setProperties(properties);
		addElement((Element)v);
		return((Element)v);
	}

	public Element addTextInput(java.lang.String id, java.lang.String label, java.lang.String description) {
		TextInput v = new TextInput();
		v.setId(id);
		v.setLabel(label);
		v.setDescription(description);
		addElement((Element)v);
		return((Element)v);
	}

	public Element addRawTextInput(java.lang.String id, java.lang.String label, java.lang.String description) {
		RawTextInput v = new RawTextInput();
		v.setId(id);
		v.setLabel(label);
		v.setDescription(description);
		addElement((Element)v);
		return((Element)v);
	}

	public Element addPasswordInput(java.lang.String id, java.lang.String label, java.lang.String description) {
		PasswordInput v = new PasswordInput();
		v.setId(id);
		v.setLabel(label);
		v.setDescription(description);
		addElement((Element)v);
		return((Element)v);
	}

	public Element addNameInput(java.lang.String id, java.lang.String label, java.lang.String description) {
		NameInput v = new NameInput();
		v.setId(id);
		v.setLabel(label);
		v.setDescription(description);
		addElement((Element)v);
		return((Element)v);
	}

	public Element addEmailAddressInput(java.lang.String id, java.lang.String label, java.lang.String description) {
		EmailAddressInput v = new EmailAddressInput();
		v.setId(id);
		v.setLabel(label);
		v.setDescription(description);
		addElement((Element)v);
		return((Element)v);
	}

	public Element addPhoneNumberInput(java.lang.String id, java.lang.String label, java.lang.String description) {
		PhoneNumberInput v = new PhoneNumberInput();
		v.setId(id);
		v.setLabel(label);
		v.setDescription(description);
		addElement((Element)v);
		return((Element)v);
	}

	public Element addStreetAddressInput(java.lang.String id, java.lang.String label, java.lang.String description) {
		StreetAddressInput v = new StreetAddressInput();
		v.setId(id);
		v.setLabel(label);
		v.setDescription(description);
		addElement((Element)v);
		return((Element)v);
	}

	public Element addMultipleChoiceInput(java.lang.String id, java.lang.String label, java.lang.String description, java.lang.String[] values) {
		cape.KeyValueList<java.lang.String, java.lang.String> vvs = new cape.KeyValueList<java.lang.String, java.lang.String>();
		if(values != null) {
			int n = 0;
			int m = values.length;
			for(n = 0 ; n < m ; n++) {
				java.lang.String value = values[n];
				if(value != null) {
					java.util.ArrayList<java.lang.String> comps = cape.String.split(value, ':', 2);
					java.lang.String kk = cape.Vector.get(comps, 0);
					java.lang.String vv = cape.Vector.get(comps, 1);
					if(android.text.TextUtils.equals(vv, null)) {
						vv = kk;
					}
					vvs.add((java.lang.String)kk, (java.lang.String)vv);
				}
			}
		}
		return(addMultipleChoiceInput(id, label, description, vvs));
	}

	public Element addMultipleChoiceInput(java.lang.String id, java.lang.String label, java.lang.String description, cape.KeyValueList<java.lang.String, java.lang.String> values) {
		MultipleChoiceInput v = new MultipleChoiceInput();
		v.setId(id);
		v.setLabel(label);
		v.setDescription(description);
		v.setValues(values);
		addElement((Element)v);
		return((Element)v);
	}

	public Element addDateInput(java.lang.String id, java.lang.String label, java.lang.String description, int skipYears) {
		DateInput v = new DateInput();
		v.setId(id);
		v.setLabel(label);
		v.setDescription(description);
		v.setSkipYears(skipYears);
		addElement((Element)v);
		return((Element)v);
	}

	public Element addPhotoCaptureInput(java.lang.String id, java.lang.String label, java.lang.String description, cave.Image defImage) {
		PhotoCaptureInput v = new PhotoCaptureInput();
		v.setId(id);
		v.setLabel(label);
		v.setDescription(description);
		v.setDefaultImage(defImage);
		addElement((Element)v);
		return((Element)v);
	}

	public Element addCodeInput(java.lang.String id, java.lang.String label, java.lang.String description, int rows) {
		CodeInput v = new CodeInput();
		v.setId(id);
		v.setLabel(label);
		v.setDescription(description);
		v.setRows(rows);
		addElement((Element)v);
		return((Element)v);
	}

	public Element addTextAreaInput(java.lang.String id, java.lang.String label, java.lang.String description, int rows) {
		TextAreaInput v = new TextAreaInput();
		v.setId(id);
		v.setLabel(label);
		v.setDescription(description);
		v.setRows(rows);
		addElement((Element)v);
		return((Element)v);
	}

	public Element addStaticTextElement(java.lang.String id, java.lang.String label, java.lang.String description) {
		StaticTextElement v = new StaticTextElement();
		v.setId(id);
		v.setLabel(label);
		v.setDescription(description);
		addElement((Element)v);
		return((Element)v);
	}

	public Element addRadioGroupInput(java.lang.String id, java.lang.String label, java.lang.String description, java.lang.String groupname, java.util.ArrayList<java.lang.String> items) {
		RadioGroupInput v = new RadioGroupInput();
		v.setId(id);
		v.setLabel(label);
		v.setDescription(description);
		v.setItems(items);
		v.setGroupName(groupname);
		addElement((Element)v);
		return((Element)v);
	}

	public Element addWithIconInput(java.lang.String id, java.lang.String label, java.lang.String description, cave.Image icon, samx.function.Procedure0 action) {
		WithIconInput v = new WithIconInput();
		v.setId(id);
		v.setLabel(label);
		v.setDescription(description);
		v.setIcon(icon);
		v.setAction(action);
		addElement((Element)v);
		return((Element)v);
	}

	public Element addStringListInput(java.lang.String id, java.lang.String label, java.lang.String description) {
		StringListInput v = new StringListInput();
		v.setId(id);
		v.setLabel(label);
		v.setDescription(description);
		addElement((Element)v);
		return((Element)v);
	}
}
