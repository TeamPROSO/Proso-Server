package com.prosoteam.proso.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class UserResponse {
    private final Long socialId;
    private final String userName;
    private final String profileImgUrl;
}
