
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

public class DNSCache
{
	private static class DNSCacheEntry
	{
		private java.lang.String ip = null;
		private int timestamp = 0;

		public static DNSCacheEntry create(java.lang.String ip) {
			DNSCacheEntry v = new DNSCacheEntry();
			v.setIp(ip);
			v.setTimestamp((int)cape.SystemClock.asSeconds());
			return(v);
		}

		public java.lang.String getIp() {
			return(ip);
		}

		public DNSCacheEntry setIp(java.lang.String v) {
			ip = v;
			return(this);
		}

		public int getTimestamp() {
			return(timestamp);
		}

		public DNSCacheEntry setTimestamp(int v) {
			timestamp = v;
			return(this);
		}
	}

	private static class DNSCacheImpl
	{
		private cape.DynamicMap entries = null;
		private cape.Mutex mutex = null;

		public DNSCacheImpl() {
			entries = new cape.DynamicMap();
			mutex = cape.Mutex.create();
		}

		private void add(java.lang.String hostname, java.lang.String ip) {
			if(mutex != null) {
				mutex.lockMutex();
			}
			entries.set(hostname, (java.lang.Object)sympathy.DNSCache.DNSCacheEntry.create(ip));
			if(mutex != null) {
				mutex.unlockMutex();
			}
		}

		static public DNSCacheEntry objectAsSympathyDNSCacheDNSCacheEntry(java.lang.Object o) {
			if(o instanceof DNSCacheEntry) {
				return((DNSCacheEntry)o);
			}
			return(null);
		}

		private java.lang.String getCachedEntry(java.lang.String hostname) {
			DNSCacheEntry v = null;
			if(mutex != null) {
				mutex.lockMutex();
			}
			v = objectAsSympathyDNSCacheDNSCacheEntry((java.lang.Object)entries.get(hostname));
			if(mutex != null) {
				mutex.unlockMutex();
			}
			if(v != null) {
				if((cape.SystemClock.asSeconds() - v.getTimestamp()) > (60 * 60)) {
					if(mutex != null) {
						mutex.lockMutex();
					}
					entries.remove(hostname);
					if(mutex != null) {
						mutex.unlockMutex();
					}
					v = null;
				}
			}
			if(v != null) {
				return(v.getIp());
			}
			return(null);
		}

		public java.lang.String resolve(java.lang.String hostname) {
			java.lang.String v = getCachedEntry(hostname);
			if(!(android.text.TextUtils.equals(v, null))) {
				return(v);
			}
			DNSResolver dr = sympathy.DNSResolver.create();
			if(dr == null) {
				return(null);
			}
			v = dr.getIPAddress(hostname, null);
			if(!(android.text.TextUtils.equals(v, null))) {
				add(hostname, v);
			}
			return(v);
		}
	}

	private static DNSCacheImpl cc = null;

	public static java.lang.String resolve(java.lang.String hostname) {
		if(cc == null) {
			cc = new DNSCacheImpl();
		}
		return(cc.resolve(hostname));
	}
}
