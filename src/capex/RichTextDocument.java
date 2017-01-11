
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

public class RichTextDocument
{
	public static RichTextDocument forWikiMarkupFile(cape.File file) {
		return(capex.RichTextWikiMarkupParser.parseFile(file));
	}

	public static RichTextDocument forWikiMarkupString(java.lang.String str) {
		return(capex.RichTextWikiMarkupParser.parseString(str));
	}

	private cape.DynamicMap metadata = null;
	private java.util.ArrayList<RichTextParagraph> paragraphs = null;

	public RichTextDocument() {
		metadata = new cape.DynamicMap();
	}

	public java.lang.String getTitle() {
		return(metadata.getString("title"));
	}

	public RichTextDocument setTitle(java.lang.String v) {
		metadata.set("title", (java.lang.Object)v);
		return(this);
	}

	public java.lang.String getMetadata(java.lang.String k) {
		return(metadata.getString(k));
	}

	public RichTextDocument setMetadata(java.lang.String k, java.lang.String v) {
		metadata.set(k, (java.lang.Object)v);
		return(this);
	}

	public RichTextDocument addParagraph(RichTextParagraph rtp) {
		if(rtp == null) {
			return(this);
		}
		if(paragraphs == null) {
			paragraphs = new java.util.ArrayList<RichTextParagraph>();
		}
		cape.Vector.append(paragraphs, rtp);
		if(((android.text.TextUtils.equals(getTitle(), null)) && (rtp instanceof RichTextStyledParagraph)) && (((RichTextStyledParagraph)((rtp instanceof RichTextStyledParagraph) ? rtp : null)).getHeading() == 1)) {
			setTitle(((RichTextStyledParagraph)((rtp instanceof RichTextStyledParagraph) ? rtp : null)).getTextContent());
		}
		return(this);
	}

	public java.util.ArrayList<java.lang.String> getAllReferences() {
		java.util.ArrayList<java.lang.String> v = new java.util.ArrayList<java.lang.String>();
		if(paragraphs != null) {
			int n = 0;
			int m = paragraphs.size();
			for(n = 0 ; n < m ; n++) {
				RichTextParagraph paragraph = paragraphs.get(n);
				if(paragraph != null) {
					if(paragraph instanceof RichTextReferenceParagraph) {
						java.lang.String ref = ((RichTextReferenceParagraph)paragraph).getReference();
						if(cape.String.isEmpty(ref) == false) {
							v.add(ref);
						}
					}
					else if(paragraph instanceof RichTextStyledParagraph) {
						java.util.ArrayList<RichTextSegment> array = ((RichTextStyledParagraph)paragraph).getSegments();
						if(array != null) {
							int n2 = 0;
							int m2 = array.size();
							for(n2 = 0 ; n2 < m2 ; n2++) {
								RichTextSegment segment = array.get(n2);
								if(segment != null) {
									java.lang.String ref = segment.getReference();
									if(cape.String.isEmpty(ref) == false) {
										v.add(ref);
									}
								}
							}
						}
					}
				}
			}
		}
		return(v);
	}

	public java.util.ArrayList<java.lang.String> getAllLinks() {
		java.util.ArrayList<java.lang.String> v = new java.util.ArrayList<java.lang.String>();
		if(paragraphs != null) {
			int n = 0;
			int m = paragraphs.size();
			for(n = 0 ; n < m ; n++) {
				RichTextParagraph paragraph = paragraphs.get(n);
				if(paragraph != null) {
					if(paragraph instanceof RichTextLinkParagraph) {
						java.lang.String link = ((RichTextLinkParagraph)paragraph).getLink();
						if(cape.String.isEmpty(link) == false) {
							v.add(link);
						}
					}
					else if(paragraph instanceof RichTextStyledParagraph) {
						java.util.ArrayList<RichTextSegment> array = ((RichTextStyledParagraph)paragraph).getSegments();
						if(array != null) {
							int n2 = 0;
							int m2 = array.size();
							for(n2 = 0 ; n2 < m2 ; n2++) {
								RichTextSegment segment = array.get(n2);
								if(segment != null) {
									java.lang.String link = segment.getLink();
									if(cape.String.isEmpty(link) == false) {
										v.add(link);
									}
								}
							}
						}
					}
				}
			}
		}
		return(v);
	}

	public cape.DynamicMap toJson() {
		cape.DynamicMap v = new cape.DynamicMap();
		v.set("metadata", (java.lang.Object)metadata);
		v.set("title", (java.lang.Object)getTitle());
		java.util.ArrayList<java.lang.Object> pp = new java.util.ArrayList<java.lang.Object>();
		if(paragraphs != null) {
			int n = 0;
			int m = paragraphs.size();
			for(n = 0 ; n < m ; n++) {
				RichTextParagraph par = paragraphs.get(n);
				if(par != null) {
					cape.DynamicMap pj = par.toJson();
					if(pj != null) {
						cape.Vector.append(pp, pj);
					}
				}
			}
		}
		v.set("paragraphs", (java.lang.Object)pp);
		return(v);
	}

	public java.lang.String toHtml(RichTextDocumentReferenceResolver refs) {
		cape.StringBuilder sb = new cape.StringBuilder();
		java.util.ArrayList<RichTextParagraph> array = getParagraphs();
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				RichTextParagraph paragraph = array.get(n);
				if(paragraph != null) {
					java.lang.String html = paragraph.toHtml(refs);
					if(cape.String.isEmpty(html) == false) {
						sb.append(html);
						sb.append('\n');
					}
				}
			}
		}
		return(sb.toString());
	}

	public java.util.ArrayList<RichTextParagraph> getParagraphs() {
		return(paragraphs);
	}

	public RichTextDocument setParagraphs(java.util.ArrayList<RichTextParagraph> v) {
		paragraphs = v;
		return(this);
	}
}
