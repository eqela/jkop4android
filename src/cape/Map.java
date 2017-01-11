
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

public class Map
{
	private static class MyMapObject<K, V> implements MapObject<K, V>
	{
		private java.util.HashMap<K,V> map = null;

		public java.util.HashMap<K,V> toMap() {
			return(map);
		}

		public java.util.HashMap<K,V> getMap() {
			return(map);
		}

		public MyMapObject<K, V> setMap(java.util.HashMap<K,V> v) {
			map = v;
			return(this);
		}
	}

	public static <K, V> MapObject<K, V> asObject(java.util.HashMap<K,V> map) {
		if(map == null) {
			return(null);
		}
		MyMapObject<K, V> v = new MyMapObject<K, V>();
		v.setMap(map);
		return((MapObject<K, V>)v);
	}

	public static <K, V> V get(java.util.HashMap<K,V> map, K key, V ddf) {
		if((map == null) || (key == null)) {
			return(ddf);
		}
		if(containsKey(map, key) == false) {
			return(ddf);
		}
		return(getValue(map, key));
	}

	public static <K, V> V get(java.util.HashMap<K,V> map, K key) {
		return(getValue(map, key));
	}

	public static <K, V> V getValue(java.util.HashMap<K,V> map, K key) {
		if((map == null) || (key == null)) {
			return((V)(null));
		}
		return(map.get(key));
	}

	public static <K, V> boolean set(java.util.HashMap<K,V> data, K key, V val) {
		if((data == null) || (key == null)) {
			return(false);
		}
		data.put(key, val);
		return(true);
	}

	public static <K, V> boolean setValue(java.util.HashMap<K,V> data, K key, V val) {
		return(set(data, key, val));
	}

	public static <K, V> void remove(java.util.HashMap<K,V> data, K key) {
		if((data == null) || (key == null)) {
			return;
		}
		data.remove(key);
	}

	public static <K, V> int count(java.util.HashMap<K,V> data) {
		if(data == null) {
			return(0);
		}
		return(data.size());
	}

	public static <K, V> boolean containsKey(java.util.HashMap<K,V> data, K key) {
		if((data == null) || (key == null)) {
			return(false);
		}
		return(data.containsKey(key));
	}

	public static <K, V> boolean containsValue(java.util.HashMap<K,V> data, V val) {
		if((data == null) || (val == null)) {
			return(false);
		}
		return(data.containsValue(val));
	}

	public static <K, V> void clear(java.util.HashMap<K,V> data) {
		if(data == null) {
			return;
		}
		data.clear();
	}

	public static <K, V> java.util.HashMap<K,V> dup(java.util.HashMap<K,V> data) {
		if(data == null) {
			return(null);
		}
		return((java.util.HashMap<K,V>)(data.clone()));
	}

	public static <K, V> java.util.ArrayList<K> getKeys(java.util.HashMap<K,V> data) {
		if(data == null) {
			return(null);
		}
		java.util.ArrayList<K> v = new java.util.ArrayList<K>();
		for(K key : data.keySet()) {
			v.add(key);
		}
		return(v);
	}

	public static <K, V> java.util.ArrayList<V> getValues(java.util.HashMap<K,V> data) {
		if(data == null) {
			return(null);
		}
		java.util.ArrayList<V> v = new java.util.ArrayList<V>();
		for(V value : data.values()) {
			v.add(value);
		}
		return(v);
	}

	public static <K, V> Iterator<K> iterateKeys(java.util.HashMap<K,V> data) {
		return(cape.Vector.iterate(getKeys(data)));
	}

	public static <K, V> Iterator<V> iterateValues(java.util.HashMap<K,V> data) {
		return(cape.Vector.iterate(getValues(data)));
	}
}
