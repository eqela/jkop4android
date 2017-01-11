
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

public class RichTextBlockParagraph extends RichTextParagraph
{
	private java.lang.String id = null;
	private java.lang.String text = null;

	@Override
	public java.lang.String toMarkup() {
		cape.StringBuilder sb = new cape.StringBuilder();
		java.lang.String delim = null;
		if(cape.String.isEmpty(id)) {
			delim = "-- ";
		}
		else {
			delim = ("-- " + id) + " --";
		}
		sb.append(delim);
		sb.append('\n');
		if(!(android.text.TextUtils.equals(text, null))) {
			sb.append(text);
			if(cape.String.endsWith(text, "\n") == false) {
				sb.append('\n');
			}
		}
		sb.append(delim);
		return(sb.toString());
	}

	@Override
	public java.lang.String toText() {
		return(text);
	}

	@Override
	public cape.DynamicMap toJson() {
		cape.DynamicMap map = new cape.DynamicMap();
		map.set("type", (java.lang.Object)"block");
		map.set("id", (java.lang.Object)id);
		map.set("text", (java.lang.Object)text);
		return(map);
	}

	@Override
	public java.lang.String toHtml(RichTextDocumentReferenceResolver refs) {
		java.lang.String ids = "";
		if(cape.String.isEmpty(id) == false) {
			ids = " " + capex.HTMLString.sanitize(id);
		}
		RichTextStyledParagraph content = capex.RichTextStyledParagraph.forString(text);
		return(((("<div class=\"block" + ids) + "\">") + content.toHtml(refs)) + "</div>");
	}

	public java.lang.String getId() {
		return(id);
	}

	public RichTextBlockParagraph setId(java.lang.String v) {
		id = v;
		return(this);
	}

	public java.lang.String getText() {
		return(text);
	}

	public RichTextBlockParagraph setText(java.lang.String v) {
		text = v;
		return(this);
	}
}
