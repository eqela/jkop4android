
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

public class AudioManagerForAndroid extends AudioManager
{
	private static class MyAudioClip implements AudioClip, cape.Runnable
	{
		private android.media.SoundPool soundPool = null;
		private int soundId = 0;

		public void run() {
			int sid = 0;
			sid = soundPool.play(soundId, 1, 1, 0, 0, 1);
		}

		public boolean play() {
			return(cape.Thread.start((cape.Runnable)this));
		}

		public android.media.SoundPool getSoundPool() {
			return(soundPool);
		}

		public MyAudioClip setSoundPool(android.media.SoundPool v) {
			soundPool = v;
			return(this);
		}

		public int getSoundId() {
			return(soundId);
		}

		public MyAudioClip setSoundId(int v) {
			soundId = v;
			return(this);
		}
	}

	private static class MyAudioStream implements AudioStream
	{
		android.media.MediaPlayer mp;

		private int resourceId = 0;
		private boolean prepared = false;
		private int milli = 1000;

		public MyAudioStream(android.content.Context activityContext, java.lang.String id) {
			java.lang.String rid = (((java.lang.String)activityContext.getPackageName()) + ":raw/") + id;
			int resourceid = 0;
			android.content.res.Resources res = activityContext.getResources();
			if(res != null) {
				int aid = res.getIdentifier(rid, null, null);
				if(aid > 0) {
					mp = android.media.MediaPlayer.create(activityContext, aid);
					resourceid = aid;
				}
			}
		}

		public boolean play() {
			return(playback("play"));
		}

		public boolean pause() {
			return(playback("pause"));
		}

		public boolean stop() {
			return(playback("stop"));
		}

		public boolean seek(int sec) {
			return(playback("seek", (java.lang.Object)cape.Integer.asObject(sec)));
		}

		public int getCurrentTime() {
			int ct = 0;
			if(mp != null) {
				ct = mp.getCurrentPosition() / milli;
			}
			return(ct);
		}

		public int getDuration() {
			int n = 0;
			if(mp != null) {
				n = (int)(mp.getDuration() / milli);
			}
			return(n);
		}

		public boolean setLooping(boolean v) {
			return(playback("loop", (java.lang.Object)cape.Boolean.asObject(v)));
		}

		public boolean setVolume(double v) {
			double volume = v;
			if(v > 1.00) {
				volume = 1.00;
			}
			else if(v < 0) {
				volume = 0.00;
			}
			return(playback("volume", (java.lang.Object)cape.Double.asObject(volume)));
		}

		public boolean playback(java.lang.String action, java.lang.Object obj) {
			if(mp == null) {
				return(false);
			}
			if(android.text.TextUtils.equals(action, "play")) {
				if(!prepared) {
					try{
						mp.prepareAsync();
					}
					catch(java.lang.IllegalStateException e) {
						e.printStackTrace();
					}
					prepared = true;
				}
				try{
					mp.start();
				}
				catch(java.lang.IllegalStateException e) {
					e.printStackTrace();
				}
			}
			else if(android.text.TextUtils.equals(action, "stop")) {
				try{
					if(mp.isPlaying()) {
						mp.seekTo(0);
					}
					mp.stop();
				}
				catch(java.lang.IllegalStateException e) {
					e.printStackTrace();
				}
				prepared = false;
			}
			else if(android.text.TextUtils.equals(action, "pause")) {
				try{
					if(mp.isPlaying()) {
						mp.pause();
					}
				}
				catch(java.lang.IllegalStateException e) {
					e.printStackTrace();
				}
			}
			else if(android.text.TextUtils.equals(action, "loop")) {
				if(obj instanceof cape.BooleanObject) {
					boolean v = cape.Boolean.asBoolean(obj);
					mp.setLooping(v);
				}
			}
			else if(android.text.TextUtils.equals(action, "seek")) {
				if(obj instanceof cape.IntegerObject) {
					int sec = cape.Integer.asInteger(obj);
					try{
						if(prepared) {
							mp.seekTo(sec * milli);
						}
					}
					catch(java.lang.IllegalStateException e) {
						e.printStackTrace();
					}
				}
			}
			else if(android.text.TextUtils.equals(action, "volume")) {
				if(obj instanceof cape.DoubleObject) {
					double v = cape.Double.asDouble(obj);
					mp.setVolume((float)v, (float)v);
				}
			}
			return(true);
		}

		public boolean playback(java.lang.String action) {
			return(playback(action, null));
		}

		protected void finalize() throws java.lang.Throwable {
			super.finalize();
			if(mp != null) {
				mp.release();
			}
		}
	}

	public static AudioManagerForAndroid forApplication(cape.AndroidApplicationContext context) {
		if(context == null) {
			return(null);
		}
		AudioManagerForAndroid v = new AudioManagerForAndroid();
		v.setContext(context);
		return(v);
	}

	private cape.AndroidApplicationContext context = null;
	private android.media.SoundPool soundPool = null;

	public AudioManagerForAndroid() {
		soundPool = new android.media.SoundPool(128, android.media.AudioManager.STREAM_MUSIC, 0);
	}

	private java.lang.String sanitizeResourceName(java.lang.String n) {
		if(android.text.TextUtils.equals(n, null)) {
			return(null);
		}
		cape.StringBuilder sb = new cape.StringBuilder();
		cape.CharacterIterator it = cape.String.iterate(n);
		char c = ' ';
		while((c = it.getNextChar()) > 0) {
			if((c >= 'A') && (c <= 'Z')) {
				sb.append((char)(('a' + c) - 'A'));
			}
			else if((c >= 'a') && (c <= 'z')) {
				sb.append(c);
			}
			else if((c >= '0') && (c <= '9')) {
				sb.append(c);
			}
			else {
				sb.append('_');
			}
		}
		return(sb.toString());
	}

	@Override
	public AudioClip getClipForResource(java.lang.String id) {
		if(cape.String.isEmpty(id)) {
			return(null);
		}
		android.content.Context activityContext = context.getAndroidActivityContext();
		if(activityContext == null) {
			return(null);
		}
		int soundId = -1;
		java.lang.String rid = (((java.lang.String)activityContext.getPackageName()) + ":raw/") + sanitizeResourceName(id);
		android.content.res.Resources res = activityContext.getResources();
		if(res != null) {
			int aid = res.getIdentifier(rid, null, null);
			if(aid > 0) {
				soundId = soundPool.load(activityContext, aid, 1);
			}
		}
		if(soundId < 0) {
			return(null);
		}
		MyAudioClip v = new MyAudioClip();
		v.setSoundPool(soundPool);
		v.setSoundId(soundId);
		return((AudioClip)v);
	}

	@Override
	public AudioStream getStreamForResource(java.lang.String id) {
		if(cape.String.isEmpty(id)) {
			return(null);
		}
		android.content.Context activityContext = context.getAndroidActivityContext();
		if(activityContext == null) {
			return(null);
		}
		return((AudioStream)new MyAudioStream(activityContext, sanitizeResourceName(id)));
	}

	public cape.AndroidApplicationContext getContext() {
		return(context);
	}

	public AudioManagerForAndroid setContext(cape.AndroidApplicationContext v) {
		context = v;
		return(this);
	}
}
