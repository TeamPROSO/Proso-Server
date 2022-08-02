package com.prosoteam.proso.domain.main.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.prosoteam.proso.domain.main.dto.Documents;
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

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class MainService {

    private final KakaoMapClient kakaoMapClient;
    /**
     * 현재위치 기반 주변 식당 리스트 저장 함수.
     */
    public MainRandomResponse getNearFoodInfo(UserCurrentRequest userCurrentRequest) {
        KakaoMapResponse kakaoMapResponse=kakaoMapClient.getNearFoodInfo("edcd891623ddb3df5598da9c63c5fd75", userCurrentRequest.getX(), userCurrentRequest.getY());
        if (kakaoMapResponse.getDocuments().isEmpty()) { //예외처리
            //
        }
        return getRandomOne(kakaoMapResponse);
    }

    /**
     * 저장된 리스트 기반으로 랜덤한 값 1개 뽑는 함수.
     */
    public MainRandomResponse getRandomOne(KakaoMapResponse kakaoMapResponse){
        Random generator = new Random();
        int size = kakaoMapResponse.getDocuments().size();
        int selectNum= generator.nextInt(size);

        Documents documents =kakaoMapResponse.getDocuments().get(selectNum);
        return MainRandomResponse.builder()
                .address_name(documents.getAddressName())
                .category_group_code(documents.getCategoryGroupCode())
                .category_group_name(documents.getCategoryGroupName())
                .id(documents.getId())
                .distance(documents.getDistance())
                .place_name(documents.getPlaceName())
                .place_url(documents.getPlaceUrl())
                .road_address_name(documents.getRoadAddressName())
                .x(documents.getX())
                .y(documents.getY())
                .build();
        
    }
}
