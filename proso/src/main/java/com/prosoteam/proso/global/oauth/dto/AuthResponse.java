package com.prosoteam.proso.global.oauth.dto;

import lombok.*;

/**
 * 프론트로 넘겨줄 jwt 토큰 두개
 */
@Getter
@RequiredArgsConstructor
@Builder
public class AuthResponse {
    private final String accessToken;
    private final String refreshToken;

}
