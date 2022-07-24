package com.prosoteam.proso.global.config.oauth;

import com.prosoteam.proso.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String userName;
    private String profileImgUrl;
    private Long socialId;
    private String socialType;


    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String userName, String profileImgUrl, Long socialId, String socialType) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.userName = userName;
        this.profileImgUrl = profileImgUrl;
        this.socialId = socialId;
        this.socialType = socialType;
    }

    public OAuthAttributes() {
    }


    // 해당 로그인인 서비스가 kakao인지 google인지 구분하여, 알맞게 매핑을 해주도록 합니다.
    // 여기서 registrationId는 OAuth2 로그인을 처리한 서비스 명("google","kakao"..)이 되고,
    // userNameAttributeName은 해당 서비스의 map의 키값이 되는 값이됩니다. {google="sub", kakao="id"}
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if (registrationId.equals("kakao")) {
            return ofKakao(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes); //구글대신 이제 애플이 들어가는..
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");  // 카카오로 받은 데이터에서 계정 정보가 담긴 kakao_account 값을 꺼낸다.
        Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");   // 마찬가지로 profile(nickname, image_url.. 등) 정보가 담긴 값을 꺼낸다.

        return new OAuthAttributes(attributes,
                userNameAttributeName,
                (String) profile.get("nickname"),
                (String) profile.get("profile_img_url"),
                (Long) attributes.get("id"),
                "KAKAO"
        );
    }


    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        return new OAuthAttributes(attributes,
                userNameAttributeName,
                (String) attributes.get("name"),
                (String) attributes.get("email"),
                121231l,
                "GOOGLE"

        );
    }

    // .. getter/setter 생략

    public User toEntity() {
        return new User(userName, profileImgUrl, socialId, socialType);
    }
}