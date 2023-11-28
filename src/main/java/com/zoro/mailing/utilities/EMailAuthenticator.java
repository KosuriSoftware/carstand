package com.zoro.mailing.utilities;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


public class EMailAuthenticator extends Authenticator {
	String user;
    String pw;
    public EMailAuthenticator (String username, String password)
    {
       super();
       this.user = username;
       this.pw = password;
    }
   public PasswordAuthentication getPasswordAuthentication()
   {
      return new PasswordAuthentication(user, pw);
   }
}
