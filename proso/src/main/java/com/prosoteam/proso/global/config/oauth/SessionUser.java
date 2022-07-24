package com.prosoteam.proso.global.config.oauth;


import com.prosoteam.proso.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SessionUser implements Serializable {
    private String name;
    private String profileImgUrl;
    private Long socialId;
    private String socialType;

    public SessionUser(User user) {
        this.name = user.getUserName();
        this.profileImgUrl = user.getProfileImgUrl();
        this.socialId=user.getSocialId();
        this.socialType=user.getSocialType();
    }

    public SessionUser() {
    }

}