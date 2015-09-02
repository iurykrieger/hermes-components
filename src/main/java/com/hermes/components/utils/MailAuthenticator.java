package com.hermes.components.utils;

import javax.mail.Authenticator;  
import javax.mail.PasswordAuthentication;  
  
public class MailAuthenticator extends Authenticator {  
      public MailAuthenticator() {    
  }    
 
  public PasswordAuthentication getPasswordAuthentication() {    
    return new PasswordAuthentication("iuty.k@hotmail.com", "iurykrieger96");    
  }      
}  

