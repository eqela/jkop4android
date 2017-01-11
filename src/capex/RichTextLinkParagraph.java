
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

public class RichTextLinkParagraph extends RichTextParagraph
{
	private java.lang.String link = null;
	private java.lang.String text = null;
	private boolean popup = false;

	@Override
	public java.lang.String toMarkup() {
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append("@link ");
		sb.append(link);
		sb.append(' ');
		sb.append('\"');
		if(cape.String.isEmpty(text) == false) {
			sb.append(text);
		}
		sb.append('\"');
		if(popup) {
			sb.append(" popup");
		}
		return(sb.toString());
	}

	@Override
	public java.lang.String toText() {
		java.lang.String v = text;
		if(cape.String.isEmpty(v)) {
			v = link;
		}
		return(v);
	}

	@Override
	public cape.DynamicMap toJson() {
		cape.DynamicMap v = new cape.DynamicMap();
		v.set("type", (java.lang.Object)"link");
		v.set("link", (java.lang.Object)link);
		v.set("text", (java.lang.Object)text);
		return(v);
	}

	@Override
	public java.lang.String toHtml(RichTextDocumentReferenceResolver refs) {
		java.lang.String href = capex.HTMLString.sanitize(link);
		java.lang.String tt = text;
		if(cape.String.isEmpty(tt)) {
			tt = href;
		}
		if(cape.String.isEmpty(tt)) {
			tt = "(empty link)";
		}
		java.lang.String targetblank = "";
		if(popup) {
			targetblank = " target=\"_blank\"";
		}
		return(((((("<p class=\"link\"><a href=\"" + href) + "\"") + targetblank) + ">") + tt) + "</a></p>\n");
	}

	public java.lang.String getLink() {
		return(link);
	}

	public RichTextLinkParagraph setLink(java.lang.String v) {
		link = v;
		return(this);
	}

	public java.lang.String getText() {
		return(text);
	}

	public RichTextLinkParagraph setText(java.lang.String v) {
		text = v;
		return(this);
	}

	public boolean getPopup() {
		return(popup);
	}

	public RichTextLinkParagraph setPopup(boolean v) {
		popup = v;
		return(this);
	}
}
