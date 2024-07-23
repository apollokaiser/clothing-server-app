package com.stu.dissertation.clothingshop.Payload.Response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GoogleOauth2TokenResponse {
    private String accessToken;
    private String refreshToken;
    private String scope;
    private String tokenType;
    private Long expiresIn;
    private String idToken;
}
