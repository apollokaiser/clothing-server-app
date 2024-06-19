package com.stu.dissertation.clothingshop.Security.JWTAuth;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;

@Component
@Slf4j
public class CustomJWTDecoder implements JwtDecoder {
    @Value("${application.security.jwt.secret}")
    private String secretKey;
    @Autowired
    private JWTService jwtService;

    private NimbusJwtDecoder jwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException{
        if(!jwtService.isNonExpiredToken(token))
            throw new JwtException("Token expired");
//            throw new ApplicationException(BusinessErrorCode.EXPIRED_TOKEN);
        if(Objects.isNull(jwtDecoder)){
            //you may create a bean "nimbusJwtDecoder"
            SecretKeySpec spec = new SecretKeySpec(secretKey.getBytes(),"HS512");
            jwtDecoder = NimbusJwtDecoder.withSecretKey(spec).macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }
        return jwtDecoder.decode(token);
    }
}
