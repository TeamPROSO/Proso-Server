package com.prosoteam.proso.global.config.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {
    private String userName;
    private String pictureImgUrl;
    private Long socialId;
    private String socialType;


    @Builder
    public UserDto(String userName, String pictureImgUrl,Long socialId ,String socialType) {
        this.userName = userName;
        this.pictureImgUrl = pictureImgUrl;
        this.socialId=socialId;
        this.socialType=socialType;
    }
}
