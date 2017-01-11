
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

public class RichTextReferenceParagraph extends RichTextParagraph
{
	private java.lang.String reference = null;
	private java.lang.String text = null;

	@Override
	public java.lang.String toMarkup() {
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append("@reference ");
		sb.append(reference);
		if(cape.String.isEmpty(text) == false) {
			sb.append(' ');
			sb.append('\"');
			sb.append(text);
			sb.append('\"');
		}
		return(sb.toString());
	}

	@Override
	public java.lang.String toText() {
		java.lang.String v = text;
		if(cape.String.isEmpty(text)) {
			v = reference;
		}
		return(v);
	}

	@Override
	public cape.DynamicMap toJson() {
		cape.DynamicMap v = new cape.DynamicMap();
		v.set("type", (java.lang.Object)"reference");
		v.set("reference", (java.lang.Object)reference);
		v.set("text", (java.lang.Object)text);
		return(v);
	}

	@Override
	public java.lang.String toHtml(RichTextDocumentReferenceResolver refs) {
		java.lang.String reftitle = null;
		java.lang.String href = null;
		if(cape.String.isEmpty(text) == false) {
			reftitle = text;
		}
		if(cape.String.isEmpty(reftitle)) {
			if(refs != null) {
				reftitle = refs.getReferenceTitle(reference);
			}
			else {
				reftitle = reference;
			}
		}
		if(refs != null) {
			href = refs.getReferenceHref(reference);
		}
		else {
			href = reference;
		}
		if(cape.String.isEmpty(href)) {
			return("");
		}
		if(cape.String.isEmpty(reftitle)) {
			reftitle = href;
		}
		return(((("<p class=\"reference\"><a href=\"" + capex.HTMLString.sanitize(href)) + "\">") + capex.HTMLString.sanitize(reftitle)) + "</a></p>\n");
	}

	public java.lang.String getReference() {
		return(reference);
	}

	public RichTextReferenceParagraph setReference(java.lang.String v) {
		reference = v;
		return(this);
	}

	public java.lang.String getText() {
		return(text);
	}

	public RichTextReferenceParagraph setText(java.lang.String v) {
		text = v;
		return(this);
	}
}
