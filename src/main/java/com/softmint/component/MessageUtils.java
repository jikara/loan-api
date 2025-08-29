package com.softmint.component;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;

@Component
public class MessageUtils {

    private final MessageSource messageSource;

    public MessageUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String key) {
        Locale locale = getCurrentLocale();
        return messageSource.getMessage(key, null, locale);
    }

    public String getMessage(String key, Object[] args) {
        Locale locale = getCurrentLocale();
        return messageSource.getMessage(key, args, locale);
    }

    private Locale getCurrentLocale() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return Locale.getDefault();  // fallback if no request context
        }
        HttpServletRequest request = attributes.getRequest();
        return request.getLocale();
    }
}

