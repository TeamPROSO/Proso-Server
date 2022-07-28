package com.prosoteam.proso.global.oauth.controller;

import com.prosoteam.proso.global.common.CommonResponse;
import com.prosoteam.proso.global.oauth.dto.AuthRequest;
import com.prosoteam.proso.global.oauth.dto.AuthResponse;
import com.prosoteam.proso.global.oauth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * KAKAO 소셜 로그인
     * @return CommonResponse<AuthResponse>
     */
    @PostMapping("/kakao")
    public CommonResponse<AuthResponse> kakaoAuthRequest(@RequestBody AuthRequest authRequest){
        return CommonResponse.success(authService.login(authRequest));
    }
    /**
     * APPLE 소셜 로그인
     * @return CommonResponse<AuthResponse>
     */

    /**
     * 앱 토큰 갱신
     * @return CommonResponse<AuthResponse>
     */
    /*
    @GetMapping("/refresh")
    public CommonResponse<AuthResponse> refreshToken (HttpServletRequest request) {
        String appToken = JwtHeaderUtil.getAccessToken(request);
        AuthToken authToken = authTokenProvider.convertAuthToken(appToken);
        if (!authToken.validate()) { // 형식에 맞지 않는 token
            return CommonResponse.error(null);
        }

        AuthResponse authResponse = authService.updateToken(authToken);
        if (authResponse == null) { // token 만료
            return CommonResponse.error(null);
        }
        return CommonResponse.success(authResponse);
    }
    */
     
}
