package com.prosoteam.proso.domain.main.service;

import com.prosoteam.proso.domain.main.dto.KakaoMapResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoMapClient {
    private final WebClient webClient;


    /**
     * code : FD6
     * "https://dapi.kakao.com/v2/local/search/category.json?category_group_code={카테고리 코드 입력}&x={현재위치 경도}&y={현재 위치 위도}&radius={현재위치로부터 반경설정}
        TODO: KEY yml 파일에서 받아오도록
     */
    public KakaoMapResponse getNearFoodInfo(String key,String x,String y) {
        KakaoMapResponse kakaoMapResponse = webClient.get()
                .uri("https://dapi.kakao.com/v2/local/search/category.json?category_group_code=FD6&x=126.84348895334325&y=37.53013286771751&radius=10000")
                .header("Authorization","KakaoAK "+key)
                .retrieve()
                .bodyToMono(KakaoMapResponse.class)
                .block();

        log.info("kakaoMapResponse: "+kakaoMapResponse.getDocuments().get(0).getPlaceName());
        return kakaoMapResponse;
    }

}
