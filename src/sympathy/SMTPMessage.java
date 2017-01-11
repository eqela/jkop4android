
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

public class SMTPMessage
{
	private cape.DynamicVector rcptsTo = null;
	private cape.DynamicVector rcptsCC = null;
	private cape.DynamicVector rcptsBCC = null;
	private java.lang.String replyTo = null;
	private java.lang.String subject = null;
	private java.lang.String contentType = null;
	private java.lang.String text = null;
	private java.lang.String myName = null;
	private java.lang.String myAddress = null;
	private java.lang.String messageBody = null;
	private java.lang.String messageID = null;
	private java.lang.String date = null;
	private cape.DynamicVector excludeAddresses = null;
	private static int counter = 0;

	public SMTPMessage() {
		date = capex.VerboseDateTimeString.forNow();
	}

	private void onChanged() {
		messageBody = null;
	}

	public SMTPMessage generateMessageID(java.lang.String host) {
		messageID = (((((cape.String.forInteger((int)cape.SystemClock.asSeconds()) + "-") + cape.String.forInteger((int)new cape.Random().nextInt((int)1000000))) + "-") + cape.String.forInteger(counter)) + "@") + host;
		counter++;
		onChanged();
		return(this);
	}

	public java.lang.String getDate() {
		return(date);
	}

	public java.lang.String getReplyTo() {
		return(replyTo);
	}

	public SMTPMessage setDate(java.lang.String date) {
		this.date = date;
		onChanged();
		return(this);
	}

	public SMTPMessage setMessageID(java.lang.String id) {
		messageID = id;
		onChanged();
		return(this);
	}

	public SMTPMessage setReplyTo(java.lang.String v) {
		replyTo = v;
		onChanged();
		return(this);
	}

	public java.lang.String getMessageID() {
		return(messageID);
	}

	private boolean isExcludedAddress(java.lang.String add) {
		if((excludeAddresses == null) || (excludeAddresses.getSize() < 1)) {
			return(false);
		}
		java.util.ArrayList<java.lang.Object> array = excludeAddresses.toVector();
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.Object tea = array.get(n);
				java.lang.String ea = null;
				if(tea instanceof java.lang.String) {
					ea = (java.lang.String)tea;
				}
				if(ea != null) {
					if(cape.String.equals(ea, add)) {
						return(true);
					}
				}
			}
		}
		return(false);
	}

	public cape.DynamicVector getAllRcpts() {
		cape.DynamicVector rcpts = new cape.DynamicVector();
		if(rcptsTo != null) {
			java.util.ArrayList<java.lang.Object> array = rcptsTo.toVector();
			if(array != null) {
				int n = 0;
				int m = array.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.Object tr = array.get(n);
					java.lang.String r = null;
					if(tr instanceof java.lang.String) {
						r = (java.lang.String)tr;
					}
					if(r != null) {
						if(isExcludedAddress(r)) {
							continue;
						}
						rcpts.append((java.lang.Object)r);
					}
				}
			}
		}
		if(rcptsCC != null) {
			java.util.ArrayList<java.lang.Object> array2 = rcptsCC.toVector();
			if(array2 != null) {
				int n2 = 0;
				int m2 = array2.size();
				for(n2 = 0 ; n2 < m2 ; n2++) {
					java.lang.Object tr2 = array2.get(n2);
					java.lang.String r = null;
					if(tr2 instanceof java.lang.String) {
						r = (java.lang.String)tr2;
					}
					if(r != null) {
						if(isExcludedAddress(r)) {
							continue;
						}
						rcpts.append((java.lang.Object)r);
					}
				}
			}
		}
		if(rcptsBCC != null) {
			java.util.ArrayList<java.lang.Object> array3 = rcptsBCC.toVector();
			if(array3 != null) {
				int n3 = 0;
				int m3 = array3.size();
				for(n3 = 0 ; n3 < m3 ; n3++) {
					java.lang.Object tr3 = array3.get(n3);
					java.lang.String r = null;
					if(tr3 instanceof java.lang.String) {
						r = (java.lang.String)tr3;
					}
					if(r != null) {
						if(isExcludedAddress(r)) {
							continue;
						}
						rcpts.append((java.lang.Object)r);
					}
				}
			}
		}
		return(rcpts);
	}

	public cape.DynamicVector getRcptsTo() {
		return(rcptsTo);
	}

	public cape.DynamicVector getRcptsCC() {
		return(rcptsCC);
	}

	public cape.DynamicVector getRcptsBCC() {
		return(rcptsBCC);
	}

	public java.lang.String getSubject() {
		return(subject);
	}

	public java.lang.String getContentType() {
		return(contentType);
	}

	public java.lang.String getText() {
		return(text);
	}

	public java.lang.String getMyName() {
		return(myName);
	}

	public java.lang.String getMyAddress() {
		return(myAddress);
	}

	public SMTPMessage setSubject(java.lang.String s) {
		subject = s;
		onChanged();
		return(this);
	}

	public SMTPMessage setContentType(java.lang.String c) {
		contentType = c;
		onChanged();
		return(this);
	}

	public SMTPMessage setText(java.lang.String t) {
		text = t;
		onChanged();
		return(this);
	}

	public SMTPMessage setMyName(java.lang.String n) {
		myName = n;
		onChanged();
		return(this);
	}

	public SMTPMessage setMyAddress(java.lang.String a) {
		myAddress = a;
		onChanged();
		return(this);
	}

	public SMTPMessage setTo(java.lang.String address) {
		rcptsTo = new cape.DynamicVector();
		rcptsTo.append((java.lang.Object)address);
		onChanged();
		return(this);
	}

	public SMTPMessage addTo(java.lang.String address) {
		if(cape.String.isEmpty(address) == false) {
			if(rcptsTo == null) {
				rcptsTo = new cape.DynamicVector();
			}
			rcptsTo.append((java.lang.Object)address);
		}
		onChanged();
		return(this);
	}

	public SMTPMessage addCC(java.lang.String address) {
		if(cape.String.isEmpty(address) == false) {
			if(rcptsCC == null) {
				rcptsCC = new cape.DynamicVector();
			}
			rcptsCC.append((java.lang.Object)address);
		}
		onChanged();
		return(this);
	}

	public SMTPMessage addBCC(java.lang.String address) {
		if(cape.String.isEmpty(address) == false) {
			if(rcptsBCC == null) {
				rcptsBCC = new cape.DynamicVector();
			}
			rcptsBCC.append((java.lang.Object)address);
		}
		onChanged();
		return(this);
	}

	public SMTPMessage setRecipients(cape.DynamicVector to, cape.DynamicVector cc, cape.DynamicVector bcc) {
		rcptsTo = to;
		rcptsCC = cc;
		rcptsBCC = bcc;
		onChanged();
		return(this);
	}

	public int getSizeBytes() {
		java.lang.String b = getMessageBody();
		if(android.text.TextUtils.equals(b, null)) {
			return(0);
		}
		byte[] bb = cape.String.toUTF8Buffer(b);
		if(bb == null) {
			return(0);
		}
		return((int)cape.Buffer.getSize(bb));
	}

	public java.lang.String getMessageBody() {
		if(!(android.text.TextUtils.equals(messageBody, null))) {
			return(messageBody);
		}
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append("From: ");
		sb.append(myName);
		sb.append(" <");
		sb.append(myAddress);
		if(cape.String.isEmpty(replyTo) == false) {
			sb.append(">\r\nReply-To: ");
			sb.append(myName);
			sb.append(" <");
			sb.append(replyTo);
		}
		sb.append(">\r\nTo: ");
		boolean first = true;
		if(rcptsTo != null) {
			java.util.ArrayList<java.lang.Object> array = rcptsTo.toVector();
			if(array != null) {
				int n = 0;
				int m = array.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.Object tto = array.get(n);
					java.lang.String to = null;
					if(tto instanceof java.lang.String) {
						to = (java.lang.String)tto;
					}
					if(to != null) {
						if(first == false) {
							sb.append(", ");
						}
						sb.append(to);
						first = false;
					}
				}
			}
		}
		sb.append("\r\nCc: ");
		first = true;
		if(rcptsCC != null) {
			java.util.ArrayList<java.lang.Object> array2 = rcptsCC.toVector();
			if(array2 != null) {
				int n2 = 0;
				int m2 = array2.size();
				for(n2 = 0 ; n2 < m2 ; n2++) {
					java.lang.Object tto2 = array2.get(n2);
					java.lang.String to = null;
					if(tto2 instanceof java.lang.String) {
						to = (java.lang.String)tto2;
					}
					if(to != null) {
						if(first == false) {
							sb.append(", ");
						}
						sb.append(to);
						first = false;
					}
				}
			}
		}
		sb.append("\r\nSubject: ");
		sb.append(subject);
		sb.append("\r\nContent-Type: ");
		sb.append(contentType);
		sb.append("\r\nDate: ");
		sb.append(date);
		sb.append("\r\nMessage-ID: <");
		sb.append(messageID);
		sb.append(">\r\n\r\n");
		sb.append(text);
		messageBody = sb.toString();
		return(messageBody);
	}

	public cape.DynamicVector getExcludeAddresses() {
		return(excludeAddresses);
	}

	public SMTPMessage setExcludeAddresses(cape.DynamicVector v) {
		excludeAddresses = v;
		return(this);
	}
}
