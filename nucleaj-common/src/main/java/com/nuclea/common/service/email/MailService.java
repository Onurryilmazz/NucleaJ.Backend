package com.nuclea.common.service.email;

import com.nuclea.common.service.marker.IScopedService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

/**
 * Mail service implementation using Spring Mail.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MailService implements IMailService, IScopedService {

    private final JavaMailSender mailSender;
    private final EmailTemplateService templateService;

    @Value("${spring.mail.username:noreply@nuclea.com}")
    private String fromEmail;

    @Value("${app.mail.sender-name:Nuclea}")
    private String fromName;

    @Override
    public boolean sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(String.format("%s <%s>", fromName, fromEmail));
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            log.info("Email sent successfully to: {}", to);
            return true;
        } catch (Exception e) {
            log.error("Error sending email to {}: {}", to, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(String.format("%s <%s>", fromName, fromEmail));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            mailSender.send(message);
            log.info("HTML email sent successfully to: {}", to);
            return true;
        } catch (Exception e) {
            log.error("Error sending HTML email to {}: {}", to, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean sendTemplateEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        try {
            String htmlContent = templateService.processTemplate(templateName, variables);
            return sendHtmlEmail(to, subject, htmlContent);
        } catch (Exception e) {
            log.error("Error sending template email to {}: {}", to, e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(String.format("%s <%s>", fromName, fromEmail));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            File attachment = new File(attachmentPath);
            if (attachment.exists()) {
                helper.addAttachment(attachment.getName(), attachment);
            }

            mailSender.send(message);
            log.info("Email with attachment sent successfully to: {}", to);
            return true;
        } catch (Exception e) {
            log.error("Error sending email with attachment to {}: {}", to, e.getMessage(), e);
            return false;
        }
    }
}
