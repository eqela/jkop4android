
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

public class SMTPMultipartMessage extends SMTPMessage
{
	public SMTPMultipartMessage() {
		setContentType("multipart/mixed");
	}

	private cape.DynamicVector attachments = null;
	private java.lang.String message = null;

	@Override
	public java.lang.String getMessageBody() {
		if((attachments == null) || (attachments.getSize() < 1)) {
			return(null);
		}
		if(cape.String.isEmpty(message) == false) {
			return(message);
		}
		java.lang.String subject = getSubject();
		java.lang.String date = getDate();
		java.lang.String myName = getMyName();
		java.lang.String myAddress = getMyAddress();
		java.lang.String text = getText();
		cape.DynamicVector recipientsTo = getRcptsTo();
		cape.DynamicVector recipientsCC = getRcptsCC();
		java.lang.String messageID = getMessageID();
		java.lang.String replyTo = getReplyTo();
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
		if(recipientsTo != null) {
			java.util.ArrayList<java.lang.Object> array = recipientsTo.toVector();
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
		if(recipientsCC != null) {
			java.util.ArrayList<java.lang.Object> array2 = recipientsCC.toVector();
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
		sb.append("\r\nMIME-Version: 1.0");
		sb.append("\r\nContent-Type: ");
		sb.append("multipart/mixed");
		sb.append("; boundary=\"XXXXboundarytext\"");
		sb.append("\r\nDate: ");
		sb.append(date);
		sb.append("\r\nMessage-ID: <");
		sb.append(messageID);
		sb.append(">\r\n\r\n");
		sb.append("This is a multipart message in MIME format.");
		sb.append("\r\n");
		sb.append("\r\n--XXXXboundarytext");
		sb.append("\r\nContent-Type: text/plain");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append(text);
		java.util.ArrayList<java.lang.Object> array3 = attachments.toVector();
		if(array3 != null) {
			int n3 = 0;
			int m3 = array3.size();
			for(n3 = 0 ; n3 < m3 ; n3++) {
				java.lang.Object tfile = array3.get(n3);
				cape.File file = null;
				if(tfile instanceof cape.File) {
					file = (cape.File)tfile;
				}
				if(file != null) {
					sb.append("\r\n--XXXXboundarytext");
					sb.append("\r\nContent-Type: ");
					java.lang.String contentType = capex.MimeTypeRegistry.typeForFile(file);
					if((cape.String.isEmpty(contentType) == false) && (cape.String.getIndexOf(contentType, "text") == 0)) {
						sb.append(contentType);
						sb.append("\r\nContent-Disposition: attachment; filename=");
						sb.append(file.baseName());
						sb.append("\r\n");
						sb.append("\r\n");
						sb.append(file.getContentsString("UTF8"));
					}
					else {
						sb.append(contentType);
						sb.append("\r\nContent-Transfer-Encoding: Base64");
						sb.append("\r\nContent-Disposition: attachment filename=");
						sb.append(file.baseName());
						sb.append("\r\n");
						sb.append("\r\n");
						sb.append(capex.Base64Encoder.encode(file.getContentsBuffer()));
					}
				}
			}
		}
		sb.append("\r\n");
		sb.append("\r\n--XXXXboundarytext--");
		return(message = sb.toString());
	}

	public cape.DynamicVector getAttachments() {
		return(attachments);
	}

	public SMTPMultipartMessage setAttachments(cape.DynamicVector v) {
		attachments = v;
		return(this);
	}
}
