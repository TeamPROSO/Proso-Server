package com.prosoteam.proso.global.oauth.service;

import com.prosoteam.proso.domain.user.entity.User;
import com.prosoteam.proso.domain.user.repository.UserRepository;
import com.prosoteam.proso.global.oauth.dto.AuthRequest;
import com.prosoteam.proso.global.oauth.dto.AuthResponse;
import com.prosoteam.proso.global.oauth.dto.KakaoResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final KakaoClient kakaoClient;
    private final JwtTokenProvider jwtAuthTokenProvider;

    public  AuthResponse login(AuthRequest request){
        User userInfo=  kakaoClient.getUserInfo(request.getAccessToken());
        Long socialId=userInfo.getSocialId();
        Optional<User> user = userRepository.findBySocialId(socialId);
        log.info("유저 : "+ user.isEmpty());
        if(user.isEmpty()){ //회윈가입이 안된 경우
            log.info("저장되었습니다.");
            userRepository.save(userInfo);
        }
        //서버용 jwt 토큰 발급

        String accessToken= jwtAuthTokenProvider.generateToken(socialId);
        String refreshToken= jwtAuthTokenProvider.generateRefreshToken(socialId);

        return AuthResponse.builder()
                .socialId(socialId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

/*
    public AuthResponse updateToken(AuthToken authToken) {
        Claims claims = authToken.getTokenClaims();
        if (claims == null) {
            return null;
        }

        Long socialId = Long.parseLong(claims.getSubject());

        AuthToken newAppToken = authTokenProvider.createUserAppToken(socialId);
        AuthToken newRefreshToken = authTokenProvider.createUserRefreshToken(socialId);

        return AuthResponse.builder()
                .appToken(newAppToken.getToken())
                .refreshToken(newRefreshToken.getToken())
                .build();
    }
    */


}
