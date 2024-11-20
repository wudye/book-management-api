package com.mypractice.book.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
@EnableAsync
public class EmailService {
    private final JavaMailSender javaEmailSender;
    private final SpringTemplateEngine tempateEngine;

    @Async
    public void sendEmail(String to, String username,
                          EmailTemplateName emailTemplateName,
                          String confimationUrl,
                          String activationCode,
                          String subject) throws MessagingException {
        String templateName;
        if (emailTemplateName == null) {
            templateName = "confirm-email";
        } else {
            templateName = emailTemplateName.name();
        }
        MimeMessage mimeMessage = javaEmailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage
        , MimeMessageHelper.MULTIPART_MODE_MIXED,
                UTF_8.name());
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confimationUrl", confimationUrl);
        properties.put("activationCode", activationCode);

        Context context = new Context();
        context.setVariables(properties);
        helper.setFrom("mingwei.wu@hotmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = tempateEngine.process(templateName, context);
        helper.setText(template, true)
        ;
        javaEmailSender.send(mimeMessage);

    }
}
