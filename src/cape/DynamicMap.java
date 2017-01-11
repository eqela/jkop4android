
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

public class DynamicMap implements Duplicateable, Iterateable<java.lang.String>
{
	public static DynamicMap asDynamicMap(java.lang.Object object) {
		if(object == null) {
			return(null);
		}
		if(object instanceof DynamicMap) {
			return((DynamicMap)object);
		}
		if(object instanceof java.util.HashMap) {
			return(forObjectMap((java.util.HashMap<java.lang.Object,java.lang.Object>)object));
		}
		return(null);
	}

	public static DynamicMap forObjectMap(java.util.HashMap<java.lang.Object,java.lang.Object> map) {
		DynamicMap v = new DynamicMap();
		if(map != null) {
			Iterator<java.lang.Object> it = cape.Map.iterateKeys(map);
			while(it != null) {
				java.lang.Object key = it.next();
				if(key == null) {
					break;
				}
				if((key instanceof java.lang.String) == false) {
					continue;
				}
				v.set((java.lang.String)key, cape.Map.getValue(map, (java.lang.String)key));
			}
		}
		return(v);
	}

	public static DynamicMap forStringMap(java.util.HashMap<java.lang.String,java.lang.String> map) {
		DynamicMap v = new DynamicMap();
		if(map != null) {
			Iterator<java.lang.String> it = cape.Map.iterateKeys(map);
			while(it != null) {
				java.lang.String key = it.next();
				if(android.text.TextUtils.equals(key, null)) {
					break;
				}
				v.set(key, (java.lang.Object)cape.Map.getValue(map, key));
			}
		}
		return(v);
	}

	private java.util.HashMap<java.lang.String,java.lang.Object> map = null;

	public DynamicMap() {
		map = new java.util.HashMap<java.lang.String,java.lang.Object>();
	}

	public java.lang.Object duplicate() {
		return((java.lang.Object)duplicateMap());
	}

	public DynamicMap duplicateMap() {
		DynamicMap v = new DynamicMap();
		Iterator<java.lang.String> it = iterateKeys();
		while(it != null) {
			java.lang.String key = it.next();
			if(android.text.TextUtils.equals(key, null)) {
				break;
			}
			v.set(key, get(key));
		}
		return(v);
	}

	public DynamicMap mergeFrom(DynamicMap other) {
		if(other == null) {
			return(this);
		}
		Iterator<java.lang.String> it = other.iterateKeys();
		while(it != null) {
			java.lang.String key = it.next();
			if(android.text.TextUtils.equals(key, null)) {
				break;
			}
			set(key, other.get(key));
		}
		return(this);
	}

	public DynamicMap set(java.lang.String key, java.lang.Object value) {
		if(!(android.text.TextUtils.equals(key, null))) {
			map.put(key, value);
		}
		return(this);
	}

	public DynamicMap set(java.lang.String key, byte[] value) {
		return(set(key, (java.lang.Object)cape.Buffer.asObject(value)));
	}

	public DynamicMap set(java.lang.String key, int value) {
		return(set(key, (java.lang.Object)cape.Integer.asObject(value)));
	}

	public DynamicMap set(java.lang.String key, boolean value) {
		return(set(key, (java.lang.Object)cape.Boolean.asObject(value)));
	}

	public DynamicMap set(java.lang.String key, double value) {
		return(set(key, (java.lang.Object)cape.Double.asObject(value)));
	}

	public java.lang.Object get(java.lang.String key) {
		return(cape.Map.get(map, key, null));
	}

	public java.lang.String getString(java.lang.String key, java.lang.String defval) {
		java.lang.String v = cape.String.asString(get(key));
		if(android.text.TextUtils.equals(v, null)) {
			return(defval);
		}
		return(v);
	}

	public java.lang.String getString(java.lang.String key) {
		return(getString(key, null));
	}

	public int getInteger(java.lang.String key, int defval) {
		java.lang.Object vv = get(key);
		if(vv == null) {
			return(defval);
		}
		return(cape.Integer.asInteger(vv));
	}

	public int getInteger(java.lang.String key) {
		return(getInteger(key, 0));
	}

	public boolean getBoolean(java.lang.String key, boolean defval) {
		java.lang.Object vv = get(key);
		if(vv == null) {
			return(defval);
		}
		return(cape.Boolean.asBoolean(vv));
	}

	public boolean getBoolean(java.lang.String key) {
		return(getBoolean(key, false));
	}

	public double getDouble(java.lang.String key, double defval) {
		java.lang.Object vv = get(key);
		if(vv == null) {
			return(defval);
		}
		return(cape.Double.asDouble(vv));
	}

	public double getDouble(java.lang.String key) {
		return(getDouble(key, 0.00));
	}

	public byte[] getBuffer(java.lang.String key) {
		java.lang.Object vv = get(key);
		if(vv == null) {
			return(null);
		}
		return(cape.Buffer.asBuffer(vv));
	}

	public DynamicVector getDynamicVector(java.lang.String key) {
		java.lang.Object tvv = get(key);
		DynamicVector vv = null;
		if(tvv instanceof DynamicVector) {
			vv = (DynamicVector)tvv;
		}
		if(vv != null) {
			return(vv);
		}
		java.util.ArrayList<java.lang.Object> v = getVector(key);
		if(v != null) {
			return(cape.DynamicVector.forObjectVector(v));
		}
		return(null);
	}

	public java.util.ArrayList<java.lang.Object> getVector(java.lang.String key) {
		java.lang.Object val = get(key);
		if(val == null) {
			return(null);
		}
		if(val instanceof java.util.ArrayList) {
			return((java.util.ArrayList<java.lang.Object>)val);
		}
		if(val instanceof VectorObject) {
			VectorObject<java.lang.Object> vo = (VectorObject<java.lang.Object>)val;
			java.util.ArrayList<java.lang.Object> vv = vo.toVector();
			return(vv);
		}
		return(null);
	}

	static public DynamicMap objectAsCapeDynamicMap(java.lang.Object o) {
		if(o instanceof DynamicMap) {
			return((DynamicMap)o);
		}
		return(null);
	}

	public DynamicMap getDynamicMap(java.lang.String key) {
		return(objectAsCapeDynamicMap((java.lang.Object)get(key)));
	}

	public java.util.ArrayList<java.lang.String> getKeys() {
		java.util.ArrayList<java.lang.String> v = new java.util.ArrayList<java.lang.String>();
		Iterator<java.lang.String> it = iterateKeys();
		while(true) {
			java.lang.String kk = it.next();
			if(android.text.TextUtils.equals(kk, null)) {
				break;
			}
			v.add(kk);
		}
		return(v);
	}

	public Iterator<java.lang.String> iterate() {
		Iterator<java.lang.String> v = cape.Map.iterateKeys(map);
		return(v);
	}

	public Iterator<java.lang.String> iterateKeys() {
		Iterator<java.lang.String> v = cape.Map.iterateKeys(map);
		return(v);
	}

	public Iterator<java.lang.Object> iterateValues() {
		Iterator<java.lang.Object> v = cape.Map.iterateValues(map);
		return(v);
	}

	public void remove(java.lang.String key) {
		cape.Map.remove(map, key);
	}

	public void clear() {
		cape.Map.clear(map);
	}

	public int getCount() {
		return(cape.Map.count(map));
	}

	public boolean containsKey(java.lang.String key) {
		return(cape.Map.containsKey(map, key));
	}
}
