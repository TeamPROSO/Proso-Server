package com.prosoteam.proso.global.config.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialService {

    private final KakaoService kakaoService;
    private final ObjectMapper objectMapper;

    public SocialDto verificationKakao(String code) throws JsonProcessingException {

        SocialDto socialDto = new SocialDto();
        // 코드를 이용하여 accessToken 추출
        String accessToken = kakaoService.getAccessTokenByCode(code);
        // accessToken을 이용하여 사용자 정보 추출
        String userInfo = kakaoService.getUserInfoByAccessToken(accessToken);

        try {
            JsonNode jsonNode = objectMapper.readTree(userInfo);
            String name = String.valueOf(jsonNode.get("kakao_account").get("profile").get("nickname"));
            socialDto.setName(name.substring(1, name.length() - 1));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return socialDto;
    }
}
