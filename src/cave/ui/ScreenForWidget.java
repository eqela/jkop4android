
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

package cave.ui;

public class ScreenForWidget extends android.app.Activity implements cave.ScreenWithContext, cave.CompatibleAndroidActivity
{
	static public ScreenForWidget objectAsCaveUiScreenForWidget(java.lang.Object o) {
		if(o instanceof ScreenForWidget) {
			return((ScreenForWidget)o);
		}
		return(null);
	}

	public static ScreenForWidget findScreenForWidget(android.view.View widget) {
		return(objectAsCaveUiScreenForWidget((java.lang.Object)cave.ui.Widget.findScreen(widget)));
	}

	protected cave.GuiApplicationContext context = null;
	private android.view.View myWidget = null;
	private samx.function.Procedure1<java.lang.Boolean> permissionRequestListener = null;
	private samx.function.Procedure3<java.lang.Integer,java.lang.Integer,android.content.Intent> androidActivityResultHandler = null;

	public void onCreate(android.os.Bundle savedInstance) {
		super.onCreate(savedInstance);
		android.app.ActionBar ab = getActionBar();
		if(ab != null) {
			ab.hide();
		}
		onPrepareScreen();
		initialize();
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

	public void onBackPressed() {
		onBackKeyPressEvent();
	}

	public void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
		if(androidActivityResultHandler == null) {
			return;
		}
		androidActivityResultHandler.execute(requestCode, resultCode, data);
		androidActivityResultHandler = null;
	}

	public boolean dispatchTouchEvent(android.view.MotionEvent event) {
		if(event.getAction() == (android.view.MotionEvent.ACTION_DOWN)) {
			android.view.View w = getCurrentFocus();;
			if((w instanceof TextInputWidget) || (w instanceof TextAreaWidget)) {
				android.graphics.Rect rect = new android.graphics.Rect();
				w.getGlobalVisibleRect(rect);
				if (!rect.contains((int)event.getRawX(), (int)event.getRawY())) {
					w.clearFocus();
					android.view.inputmethod.InputMethodManager im = (android.view.inputmethod.InputMethodManager)getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
					im.hideSoftInputFromWindow(w.getWindowToken(), 0);
				}
			}
		}
		return(super.dispatchTouchEvent(event));
	}

	private cave.KeyEvent keyEvent = null;

	public void onBackKeyPressEvent() {
		if(keyEvent == null) {
			keyEvent = new cave.KeyEvent();
		}
		keyEvent.clear();
		keyEvent.setAction(cave.KeyEvent.ACTION_DOWN);
		keyEvent.setKeyCode(cave.KeyEvent.KEY_BACK);
		deliverKeyEventToWidget(keyEvent, getWidget());
		if(keyEvent.isConsumed == false) {
			super.onBackPressed();
		}
	}

	public void deliverKeyEventToWidget(cave.KeyEvent event, android.view.View widget) {
		if(widget == null) {
			return;
		}
		java.util.ArrayList<android.view.View> array = cave.ui.Widget.getChildren(widget);
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				android.view.View child = array.get(n);
				if(child != null) {
					deliverKeyEventToWidget(event, child);
					if(event.isConsumed) {
						return;
					}
				}
			}
		}
		cave.KeyListener kl = (cave.KeyListener)((widget instanceof cave.KeyListener) ? widget : null);
		if(kl != null) {
			kl.onKeyEvent(event);
			if(event.isConsumed) {
				return;
			}
		}
	}

	public void unlockOrientation() {
		setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
	}

	public void lockToLandscapeOrientation() {
		setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	public void lockToPortraitOrientation() {
		setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	public void setContext(cave.GuiApplicationContext context) {
		this.context = context;
	}

	public cave.GuiApplicationContext getContext() {
		return(context);
	}

	private cave.GuiApplicationContext createContext() {
		cave.GuiApplicationContext v = null;
		v = (cave.GuiApplicationContext)cave.GuiApplicationContextForAndroid.forActivityContext((android.content.Context)this);
		return(v);
	}

	public void onPrepareScreen() {
	}

	public void initialize() {
		if(context == null) {
			context = createContext();
		}
	}

	public void cleanup() {
	}

	public android.view.View getWidget() {
		return(myWidget);
	}

	public void setWidget(CustomContainerWidget widget) {
		if(myWidget != null) {
			System.out.println("[cave.ui.ScreenForWidget.setWidget] (ScreenForWidget.sling:376:2): Nultiple calls to setWidget()");
			return;
		}
		if(widget == null) {
			return;
		}
		myWidget = (android.view.View)widget;
		widget.setAllowResize(false);
		setContentView(widget);
		widget.tryInitializeWidget();
		cave.ui.Widget.onWidgetAddedToParent((android.view.View)widget);
		cave.ui.Widget.notifyOnAddedToScreen((android.view.View)widget, this);
	}

	public samx.function.Procedure3<java.lang.Integer,java.lang.Integer,android.content.Intent> getAndroidActivityResultHandler() {
		return(androidActivityResultHandler);
	}

	public ScreenForWidget setAndroidActivityResultHandler(samx.function.Procedure3<java.lang.Integer,java.lang.Integer,android.content.Intent> v) {
		androidActivityResultHandler = v;
		return(this);
	}
}
