
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

public interface GuiApplicationContext extends cape.ApplicationContext
{
	public Image getResourceImage(java.lang.String id);
	public Image getImageForBuffer(byte[] buffer, java.lang.String mimeType);
	public void showMessageDialog(java.lang.String title, java.lang.String message);
	public void showMessageDialog(java.lang.String title, java.lang.String message, samx.function.Procedure0 callback);
	public void showConfirmDialog(java.lang.String title, java.lang.String message, samx.function.Procedure0 okcallback, samx.function.Procedure0 cancelcallback);
	public void showErrorDialog(java.lang.String message);
	public void showErrorDialog(java.lang.String message, samx.function.Procedure0 callback);
	public int getScreenTopMargin();
	public int getScreenWidth();
	public int getScreenHeight();
	public int getScreenDensity();
	public int getHeightValue(java.lang.String spec);
	public int getWidthValue(java.lang.String spec);
	public void startTimer(long timeout, samx.function.Procedure0 callback);
}
