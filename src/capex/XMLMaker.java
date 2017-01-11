
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

package capex;

public class XMLMaker implements cape.StringObject
{
	public static class CData implements cape.StringObject
	{
		private java.lang.String text = null;
		private boolean simple = false;
		private boolean singleLine = false;

		public CData(java.lang.String t) {
			text = t;
		}

		public java.lang.String toString() {
			cape.StringBuilder sb = new cape.StringBuilder();
			if(simple) {
				sb.append(text);
			}
			else {
				sb.append("<![CDATA[");
				sb.append(text);
				sb.append("]]>");
			}
			return(sb.toString());
		}

		public java.lang.String getText() {
			return(text);
		}

		public CData setText(java.lang.String v) {
			text = v;
			return(this);
		}

		public boolean getSimple() {
			return(simple);
		}

		public CData setSimple(boolean v) {
			simple = v;
			return(this);
		}

		public boolean getSingleLine() {
			return(singleLine);
		}

		public CData setSingleLine(boolean v) {
			singleLine = v;
			return(this);
		}
	}

	public static class Element extends StartElement
	{
		public Element(java.lang.String name) {
			super(name);
			setSingle(true);
		}
	}

	public static class EndElement implements cape.StringObject
	{
		private java.lang.String name = null;

		public EndElement(java.lang.String n) {
			name = n;
		}

		public java.lang.String toString() {
			return(("</" + getName()) + ">");
		}

		public java.lang.String getName() {
			return(name);
		}

		public EndElement setName(java.lang.String v) {
			name = v;
			return(this);
		}
	}

	public static class StartElement implements cape.StringObject
	{
		private java.lang.String name = null;
		private java.util.HashMap<java.lang.String,java.lang.String> attributes = null;
		private boolean single = false;
		private boolean singleLine = false;

		public StartElement(java.lang.String n) {
			name = n;
			attributes = new java.util.HashMap<java.lang.String,java.lang.String>();
		}

		public StartElement attribute(java.lang.String key, java.lang.String value) {
			cape.Map.setValue(attributes, key, value);
			return(this);
		}

		public java.lang.String toString() {
			cape.StringBuilder sb = new cape.StringBuilder();
			sb.append('<');
			sb.append(name);
			java.util.ArrayList<java.lang.String> keys = cape.Map.getKeys(attributes);
			if(keys != null) {
				int n = 0;
				int m = keys.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.String key = keys.get(n);
					if(key != null) {
						sb.append(' ');
						sb.append(key);
						sb.append('=');
						sb.append('\"');
						java.lang.String val = cape.Map.getValue(attributes, key);
						sb.append(val);
						sb.append('\"');
					}
				}
			}
			if(single) {
				sb.append(' ');
				sb.append('/');
			}
			sb.append('>');
			return(sb.toString());
		}

		public java.lang.String getName() {
			return(name);
		}

		public StartElement setName(java.lang.String v) {
			name = v;
			return(this);
		}

		public java.util.HashMap<java.lang.String,java.lang.String> getAttributes() {
			return(attributes);
		}

		public StartElement setAttributes(java.util.HashMap<java.lang.String,java.lang.String> v) {
			attributes = v;
			return(this);
		}

		public boolean getSingle() {
			return(single);
		}

		public StartElement setSingle(boolean v) {
			single = v;
			return(this);
		}

		public boolean getSingleLine() {
			return(singleLine);
		}

		public StartElement setSingleLine(boolean v) {
			singleLine = v;
			return(this);
		}
	}

	public static class CustomXML
	{
		private java.lang.String string = null;

		public CustomXML(java.lang.String s) {
			string = s;
		}

		public java.lang.String getString() {
			return(string);
		}

		public CustomXML setString(java.lang.String v) {
			string = v;
			return(this);
		}
	}

	private java.util.ArrayList<java.lang.Object> elements = null;
	private java.lang.String customHeader = null;
	private boolean singleLine = false;
	private java.lang.String header = null;

	public XMLMaker() {
		elements = new java.util.ArrayList<java.lang.Object>();
		header = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
	}

	public XMLMaker start(java.lang.String element, boolean singleLine) {
		add((java.lang.Object)new StartElement(element).setSingleLine(singleLine));
		return(this);
	}

	public XMLMaker start(java.lang.String element) {
		return(start(element, false));
	}

	public XMLMaker start(java.lang.String element, java.lang.String k1, java.lang.String v1, boolean singleLine) {
		StartElement v = new StartElement(element);
		v.setSingleLine(singleLine);
		if(!(android.text.TextUtils.equals(k1, null))) {
			v.attribute(k1, v1);
		}
		add((java.lang.Object)v);
		return(this);
	}

	public XMLMaker start(java.lang.String element, java.lang.String k1, java.lang.String v1) {
		return(start(element, k1, v1, false));
	}

	public XMLMaker start(java.lang.String element, java.util.HashMap<java.lang.String,java.lang.String> attrs, boolean singleLine) {
		StartElement v = new StartElement(element);
		v.setSingleLine(singleLine);
		if(attrs != null) {
			java.util.ArrayList<java.lang.String> keys = cape.Map.getKeys(attrs);
			if(keys != null) {
				int n = 0;
				int m = keys.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.String key = keys.get(n);
					if(key != null) {
						java.lang.String val = attrs.get(key);
						v.attribute(key, val);
					}
				}
			}
		}
		add((java.lang.Object)v);
		return(this);
	}

	public XMLMaker start(java.lang.String element, java.util.HashMap<java.lang.String,java.lang.String> attrs) {
		return(start(element, attrs, false));
	}

	public XMLMaker element(java.lang.String element, java.util.HashMap<java.lang.String,java.lang.String> attrs) {
		Element v = new Element(element);
		if(attrs != null) {
			java.util.ArrayList<java.lang.String> keys = cape.Map.getKeys(attrs);
			if(keys != null) {
				int n = 0;
				int m = keys.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.String key = keys.get(n);
					if(key != null) {
						java.lang.String val = attrs.get(key);
						v.attribute(key, val);
					}
				}
			}
		}
		add((java.lang.Object)v);
		return(this);
	}

	public XMLMaker end(java.lang.String element) {
		add((java.lang.Object)new EndElement(element));
		return(this);
	}

	public XMLMaker text(java.lang.String element) {
		add((java.lang.Object)element);
		return(this);
	}

	public XMLMaker cdata(java.lang.String element) {
		add((java.lang.Object)new CData(element));
		return(this);
	}

	public XMLMaker textElement(java.lang.String element, java.lang.String text) {
		add((java.lang.Object)new StartElement(element).setSingleLine(true));
		add((java.lang.Object)text);
		add((java.lang.Object)new EndElement(element));
		return(this);
	}

	public XMLMaker add(java.lang.Object o) {
		if(o != null) {
			cape.Vector.append(elements, o);
		}
		return(this);
	}

	private void append(cape.StringBuilder sb, int level, java.lang.String str, boolean noIndent, boolean noNewLine) {
		int n = 0;
		if((singleLine == false) && (noIndent == false)) {
			for(n = 0 ; n < level ; n++) {
				sb.append(' ');
				sb.append(' ');
			}
		}
		sb.append(str);
		if((singleLine == false) && (noNewLine == false)) {
			sb.append('\n');
		}
	}

	private java.lang.String escapeString(java.lang.String str) {
		cape.StringBuilder sb = new cape.StringBuilder();
		if(!(android.text.TextUtils.equals(str, null))) {
			char[] array = cape.String.toCharArray(str);
			if(array != null) {
				int n = 0;
				int m = array.length;
				for(n = 0 ; n < m ; n++) {
					char c = array[n];
					if(c == '\"') {
						sb.append("&quot;");
					}
					else if(c == '\'') {
						sb.append("&apos;");
					}
					else if(c == '<') {
						sb.append("&lt;");
					}
					else if(c == '>') {
						sb.append("&gt;");
					}
					else if(c == '&') {
						sb.append("&amp;");
					}
					else {
						sb.append(c);
					}
				}
			}
		}
		return(sb.toString());
	}

	public java.lang.String toString() {
		cape.StringBuilder sb = new cape.StringBuilder();
		int level = 0;
		if(!(android.text.TextUtils.equals(header, null))) {
			append(sb, level, header, false, false);
		}
		if(!(android.text.TextUtils.equals(customHeader, null))) {
			sb.append(customHeader);
		}
		boolean singleLine = false;
		if(elements != null) {
			int n = 0;
			int m = elements.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.Object o = elements.get(n);
				if(o != null) {
					if(o instanceof Element) {
						append(sb, level, ((Element)o).toString(), singleLine, singleLine);
					}
					else if(o instanceof StartElement) {
						singleLine = ((StartElement)o).getSingleLine();
						append(sb, level, ((StartElement)o).toString(), false, singleLine);
						level++;
					}
					else if(o instanceof EndElement) {
						level--;
						append(sb, level, ((EndElement)o).toString(), singleLine, false);
						singleLine = false;
					}
					else if(o instanceof CustomXML) {
						append(sb, level, ((CustomXML)o).getString(), singleLine, singleLine);
					}
					else if(o instanceof java.lang.String) {
						append(sb, level, escapeString((java.lang.String)o), singleLine, singleLine);
					}
					else if(o instanceof CData) {
						append(sb, level, ((CData)o).toString(), singleLine, ((CData)o).getSingleLine());
					}
				}
			}
		}
		return(sb.toString());
	}

	public java.util.ArrayList<java.lang.Object> getElements() {
		return(elements);
	}

	public XMLMaker setElements(java.util.ArrayList<java.lang.Object> v) {
		elements = v;
		return(this);
	}

	public java.lang.String getCustomHeader() {
		return(customHeader);
	}

	public XMLMaker setCustomHeader(java.lang.String v) {
		customHeader = v;
		return(this);
	}

	public boolean getSingleLine() {
		return(singleLine);
	}

	public XMLMaker setSingleLine(boolean v) {
		singleLine = v;
		return(this);
	}

	public java.lang.String getHeader() {
		return(header);
	}

	public XMLMaker setHeader(java.lang.String v) {
		header = v;
		return(this);
	}
}
