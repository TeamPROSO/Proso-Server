package com.prosoteam.proso.global.oauth.service;

import com.prosoteam.proso.domain.user.entity.User;
import com.prosoteam.proso.domain.user.repository.UserRepository;
import com.prosoteam.proso.global.common.ErrorCode;
import com.prosoteam.proso.global.common.exception.BaseException;
import com.prosoteam.proso.global.oauth.dto.AuthRequest;
import com.prosoteam.proso.global.oauth.dto.AuthResponse;
import com.prosoteam.proso.global.oauth.dto.KakaoResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
        Optional<User> findUser= userRepository.findBySocialId(socialId);

        if(findUser.isEmpty()){ //회윈가입이 안된 경우
            log.info("저장되었습니다.");
            userRepository.save(userInfo);
        }
        //서버용 jwt 토큰 발급

        String accessToken= jwtAuthTokenProvider.generateToken(socialId);
        String refreshToken= jwtAuthTokenProvider.generateRefreshToken(socialId);

        Optional<User> user = userRepository.findBySocialId(socialId)
                .map(entity -> entity.updateRefreshToken(refreshToken));


        log.info("업데이트된 리프레쉬 토큰"+ user.get().getRefreshToken());
        userRepository.flush();
        //로그인 처리 완료
        return AuthResponse.builder()
                .socialId(socialId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    /**
     *
     * access_token이 만료 되었을때 refresh token을 이용해 재발급
     */
    //TODO 예외 처리
    public AuthResponse updateToken(HttpServletRequest request) {
        Long socialId=1L;
        String accessToken = jwtAuthTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtAuthTokenProvider.resolveRefreshToken(request);
        log.info("accessToken = " + accessToken);
        log.info("refreshToken = " + refreshToken);

        //accessToken이 만료됐고 refresh Token이 validate하면 accessToken을 새로 발급(refreshToken의 내용을 통해서)
        if (jwtAuthTokenProvider.isValidToken(refreshToken)) {
            log.info("Refresh 토큰은 유효함");

            Claims claimsToken = jwtAuthTokenProvider.getClaimsToken(refreshToken);
            socialId = Long.parseLong((String) claimsToken.getSubject());
            log.info("갱신을 요청한 유저 : " + socialId);

            Optional<User> user = userRepository.findBySocialId(socialId);
            String savedRefreshToken = user.get().getRefreshToken();
            log.info("저장돼있던 Refresh Token = " + savedRefreshToken);

            if (refreshToken.equals(savedRefreshToken)) {   //DB의 refresh토큰과 지금들어온 토큰이 같은지 확인
                log.info("access 토큰 재발급 완료");
                accessToken = jwtAuthTokenProvider.generateToken(socialId);
            } else {
                //DB의 Refresh토큰과 들어온 Refresh토큰이 다르면 중간에 변조된 것
                log.error("Refresh Token Tampered");
                //예외 처리
                return null;
            }
        } else {
            log.error("Refresh token not validate");
            return null;
            //입력으로 들어온 Refresh 토큰이 유효하지 않음 . refresh 토큰 기간 만료된 경우는 제외 . 예외처리
        }

        return AuthResponse.builder()
                .socialId(socialId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


}
