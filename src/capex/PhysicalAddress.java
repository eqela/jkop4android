
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

public class PhysicalAddress implements cape.StringObject
{
	public static PhysicalAddress forString(java.lang.String str) {
		PhysicalAddress v = new PhysicalAddress();
		v.fromString(str);
		return(v);
	}

	private double latitude = 0.00;
	private double longitude = 0.00;
	private java.lang.String completeAddress = null;
	private java.lang.String country = null;
	private java.lang.String countryCode = null;
	private java.lang.String administrativeArea = null;
	private java.lang.String subAdministrativeArea = null;
	private java.lang.String locality = null;
	private java.lang.String subLocality = null;
	private java.lang.String streetAddress = null;
	private java.lang.String streetAddressDetail = null;
	private java.lang.String postalCode = null;

	static public cape.DynamicMap objectAsCapeDynamicMap(java.lang.Object o) {
		if(o instanceof cape.DynamicMap) {
			return((cape.DynamicMap)o);
		}
		return(null);
	}

	public void fromString(java.lang.String str) {
		cape.DynamicMap data = null;
		if(!(android.text.TextUtils.equals(str, null))) {
			data = objectAsCapeDynamicMap((java.lang.Object)cape.JSONParser.parse(str));
		}
		if(data == null) {
			data = new cape.DynamicMap();
		}
		latitude = data.getDouble("latitude");
		longitude = data.getDouble("longitude");
		completeAddress = data.getString("completeAddress");
		country = data.getString("country");
		countryCode = data.getString("countryCode");
		administrativeArea = data.getString("administrativeArea");
		subAdministrativeArea = data.getString("subAdministrativeArea");
		locality = data.getString("locality");
		subLocality = data.getString("subLocality");
		streetAddress = data.getString("streetAddress");
		streetAddressDetail = data.getString("streetAddressDetail");
		postalCode = data.getString("postalCode");
	}

	public java.lang.String toString() {
		cape.DynamicMap v = new cape.DynamicMap();
		v.set("latitude", latitude);
		v.set("longitude", longitude);
		v.set("completeAddress", (java.lang.Object)completeAddress);
		v.set("country", (java.lang.Object)country);
		v.set("countryCode", (java.lang.Object)countryCode);
		v.set("administrativeArea", (java.lang.Object)administrativeArea);
		v.set("subAdministrativeArea", (java.lang.Object)subAdministrativeArea);
		v.set("locality", (java.lang.Object)locality);
		v.set("subLocality", (java.lang.Object)subLocality);
		v.set("streetAddress", (java.lang.Object)streetAddress);
		v.set("streetAddressDetail", (java.lang.Object)streetAddressDetail);
		v.set("postalCode", (java.lang.Object)postalCode);
		return(cape.JSONEncoder.encode((java.lang.Object)v, false));
	}

	public double getLatitude() {
		return(latitude);
	}

	public PhysicalAddress setLatitude(double v) {
		latitude = v;
		return(this);
	}

	public double getLongitude() {
		return(longitude);
	}

	public PhysicalAddress setLongitude(double v) {
		longitude = v;
		return(this);
	}

	public java.lang.String getCompleteAddress() {
		return(completeAddress);
	}

	public PhysicalAddress setCompleteAddress(java.lang.String v) {
		completeAddress = v;
		return(this);
	}

	public java.lang.String getCountry() {
		return(country);
	}

	public PhysicalAddress setCountry(java.lang.String v) {
		country = v;
		return(this);
	}

	public java.lang.String getCountryCode() {
		return(countryCode);
	}

	public PhysicalAddress setCountryCode(java.lang.String v) {
		countryCode = v;
		return(this);
	}

	public java.lang.String getAdministrativeArea() {
		return(administrativeArea);
	}

	public PhysicalAddress setAdministrativeArea(java.lang.String v) {
		administrativeArea = v;
		return(this);
	}

	public java.lang.String getSubAdministrativeArea() {
		return(subAdministrativeArea);
	}

	public PhysicalAddress setSubAdministrativeArea(java.lang.String v) {
		subAdministrativeArea = v;
		return(this);
	}

	public java.lang.String getLocality() {
		return(locality);
	}

	public PhysicalAddress setLocality(java.lang.String v) {
		locality = v;
		return(this);
	}

	public java.lang.String getSubLocality() {
		return(subLocality);
	}

	public PhysicalAddress setSubLocality(java.lang.String v) {
		subLocality = v;
		return(this);
	}

	public java.lang.String getStreetAddress() {
		return(streetAddress);
	}

	public PhysicalAddress setStreetAddress(java.lang.String v) {
		streetAddress = v;
		return(this);
	}

	public java.lang.String getStreetAddressDetail() {
		return(streetAddressDetail);
	}

	public PhysicalAddress setStreetAddressDetail(java.lang.String v) {
		streetAddressDetail = v;
		return(this);
	}

	public java.lang.String getPostalCode() {
		return(postalCode);
	}

	public PhysicalAddress setPostalCode(java.lang.String v) {
		postalCode = v;
		return(this);
	}
}
