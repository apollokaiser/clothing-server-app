package com.stu.dissertation.clothingshop.Security.JWTAuth;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.stu.dissertation.clothingshop.Entities.NguoiDung;
import com.stu.dissertation.clothingshop.Enum.BusinessErrorCode;
import com.stu.dissertation.clothingshop.Exception.CustomException.ApplicationException;
import com.stu.dissertation.clothingshop.Repositories.InvalidTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JWTService {
    @Value("${application.security.jwt.secret}")
    private String secretKey;
    @Value("${application.security.jwt.expiration-token}")
    private long expiration;
    private final InvalidTokenRepository invalidatedTokenRepository;

    public String generateToken(NguoiDung user) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .jwtID(UUID.randomUUID().toString())
                .subject(user.getEmail())
                .issuer("clothingshop.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(expiration, ChronoUnit.MINUTES).toEpochMilli()))
                .claim("name", user.getName())
                .claim("scope", getAuthorities(user))
                .claim("uid", user.getId())
                .build();
        Payload payload = new Payload(claims.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        jwsObject.sign(new MACSigner(secretKey.getBytes()));
        return jwsObject.serialize();
    }
    private String getAuthorities(NguoiDung user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        user.getAuthorities().forEach(authority -> stringJoiner.add(authority.getAuthority()));
        return stringJoiner.toString();
    }
    public String extractEmail(String token) {
        return verifiedToken(token).getSubject();
    }
    public Long extractExpTime(String token) {
        try {
            long exp = verifiedToken(token).getExpirationTime().getTime();
            if(new Date(exp).after(new Date())) {
                return null;
            }
            return exp;
        }  catch (Exception e) {
            throw new ApplicationException(BusinessErrorCode.INVALID_TOKEN);
        }
    }
    public boolean isNonExpiredToken(String token){
        try {
            Date expireTime = verifiedToken(token).getExpirationTime();
            return !expireTime.before(new Date());
        }  catch (Exception e) {
            throw new ApplicationException(BusinessErrorCode.INVALID_TOKEN);
        }
    }

    public JWTClaimsSet verifiedToken(String token) {
        try {
            JWSVerifier verifier = new MACVerifier(secretKey.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            var verified = signedJWT.verify(verifier);

            if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
                throw new ApplicationException(BusinessErrorCode.TOKEN_HAS_DESTROYED);
            return signedJWT.getJWTClaimsSet();
        } catch (JOSEException | ParseException | ApplicationException e) {
            if(e instanceof ParseException || e instanceof JOSEException) {
            throw new ApplicationException(BusinessErrorCode.INVALID_TOKEN);
            }
           throw new ApplicationException(((ApplicationException) e).getErrorCode());
        }
    }
}
