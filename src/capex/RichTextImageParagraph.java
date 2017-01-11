
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

public class RichTextImageParagraph extends RichTextParagraph
{
	private java.lang.String filename = null;
	private int width = 100;

	@Override
	public java.lang.String toMarkup() {
		if(width >= 100) {
			return(("@image " + filename) + "\n");
		}
		if(width >= 75) {
			return(("@image75 " + filename) + "\n");
		}
		if(width >= 50) {
			return(("@image50 " + filename) + "\n");
		}
		return(("@image25 " + filename) + "\n");
	}

	@Override
	public java.lang.String toText() {
		return(("[image:" + filename) + "]\n");
	}

	@Override
	public cape.DynamicMap toJson() {
		cape.DynamicMap v = new cape.DynamicMap();
		v.set("type", (java.lang.Object)"image");
		v.set("width", width);
		v.set("filename", (java.lang.Object)filename);
		return(v);
	}

	@Override
	public java.lang.String toHtml(RichTextDocumentReferenceResolver refs) {
		cape.StringBuilder sb = new cape.StringBuilder();
		if(width >= 100) {
			sb.append("<div class=\"img100\">");
		}
		else if(width >= 75) {
			sb.append("<div class=\"img75\">");
		}
		else if(width >= 50) {
			sb.append("<div class=\"img50\">");
		}
		else {
			sb.append("<div class=\"img25\">");
		}
		sb.append(("<img src=\"" + capex.HTMLString.sanitize(filename)) + "\" />");
		sb.append("</div>\n");
		return(sb.toString());
	}

	public java.lang.String getFilename() {
		return(filename);
	}

	public RichTextImageParagraph setFilename(java.lang.String v) {
		filename = v;
		return(this);
	}

	public int getWidth() {
		return(width);
	}

	public RichTextImageParagraph setWidth(int v) {
		width = v;
		return(this);
	}
}
