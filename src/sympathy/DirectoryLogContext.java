
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

package sympathy;

public class DirectoryLogContext implements cape.LoggingContext
{
	public static DirectoryLogContext create(cape.File logDir, java.lang.String logIdPrefix, boolean dbg) {
		DirectoryLogContext v = new DirectoryLogContext();
		v.setLogDir(logDir);
		v.setEnableDebugMessages(dbg);
		if(cape.String.isEmpty(logIdPrefix) == false) {
			v.setLogIdPrefix(logIdPrefix);
		}
		return(v);
	}

	public static DirectoryLogContext create(cape.File logDir, java.lang.String logIdPrefix) {
		return(sympathy.DirectoryLogContext.create(logDir, logIdPrefix, true));
	}

	public static DirectoryLogContext create(cape.File logDir) {
		return(sympathy.DirectoryLogContext.create(logDir, null));
	}

	private boolean enableDebugMessages = true;
	private cape.File logDir = null;
	private java.lang.String logIdPrefix = "messages";
	private cape.PrintWriter os = null;
	private java.lang.String currentLogIdName = null;
	private boolean alsoPrintOnConsole = false;

	public void logError(java.lang.String text) {
		message("ERROR", text);
	}

	public void logWarning(java.lang.String text) {
		message("WARNING", text);
	}

	public void logInfo(java.lang.String text) {
		message("INFO", text);
	}

	public void logDebug(java.lang.String text) {
		message("DEBUG", text);
	}

	public void message(java.lang.String type, java.lang.String text) {
		if((enableDebugMessages == false) && cape.String.equalsIgnoreCase("debug", type)) {
			return;
		}
		cape.DateTime dt = cape.DateTime.forNow();
		java.lang.String logTime = null;
		if(dt != null) {
			logTime = ((((((((((cape.String.forInteger(dt.getYear()) + "-") + cape.String.forIntegerWithPadding(dt.getMonth(), 2)) + "-") + cape.String.forIntegerWithPadding(dt.getDayOfMonth(), 2)) + " ") + cape.String.forIntegerWithPadding(dt.getHours(), 2)) + ":") + cape.String.forIntegerWithPadding(dt.getMinutes(), 2)) + ":") + cape.String.forIntegerWithPadding(dt.getSeconds(), 2)) + " UTC";
		}
		else {
			logTime = "DATE/TIME";
		}
		java.lang.String logLine = (((("[" + cape.String.padToLength(type, 7)) + "] [") + logTime) + "]: ") + text;
		if(logDir != null) {
			java.lang.String logIdName = null;
			if(dt != null) {
				logIdName = ((((logIdPrefix + "_") + cape.String.forInteger(dt.getYear())) + cape.String.forIntegerWithPadding(dt.getMonth(), 2)) + cape.String.forIntegerWithPadding(dt.getDayOfMonth(), 2)) + ".log";
			}
			else {
				logIdName = logIdPrefix + ".log";
			}
			if((os == null) || !(android.text.TextUtils.equals(currentLogIdName, logIdName))) {
				currentLogIdName = logIdName;
				os = cape.PrintWriterWrapper.forWriter((cape.Writer)logDir.entry(currentLogIdName).append());
				if((os == null) && (logDir.isDirectory() == false)) {
					logDir.createDirectoryRecursive();
					os = cape.PrintWriterWrapper.forWriter((cape.Writer)logDir.entry(currentLogIdName).append());
				}
			}
			if(os != null) {
				if(os.println(logLine) == false) {
					return;
				}
				if(os instanceof cape.FlushableWriter) {
					((cape.FlushableWriter)os).flush();
				}
			}
		}
		if(alsoPrintOnConsole) {
			System.out.println(logLine);
		}
	}

	protected void finalize() throws java.lang.Throwable {
		super.finalize();
		if(os != null) {
			if(os instanceof cape.Closable) {
				((cape.Closable)os).close();
			}
			os = null;
		}
	}

	public boolean getEnableDebugMessages() {
		return(enableDebugMessages);
	}

	public DirectoryLogContext setEnableDebugMessages(boolean v) {
		enableDebugMessages = v;
		return(this);
	}

	public cape.File getLogDir() {
		return(logDir);
	}

	public DirectoryLogContext setLogDir(cape.File v) {
		logDir = v;
		return(this);
	}

	public java.lang.String getLogIdPrefix() {
		return(logIdPrefix);
	}

	public DirectoryLogContext setLogIdPrefix(java.lang.String v) {
		logIdPrefix = v;
		return(this);
	}

	public boolean getAlsoPrintOnConsole() {
		return(alsoPrintOnConsole);
	}

	public DirectoryLogContext setAlsoPrintOnConsole(boolean v) {
		alsoPrintOnConsole = v;
		return(this);
	}
}
