package com.prosoteam.proso.global.oauth.service;

import com.prosoteam.proso.domain.user.entity.User;
import com.prosoteam.proso.global.common.CommonResponse;
import com.prosoteam.proso.global.common.ErrorCode;
import com.prosoteam.proso.global.common.exception.TokenValidFailedException;
import com.prosoteam.proso.global.oauth.dto.KakaoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoClient {
    private final WebClient webClient;


    public User getUserInfo(String accessToken) {
        KakaoResponse kakaoResponse = webClient.get()
                .uri("https://kapi.kakao.com/v2/user/me")
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException()))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException()))
                .bodyToMono(KakaoResponse.class)
                .block();


        log.info("kakaoUserResponse: "+kakaoResponse.getId());
        log.info("kakaoUserResponse: "+kakaoResponse.getProperties().getNickname());
        log.info("kakaoUserResponse: "+kakaoResponse.getProperties().getProfile_image());
        return User.builder()
                .socialId(kakaoResponse.getId())
                .userName(kakaoResponse.getProperties().getNickname())
                .socialType("KAKAO")
                .profileImgUrl(kakaoResponse.getProperties().getProfile_image())
                .build();
    }
}

