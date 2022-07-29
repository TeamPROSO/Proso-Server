package com.prosoteam.proso.global.oauth.controller;

import com.prosoteam.proso.domain.user.entity.User;
import com.prosoteam.proso.global.common.CommonResponse;
import com.prosoteam.proso.global.oauth.JwtHeaderUtil;
import com.prosoteam.proso.global.oauth.dto.AuthRequest;
import com.prosoteam.proso.global.oauth.dto.AuthResponse;
import com.prosoteam.proso.global.oauth.service.AuthService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/refresh")
    public CommonResponse<AuthResponse> refreshToken (HttpServletRequest request) {
        return CommonResponse.success(authService.updateToken(request));
    }


}
