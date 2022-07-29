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
    public AuthResponse updateToken(HttpServletRequest request) {
        String accessToken = jwtAuthTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtAuthTokenProvider.resolveRefreshToken(request);
        System.out.println("accessToken = " + accessToken);
        System.out.println("refreshToken = " + refreshToken);
        //accessToken이 만료됐고 refreshToken이 맞으면 accessToken을 새로 발급(refreshToken의 내용을 통해서)

        if (jwtAuthTokenProvider.isValidToken(refreshToken)) {     //들어온 Refresh 토큰이 유효한지
            System.out.println("Refresh 토큰은 유효함");

            Claims claimsToken = jwtAuthTokenProvider.getClaimsToken(refreshToken);
            Long socialId = Long.parseLong( (String) claimsToken.get("socialId"));

            Optional<User> user = userRepository.findBySocialId(socialId);
            String savedRefreshToken = user.get().getRefreshToken();
            System.out.println("tokenFromDB = " + savedRefreshToken);

            if (refreshToken.equals(savedRefreshToken)) {   //DB의 refresh토큰과 지금들어온 토큰이 같은지 확인
                System.out.println("access 토큰 재발급 완료");
                accessToken = jwtAuthTokenProvider.generateToken(socialId);
            } else {
                //DB의 Refresh토큰과 들어온 Refresh토큰이 다르면 중간에 변조된 것임
                System.out.println("Refresh Token Tampered");
                //예외발생
            }
        } else {
            //입력으로 들어온 Refresh 토큰이 유효하지 않음
        }

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


}
