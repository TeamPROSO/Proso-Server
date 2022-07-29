package com.prosoteam.proso.domain.user.service;

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
}
