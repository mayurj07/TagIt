package edu.sjsu.cmpe275.tagit.Utils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailNotification {

    static final String host = "smtp.gmail.com";
    static final String username = "CMPE275Tagit";
    static final String password = "qazedctgb";

    public void sendEmailonSignUp(String userEmailAddress, String userName, String token)
    {
        String subject = "Welcome to TagIt!";

        String msgBody = "Hello "+ userEmailAddress +",\n\nYour account has been successfully created." +
                "\n\nThank you for choosing TagIt !" + "\n\nNow you can easily store and share bookmarks !!\n\n" +
                "Team TagIt !!! \n\n\n\n"+
                "\nClick the link below to verify your email address: \n\n"+
                "http://localhost:8080/user/verify/"+userEmailAddress+"/"+token;

        emailGenerator(userEmailAddress, userName, subject, msgBody);


    }

    public void sendEmailOnSharing(String userEmailAddress, String userName, String SharingUser, String notebookName)
    {

        String subject = "Your friend [ " + SharingUser + " ]" + " has shared a notebook with you !";
        String msgBody = "Hello "+ userEmailAddress + ",\n\nA notebook has been shared with you [Notebook Ref :"+ notebookName +"] by " + SharingUser +"."+
                "\n\nPlease login to your TagIt account to view your bookmarks."+
                "\n\n" +
                "Team TagIt !!! ";

        emailGenerator(userEmailAddress, userName, subject, msgBody);

    }


    public void emailGenerator(String userEmailAddress, String userName, String subject, String msgBody)

    {
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);

        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message msg = new MimeMessage(session);
            try {
                msg.setFrom(new InternetAddress("CMPE275Tagit@gmail.com", "TagIt"));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                msg.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(userEmailAddress, "Hello " + userName ));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            msg.setSubject(subject);
            msg.setText(msgBody);
            Transport.send(msg);

        } catch (AddressException e) {
            System.out.println(e.getMessage());
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }



    }


}
