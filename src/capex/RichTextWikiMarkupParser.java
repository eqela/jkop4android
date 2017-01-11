
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

public class RichTextWikiMarkupParser
{
	public static RichTextDocument parseFile(cape.File file) {
		return(new RichTextWikiMarkupParser().setFile(file).parse());
	}

	public static RichTextDocument parseString(java.lang.String data) {
		return(new RichTextWikiMarkupParser().setData(data).parse());
	}

	private cape.File file = null;
	private java.lang.String data = null;

	public java.lang.String skipEmptyLines(cape.LineReader pr) {
		java.lang.String line = null;
		while(!(android.text.TextUtils.equals(line = pr.readLine(), null))) {
			line = cape.String.strip(line);
			if(!(android.text.TextUtils.equals(line, null)) && cape.String.startsWith(line, "#")) {
				continue;
			}
			if(cape.String.isEmpty(line) == false) {
				break;
			}
		}
		return(line);
	}

	public RichTextPreformattedParagraph readPreformattedParagraph(java.lang.String id, cape.LineReader pr) {
		cape.StringBuilder sb = new cape.StringBuilder();
		java.lang.String line = null;
		while(!(android.text.TextUtils.equals(line = pr.readLine(), null))) {
			if(cape.String.startsWith(line, "---") && cape.String.endsWith(line, "---")) {
				java.lang.String lid = cape.String.subString(line, 3, cape.String.getLength(line) - 6);
				if(!(android.text.TextUtils.equals(lid, null))) {
					lid = cape.String.strip(lid);
				}
				if(cape.String.isEmpty(id)) {
					if(cape.String.isEmpty(lid)) {
						break;
					}
				}
				else if(cape.String.equals(id, lid)) {
					break;
				}
			}
			sb.append(line);
			sb.append('\n');
		}
		return(new RichTextPreformattedParagraph().setId(id).setText(sb.toString()));
	}

	public RichTextBlockParagraph readBlockParagraph(java.lang.String id, cape.LineReader pr) {
		cape.StringBuilder sb = new cape.StringBuilder();
		java.lang.String line = null;
		while(!(android.text.TextUtils.equals(line = pr.readLine(), null))) {
			if(cape.String.startsWith(line, "--") && cape.String.endsWith(line, "--")) {
				java.lang.String lid = cape.String.subString(line, 2, cape.String.getLength(line) - 4);
				if(!(android.text.TextUtils.equals(lid, null))) {
					lid = cape.String.strip(lid);
				}
				if(cape.String.isEmpty(id)) {
					if(cape.String.isEmpty(lid)) {
						break;
					}
				}
				else if(cape.String.equals(id, lid)) {
					break;
				}
			}
			sb.append(line);
			sb.append('\n');
		}
		return(new RichTextBlockParagraph().setId(id).setText(sb.toString()));
	}

	public boolean processInput(cape.LineReader pr, RichTextDocument doc) {
		java.lang.String line = skipEmptyLines(pr);
		if(android.text.TextUtils.equals(line, null)) {
			return(false);
		}
		if(android.text.TextUtils.equals(line, "-")) {
			doc.addParagraph((RichTextParagraph)new RichTextSeparatorParagraph());
			return(true);
		}
		if(cape.String.startsWith(line, "@content ")) {
			java.lang.String id = cape.String.subString(line, 9);
			if(!(android.text.TextUtils.equals(id, null))) {
				id = cape.String.strip(id);
			}
			doc.addParagraph((RichTextParagraph)new RichTextContentParagraph().setContentId(id));
			return(true);
		}
		if(cape.String.startsWith(line, "@image ")) {
			java.lang.String ref = cape.String.subString(line, 7);
			if(!(android.text.TextUtils.equals(ref, null))) {
				ref = cape.String.strip(ref);
			}
			doc.addParagraph((RichTextParagraph)new RichTextImageParagraph().setFilename(ref));
			return(true);
		}
		if(cape.String.startsWith(line, "@image100 ")) {
			java.lang.String ref = cape.String.subString(line, 10);
			if(!(android.text.TextUtils.equals(ref, null))) {
				ref = cape.String.strip(ref);
			}
			doc.addParagraph((RichTextParagraph)new RichTextImageParagraph().setFilename(ref));
			return(true);
		}
		if(cape.String.startsWith(line, "@image75 ")) {
			java.lang.String ref = cape.String.subString(line, 9);
			if(!(android.text.TextUtils.equals(ref, null))) {
				ref = cape.String.strip(ref);
			}
			doc.addParagraph((RichTextParagraph)new RichTextImageParagraph().setFilename(ref).setWidth(75));
			return(true);
		}
		if(cape.String.startsWith(line, "@image50 ")) {
			java.lang.String ref = cape.String.subString(line, 9);
			if(!(android.text.TextUtils.equals(ref, null))) {
				ref = cape.String.strip(ref);
			}
			doc.addParagraph((RichTextParagraph)new RichTextImageParagraph().setFilename(ref).setWidth(50));
			return(true);
		}
		if(cape.String.startsWith(line, "@image25 ")) {
			java.lang.String ref = cape.String.subString(line, 9);
			if(!(android.text.TextUtils.equals(ref, null))) {
				ref = cape.String.strip(ref);
			}
			doc.addParagraph((RichTextParagraph)new RichTextImageParagraph().setFilename(ref).setWidth(25));
			return(true);
		}
		if(cape.String.startsWith(line, "@reference ")) {
			java.lang.String ref = cape.String.subString(line, 11);
			if(!(android.text.TextUtils.equals(ref, null))) {
				ref = cape.String.strip(ref);
			}
			java.util.ArrayList<java.lang.String> sq = cape.String.quotedStringToVector(ref, ' ');
			java.lang.String rrf = cape.Vector.getAt(sq, 0);
			java.lang.String txt = cape.Vector.getAt(sq, 1);
			doc.addParagraph((RichTextParagraph)new RichTextReferenceParagraph().setReference(rrf).setText(txt));
			return(true);
		}
		if(cape.String.startsWith(line, "@set ")) {
			java.lang.String link = cape.String.subString(line, 5);
			if(!(android.text.TextUtils.equals(link, null))) {
				link = cape.String.strip(link);
			}
			java.util.ArrayList<java.lang.String> sq = cape.String.quotedStringToVector(link, ' ');
			java.lang.String key = cape.Vector.getAt(sq, 0);
			java.lang.String val = cape.Vector.getAt(sq, 1);
			if(cape.String.isEmpty(key)) {
				return(true);
			}
			doc.setMetadata(key, val);
			return(true);
		}
		if(cape.String.startsWith(line, "@link ")) {
			java.lang.String link = cape.String.subString(line, 6);
			if(!(android.text.TextUtils.equals(link, null))) {
				link = cape.String.strip(link);
			}
			java.util.ArrayList<java.lang.String> sq = cape.String.quotedStringToVector(link, ' ');
			java.lang.String url = cape.Vector.getAt(sq, 0);
			java.lang.String txt = cape.Vector.getAt(sq, 1);
			java.lang.String flags = cape.Vector.getAt(sq, 2);
			if(cape.String.isEmpty(txt)) {
				txt = url;
			}
			RichTextLinkParagraph v = new RichTextLinkParagraph();
			v.setLink(url);
			v.setText(txt);
			if(cape.String.equals("internal", flags)) {
				v.setPopup(false);
			}
			else {
				v.setPopup(true);
			}
			doc.addParagraph((RichTextParagraph)v);
			return(true);
		}
		if(cape.String.startsWith(line, "---") && cape.String.endsWith(line, "---")) {
			java.lang.String id = cape.String.subString(line, 3, cape.String.getLength(line) - 6);
			if(!(android.text.TextUtils.equals(id, null))) {
				id = cape.String.strip(id);
			}
			if(cape.String.isEmpty(id)) {
				id = null;
			}
			doc.addParagraph((RichTextParagraph)readPreformattedParagraph(id, pr));
			return(true);
		}
		if(cape.String.startsWith(line, "--") && cape.String.endsWith(line, "--")) {
			java.lang.String id = cape.String.subString(line, 2, cape.String.getLength(line) - 4);
			if(!(android.text.TextUtils.equals(id, null))) {
				id = cape.String.strip(id);
			}
			if(cape.String.isEmpty(id)) {
				id = null;
			}
			doc.addParagraph((RichTextParagraph)readBlockParagraph(id, pr));
			return(true);
		}
		cape.StringBuilder sb = new cape.StringBuilder();
		char pc = (char)0;
		do {
			line = cape.String.strip(line);
			if(cape.String.isEmpty(line)) {
				break;
			}
			if(cape.String.startsWith(line, "#") == false) {
				cape.CharacterIterator it = cape.String.iterate(line);
				char c = ' ';
				if((sb.count() > 0) && (pc != ' ')) {
					sb.append(' ');
					pc = ' ';
				}
				while((c = it.getNextChar()) > 0) {
					if((((c == ' ') || (c == '\t')) || (c == '\r')) || (c == '\n')) {
						if(pc == ' ') {
							continue;
						}
						c = ' ';
					}
					sb.append(c);
					pc = c;
				}
			}
		}
		while(!(android.text.TextUtils.equals(line = pr.readLine(), null)));
		java.lang.String s = sb.toString();
		if(cape.String.isEmpty(s)) {
			return(false);
		}
		doc.addParagraph((RichTextParagraph)capex.RichTextStyledParagraph.forString(s));
		return(true);
	}

	public RichTextDocument parse() {
		cape.LineReader pr = null;
		if(file != null) {
			pr = (cape.LineReader)new cape.PrintReader((cape.Reader)file.read());
		}
		else if(!(android.text.TextUtils.equals(data, null))) {
			pr = (cape.LineReader)new cape.StringLineReader(data);
		}
		if(pr == null) {
			return(null);
		}
		RichTextDocument v = new RichTextDocument();
		while(processInput(pr, v)) {
			;
		}
		return(v);
	}

	public cape.File getFile() {
		return(file);
	}

	public RichTextWikiMarkupParser setFile(cape.File v) {
		file = v;
		return(this);
	}

	public java.lang.String getData() {
		return(data);
	}

	public RichTextWikiMarkupParser setData(java.lang.String v) {
		data = v;
		return(this);
	}
}
