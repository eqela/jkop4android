
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

public class GeoLocationManagerForAndroid extends GeoLocationManager
{
	private static GeoLocationManagerForAndroid instance = null;

	public static GeoLocationManagerForAndroid getInstance(cape.AndroidApplicationContext ctx) {
		if(instance == null) {
			instance = new GeoLocationManagerForAndroid(ctx);
		}
		return(instance);
	}

	private android.location.LocationManager locationManager;
	private LocationCallBack lcb;

	private cape.AndroidApplicationContext context = null;

	private GeoLocationManagerForAndroid(cape.AndroidApplicationContext context) {
		super();
		this.context = context;
		android.content.Context acontext = context.getAndroidActivityContext();
		lcb = new LocationCallBack(this);
		locationManager = (android.location.LocationManager)acontext.getSystemService(acontext.LOCATION_SERVICE);
	}

	@Override
	public void startLocationUpdates(samx.function.Procedure1<java.lang.Boolean> callback) {
		java.lang.String[] perms = new java.lang.String[] {
			"android.permission.ACCESS_FINE_LOCATION",
			"android.permission.ACCESS_COARSE_LOCATION"
		};
		final samx.function.Procedure1<java.lang.Boolean> cb = callback;
		context.requestPermissions(perms, new samx.function.Procedure1<java.lang.Boolean>() {
			public void execute(java.lang.Boolean status) {
				if(status == false) {
					if(cb != null) {
						cb.execute(false);
					}
					return;
				}
				boolean v = true;
				boolean isGPSEnabled = false;
				boolean isNetworkEnabled = false;
				try {
					isGPSEnabled = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
					isNetworkEnabled = locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);
					if(isGPSEnabled) {
						locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 0, 0, lcb);
					}
					else if(isNetworkEnabled) {
						locationManager.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER, 0, 0, lcb);
					}
					else {
						v = false;
					}
				}
				catch (java.lang.Exception e) {
					System.out.println(e);
					v = false;
				}
				if(cb != null) {
					cb.execute(v);
				}
			}
		});
	}

	@Override
	public void stopLocationUpdates() {
		if(locationManager != null) {
			locationManager.removeUpdates(lcb);
		}
	}

	class LocationCallBack implements android.location.LocationListener {
		public LocationCallBack(GeoLocationManagerForAndroid self) {
			this.self = self;
		}
		private GeoLocationManagerForAndroid self;
		@Override
		public void onLocationChanged(android.location.Location location) {
			GeoLocation l = new GeoLocation();
			l.setLatitude(location.getLatitude());
			l.setLongitude(location.getLongitude());
			self.notifyListeners(l);
		}
		@Override
		public void onProviderDisabled(String provider) {
		}
		@Override
		public void onProviderEnabled(String provider) {
		}
		@Override
		public void onStatusChanged(String provider, int status, android.os.Bundle extras) {
		}
	}
}
