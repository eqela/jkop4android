
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

public class PhotoSelectorWidget
{
	private cave.GuiApplicationContext context = null;

	public PhotoSelectorWidget(cave.GuiApplicationContext context) {
		this.context = context;
	}

	public void openPhotoDialog(android.view.View widget, samx.function.Procedure3<cave.Image,java.lang.String,cape.Error> callback) {
		final samx.function.Procedure3<cave.Image,java.lang.String,cape.Error> cb = callback;
		ScreenForWidget screen = cave.ui.ScreenForWidget.findScreenForWidget(widget);
		if(screen == null) {
			if(cb != null) {
				cb.execute(null, null, cape.Error.forCode("no_screen_found"));
			}
			return;
		}
		android.content.Context actx = ((cave.GuiApplicationContextForAndroid)((context instanceof cave.GuiApplicationContextForAndroid) ? context : null)).getAndroidActivityContext();
		final android.content.Context ctx = actx;
		screen.setAndroidActivityResultHandler(new samx.function.Procedure3<java.lang.Integer, java.lang.Integer, android.content.Intent>() {
			public void execute(java.lang.Integer requestCode, java.lang.Integer resultCode, android.content.Intent data) {
				if(requestCode != 0) {
					if(cb != null) {
						cb.execute(null, null, cape.Error.forCode("invalid_request_code"));
					}
					return;
				}
				if(resultCode != (android.app.Activity.RESULT_OK)) {
					if(cb != null) {
						cb.execute(null, null, null);
					}
					return;
				}
				cave.Image img = null;
				java.lang.String imgName = null;
				android.net.Uri uri = data.getData();
				android.database.Cursor returnCursor = ctx.getContentResolver().query(uri, null, null, null, null);
				int nameIndex = returnCursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME);
				returnCursor.moveToFirst();
				imgName = returnCursor.getString(nameIndex);
				try {
					java.io.InputStream stream = ctx.getContentResolver().openInputStream(data.getData());
					android.graphics.Bitmap bitmap = android.graphics.BitmapFactory.decodeStream(stream);
					stream.close();
					img = cave.ImageForAndroid.forAndroidBitmap(bitmap);
				} catch (java.io.FileNotFoundException e) {
					e.printStackTrace();
				} catch (java.io.IOException e) {
					e.printStackTrace();
				}
				if(cb != null) {
					if(img == null) {
						cb.execute(null, null, cape.Error.forCode("no_photo_data"));
					}
					else {
						cb.execute(img, imgName, null);
					}
				}
			}
		});
		android.content.Context tact = ((cave.GuiApplicationContextForAndroid)((context instanceof cave.GuiApplicationContextForAndroid) ? context : null)).getAndroidActivityContext();
		android.app.Activity act = null;
		if(tact instanceof android.app.Activity) {
			act = (android.app.Activity)tact;
		}
		final android.app.Activity activity = act;
		android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		try {
			activity.startActivityForResult(android.content.Intent.createChooser(intent, "Select a photo"), 0);
		}
		catch(android.content.ActivityNotFoundException ex) {
		}
	}
}
