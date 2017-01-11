
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

package cave.ui;

public class WidgetAnimation
{
	public static WidgetAnimation forDuration(cave.GuiApplicationContext context, long duration) {
		WidgetAnimation v = new WidgetAnimation(context);
		v.setDuration(duration);
		return(v);
	}

	private cave.GuiApplicationContext context = null;
	private long duration = (long)0;
	private java.util.ArrayList<samx.function.Procedure1<java.lang.Double>> callbacks = null;
	private samx.function.Procedure0 endListener = null;
	private boolean shouldStop = false;
	private boolean shouldLoop = false;

	public WidgetAnimation(cave.GuiApplicationContext context) {
		this.context = context;
		callbacks = new java.util.ArrayList<samx.function.Procedure1<java.lang.Double>>();
	}

	public WidgetAnimation addCallback(samx.function.Procedure1<java.lang.Double> callback) {
		if(callback != null) {
			callbacks.add(callback);
		}
		return(this);
	}

	public WidgetAnimation addCrossFade(android.view.View from, android.view.View to, boolean removeFrom) {
		final android.view.View ff = from;
		final android.view.View tt = to;
		final boolean rf = removeFrom;
		addCallback(new samx.function.Procedure1<java.lang.Double>() {
			public void execute(java.lang.Double completion) {
				cave.ui.Widget.setAlpha(ff, 1.00 - completion);
				cave.ui.Widget.setAlpha(tt, completion);
				if(rf && (completion >= 1.00)) {
					cave.ui.Widget.removeFromParent(ff);
				}
			}
		});
		return(this);
	}

	public WidgetAnimation addCrossFade(android.view.View from, android.view.View to) {
		return(addCrossFade(from, to, false));
	}

	public WidgetAnimation addFadeIn(android.view.View from) {
		final android.view.View ff = from;
		addCallback(new samx.function.Procedure1<java.lang.Double>() {
			public void execute(java.lang.Double completion) {
				cave.ui.Widget.setAlpha(ff, completion);
			}
		});
		return(this);
	}

	public WidgetAnimation addFadeOut(android.view.View from, boolean removeAfter) {
		final android.view.View ff = from;
		final boolean ra = removeAfter;
		addCallback(new samx.function.Procedure1<java.lang.Double>() {
			public void execute(java.lang.Double completion) {
				cave.ui.Widget.setAlpha(ff, 1.00 - completion);
				if(ra && (completion >= 1.00)) {
					cave.ui.Widget.removeFromParent(ff);
				}
			}
		});
		return(this);
	}

	public WidgetAnimation addFadeOut(android.view.View from) {
		return(addFadeOut(from, false));
	}

	public WidgetAnimation addFadeOutIn(android.view.View from) {
		final android.view.View ff = from;
		addCallback(new samx.function.Procedure1<java.lang.Double>() {
			public void execute(java.lang.Double completion) {
				double r = cape.Math.remainder(completion, 1.00);
				if(r < 0.50) {
					cave.ui.Widget.setAlpha(ff, 1.00 - (r * 2));
				}
				else {
					cave.ui.Widget.setAlpha(ff, (r - 0.50) * 2);
				}
			}
		});
		return(this);
	}

	public void tick(double completion) {
		if(callbacks != null) {
			int n = 0;
			int m = callbacks.size();
			for(n = 0 ; n < m ; n++) {
				samx.function.Procedure1<java.lang.Double> callback = callbacks.get(n);
				if(callback != null) {
					callback.execute(completion);
				}
			}
		}
	}

	public boolean onProgress(long elapsedTime) {
		double completion = ((double)elapsedTime) / ((double)duration);
		tick(completion);
		if(((shouldLoop == false) && (completion >= 1.00)) || shouldStop) {
			onAnimationEnded();
			return(false);
		}
		return(true);
	}

	public void onAnimationEnded() {
		if(endListener != null) {
			endListener.execute();
		}
	}

	static class MyAnimatorListener implements android.animation.TimeAnimator.TimeListener
	{
		private WidgetAnimation listener = null;
		private long startTime = (long)-1;
		private long duration = (long)0;
		private android.animation.TimeAnimator animator = null;

		public void onTimeUpdate(android.animation.TimeAnimator animation, long totalTime, long deltaTime) {
			if(startTime < 0) {
				startTime = totalTime;
			}
			if(listener.onProgress(totalTime - startTime) == false) {
				animator.end();
			}
		}

		public WidgetAnimation getListener() {
			return(listener);
		}

		public MyAnimatorListener setListener(WidgetAnimation v) {
			listener = v;
			return(this);
		}

		public long getStartTime() {
			return(startTime);
		}

		public MyAnimatorListener setStartTime(long v) {
			startTime = v;
			return(this);
		}

		public long getDuration() {
			return(duration);
		}

		public MyAnimatorListener setDuration(long v) {
			duration = v;
			return(this);
		}

		public android.animation.TimeAnimator getAnimator() {
			return(animator);
		}

		public MyAnimatorListener setAnimator(android.animation.TimeAnimator v) {
			animator = v;
			return(this);
		}
	}

	private android.animation.TimeAnimator animator = null;

	public void start() {
		animator = new android.animation.TimeAnimator();
		MyAnimatorListener listener = new MyAnimatorListener();
		listener.setListener(this);
		listener.setDuration(duration);
		listener.setAnimator(animator);
		animator.setTimeListener(listener);
		animator.start();
	}

	public long getDuration() {
		return(duration);
	}

	public WidgetAnimation setDuration(long v) {
		duration = v;
		return(this);
	}

	public samx.function.Procedure0 getEndListener() {
		return(endListener);
	}

	public WidgetAnimation setEndListener(samx.function.Procedure0 v) {
		endListener = v;
		return(this);
	}

	public boolean getShouldStop() {
		return(shouldStop);
	}

	public WidgetAnimation setShouldStop(boolean v) {
		shouldStop = v;
		return(this);
	}

	public boolean getShouldLoop() {
		return(shouldLoop);
	}

	public WidgetAnimation setShouldLoop(boolean v) {
		shouldLoop = v;
		return(this);
	}
}
