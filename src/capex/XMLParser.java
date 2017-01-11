
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

public class XMLParser
{
	public static cape.DynamicMap parseAsTreeObject(java.lang.String xml, boolean ignoreWhiteSpace) {
		cape.DynamicMap root = null;
		cape.Stack<cape.DynamicMap> stack = new cape.Stack<cape.DynamicMap>();
		XMLParser pp = forString(xml);
		pp.setIgnoreWhiteSpace(ignoreWhiteSpace);
		while(true) {
			java.lang.Object o = pp.next();
			if(o == null) {
				break;
			}
			if(o instanceof StartElement) {
				cape.DynamicMap nn = new cape.DynamicMap();
				nn.set("name", (java.lang.Object)((StartElement)o).getName());
				nn.set("attributes", (java.lang.Object)((StartElement)o).getParams());
				if(root == null) {
					root = nn;
					stack.push((cape.DynamicMap)nn);
				}
				else {
					cape.DynamicMap current = stack.peek();
					if(current == null) {
						current = root;
					}
					cape.DynamicVector children = current.getDynamicVector("children");
					if(children == null) {
						children = new cape.DynamicVector();
						current.set("children", (java.lang.Object)children);
					}
					children.append((java.lang.Object)nn);
					stack.push((cape.DynamicMap)nn);
				}
			}
			else if(o instanceof EndElement) {
				stack.pop();
			}
			else if(o instanceof CharacterData) {
				cape.DynamicMap current = stack.peek();
				if(current != null) {
					cape.DynamicVector children = current.getDynamicVector("children");
					if(children == null) {
						children = new cape.DynamicVector();
						current.set("children", (java.lang.Object)children);
					}
					children.append((java.lang.Object)((CharacterData)o).getData());
				}
			}
		}
		return(root);
	}

	public static cape.DynamicMap parseAsTreeObject(java.lang.String xml) {
		return(capex.XMLParser.parseAsTreeObject(xml, true));
	}

	public static class StartElement
	{
		private java.lang.String name = null;
		private cape.DynamicMap params = null;

		public java.lang.String getParam(java.lang.String pname) {
			if(params == null) {
				return(null);
			}
			return(params.getString(pname));
		}

		public java.lang.String getName() {
			return(name);
		}

		public StartElement setName(java.lang.String v) {
			name = v;
			return(this);
		}

		public cape.DynamicMap getParams() {
			return(params);
		}

		public StartElement setParams(cape.DynamicMap v) {
			params = v;
			return(this);
		}
	}

	public static class EndElement
	{
		private java.lang.String name = null;

		public java.lang.String getName() {
			return(name);
		}

		public EndElement setName(java.lang.String v) {
			name = v;
			return(this);
		}
	}

	public static class CharacterData
	{
		private java.lang.String data = null;

		public java.lang.String getData() {
			return(data);
		}

		public CharacterData setData(java.lang.String v) {
			data = v;
			return(this);
		}
	}

	public static class Comment
	{
		private java.lang.String text = null;

		public java.lang.String getText() {
			return(text);
		}

		public Comment setText(java.lang.String v) {
			text = v;
			return(this);
		}
	}

	private cape.CharacterIterator it = null;
	private java.lang.Object nextQueue = null;
	private java.lang.String cdataStart = "![CDATA[";
	private java.lang.String commentStart = "!--";
	private cape.StringBuilder tag = null;
	private cape.StringBuilder def = null;
	private cape.StringBuilder cdata = null;
	private cape.StringBuilder comment = null;
	private boolean ignoreWhiteSpace = false;

	public static XMLParser forFile(cape.File file) {
		if(file == null) {
			return(null);
		}
		cape.FileReader reader = file.read();
		if(reader == null) {
			return(null);
		}
		XMLParser v = new XMLParser();
		v.it = (cape.CharacterIterator)new cape.CharacterIteratorForReader((cape.Reader)reader);
		return(v);
	}

	public static XMLParser forString(java.lang.String string) {
		if(android.text.TextUtils.equals(string, null)) {
			return(null);
		}
		XMLParser v = new XMLParser();
		v.it = (cape.CharacterIterator)new cape.CharacterIteratorForString(string);
		return(v);
	}

	public static XMLParser forIterator(cape.CharacterIterator it) {
		if(it == null) {
			return(null);
		}
		XMLParser v = new XMLParser();
		v.it = it;
		return(v);
	}

	private java.lang.Object onTagString(java.lang.String tagstring) {
		if(cape.String.charAt(tagstring, 0) == '/') {
			return((java.lang.Object)new EndElement().setName(cape.String.subString(tagstring, 1)));
		}
		cape.StringBuilder element = new cape.StringBuilder();
		cape.DynamicMap params = new cape.DynamicMap();
		cape.CharacterIteratorForString it = new cape.CharacterIteratorForString(tagstring);
		char c = ' ';
		while((c = it.getNextChar()) > 0) {
			if(((((c == ' ') || (c == '\t')) || (c == '\n')) || (c == '\r')) || (c == '/')) {
				if(element.count() > 0) {
					break;
				}
			}
			else {
				element.append(c);
			}
		}
		while((c > 0) && (c != '/')) {
			cape.StringBuilder pname = new cape.StringBuilder();
			cape.StringBuilder pval = new cape.StringBuilder();
			while((((c == ' ') || (c == '\t')) || (c == '\n')) || (c == '\r')) {
				c = it.getNextChar();
			}
			while((((((c > 0) && (c != ' ')) && (c != '\t')) && (c != '\n')) && (c != '\r')) && (c != '=')) {
				pname.append(c);
				c = it.getNextChar();
			}
			while((((c == ' ') || (c == '\t')) || (c == '\n')) || (c == '\r')) {
				c = it.getNextChar();
			}
			if(c != '=') {
				;
			}
			else {
				c = it.getNextChar();
				while((((c == ' ') || (c == '\t')) || (c == '\n')) || (c == '\r')) {
					c = it.getNextChar();
				}
				if(c != '\"') {
					;
					while(((((c > 0) && (c != ' ')) && (c != '\t')) && (c != '\n')) && (c != '\r')) {
						pval.append(c);
						c = it.getNextChar();
					}
					while((((c == ' ') || (c == '\t')) || (c == '\n')) || (c == '\r')) {
						c = it.getNextChar();
					}
				}
				else {
					c = it.getNextChar();
					while((c > 0) && (c != '\"')) {
						pval.append(c);
						c = it.getNextChar();
					}
					if(c != '\"') {
						;
					}
					else {
						c = it.getNextChar();
					}
					while((((c == ' ') || (c == '\t')) || (c == '\n')) || (c == '\r')) {
						c = it.getNextChar();
					}
				}
			}
			java.lang.String pnamestr = pname.toString();
			java.lang.String pvalstr = pval.toString();
			params.set(pnamestr, (java.lang.Object)pvalstr);
		}
		java.lang.String els = element.toString();
		if(c == '/') {
			nextQueue = (java.lang.Object)new EndElement().setName(els);
		}
		return((java.lang.Object)new StartElement().setName(els).setParams(params));
	}

	private boolean isOnlyWhiteSpace(java.lang.String str) {
		if(android.text.TextUtils.equals(str, null)) {
			return(true);
		}
		char[] array = cape.String.toCharArray(str);
		if(array != null) {
			int n = 0;
			int m = array.length;
			for(n = 0 ; n < m ; n++) {
				char c = array[n];
				if((((c == ' ') || (c == '\t')) || (c == '\n')) || (c == '\r')) {
					;
				}
				else {
					return(false);
				}
			}
		}
		return(true);
	}

	public java.lang.Object next() {
		if(nextQueue != null) {
			java.lang.Object v = nextQueue;
			nextQueue = null;
			return(v);
		}
		while(it.hasEnded() == false) {
			char nxb = it.getNextChar();
			if(nxb < 1) {
				continue;
			}
			if(tag != null) {
				if(nxb == '>') {
					java.lang.String ts = tag.toString();
					tag = null;
					return(onTagString(ts));
				}
				tag.append(nxb);
				if(((nxb == '[') && (tag.count() == cape.String.getLength(cdataStart))) && cape.String.equals(cdataStart, tag.toString())) {
					tag = null;
					cdata = new cape.StringBuilder();
				}
				else if(((nxb == '-') && (tag.count() == cape.String.getLength(commentStart))) && cape.String.equals(commentStart, tag.toString())) {
					tag = null;
					comment = new cape.StringBuilder();
				}
			}
			else if(cdata != null) {
				char c0 = nxb;
				char c1 = ' ';
				char c2 = ' ';
				if(c0 == ']') {
					c1 = it.getNextChar();
					if(c1 == ']') {
						c2 = it.getNextChar();
						if(c2 == '>') {
							java.lang.String dd = cdata.toString();
							cdata = null;
							return((java.lang.Object)new CharacterData().setData(dd));
						}
						else {
							it.moveToPreviousChar();
							it.moveToPreviousChar();
							cdata.append(c0);
						}
					}
					else {
						it.moveToPreviousChar();
						cdata.append(c0);
					}
				}
				else {
					cdata.append(c0);
				}
			}
			else if(comment != null) {
				char c0 = nxb;
				char c1 = ' ';
				char c2 = ' ';
				if(c0 == '-') {
					c1 = it.getNextChar();
					if(c1 == '-') {
						c2 = it.getNextChar();
						if(c2 == '>') {
							java.lang.String ct = comment.toString();
							comment = null;
							return((java.lang.Object)new Comment().setText(ct));
						}
						else {
							it.moveToPreviousChar();
							it.moveToPreviousChar();
							comment.append(c0);
						}
					}
					else {
						it.moveToPreviousChar();
						comment.append(c0);
					}
				}
				else {
					comment.append(c0);
				}
			}
			else if(nxb == '<') {
				if(def != null) {
					java.lang.String cd = def.toString();
					def = null;
					if(ignoreWhiteSpace && !(android.text.TextUtils.equals(cd, null))) {
						if(isOnlyWhiteSpace(cd)) {
							cd = null;
						}
					}
					if(!(android.text.TextUtils.equals(cd, null))) {
						it.moveToPreviousChar();
						return((java.lang.Object)new CharacterData().setData(cd));
					}
				}
				tag = new cape.StringBuilder();
			}
			else {
				if(def == null) {
					def = new cape.StringBuilder();
				}
				def.append(nxb);
			}
		}
		return(null);
	}

	public boolean getIgnoreWhiteSpace() {
		return(ignoreWhiteSpace);
	}

	public XMLParser setIgnoreWhiteSpace(boolean v) {
		ignoreWhiteSpace = v;
		return(this);
	}
}
