package com.mycompany.easybuy.utility;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

public class MailUtility {
    
    private final String host = "smtp.gmail.com";
    
    private final String from = "ezy.buy.info@gmail.com";
    private final String to;
    private final String message;
    private final String subject;

    public MailUtility(String to, String message, String subject) {
        this.to = to;
        this.message = message;
        this.subject = subject;
    }
    
    public void sendMail(){
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(from,"nehlnqphahwnqffe");
            }
        });

        session.setDebug(true);

        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(from);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            Transport.send(mimeMessage);
            System.out.println("Mail Sent...");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
