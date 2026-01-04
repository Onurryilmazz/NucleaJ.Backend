package com.nuclea.common.service.email;

import java.util.Map;

/**
 * Mail service interface for sending emails.
 */
public interface IMailService {

    /**
     * Send simple email.
     */
    boolean sendEmail(String to, String subject, String body);

    /**
     * Send email with HTML content.
     */
    boolean sendHtmlEmail(String to, String subject, String htmlBody);

    /**
     * Send email using template.
     */
    boolean sendTemplateEmail(String to, String subject, String templateName, Map<String, Object> variables);

    /**
     * Send email with attachments.
     */
    boolean sendEmailWithAttachment(String to, String subject, String body, String attachmentPath);
}
