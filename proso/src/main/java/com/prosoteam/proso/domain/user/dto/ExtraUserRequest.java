package com.prosoteam.proso.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExtraUserRequest {
    private String userName;
    private String profileImgUrl;
}
