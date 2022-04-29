package com.bac.tn.gestionusers.service.impl;

import com.MS1.gestionUsers.message.ContactForm;
import com.bac.tn.gestionusers.service.MailService;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Override
    public boolean sendMail(ContactForm contactForm) {
        boolean res = false;
        if (contactForm != null) {
            System.out.println(contactForm.getNumTel());


            final String username = "trivaw.hostun@gmail.com";
            final String password = "trivaw987654321";

            Properties props = new Properties();
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enable", true);
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("from.mail.id@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("mendili.wejden@gmail.com"));
                message.setSubject(contactForm.getSujet());
                message.setText("PFA");

                MimeBodyPart messageBodyMessage = new MimeBodyPart();
                MimeBodyPart signature = new MimeBodyPart();

                Multipart multipart = new MimeMultipart();

                messageBodyMessage.setContent("Bonjour,<br>" + "<p> <hr noshade size=2 width=60 align=left>"
                        + contactForm.getMessage() + "</p> <br>", "text/html");
                multipart.addBodyPart(messageBodyMessage);

                signature.setContent("<hr noshade size=2 width=60 align=left>" + "<br> Cordialement <br>"
                                + contactForm.getNom() + "<br>" + contactForm.getNumTel() + "<br>" + contactForm.getEmail(),
                        "text/html");
                multipart.addBodyPart(signature);
                message.setContent(multipart);

                System.out.println("Sending mail...");

                Transport.send(message);

                System.out.println("Done");
                res = true;

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

}

