package com.usman.utilities;

import com.usman.student.StudentDAO;
import com.usman.student.StudentData;
import javafx.scene.image.Image;
import sun.misc.IOUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.Properties;
import java.util.Random;

public class SentMail {
    private static String EMAIL_ID;
    private static String PASSWORD;

    private static void readData() throws Exception {
        String[] data = Utilities.readDataInJsonFile();
        EMAIL_ID = data[0];
        PASSWORD = data[1];
    }

    private static int generateVerificationCode(){
        int OTP = 0;
        Random random = new Random();
        for (int i = 0; i < 6; i++ ) {
            OTP = OTP *10 + random.nextInt(9)+1;
        }
        return OTP;
    }

    public static int sentVerificationCode(String userEmailId) throws Exception {
        readData();
        int verificationCode = generateVerificationCode();
        //Get the session object
        String content = "<html>\n" +
                "    <body>\n" +
                "        <div style=\"width: 100%;height:200px;background:goldenrod;border-bottom:2px solid #212121\">\n" +
                "            <h1 style=\"text-align: center;font-family:verdana;color:#fff;padding-top:50px\"> SOLO-ANS</h1>\n" +
                "            <p style=\"text-align: center;color:#fff;font-family:verdana\">Better Eduction Better Life</p>\n" +
                "        </div>\n" +
                "        <p style=\"padding: 6px;font-size:2rem;text-align: center\">Welcome :" + userEmailId + "</p>\n" +
                "        <p style=\"padding: 10px;font-size:1rem;font-family: verdana;text-align: center\">You can confirm your account email through the OTP. <br>\n" +
                "        Your Verification code is : "+ verificationCode + " </p>\n" +
                "        <div style=\"width: 100%;height:100px;background:#212121;\">\n" +
                "            <p style=\"text-align: center;color:#fff;font-family:verdana;line-height: 100px\">Copyright @ Man Of Action Worldwide</p>\n" +
                "        </div>\n" +
                "    </body>\n" +
                "</html>";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port","587");
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL_ID,PASSWORD);
                    }
                });

        //Compose the message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(EMAIL_ID));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(userEmailId));
        message.setSubject("CMS Verification Code");
        message.setContent(content,"text/html");
        //send the message
        Transport.send(message);
        return verificationCode;
    }
    public static void sentIDCard(StudentData studentData) throws Exception {
        readData();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL_ID, PASSWORD);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(EMAIL_ID));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(studentData.getEmailId()));

            // Set Subject: header field
            message.setSubject("Card ID of CMS");

            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            //String htmlText = "<H1>Hello</H1><img src=\"cid:image\" alt=\"student image\">";
            String content = "<html>\n" +
                    "<body>\n" +
                    "    <div style=\"width:680px;height:430px;background-color: #eee;border-radius: 10px;margin: auto\">\n" +
                    "        <div style=\"height:100px;background-image: linear-gradient( to right, rgb(255, 17, 108) 3.3%, rgb(206, 13, 88) 98.4% );\">\n" +
                    "            <p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 27px;color: #ffffff;font-weight: bold;text-align: center;padding-top: 16px;\">DEPT. OF B.B.C & B.C.A</p>\n" +
                    "            <p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 19px;color: #ffffff;text-align: center;margin-top: -20px;\">ANS COLLEGE, GAYA</p>\n" +
                    "            <div style=\"height: 230px;width:200px;margin-top: 50px;margin-left: 10px;\">\n" +
                    "                    <img width=\"100%\" src=\"cid:image\" alt=\"Student image\">\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "\n" +
                    "        <table style=\"margin-left: 220px;margin-top: 30px; font-family: Arial, Helvetica, sans-serif;font-size: 18px;\">\n" +
                    "            <tr>\n" +
                    "                <td >Name :  " + studentData.getName() + "</td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "            <td style=\"padding-top: 10px;\">Father's Name : " + studentData.getFatherName() + "</td>\n" +
                    "                </tr>\n" +
                    "            <tr>\n" +
                    "            <td style=\"padding-top: 10px;\">Permament Address :  " + studentData.getAddress() + "</td>\n" +
                    "               </tr>\n" +
                    "            <tr>\n" +
                    "            <td style=\"padding-top: 10px;\">Session : " + studentData.getSession() + "</td> <td>Class : " + studentData.getCourse() + "</td>\n" +
                    "            </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "             <td style=\"padding-top: 10px;\">Roll No. : " + studentData.getRollNo() + "</td> <td>DOB : " + studentData.getDob() + "</td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "                 <td style=\"padding-top: 10px;\">Mobile No. :  " + studentData.getStudentMobileNo() + "</td>\n" +
                    "            </tr>\n" +
                    "        </table>\n" +
                    "         <h3 style=\"margin-left:40px;margin-top:70px;float: left;\">Sign. Of Director</h3>\n" +
                    "         <h3 style=\"margin-left:300px;margin-top:70px;float: left;\">Sign. Of Student</h3>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";
            messageBodyPart.setContent(content, "text/html");
            // add it
            multipart.addBodyPart(messageBodyPart);

            // second part (the image)
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(new InputStreamDataSource(Utilities.imageToBinary(studentData.getImage()), "Usman")));
            messageBodyPart.setHeader("Content-ID", "<image>");

            // add image to the multipart
            multipart.addBodyPart(messageBodyPart);

            // put everything together
            message.setContent(multipart);
            // Send message
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
