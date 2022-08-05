package com.prosoteam.proso.domain.main.service;

import com.prosoteam.proso.domain.main.dto.KakaoMapResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoMapClient {
    @Value("${secret.api-key}")
    private String key;
    private final WebClient webClient;


    /**
     * code : FD6
     * "https://dapi.kakao.com/v2/local/search/category.json?category_group_code={카테고리 코드 입력}&x={현재위치 경도}&y={현재 위치 위도}&radius={현재위치로부터 반경설정}

     */
    public KakaoMapResponse getNearFoodInfo(String x,String y,int page) {
        KakaoMapResponse kakaoMapResponse = webClient.get()
                .uri("https://dapi.kakao.com/v2/local/search/category.json?category_group_code=FD6&x="+x+"&y="+y+"&radius=10000&page="+page)
                .header("Authorization","KakaoAK "+key)
                .retrieve()
                .bodyToMono(KakaoMapResponse.class)
                .block();
        return kakaoMapResponse;
    }
    /**
     * code : CE7
     * "https://dapi.kakao.com/v2/local/search/category.json?category_group_code={카테고리 코드 입력}&x={현재위치 경도}&y={현재 위치 위도}&radius={현재위치로부터 반경설정}

     */
    public KakaoMapResponse getNearCafeInfo(String x,String y,int page) {
        KakaoMapResponse kakaoMapResponse = webClient.get()
                .uri("https://dapi.kakao.com/v2/local/search/category.json?category_group_code=CE7&x="+x+"&y="+y+"&radius=10000&page="+page)
                .header("Authorization","KakaoAK "+key)
                .retrieve()
                .bodyToMono(KakaoMapResponse.class)
                .block();
        return kakaoMapResponse;
    }

    /**
     * food code FD6
     * cafe code CE7
     * activity code CT1 , AT4
     * "https://dapi.kakao.com/v2/local/search/category.json?category_group_code={카테고리 코드 입력}&x={현재위치 경도}&y={현재 위치 위도}&radius={현재위치로부터 반경설정}

     */
    public KakaoMapResponse getNearInfo(String x,String y,String code,int page) {
        KakaoMapResponse kakaoMapResponse = webClient.get()
                .uri("https://dapi.kakao.com/v2/local/search/category.json?category_group_code="+code+"&x="+x+"&y="+y+"&radius=10000&page="+page)
                .header("Authorization","KakaoAK "+key)
                .retrieve()
                .bodyToMono(KakaoMapResponse.class)
                .block();
        return kakaoMapResponse;
    }
}
