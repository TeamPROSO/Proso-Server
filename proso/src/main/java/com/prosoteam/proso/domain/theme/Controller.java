package com.prosoteam.proso.domain.theme;

import com.prosoteam.proso.domain.theme.model.Theme;
import com.prosoteam.proso.domain.theme.model.ThemeCreationRequest;
import com.prosoteam.proso.global.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.prosoteam.proso.global.common.CommonResponse;

@RestController
@RequestMapping(value = "/api/themes")
@RequiredArgsConstructor
public class Controller {
    private final ThemeService themeService;

    //테마 생성
    @PostMapping("/theme")
    public CommonResponse<Theme> createTheme (@RequestBody ThemeCreationRequest request) {
        return CommonResponse.success(themeService.createTheme(request));
    }

    //테마 조회(아이디로 테마조회 or 테마 전체 조회 둘 다 가능)
    @GetMapping("/theme")
    public CommonResponse readThemes(@RequestParam(required = false) Long themeId) {
        if (themeId == null) {
            return CommonResponse.success(themeService.readThemes());
        }
        return CommonResponse.success(themeService.readTheme(themeId));
    }

    //테마 수정
    @PatchMapping("/theme/{themeId}")
    public CommonResponse<Theme> updateTheme(@RequestBody ThemeCreationRequest request, @PathVariable Long themeId){
        return CommonResponse.success(themeService.updateTheme(themeId, request));
    }


    @DeleteMapping("/theme/{themeId}")
    public CommonResponse<String> deleteTheme(@PathVariable Long themeId){
        try{
            themeService.deleteTheme(themeId);
            String result = "테마가 성공적으로 삭제되었습니다.";
            return CommonResponse.success(result);

        }catch(BaseException exception){

        }
        return null;
    }



}
