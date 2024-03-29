package com.prosoteam.proso.global.oauth.controller;

import com.prosoteam.proso.global.common.CommonResponse;
import com.prosoteam.proso.global.oauth.dto.AuthRequest;
import com.prosoteam.proso.global.oauth.dto.AuthResponse;
import com.prosoteam.proso.global.oauth.service.AuthService;
import com.prosoteam.proso.global.oauth.service.KakaoClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final KakaoClient kakaoClient;

    /**
     * KAKAO 소셜 로그인
     * @return CommonResponse<AuthResponse>
     */
    @PostMapping("/kakao")
    public CommonResponse<AuthResponse> kakaoAuthRequest(@RequestBody AuthRequest authRequest){
        return CommonResponse.success(authService.login(authRequest));
    }

    /**
     * KAKAO 소셜 로그아웃
     */
    @PostMapping("/klogout")
    public CommonResponse<String> kakaoLogout(@RequestBody AuthRequest authRequest){
        return CommonResponse.success(kakaoClient.kakaoLogout(authRequest.getAccessToken()));
    }

    /**
     * APPLE 소셜 로그인
     * @return CommonResponse<AuthResponse>
     */


    /**
     * 앱 토큰 갱신
     * @return CommonResponse<AuthResponse>
     *    리프레쉬 토큰 만료 -> access 토큰 , 리프레쉬 토큰 둘 다 갱신
     *    리프레쉬 토큰 유효 -> refresh token 이용해 access 토큰만 갱신
     */

    @PostMapping("/refresh")
    public CommonResponse<AuthResponse> refreshToken (HttpServletRequest request) {
        AuthResponse authResponse =authService.updateToken(request);
        return CommonResponse.success(authResponse);
    }


}
