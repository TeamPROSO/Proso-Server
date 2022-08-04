package com.prosoteam.proso.domain.user.service;

import com.prosoteam.proso.domain.user.dto.ExtraUserRequest;
import com.prosoteam.proso.domain.user.dto.UserResponse;
import com.prosoteam.proso.domain.user.entity.User;
import com.prosoteam.proso.domain.user.repository.UserRepository;
import com.prosoteam.proso.global.common.CommonResponse;
import com.prosoteam.proso.global.common.ErrorCode;
import com.prosoteam.proso.global.common.exception.BaseException;
import com.prosoteam.proso.global.oauth.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse findBySocialId(Long socialId) {
        Optional<User> user = userRepository.findBySocialId(socialId);
        if (user.isEmpty()) {
            throw new BaseException(ErrorCode.USERS_EMPTY_USER_ID);
        }
        return UserResponse.builder()
                .socialId(socialId)
                .userName(user.get().getUserName())
                .profileImgUrl(user.get().getProfileImgUrl())
                .build();
    }

    public String getUserRole(Long socialId) {
        Optional<User> user = userRepository.findBySocialId(socialId);
        if (user.isEmpty()) {
            throw new BaseException(ErrorCode.USERS_EMPTY_USER_ID);
        }
        return user.get().getRole();
    }

    public UserResponse addExtraUserInfo(Long socialId, ExtraUserRequest extraUserRequest){
        Optional<User> user= userRepository.findBySocialId(socialId);
        if (user.isEmpty()) {
            throw new BaseException(ErrorCode.USERS_EMPTY_USER_ID);
        }
        String userName= extraUserRequest.getUserName();
        String profileImgUrl= extraUserRequest.getProfileImgUrl();
        log.info("바꾸려는 닉네임 : "+userName);
        log.info("바꾸려는 프사 : "+profileImgUrl);

        if (userName==null){
            user.get().updateProfileImgUrl(profileImgUrl);
        }else if(profileImgUrl==null){
            user.get().updateUserName(userName);
        }else{
            user.get().updateBoth(userName,profileImgUrl);
        }
        log.info("업데이트 성공: "+ user.get().getSocialId());
        userRepository.flush();

        return UserResponse.builder()
                .socialId(socialId)
                .userName(user.get().getUserName())
                .profileImgUrl(user.get().getProfileImgUrl())
                .build();
    }

}
