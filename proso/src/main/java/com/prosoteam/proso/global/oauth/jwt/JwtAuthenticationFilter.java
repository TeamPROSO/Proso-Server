package com.prosoteam.proso.global.oauth.jwt;

import com.prosoteam.proso.domain.user.entity.User;
import com.prosoteam.proso.global.oauth.service.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Request로 들어오는 Jwt Token의 유효성을 검증하는 filter를 filter Chain에 등록
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.getAccessTokenHeader((HttpServletRequest) servletRequest);
        if (token != null && jwtTokenProvider.isValidToken(token)) {   // token 검증
            Authentication authentication= jwtTokenProvider.getAuthentication(token);
            log.info("권한"+authentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContextHolder에 인증 객체 저장
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}