package com.prosoteam.proso.global.config.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoService {

    public String getAccessTokenByCode(String code) throws JsonProcessingException { //코드가지고 엑세스 토큰 요청하기

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","");
        params.add("redirect_uri","");
        params.add("code",code);
        params.add("client_secret","");

        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(params,headers);

        String url = "https://kauth.kakao.com/oauth/token";

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        ObjectMapper objectMapper=new ObjectMapper();
        OAuth2Token oAuth2Token = objectMapper.readValue(response.getBody(), OAuth2Token.class);

        return oAuth2Token.getTokenValue();
    }

    public String getUserInfoByAccessToken(String accessToken) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        String url = "https://kapi.kakao.com/v2/user/me";
        return restTemplate.postForObject(url, request, String.class);
    }
}
