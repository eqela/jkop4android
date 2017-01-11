
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

public class QueryString
{
	public static java.util.HashMap<java.lang.String,java.lang.String> parse(java.lang.String queryString) {
		java.util.HashMap<java.lang.String,java.lang.String> v = new java.util.HashMap<java.lang.String,java.lang.String>();
		java.util.ArrayList<java.lang.String> array = cape.String.split(queryString, '&');
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.String qs = array.get(n);
				if(qs != null) {
					if(cape.String.isEmpty(qs)) {
						continue;
					}
					if(cape.String.indexOf(qs, '=') < 0) {
						cape.Map.set(v, qs, null);
						continue;
					}
					java.util.ArrayList<java.lang.String> qsps = cape.String.split(qs, '=', 2);
					java.lang.String key = qsps.get(0);
					java.lang.String val = qsps.get(1);
					if(android.text.TextUtils.equals(val, null)) {
						val = "";
					}
					if(cape.String.isEmpty(key) == false) {
						cape.Map.set(v, key, capex.URLDecoder.decode(val));
					}
				}
			}
		}
		return(v);
	}
}
