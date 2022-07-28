package com.example.easybuy.utility;

import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

    class SendMail extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Properties properties = new Properties();

            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(properties, new Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(from,"lqpkhexwfsvshzol");
                }
            });

            session.setDebug(true);

            MimeMessage mimeMessage = new MimeMessage(session);
            try {
                mimeMessage.setFrom(new InternetAddress(from));
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                mimeMessage.setSubject(subject);
                mimeMessage.setText(message);

                Transport.send(mimeMessage);
                System.out.println("Mail Sent...");
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }

            return null;
        }
    }

    public void sendMail(){

        SendMail send = new SendMail();

        try { send.execute(); }
        catch (Exception exception) { exception.printStackTrace(); }
    }
}
