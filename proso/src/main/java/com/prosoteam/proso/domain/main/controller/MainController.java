package com.prosoteam.proso.domain.main.controller;

import com.prosoteam.proso.domain.main.dto.MainRandomResponse;
import com.prosoteam.proso.domain.main.dto.UserCurrentRequest;
import com.prosoteam.proso.domain.main.service.KakaoMapClient;
import com.prosoteam.proso.domain.main.service.MainService;
import com.prosoteam.proso.global.common.CommonResponse;
import com.prosoteam.proso.global.oauth.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {
    private final JwtTokenProvider jwtTokenProvider;
    private final MainService mainService;

    @GetMapping("/food")
    public CommonResponse<MainRandomResponse> GetRandomFood(@RequestParam String x,@RequestParam String y){
        return CommonResponse.success(mainService.getNearFoodInfo(x,y));
    }

    @GetMapping("/cafe")
    public CommonResponse<MainRandomResponse> GetRandomCafe(@RequestParam String x,@RequestParam String y){
        return CommonResponse.success(mainService.getNearCafeInfo(x,y));
    }


    @GetMapping("/all")
    public CommonResponse<MainRandomResponse> GetRandomAll(@RequestParam String x,@RequestParam String y){
        return CommonResponse.success(mainService.getNearInfo(x,y));
    }

}
