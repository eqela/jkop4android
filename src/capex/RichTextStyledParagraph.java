
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

public class RichTextStyledParagraph extends RichTextParagraph
{
	public static RichTextStyledParagraph forString(java.lang.String text) {
		RichTextStyledParagraph rtsp = new RichTextStyledParagraph();
		return(rtsp.parse(text));
	}

	private int heading = 0;
	private java.util.ArrayList<RichTextSegment> segments = null;

	public boolean isHeading() {
		if(heading > 0) {
			return(true);
		}
		return(false);
	}

	public java.lang.String getTextContent() {
		cape.StringBuilder sb = new cape.StringBuilder();
		if(segments != null) {
			int n = 0;
			int m = segments.size();
			for(n = 0 ; n < m ; n++) {
				RichTextSegment segment = segments.get(n);
				if(segment != null) {
					sb.append(segment.getText());
				}
			}
		}
		return(sb.toString());
	}

	@Override
	public cape.DynamicMap toJson() {
		java.util.ArrayList<java.lang.Object> segs = new java.util.ArrayList<java.lang.Object>();
		if(segments != null) {
			int n = 0;
			int m = segments.size();
			for(n = 0 ; n < m ; n++) {
				RichTextSegment segment = segments.get(n);
				if(segment != null) {
					cape.DynamicMap segj = segment.toJson();
					if(segj != null) {
						cape.Vector.append(segs, segj);
					}
				}
			}
		}
		cape.DynamicMap v = new cape.DynamicMap();
		v.set("type", (java.lang.Object)"styled");
		v.set("heading", heading);
		v.set("segments", (java.lang.Object)segs);
		return(v);
	}

	@Override
	public java.lang.String toText() {
		cape.StringBuilder sb = new cape.StringBuilder();
		if(segments != null) {
			int n = 0;
			int m = segments.size();
			for(n = 0 ; n < m ; n++) {
				RichTextSegment sg = segments.get(n);
				if(sg != null) {
					sb.append(sg.getText());
					java.lang.String link = sg.getLink();
					if(cape.String.isEmpty(link) == false) {
						sb.append((" (" + link) + ")");
					}
					java.lang.String ref = sg.getReference();
					if(cape.String.isEmpty(ref) == false) {
						sb.append((" {" + ref) + "}");
					}
				}
			}
		}
		return(sb.toString());
	}

	@Override
	public java.lang.String toHtml(RichTextDocumentReferenceResolver refs) {
		cape.StringBuilder sb = new cape.StringBuilder();
		java.lang.String tag = "p";
		if(heading > 0) {
			tag = "h" + cape.String.forInteger(heading);
		}
		sb.append("<");
		sb.append(tag);
		sb.append(">");
		if(segments != null) {
			int n = 0;
			int m = segments.size();
			for(n = 0 ; n < m ; n++) {
				RichTextSegment sg = segments.get(n);
				if(sg != null) {
					boolean aOpen = false;
					java.lang.String text = sg.getText();
					java.lang.String link = sg.getLink();
					if(cape.String.isEmpty(link) == false) {
						if(sg.getIsInline()) {
							sb.append(("<img src=\"" + capex.HTMLString.sanitize(link)) + "\" />");
						}
						else {
							java.lang.String targetblank = "";
							if(sg.getLinkPopup()) {
								targetblank = " target=\"_blank\"";
							}
							sb.append(((("<a" + targetblank) + " class=\"urlLink\" href=\"") + capex.HTMLString.sanitize(link)) + "\">");
							aOpen = true;
						}
					}
					if(cape.String.isEmpty(sg.getReference()) == false) {
						java.lang.String ref = sg.getReference();
						java.lang.String href = null;
						if(refs != null) {
							href = refs.getReferenceHref(ref);
							if(cape.String.isEmpty(text)) {
								text = refs.getReferenceTitle(ref);
							}
						}
						if(cape.String.isEmpty(href) == false) {
							if(cape.String.isEmpty(text)) {
								text = ref;
							}
							sb.append(("<a class=\"referenceLink\" href=\"" + capex.HTMLString.sanitize(href)) + "\">");
							aOpen = true;
						}
					}
					boolean span = false;
					if(((sg.getBold() || sg.getItalic()) || sg.getUnderline()) || (cape.String.isEmpty(sg.getColor()) == false)) {
						span = true;
						sb.append("<span style=\"");
						if(sg.getBold()) {
							sb.append(" font-weight: bold;");
						}
						if(sg.getItalic()) {
							sb.append(" font-style: italic;");
						}
						if(sg.getUnderline()) {
							sb.append(" text-decoration: underline;");
						}
						if(cape.String.isEmpty(sg.getColor()) == false) {
							sb.append((" color: " + capex.HTMLString.sanitize(sg.getColor())) + "");
						}
						sb.append("\">");
					}
					if(sg.getIsInline() == false) {
						sb.append(capex.HTMLString.sanitize(text));
					}
					if(span) {
						sb.append("</span>");
					}
					if(aOpen) {
						sb.append("</a>");
					}
				}
			}
		}
		sb.append(("</" + tag) + ">");
		return(sb.toString());
	}

	public RichTextParagraph addSegment(RichTextSegment rts) {
		if(rts == null) {
			return((RichTextParagraph)this);
		}
		if(segments == null) {
			segments = new java.util.ArrayList<RichTextSegment>();
		}
		cape.Vector.append(segments, rts);
		return((RichTextParagraph)this);
	}

	public void setSegmentLink(RichTextSegment seg, java.lang.String alink) {
		if(android.text.TextUtils.equals(alink, null)) {
			seg.setLink(null);
			return;
		}
		java.lang.String link = alink;
		if(cape.String.startsWith(link, ">")) {
			seg.setIsInline(true);
			link = cape.String.subString(link, 1);
		}
		if(cape.String.startsWith(link, "!")) {
			seg.setLinkPopup(false);
			link = cape.String.subString(link, 1);
		}
		else {
			seg.setLinkPopup(true);
		}
		seg.setLink(link);
	}

	public void parseSegments(java.lang.String txt) {
		if(android.text.TextUtils.equals(txt, null)) {
			return;
		}
		cape.StringBuilder segmentsb = null;
		cape.StringBuilder linksb = null;
		cape.StringBuilder sb = new cape.StringBuilder();
		cape.CharacterIterator it = cape.String.iterate(txt);
		char c = ' ';
		char pc = (char)0;
		RichTextSegment seg = new RichTextSegment();
		while((c = it.getNextChar()) > 0) {
			if(pc == '[') {
				if(c == '[') {
					sb.append(c);
					pc = (char)0;
					continue;
				}
				if(sb.count() > 0) {
					seg.setText(sb.toString());
					sb.clear();
					addSegment(seg);
				}
				seg = new RichTextSegment();
				linksb = new cape.StringBuilder();
				linksb.append(c);
				pc = c;
				continue;
			}
			if(linksb != null) {
				if(c == '|') {
					setSegmentLink(seg, linksb.toString());
					linksb.clear();
					pc = c;
					continue;
				}
				if(c == ']') {
					java.lang.String xt = linksb.toString();
					if(android.text.TextUtils.equals(seg.getLink(), null)) {
						setSegmentLink(seg, xt);
					}
					else {
						seg.setText(xt);
					}
					if(cape.String.isEmpty(seg.getText())) {
						java.lang.String ll = xt;
						if(cape.String.startsWith(ll, "http://")) {
							ll = cape.String.subString(ll, 7);
						}
						seg.setText(ll);
					}
					addSegment(seg);
					seg = new RichTextSegment();
					linksb = null;
				}
				else {
					linksb.append(c);
				}
				pc = c;
				continue;
			}
			if(pc == '{') {
				if(c == '{') {
					sb.append(c);
					pc = (char)0;
					continue;
				}
				if(sb.count() > 0) {
					seg.setText(sb.toString());
					sb.clear();
					addSegment(seg);
				}
				seg = new RichTextSegment();
				segmentsb = new cape.StringBuilder();
				segmentsb.append(c);
				pc = c;
				continue;
			}
			if(segmentsb != null) {
				if(c == '|') {
					seg.setReference(segmentsb.toString());
					segmentsb.clear();
					pc = c;
					continue;
				}
				if(c == '}') {
					java.lang.String xt = segmentsb.toString();
					if(android.text.TextUtils.equals(seg.getReference(), null)) {
						seg.setReference(xt);
					}
					else {
						seg.setText(xt);
					}
					addSegment(seg);
					seg = new RichTextSegment();
					segmentsb = null;
				}
				else {
					segmentsb.append(c);
				}
				pc = c;
				continue;
			}
			if(pc == '*') {
				if(c == '*') {
					if(sb.count() > 0) {
						seg.setText(sb.toString());
						sb.clear();
						addSegment(seg);
					}
					if(seg.getBold()) {
						seg = new RichTextSegment().setBold(false);
					}
					else {
						seg = new RichTextSegment().setBold(true);
					}
				}
				else {
					sb.append(pc);
					sb.append(c);
				}
				pc = (char)0;
				continue;
			}
			if(pc == '_') {
				if(c == '_') {
					if(sb.count() > 0) {
						seg.setText(sb.toString());
						sb.clear();
						addSegment(seg);
					}
					if(seg.getUnderline()) {
						seg = new RichTextSegment().setUnderline(false);
					}
					else {
						seg = new RichTextSegment().setUnderline(true);
					}
				}
				else {
					sb.append(pc);
					sb.append(c);
				}
				pc = (char)0;
				continue;
			}
			if(pc == '\'') {
				if(c == '\'') {
					if(sb.count() > 0) {
						seg.setText(sb.toString());
						sb.clear();
						addSegment(seg);
					}
					if(seg.getItalic()) {
						seg = new RichTextSegment().setItalic(false);
					}
					else {
						seg = new RichTextSegment().setItalic(true);
					}
				}
				else {
					sb.append(pc);
					sb.append(c);
				}
				pc = (char)0;
				continue;
			}
			if(((((c != '*') && (c != '_')) && (c != '\'')) && (c != '{')) && (c != '[')) {
				sb.append(c);
			}
			pc = c;
		}
		if(((((pc == '*') || (pc == '_')) || (pc == '\'')) && (pc != '{')) && (pc != '[')) {
			sb.append(pc);
		}
		if(sb.count() > 0) {
			seg.setText(sb.toString());
			sb.clear();
			addSegment(seg);
		}
	}

	public RichTextStyledParagraph parse(java.lang.String text) {
		if(android.text.TextUtils.equals(text, null)) {
			return(this);
		}
		java.lang.String txt = text;
		java.lang.String[] prefixes = new java.lang.String[] {
			"=",
			"==",
			"===",
			"====",
			"====="
		};
		int n = 0;
		for(n = 0 ; n < prefixes.length ; n++) {
			java.lang.String key = prefixes[n];
			if(cape.String.startsWith(txt, key + " ") && cape.String.endsWith(txt, " " + key)) {
				setHeading(n + 1);
				txt = cape.String.subString(txt, cape.String.getLength(key) + 1, (cape.String.getLength(txt) - (cape.String.getLength(key) * 2)) - 2);
				if(!(android.text.TextUtils.equals(txt, null))) {
					txt = cape.String.strip(txt);
				}
				break;
			}
		}
		parseSegments(txt);
		return(this);
	}

	@Override
	public java.lang.String toMarkup() {
		java.lang.String ident = null;
		if(heading == 1) {
			ident = "=";
		}
		else if(heading == 2) {
			ident = "==";
		}
		else if(heading == 3) {
			ident = "===";
		}
		else if(heading == 4) {
			ident = "====";
		}
		else if(heading == 5) {
			ident = "=====";
		}
		cape.StringBuilder sb = new cape.StringBuilder();
		if(cape.String.isEmpty(ident) == false) {
			sb.append(ident);
			sb.append(' ');
		}
		if(segments != null) {
			int n = 0;
			int m = segments.size();
			for(n = 0 ; n < m ; n++) {
				RichTextSegment segment = segments.get(n);
				if(segment != null) {
					sb.append(segment.toMarkup());
				}
			}
		}
		if(cape.String.isEmpty(ident) == false) {
			sb.append(' ');
			sb.append(ident);
		}
		return(sb.toString());
	}

	public int getHeading() {
		return(heading);
	}

	public RichTextStyledParagraph setHeading(int v) {
		heading = v;
		return(this);
	}

	public java.util.ArrayList<RichTextSegment> getSegments() {
		return(segments);
	}

	public RichTextStyledParagraph setSegments(java.util.ArrayList<RichTextSegment> v) {
		segments = v;
		return(this);
	}
}
