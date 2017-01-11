
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

public class PosixEnvironment
{
	public static class PosixUser
	{
		private java.lang.String pwName = null;
		private int pwUid = 0;
		private int pwGid = 0;
		private java.lang.String pwGecos = null;
		private java.lang.String pwDir = null;
		private java.lang.String pwShell = null;

		public java.lang.String getPwName() {
			return(pwName);
		}

		public PosixUser setPwName(java.lang.String v) {
			pwName = v;
			return(this);
		}

		public int getPwUid() {
			return(pwUid);
		}

		public PosixUser setPwUid(int v) {
			pwUid = v;
			return(this);
		}

		public int getPwGid() {
			return(pwGid);
		}

		public PosixUser setPwGid(int v) {
			pwGid = v;
			return(this);
		}

		public java.lang.String getPwGecos() {
			return(pwGecos);
		}

		public PosixUser setPwGecos(java.lang.String v) {
			pwGecos = v;
			return(this);
		}

		public java.lang.String getPwDir() {
			return(pwDir);
		}

		public PosixUser setPwDir(java.lang.String v) {
			pwDir = v;
			return(this);
		}

		public java.lang.String getPwShell() {
			return(pwShell);
		}

		public PosixUser setPwShell(java.lang.String v) {
			pwShell = v;
			return(this);
		}
	}

	public static PosixUser getpwnam(java.lang.String username) {
		return(null);
	}

	public static PosixUser getpwuid(int uid) {
		return(null);
	}

	public static boolean setuid(int gid) {
		return(false);
	}

	public static boolean setgid(int gid) {
		return(false);
	}

	public static boolean seteuid(int uid) {
		return(false);
	}

	public static boolean setegid(int gid) {
		return(false);
	}

	public static int getuid() {
		return(-1);
	}

	public static int geteuid() {
		return(-1);
	}

	public static int getgid() {
		return(-1);
	}

	public static int getegid() {
		return(-1);
	}
}
