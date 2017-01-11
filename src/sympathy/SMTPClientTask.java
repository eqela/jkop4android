
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

public class SMTPClientTask implements cape.Runnable
{
	private cape.LoggingContext ctx = null;
	private URL server = null;
	private SMTPMessage msg = null;
	private java.lang.String serverAddress = null;
	private SMTPSenderListener listener = null;
	private SMTPSender sender = null;

	public void run() {
		SMTPClientResult r = null;
		if(msg == null) {
			r = sympathy.SMTPClientResult.forError("No message was given to SMTPClientTask", msg);
		}
		else {
			r = sympathy.SMTPClient.sendMessage(msg, server, serverAddress, ctx);
		}
		if(r == null) {
			r = sympathy.SMTPClientResult.forError("Unknown error", msg);
		}
		if(sender != null) {
			sender.onSendEnd();
		}
		if(listener == null) {
			return;
		}
		listener.onSMTPSendComplete(r.getMessage(), r);
	}

	public cape.LoggingContext getCtx() {
		return(ctx);
	}

	public SMTPClientTask setCtx(cape.LoggingContext v) {
		ctx = v;
		return(this);
	}

	public URL getServer() {
		return(server);
	}

	public SMTPClientTask setServer(URL v) {
		server = v;
		return(this);
	}

	public SMTPMessage getMsg() {
		return(msg);
	}

	public SMTPClientTask setMsg(SMTPMessage v) {
		msg = v;
		return(this);
	}

	public java.lang.String getServerAddress() {
		return(serverAddress);
	}

	public SMTPClientTask setServerAddress(java.lang.String v) {
		serverAddress = v;
		return(this);
	}

	public SMTPSenderListener getListener() {
		return(listener);
	}

	public SMTPClientTask setListener(SMTPSenderListener v) {
		listener = v;
		return(this);
	}

	public SMTPSender getSender() {
		return(sender);
	}

	public SMTPClientTask setSender(SMTPSender v) {
		sender = v;
		return(this);
	}
}
