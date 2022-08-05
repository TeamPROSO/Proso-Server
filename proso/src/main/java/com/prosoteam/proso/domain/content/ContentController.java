package com.prosoteam.proso.domain.content;


import com.prosoteam.proso.domain.content.model.Content;
import com.prosoteam.proso.domain.content.model.ContentCreationRequest;
import com.prosoteam.proso.global.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.prosoteam.proso.global.common.CommonResponse;
import com.prosoteam.proso.global.common.ErrorCode;


@RestController
@RequestMapping(value = "/api/contents")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

    //콘텐츠 생성
    @PostMapping("/content")
    public CommonResponse<Content> createContent (@RequestBody ContentCreationRequest request) {
        Content content = contentService.createContent(request);
        if(content == null){
            //에러발생
            return CommonResponse.error(ErrorCode.POST_POSTS_INVALID_CONTENTS);
        }
        return CommonResponse.success(content);
    }

    /*
    //컨텐츠 조회(아이디로 컨텐츠조회 or 컨텐츠 전체 조회 둘 다 가능)
    @GetMapping("/content")
    public CommonResponse readContents(@RequestParam(required = false) Long contentId) {
        if (contentId == null) {
            return CommonResponse.success(contentService.readContents());
        }
        return CommonResponse.success(contentService.readContent(contentId));
    }
    */

    //콘텐츠 수정
    @PatchMapping("/content/{contentId}")
    public CommonResponse<Content> updateContent(@RequestBody ContentCreationRequest request, @PathVariable Long contentId){
        if(contentId==null){ //오류 발생
            return CommonResponse.error(ErrorCode.POSTS_EMPTY_POST_ID);
        }
        return CommonResponse.success(contentService.updateContent(contentId, request));
    }


    //콘텐츠 삭제
    @DeleteMapping("/content/{contentId}")
    public CommonResponse<String> deleteContent(@PathVariable Long contentId){
        try{
            contentService.deleteContent(contentId);
            String result = "콘텐츠가 성공적으로 삭제되었습니다.";
            return CommonResponse.success(result);

        }catch(BaseException exception){

        }
        return null;
    }
}
