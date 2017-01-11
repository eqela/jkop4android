
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

public class RichTextContentParagraph extends RichTextParagraph
{
	private java.lang.String contentId = null;

	@Override
	public java.lang.String toMarkup() {
		return(("@content " + contentId) + "\n");
	}

	@Override
	public java.lang.String toText() {
		return(("[content:" + contentId) + "]\n");
	}

	@Override
	public cape.DynamicMap toJson() {
		cape.DynamicMap map = new cape.DynamicMap();
		map.set("type", (java.lang.Object)"content");
		map.set("id", (java.lang.Object)contentId);
		return(map);
	}

	@Override
	public java.lang.String toHtml(RichTextDocumentReferenceResolver refs) {
		java.lang.String cc = null;
		if((refs != null) && !(android.text.TextUtils.equals(contentId, null))) {
			cc = refs.getContentString(contentId);
		}
		if(android.text.TextUtils.equals(cc, null)) {
			cc = "";
		}
		return(cc);
	}

	public java.lang.String getContentId() {
		return(contentId);
	}

	public RichTextContentParagraph setContentId(java.lang.String v) {
		contentId = v;
		return(this);
	}
}
