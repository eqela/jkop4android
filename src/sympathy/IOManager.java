
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

package sympathy;

abstract public class IOManager
{
	public static IOManager createDefault() {
		return(null);
	}

	private boolean executable = false;

	public boolean execute(cape.LoggingContext ctx) {
		return(false);
	}

	public IOManagerEntry addWithReadListener(java.lang.Object o, samx.function.Procedure0 rrl) {
		IOManagerEntry v = add(o);
		if(v != null) {
			v.setReadListener(rrl);
		}
		return(v);
	}

	public IOManagerEntry addWithWriteListener(java.lang.Object o, samx.function.Procedure0 wrl) {
		IOManagerEntry v = add(o);
		if(v != null) {
			v.setWriteListener(wrl);
		}
		return(v);
	}

	public abstract IOManagerEntry add(java.lang.Object o);
	public abstract IOManagerTimer startTimer(long delay, samx.function.Function0<java.lang.Boolean> handler);
	public abstract void stop();

	public boolean getExecutable() {
		return(executable);
	}

	public IOManager setExecutable(boolean v) {
		executable = v;
		return(this);
	}
}
