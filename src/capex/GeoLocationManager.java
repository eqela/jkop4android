
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

abstract public class GeoLocationManager
{
	public static GeoLocationManager forApplicationContext(cape.ApplicationContext context) {
		cape.AndroidApplicationContext ctx = (cape.AndroidApplicationContext)((context instanceof cape.AndroidApplicationContext) ? context : null);
		if(ctx == null) {
			return(null);
		}
		return((GeoLocationManager)capex.GeoLocationManagerForAndroid.getInstance(ctx));
	}

	private java.util.ArrayList<samx.function.Procedure1<GeoLocation>> listeners = null;

	public GeoLocationManager() {
		listeners = new java.util.ArrayList<samx.function.Procedure1<GeoLocation>>();
	}

	public void addListener(samx.function.Procedure1<GeoLocation> l) {
		listeners.add(l);
	}

	public void removeListener(samx.function.Procedure1<GeoLocation> l) {
		cape.Vector.removeValue(listeners, l);
	}

	public void removeAllListeners() {
		cape.Vector.clear(listeners);
	}

	public void notifyListeners(GeoLocation location) {
		if(listeners != null) {
			int n = 0;
			int m = listeners.size();
			for(n = 0 ; n < m ; n++) {
				samx.function.Procedure1<GeoLocation> listener = listeners.get(n);
				if(listener != null) {
					if(listener != null) {
						listener.execute(location);
					}
				}
			}
		}
	}

	public abstract void startLocationUpdates(samx.function.Procedure1<java.lang.Boolean> callback);
	public abstract void stopLocationUpdates();
}
