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
import dataaccess.UserResetTokenDB;
import models.UserResetToken;
import java.util.Date;
import java.sql.Timestamp;
import java.net.URL;
import java.net.MalformedURLException;

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
    
    public void sendForgotPasswordEmail(String recipientEmail, int userID) throws MalformedURLException {
        String token = "";
        boolean unique = false;
        //While loop Generates a Token, and checks if the token exists in database already, if it does, it will generate new token until it does not match one in database.
        while(!unique){
            token = generateToken();
            UserResetToken urt = null;
            try{
                UserResetTokenDB urtDB = new UserResetTokenDB();
                urt = urtDB.getToken(token);
            } catch (Exception e){}
            if (urt == null){
                unique = true;
            }            
        }
        //Updates the UserResetToken table to include Token and timestamp
        try{
            UserResetTokenDB urtDB = new UserResetTokenDB();
            UserResetToken urt = urtDB.getUserID(userID);
            Date date = new Date();
            Timestamp timestamp = new Timestamp(date.getTime());
            urt.setExpirationdate(timestamp);
            urt.setToken(token);
            urtDB.update(urt);
        } catch (Exception e){}
        URL url = new URL("http://localhost:8080/Schedule-MD/resetPassword?t=" + token);
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
            message.setText("Please go to this link to change password: " + url);
            Transport.send(message);
        } catch (MessagingException mex) {}
    }
    public String generateToken() {
        
        
        
        return "abcd";
    }
}
