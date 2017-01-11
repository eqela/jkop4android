
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

public class ProcessLauncherJava
{
	private static class MyProcess implements Process
	{
		public java.lang.Process process = null;

		private int exitValue = 0;

		public void close() {
			if(process != null) {
				exitValue = process.exitValue();
				process = null;
			}
		}

		public java.lang.String getId() {
			int id = 0;
			if(process != null) {
				id = process.hashCode();
			}
			return(cape.String.forInteger(id));
		}

		public boolean isRunning() {
			boolean v = false;
			if(process != null) {
				try {
					int x = process.exitValue();
				}
				catch(Exception e) {
					v = true;
				}
			}
			return(v);
		}

		public int getExitStatus() {
			if(isRunning()) {
				return(-1);
			}
			if(process != null) {
				int v;
				try {
					v = process.exitValue();
				}
				catch(Exception e) {
					v = -1;
				}
				exitValue = v;
			}
			close();
			return(exitValue);
		}

		public void sendInterrupt() {
		}

		public void killRequest() {
			killForce();
		}

		public void killForce() {
			if(process != null) {
				try {
					process.destroy();
				}
				catch(Exception e) {
				}
			}
		}

		public void kill(int timeout) {
			killForce();
			if(process != null) {
				try {
					process.waitFor();
				}
				catch(Exception e) {
				}
			}
		}

		public void kill() {
			kill(2);
		}

		public int waitForExit() {
			if(process != null) {
				try {
					process.waitFor();
				}
				catch(Exception e) {
				}
			}
			return(getExitStatus());
		}
	}

	public static Process startProcess(ProcessLauncher launcher, boolean wait, BufferDataReceiver pipeHandler) {
		File ff = launcher.getFile();
		if(ff == null) {
			return(null);
		}
		StringBuilder sb = new StringBuilder();
		java.util.ArrayList<java.lang.String> array = launcher.getParams();
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.String param = array.get(n);
				if(param != null) {
					if(sb.count() > 0) {
						sb.append(' ');
					}
					sb.append('\"');
					sb.append(param);
					sb.append('\"');
				}
			}
		}
		java.lang.String sbs = sb.toString();
		if(android.text.TextUtils.equals(sbs, null)) {
			sbs = "";
		}
		MyProcess np = new MyProcess();
		java.lang.String cwdp = null;
		File cwd = launcher.getCwd();
		if(cwd != null) {
			cwdp = cwd.getPath();
		}
		ProcessBuilder pb = new ProcessBuilder(sbs);
		pb.directory(new java.io.File(ff.getPath()));
		pb.directory(new java.io.File(cwdp));
		java.util.HashMap<java.lang.String,java.lang.String> env = launcher.getEnv();
		if((env != null) && (cape.Map.count(env) > 0)) {
			java.util.ArrayList<java.lang.String> keys = cape.Map.getKeys(env);
			if(keys != null) {
				int n2 = 0;
				int m2 = keys.size();
				for(n2 = 0 ; n2 < m2 ; n2++) {
					java.lang.String key = keys.get(n2);
					if(key != null) {
						java.lang.String val = env.get(key);
						if(android.text.TextUtils.equals(val, null)) {
							val = "";
						}
						env.put(key, val);
					}
				}
			}
		}
		try {
			np.process = pb.start();
		}
		catch(Exception e) {
			np = null;
		}
		if(np == null) {
			return(null);
		}
		if(wait) {
			java.lang.String output = null;
			if(pipeHandler != null) {
				try {
					java.io.InputStream stdin = np.process.getInputStream();
					java.io.InputStream stderr = np.process.getErrorStream();
					byte[] bufferin = new byte[1024];
					byte[] buffererr = new byte[1024];
					while(true) {
						int sizeOfIn = stdin.read(bufferin, 0, bufferin.length);
						int sizeOfErr = stderr.read(buffererr, 0, buffererr.length);
						if(sizeOfIn > 0  && sizeOfIn != -1) {
							pipeHandler.onBufferData(bufferin, (long)sizeOfIn);
						}
						if(sizeOfErr > 0 && sizeOfErr != -1) {
							pipeHandler.onBufferData(buffererr, (long)sizeOfErr);
						}
						if((sizeOfErr < 0 && sizeOfErr == -1) && (sizeOfIn < 0  && sizeOfIn == -1)) {
							break;
						}
					}
				}
				catch(Exception e) {
				}
			}
			else {
				try {
					java.lang.StringBuilder sbn = new java.lang.StringBuilder();
					java.io.BufferedReader stdin = new java.io.BufferedReader(new java.io.InputStreamReader(np.process.getInputStream()));
					java.io.BufferedReader stderr = new java.io.BufferedReader(new java.io.InputStreamReader(np.process.getErrorStream()));
					while(true) {
						java.lang.String line1 = stdin.readLine();
						if(line1 != null) {
							sbn.append(line1);
						}
						java.lang.String line2 = stderr.readLine();
						if(line2 != null) {
							sbn.append(line2);
						}
						if(line1 == null && line2 == null) {
							break;
						}
					}
					stdin.close();
					stderr.close();
					output = sbn.toString();
				}
				catch(Exception e) {
				}
			}
			np.waitForExit();
			if(!(android.text.TextUtils.equals(output, null))) {
				java.lang.System.out.println(output);
			}
			np.close();
		}
		return((Process)np);
	}
}
