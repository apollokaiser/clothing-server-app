package com.stu.dissertation.clothingshop.Service.ExternalIdentity.HttpClient;

import com.stu.dissertation.clothingshop.Payload.Request.GoogleOauth2TokenRequest;
import com.stu.dissertation.clothingshop.Payload.Response.GoogleOauth2TokenResponse;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="google-identity", url ="https://oauth2.googleapis.com")
public interface GoogleIdentityClient {
    @PostMapping(value = "/token", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    GoogleOauth2TokenResponse exchangeToken(@QueryMap GoogleOauth2TokenRequest req);
}
