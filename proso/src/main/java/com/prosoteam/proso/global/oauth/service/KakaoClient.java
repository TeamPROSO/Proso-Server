package com.prosoteam.proso.global.oauth.service;

import com.prosoteam.proso.domain.user.entity.User;
import com.prosoteam.proso.global.common.CommonResponse;
import com.prosoteam.proso.global.common.ErrorCode;
import com.prosoteam.proso.global.common.exception.BaseException;
import com.prosoteam.proso.global.common.exception.TokenValidFailedException;
import com.prosoteam.proso.global.oauth.dto.KakaoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new TokenValidFailedException(ErrorCode.INVALID_ACCESS_TOKEN)))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new TokenValidFailedException(ErrorCode.INVALID_ACCESS_TOKEN)))
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
                .status("ACTIVEK")
                .role("ROLE_USER")
                .build();
    }

    /**
     * 로그아웃
     */
    public String kakaoLogout(String accessToken) {
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        String result="";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}

