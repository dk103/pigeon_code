package com.app.mail.pigeonmailer.com.app.mail.pigeonmailer.MailApi;


import android.util.Log;

import javax.activation.DataHandler;

import javax.activation.DataSource;

import javax.activation.FileDataSource;

import javax.mail.BodyPart;

import javax.mail.Message;

import javax.mail.Multipart;

import javax.mail.PasswordAuthentication;

import javax.mail.Session;

import javax.mail.Transport;

import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeBodyPart;

import javax.mail.internet.MimeMessage;

import javax.mail.internet.MimeMultipart;




import java.security.Security;

import java.util.Properties;


    public class GMailSender extends javax.mail.Authenticator {

        private String mailhost = "smtp.sendgrid.net";

        private String user;

        private String password;

        private Session session;

        private static final String TAG = "GmailSender";

        private Multipart _multipart = new MimeMultipart();

        static {

            Security.addProvider(new JSSEProvider());

        }



        public GMailSender(String user, String password) {

            this.user = user;

            this.password = password;



            Properties props = new Properties();

            props.setProperty("mail.transport.protocol", "smtp");

            props.setProperty("mail.host", mailhost);

            props.put("mail.smtp.auth", "true");

            props.put("mail.smtp.port", "465");

            props.put("mail.smtp.socketFactory.port", "465");

            props.put("mail.smtp.socketFactory.class",

                    "javax.net.ssl.SSLSocketFactory");

            props.put("mail.smtp.socketFactory.fallback", "false");

        //change//
               props.put("mail.smtp.user", user);

            props.setProperty("mail.smtp.quitwait", "false");



            session = Session.getDefaultInstance(props);

        }



        protected PasswordAuthentication getPasswordAuthentication() {

            return new PasswordAuthentication(user, password);

        }



        public synchronized void sendMail(String subject, String body,

                                          String sender, String recipients) throws Exception {

            try {

                Message message = new MimeMessage(session);

                DataHandler handler = new DataHandler(new ByteArrayDataSource(

                        body.getBytes(), "text/plain"));

            //    message.setSender(new InternetAddress(sender));
                message.setFrom(new InternetAddress(user));
                InternetAddress[] toAddresses = { new InternetAddress(sender) };
                message.setRecipients(Message.RecipientType.TO, toAddresses);

                message.setSubject(subject);

                message.setDataHandler(handler);

                BodyPart messageBodyPart = new MimeBodyPart();

                messageBodyPart.setText(body);

                _multipart.addBodyPart(messageBodyPart);



                // Put parts in message

                message.setContent(_multipart);

                if (recipients.indexOf(',') > 0)

                    message.setRecipients(Message.RecipientType.TO,

                            InternetAddress.parse(recipients));

                else

                    message.setRecipient(Message.RecipientType.TO,

                            new InternetAddress(recipients));


                //changes
                Transport t = session.getTransport("smtp");
                t.connect(user, password);
                t.sendMessage(message,message.getAllRecipients());
                t.close();
              //  Transport.send(message);


            } catch (Exception e) {

                Log.e(TAG,e.getMessage());

            }

        }

    }
