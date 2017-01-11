
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

public class TimeValue
{
	public static TimeValue forSeconds(long seconds) {
		TimeValue v = new TimeValue();
		v.seconds = seconds;
		return(v);
	}

	private long seconds = (long)0;
	private long microSeconds = (long)0;

	public TimeValue dup() {
		TimeValue v = new TimeValue();
		v.copyFrom(this);
		return(v);
	}

	public void reset() {
		seconds = (long)0;
		microSeconds = (long)0;
	}

	public void copyFrom(TimeValue tv) {
		seconds = tv.seconds;
		microSeconds = tv.microSeconds;
	}

	public void set(TimeValue tv) {
		seconds = tv.getSeconds();
		microSeconds = tv.getMicroSeconds();
	}

	public void setSeconds(long value) {
		seconds = value;
	}

	public void setMilliSeconds(long value) {
		microSeconds = value * 1000;
	}

	public void setMicroSeconds(long value) {
		microSeconds = value;
	}

	public TimeValue add(long s, long us) {
		long ts = this.getSeconds() + s;
		long tus = this.getMicroSeconds() + us;
		if(tus > 1000000) {
			ts += tus / 1000000;
			tus = tus % 1000000;
		}
		while(tus < 0) {
			ts--;
			tus += (long)1000000;
		}
		TimeValue v = new TimeValue();
		v.seconds = ts;
		v.microSeconds = tus;
		return(v);
	}

	public TimeValue add(TimeValue tv) {
		if(tv == null) {
			return(this);
		}
		return(add(tv.getSeconds(), tv.getMicroSeconds()));
	}

	public TimeValue subtract(TimeValue tv) {
		if(tv == null) {
			return(this);
		}
		return(add(-tv.getSeconds(), -tv.getMicroSeconds()));
	}

	public long asMicroSeconds() {
		return((long)((this.getSeconds() * 1000000) + this.getMicroSeconds()));
	}

	public static long diff(TimeValue a, TimeValue b) {
		if((a == null) && (b == null)) {
			return((long)0);
		}
		if(a == null) {
			return(b.asMicroSeconds());
		}
		if(b == null) {
			return(a.asMicroSeconds());
		}
		long r = ((a.seconds - b.seconds) * 1000000) + (a.microSeconds - b.microSeconds);
		return(r);
	}

	public static double diffDouble(TimeValue a, TimeValue b) {
		return((double)(diff(a, b) / 1000000.00));
	}

	public long getSeconds() {
		return(seconds);
	}

	public long getMicroSeconds() {
		return(microSeconds);
	}
}
