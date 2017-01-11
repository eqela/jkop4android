
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

/**
 * The ProcessLauncher class provides a mechanism for starting and controlling
 * additional processes.
 */

public class ProcessLauncher
{
	private static class MyStringPipeHandler implements BufferDataReceiver
	{
		private StringBuilder builder = null;
		private java.lang.String encoding = null;

		public MyStringPipeHandler() {
			encoding = "UTF-8";
		}

		public void onBufferData(byte[] data, long size) {
			if(((builder == null) || (data == null)) || (size < 1)) {
				return;
			}
			java.lang.String str = cape.String.forBuffer(cape.Buffer.getSubBuffer(data, (long)0, size), encoding);
			if(android.text.TextUtils.equals(str, null)) {
				return;
			}
			builder.append(str);
		}

		public StringBuilder getBuilder() {
			return(builder);
		}

		public MyStringPipeHandler setBuilder(StringBuilder v) {
			builder = v;
			return(this);
		}

		public java.lang.String getEncoding() {
			return(encoding);
		}

		public MyStringPipeHandler setEncoding(java.lang.String v) {
			encoding = v;
			return(this);
		}
	}

	private static class MyBufferPipeHandler implements BufferDataReceiver
	{
		private byte[] data = null;

		public void onBufferData(byte[] newData, long size) {
			data = cape.Buffer.append(data, newData, size);
		}

		public byte[] getData() {
			return(data);
		}

		public MyBufferPipeHandler setData(byte[] v) {
			data = v;
			return(this);
		}
	}

	private static class QuietPipeHandler implements BufferDataReceiver
	{
		public void onBufferData(byte[] data, long size) {
		}
	}

	public static ProcessLauncher forSelf() {
		File exe = cape.CurrentProcess.getExecutableFile();
		if(exe == null) {
			return(null);
		}
		ProcessLauncher v = new ProcessLauncher();
		v.setFile(exe);
		return(v);
	}

	/**
	 * Creates a launcher for the given executable file. If the file does not exist,
	 * this method returns a null object instead.
	 */

	public static ProcessLauncher forFile(File file, java.lang.String[] params) {
		if((file == null) || (file.isFile() == false)) {
			return(null);
		}
		ProcessLauncher v = new ProcessLauncher();
		v.setFile(file);
		if(params != null) {
			int n = 0;
			int m = params.length;
			for(n = 0 ; n < m ; n++) {
				java.lang.String param = params[n];
				if(param != null) {
					v.addToParams(param);
				}
			}
		}
		return(v);
	}

	public static ProcessLauncher forFile(File file) {
		return(cape.ProcessLauncher.forFile(file, null));
	}

	/**
	 * Creates a process launcher for the given command. The command can either be a
	 * full or relative path to an executable file or, if not, a matching executable
	 * file will be searched for in the PATH environment variable (or through other
	 * applicable standard means on the given platform), and an appropriately
	 * configured ProcessLauncher object will be returned. However, if the given
	 * command is not found, this method returns null.
	 */

	public static ProcessLauncher forCommand(java.lang.String command, java.lang.String[] params) {
		if(cape.String.isEmpty(command)) {
			return(null);
		}
		File file = null;
		if(cape.String.indexOf(command, cape.Environment.getPathSeparator()) >= 0) {
			file = cape.FileInstance.forPath(command);
		}
		else {
			file = cape.Environment.findCommand(command);
		}
		return(forFile(file, params));
	}

	public static ProcessLauncher forCommand(java.lang.String command) {
		return(cape.ProcessLauncher.forCommand(command, null));
	}

	/**
	 * Creates a new process launcher object for the given string, which includes a
	 * complete command line for executing the process, including the command itself
	 * and all the parameters, delimited with spaces. If parameters will need to
	 * contain space as part of their value, those parameters can be enclosed in double
	 * quotes. This method will return null if the command does not exist and/or is not
	 * found.
	 */

	public static ProcessLauncher forString(java.lang.String str) {
		if(cape.String.isEmpty(str)) {
			return(null);
		}
		java.util.ArrayList<java.lang.String> arr = cape.String.quotedStringToVector(str, ' ');
		if((arr == null) || (cape.Vector.getSize(arr) < 1)) {
			return(null);
		}
		int vsz = cape.Vector.getSize(arr);
		java.lang.String cmd = arr.get(0);
		java.lang.String[] params = null;
		int paramCount = vsz - 1;
		if(paramCount > 0) {
			params = new java.lang.String[paramCount];
			for(int n = 1 ; n < vsz ; n++) {
				params[n - 1] = arr.get(n);
			}
		}
		return(forCommand(cmd, params));
	}

	private File file = null;
	private java.util.ArrayList<java.lang.String> params = null;
	private java.util.HashMap<java.lang.String,java.lang.String> env = null;
	private File cwd = null;
	private int uid = -1;
	private int gid = -1;
	private boolean trapSigint = true;
	private boolean replaceSelf = false;
	private boolean pipePty = false;
	private boolean startGroup = false;
	private boolean noCmdWindow = false;
	private StringBuilder errorBuffer = null;

	public ProcessLauncher() {
		params = new java.util.ArrayList<java.lang.String>();
		env = new java.util.HashMap<java.lang.String,java.lang.String>();
	}

	private void appendProperParam(StringBuilder sb, java.lang.String p) {
		boolean noQuotes = false;
		if(cape.OS.isWindows()) {
			int rc = cape.String.lastIndexOf(p, ' ');
			if(rc < 0) {
				noQuotes = true;
			}
		}
		sb.append(' ');
		if(noQuotes) {
			sb.append(p);
		}
		else {
			sb.append('\"');
			sb.append(p);
			sb.append('\"');
		}
	}

	/**
	 * Produces a string representation of this command with the command itself,
	 * parameters and environment variables included.
	 */

	public java.lang.String toString(boolean includeEnv) {
		StringBuilder sb = new StringBuilder();
		if(includeEnv) {
			java.util.ArrayList<java.lang.String> keys = cape.Map.getKeys(env);
			if(keys != null) {
				int n = 0;
				int m = keys.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.String key = keys.get(n);
					if(key != null) {
						sb.append(key);
						sb.append("=");
						sb.append(env.get(key));
						sb.append(" ");
					}
				}
			}
		}
		sb.append("\"");
		if(file != null) {
			sb.append(file.getPath());
		}
		sb.append("\"");
		if(params != null) {
			int n2 = 0;
			int m2 = params.size();
			for(n2 = 0 ; n2 < m2 ; n2++) {
				java.lang.String p = params.get(n2);
				if(p != null) {
					appendProperParam(sb, p);
				}
			}
		}
		return(sb.toString());
	}

	public java.lang.String toString() {
		return(toString(true));
	}

	public ProcessLauncher addToParams(java.lang.String arg) {
		if(!(android.text.TextUtils.equals(arg, null))) {
			if(params == null) {
				params = new java.util.ArrayList<java.lang.String>();
			}
			params.add(arg);
		}
		return(this);
	}

	public ProcessLauncher addToParams(File file) {
		if(file != null) {
			addToParams(file.getPath());
		}
		return(this);
	}

	public ProcessLauncher addToParams(java.util.ArrayList<java.lang.String> params) {
		if(params != null) {
			int n = 0;
			int m = params.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.String s = params.get(n);
				if(s != null) {
					addToParams(s);
				}
			}
		}
		return(this);
	}

	public void setEnvVariable(java.lang.String key, java.lang.String val) {
		if(!(android.text.TextUtils.equals(key, null))) {
			if(env == null) {
				env = new java.util.HashMap<java.lang.String,java.lang.String>();
			}
			env.put(key, val);
		}
	}

	private Process startProcess(boolean wait, BufferDataReceiver pipeHandler, boolean withIO) {
		return(cape.ProcessLauncherJava.startProcess(this, wait, pipeHandler));
	}

	private Process startProcess(boolean wait, BufferDataReceiver pipeHandler) {
		return(startProcess(wait, pipeHandler, false));
	}

	public Process start() {
		return(startProcess(false, null));
	}

	static public ProcessWithIO objectAsCapeProcessWithIO(java.lang.Object o) {
		if(o instanceof ProcessWithIO) {
			return((ProcessWithIO)o);
		}
		return(null);
	}

	public ProcessWithIO startWithIO() {
		return(objectAsCapeProcessWithIO((java.lang.Object)startProcess(false, null, true)));
	}

	public int execute() {
		Process cp = startProcess(true, null);
		if(cp == null) {
			return(-1);
		}
		return(cp.getExitStatus());
	}

	public int executeSilent() {
		Process cp = startProcess(true, (BufferDataReceiver)new QuietPipeHandler());
		if(cp == null) {
			return(-1);
		}
		return(cp.getExitStatus());
	}

	public int executeToStringBuilder(StringBuilder output) {
		MyStringPipeHandler msp = new MyStringPipeHandler();
		msp.setBuilder(output);
		Process cp = startProcess(true, (BufferDataReceiver)msp);
		if(cp == null) {
			return(-1);
		}
		return(cp.getExitStatus());
	}

	public java.lang.String executeToString() {
		StringBuilder sb = new StringBuilder();
		if(executeToStringBuilder(sb) < 0) {
			return(null);
		}
		return(sb.toString());
	}

	public byte[] executeToBuffer() {
		MyBufferPipeHandler ph = new MyBufferPipeHandler();
		Process cp = startProcess(true, (BufferDataReceiver)ph);
		if(cp == null) {
			return(null);
		}
		return(ph.getData());
	}

	public int executeToPipe(BufferDataReceiver pipeHandler) {
		Process cp = startProcess(true, pipeHandler);
		if(cp == null) {
			return(-1);
		}
		return(cp.getExitStatus());
	}

	public File getFile() {
		return(file);
	}

	public ProcessLauncher setFile(File v) {
		file = v;
		return(this);
	}

	public java.util.ArrayList<java.lang.String> getParams() {
		return(params);
	}

	public ProcessLauncher setParams(java.util.ArrayList<java.lang.String> v) {
		params = v;
		return(this);
	}

	public java.util.HashMap<java.lang.String,java.lang.String> getEnv() {
		return(env);
	}

	public ProcessLauncher setEnv(java.util.HashMap<java.lang.String,java.lang.String> v) {
		env = v;
		return(this);
	}

	public File getCwd() {
		return(cwd);
	}

	public ProcessLauncher setCwd(File v) {
		cwd = v;
		return(this);
	}

	public int getUid() {
		return(uid);
	}

	public ProcessLauncher setUid(int v) {
		uid = v;
		return(this);
	}

	public int getGid() {
		return(gid);
	}

	public ProcessLauncher setGid(int v) {
		gid = v;
		return(this);
	}

	public boolean getTrapSigint() {
		return(trapSigint);
	}

	public ProcessLauncher setTrapSigint(boolean v) {
		trapSigint = v;
		return(this);
	}

	public boolean getReplaceSelf() {
		return(replaceSelf);
	}

	public ProcessLauncher setReplaceSelf(boolean v) {
		replaceSelf = v;
		return(this);
	}

	public boolean getPipePty() {
		return(pipePty);
	}

	public ProcessLauncher setPipePty(boolean v) {
		pipePty = v;
		return(this);
	}

	public boolean getStartGroup() {
		return(startGroup);
	}

	public ProcessLauncher setStartGroup(boolean v) {
		startGroup = v;
		return(this);
	}

	public boolean getNoCmdWindow() {
		return(noCmdWindow);
	}

	public ProcessLauncher setNoCmdWindow(boolean v) {
		noCmdWindow = v;
		return(this);
	}

	public StringBuilder getErrorBuffer() {
		return(errorBuffer);
	}

	public ProcessLauncher setErrorBuffer(StringBuilder v) {
		errorBuffer = v;
		return(this);
	}
}
