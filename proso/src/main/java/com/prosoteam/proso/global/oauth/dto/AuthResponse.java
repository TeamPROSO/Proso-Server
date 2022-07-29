package com.prosoteam.proso.global.oauth.dto;

import com.prosoteam.proso.domain.user.entity.User;
import lombok.*;

import java.util.List;

/**
 * 프론트로 넘겨줄 jwt 토큰 두개
 */
@Getter
@RequiredArgsConstructor
@Builder
public class AuthResponse {
    private final Long socialId;
    private final String accessToken;
    private final String refreshToken;

}
