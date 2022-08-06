package com.prosoteam.proso.domain.theme;

import com.prosoteam.proso.domain.theme.model.Theme;
import com.prosoteam.proso.domain.theme.model.ThemeCreationRequest;
import com.prosoteam.proso.global.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.prosoteam.proso.global.common.CommonResponse;
import com.prosoteam.proso.global.common.ErrorCode;

@RestController
@RequestMapping(value = "/theme")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;

    //테마 생성
    @PostMapping("")
    public CommonResponse<Theme> createTheme (@RequestBody ThemeCreationRequest request) {
        Theme theme = themeService.createTheme(request);
        if(theme == null){
           //에러발생
           return CommonResponse.error(ErrorCode.POST_POSTS_INVALID_CONTENTS);
        }
        return CommonResponse.success(theme);
    }

    //테마 조회(아이디로 테마조회 or 테마 전체 조회 둘 다 가능)
    @GetMapping("")
    public CommonResponse readThemes(@RequestParam(required = false) Long themeId) {
        if (themeId == null) {
            return CommonResponse.success(themeService.readThemes());
        }
        return CommonResponse.success(themeService.readTheme(themeId));
    }

    //테마 수정
    @PatchMapping("/{themeId}")
    public CommonResponse<Theme> updateTheme(@RequestBody ThemeCreationRequest request, @PathVariable Long themeId){
        if(themeId==null){ //오류 발생
            return CommonResponse.error(ErrorCode.POSTS_EMPTY_POST_ID);
        }
        return CommonResponse.success(themeService.updateTheme(themeId, request));
    }


    @DeleteMapping("/{themeId}")
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
