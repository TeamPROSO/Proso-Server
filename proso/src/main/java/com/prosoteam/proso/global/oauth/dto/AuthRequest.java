package com.prosoteam.proso.global.oauth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프론트에서 넘겨주는 accesstoken.
 */
@Getter
@NoArgsConstructor
public class AuthRequest {
    private String accessToken;
}
