package com.nuclea.common.service.email;

import com.nuclea.common.service.marker.ISingletonService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;

/**
 * Email template engine service using FreeMarker.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailTemplateService implements ISingletonService {

    private final Configuration freemarkerConfig;

    /**
     * Process template with variables.
     */
    public String processTemplate(String templateName, Map<String, Object> variables) {
        try {
            Template template = freemarkerConfig.getTemplate(templateName + ".ftl");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, variables);
        } catch (Exception e) {
            log.error("Error processing template {}: {}", templateName, e.getMessage(), e);
            throw new RuntimeException("Failed to process email template: " + templateName, e);
        }
    }
}
