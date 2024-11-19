package com.mypractice.book.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaEmailSender;
    private final SpringTemplateEngine tempateEngine;

    public void sendEmail(String to, String username,
                          EmailTemplateName emailTemplateName,
                          String confimationUrl,
                          String activationCode,
                          String subject)  {

    }
}
