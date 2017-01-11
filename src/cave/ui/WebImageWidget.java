
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

public class WebImageWidget extends AsynchronousImageWidget
{
	public static WebImageWidget forPlaceholderImage(cave.GuiApplicationContext context, cave.Image image) {
		WebImageWidget v = new WebImageWidget(context);
		v.setWidgetPlaceholderImage(image);
		return(v);
	}

	public WebImageWidget(cave.GuiApplicationContext context) {
		super(context);
	}

	public void setWidgetImageResource(java.lang.String resName) {
		ImageWidget img = onStartLoading(false);
		if(img != null) {
			img.setWidgetImageResource(resName);
		}
		onEndLoading();
	}

	public void setWidgetImage(cave.Image image) {
		ImageWidget img = onStartLoading(false);
		if(img != null) {
			img.setWidgetImage(image);
		}
		onEndLoading();
	}

	public void setWidgetImageUrl(java.lang.String url, samx.function.Procedure1<cape.Error> callback) {
		setWidgetImageUrl(url, null, null, callback);
	}

	public void setWidgetImageUrl(java.lang.String url) {
		setWidgetImageUrl(url, null);
	}

	public void setWidgetImageUrl(java.lang.String url, cape.KeyValueList<java.lang.String, java.lang.String> headers, byte[] body, samx.function.Procedure1<cape.Error> callback) {
		capex.WebClient client = capex.NativeWebClient.instance();
		if(client == null) {
			cape.Log.error((cape.LoggingContext)context, "Failed to create web client.");
			if(callback != null) {
				callback.execute(cape.Error.forCode("noWebClient"));
			}
			return;
		}
		cape.Log.debug((cape.LoggingContext)context, (("WebImageWidget" + ": Start loading image: `") + url) + "'");
		final ImageWidget img = onStartLoading();
		final java.lang.String uu = url;
		final samx.function.Procedure1<cape.Error> cb = callback;
		client.query("GET", url, headers, body, new samx.function.Procedure3<java.lang.String, cape.KeyValueList<java.lang.String, java.lang.String>, byte[]>() {
			public void execute(java.lang.String rcode, cape.KeyValueList<java.lang.String, java.lang.String> rheaders, byte[] rbody) {
				onEndLoading();
				if(rbody == null) {
					cape.Log.error((cape.LoggingContext)context, (("WebImageWidget" + ": FAILED loading image: `") + uu) + "'");
					if(cb != null) {
						cb.execute(cape.Error.forCode("failedToDownload"));
					}
					return;
				}
				java.lang.String mimeType = null;
				java.util.ArrayList<cape.KeyValuePair<java.lang.String, java.lang.String>> hdrv = rheaders.asVector();
				if(hdrv != null) {
					int n = 0;
					int m = hdrv.size();
					for(n = 0 ; n < m ; n++) {
						cape.KeyValuePair<java.lang.String, java.lang.String> hdr = hdrv.get(n);
						if(hdr != null) {
							if(cape.String.equalsIgnoreCase(hdr.key, "content-type")) {
								java.lang.String vv = hdr.value;
								if(!(android.text.TextUtils.equals(vv, null))) {
									int sc = cape.String.indexOf(vv, ';');
									if(sc < 0) {
										mimeType = vv;
									}
									else {
										mimeType = cape.String.getSubString(vv, sc);
									}
								}
							}
						}
					}
				}
				cave.Image imgo = context.getImageForBuffer(rbody, mimeType);
				if(imgo == null) {
					cape.Log.error((cape.LoggingContext)context, "WebImageWidget" + ": Failed to create image from the returned data");
					if(cb != null) {
						cb.execute(cape.Error.forCode("failedToCreateImage"));
					}
					return;
				}
				cape.Log.debug((cape.LoggingContext)context, (("WebImageWidget" + ": DONE loading image: `") + uu) + "'");
				img.setWidgetImage(imgo);
			}
		});
	}
}
