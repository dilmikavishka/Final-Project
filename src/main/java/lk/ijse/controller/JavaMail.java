package lk.ijse.controller;

import javafx.scene.control.Alert;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMail {
    public static void sendMail(String recipient ,int otp) throws MessagingException {
        try {
            System.out.println("Preparing to send email");
            Properties properties = new Properties();


            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            String myAccountEmail = "sehansassara2002@gmail.com";
            String password = "vqgc nsjj lpsb bhcn";
            System.out.println("aaa");
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(myAccountEmail, password);

                }

            });

            System.out.println("bbb");
            Message message = prepareMessage(session, myAccountEmail, recipient, otp);
            System.out.println("ccc");
            if (message != null) {
                Transport.send(message);
            }
            System.out.println("Email Send successfully");
        } catch (MessagingException ex) {
            ex.printStackTrace(); // or log the exception
            new Alert(Alert.AlertType.ERROR, "Error occurred while sending email: " + ex.getMessage()).show();
        }
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recipient, int otp) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Your OTP");
            message.setText("Your OTP is "+otp);
            return message;
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Connect Internet Connection !!").show();
        }
        return null;
    }
}