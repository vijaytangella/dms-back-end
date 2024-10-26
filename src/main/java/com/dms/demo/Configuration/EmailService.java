package com.dms.demo.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Vijaya Hospital");

        // Get the current date
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        message.setText("Welcome! You have been successfully registered at Vijaya Hospital on " + "(" + currentDate + ")" + " " + to);

        // Send the email
        try {
            mailSender.send(message);
            System.out.println("Registration email sent to: " + to);
        } catch (Exception e) {
            System.out.println("Failed to send registration email: " + e.getMessage());
        }
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("vijaytangella179@gmail.com");  // Sender email address
        mailSender.send(message);  // Send the email using JavaMailSender
    }
}
