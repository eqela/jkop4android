
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

public class ContentCache
{
	private static class CacheEntry
	{
		private java.lang.Object data = null;
		private int ttl = 0;
		private int timestamp = 0;

		public java.lang.Object getData() {
			return(data);
		}

		public CacheEntry setData(java.lang.Object v) {
			data = v;
			return(this);
		}

		public int getTtl() {
			return(ttl);
		}

		public CacheEntry setTtl(int v) {
			ttl = v;
			return(this);
		}

		public int getTimestamp() {
			return(timestamp);
		}

		public CacheEntry setTimestamp(int v) {
			timestamp = v;
			return(this);
		}
	}

	private java.util.HashMap<java.lang.String,CacheEntry> cache = null;
	private int cacheTtl = 3600;

	public void onMaintenance() {
		if(cache == null) {
			return;
		}
		long now = cape.SystemClock.asSeconds();
		java.util.ArrayList<java.lang.String> keys = cape.Map.getKeys(cache);
		if(keys != null) {
			int n = 0;
			int m = keys.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.String key = keys.get(n);
				if(key != null) {
					CacheEntry tce = cape.Map.get(cache, key);
					CacheEntry ce = null;
					if(tce instanceof CacheEntry) {
						ce = (CacheEntry)tce;
					}
					if(ce == null) {
						cape.Map.remove(cache, key);
					}
					else {
						long diff = now - ce.getTimestamp();
						if(diff >= ce.getTtl()) {
							cape.Map.remove(cache, key);
						}
					}
				}
			}
		}
	}

	public void clear() {
		cache = null;
	}

	public void remove(java.lang.String cacheid) {
		cape.Map.remove(cache, cacheid);
	}

	public void set(java.lang.String cacheid, java.lang.Object content, int ttl) {
		if(android.text.TextUtils.equals(cacheid, null)) {
			return;
		}
		CacheEntry ee = new CacheEntry();
		ee.setData(content);
		if(ttl >= 0) {
			ee.setTtl(ttl);
		}
		else {
			ee.setTtl(cacheTtl);
		}
		if(ee.getTtl() < 1) {
			return;
		}
		ee.setTimestamp((int)cape.SystemClock.asSeconds());
		if(cache == null) {
			cache = new java.util.HashMap<java.lang.String,CacheEntry>();
		}
		cache.put(cacheid, ee);
	}

	public void set(java.lang.String cacheid, java.lang.Object content) {
		set(cacheid, content, -1);
	}

	public java.lang.Object get(java.lang.String cacheid) {
		if(cache == null) {
			return(null);
		}
		CacheEntry tee = cape.Map.getValue(cache, cacheid);
		CacheEntry ee = null;
		if(tee instanceof CacheEntry) {
			ee = (CacheEntry)tee;
		}
		if(ee != null) {
			long diff = cape.SystemClock.asSeconds() - ee.getTimestamp();
			if(diff >= ee.getTtl()) {
				cape.Map.remove(cache, cacheid);
				ee = null;
			}
		}
		if(ee != null) {
			return(ee.getData());
		}
		return(null);
	}

	public int getCacheTtl() {
		return(cacheTtl);
	}

	public ContentCache setCacheTtl(int v) {
		cacheTtl = v;
		return(this);
	}
}
