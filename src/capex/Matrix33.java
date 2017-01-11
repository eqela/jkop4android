
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

public class Matrix33
{
	public static Matrix33 forZero() {
		return(forValues(new double[] {
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00,
			0.00
		}));
	}

	public static Matrix33 forIdentity() {
		return(forValues(new double[] {
			1.00,
			0.00,
			0.00,
			0.00,
			1.00,
			0.00,
			0.00,
			0.00,
			1.00
		}));
	}

	public static Matrix33 invertMatrix(Matrix33 m) {
		double d = ((((((m.v[0] * m.v[4]) * m.v[8]) + ((m.v[3] * m.v[7]) * m.v[2])) + ((m.v[6] * m.v[1]) * m.v[5])) - ((m.v[0] * m.v[7]) * m.v[5])) - ((m.v[3] * m.v[1]) * m.v[8])) - ((m.v[6] * m.v[4]) * m.v[2]);
		Matrix33 v = new Matrix33();
		v.v[0] = ((m.v[4] * m.v[8]) - (m.v[7] * m.v[5])) / d;
		v.v[3] = ((m.v[6] * m.v[5]) - (m.v[3] * m.v[8])) / d;
		v.v[6] = ((m.v[3] * m.v[7]) - (m.v[6] * m.v[4])) / d;
		v.v[1] = ((m.v[7] * m.v[2]) - (m.v[1] * m.v[8])) / d;
		v.v[4] = ((m.v[0] * m.v[8]) - (m.v[6] * m.v[2])) / d;
		v.v[7] = ((m.v[6] * m.v[1]) - (m.v[0] * m.v[7])) / d;
		v.v[2] = ((m.v[1] * m.v[5]) - (m.v[4] * m.v[2])) / d;
		v.v[5] = ((m.v[3] * m.v[2]) - (m.v[0] * m.v[5])) / d;
		v.v[8] = ((m.v[0] * m.v[4]) - (m.v[3] * m.v[1])) / d;
		return(v);
	}

	public static Matrix33 forTranslate(double translateX, double translateY) {
		return(forValues(new double[] {
			1.00,
			0.00,
			translateX,
			0.00,
			1.00,
			translateY,
			0.00,
			0.00,
			1.00
		}));
	}

	public static Matrix33 forRotation(double angle) {
		double c = cape.Math.cos(angle);
		double s = cape.Math.sin(angle);
		return(forValues(new double[] {
			c,
			s,
			0.00,
			-s,
			c,
			0.00,
			0.00,
			0.00,
			1.00
		}));
	}

	public static Matrix33 forRotationWithCenter(double angle, double centerX, double centerY) {
		Matrix33 translate = forTranslate(centerX, centerY);
		Matrix33 rotate = forRotation(angle);
		Matrix33 translateBack = forTranslate(-centerX, -centerY);
		Matrix33 translatedRotated = multiplyMatrix(translate, rotate);
		return(multiplyMatrix(translatedRotated, translateBack));
	}

	public static Matrix33 forSkew(double skewX, double skewY) {
		return(forValues(new double[] {
			1.00,
			skewX,
			0.00,
			skewY,
			1.00,
			0.00,
			0.00,
			0.00,
			1.00
		}));
	}

	public static Matrix33 forScale(double scaleX, double scaleY) {
		return(forValues(new double[] {
			scaleX,
			0.00,
			0.00,
			0.00,
			scaleY,
			0.00,
			0.00,
			0.00,
			1.00
		}));
	}

	public static Matrix33 forFlip(boolean flipX, boolean flipY) {
		Matrix33 xmat33 = forValues(new double[] {
			1.00,
			0.00,
			0.00,
			0.00,
			-1.00,
			0.00,
			0.00,
			0.00,
			1.00
		});
		Matrix33 ymat33 = forValues(new double[] {
			-1.00,
			0.00,
			0.00,
			0.00,
			1.00,
			0.00,
			0.00,
			0.00,
			1.00
		});
		if(flipX && flipY) {
			return(multiplyMatrix(xmat33, ymat33));
		}
		else if(flipX) {
			return(xmat33);
		}
		else if(flipY) {
			return(ymat33);
		}
		return(forIdentity());
	}

	public static Matrix33 forValues(double[] mv) {
		Matrix33 v = new Matrix33();
		int i = 0;
		for(i = 0 ; i < 9 ; i++) {
			if(i >= mv.length) {
				v.v[i] = 0.00;
			}
			else {
				v.v[i] = mv[i];
			}
		}
		return(v);
	}

	public static Matrix33 multiplyScalar(double v, Matrix33 mm) {
		Matrix33 mat33 = forZero();
		mat33.v[0] = mm.v[0] * v;
		mat33.v[1] = mm.v[1] * v;
		mat33.v[2] = mm.v[2] * v;
		mat33.v[3] = mm.v[3] * v;
		mat33.v[4] = mm.v[4] * v;
		mat33.v[5] = mm.v[5] * v;
		mat33.v[6] = mm.v[6] * v;
		mat33.v[7] = mm.v[7] * v;
		mat33.v[8] = mm.v[8] * v;
		return(mat33);
	}

	public static Matrix33 multiplyMatrix(Matrix33 a, Matrix33 b) {
		Matrix33 matrix33 = new Matrix33();
		matrix33.v[0] = ((a.v[0] * b.v[0]) + (a.v[1] * b.v[3])) + (a.v[2] * b.v[6]);
		matrix33.v[1] = ((a.v[0] * b.v[1]) + (a.v[1] * b.v[4])) + (a.v[2] * b.v[7]);
		matrix33.v[2] = ((a.v[0] * b.v[2]) + (a.v[1] * b.v[5])) + (a.v[2] * b.v[8]);
		matrix33.v[3] = ((a.v[3] * b.v[0]) + (a.v[4] * b.v[3])) + (a.v[5] * b.v[6]);
		matrix33.v[4] = ((a.v[3] * b.v[1]) + (a.v[4] * b.v[4])) + (a.v[5] * b.v[7]);
		matrix33.v[5] = ((a.v[3] * b.v[2]) + (a.v[4] * b.v[5])) + (a.v[5] * b.v[8]);
		matrix33.v[6] = ((a.v[6] * b.v[0]) + (a.v[7] * b.v[3])) + (a.v[8] * b.v[6]);
		matrix33.v[7] = ((a.v[6] * b.v[1]) + (a.v[7] * b.v[4])) + (a.v[8] * b.v[7]);
		matrix33.v[8] = ((a.v[6] * b.v[2]) + (a.v[7] * b.v[5])) + (a.v[8] * b.v[8]);
		return(matrix33);
	}

	public static Vector2 multiplyVector(Matrix33 a, Vector2 b) {
		double x = ((a.v[0] * b.x) + (a.v[1] * b.y)) + (a.v[2] * 1.00);
		double y = ((a.v[3] * b.x) + (a.v[4] * b.y)) + (a.v[5] * 1.00);
		return(capex.Vector2.create(x, y));
	}

	public double[] v = new double[9];
}
