
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

package motion;

public class MultiSpriteEntity extends Entity
{
	protected SpriteLayer layer = null;
	private double x = 0.00;
	private double y = 0.00;

	public void layoutSprites(double x, double y) {
	}

	@Override
	public void initialize() {
		super.initialize();
		if(layer == null) {
			layer = (SpriteLayer)((scene instanceof SpriteLayer) ? scene : null);
		}
	}

	@Override
	public void cleanup() {
		super.cleanup();
		layer = null;
	}

	public void move(double x, double y) {
		this.x = x;
		this.y = y;
		layoutSprites(x, y);
	}

	public void onLayout() {
		layoutSprites(x, y);
	}

	public double getX() {
		return(x);
	}

	public double getY() {
		return(y);
	}
}
