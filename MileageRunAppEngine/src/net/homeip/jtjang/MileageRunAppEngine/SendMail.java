// Copyright 2011, Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package net.homeip.jtjang.MileageRunAppEngine;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * This class details about the process for sending mail.
 * 
 * @author
 */
public class SendMail {
  private static Logger log = Logger.getLogger(SendMail.class.getCanonicalName());

  //mail id from which the mail has to be sent
  private static String fromAddress = "keithjtjang@gmail.com";
  
  /**
   * Method defines the way to send a mail
   * 
   * @param toAddress : the address to which mail needs to be sent
   * @param subject : subject of the mail
   * @param msgBody : mail content
   * @throws IOException
   */
  // Send the Mail
  public void send(String toAddress, String subject, String msgBody)
      throws IOException {
  	Properties props = new Properties();
  	Session session = Session.getDefaultInstance(props, null);

  	try {
  	  Message msg = new MimeMessage(session);
  	  msg.setFrom(new InternetAddress(fromAddress));
  	  InternetAddress to = new InternetAddress(toAddress);
  	  msg.addRecipient(Message.RecipientType.TO, to);
  	  msg.setSubject(subject);
  	  msg.setText(msgBody);
  	  Transport.send(msg, new InternetAddress[] { to });
  	} catch (AddressException addressException) {
  	  log.log(Level.SEVERE, "Address Exception , mail could not be sent", addressException);
  	} catch (MessagingException messageException) {
  	  log.log(Level.SEVERE, "Messaging Exception , mail could not be sent", messageException);
  	}
  }
}
