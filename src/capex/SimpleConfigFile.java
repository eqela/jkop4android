
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

public class SimpleConfigFile
{
	public static SimpleConfigFile forFile(cape.File file) {
		SimpleConfigFile v = new SimpleConfigFile();
		if(v.read(file) == false) {
			v = null;
		}
		return(v);
	}

	public static SimpleConfigFile forMap(cape.DynamicMap map) {
		SimpleConfigFile v = new SimpleConfigFile();
		v.setDataAsMap(map);
		return(v);
	}

	public static cape.DynamicMap readFileAsMap(cape.File file) {
		SimpleConfigFile cf = forFile(file);
		if(cf == null) {
			return(null);
		}
		return(cf.getDataAsMap());
	}

	private cape.KeyValueList<java.lang.String, java.lang.String> data = null;
	private cape.DynamicMap mapData = null;
	private cape.File file = null;

	public SimpleConfigFile() {
		data = new cape.KeyValueList<java.lang.String, java.lang.String>();
	}

	public cape.File getFile() {
		return(file);
	}

	public cape.File getRelativeFile(java.lang.String fileName) {
		if((file == null) || (android.text.TextUtils.equals(fileName, null))) {
			return(null);
		}
		cape.File p = file.getParent();
		if(p == null) {
			return(null);
		}
		return(p.entry(fileName));
	}

	public void clear() {
		data.clear();
		mapData = null;
	}

	public SimpleConfigFile setDataAsMap(cape.DynamicMap map) {
		clear();
		cape.Iterator<java.lang.String> keys = map.iterateKeys();
		while(keys != null) {
			java.lang.String key = keys.next();
			if(android.text.TextUtils.equals(key, null)) {
				break;
			}
			data.add((java.lang.String)key, (java.lang.String)map.getString(key));
		}
		return(this);
	}

	public cape.DynamicMap getDataAsMap() {
		if(mapData == null) {
			mapData = new cape.DynamicMap();
			cape.Iterator<cape.KeyValuePair<java.lang.String, java.lang.String>> it = data.iterate();
			while(it != null) {
				cape.KeyValuePair<java.lang.String, java.lang.String> kvp = it.next();
				if(kvp == null) {
					break;
				}
				mapData.set(kvp.key, (java.lang.Object)kvp.value);
			}
		}
		return(mapData);
	}

	public cape.DynamicMap getDynamicMapValue(java.lang.String key, cape.DynamicMap defval) {
		java.lang.String str = getStringValue(key, null);
		if(android.text.TextUtils.equals(str, null)) {
			return(defval);
		}
		java.lang.Object tv = cape.JSONParser.parse(str);
		cape.DynamicMap v = null;
		if(tv instanceof cape.DynamicMap) {
			v = (cape.DynamicMap)tv;
		}
		if(v == null) {
			return(defval);
		}
		return(v);
	}

	public cape.DynamicVector getDynamicVectorValue(java.lang.String key, cape.DynamicVector defval) {
		java.lang.String str = getStringValue(key, null);
		if(android.text.TextUtils.equals(str, null)) {
			return(defval);
		}
		java.lang.Object tv = cape.JSONParser.parse(str);
		cape.DynamicVector v = null;
		if(tv instanceof cape.DynamicVector) {
			v = (cape.DynamicVector)tv;
		}
		if(v == null) {
			return(defval);
		}
		return(v);
	}

	public java.lang.String getStringValue(java.lang.String key, java.lang.String defval) {
		cape.DynamicMap map = getDataAsMap();
		if(map == null) {
			return(defval);
		}
		java.lang.String v = map.getString(key);
		if(android.text.TextUtils.equals(v, null)) {
			return(defval);
		}
		if(cape.String.startsWith(v, "\"\"\"\n") && cape.String.endsWith(v, "\n\"\"\"")) {
			v = cape.String.getSubString(v, 4, cape.String.getLength(v) - 8);
		}
		return(v);
	}

	public java.lang.String getStringValue(java.lang.String key) {
		return(getStringValue(key, null));
	}

	public int getIntegerValue(java.lang.String key, int defval) {
		cape.DynamicMap map = getDataAsMap();
		if(map == null) {
			return(defval);
		}
		return(map.getInteger(key, defval));
	}

	public int getIntegerValue(java.lang.String key) {
		return(getIntegerValue(key, 0));
	}

	public double getDoubleValue(java.lang.String key, double defval) {
		cape.DynamicMap map = getDataAsMap();
		if(map == null) {
			return(defval);
		}
		return(map.getDouble(key, defval));
	}

	public double getDoubleValue(java.lang.String key) {
		return(getDoubleValue(key, 0.00));
	}

	public boolean getBooleanValue(java.lang.String key, boolean defval) {
		cape.DynamicMap map = getDataAsMap();
		if(map == null) {
			return(defval);
		}
		return(map.getBoolean(key, defval));
	}

	public boolean getBooleanValue(java.lang.String key) {
		return(getBooleanValue(key, false));
	}

	public cape.File getFileValue(java.lang.String key, cape.File defval) {
		cape.File v = getRelativeFile(getStringValue(key, null));
		if(v == null) {
			return(defval);
		}
		return(v);
	}

	public cape.File getFileValue(java.lang.String key) {
		return(getFileValue(key, null));
	}

	public cape.Iterator<cape.KeyValuePair<java.lang.String, java.lang.String>> iterate() {
		if(data == null) {
			return(null);
		}
		return(data.iterate());
	}

	public boolean read(cape.File file) {
		if(file == null) {
			return(false);
		}
		cape.FileReader reader = file.read();
		if(reader == null) {
			return(false);
		}
		cape.PrintReader ins = new cape.PrintReader((cape.Reader)reader);
		java.lang.String line = null;
		java.lang.String tag = null;
		cape.StringBuilder lineBuffer = null;
		java.lang.String terminator = null;
		while(!(android.text.TextUtils.equals(line = ins.readLine(), null))) {
			if(lineBuffer != null) {
				lineBuffer.append(line);
				if(android.text.TextUtils.equals(line, terminator)) {
					line = lineBuffer.toString();
					lineBuffer = null;
					terminator = null;
				}
				else {
					lineBuffer.append('\n');
					continue;
				}
			}
			line = cape.String.strip(line);
			if(cape.String.isEmpty(line) || cape.String.startsWith(line, "#")) {
				continue;
			}
			if(cape.String.endsWith(line, "{")) {
				if(cape.String.indexOf(line, ':') < 0) {
					if(android.text.TextUtils.equals(tag, null)) {
						tag = cape.String.strip(cape.String.getSubString(line, 0, cape.String.getLength(line) - 1));
					}
					continue;
				}
				else {
					lineBuffer = new cape.StringBuilder();
					lineBuffer.append(line);
					lineBuffer.append('\n');
					terminator = "}";
					continue;
				}
			}
			if(cape.String.endsWith(line, "[")) {
				lineBuffer = new cape.StringBuilder();
				lineBuffer.append(line);
				lineBuffer.append('\n');
				terminator = "]";
				continue;
			}
			if(cape.String.endsWith(line, "\"\"\"")) {
				lineBuffer = new cape.StringBuilder();
				lineBuffer.append(line);
				lineBuffer.append('\n');
				terminator = "\"\"\"";
				continue;
			}
			if(!(android.text.TextUtils.equals(tag, null)) && (android.text.TextUtils.equals(line, "}"))) {
				tag = null;
				continue;
			}
			java.util.ArrayList<java.lang.String> sp = cape.String.split(line, ':', 2);
			if(sp == null) {
				continue;
			}
			java.lang.String key = cape.String.strip(cape.Vector.get(sp, 0));
			java.lang.String val = cape.String.strip(cape.Vector.get(sp, 1));
			if(cape.String.startsWith(val, "\"") && cape.String.endsWith(val, "\"")) {
				val = cape.String.getSubString(val, 1, cape.String.getLength(val) - 2);
			}
			if(cape.String.isEmpty(key)) {
				continue;
			}
			if(!(android.text.TextUtils.equals(tag, null))) {
				key = ((key + "[") + tag) + "]";
			}
			data.add((java.lang.String)key, (java.lang.String)val);
		}
		this.file = file;
		return(true);
	}

	public boolean write(cape.File outfile) {
		if((outfile == null) || (data == null)) {
			return(false);
		}
		cape.PrintWriter os = cape.PrintWriterWrapper.forWriter((cape.Writer)outfile.write());
		if(os == null) {
			return(false);
		}
		cape.Iterator<cape.KeyValuePair<java.lang.String, java.lang.String>> it = data.iterate();
		while(it != null) {
			cape.KeyValuePair<java.lang.String, java.lang.String> kvp = it.next();
			if(kvp == null) {
				break;
			}
			os.println((kvp.key + ": ") + kvp.value);
		}
		return(true);
	}
}
