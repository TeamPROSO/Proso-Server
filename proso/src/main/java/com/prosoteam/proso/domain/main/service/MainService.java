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

import java.util.ArrayList;
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
        ArrayList<MainRandomResponse> list = new ArrayList<>();
        int page = 0;

        while (true) {
            page++;
            log.info("페이지" + page);
            KakaoMapResponse kakaoMapResponse = kakaoMapClient.getNearFoodInfo(userCurrentRequest.getX(), userCurrentRequest.getY(), page);
            if (kakaoMapResponse.getDocuments().isEmpty()) {//예외처리->아무것도 안담겨온경우
                throw new BaseException(ErrorCode.MAIN_RESPONSE_EMPTY);
            }
            if (kakaoMapResponse.getMeta().getIsEnd()) { //종료조건
                list.add(getRandomOne(kakaoMapResponse));
                log.info("리스트 size " + list.size());
                int resultNum = getFinalResult(list.size());
                return list.get(resultNum);
            }
            list.add(getRandomOne(kakaoMapResponse));
        }
    }
    /**
     * 현재위치 기반 주변 카페 리스트 저장 함수.
     */
    public MainRandomResponse getNearCafeInfo(UserCurrentRequest userCurrentRequest){
        ArrayList<MainRandomResponse> list = new ArrayList<>();
        int page = 0;

        while (true) {
            page++;
            log.info("페이지" + page);
            KakaoMapResponse kakaoMapResponse = kakaoMapClient.getNearCafeInfo(userCurrentRequest.getX(), userCurrentRequest.getY(), page);
            if (kakaoMapResponse.getDocuments().isEmpty()) {//예외처리->아무것도 안담겨온경우
                throw new BaseException(ErrorCode.MAIN_RESPONSE_EMPTY);
            }
            if (kakaoMapResponse.getMeta().getIsEnd()) { //종료조건
                list.add(getRandomOne(kakaoMapResponse));
                log.info("리스트 size " + list.size());
                int resultNum = getFinalResult(list.size());
                return list.get(resultNum);
            }
            list.add(getRandomOne(kakaoMapResponse));
        }

    }
    /**
     * 현재위치 기반 주변 리스트 저장 함수.
     * food code FD6
     * cafe code CE7
     * activity code CT1 , AT4
     */
    public MainRandomResponse getResultList(String x,String y, String code){
        ArrayList<MainRandomResponse> list = new ArrayList<>();
        int page = 0;

        while (true) {
            page++;
            log.info("페이지" + page);
            KakaoMapResponse kakaoMapResponse = kakaoMapClient.getNearInfo(x, y, code, page);
            if (kakaoMapResponse.getDocuments().isEmpty()) {//예외처리->아무것도 안담겨온경우
                throw new BaseException(ErrorCode.MAIN_RESPONSE_EMPTY);
            }
            if (kakaoMapResponse.getMeta().getIsEnd()) { //종료조건
                list.add(getRandomOne(kakaoMapResponse));
                log.info("리스트 size " + list.size());
                int resultNum = getFinalResult(list.size());
                return list.get(resultNum);
            }
            list.add(getRandomOne(kakaoMapResponse));
        }
    }

    public MainRandomResponse getNearInfo(UserCurrentRequest userCurrentRequest) {
        ArrayList<MainRandomResponse> list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    list.add(getResultList(userCurrentRequest.getX(), userCurrentRequest.getY(), "FD6"));
                    break;
                case 1:
                    list.add(getResultList(userCurrentRequest.getX(), userCurrentRequest.getY(), "CE7"));
                    break;
                case 2:
                    list.add(getResultList(userCurrentRequest.getX(), userCurrentRequest.getY(), "CT1"));
                    break;
                case 3:
                    list.add(getResultList(userCurrentRequest.getX(), userCurrentRequest.getY(), "AT4"));
                    break;
            }

        }
        log.info("리스트 size " + list.size());
        int resultNum = getFinalResult(list.size());
        return list.get(resultNum);
    }

    /**
     * kakaoMapResponse 기반으로 랜덤한 값 1개 뽑는 함수.
     */
    public MainRandomResponse getRandomOne(KakaoMapResponse kakaoMapResponse) {
        Random generator = new Random();
        int size = kakaoMapResponse.getDocuments().size();
        int selectNum = generator.nextInt(size);

        Documents documents = kakaoMapResponse.getDocuments().get(selectNum);
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

    /**
     * 저장된 List 기반으로 최종 값 1개 뽑는 함수
     */
    public int getFinalResult(int size) {
        Random generator = new Random();
        int selectNum = generator.nextInt(size);

        return selectNum;
    }

}
