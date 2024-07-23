package com.stu.dissertation.clothingshop.Service.ExternalIdentity;

import com.stu.dissertation.clothingshop.Payload.Request.GoogleOauth2TokenRequest;
import com.stu.dissertation.clothingshop.Payload.Response.GoogleOauth2TokenResponse;
import com.stu.dissertation.clothingshop.Payload.Response.UserInfo;
import com.stu.dissertation.clothingshop.Security.ClientInfo.GoogleClient;
import com.stu.dissertation.clothingshop.Service.ExternalIdentity.HttpClient.GoogleIdentityClient;
import com.stu.dissertation.clothingshop.Service.ExternalIdentity.HttpClient.GoogleUserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
@RequiredArgsConstructor
public class GoogleIdentityService {
    private final GoogleClient googleClient;
    private final GoogleIdentityClient identityClient;
    private final GoogleUserClient userClient;

    public UserInfo exchangeUserInfo(String authentication_code) {
        GoogleOauth2TokenRequest request = GoogleOauth2TokenRequest.builder()
                .code(authentication_code)
                .clientId(googleClient.getClientId())
                .clientSecret(googleClient.getClientSecret())
                .redirectUri("http://localhost:5173")
                .grantType("authorization_code")
                .build();
        GoogleOauth2TokenResponse response = identityClient.exchangeToken(request);
        UserInfo userInfo = userClient.getUserInfo("json", response.getAccessToken());
        return userInfo;
    }
}
