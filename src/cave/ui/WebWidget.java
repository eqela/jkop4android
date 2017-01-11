
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

public class WebWidget extends android.webkit.WebView
{
	public static WebWidget forUrl(cave.GuiApplicationContext context, java.lang.String url) {
		WebWidget v = new WebWidget(context);
		v.setWidgetUrl(url);
		return(v);
	}

	public static WebWidget forHtmlString(cave.GuiApplicationContext context, java.lang.String html) {
		WebWidget v = new WebWidget(context);
		v.setWidgetHtmlString(html);
		return(v);
	}

	static class MyWebViewClient extends android.webkit.WebViewClient
	{
		public boolean shouldOverrideUrlLoading(android.webkit.WebView view, java.lang.String url) {
			return(((WebWidget)view).overrideWidgetUrlLoading(url));
		}
	}

	private cave.GuiApplicationContext widgetContext = null;
	private java.lang.String widgetUrl = null;
	private java.lang.String widgetHtmlString = null;
	private samx.function.Function1<java.lang.Boolean,java.lang.String> customUrlHandler = null;

	public WebWidget(cave.GuiApplicationContext context) {
		super(((cave.GuiApplicationContextForAndroid)context).getActivityContext());
		setWebViewClient(new MyWebViewClient());
		android.webkit.WebSettings settings = getSettings();
		settings.setJavaScriptEnabled(true);
		widgetContext = context;
	}

	public boolean overrideWidgetUrlLoading(java.lang.String url) {
		if(customUrlHandler != null) {
			return(customUrlHandler.execute(url));
		}
		return(false);
	}

	public WebWidget setWidgetHtmlString(java.lang.String html) {
		this.widgetHtmlString = html;
		this.widgetUrl = null;
		updateWidgetContent();
		return(this);
	}

	public java.lang.String getWidgetHtmlString() {
		return(widgetHtmlString);
	}

	public WebWidget setWidgetUrl(java.lang.String url) {
		this.widgetUrl = url;
		this.widgetHtmlString = null;
		updateWidgetContent();
		return(this);
	}

	public java.lang.String getWidgetUrl() {
		return(widgetUrl);
	}

	private void updateWidgetContent() {
		java.lang.String url = widgetUrl;
		if(!(android.text.TextUtils.equals(url, null))) {
			loadUrl(url);
		}
		else {
			java.lang.String htmlString = widgetHtmlString;
			loadData(htmlString, "text/html", null);
		}
	}

	public samx.function.Function1<java.lang.Boolean,java.lang.String> getCustomUrlHandler() {
		return(customUrlHandler);
	}

	public WebWidget setCustomUrlHandler(samx.function.Function1<java.lang.Boolean,java.lang.String> v) {
		customUrlHandler = v;
		return(this);
	}
}
