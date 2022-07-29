package com.prosoteam.proso.global.oauth.service;

import com.prosoteam.proso.global.common.ErrorCode;
import com.prosoteam.proso.global.common.exception.BaseException;
import com.prosoteam.proso.global.common.exception.TokenValidFailedException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${secret.access}")
    private String secretKey;


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



    public Claims getClaimsToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token)
                .getBody();
    }

    //토큰 유효성 검사
    //TODO 예외 처리 ! 특히 refresh token이 만료 됐을 떄는 refresh token도 재발행 해줘야함.
    public boolean isValidToken(String token) {
        log.info("isValidToken is : " + token);
        try {
            Claims accessClaims = getClaimsToken(token);
            log.info("Access expireTime: " + accessClaims.getExpiration());
            log.info("Access socialId: " + accessClaims.get("socialId"));
            return true;
        } catch (ExpiredJwtException exception) {
            //기간 만료됨
            log.error("Token Expired UserID : " + exception.getClaims().getSubject());
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return false;
        }
    }


    //Request의 Header에서 token값 가져오기 "ACCESS_TOKEN" : "TOKEN값'
    public String resolveAccessToken(HttpServletRequest request){
        return request.getHeader("ACCESS_TOKEN");
    }

    public String resolveRefreshToken(HttpServletRequest request){
        return request.getHeader("REFRESH_TOKEN");
    }

    //토큰에서 회원 정보 추출
    public Long getUserSocialId(String token) throws TokenValidFailedException {
        if(isValidToken(token)) {
            return Long.parseLong(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject());
        }
        return null;
    }


}
