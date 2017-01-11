
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

public class SMTPClient
{
	static public TCPSocket connect(java.lang.String server, int port, cape.LoggingContext ctx) {
		if(cape.String.isEmpty(server) || (port < 1)) {
			return(null);
		}
		java.lang.String address = server;
		DNSResolver dns = sympathy.DNSResolver.create();
		if(dns != null) {
			address = dns.getIPAddress(server, ctx);
			if(cape.String.isEmpty(address)) {
				cape.Log.error(ctx, ("SMTPClient: Could not resolve SMTP server address: `" + server) + "'");
				return(null);
			}
		}
		cape.Log.debug(ctx, ((("SMTPClient: Connecting to SMTP server `" + address) + ":") + cape.String.forInteger(port)) + "' ..");
		TCPSocket v = sympathy.TCPSocket.createAndConnect(address, port);
		if(v != null) {
			cape.Log.debug(ctx, ((("SMTPClient: Connected to SMTP server `" + address) + ":") + cape.String.forInteger(port)) + "' ..");
		}
		else {
			cape.Log.error(ctx, ((("SMTPClient: FAILED connection to SMTP server `" + address) + ":") + cape.String.forInteger(port)) + "' ..");
		}
		return(v);
	}

	static public boolean writeLine(cape.PrintWriter ops, java.lang.String str) {
		return(ops.print(str + "\r\n"));
	}

	static public java.lang.String communicate(cape.PrintReader ins, java.lang.String expectCode) {
		cape.StringBuilder sb = new cape.StringBuilder();
		java.lang.String line = ins.readLine();
		if(cape.String.isEmpty(line) == false) {
			sb.append(line);
		}
		while((cape.String.isEmpty(line) == false) && (cape.String.getChar(line, 3) == '-')) {
			line = ins.readLine();
			if(cape.String.isEmpty(line) == false) {
				sb.append(line);
			}
		}
		if((cape.String.isEmpty(line) == false) && (cape.String.getChar(line, 3) == ' ')) {
			if(cape.String.isEmpty(expectCode)) {
				return(null);
			}
			java.lang.String rc = cape.String.getSubString(line, 0, 3);
			java.util.ArrayList<java.lang.String> array = cape.String.split(expectCode, '|');
			if(array != null) {
				int n = 0;
				int m = array.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.String cc = array.get(n);
					if(cc != null) {
						if(cape.String.equals(cc, rc)) {
							return(null);
						}
					}
				}
			}
		}
		java.lang.String v = sb.toString();
		if(cape.String.isEmpty(v)) {
			v = "XXX Unknown SMTP server error response";
		}
		return(v);
	}

	static public java.lang.String encode(java.lang.String enc) {
		if(cape.String.isEmpty(enc)) {
			return(null);
		}
		return(capex.Base64Encoder.encode(cape.String.toUTF8Buffer(enc)));
	}

	static public java.lang.String rcptAsEmailAddress(java.lang.String ss) {
		if(cape.String.isEmpty(ss)) {
			return(ss);
		}
		int b = cape.String.getIndexOf(ss, '<');
		if(b < 0) {
			return(ss);
		}
		int e = cape.String.getIndexOf(ss, '>');
		if(e < 0) {
			return(ss);
		}
		return(cape.String.getSubString(ss, b + 1, (e - b) - 1));
	}

	static public java.lang.String resolveMXServerForDomain(java.lang.String domain) {
		DNSResolver dns = sympathy.DNSResolver.instance();
		if(dns == null) {
			return(null);
		}
		cape.DynamicVector rcs = dns.getNSRecords(domain, "MX", null);
		if((rcs == null) || (rcs.getSize() < 1)) {
			return(null);
		}
		java.lang.String v = null;
		int pr = 0;
		java.util.ArrayList<java.lang.Object> array = rcs.toVector();
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.Object tmx = array.get(n);
				DNSRecordMX mx = null;
				if(tmx instanceof DNSRecordMX) {
					mx = (DNSRecordMX)tmx;
				}
				if(mx != null) {
					int p = mx.getPriority();
					if((android.text.TextUtils.equals(v, null)) || (p < pr)) {
						pr = p;
						v = mx.getAddress();
					}
				}
			}
		}
		return(v);
	}

	public static SMTPClientResult sendMessage(SMTPMessage msg, URL server, java.lang.String serverName, cape.LoggingContext ctx) {
		if(msg == null) {
			return(sympathy.SMTPClientResult.forMessage(msg).addTransaction(sympathy.SMTPClientTransactionResult.forError("No message")));
		}
		cape.DynamicVector rcpts = msg.getAllRcpts();
		if(server != null) {
			SMTPClientTransactionResult t = executeTransaction(msg, server, rcpts, serverName, ctx);
			if(t != null) {
				t.setServer(server.getHost());
				t.setRecipients(rcpts);
			}
			return(sympathy.SMTPClientResult.forMessage(msg).addTransaction(t));
		}
		SMTPClientResult r = sympathy.SMTPClientResult.forMessage(msg);
		cape.DynamicMap servers = new cape.DynamicMap();
		java.util.ArrayList<java.lang.Object> array = rcpts.toVector();
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.Object trcpt = array.get(n);
				java.lang.String rcpt = null;
				if(trcpt instanceof java.lang.String) {
					rcpt = (java.lang.String)trcpt;
				}
				if(rcpt != null) {
					java.lang.String em = rcptAsEmailAddress(rcpt);
					if(cape.String.isEmpty(em)) {
						r.addTransaction(sympathy.SMTPClientTransactionResult.forError(("Invalid recipient address: `" + rcpt) + "'"));
						break;
					}
					int at = cape.String.getIndexOf(em, '@');
					if(at < 0) {
						r.addTransaction(sympathy.SMTPClientTransactionResult.forError(("Invalid recipient address: `" + rcpt) + "'"));
						break;
					}
					java.lang.String sa = cape.String.getSubString(em, at + 1);
					if(cape.String.isEmpty(sa)) {
						r.addTransaction(sympathy.SMTPClientTransactionResult.forError(("Invalid recipient address: `" + rcpt) + "'"));
						break;
					}
					java.lang.Object tss = servers.get(sa);
					cape.DynamicVector ss = null;
					if(tss instanceof cape.DynamicVector) {
						ss = (cape.DynamicVector)tss;
					}
					if(ss == null) {
						ss = new cape.DynamicVector();
						servers.set(sa, (java.lang.Object)ss);
					}
					ss.append((java.lang.Object)rcpt);
				}
			}
		}
		cape.Iterator<java.lang.String> itr = servers.iterateKeys();
		while(itr != null) {
			java.lang.String domain = itr.next();
			if(cape.String.isEmpty(domain)) {
				break;
			}
			java.lang.String ds = resolveMXServerForDomain(domain);
			if(cape.String.isEmpty(ds)) {
				r.addTransaction(sympathy.SMTPClientTransactionResult.forError(("Unable to determine mail server for `" + domain) + "'"));
			}
			else {
				cape.Log.debug(ctx, ((("SMTP server for domain `" + domain) + "': `") + ds) + "'");
				java.lang.Object ttrcpts = servers.get(domain);
				cape.DynamicVector trcpts = null;
				if(ttrcpts instanceof cape.DynamicVector) {
					trcpts = (cape.DynamicVector)ttrcpts;
				}
				SMTPClientTransactionResult t = executeTransaction(msg, sympathy.URL.forString("smtp://" + ds), trcpts, serverName, ctx);
				if(t != null) {
					t.setDomain(domain);
					t.setServer(ds);
					t.setRecipients(trcpts);
				}
				r.addTransaction(t);
			}
		}
		cape.DynamicVector vt = r.getTransactions();
		if((vt == null) || (vt.getSize() < 1)) {
			r.addTransaction(sympathy.SMTPClientTransactionResult.forError("Unknown error in SMTPClient"));
		}
		return(r);
	}

	public static SMTPClientResult sendMessage(SMTPMessage msg, URL server, java.lang.String serverName) {
		return(sympathy.SMTPClient.sendMessage(msg, server, serverName, null));
	}

	static public SMTPClientTransactionResult executeTransaction(SMTPMessage msg, URL server, cape.DynamicVector rcpts, java.lang.String serverName, cape.LoggingContext ctx) {
		URL url = server;
		if(url == null) {
			return(sympathy.SMTPClientTransactionResult.forError("No server URL"));
		}
		ConnectedSocket socket = null;
		java.lang.String scheme = url.getScheme();
		java.lang.String host = url.getHost();
		int port = url.getPortInt();
		int n = 0;
		for(n = 0 ; n < 3 ; n++) {
			if(cape.String.equals("smtp", scheme) || cape.String.equals("smtp+tls", scheme)) {
				if(port < 1) {
					port = 25;
				}
				socket = (ConnectedSocket)connect(host, port, ctx);
			}
			else if(cape.String.equals("smtp+ssl", scheme)) {
				if(port < 1) {
					port = 465;
				}
				TCPSocket ts = connect(host, port, ctx);
				if(ts != null) {
					socket = (ConnectedSocket)sympathy.SSLSocket.forClient((ConnectedSocket)ts, host);
					if(socket == null) {
						return(sympathy.SMTPClientTransactionResult.forError("Failed to start SSL"));
					}
				}
			}
			else {
				return(sympathy.SMTPClientTransactionResult.forError(("SMTPClient: Unknown SMTP URI scheme `" + scheme) + "'"));
			}
			if(socket != null) {
				break;
			}
			cape.Log.debug(ctx, ((("Failed to connect to SMTP server `" + host) + ":") + cape.String.forInteger(port)) + "'. Waiting to retry ..");
			cape.Log.error(ctx, "FIXME - No sleep implementation yet, quitting instead!");
			break;
		}
		if(socket == null) {
			return(sympathy.SMTPClientTransactionResult.forError(((("Unable to connect to SMTP server `" + host) + ":") + cape.String.forInteger(port)) + "'"));
		}
		cape.PrintWriter ops = cape.PrintWriterWrapper.forWriter((cape.Writer)socket);
		cape.PrintReader ins = new cape.PrintReader((cape.Reader)socket);
		if((ops == null) || (ins == null)) {
			return(sympathy.SMTPClientTransactionResult.forError("Unable to establish SMTP I/O streams"));
		}
		java.lang.String err = null;
		if(!(android.text.TextUtils.equals(err = communicate(ins, "220"), null))) {
			return(sympathy.SMTPClientTransactionResult.forError(err));
		}
		java.lang.String sn = serverName;
		if(cape.String.isEmpty(sn)) {
			sn = "eq.net.smtpclient";
		}
		if(writeLine(ops, "EHLO " + sn) == false) {
			return(sympathy.SMTPClientTransactionResult.forNetworkError());
		}
		if(!(android.text.TextUtils.equals(err = communicate(ins, "250"), null))) {
			return(sympathy.SMTPClientTransactionResult.forError(err));
		}
		if(cape.String.equals("smtp+tls", scheme)) {
			if(writeLine(ops, "STARTTLS") == false) {
				return(sympathy.SMTPClientTransactionResult.forNetworkError());
			}
			if(!(android.text.TextUtils.equals(err = communicate(ins, "220"), null))) {
				return(sympathy.SMTPClientTransactionResult.forError(err));
			}
			ops = null;
			ins = null;
			socket = (ConnectedSocket)sympathy.SSLSocket.forClient(socket, host);
			if(socket == null) {
				return(sympathy.SMTPClientTransactionResult.forError("Failed to start SSL"));
			}
			ops = cape.PrintWriterWrapper.forWriter((cape.Writer)socket);
			ins = new cape.PrintReader((cape.Reader)socket);
		}
		java.lang.String username = url.getUsername();
		java.lang.String password = url.getPassword();
		if(cape.String.isEmpty(username) == false) {
			if(writeLine(ops, "AUTH login") == false) {
				return(sympathy.SMTPClientTransactionResult.forNetworkError());
			}
			if(!(android.text.TextUtils.equals(err = communicate(ins, "334"), null))) {
				return(sympathy.SMTPClientTransactionResult.forError(err));
			}
			if(writeLine(ops, encode(username)) == false) {
				return(sympathy.SMTPClientTransactionResult.forNetworkError());
			}
			if(!(android.text.TextUtils.equals(err = communicate(ins, "334"), null))) {
				return(sympathy.SMTPClientTransactionResult.forError(err));
			}
			if(writeLine(ops, encode(password)) == false) {
				return(sympathy.SMTPClientTransactionResult.forNetworkError());
			}
			if(!(android.text.TextUtils.equals(err = communicate(ins, "235|530"), null))) {
				return(sympathy.SMTPClientTransactionResult.forError(err));
			}
		}
		if(writeLine(ops, ("MAIL FROM:<" + msg.getMyAddress()) + ">") == false) {
			return(sympathy.SMTPClientTransactionResult.forNetworkError());
		}
		if(!(android.text.TextUtils.equals(err = communicate(ins, "250"), null))) {
			return(sympathy.SMTPClientTransactionResult.forError(err));
		}
		if(rcpts != null) {
			java.util.ArrayList<java.lang.Object> array = rcpts.toVector();
			if(array != null) {
				int n2 = 0;
				int m = array.size();
				for(n2 = 0 ; n2 < m ; n2++) {
					java.lang.Object trcpt = array.get(n2);
					java.lang.String rcpt = null;
					if(trcpt instanceof java.lang.String) {
						rcpt = (java.lang.String)trcpt;
					}
					if(rcpt != null) {
						if(writeLine(ops, ("RCPT TO:<" + rcptAsEmailAddress(rcpt)) + ">") == false) {
							return(sympathy.SMTPClientTransactionResult.forNetworkError());
						}
						if(!(android.text.TextUtils.equals(err = communicate(ins, "250"), null))) {
							return(sympathy.SMTPClientTransactionResult.forError(err));
						}
					}
				}
			}
		}
		if(writeLine(ops, "DATA") == false) {
			return(sympathy.SMTPClientTransactionResult.forNetworkError());
		}
		if(!(android.text.TextUtils.equals(err = communicate(ins, "354"), null))) {
			return(sympathy.SMTPClientTransactionResult.forError(err));
		}
		if(cape.String.isEmpty(msg.getMessageID())) {
			msg.generateMessageID(sn);
		}
		java.lang.String bod = msg.getMessageBody();
		cape.Log.debug(ctx, ("Sending message body: `" + bod) + "'");
		if(ops.print(bod) == false) {
			return(sympathy.SMTPClientTransactionResult.forNetworkError());
		}
		if(ops.print("\r\n.\r\n") == false) {
			return(sympathy.SMTPClientTransactionResult.forNetworkError());
		}
		if(!(android.text.TextUtils.equals(err = communicate(ins, "250"), null))) {
			return(sympathy.SMTPClientTransactionResult.forError(err));
		}
		if(writeLine(ops, "QUIT") == false) {
			return(sympathy.SMTPClientTransactionResult.forNetworkError());
		}
		return(sympathy.SMTPClientTransactionResult.forSuccess());
	}
}
