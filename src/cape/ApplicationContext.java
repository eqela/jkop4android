
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

package cape;

/**
 * An ApplicationContext represents the environment in which an application
 * executes. The implementation of this is system dependent, and depending on the
 * actual environment, the object that implements ApplicationContext may also
 * implement other interfaces that provide additional features into that particular
 * environment.
 */

public interface ApplicationContext extends LoggingContext
{
	/**
	 * Returns a File object that represents a directory where this application can
	 * store private data. The data is specific to the current applications and (if
	 * applicable) the current user. The exact location and implementation fully
	 * depends on the underlying operating system.
	 */

	public File getApplicationDataDirectory();
}
