
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

public class RichTextSegment
{
	private java.lang.String text = null;
	private boolean bold = false;
	private boolean italic = false;
	private boolean underline = false;
	private java.lang.String color = null;
	private java.lang.String link = null;
	private java.lang.String reference = null;
	private boolean isInline = false;
	private boolean linkPopup = false;

	public void addMarkupModifiers(cape.StringBuilder sb) {
		if(bold) {
			sb.append("**");
		}
		if(italic) {
			sb.append("''");
		}
		if(underline) {
			sb.append("__");
		}
	}

	public java.lang.String toMarkup() {
		cape.StringBuilder sb = new cape.StringBuilder();
		addMarkupModifiers(sb);
		if(cape.String.isEmpty(link) == false) {
			sb.append('[');
			if(isInline) {
				sb.append('>');
			}
			sb.append(link);
			if(cape.String.isEmpty(text) == false) {
				sb.append('|');
				sb.append(text);
			}
			sb.append(']');
		}
		else if(cape.String.isEmpty(reference) == false) {
			sb.append('{');
			if(isInline) {
				sb.append('>');
			}
			sb.append(reference);
			if(cape.String.isEmpty(text) == false) {
				sb.append('|');
				sb.append(text);
			}
			sb.append('}');
		}
		else {
			sb.append(text);
		}
		addMarkupModifiers(sb);
		return(sb.toString());
	}

	public cape.DynamicMap toJson() {
		cape.DynamicMap v = new cape.DynamicMap();
		v.set("text", (java.lang.Object)text);
		if(isInline) {
			v.set("inline", isInline);
		}
		if(bold) {
			v.set("bold", bold);
		}
		if(italic) {
			v.set("italic", italic);
		}
		if(underline) {
			v.set("underline", underline);
		}
		if(cape.String.isEmpty(color) == false) {
			v.set("color", (java.lang.Object)color);
		}
		if(cape.String.isEmpty(link) == false) {
			v.set("link", (java.lang.Object)link);
		}
		if(cape.String.isEmpty(reference) == false) {
			v.set("reference", (java.lang.Object)reference);
		}
		return(v);
	}

	public java.lang.String getText() {
		return(text);
	}

	public RichTextSegment setText(java.lang.String v) {
		text = v;
		return(this);
	}

	public boolean getBold() {
		return(bold);
	}

	public RichTextSegment setBold(boolean v) {
		bold = v;
		return(this);
	}

	public boolean getItalic() {
		return(italic);
	}

	public RichTextSegment setItalic(boolean v) {
		italic = v;
		return(this);
	}

	public boolean getUnderline() {
		return(underline);
	}

	public RichTextSegment setUnderline(boolean v) {
		underline = v;
		return(this);
	}

	public java.lang.String getColor() {
		return(color);
	}

	public RichTextSegment setColor(java.lang.String v) {
		color = v;
		return(this);
	}

	public java.lang.String getLink() {
		return(link);
	}

	public RichTextSegment setLink(java.lang.String v) {
		link = v;
		return(this);
	}

	public java.lang.String getReference() {
		return(reference);
	}

	public RichTextSegment setReference(java.lang.String v) {
		reference = v;
		return(this);
	}

	public boolean getIsInline() {
		return(isInline);
	}

	public RichTextSegment setIsInline(boolean v) {
		isInline = v;
		return(this);
	}

	public boolean getLinkPopup() {
		return(linkPopup);
	}

	public RichTextSegment setLinkPopup(boolean v) {
		linkPopup = v;
		return(this);
	}
}
