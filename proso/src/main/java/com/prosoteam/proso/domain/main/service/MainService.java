package com.prosoteam.proso.domain.main.service;


import com.prosoteam.proso.domain.main.dto.KakaoMapResponse;
import com.prosoteam.proso.domain.main.dto.MainRandomResponse;
import com.prosoteam.proso.domain.main.dto.UserCurrentRequest;
import com.prosoteam.proso.domain.user.dto.UserResponse;
import com.prosoteam.proso.domain.user.entity.User;
import com.prosoteam.proso.global.common.ErrorCode;
import com.prosoteam.proso.global.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainService {

    private final KakaoMapClient kakaoMapClient;
    /**
     * 현재위치 기반 주변 식당 리스트 저장 함수.
     */
    public void getNearFoodInfo(UserCurrentRequest userCurrentRequest) {
        KakaoMapResponse kakaoMapResponse=kakaoMapClient.getNearFoodInfo("edcd891623ddb3df5598da9c63c5fd75", userCurrentRequest.getX(), userCurrentRequest.getY());
        if (kakaoMapResponse.getDocuments().isEmpty()) { //예외처리
            //
        }
        return UserResponse.builder()
                .socialId(socialId)
                .userName(user.get().getUserName())
                .profileImgUrl(user.get().getProfileImgUrl())
                .build();
    }

    /**
     * 저장된 리스트 기반으로 랜덤한 값 1개 뽑는 함수.
     */
    public MainRandomResponse getRandomOne(KakaoMapResponse kakaoMapResponse){
        int size = kakaoMapResponse.getDocuments().size();
        
    }
}
