package com.stu.dissertation.clothingshop.Payload.Request;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class GoogleOauth2TokenRequest {
    String code;
    String clientId;
    String clientSecret;
    String redirectUri;
    String grantType;
}
