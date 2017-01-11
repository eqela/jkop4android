
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

package cave;

public class AndroidActivityAdapter extends android.app.Activity implements CompatibleAndroidActivity
{
	private samx.function.Procedure1<java.lang.Boolean> permissionRequestListener = null;
	protected GuiApplicationContextForAndroid context = null;

	public void initializeAndroidActivity() {
	}

	public void onCreate(android.os.Bundle savedInstance) {
		super.onCreate(savedInstance);
		context = cave.GuiApplicationContextForAndroid.forActivityContext((android.content.Context)this);
		initializeAndroidActivity();
	}

	public void setPermissionRequestListener(samx.function.Procedure1<java.lang.Boolean> listener) {
		permissionRequestListener = listener;
	}

	public void onRequestPermissionsResult(int requestCode, java.lang.String[] permissions, int[] grantResults) {
		samx.function.Procedure1<java.lang.Boolean> listener = permissionRequestListener;
		if(listener == null) {
			return;
		}
		permissionRequestListener = null;
		boolean status = true;
		int granted = 0;
		granted = android.content.pm.PackageManager.PERMISSION_GRANTED;
		if(grantResults != null) {
			int n = 0;
			int m = grantResults.length;
			for(n = 0 ; n < m ; n++) {
				int result = grantResults[n];
				if(result != granted) {
					status = false;
					break;
				}
			}
		}
		listener.execute(status);
	}
}
