
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

package sympathy;

public class HTTPServerDirectoryHandler extends HTTPServerRequestHandlerAdapter
{
	public static HTTPServerDirectoryHandler forDirectory(cape.File dir) {
		HTTPServerDirectoryHandler v = new HTTPServerDirectoryHandler();
		v.setDirectory(dir);
		return(v);
	}

	private boolean listDirectories = false;
	private boolean processTemplateFiles = false;
	private cape.File directory = null;
	private int maxAge = 300;
	private java.lang.String serverURL = null;
	private java.util.ArrayList<java.lang.String> indexFiles = null;
	private java.util.ArrayList<cape.File> templateIncludeDirs = null;
	private java.lang.String serverName = null;
	private cape.DynamicMap templateData = null;

	public void addTemplateIncludeDir(cape.File dir) {
		if(dir == null) {
			return;
		}
		if(templateIncludeDirs == null) {
			templateIncludeDirs = new java.util.ArrayList<cape.File>();
		}
		templateIncludeDirs.add(dir);
	}

	public HTTPServerDirectoryHandler setIndexFiles(java.lang.String[] files) {
		indexFiles = new java.util.ArrayList<java.lang.String>();
		if(files != null) {
			int n = 0;
			int m = files.length;
			for(n = 0 ; n < m ; n++) {
				java.lang.String file = files[n];
				if(file != null) {
					indexFiles.add(file);
				}
			}
		}
		return(this);
	}

	public HTTPServerDirectoryHandler setServerName(java.lang.String name) {
		this.serverName = name;
		return(this);
	}

	public java.lang.String getServerName() {
		if(!(android.text.TextUtils.equals(serverName, null))) {
			return(serverName);
		}
		HTTPServerBase server = getServer();
		if(server == null) {
			return(null);
		}
		return(server.getServerName());
	}

	public void getDirectoryEntries(cape.File dir, java.util.ArrayList<java.lang.String> allEntries, java.util.ArrayList<java.lang.String> dirs, java.util.ArrayList<java.lang.String> files) {
		if(dir == null) {
			return;
		}
		cape.Iterator<cape.File> entries = dir.entries();
		while(entries != null) {
			cape.File entry = entries.next();
			if(entry == null) {
				break;
			}
			java.lang.String name = entry.baseName();
			if((dirs != null) && entry.isDirectory()) {
				dirs.add(name);
			}
			if((files != null) && entry.isFile()) {
				files.add(name);
			}
			if(allEntries != null) {
				allEntries.add(name);
			}
		}
	}

	public cape.DynamicMap dirToJSONObject(cape.File dir) {
		java.util.ArrayList<java.lang.String> allEntries = new java.util.ArrayList<java.lang.String>();
		java.util.ArrayList<java.lang.String> dirs = new java.util.ArrayList<java.lang.String>();
		java.util.ArrayList<java.lang.String> files = new java.util.ArrayList<java.lang.String>();
		getDirectoryEntries(dir, allEntries, dirs, files);
		cape.DynamicMap v = new cape.DynamicMap();
		v.set("files", (java.lang.Object)cape.DynamicVector.forStringVector(files));
		v.set("dirs", (java.lang.Object)cape.DynamicVector.forStringVector(dirs));
		v.set("all", (java.lang.Object)cape.DynamicVector.forStringVector(allEntries));
		return(v);
	}

	public java.lang.String dirToJSON(cape.File dir) {
		return(cape.JSONEncoder.encode((java.lang.Object)dirToJSONObject(dir)));
	}

	public cape.DynamicMap getTemplateVariablesForFile(cape.File file) {
		return(null);
	}

	public java.lang.String dirToHTML(cape.File dir) {
		if(dir == null) {
			return(null);
		}
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<title>");
		sb.append(dir.baseName());
		sb.append("</title>\n");
		sb.append("<style>\n");
		sb.append("* { font-face: arial; font-size: 12px; }\n");
		sb.append("h1 { font-face: arial; font-size: 14px; font-style: bold; border-bottom: solid 1px black; padding: 4px; margin: 4px}\n");
		sb.append("#content a { text-decoration: none; color: #000080; }\n");
		sb.append("#content a:hover { text-decoration: none; color: #FFFFFF; font-weight: bold; }\n");
		sb.append(".entry { padding: 4px; }\n");
		sb.append(".entry:hover { background-color: #AAAACC; }\n");
		sb.append(".dir { font-weight: bold; }\n");
		sb.append(".even { background-color: #DDDDDD; }\n");
		sb.append(".odd { background-color: #EEEEEE; }\n");
		sb.append("#footer { border-top: 1px solid black; padding: 4px; margin: 4px; font-size: 10px; text-align: right; }\n");
		sb.append("#footer a { font-size: 10px; text-decoration: none; color: #000000; }\n");
		sb.append("#footer a:hover { font-size: 10px; text-decoration: underline; color: #000000; }\n");
		sb.append("</style>\n");
		sb.append("<meta name=\"viewport\" content=\"initial-scale=1,maximum-scale=1\" />\n");
		sb.append("</head>\n");
		sb.append("<body>\n");
		sb.append("<h1>");
		sb.append(dir.baseName());
		sb.append("</h1>\n");
		sb.append("<div id=\"content\">\n");
		java.util.ArrayList<java.lang.String> dirs = new java.util.ArrayList<java.lang.String>();
		java.util.ArrayList<java.lang.String> files = new java.util.ArrayList<java.lang.String>();
		getDirectoryEntries(dir, null, dirs, files);
		int n = 0;
		if(dirs != null) {
			int n2 = 0;
			int m = dirs.size();
			for(n2 = 0 ; n2 < m ; n2++) {
				java.lang.String dn = dirs.get(n2);
				if(dn != null) {
					java.lang.String cc = null;
					if((n % 2) == 0) {
						cc = "even";
					}
					else {
						cc = "odd";
					}
					sb.append("<a href=\"");
					sb.append(dn);
					sb.append("/\"><div class=\"entry dir ");
					sb.append(cc);
					sb.append("\">");
					sb.append(dn);
					sb.append("/</div></a>\n");
					n++;
				}
			}
		}
		if(files != null) {
			int n3 = 0;
			int m2 = files.size();
			for(n3 = 0 ; n3 < m2 ; n3++) {
				java.lang.String fn = files.get(n3);
				if(fn != null) {
					java.lang.String cc = null;
					if((n % 2) == 0) {
						cc = "even";
					}
					else {
						cc = "odd";
					}
					sb.append("<a href=\"");
					sb.append(fn);
					sb.append("\"><div class=\"entry ");
					sb.append(cc);
					sb.append("\">");
					sb.append(fn);
					sb.append("</div></a>\n");
					n++;
				}
			}
		}
		sb.append("</div>\n");
		sb.append("<div id=\"footer\">");
		java.lang.String serverName = getServerName();
		if(cape.String.isEmpty(serverName) == false) {
			if(cape.String.isEmpty(serverURL) == false) {
				sb.append("Generated by <a href=\"");
				if(cape.String.contains(serverURL, "://") == false) {
					sb.append("http://");
				}
				sb.append(serverURL);
				sb.append("\">");
				sb.append(serverName);
				sb.append("</a>\n");
			}
			else {
				sb.append("Generated by ");
				sb.append(serverName);
				sb.append("\n");
			}
		}
		sb.append("</div>\n");
		sb.append("</body>\n");
		sb.append("</html>\n");
		return(sb.toString());
	}

	@Override
	public boolean onGET(HTTPServerRequest req) {
		if(directory == null) {
			return(false);
		}
		cape.File dd = directory;
		while(true) {
			java.lang.String comp = req.popResource();
			if(android.text.TextUtils.equals(comp, null)) {
				break;
			}
			dd = dd.entry(comp);
		}
		if(dd.isDirectory()) {
			if(indexFiles != null) {
				int n = 0;
				int m = indexFiles.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.String indexFile = indexFiles.get(n);
					if(indexFile != null) {
						cape.File ff = dd.entry(indexFile);
						if(ff.isFile()) {
							dd = ff;
							break;
						}
					}
				}
			}
		}
		if(dd.isDirectory()) {
			if(req.isForDirectory() == false) {
				req.sendRedirectAsDirectory();
				return(true);
			}
			if(listDirectories == false) {
				return(false);
			}
			req.sendHTMLString(dirToHTML(dd));
			return(true);
		}
		if((dd.exists() == false) && processTemplateFiles) {
			java.lang.String bn = dd.baseName();
			cape.File nf = dd.getParent().entry(bn + ".t");
			if(nf.isFile()) {
				dd = nf;
			}
			else {
				nf = dd.getParent().entry(bn + ".html.t");
				if(nf.isFile()) {
					dd = nf;
				}
			}
		}
		if(dd.isFile()) {
			HTTPServerResponse resp = null;
			if(processTemplateFiles) {
				java.lang.String bn = dd.baseName();
				boolean isJSONTemplate = false;
				boolean isHTMLTemplate = false;
				boolean isCSSTemplate = false;
				if(cape.String.endsWith(bn, ".html.t")) {
					isHTMLTemplate = true;
				}
				else if(cape.String.endsWith(bn, ".css.t")) {
					isCSSTemplate = true;
				}
				else if(cape.String.endsWith(bn, ".json.t")) {
					isJSONTemplate = true;
				}
				if((isHTMLTemplate || isCSSTemplate) || isJSONTemplate) {
					java.lang.String data = dd.getContentsString("UTF-8");
					if(android.text.TextUtils.equals(data, null)) {
						cape.Log.error(logContext, ("Failed to read template file content: `" + dd.getPath()) + "'");
						req.sendResponse(sympathy.HTTPServerResponse.forHTTPInternalError());
						return(true);
					}
					java.util.ArrayList<cape.File> includeDirs = new java.util.ArrayList<cape.File>();
					includeDirs.add(dd.getParent());
					if(templateIncludeDirs != null) {
						int n2 = 0;
						int m2 = templateIncludeDirs.size();
						for(n2 = 0 ; n2 < m2 ; n2++) {
							cape.File dir = templateIncludeDirs.get(n2);
							if(dir != null) {
								includeDirs.add(dir);
							}
						}
					}
					capex.TextTemplate tt = null;
					if(isHTMLTemplate || isCSSTemplate) {
						tt = capex.TextTemplate.forHTMLString(data, includeDirs, logContext);
					}
					else {
						tt = capex.TextTemplate.forJSONString(data, includeDirs, logContext);
					}
					if(tt == null) {
						cape.Log.error(logContext, ("Failed to process template file content: `" + dd.getPath()) + "'");
						req.sendResponse(sympathy.HTTPServerResponse.forHTTPInternalError());
						return(true);
					}
					cape.DynamicMap tdata = templateData;
					cape.DynamicMap dynamicData = getTemplateVariablesForFile(dd);
					if(dynamicData != null) {
						if(tdata == null) {
							tdata = dynamicData;
						}
						else {
							tdata.mergeFrom(dynamicData);
						}
					}
					java.lang.String text = tt.execute(tdata, includeDirs);
					if(android.text.TextUtils.equals(text, null)) {
						cape.Log.error(logContext, ("Failed to execute template: `" + dd.getPath()) + "'");
						req.sendResponse(sympathy.HTTPServerResponse.forHTTPInternalError());
						return(true);
					}
					if(isHTMLTemplate) {
						resp = sympathy.HTTPServerResponse.forHTMLString(text);
					}
					else if(isCSSTemplate) {
						resp = sympathy.HTTPServerResponse.forString(text, "text/css");
					}
					else {
						resp = sympathy.HTTPServerResponse.forJSONString(text);
					}
				}
			}
			if(resp == null) {
				resp = sympathy.HTTPServerResponse.forFile(dd);
			}
			if(maxAge > 0) {
				resp.addHeader("Cache-Control", "max-age=" + cape.String.forInteger(maxAge));
			}
			req.sendResponse(resp);
			return(true);
		}
		return(false);
	}

	public boolean getListDirectories() {
		return(listDirectories);
	}

	public HTTPServerDirectoryHandler setListDirectories(boolean v) {
		listDirectories = v;
		return(this);
	}

	public boolean getProcessTemplateFiles() {
		return(processTemplateFiles);
	}

	public HTTPServerDirectoryHandler setProcessTemplateFiles(boolean v) {
		processTemplateFiles = v;
		return(this);
	}

	public cape.File getDirectory() {
		return(directory);
	}

	public HTTPServerDirectoryHandler setDirectory(cape.File v) {
		directory = v;
		return(this);
	}

	public int getMaxAge() {
		return(maxAge);
	}

	public HTTPServerDirectoryHandler setMaxAge(int v) {
		maxAge = v;
		return(this);
	}

	public java.lang.String getServerURL() {
		return(serverURL);
	}

	public HTTPServerDirectoryHandler setServerURL(java.lang.String v) {
		serverURL = v;
		return(this);
	}

	public java.util.ArrayList<java.lang.String> getIndexFiles() {
		return(indexFiles);
	}

	public java.util.ArrayList<cape.File> getTemplateIncludeDirs() {
		return(templateIncludeDirs);
	}

	public HTTPServerDirectoryHandler setTemplateIncludeDirs(java.util.ArrayList<cape.File> v) {
		templateIncludeDirs = v;
		return(this);
	}

	public cape.DynamicMap getTemplateData() {
		return(templateData);
	}

	public HTTPServerDirectoryHandler setTemplateData(cape.DynamicMap v) {
		templateData = v;
		return(this);
	}
}
