
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

public class KeyValueList<K, V>
{
	private java.util.ArrayList<KeyValuePair<K, V>> values = null;

	public void add(K key, V val) {
		if(values == null) {
			values = new java.util.ArrayList<KeyValuePair<K, V>>();
		}
		KeyValuePair<K, V> kvp = new KeyValuePair<K, V>();
		kvp.key = key;
		kvp.value = val;
		values.add(kvp);
	}

	public void add(KeyValuePair<K, V> pair) {
		if(values == null) {
			values = new java.util.ArrayList<KeyValuePair<K, V>>();
		}
		values.add(pair);
	}

	public Iterator<KeyValuePair<K, V>> iterate() {
		Iterator<KeyValuePair<K, V>> v = cape.Vector.iterate(values);
		return(v);
	}

	public java.util.ArrayList<KeyValuePair<K, V>> asVector() {
		return(values);
	}

	public KeyValueList<K, V> dup() {
		KeyValueList<K, V> v = new KeyValueList<K, V>();
		Iterator<KeyValuePair<K, V>> it = iterate();
		while(true) {
			KeyValuePair<K, V> kvp = it.next();
			if(kvp == null) {
				break;
			}
			v.add(kvp.key, kvp.value);
		}
		return(v);
	}

	public K getKey(int index) {
		if(values == null) {
			return((K)(null));
		}
		KeyValuePair<K, V> kvp = cape.Vector.get(values, index);
		if(kvp == null) {
			return((K)(null));
		}
		return(kvp.key);
	}

	public V getValue(int index) {
		if(values == null) {
			return((V)(null));
		}
		KeyValuePair<K, V> kvp = cape.Vector.get(values, index);
		if(kvp == null) {
			return((V)(null));
		}
		return(kvp.value);
	}

	public int count() {
		if(values == null) {
			return(0);
		}
		return(cape.Vector.getSize(values));
	}

	public void clear() {
		values = null;
	}
}
