
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

package capex;

public class Vector2
{
	public double x = 0.00;
	public double y = 0.00;

	public static Vector2 create(double x, double y) {
		Vector2 v = new Vector2();
		v.x = x;
		v.y = y;
		return(v);
	}

	public Vector2 add(Vector2 b) {
		x += b.x;
		y += b.y;
		return(this);
	}

	public Vector2 subtract(Vector2 b) {
		x -= b.x;
		y -= b.y;
		return(this);
	}

	public Vector2 multiply(Vector2 b) {
		x *= b.x;
		y *= b.y;
		return(this);
	}

	public Vector2 multiplyScalar(double a) {
		x += a;
		y += a;
		return(this);
	}

	public double distance(Vector2 b) {
		double dist = ((y - b.y) * (y - b.y)) + ((x - b.x) * (x - b.x));
		return(cape.Math.sqrt(dist));
	}

	public double getLength() {
		return(cape.Math.sqrt((x * x) + (y * y)));
	}
}
