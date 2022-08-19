package com.prosoteam.proso.domain.contentImg;


import com.prosoteam.proso.domain.contentImg.model.ContentImg;
import com.prosoteam.proso.domain.contentImg.model.ContentImgCreationRequest;
import com.prosoteam.proso.global.common.CommonResponse;
import com.prosoteam.proso.global.common.ErrorCode;
import com.prosoteam.proso.global.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/content")
@RequiredArgsConstructor

public class ContentImgController {
    private final ContentImgService contentImgService;

    //콘텐츠 이미지 생성
    @PostMapping("/img")
    public CommonResponse<ContentImg> createContentImg (@RequestBody ContentImgCreationRequest request) {
        ContentImg contentImg = contentImgService.createContentImg(request);
        if(contentImg == null){
            //에러발생
            return CommonResponse.error(ErrorCode.POST_POSTS_INVALID_CONTENTS);
        }
        return CommonResponse.success(contentImg);
    }

    //콘텐츠 이미지 삭제
    @DeleteMapping("/img/{contentImgId}")
    public CommonResponse<String> deleteContentImg(@PathVariable Long contentImgId){
        try{
            contentImgService.deleteContentImg(contentImgId);
            String result = "콘텐츠 이미지가 성공적으로 삭제되었습니다.";
            return CommonResponse.success(result);

        }catch(BaseException exception){

        }
        return null;
    }
}
