package com.example.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("security")
public record SecurityProperties(String username, String password, String role) {
}
