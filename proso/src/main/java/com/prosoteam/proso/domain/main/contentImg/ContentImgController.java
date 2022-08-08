package com.prosoteam.proso.domain.main.contentImg;


import com.prosoteam.proso.domain.main.contentImg.model.ContentImg;
import com.prosoteam.proso.domain.main.contentImg.model.ContentImgCreationRequest;
import com.prosoteam.proso.global.common.CommonResponse;
import com.prosoteam.proso.global.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/content")
@RequiredArgsConstructor

public class ContentImgController {
    private final ContentImgService contentImgService;

    //콘텐츠 생성
    @PostMapping("/img")
    public CommonResponse<ContentImg> createContentImg (@RequestBody ContentImgCreationRequest request) {
        ContentImg contentImg = contentImgService.createContentImg(request);
        if(contentImg == null){
            //에러발생
            return CommonResponse.error(ErrorCode.POST_POSTS_INVALID_CONTENTS);
        }
        return CommonResponse.success(contentImg);
    }
}
