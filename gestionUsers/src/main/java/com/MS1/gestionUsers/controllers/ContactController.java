package com.bac.tn.gestionusers.controllers;

import com.MS1.gestionUsers.message.ContactForm;
import com.MS1.gestionUsers.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin
public class ContactController {

    @Autowired
    private MailService mailService;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/sendMessage")
    public boolean sendMessage(@RequestBody ContactForm contactForm) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hostun1@gmail.com");
        message.setTo("hostun1@gmail.com");
        message.setText("This email was sent from " + contactForm.getNom()
                + " owner of the email : " + contactForm.getEmail() + ". The message says : " + contactForm.getMessage());
        message.setSubject(contactForm.getSujet());

        mailSender.send(message);
        System.out.println("Mail Send...");
        return mailService.sendMail(contactForm);
    }
}
