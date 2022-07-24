package com.prosoteam.proso.global.config.jwt;

import com.prosoteam.proso.global.config.jwt.UserDto;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {
    public UserDto toDto(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        return UserDto.builder()
                .userName((String)attributes.get("userName"))
                .pictureImgUrl((String)attributes.get("pictureImgUrl"))
                .socialId((Long) attributes.get("socialId"))
                .socialType((String) attributes.get("socialType"))
                .build();
    }

}
