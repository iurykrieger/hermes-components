package com.hermes.components.utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;

public class MailInbox {

    public static void main(String[] args) throws Exception, MessagingException {

        URLName server = new URLName("pop3://iury.k@pop.gmail.com:995/Inbox");
        Properties pop = new Properties();

        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        pop.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
        pop.setProperty("mail.pop3.socketFactory.fallback", "false");
        pop.setProperty("mail.pop3.port", "995");
        pop.setProperty("mail.pop3.socketFactory.port", "995");

        Session session = Session.getDefaultInstance(pop, new MailAuthenticator());

        Folder folder = session.getFolder(server);
        if (folder == null) {
            System.out.println("Folder " + server.getFile() + " not found.");
            System.exit(1);
        }
        folder.open(Folder.READ_ONLY);

        // Get the messages from the server    
        Message[] messages = folder.getMessages();
        for (int i = 0; i < messages.length; i++) {
            // Get the headers    
            System.out.println("From: " + InternetAddress.toString(messages[i].getFrom()));
            System.out.println("Reply-to: " + InternetAddress.toString(messages[i].getReplyTo()));
            System.out.println("To: " + InternetAddress.toString(messages[i].getRecipients(Message.RecipientType.TO)));
            System.out.println("Cc: " + InternetAddress.toString(messages[i].getRecipients(Message.RecipientType.CC)));
            System.out.println("Bcc: " + InternetAddress.toString(messages[i].getRecipients(Message.RecipientType.BCC)));
            System.out.println("Subject: " + messages[i].getSubject());

            System.out.println("Sent: " + messages[i].getSentDate());
            System.out.println("Received: " + messages[i].getReceivedDate());

            if (messages[i].isSet(Flags.Flag.DELETED)) {
                System.out.println("Deleted");
            }
            if (messages[i].isSet(Flags.Flag.ANSWERED)) {
                System.out.println("Answered");
            }
            if (messages[i].isSet(Flags.Flag.DRAFT)) {
                System.out.println("Draft");
            }
            if (messages[i].isSet(Flags.Flag.FLAGGED)) {
                System.out.println("Marked");
            }
            if (messages[i].isSet(Flags.Flag.RECENT)) {
                System.out.println("Recent");
            }
            if (messages[i].isSet(Flags.Flag.SEEN)) {
                System.out.println("Read");
            }
            if (messages[i].isSet(Flags.Flag.USER)) {
                // We don't know what the user flags might be in advance    
                // so they're returned as an array of strings    
                String[] userFlags = messages[i].getFlags().getUserFlags();
                for (int j = 0; j < userFlags.length; j++) {
                    System.out.println("User flag: " + userFlags[j]);
                }
            }
////////////////////////////  
            Multipart multipart = (Multipart) messages[i].getContent();
            Object msgObj = messages[i].getContent();


            if (msgObj instanceof Multipart) {

                String subject = messages[i].getSubject();
                multipart = (Multipart) messages[i].getContent();

                for (int w = 0; w < multipart.getCount(); w++) {

                    Part part = multipart.getBodyPart(w);

                    String contentType = part.getContentType();

                    String fileName = part.getFileName();
                    String corpoMsg = part.getDescription();
                    if (fileName != null) {
                        System.out.println("Numero da mensagem: " + i + " Nome do anexo: " + fileName + " | Assunto: " + messages[i].getSubject()
                    
                    );  
               }    
               fileName = null;
                }
            };
///////////////////////////        
            System.out.println("Numero do email: " + i);
            System.out.println("===========================");
        }

        folder.close(false);
    }
}
