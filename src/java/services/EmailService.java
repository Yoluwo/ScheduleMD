/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author epaul
 */
public class EmailService {
    //
    private final String USER_NAME = "schedulemdtest@gmail.com";
    private final String PASSWORD = "TestPassword1!";
    private String host = "smtp.gmail.com";
    private String recipient;
    
    public void sendForgotPasswordEmail(String recipientEmail) {
        this.recipient = recipientEmail;
        //Get system properties
        Properties properties = System.getProperties();
        //Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        
        //Get session object and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER_NAME, PASSWORD);
            }
        });
        try {
            //Create default MimeMessage Object
            MimeMessage message = new MimeMessage(session);
            //Set From: header field of the header
            message.setFrom(new InternetAddress(USER_NAME));
            //Set To: header field on the header
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            //Set Subject of the message
            message.setSubject("This is subject line");
            message.setText("This is the message text");
            Transport.send(message);
        } catch (MessagingException mex) {}
    }
}
