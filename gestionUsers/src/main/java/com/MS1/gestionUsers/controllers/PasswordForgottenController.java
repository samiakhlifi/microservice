package com.bac.tn.gestionusers.controllers;

import com.MS1.gestionUsers.models.User;
import com.MS1.gestionUsers.payload.request.ForgotPwdRequest;
import com.MS1.gestionUsers.payload.request.ResetPawdRequest;
import com.MS1.gestionUsers.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Properties;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/resetmdp")
public class PasswordForgottenController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private JavaMailSender mailSender;

    //Reset password


    @PostMapping("/forgot_password")
    public void processForgotPassword(@RequestBody ForgotPwdRequest forgetPwdReq, Model model) {
        String email = forgetPwdReq.getEmail();
        String token = forgetPwdReq.getResetToken();


        try {
            updateResetPasswordToken(token, email);
            String resetPasswordLink = "http://localhost:4200/resetPassword/" + token;
            sendEmail(email, resetPasswordLink);
//            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
//
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }
//
//        return "forget_password_form";

    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {


        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("hostunh1@gmail.com");
        mailSender.setPassword("ocszktimpsdnfnne");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);


        helper.setFrom("hostunh1@gmail.com", "Hostun Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }


    @PostMapping("/reset_password")
    public void processResetPassword(@RequestBody ResetPawdRequest resetPwd, Model model) {
        //String token = request.getParameter("token");
        //String password = request.getParameter("password");

        User user = getByResetPasswordToken(resetPwd.getResetToken());
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");

        } else {
            updatePassword(user, resetPwd.getPassword());
            System.out.println("You have successfully changed your password");
            model.addAttribute("message", "You have successfully changed your password.");
        }


    }

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        Optional<User> userOption = userRepository.findByEmail(email);
        if (userOption.isPresent()) {
            User user = userOption.get();
            if (user != null) {
                user.setResetPasswordToken(token);
                userRepository.save(user);
            } else {
                throw new UsernameNotFoundException("Could not find any customer with the email " + email);
            }
        }

    }

    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setMdp(encodedPassword);

        user.setResetPasswordToken(null);
        System.out.println(user);
        userRepository.save(user);
    }
}
