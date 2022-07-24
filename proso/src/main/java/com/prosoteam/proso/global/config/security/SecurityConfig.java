package com.prosoteam.proso.global.config.security;

import com.prosoteam.proso.global.config.jwt.OAuth2SuccessHandler;
import com.prosoteam.proso.global.config.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity  // 해당 애노테이션을 붙인 필터(현재 클래스)를 스프링 필터체인에 등록.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2SuccessHandler successHandler;
    // 커스텀한 OAuth2UserService DI.
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;


    // WebSecurity에 필터를 거는 게 훨씬 빠름. HttpSecrity에 필터를 걸면, 이미 스프링 시큐리티 내부에 들어온 상태기 때문에..
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/members/**", "/image/**");    // /image/** 있는 모든 파일들은 시큐리티 적용을 무시한다.
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());    // 정적인 리소스들에 대해서 시큐리티 적용 무시.
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()//이거 추가
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //이거 추가
                .and()
                .authorizeRequests()
                //.antMatchers("/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .successHandler(successHandler)
                //.defaultSuccessUrl("/login-success")
                .userInfoEndpoint()
                .userService(customOAuth2UserService);   // oauth2 로그인에 성공하면, 유저 데이터를 가지고 우리가 생성한
        // customOAuth2UserService에서 처리를 하겠다. 그리고 "/login-success"로 이동하라.


    }
}