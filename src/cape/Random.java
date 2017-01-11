
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

public class Random
{
	java.util.Random random;

	public Random() {
		random = new java.util.Random();
	}

	public Random(long seed) {
		setSeed(seed);
	}

	public void setSeed(long seed) {
		random.setSeed(seed);
	}

	public boolean nextBoolean() {
		if((nextInt() % 2) == 0) {
			return(false);
		}
		return(true);
	}

	public int nextInt() {
		return(random.nextInt());
	}

	public int nextInt(int n) {
		return(random.nextInt(n));
	}

	/**
	 * Used to retrieve integer values from "s" (inclusive) and "e" (exclusive)
	 */

	public int nextInt(int s, int e) {
		return(random.nextInt(e - s + 1) + s);
	}

	public void nextBytes(byte[] buf) {
		random.nextBytes(buf);
	}

	public double nextDouble() {
		return(random.nextDouble());
	}

	public float nextFloat() {
		return(random.nextFloat());
	}

	public double nextGaussian() {
		System.out.println("--- stub --- cape.Random :: nextGaussian");
		return(0.00);
	}
}
