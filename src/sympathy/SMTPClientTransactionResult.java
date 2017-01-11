
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

public class SMTPClientTransactionResult
{
	public static SMTPClientTransactionResult forError(java.lang.String error) {
		return(new SMTPClientTransactionResult().setStatus(false).setErrorMessage(error));
	}

	public static SMTPClientTransactionResult forNetworkError() {
		return(forError("Network communications error"));
	}

	public static SMTPClientTransactionResult forSuccess() {
		return(new SMTPClientTransactionResult().setStatus(true));
	}

	private boolean status = false;
	private java.lang.String errorMessage = null;
	private java.lang.String domain = null;
	private java.lang.String server = null;
	private cape.DynamicVector recipients = null;

	public boolean getStatus() {
		return(status);
	}

	public SMTPClientTransactionResult setStatus(boolean v) {
		status = v;
		return(this);
	}

	public java.lang.String getErrorMessage() {
		return(errorMessage);
	}

	public SMTPClientTransactionResult setErrorMessage(java.lang.String v) {
		errorMessage = v;
		return(this);
	}

	public java.lang.String getDomain() {
		return(domain);
	}

	public SMTPClientTransactionResult setDomain(java.lang.String v) {
		domain = v;
		return(this);
	}

	public java.lang.String getServer() {
		return(server);
	}

	public SMTPClientTransactionResult setServer(java.lang.String v) {
		server = v;
		return(this);
	}

	public cape.DynamicVector getRecipients() {
		return(recipients);
	}

	public SMTPClientTransactionResult setRecipients(cape.DynamicVector v) {
		recipients = v;
		return(this);
	}
}
