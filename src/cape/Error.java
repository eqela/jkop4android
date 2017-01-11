
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

public class Error implements StringObject
{
	public static Error forCode(java.lang.String code, java.lang.String detail) {
		return(new Error().setCode(code).setDetail(detail));
	}

	public static Error forCode(java.lang.String code) {
		return(cape.Error.forCode(code, null));
	}

	public static Error forMessage(java.lang.String message) {
		return(new Error().setMessage(message));
	}

	public static Error instance(java.lang.String code, java.lang.String message, java.lang.String detail) {
		return(new Error().setCode(code).setMessage(message).setDetail(detail));
	}

	public static Error instance(java.lang.String code, java.lang.String message) {
		return(cape.Error.instance(code, message, null));
	}

	public static Error instance(java.lang.String code) {
		return(cape.Error.instance(code, null));
	}

	public static Error set(Error error, java.lang.String code, java.lang.String message) {
		if(error == null) {
			return(null);
		}
		error.setCode(code);
		error.setMessage(message);
		return(error);
	}

	public static Error set(Error error, java.lang.String code) {
		return(cape.Error.set(error, code, null));
	}

	public static Error setErrorCode(Error error, java.lang.String code) {
		return(set(error, code, null));
	}

	public static Error setErrorMessage(Error error, java.lang.String message) {
		return(set(error, null, message));
	}

	public static boolean isError(java.lang.Object o) {
		if(o == null) {
			return(false);
		}
		if((o instanceof Error) == false) {
			return(false);
		}
		Error e = (Error)((o instanceof Error) ? o : null);
		if(cape.String.isEmpty(e.getCode()) && cape.String.isEmpty(e.getMessage())) {
			return(false);
		}
		return(true);
	}

	public static java.lang.String asString(Error error) {
		if(error == null) {
			return(null);
		}
		return(error.toString());
	}

	private java.lang.String code = null;
	private java.lang.String message = null;
	private java.lang.String detail = null;

	public Error clear() {
		code = null;
		message = null;
		detail = null;
		return(this);
	}

	public java.lang.String toStringWithDefault(java.lang.String defaultError) {
		if(cape.String.isEmpty(message) == false) {
			return(message);
		}
		if(cape.String.isEmpty(code) == false) {
			if(cape.String.isEmpty(detail) == false) {
				return((code + ":") + detail);
			}
			return(code);
		}
		if(cape.String.isEmpty(detail) == false) {
			return("Error with detail: " + detail);
		}
		return(defaultError);
	}

	public java.lang.String toString() {
		return(toStringWithDefault("Unknown Error"));
	}

	public java.lang.String getCode() {
		return(code);
	}

	public Error setCode(java.lang.String v) {
		code = v;
		return(this);
	}

	public java.lang.String getMessage() {
		return(message);
	}

	public Error setMessage(java.lang.String v) {
		message = v;
		return(this);
	}

	public java.lang.String getDetail() {
		return(detail);
	}

	public Error setDetail(java.lang.String v) {
		detail = v;
		return(this);
	}
}
