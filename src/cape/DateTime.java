
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

public class DateTime implements StringObject
{
	public static DateTime forNow() {
		return(forTimeSeconds(cape.SystemClock.asSeconds()));
	}

	public static DateTime forTimeSeconds(long seconds) {
		DateTime v = new DateTime();
		if(v == null) {
			return(null);
		}
		v.setTimeSeconds(seconds);
		return(v);
	}

	public static DateTime forTimeValue(TimeValue tv) {
		if(tv == null) {
			return(null);
		}
		return(forTimeSeconds(tv.getSeconds()));
	}

	private long timeSeconds = (long)0;
	private int weekDay = 0;
	private int dayOfMonth = 0;
	private int month = 0;
	private int year = 0;
	private int hours = 0;
	private int minutes = 0;
	private int seconds = 0;
	private boolean utc = false;

	public void onTimeSecondsChanged(long seconds) {
		java.util.TimeZone tz = java.util.TimeZone.getDefault();
		java.util.Calendar cal = java.util.Calendar.getInstance(tz);
		cal.setTimeInMillis(seconds*1000);
		setDayOfMonth(cal.get(java.util.Calendar.DAY_OF_MONTH));
		setMonth(cal.get(java.util.Calendar.MONTH) + 1);
		setYear(cal.get(java.util.Calendar.YEAR));
		setWeekDay(cal.get(java.util.Calendar.DAY_OF_WEEK));
		setHours(cal.get(java.util.Calendar.HOUR_OF_DAY));
		setMinutes(cal.get(java.util.Calendar.MINUTE));
		setSeconds(cal.get(java.util.Calendar.SECOND));
	}

	public long getTimeSeconds() {
		return(timeSeconds);
	}

	public void setTimeSeconds(long seconds) {
		timeSeconds = seconds;
		onTimeSecondsChanged(seconds);
	}

	public java.lang.String toStringDate(char delim) {
		StringBuilder sb = new StringBuilder();
		sb.append(cape.String.forIntegerWithPadding(getYear(), 4));
		if(delim > 0) {
			sb.append(delim);
		}
		sb.append(cape.String.forIntegerWithPadding(getMonth(), 2));
		if(delim > 0) {
			sb.append(delim);
		}
		sb.append(cape.String.forIntegerWithPadding(getDayOfMonth(), 2));
		return(sb.toString());
	}

	public java.lang.String toStringDate() {
		return(toStringDate('-'));
	}

	public java.lang.String toStringTime(char delim) {
		StringBuilder sb = new StringBuilder();
		sb.append(cape.String.forIntegerWithPadding(getHours(), 2));
		if(delim > 0) {
			sb.append(delim);
		}
		sb.append(cape.String.forIntegerWithPadding(getMinutes(), 2));
		if(delim > 0) {
			sb.append(delim);
		}
		sb.append(cape.String.forIntegerWithPadding(getSeconds(), 2));
		return(sb.toString());
	}

	public java.lang.String toStringTime() {
		return(toStringTime('-'));
	}

	public java.lang.String toStringDateTime() {
		StringBuilder sb = new StringBuilder();
		sb.append(toStringDate());
		sb.append(" ");
		sb.append(toStringTime());
		return(sb.toString());
	}

	public java.lang.String toString() {
		return(toStringDateTime());
	}

	public int getWeekDay() {
		return(weekDay);
	}

	public DateTime setWeekDay(int v) {
		weekDay = v;
		return(this);
	}

	public int getDayOfMonth() {
		return(dayOfMonth);
	}

	public DateTime setDayOfMonth(int v) {
		dayOfMonth = v;
		return(this);
	}

	public int getMonth() {
		return(month);
	}

	public DateTime setMonth(int v) {
		month = v;
		return(this);
	}

	public int getYear() {
		return(year);
	}

	public DateTime setYear(int v) {
		year = v;
		return(this);
	}

	public int getHours() {
		return(hours);
	}

	public DateTime setHours(int v) {
		hours = v;
		return(this);
	}

	public int getMinutes() {
		return(minutes);
	}

	public DateTime setMinutes(int v) {
		minutes = v;
		return(this);
	}

	public int getSeconds() {
		return(seconds);
	}

	public DateTime setSeconds(int v) {
		seconds = v;
		return(this);
	}

	public boolean getUtc() {
		return(utc);
	}

	public DateTime setUtc(boolean v) {
		utc = v;
		return(this);
	}
}
