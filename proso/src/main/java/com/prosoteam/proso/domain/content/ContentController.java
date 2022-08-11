package com.prosoteam.proso.domain.content;


import com.prosoteam.proso.domain.content.model.Content;
import com.prosoteam.proso.domain.content.model.ContentCreationRequest;
import com.prosoteam.proso.global.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.prosoteam.proso.global.common.CommonResponse;
import com.prosoteam.proso.global.common.ErrorCode;


@RestController
@RequestMapping(value = "/content")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

    //콘텐츠 생성
    @PostMapping("")
    public CommonResponse<Content> createContent (@RequestBody ContentCreationRequest request) {

        Content content = contentService.createContent(request);
        if(content == null){
            //에러발생
            return CommonResponse.error(ErrorCode.POST_POSTS_INVALID_CONTENTS);
        }
        return CommonResponse.success(content);

    }



    //컨텐츠 랜덤 조회
    @GetMapping("")
    public CommonResponse readContents(@RequestParam(required = true) Long themeId) {
        return CommonResponse.success(contentService.readContent(themeId));
    }




    //콘텐츠 수정
    @PatchMapping("/{contentId}")
    public CommonResponse<Content> updateContent(@RequestBody ContentCreationRequest request, @PathVariable Long contentId){
        if(contentId==null){ //오류 발생
            return CommonResponse.error(ErrorCode.POSTS_EMPTY_POST_ID);
        }
        return CommonResponse.success(contentService.updateContent(contentId, request));
    }


    //콘텐츠 삭제
    @DeleteMapping("/{contentId}")
    public CommonResponse<String> deleteContent(@PathVariable Long contentId,@RequestBody(required = false)
            ContentCreationRequest request){
        try{
            contentService.deleteContent(contentId,request);
            String result = "콘텐츠가 성공적으로 삭제되었습니다.";
            return CommonResponse.success(result);

        }catch(BaseException exception){

        }
        return null;
    }
}
