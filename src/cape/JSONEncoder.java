
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

package cape;

public class JSONEncoder
{
	private boolean isNewLine = true;

	private void print(java.lang.String line, int indent, boolean startline, boolean endline, StringBuilder sb, boolean niceFormatting) {
		if(startline && (isNewLine == false)) {
			if(niceFormatting) {
				sb.append('\n');
			}
			else {
				sb.append(' ');
			}
			isNewLine = true;
		}
		if(isNewLine && niceFormatting) {
			for(int n = 0 ; n < indent ; n++) {
				sb.append('\t');
			}
		}
		sb.append(line);
		if(endline) {
			if(niceFormatting) {
				sb.append('\n');
			}
			else {
				sb.append(' ');
			}
			isNewLine = true;
		}
		else {
			isNewLine = false;
		}
	}

	private void encodeArray(java.lang.Object[] cc, int indent, StringBuilder sb, boolean niceFormatting) {
		print("[", indent, false, true, sb, niceFormatting);
		boolean first = true;
		if(cc != null) {
			int n = 0;
			int m = cc.length;
			for(n = 0 ; n < m ; n++) {
				java.lang.Object o = cc[n];
				if(o != null) {
					if(first == false) {
						print(",", indent, false, true, sb, niceFormatting);
					}
					encodeObject(o, indent + 1, sb, niceFormatting);
					first = false;
				}
			}
		}
		print("]", indent, true, false, sb, niceFormatting);
	}

	private void encodeDynamicVector(DynamicVector cc, int indent, StringBuilder sb, boolean niceFormatting) {
		print("[", indent, false, true, sb, niceFormatting);
		boolean first = true;
		Iterator<java.lang.Object> it = cc.iterate();
		while(it != null) {
			java.lang.Object o = it.next();
			if(o == null) {
				break;
			}
			if(first == false) {
				print(",", indent, false, true, sb, niceFormatting);
			}
			encodeObject(o, indent + 1, sb, niceFormatting);
			first = false;
		}
		print("]", indent, true, false, sb, niceFormatting);
	}

	private void encodeVector(java.util.ArrayList<java.lang.Object> cc, int indent, StringBuilder sb, boolean niceFormatting) {
		print("[", indent, false, true, sb, niceFormatting);
		boolean first = true;
		if(cc != null) {
			int n = 0;
			int m = cc.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.Object o = cc.get(n);
				if(o != null) {
					if(first == false) {
						print(",", indent, false, true, sb, niceFormatting);
					}
					encodeObject(o, indent + 1, sb, niceFormatting);
					first = false;
				}
			}
		}
		print("]", indent, true, false, sb, niceFormatting);
	}

	private void encodeMap(java.util.HashMap<java.lang.String,java.lang.Object> map, int indent, StringBuilder sb, boolean niceFormatting) {
		print("{", indent, false, true, sb, niceFormatting);
		boolean first = true;
		java.util.ArrayList<java.lang.String> keys = cape.Map.getKeys(map);
		if(keys != null) {
			int n = 0;
			int m = keys.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.String key = keys.get(n);
				if(key != null) {
					if(first == false) {
						print(",", indent, false, true, sb, niceFormatting);
					}
					encodeString(key, indent + 1, sb, niceFormatting);
					sb.append(" : ");
					encodeObject(map.get(key), indent + 1, sb, niceFormatting);
					first = false;
				}
			}
		}
		print("}", indent, true, false, sb, niceFormatting);
	}

	public void encodeDynamicMap(DynamicMap map, int indent, StringBuilder sb, boolean niceFormatting) {
		print("{", indent, false, true, sb, niceFormatting);
		boolean first = true;
		java.util.ArrayList<java.lang.String> keys = map.getKeys();
		if(keys != null) {
			int n = 0;
			int m = keys.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.String key = keys.get(n);
				if(key != null) {
					if(first == false) {
						print(",", indent, false, true, sb, niceFormatting);
					}
					encodeString(key, indent + 1, sb, niceFormatting);
					sb.append(" : ");
					encodeObject(map.get(key), indent + 1, sb, niceFormatting);
					first = false;
				}
			}
		}
		print("}", indent, true, false, sb, niceFormatting);
	}

	private void encodeKeyValueList(KeyValueListForStrings list, int indent, StringBuilder sb, boolean niceFormatting) {
		print("{", indent, false, true, sb, niceFormatting);
		boolean first = true;
		Iterator<KeyValuePair<java.lang.String, java.lang.String>> it = list.iterate();
		while(it != null) {
			KeyValuePair<java.lang.String, java.lang.String> pair = it.next();
			if(pair == null) {
				break;
			}
			if(first == false) {
				print(",", indent, false, true, sb, niceFormatting);
			}
			encodeString(pair.key, indent + 1, sb, niceFormatting);
			sb.append(" : ");
			encodeString(pair.value, indent + 1, sb, niceFormatting);
			first = false;
		}
		print("}", indent, true, false, sb, niceFormatting);
	}

	private void encodeString(java.lang.String s, int indent, StringBuilder sb, boolean niceFormatting) {
		StringBuilder mysb = new StringBuilder();
		mysb.append('\"');
		CharacterIteratorForString it = new CharacterIteratorForString(s);
		while(true) {
			char c = it.getNextChar();
			if(c < 1) {
				break;
			}
			if(c == '\"') {
				mysb.append('\\');
			}
			else if(c == '\\') {
				mysb.append('\\');
			}
			mysb.append(c);
		}
		mysb.append('\"');
		print(mysb.toString(), indent, false, false, sb, niceFormatting);
	}

	private void encodeObject(java.lang.Object o, int indent, StringBuilder sb, boolean niceFormatting) {
		if(o == null) {
			encodeString("", indent, sb, niceFormatting);
		}
		else if(o instanceof java.lang.Object[]) {
			encodeArray((java.lang.Object[])o, indent, sb, niceFormatting);
		}
		else if(o instanceof java.util.ArrayList) {
			encodeVector((java.util.ArrayList<java.lang.Object>)o, indent, sb, niceFormatting);
		}
		else if(o instanceof DynamicMap) {
			encodeDynamicMap((DynamicMap)o, indent, sb, niceFormatting);
		}
		else if(o instanceof java.lang.String) {
			encodeString((java.lang.String)o, indent, sb, niceFormatting);
		}
		else if(o instanceof StringObject) {
			encodeString(((StringObject)o).toString(), indent, sb, niceFormatting);
		}
		else if(o instanceof ArrayObject) {
			java.lang.Object[] aa = ((ArrayObject<java.lang.Object>)o).toArray();
			encodeArray(aa, indent, sb, niceFormatting);
		}
		else if(o instanceof VectorObject) {
			java.util.ArrayList<java.lang.Object> vv = ((VectorObject<java.lang.Object>)o).toVector();
			encodeVector(vv, indent, sb, niceFormatting);
		}
		else if(o instanceof DynamicVector) {
			java.util.ArrayList<java.lang.Object> vv = ((VectorObject<java.lang.Object>)o).toVector();
			encodeDynamicVector(cape.DynamicVector.forObjectVector(vv), indent, sb, niceFormatting);
		}
		else if(o instanceof KeyValueListForStrings) {
			encodeKeyValueList((KeyValueListForStrings)o, indent, sb, niceFormatting);
		}
		else if((((o instanceof IntegerObject) || (o instanceof BooleanObject)) || (o instanceof DoubleObject)) || (o instanceof CharacterObject)) {
			encodeString(cape.String.asString(o), indent, sb, niceFormatting);
		}
		else {
			encodeString("", indent, sb, niceFormatting);
		}
	}

	public static java.lang.String encode(java.lang.Object o, boolean niceFormatting) {
		StringBuilder sb = new StringBuilder();
		new JSONEncoder().encodeObject(o, 0, sb, niceFormatting);
		return(sb.toString());
	}

	public static java.lang.String encode(java.lang.Object o) {
		return(cape.JSONEncoder.encode(o, true));
	}
}
