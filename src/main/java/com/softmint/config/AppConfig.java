package com.softmint.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.config")
public class AppConfig {

    private String apiBaseUrl;
    private String mailScriptUrl;
    private String uiBaseUrl;
    private String mailFrom;
    private int maxLoginAttempts;
}

