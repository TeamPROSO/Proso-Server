package com.prosoteam.proso.domain.bookmark.controller;

import com.prosoteam.proso.domain.bookmark.dto.BookmarkResponse;
import com.prosoteam.proso.domain.bookmark.service.BookmarkService;
import com.prosoteam.proso.domain.user.dto.ExtraUserRequest;
import com.prosoteam.proso.domain.user.dto.UserResponse;
import com.prosoteam.proso.domain.user.service.UserService;
import com.prosoteam.proso.global.common.CommonResponse;
import com.prosoteam.proso.global.oauth.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/theme")
public class BookmarkController {

    private final JwtTokenProvider jwtTokenProvider;
    private final BookmarkService bookmarkService;


    /**
     * 어떤 유저가 한 테마에 북마크 누르기
     */
    @PutMapping("/bookmark")
    public CommonResponse<BookmarkResponse> CheckBookmark(@RequestHeader("Authorization") String jwtToken, @RequestParam Long themeIdx) {
        // jwt 복호화. user정보 얻기
        Long socialId = jwtTokenProvider.getUserSocialId(jwtToken);
        return CommonResponse.success(bookmarkService.checkBookmark(socialId,themeIdx));
    }


}
