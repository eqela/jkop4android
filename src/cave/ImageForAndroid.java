
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

public class ImageForAndroid extends Image
{
	private static java.util.HashMap<java.lang.String,ImageForAndroid> resourceCache = null;

	public static ImageForAndroid forAndroidBitmap(android.graphics.Bitmap bitmap) {
		if(bitmap == null) {
			return(null);
		}
		ImageForAndroid v = new ImageForAndroid();
		v.bitmap = bitmap;
		return(v);
	}

	public static ImageForAndroid forSize(int width, int height) {
		ImageForAndroid v = new ImageForAndroid();
		try {
			v.bitmap = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.ARGB_8888);
		}
		catch(Exception e) {
			v.bitmap = null;
		}
		if(v.bitmap == null) {
			return(null);
		}
		return(v);
	}

	static public java.lang.String sanitizeResourceName(java.lang.String n) {
		if(android.text.TextUtils.equals(n, null)) {
			return(null);
		}
		cape.StringBuilder sb = new cape.StringBuilder();
		cape.CharacterIterator it = cape.String.iterate(n);
		char c = ' ';
		while((c = it.getNextChar()) > 0) {
			if((c >= 'A') && (c <= 'Z')) {
				sb.append((char)(('a' + c) - 'A'));
			}
			else if((c >= 'a') && (c <= 'z')) {
				sb.append(c);
			}
			else if((c >= '0') && (c <= '9')) {
				sb.append(c);
			}
			else {
				sb.append('_');
			}
		}
		return(sb.toString());
	}

	public static ImageForAndroid forResource(java.lang.String name, android.content.Context context) {
		if((android.text.TextUtils.equals(name, null)) || (context == null)) {
			return(null);
		}
		if(resourceCache != null) {
			ImageForAndroid v = cape.Map.getValue(resourceCache, name);
			if(v != null) {
				return(v);
			}
		}
		android.graphics.Bitmap bm = null;
		java.lang.String rid = (((java.lang.String)context.getPackageName()) + ":drawable/") + sanitizeResourceName(name);
		android.content.res.Resources res = context.getResources();
		if(res != null) {
			int aid = res.getIdentifier(rid, null, null);
			if(aid > 0) {
				android.graphics.drawable.Drawable d = res.getDrawable(aid);
				if(d != null && d instanceof android.graphics.drawable.BitmapDrawable) {
					bm =((android.graphics.drawable.BitmapDrawable)d).getBitmap();
				}
			}
		}
		if(bm == null) {
			return(null);
		}
		ImageForAndroid v = new ImageForAndroid();
		v.bitmap = bm;
		if(resourceCache == null) {
			resourceCache = new java.util.HashMap<java.lang.String,ImageForAndroid>();
		}
		resourceCache.put(name, v);
		return(v);
	}

	public static ImageForAndroid forFile(cape.File file) {
		System.out.println("[cave.ImageForAndroid.forFile] (ImageForAndroid@target_android.sling:207:1): Not implemented");
		return(null);
	}

	public static ImageForAndroid forBuffer(byte[] buffer) {
		if(buffer == null) {
			return(null);
		}
		android.graphics.Bitmap bm = null;
		bm = android.graphics.BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
		if(bm == null) {
			return(null);
		}
		return(forAndroidBitmap(bm));
	}

	public android.graphics.Bitmap bitmap = null;

	public android.graphics.Bitmap getAndroidBitmap() {
		return(bitmap);
	}

	@Override
	public int getPixelWidth() {
		int v = 0;
		if(bitmap != null) {
			v = (int)bitmap.getWidth();
		}
		return(v);
	}

	@Override
	public int getPixelHeight() {
		int v = 0;
		if(bitmap != null) {
			v = (int)bitmap.getHeight();
		}
		return(v);
	}

	@Override
	public Image scaleToSize(int width, int height) {
		if((width < 0) || (height < 0)) {
			return((Image)this);
		}
		android.graphics.Bitmap bm = null;
		try {
			bm = android.graphics.Bitmap.createScaledBitmap(bitmap, width, height, true);
		}
		catch(Exception e) {
			bm = null;
		}
		if(bm == null) {
			return(null);
		}
		return((Image)forAndroidBitmap(bm));
	}

	@Override
	public Image scaleToWidth(int width) {
		int height = (int)(((double)getPixelHeight()) / (((double)getPixelHeight()) / ((double)width)));
		return(scaleToSize(width, height));
	}

	@Override
	public Image scaleToHeight(int height) {
		int width = (int)(((double)getPixelWidth()) / (((double)getPixelHeight()) / ((double)height)));
		return(scaleToSize(width, height));
	}

	@Override
	public Image crop(int x, int y, int w, int h) {
		if(bitmap == null) {
			return(null);
		}
		android.graphics.Bitmap bm = null;
		bm = android.graphics.Bitmap.createBitmap(bitmap, x, y, w, h, null, false);
		if(bm == null) {
			return(null);
		}
		return((Image)forAndroidBitmap(bm));
	}

	@Override
	public byte[] toJPGData() {
		System.out.println("[cave.ImageForAndroid.toJPGData] (ImageForAndroid@target_android.sling:300:1): Not implemented");
		return(null);
	}

	@Override
	public byte[] toPNGData() {
		if(bitmap == null) {
			return(null);
		}
		byte[] buf = null;
		java.io.ByteArrayOutputStream baStream = new java.io.ByteArrayOutputStream();
		android.graphics.Bitmap.CompressFormat cf = android.graphics.Bitmap.CompressFormat.PNG;
		if(bitmap.compress(cf, 70, baStream)) {
			buf = baStream.toByteArray();
		}
		return(buf);
	}

	@Override
	public byte[] toRGBAData() {
		System.out.println("[cave.ImageForAndroid.toRGBAData] (ImageForAndroid@target_android.sling:322:1): Not implemented");
		return(null);
	}

	@Override
	public void releaseImage() {
		if(bitmap != null) {
			bitmap.recycle();
		}
		bitmap = null;
	}
}
