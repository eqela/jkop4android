
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

public class GeoCoderForAndroid implements GeoCoder
{
	private android.content.Context context = null;

	public boolean queryAddress(double latitude, double longitude, GeoCoderAddressListener listener) {
		if(!android.location.Geocoder.isPresent()) {
			return(false);
		}
		android.location.Geocoder geocoder = new android.location.Geocoder(context);
		android.location.Location location = new android.location.Location(android.location.LocationManager.GPS_PROVIDER);
		location.setLatitude(latitude);
		location.setLongitude(longitude);
		new GetAddressTask(listener).execute(location);
		return(true);
	}

	public boolean queryLocation(java.lang.String address, GeoCoderLocationListener listener) {
		if(cape.String.isEmpty(address)) {
			return(false);
		}
		if(!android.location.Geocoder.isPresent()) {
			return(false);
		}
		new GetLocationTask(listener).execute(address);
		return(true);
	}

	private class GetLocationTask extends android.os.AsyncTask<java.lang.String, Void, android.location.Address>
	{
		public GetLocationTask(GeoCoderLocationListener listener) {
			this.listener = listener;
		}
		private GeoCoderLocationListener listener;
		private String error = null;
		protected android.location.Address doInBackground(java.lang.String... params) {
			java.lang.String locationName = params[0];
			java.util.List<android.location.Address> addresses = null;
			android.location.Geocoder geocoder = new android.location.Geocoder(context);
			try {
				addresses = geocoder.getFromLocationName(locationName, 1);
			}
			catch (java.lang.Exception e) {
				error = e.getMessage();
				return(null);
			}
			if (addresses != null && addresses.size() > 0) {
				return(addresses.get(0));
			}
			return(null);
		}
		protected void onPostExecute(android.location.Address address) {
			if(error != null) {
				if(listener != null) {
					listener.onQueryLocationErrorReceived(cape.Error.forMessage(error));
				}
				return;
			}
			if(address == null) {
				if(listener != null) {
					String err_msg = "Can't Find Location";
					listener.onQueryLocationErrorReceived(cape.Error.forMessage(err_msg));
				}
			}
			else {
				GeoLocation l = new GeoLocation();
				l.setLatitude(address.getLatitude());
				l.setLongitude(address.getLongitude());
				if(listener != null) {
					listener.onQueryLocationCompleted(l);
				}
			}
		}
	}
	private class GetAddressTask extends android.os.AsyncTask<android.location.Location, Void, android.location.Address>
	{
		public GetAddressTask(GeoCoderAddressListener listener) {
			this.listener = listener;
		}
		private GeoCoderAddressListener listener;
		android.location.Location loc;
		private String error = null;
		protected android.location.Address doInBackground(android.location.Location... params) {
			loc = params[0];
			java.util.List<android.location.Address> addresses = null;
			android.location.Geocoder geocoder = new android.location.Geocoder(context);
			try {
				addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
			}
			catch (java.lang.Exception e) {
				error = e.getMessage();
				return(null);
			}
			if(addresses != null && addresses.size() > 0) {
				return(addresses.get(0));
			}
			return(null);
		}
		protected void onPostExecute(android.location.Address address) {
			if(error != null) {
				if(listener != null) {
					listener.onQueryAddressErrorReceived(cape.Error.forMessage(error));
				}
				return;
			}
			if(address == null) {
				if(listener != null) {
					String err_msg = "Can't Find Address";
					listener.onQueryAddressErrorReceived(cape.Error.forMessage(err_msg));
				}
			}
			else {
				PhysicalAddress a = new PhysicalAddress();
				java.lang.StringBuilder sb = new java.lang.StringBuilder();
				for(int i = 0; true; i++) {
					java.lang.String str = address.getAddressLine(i);
					if(str == null) {
						break;
					}
					sb.append(str);
					sb.append(" ");
				}
				if(loc != null) {
					a.setLatitude(loc.getLatitude());
					a.setLongitude(loc.getLongitude());
				}
				a.setCompleteAddress(sb.toString());
				a.setCountry(address.getCountryName());
				a.setCountryCode(address.getCountryCode());
				a.setAdministrativeArea(address.getAdminArea());
				a.setSubAdministrativeArea(address.getSubAdminArea());
				a.setLocality(address.getLocality());
				a.setSubLocality(address.getSubLocality());
				a.setStreetAddress(address.getThoroughfare());
				a.setStreetAddressDetail(address.getSubThoroughfare());
				a.setPostalCode(address.getPostalCode());
				if(listener != null) {
					listener.onQueryAddressCompleted(a);
				}
			}
		}
	}

	public android.content.Context getContext() {
		return(context);
	}

	public GeoCoderForAndroid setContext(android.content.Context v) {
		context = v;
		return(this);
	}
}
