package com.prosoteam.proso.global.oauth.service;

import com.prosoteam.proso.global.common.ErrorCode;
import com.prosoteam.proso.global.common.exception.BaseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private String secretKey = "e7yF1Exi8bUKTAfkT39Dq2yVKaoCsC6pN/ZaApWjQJI=";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String generateToken(Long socialId){
        long tokenPeriod = 1000L * 60L * 10L;

        Claims claims = Jwts.claims().setSubject(String.valueOf(socialId));

        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateRefreshToken(Long socialId){
        long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L;

        Claims claims = Jwts.claims().setSubject(String.valueOf(socialId));
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date((now.getTime() + refreshPeriod)))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public void validateRefreshToken(String refreshToken) throws BaseException {
        Date exp = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken).getBody().getExpiration();

        Date now = new Date();

        if (!exp.after(now)) {
            throw new BaseException(ErrorCode.INVALID_JWT);
        }
    }


}
