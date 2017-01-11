
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

public class TextTemplate
{
	private static class TagData
	{
		public java.util.ArrayList<java.lang.String> words = null;

		public TagData(java.util.ArrayList<java.lang.String> words) {
			this.words = words;
		}
	}

	public static TextTemplate forFile(cape.File file, java.lang.String markerBegin, java.lang.String markerEnd, int type, java.util.ArrayList<cape.File> includeDirs, cape.LoggingContext logContext) {
		if(file == null) {
			return(null);
		}
		java.lang.String text = file.getContentsString("UTF-8");
		if(android.text.TextUtils.equals(text, null)) {
			return(null);
		}
		java.util.ArrayList<cape.File> ids = includeDirs;
		if(ids == null) {
			ids = new java.util.ArrayList<cape.File>();
			ids.add(file.getParent());
		}
		return(forString(text, markerBegin, markerEnd, type, ids, logContext));
	}

	public static TextTemplate forFile(cape.File file, java.lang.String markerBegin, java.lang.String markerEnd, int type, java.util.ArrayList<cape.File> includeDirs) {
		return(capex.TextTemplate.forFile(file, markerBegin, markerEnd, type, includeDirs, null));
	}

	public static TextTemplate forFile(cape.File file, java.lang.String markerBegin, java.lang.String markerEnd, int type) {
		return(capex.TextTemplate.forFile(file, markerBegin, markerEnd, type, null));
	}

	public static TextTemplate forFile(cape.File file, java.lang.String markerBegin, java.lang.String markerEnd) {
		return(capex.TextTemplate.forFile(file, markerBegin, markerEnd, 0));
	}

	public static TextTemplate forString(java.lang.String text, java.lang.String markerBegin, java.lang.String markerEnd, int type, java.util.ArrayList<cape.File> includeDirs, cape.LoggingContext logContext) {
		TextTemplate v = new TextTemplate();
		v.setLogContext(logContext);
		v.setText(text);
		v.setType(type);
		v.setMarkerBegin(markerBegin);
		v.setMarkerEnd(markerEnd);
		if(v.prepare(includeDirs) == false) {
			v = null;
		}
		return(v);
	}

	public static TextTemplate forString(java.lang.String text, java.lang.String markerBegin, java.lang.String markerEnd, int type, java.util.ArrayList<cape.File> includeDirs) {
		return(capex.TextTemplate.forString(text, markerBegin, markerEnd, type, includeDirs, null));
	}

	public static TextTemplate forString(java.lang.String text, java.lang.String markerBegin, java.lang.String markerEnd, int type) {
		return(capex.TextTemplate.forString(text, markerBegin, markerEnd, type, null));
	}

	public static TextTemplate forString(java.lang.String text, java.lang.String markerBegin, java.lang.String markerEnd) {
		return(capex.TextTemplate.forString(text, markerBegin, markerEnd, 0));
	}

	public static TextTemplate forHTMLString(java.lang.String text, java.util.ArrayList<cape.File> includeDirs, cape.LoggingContext logContext) {
		TextTemplate v = new TextTemplate();
		v.setLogContext(logContext);
		v.setText(text);
		v.setType(TYPE_HTML);
		v.setMarkerBegin("<%");
		v.setMarkerEnd("%>");
		if(v.prepare(includeDirs) == false) {
			v = null;
		}
		return(v);
	}

	public static TextTemplate forHTMLString(java.lang.String text, java.util.ArrayList<cape.File> includeDirs) {
		return(capex.TextTemplate.forHTMLString(text, includeDirs, null));
	}

	public static TextTemplate forHTMLString(java.lang.String text) {
		return(capex.TextTemplate.forHTMLString(text, null));
	}

	public static TextTemplate forJSONString(java.lang.String text, java.util.ArrayList<cape.File> includeDirs, cape.LoggingContext logContext) {
		TextTemplate v = new TextTemplate();
		v.setLogContext(logContext);
		v.setText(text);
		v.setType(TYPE_JSON);
		v.setMarkerBegin("{{");
		v.setMarkerEnd("}}");
		if(v.prepare(includeDirs) == false) {
			v = null;
		}
		return(v);
	}

	public static TextTemplate forJSONString(java.lang.String text, java.util.ArrayList<cape.File> includeDirs) {
		return(capex.TextTemplate.forJSONString(text, includeDirs, null));
	}

	public static TextTemplate forJSONString(java.lang.String text) {
		return(capex.TextTemplate.forJSONString(text, null));
	}

	public static final int TYPE_GENERIC = 0;
	public static final int TYPE_HTML = 1;
	public static final int TYPE_JSON = 2;
	private java.util.ArrayList<java.lang.Object> tokens = null;
	private java.lang.String text = null;
	private java.lang.String markerBegin = null;
	private java.lang.String markerEnd = null;
	private cape.LoggingContext logContext = null;
	private int type = TYPE_GENERIC;
	private java.util.ArrayList<java.lang.String> languagePreferences = null;

	public void setLanguagePreference(java.lang.String language) {
		languagePreferences = new java.util.ArrayList<java.lang.String>();
		if(!(android.text.TextUtils.equals(language, null))) {
			languagePreferences.add(language);
		}
	}

	private java.util.ArrayList<java.lang.Object> tokenizeString(java.lang.String inputdata, java.util.ArrayList<cape.File> includeDirs) {
		if((android.text.TextUtils.equals(markerBegin, null)) || (android.text.TextUtils.equals(markerEnd, null))) {
			cape.Log.error(logContext, "No template markers were given");
			return(null);
		}
		if((cape.String.getLength(markerBegin) != 2) || (cape.String.getLength(markerEnd) != 2)) {
			cape.Log.error(logContext, ((("Invalid template markers: `" + markerBegin) + "' and `") + markerEnd) + "'");
			return(null);
		}
		char mb1 = cape.String.charAt(markerBegin, 0);
		char mb2 = cape.String.charAt(markerBegin, 1);
		char me1 = cape.String.charAt(markerEnd, 0);
		char me2 = cape.String.charAt(markerEnd, 1);
		char pc = ' ';
		cape.StringBuilder tag = null;
		cape.StringBuilder data = null;
		cape.CharacterIterator it = cape.String.iterate(inputdata);
		java.util.ArrayList<java.lang.Object> v = new java.util.ArrayList<java.lang.Object>();
		while(it != null) {
			char c = it.getNextChar();
			if(c <= 0) {
				break;
			}
			if(tag != null) {
				if((pc == me1) && (tag.count() > 2)) {
					tag.append(pc);
					tag.append(c);
					if(c == me2) {
						java.lang.String tt = tag.toString();
						java.lang.String tts = cape.String.strip(cape.String.subString(tt, 2, cape.String.getLength(tt) - 4));
						java.util.ArrayList<java.lang.String> words = cape.String.quotedStringToVector(tts, ' ');
						if(android.text.TextUtils.equals(cape.Vector.get(words, 0), "include")) {
							java.lang.String fileName = cape.Vector.get(words, 1);
							if(cape.String.isEmpty(fileName)) {
								cape.Log.warning(logContext, "Include tag with empty filename. Ignoring it.");
							}
							else {
								cape.File ff = null;
								if(cape.Environment.isAbsolutePath(fileName)) {
									ff = cape.FileInstance.forPath(fileName);
								}
								else if(includeDirs != null) {
									int n = 0;
									int m = includeDirs.size();
									for(n = 0 ; n < m ; n++) {
										cape.File includeDir = includeDirs.get(n);
										if(includeDir != null) {
											cape.File x = cape.FileInstance.forRelativePath(fileName, includeDir);
											if(x.isFile()) {
												ff = x;
												break;
											}
										}
									}
								}
								if((ff == null) || (ff.isFile() == false)) {
									cape.Log.warning(logContext, ("Included file was not found: `" + fileName) + "'");
								}
								else {
									java.lang.String cc = ff.getContentsString("UTF-8");
									if(android.text.TextUtils.equals(cc, null)) {
										cape.Log.warning(logContext, ("Failed to read included file: `" + ff.getPath()) + "'");
									}
									else {
										TextTemplate nt = forString(cc, markerBegin, markerEnd, type, includeDirs);
										if(nt == null) {
											cape.Log.warning(logContext, ("Failed to read included template file: `" + ff.getPath()) + "'");
										}
										else {
											java.util.ArrayList<java.lang.Object> array = nt.getTokens();
											if(array != null) {
												int n2 = 0;
												int m2 = array.size();
												for(n2 = 0 ; n2 < m2 ; n2++) {
													java.lang.Object token = array.get(n2);
													if(token != null) {
														v.add(token);
													}
												}
											}
										}
									}
								}
							}
						}
						else {
							v.add(new TagData(words));
						}
						tag = null;
					}
				}
				else if(c != me1) {
					tag.append(c);
				}
			}
			else if(pc == mb1) {
				if(c == mb2) {
					if(data != null) {
						v.add(data);
						data = null;
					}
					tag = new cape.StringBuilder();
					tag.append(pc);
					tag.append(c);
				}
				else {
					if(data == null) {
						data = new cape.StringBuilder();
					}
					data.append(pc);
					data.append(c);
				}
			}
			else if(c != mb1) {
				if(data == null) {
					data = new cape.StringBuilder();
				}
				data.append(c);
			}
			pc = c;
		}
		if(pc == mb1) {
			if(data == null) {
				data = new cape.StringBuilder();
			}
			data.append(pc);
		}
		if(data != null) {
			v.add(data);
			data = null;
		}
		if(tag != null) {
			cape.Log.error(logContext, ("Unfinished tag: `" + tag.toString()) + "'");
			return(null);
		}
		return(v);
	}

	public boolean prepare(java.util.ArrayList<cape.File> includeDirs) {
		java.lang.String str = text;
		if(android.text.TextUtils.equals(str, null)) {
			cape.Log.error(logContext, "No input string was specified.");
			return(false);
		}
		tokens = tokenizeString(str, includeDirs);
		if(tokens == null) {
			return(false);
		}
		return(true);
	}

	public java.lang.String execute(cape.DynamicMap vars, java.util.ArrayList<cape.File> includeDirs) {
		if(tokens == null) {
			cape.Log.error(logContext, "TextTemplate has not been prepared");
			return(null);
		}
		cape.StringBuilder result = new cape.StringBuilder();
		if(doExecute(tokens, vars, result, includeDirs) == false) {
			return(null);
		}
		return(result.toString());
	}

	public java.lang.String execute(cape.DynamicMap vars) {
		return(execute(vars, null));
	}

	private java.lang.String substituteVariables(java.lang.String orig, cape.DynamicMap vars) {
		if(android.text.TextUtils.equals(orig, null)) {
			return(orig);
		}
		if(cape.String.indexOf(orig, "${") < 0) {
			return(orig);
		}
		cape.StringBuilder sb = new cape.StringBuilder();
		cape.StringBuilder varbuf = null;
		boolean flag = false;
		cape.CharacterIterator it = cape.String.iterate(orig);
		while(it != null) {
			char c = it.getNextChar();
			if(c <= 0) {
				break;
			}
			if(varbuf != null) {
				if(c == '}') {
					java.lang.String varname = varbuf.toString();
					if(vars != null) {
						java.lang.String varcut = null;
						if(cape.String.indexOf(varname, '!') > 0) {
							cape.Iterator<java.lang.String> sp = cape.Vector.iterate(cape.String.split(varname, '!', 2));
							varname = sp.next();
							varcut = sp.next();
						}
						java.lang.String r = getVariableValueString(vars, varname);
						if(cape.String.isEmpty(varcut) == false) {
							cape.CharacterIterator itc = cape.String.iterate(varcut);
							char cx = ' ';
							while((cx = itc.getNextChar()) > 0) {
								int n = cape.String.lastIndexOf(r, cx);
								if(n < 0) {
									break;
								}
								r = cape.String.subString(r, 0, n);
							}
						}
						sb.append(r);
					}
					varbuf = null;
				}
				else {
					varbuf.append(c);
				}
				continue;
			}
			if(flag == true) {
				flag = false;
				if(c == '{') {
					varbuf = new cape.StringBuilder();
				}
				else {
					sb.append('$');
					sb.append(c);
				}
				continue;
			}
			if(c == '$') {
				flag = true;
				continue;
			}
			sb.append(c);
		}
		return(sb.toString());
	}

	static public cape.DynamicMap objectAsCapeDynamicMap(java.lang.Object o) {
		if(o instanceof cape.DynamicMap) {
			return((cape.DynamicMap)o);
		}
		return(null);
	}

	public java.lang.Object getVariableValue(cape.DynamicMap vars, java.lang.String varname) {
		if((vars == null) || (android.text.TextUtils.equals(varname, null))) {
			return(null);
		}
		java.lang.Object vv = vars.get(varname);
		if(vv != null) {
			return(vv);
		}
		java.util.ArrayList<java.lang.String> ll = cape.String.split(varname, '.');
		if(cape.Vector.getSize(ll) < 2) {
			return(null);
		}
		java.lang.String tnvar = cape.Vector.get(ll, cape.Vector.getSize(ll) - 1);
		java.lang.String nvar = null;
		if(tnvar instanceof java.lang.String) {
			nvar = (java.lang.String)tnvar;
		}
		cape.Vector.removeLast(ll);
		cape.DynamicMap cc = vars;
		if(ll != null) {
			int n = 0;
			int m = ll.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.String vv2 = ll.get(n);
				if(vv2 != null) {
					if(cc == null) {
						return(null);
					}
					java.lang.Object vv2o = cc.get(vv2);
					cc = (cape.DynamicMap)((vv2o instanceof cape.DynamicMap) ? vv2o : null);
					if(((cc == null) && (vv2o != null)) && (vv2o instanceof cape.JSONObject)) {
						cc = objectAsCapeDynamicMap((java.lang.Object)((cape.JSONObject)vv2o).toJsonObject());
					}
				}
			}
		}
		if(cc != null) {
			return(cc.get(nvar));
		}
		return(null);
	}

	public java.lang.String getVariableValueString(cape.DynamicMap vars, java.lang.String varname) {
		java.lang.Object v = getVariableValue(vars, varname);
		if(v == null) {
			return(null);
		}
		if(v instanceof cape.DynamicMap) {
			if(languagePreferences != null) {
				if(languagePreferences != null) {
					int n = 0;
					int m = languagePreferences.size();
					for(n = 0 ; n < m ; n++) {
						java.lang.String language = languagePreferences.get(n);
						if(language != null) {
							java.lang.String s = ((cape.DynamicMap)v).getString(language);
							if(!(android.text.TextUtils.equals(s, null))) {
								return(s);
							}
						}
					}
				}
			}
			else {
				java.lang.String s = ((cape.DynamicMap)v).getString("en");
				if(!(android.text.TextUtils.equals(s, null))) {
					return(s);
				}
			}
			return(null);
		}
		return(cape.String.asString(v));
	}

	public java.util.ArrayList<java.lang.Object> getVariableValueVector(cape.DynamicMap vars, java.lang.String varname) {
		java.lang.Object v = getVariableValue(vars, varname);
		if(v == null) {
			return(null);
		}
		if(v instanceof java.util.ArrayList) {
			return((java.util.ArrayList<java.lang.Object>)v);
		}
		if(v instanceof cape.VectorObject) {
			cape.VectorObject<java.lang.Object> vo = (cape.VectorObject<java.lang.Object>)v;
			java.util.ArrayList<java.lang.Object> vv = vo.toVector();
			return(vv);
		}
		if(v instanceof cape.ArrayObject) {
			cape.ArrayObject<java.lang.Object> vo = (cape.ArrayObject<java.lang.Object>)((v instanceof cape.ArrayObject) ? v : null);
			java.lang.Object[] vv = vo.toArray();
			java.util.ArrayList<java.lang.Object> vvx = cape.Vector.forArray(vv);
			return(vvx);
		}
		if(v instanceof cape.DynamicVector) {
			return(((cape.DynamicVector)v).toVector());
		}
		return(null);
	}

	public boolean handleBlock(cape.DynamicMap vars, java.util.ArrayList<java.lang.String> tag, java.util.ArrayList<java.lang.Object> data, cape.StringBuilder result, java.util.ArrayList<cape.File> includeDirs) {
		if(tag == null) {
			return(false);
		}
		java.lang.String tagname = cape.Vector.get(tag, 0);
		if(cape.String.isEmpty(tagname)) {
			return(false);
		}
		if(android.text.TextUtils.equals(tagname, "for")) {
			java.lang.String varname = cape.Vector.get(tag, 1);
			java.lang.String inword = cape.Vector.get(tag, 2);
			java.lang.String origvar = substituteVariables(cape.Vector.get(tag, 3), vars);
			if((cape.String.isEmpty(varname) || cape.String.isEmpty(origvar)) || !(android.text.TextUtils.equals(inword, "in"))) {
				cape.Log.error(logContext, ("Invalid for tag: `" + cape.String.combine(tag, ' ')) + "'");
				return(false);
			}
			int index = 0;
			vars.set("__for_first", (java.lang.Object)"true");
			java.util.ArrayList<java.lang.Object> vv = getVariableValueVector(vars, origvar);
			if(vv != null) {
				int n = 0;
				int m = vv.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.Object o = vv.get(n);
					if(o != null) {
						if((index % 2) == 0) {
							vars.set("__for_parity", (java.lang.Object)"even");
						}
						else {
							vars.set("__for_parity", (java.lang.Object)"odd");
						}
						vars.set(varname, o);
						if(doExecute(data, vars, result, includeDirs) == false) {
							return(false);
						}
						if(index == 0) {
							vars.set("__for_first", (java.lang.Object)"false");
						}
						index++;
					}
				}
			}
			vars.remove("__for_first");
			vars.remove("__for_parity");
			return(true);
		}
		if(android.text.TextUtils.equals(tagname, "if")) {
			java.lang.String lvalue = cape.Vector.get(tag, 1);
			if(android.text.TextUtils.equals(lvalue, null)) {
				return(true);
			}
			java.lang.String _x_operator = cape.Vector.get(tag, 2);
			if(android.text.TextUtils.equals(_x_operator, null)) {
				java.lang.Object varval = getVariableValue(vars, lvalue);
				if(varval == null) {
					return(true);
				}
				if(varval instanceof cape.VectorObject) {
					if(cape.Vector.isEmpty(((cape.VectorObject<java.lang.Object>)varval).toVector())) {
						return(true);
					}
				}
				if(varval instanceof cape.DynamicMap) {
					cape.DynamicMap value = (cape.DynamicMap)varval;
					if(value.getCount() < 1) {
						return(true);
					}
				}
				if(varval instanceof java.lang.String) {
					if(cape.String.isEmpty((java.lang.String)varval)) {
						return(true);
					}
				}
				if(varval instanceof cape.StringObject) {
					if(cape.String.isEmpty(((cape.StringObject)varval).toString())) {
						return(true);
					}
				}
				if(doExecute(data, vars, result, includeDirs) == false) {
					return(false);
				}
				return(true);
			}
			lvalue = substituteVariables(lvalue, vars);
			java.lang.String rvalue = cape.Vector.get(tag, 3);
			if(!(android.text.TextUtils.equals(rvalue, null))) {
				rvalue = substituteVariables(rvalue, vars);
			}
			if(((android.text.TextUtils.equals(lvalue, null)) || cape.String.isEmpty(_x_operator)) || (android.text.TextUtils.equals(rvalue, null))) {
				cape.Log.error(logContext, ("Invalid if tag: `" + cape.String.combine(tag, ' ')) + "'");
				return(false);
			}
			if(((android.text.TextUtils.equals(_x_operator, "==")) || (android.text.TextUtils.equals(_x_operator, "="))) || (android.text.TextUtils.equals(_x_operator, "is"))) {
				if(!(android.text.TextUtils.equals(rvalue, lvalue))) {
					return(true);
				}
				if(doExecute(data, vars, result, includeDirs) == false) {
					return(false);
				}
				return(true);
			}
			if((android.text.TextUtils.equals(_x_operator, "!=")) || (android.text.TextUtils.equals(_x_operator, "not"))) {
				if(android.text.TextUtils.equals(rvalue, lvalue)) {
					return(true);
				}
				if(doExecute(data, vars, result, includeDirs) == false) {
					return(false);
				}
				return(true);
			}
			cape.Log.error(logContext, ((("Unknown operator `" + _x_operator) + "' in if tag: `") + cape.String.combine(tag, ' ')) + "'");
			return(false);
		}
		return(false);
	}

	private boolean doExecute(java.util.ArrayList<java.lang.Object> data, cape.DynamicMap avars, cape.StringBuilder result, java.util.ArrayList<cape.File> includeDirs) {
		if(data == null) {
			return(true);
		}
		int blockctr = 0;
		java.util.ArrayList<java.lang.Object> blockdata = null;
		java.util.ArrayList<java.lang.String> blocktag = null;
		cape.DynamicMap vars = avars;
		if(vars == null) {
			vars = new cape.DynamicMap();
		}
		if(data != null) {
			int n2 = 0;
			int m = data.size();
			for(n2 = 0 ; n2 < m ; n2++) {
				java.lang.Object o = data.get(n2);
				if(o != null) {
					java.lang.String tagname = null;
					java.util.ArrayList<java.lang.String> words = null;
					TagData tagData = (TagData)((o instanceof TagData) ? o : null);
					if(tagData != null) {
						words = tagData.words;
						if(words != null) {
							tagname = cape.Vector.get(words, 0);
							if(cape.String.isEmpty(tagname)) {
								cape.Log.warning(logContext, "Empty tag encountered. Ignoring it.");
								continue;
							}
						}
					}
					if(android.text.TextUtils.equals(tagname, "end")) {
						blockctr--;
						if((blockctr == 0) && (blockdata != null)) {
							if(handleBlock(vars, blocktag, blockdata, result, includeDirs) == false) {
								cape.Log.error(logContext, "Handling of a block failed");
								continue;
							}
							blockdata = null;
							blocktag = null;
						}
					}
					if(blockctr > 0) {
						if((android.text.TextUtils.equals(tagname, "for")) || (android.text.TextUtils.equals(tagname, "if"))) {
							blockctr++;
						}
						if(blockdata == null) {
							blockdata = new java.util.ArrayList<java.lang.Object>();
						}
						blockdata.add(o);
						continue;
					}
					if((o instanceof java.lang.String) || (o instanceof cape.StringObject)) {
						result.append(cape.String.asString(o));
						continue;
					}
					if((android.text.TextUtils.equals(tagname, "=")) || (android.text.TextUtils.equals(tagname, "printstring"))) {
						java.lang.String varname = substituteVariables(cape.Vector.get(words, 1), vars);
						if(cape.String.isEmpty(varname) == false) {
							java.lang.String vv = getVariableValueString(vars, varname);
							if(cape.String.isEmpty(vv) == false) {
								if(this.type == TYPE_HTML) {
									vv = capex.HTMLString.sanitize(vv);
								}
								result.append(vv);
							}
							else {
								java.lang.String defaultvalue = substituteVariables(cape.Vector.get(words, 2), vars);
								if(cape.String.isEmpty(defaultvalue) == false) {
									if(this.type == TYPE_HTML) {
										defaultvalue = capex.HTMLString.sanitize(defaultvalue);
									}
									result.append(defaultvalue);
								}
							}
						}
					}
					else if(android.text.TextUtils.equals(tagname, "printRaw")) {
						java.lang.String varname = substituteVariables(cape.Vector.get(words, 1), vars);
						if(cape.String.isEmpty(varname) == false) {
							java.lang.String vv = getVariableValueString(vars, varname);
							if(cape.String.isEmpty(vv) == false) {
								result.append(vv);
							}
							else {
								java.lang.String defaultvalue = substituteVariables(cape.Vector.get(words, 2), vars);
								if(cape.String.isEmpty(defaultvalue) == false) {
									result.append(defaultvalue);
								}
							}
						}
					}
					else if(android.text.TextUtils.equals(tagname, "catPath")) {
						boolean hasSlash = false;
						int n = 0;
						if(words != null) {
							int n3 = 0;
							int m2 = words.size();
							for(n3 = 0 ; n3 < m2 ; n3++) {
								java.lang.String word = words.get(n3);
								if(word != null) {
									n++;
									if(n == 1) {
										continue;
									}
									word = substituteVariables(word, vars);
									cape.CharacterIterator it = cape.String.iterate(word);
									if(it == null) {
										continue;
									}
									if((n > 2) && (hasSlash == false)) {
										result.append('/');
										hasSlash = true;
									}
									while(true) {
										char c = it.getNextChar();
										if(c < 1) {
											break;
										}
										if(c == '/') {
											if(hasSlash == false) {
												result.append(c);
												hasSlash = true;
											}
										}
										else {
											result.append(c);
											hasSlash = false;
										}
									}
								}
							}
						}
					}
					else if(android.text.TextUtils.equals(tagname, "printJson")) {
						java.lang.String varname = substituteVariables(cape.Vector.get(words, 1), vars);
						if(cape.String.isEmpty(varname) == false) {
							java.lang.Object vv = getVariableValue(vars, varname);
							if(vv != null) {
								result.append(cape.JSONEncoder.encode(vv));
							}
						}
					}
					else if(android.text.TextUtils.equals(tagname, "printRichText")) {
						java.lang.String markup = substituteVariables(cape.Vector.get(words, 1), vars);
						if(cape.String.isEmpty(markup) == false) {
							RichTextDocument doc = capex.RichTextDocument.forWikiMarkupString(markup);
							if(doc != null) {
								result.append(doc.toHtml(null));
							}
						}
					}
					else if(android.text.TextUtils.equals(tagname, "printDate")) {
						java.lang.String timestamp = substituteVariables(cape.Vector.get(words, 1), vars);
						long aslong = cape.String.toLong(timestamp);
						java.lang.String asstring = cape.DateTime.forTimeSeconds(aslong).toStringDate('/');
						result.append(asstring);
					}
					else if(android.text.TextUtils.equals(tagname, "printTime")) {
						java.lang.String timestamp = substituteVariables(cape.Vector.get(words, 1), vars);
						long aslong = cape.String.toLong(timestamp);
						java.lang.String asstring = cape.DateTime.forTimeSeconds(aslong).toStringTime(':');
						result.append(asstring);
					}
					else if(android.text.TextUtils.equals(tagname, "printDateTime")) {
						java.lang.String timestamp = substituteVariables(cape.Vector.get(words, 1), vars);
						long aslong = cape.String.toLong(timestamp);
						cape.DateTime dt = cape.DateTime.forTimeSeconds(aslong);
						result.append(dt.toStringDate('/'));
						result.append(' ');
						result.append(dt.toStringTime(':'));
					}
					else if(android.text.TextUtils.equals(tagname, "import")) {
						java.lang.String type = cape.Vector.get(words, 1);
						java.lang.String filename = substituteVariables(cape.Vector.get(words, 2), vars);
						if(cape.String.isEmpty(filename)) {
							cape.Log.warning(logContext, "Invalid import tag with empty filename");
							continue;
						}
						cape.File ff = null;
						if(includeDirs != null) {
							int n4 = 0;
							int m3 = includeDirs.size();
							for(n4 = 0 ; n4 < m3 ; n4++) {
								cape.File dir = includeDirs.get(n4);
								if(dir != null) {
									ff = cape.FileInstance.forRelativePath(filename, dir);
									if((ff != null) && ff.isFile()) {
										break;
									}
								}
							}
						}
						if((ff == null) || (ff.isFile() == false)) {
							cape.Log.error(logContext, ("Unable to find file to import: `" + filename) + "'");
							continue;
						}
						cape.Log.debug(logContext, ("Attempting to import file: `" + ff.getPath()) + "'");
						java.lang.String content = ff.getContentsString("UTF-8");
						if(cape.String.isEmpty(content)) {
							cape.Log.error(logContext, ("Unable to read import file: `" + ff.getPath()) + "'");
							continue;
						}
						if(android.text.TextUtils.equals(type, "html")) {
							content = capex.HTMLString.sanitize(content);
						}
						else if(android.text.TextUtils.equals(type, "template")) {
							TextTemplate t = forString(content, markerBegin, markerEnd, this.type, includeDirs);
							if(t == null) {
								cape.Log.error(logContext, ("Failed to parse imported template file: `" + ff.getPath()) + "'");
								continue;
							}
							if(doExecute(t.getTokens(), vars, result, includeDirs) == false) {
								cape.Log.error(logContext, ("Failed to process imported template file: `" + ff.getPath()) + "'");
								continue;
							}
							content = null;
						}
						else if(android.text.TextUtils.equals(type, "raw")) {
							;
						}
						else {
							cape.Log.error(logContext, ("Unknown type for import: `" + type) + "'. Ignoring the import.");
							continue;
						}
						if(cape.String.isEmpty(content) == false) {
							result.append(content);
						}
					}
					else if(android.text.TextUtils.equals(tagname, "escapeHtml")) {
						java.lang.String content = substituteVariables(cape.Vector.get(words, 1), vars);
						if(cape.String.isEmpty(content) == false) {
							content = capex.HTMLString.sanitize(content);
							if(cape.String.isEmpty(content) == false) {
								result.append(content);
							}
						}
					}
					else if(android.text.TextUtils.equals(tagname, "set")) {
						if(cape.Vector.getSize(words) != 3) {
							cape.Log.warning(logContext, "Invalid number of parameters for set tag encountered: " + cape.String.forInteger(cape.Vector.getSize(words)));
							continue;
						}
						java.lang.String varname = substituteVariables(cape.Vector.get(words, 1), vars);
						if(cape.String.isEmpty(varname)) {
							cape.Log.warning(logContext, "Empty variable name in set tag encountered.");
							continue;
						}
						java.lang.String newValue = substituteVariables(cape.Vector.get(words, 2), vars);
						vars.set(varname, (java.lang.Object)newValue);
					}
					else if(android.text.TextUtils.equals(tagname, "assign")) {
						if(cape.Vector.getSize(words) != 3) {
							cape.Log.warning(logContext, "Invalid number of parameters for assign tag encountered: " + cape.String.forInteger(cape.Vector.getSize(words)));
							continue;
						}
						java.lang.String varname = substituteVariables(cape.Vector.get(words, 1), vars);
						if(cape.String.isEmpty(varname)) {
							cape.Log.warning(logContext, "Empty variable name in assign tag encountered.");
							continue;
						}
						java.lang.String vv = cape.Vector.get(words, 2);
						if(android.text.TextUtils.equals(vv, "none")) {
							vars.remove(varname);
						}
						else {
							vars.set(varname, getVariableValue(vars, vv));
						}
					}
					else if((android.text.TextUtils.equals(tagname, "for")) || (android.text.TextUtils.equals(tagname, "if"))) {
						if(blockctr == 0) {
							blocktag = words;
						}
						blockctr++;
					}
					else if(android.text.TextUtils.equals(tagname, "end")) {
						;
					}
					else if(type == TYPE_HTML) {
						onHTMLTag(vars, result, includeDirs, tagname, words);
					}
					else if(type == TYPE_JSON) {
						onJSONTag(vars, result, includeDirs, tagname, words);
					}
					else {
						;
					}
				}
			}
		}
		return(true);
	}

	private java.lang.String basename(java.lang.String path) {
		if(android.text.TextUtils.equals(path, null)) {
			return(null);
		}
		java.lang.String v = "";
		cape.Iterator<java.lang.String> comps = cape.Vector.iterate(cape.String.split(path, '/'));
		if(comps != null) {
			java.lang.String comp = comps.next();
			while(!(android.text.TextUtils.equals(comp, null))) {
				if(cape.String.getLength(comp) > 0) {
					v = comp;
				}
				comp = comps.next();
			}
		}
		return(v);
	}

	private void onHTMLTag(cape.DynamicMap vars, cape.StringBuilder result, java.util.ArrayList<cape.File> includeDirs, java.lang.String tagname, java.util.ArrayList<java.lang.String> words) {
		if(android.text.TextUtils.equals(tagname, "link")) {
			java.lang.String path = substituteVariables(cape.Vector.get(words, 1), vars);
			java.lang.String text = substituteVariables(cape.Vector.get(words, 2), vars);
			if(cape.String.isEmpty(text)) {
				text = basename(path);
			}
			if(cape.String.isEmpty(text)) {
				return;
			}
			result.append(((("<a href=\"" + path) + "\"><span>") + text) + "</span></a>");
			return;
		}
	}

	public void encodeJSONString(java.lang.String s, cape.StringBuilder sb) {
		if(android.text.TextUtils.equals(s, null)) {
			return;
		}
		cape.CharacterIterator it = cape.String.iterate(s);
		if(it == null) {
			return;
		}
		char c = ' ';
		while((c = it.getNextChar()) > 0) {
			if(c == '\"') {
				sb.append('\\');
			}
			else if(c == '\\') {
				sb.append('\\');
			}
			sb.append(c);
		}
	}

	private void onJSONTag(cape.DynamicMap vars, cape.StringBuilder result, java.util.ArrayList<cape.File> includeDirs, java.lang.String tagname, java.util.ArrayList<java.lang.String> words) {
		if(android.text.TextUtils.equals(tagname, "quotedstring")) {
			java.lang.String string = substituteVariables(cape.Vector.get(words, 1), vars);
			encodeJSONString(string, result);
		}
	}

	public java.util.ArrayList<java.lang.Object> getTokens() {
		return(tokens);
	}

	public TextTemplate setTokens(java.util.ArrayList<java.lang.Object> v) {
		tokens = v;
		return(this);
	}

	public java.lang.String getText() {
		return(text);
	}

	public TextTemplate setText(java.lang.String v) {
		text = v;
		return(this);
	}

	public java.lang.String getMarkerBegin() {
		return(markerBegin);
	}

	public TextTemplate setMarkerBegin(java.lang.String v) {
		markerBegin = v;
		return(this);
	}

	public java.lang.String getMarkerEnd() {
		return(markerEnd);
	}

	public TextTemplate setMarkerEnd(java.lang.String v) {
		markerEnd = v;
		return(this);
	}

	public cape.LoggingContext getLogContext() {
		return(logContext);
	}

	public TextTemplate setLogContext(cape.LoggingContext v) {
		logContext = v;
		return(this);
	}

	public int getType() {
		return(type);
	}

	public TextTemplate setType(int v) {
		type = v;
		return(this);
	}

	public java.util.ArrayList<java.lang.String> getLanguagePreferences() {
		return(languagePreferences);
	}

	public TextTemplate setLanguagePreferences(java.util.ArrayList<java.lang.String> v) {
		languagePreferences = v;
		return(this);
	}
}
