package com.prosoteam.proso.domain.user.controller;

import com.prosoteam.proso.domain.user.dto.ExtraUserRequest;
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
@RequestMapping("/user")
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;


    /**
     * 로그인한 유저 정보 얻기
     * @param jwtToken
     * @return
     */
    @PostMapping("")
    public CommonResponse<UserResponse> GetCurrentUserInfo(@RequestHeader("Authorization") String jwtToken) {
        // jwt 복호화. user정보 얻기 //TODO 예외처리 적용하기
        Long socialId = jwtTokenProvider.getUserSocialId(jwtToken);
        return CommonResponse.success(userService.findBySocialId(socialId));
    }


    /**
     * 유저 회원 가입시 추가 정보 얻어서 업데이트하기
     * 일단 회원 가입 자체는 완료 된 상황 -> 그래서 jwt 토큰은 발급 받음. 추가적인 정보를 입력한다고 보면 됨
     * 정보 수정이나 마찬가지 .
     * 둘 다 수정 하지 않는 경우는 요청을 보내지 않음. 둘중에 하나 또는 둘다 수정하는 경우만 요청 보내기
     */
    @PostMapping("/extra")
    public CommonResponse<UserResponse> updateExtraInfo(@RequestHeader("Authorization") String jwtToken,@RequestBody ExtraUserRequest extraUserRequest){
        Long socialId = jwtTokenProvider.getUserSocialId(jwtToken);
        return CommonResponse.success(userService.addExtraUserInfo(socialId,extraUserRequest));
    }
}
