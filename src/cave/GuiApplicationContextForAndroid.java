
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

public class GuiApplicationContextForAndroid implements GuiApplicationContext, cape.AndroidApplicationContext
{
	public static GuiApplicationContextForAndroid forActivityContext(android.content.Context ctx) {
		GuiApplicationContextForAndroid v = new GuiApplicationContextForAndroid();
		v.setActivityContext(ctx);
		v.printDeviceInfo();
		return(v);
	}

	private android.content.Context activityContext = null;

	public android.content.Context getAndroidActivityContext() {
		return(activityContext);
	}

	public void logError(java.lang.String message) {
		System.out.println("[ERROR] " + message);
	}

	public void logWarning(java.lang.String message) {
		System.out.println("[WARNING] " + message);
	}

	public void logInfo(java.lang.String message) {
		System.out.println("[INFO] " + message);
	}

	public void logDebug(java.lang.String message) {
		System.out.println("[DEBUG] " + message);
	}

	public cape.File getApplicationDataDirectory() {
		java.lang.String path = null;
		android.content.Context ctx = activityContext;
		android.content.pm.PackageManager pkm = ctx.getPackageManager();
		java.lang.String pkn = ctx.getPackageName();
		try {
			android.content.pm.PackageInfo pki = pkm.getPackageInfo(pkn, 0);
			path = pki.applicationInfo.dataDir;
		}
		catch(android.content.pm.PackageManager.NameNotFoundException e) {
			path = null;
		}
		if(android.text.TextUtils.equals(path, null)) {
			return((cape.File)new cape.FileInvalid());
		}
		return(cape.FileInstance.forPath(path));
	}

	public void showConfirmDialog(java.lang.String title, java.lang.String message, samx.function.Procedure0 okcallback, samx.function.Procedure0 cancelcallback) {
		android.content.Context activity = activityContext;
		if(activity == null) {
			return;
		}
		final samx.function.Procedure0 okcb = okcallback;
		final samx.function.Procedure0 clcb = cancelcallback;
		android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(activity);
		builder1.setTitle(title);
		builder1.setMessage(message);
		builder1.setPositiveButton("YES", new android.content.DialogInterface.OnClickListener() {
			public void onClick(android.content.DialogInterface dialog, int id) {
				dialog.cancel();
				if(okcb != null) {
					okcb.execute();
				}
			}
		});
		builder1.setNegativeButton("NO", new android.content.DialogInterface.OnClickListener() {
			public void onClick(android.content.DialogInterface dialog, int id) {
				dialog.cancel();
				if(clcb != null) {
					clcb.execute();
				}
			}
		});
		builder1.create().show();
	}

	public void showMessageDialog(java.lang.String title, java.lang.String message) {
		showMessageDialog(title, message, null);
	}

	public void showMessageDialog(java.lang.String title, java.lang.String message, samx.function.Procedure0 callback) {
		android.content.Context activity = activityContext;
		if(activity == null) {
			return;
		}
		final samx.function.Procedure0 cb = callback;
		android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(activity);
		builder1.setTitle(title);
		builder1.setMessage(message);
		builder1.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
			public void onClick(android.content.DialogInterface dialog, int id) {
				dialog.cancel();
				if(cb != null) {
					cb.execute();
				}
			}
		});
		builder1.create().show();
	}

	public void showErrorDialog(java.lang.String message) {
		showErrorDialog(message, null);
	}

	public void showErrorDialog(java.lang.String message, samx.function.Procedure0 callback) {
		android.content.Context activity = activityContext;
		if(activity == null) {
			return;
		}
		final samx.function.Procedure0 cb = callback;
		android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(activity);
		builder1.setTitle("Error");
		builder1.setMessage(message);
		builder1.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
			public void onClick(android.content.DialogInterface dialog, int id) {
				dialog.cancel();
				if(cb != null) {
					cb.execute();
				}
			}
		});
		builder1.create().show();
	}

	public boolean hasPermissions(java.lang.String[] permissions) {
		if(activityContext == null) {
			return(false);
		}
		if(permissions != null) {
			int n = 0;
			int m = permissions.length;
			for(n = 0 ; n < m ; n++) {
				java.lang.String permission = permissions[n];
				if(permission != null) {
					int v;
					try {
						v = activityContext.checkSelfPermission(permission);
					}
					catch(java.lang.NoSuchMethodError e) {
						logDebug("checkSelfPermission method does not exist.");
						return(true);
					}
					catch(Exception e) {
						logError("Permission checking was not succcessful:");
						logError(e.toString());
						return(false);
					}
					if(v != android.content.pm.PackageManager.PERMISSION_GRANTED) {
						logError("Permission is NOT granted: " + permission);
						return(false);
					}
					logDebug("Permission IS granted: " + permission);
				}
			}
		}
		return(true);
	}

	public void requestPermissions(java.lang.String[] permissions, samx.function.Procedure1<java.lang.Boolean> callback) {
		if(hasPermissions(permissions)) {
			if(callback != null) {
				callback.execute(true);
			}
			return;
		}
		CompatibleAndroidActivity activity = (CompatibleAndroidActivity)((activityContext instanceof CompatibleAndroidActivity) ? activityContext : null);
		if(activity == null) {
			if(callback != null) {
				callback.execute(true);
			}
			return;
		}
		activity.setPermissionRequestListener(callback);
		android.app.Activity aa = (android.app.Activity)((activity instanceof android.app.Activity) ? activity : null);
		boolean r = false;
		try {
			aa.requestPermissions(permissions, 0);
			return;
		}
		catch(java.lang.NoSuchMethodError e) {
			logDebug("requestPermissions method does not exist.");
			r = true;
		}
		catch(Exception e) {
			logError("Permission request was not succcessful:");
			logError(e.toString());
		}
		if(callback != null) {
			callback.execute(r);
		}
	}

	public void switchActivity(java.lang.String className) {
		if(cape.String.isEmpty(className)) {
			return;
		}
		android.content.Intent intent = null;
		try {
			intent = new android.content.Intent(activityContext, java.lang.Class.forName(className));
		}
		catch(java.lang.Exception e) {
			e.printStackTrace();
			return;
		}
		activityContext.startActivity(intent);
	}

	public Image getResourceImage(java.lang.String id) {
		return((Image)cave.ImageForAndroid.forResource(id, activityContext));
	}

	public Image getImageForBuffer(byte[] buffer, java.lang.String mimeType) {
		return((Image)cave.ImageForAndroid.forBuffer(buffer));
	}

	public int getScreenTopMargin() {
		return(0);
	}

	public int getScreenWidth() {
		android.app.Activity activity = (android.app.Activity)((activityContext instanceof android.app.Activity) ? activityContext : null);
		if(activity == null) {
			return(0);
		}
		android.view.WindowManager wm = (android.view.WindowManager)activity.getWindowManager();
		if(wm == null) {
			return(0);
		}
		android.view.Display display = (android.view.Display)wm.getDefaultDisplay();
		if(display == null) {
			return(0);
		}
		android.graphics.Point pt = new android.graphics.Point();
		display.getSize(pt);
		return(pt.x);
	}

	public int getScreenHeight() {
		android.app.Activity activity = (android.app.Activity)((activityContext instanceof android.app.Activity) ? activityContext : null);
		if(activity == null) {
			return(0);
		}
		android.view.WindowManager wm = (android.view.WindowManager)activity.getWindowManager();
		if(wm == null) {
			return(0);
		}
		android.view.Display display = (android.view.Display)wm.getDefaultDisplay();
		if(display == null) {
			return(0);
		}
		android.graphics.Point pt = new android.graphics.Point();
		display.getSize(pt);
		return(pt.y);
	}

	public int getScreenDensity() {
		android.app.Activity activity = (android.app.Activity)((activityContext instanceof android.app.Activity) ? activityContext : null);
		if(activity == null) {
			return(0);
		}
		android.view.WindowManager wm = (android.view.WindowManager)activity.getWindowManager();
		if(wm == null) {
			return(0);
		}
		android.view.Display display = (android.view.Display)wm.getDefaultDisplay();
		if(display == null) {
			return(0);
		}
		android.util.DisplayMetrics metrics = new android.util.DisplayMetrics();
		display.getMetrics(metrics);
		return((int)metrics.xdpi);
	}

	public int getHeightValue(java.lang.String spec) {
		return(cave.Length.asPoints(spec, getScreenDensity()));
	}

	public int getWidthValue(java.lang.String spec) {
		return(cave.Length.asPoints(spec, getScreenDensity()));
	}

	private static class TimerWrapper implements java.lang.Runnable
	{
		private samx.function.Procedure0 callback = null;
		private long timeout = (long)0;

		public void run() {
			callback.execute();
		}

		public void execute() {
			android.os.Handler h = new android.os.Handler();
			h.postDelayed(this, timeout);
		}

		public samx.function.Procedure0 getCallback() {
			return(callback);
		}

		public TimerWrapper setCallback(samx.function.Procedure0 v) {
			callback = v;
			return(this);
		}

		public long getTimeout() {
			return(timeout);
		}

		public TimerWrapper setTimeout(long v) {
			timeout = v;
			return(this);
		}
	}

	public void startTimer(long timeout, samx.function.Procedure0 callback) {
		TimerWrapper w = new TimerWrapper();
		w.setCallback(callback);
		w.setTimeout(timeout);
		w.execute();
	}

	public void printDeviceInfo() {
		logDebug(((((("GuiApplicationContextForAndroid initialized: " + cape.String.forInteger(getScreenWidth())) + "x") + cape.String.forInteger(getScreenHeight())) + " @ ") + cape.String.forInteger(getScreenDensity())) + "DPI");
	}

	public android.content.Context getActivityContext() {
		return(activityContext);
	}

	public GuiApplicationContextForAndroid setActivityContext(android.content.Context v) {
		activityContext = v;
		return(this);
	}
}
