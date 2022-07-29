package com.prosoteam.proso.domain.user.controller;

import com.prosoteam.proso.domain.user.dto.UserResponse;
import com.prosoteam.proso.domain.user.entity.User;
import com.prosoteam.proso.domain.user.service.UserService;
import com.prosoteam.proso.global.common.CommonResponse;
import com.prosoteam.proso.global.common.ErrorCode;
import com.prosoteam.proso.global.common.exception.BaseException;
import com.prosoteam.proso.global.oauth.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;


    /**
     * 로그인한 유저 정보 얻기
     * @param jwtToken
     * @return
     */
    @PostMapping("/user")
    public CommonResponse<UserResponse> CurrentUser(@RequestHeader("Authorization") String jwtToken) {
        // jwt 복호화. user정보 얻기
        Long socialId = jwtTokenProvider.getUserSocialId(jwtToken);
        return CommonResponse.success(userService.findBySocialId(socialId));
    }
}
