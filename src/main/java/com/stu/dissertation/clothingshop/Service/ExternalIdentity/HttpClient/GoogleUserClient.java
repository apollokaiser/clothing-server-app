package com.stu.dissertation.clothingshop.Service.ExternalIdentity.HttpClient;

import com.stu.dissertation.clothingshop.Payload.Response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="google-user", url ="https://www.googleapis.com")
public interface GoogleUserClient {
    @GetMapping(value="/oauth2/v1/userinfo")
    UserInfo getUserInfo(@RequestParam("alt") String alt,
                         @RequestParam("access_token") String accessToken);
}
