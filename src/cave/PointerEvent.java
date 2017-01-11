
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

public class PointerEvent
{
	public static final int DOWN = 0;
	public static final int MOVE = 1;
	public static final int CANCEL = 2;
	public static final int UP = 3;
	public int pointerId = 0;
	public int action = 0;
	public double x = 0.00;
	public double y = 0.00;
	public boolean isConsumed = false;

	public void consume() {
		isConsumed = true;
	}

	public int getPointerId() {
		return(pointerId);
	}

	public PointerEvent setPointerId(int value) {
		pointerId = value;
		return(this);
	}

	public int getAction() {
		return(action);
	}

	public PointerEvent setAction(int value) {
		action = value;
		return(this);
	}

	public double getX() {
		return(x);
	}

	public PointerEvent setX(double value) {
		x = value;
		return(this);
	}

	public double getY() {
		return(y);
	}

	public PointerEvent setY(double value) {
		y = value;
		return(this);
	}

	public boolean isInside(double xc, double yc, double width, double height) {
		if((((x >= xc) && (x < (xc + width))) && (y >= yc)) && (y < (yc + height))) {
			return(true);
		}
		return(false);
	}
}
