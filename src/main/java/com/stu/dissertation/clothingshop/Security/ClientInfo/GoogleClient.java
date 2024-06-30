package com.stu.dissertation.clothingshop.Security.ClientInfo;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GoogleClient {
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;
}
