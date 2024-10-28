package com.example.demo.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Method to send registration email
    public void sendRegistrationEmail(String to) {
        String subject = "Welcome to Vijaya Hospital";
        String text = "<h1>Welcome!</h1><p>You have been successfully registered at Vijaya Hospital on " + getCurrentDate() + "</p>";
        
        sendHtmlEmail(to, subject, text);
    }

    // Method to send HTML email
    public void sendHtmlEmail(String to, String subject, String text) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // Set true for HTML content
            
            mailSender.send(mimeMessage);
            System.out.println("HTML email sent to: " + to);
        } catch (Exception e) {
            System.out.println("Failed to send HTML email: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    // Helper method to get the current date formatted as "yyyy-MM-dd"
    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
