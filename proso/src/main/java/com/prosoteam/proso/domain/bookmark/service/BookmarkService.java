package com.prosoteam.proso.domain.bookmark.service;

import com.prosoteam.proso.domain.bookmark.dto.BookmarkResponse;
import com.prosoteam.proso.domain.bookmark.model.Bookmark;
import com.prosoteam.proso.domain.bookmark.model.BookmarkStatus;
import com.prosoteam.proso.domain.bookmark.repository.BookmarkRepository;
import com.prosoteam.proso.domain.theme.ThemeRepository.ThemeRepository;
import com.prosoteam.proso.domain.theme.model.Theme;
import com.prosoteam.proso.domain.user.entity.User;
import com.prosoteam.proso.domain.user.repository.UserRepository;
import com.prosoteam.proso.domain.user.service.UserService;
import com.prosoteam.proso.global.common.ErrorCode;
import com.prosoteam.proso.global.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookmarkService {
    private final ThemeRepository themeRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

    public BookmarkResponse checkBookmark(Long userId, Long themeIdx){
        Optional<User> user=userRepository.findBySocialId(userId);
        Optional<Theme> theme= themeRepository.findById(themeIdx);
        if (user.isEmpty()){ //TODO: 에러처리 존재하지 않는 유저
            throw new BaseException(ErrorCode.USERS_EMPTY_USER_ID);
        }
        if(theme.isEmpty()){//TODO: 에러처리 잘못된 THEME_IDX 존재하지 않음
            throw new BaseException(ErrorCode.REQUEST_ERROR);
        }
        User newUser = user.get();
        Theme newTheme = theme.get();
        Optional<Bookmark> bookmark = bookmarkRepository.getBookmark(userId,themeIdx); //있다면 무조건 1개만 존재
        if(bookmark.isEmpty()){ //최초로 누른 경우 -> 북마크 생성하고, status가 ON

            log.info("북마크없음");
            Bookmark newBookmark = new Bookmark(BookmarkStatus.ON,newUser,newTheme);
            bookmarkRepository.save(newBookmark);

            return BookmarkResponse.builder()
                    .userId(userId)
                    .themeId(themeIdx)
                    .themeTitle(newTheme.getThemeTitle())
                    .status(newBookmark.getStatus())
                    .build();
        }else{ //최초로 북마크 누른 게 아님.
            log.info("북마크이미있음");
            Bookmark updateBookmark = bookmark.get();
            if(updateBookmark.getStatus().equals(BookmarkStatus.OFF)){ //status 다시 ON
               updateBookmark.setStatus(BookmarkStatus.ON);
               bookmarkRepository.save(updateBookmark);
            }else{ //OFF
                updateBookmark.setStatus(BookmarkStatus.OFF);
                bookmarkRepository.save(updateBookmark);

            }
            return BookmarkResponse.builder()
                    .userId(userId)
                    .themeId(themeIdx)
                    .themeTitle(updateBookmark.getTheme().getThemeTitle())
                    .status(updateBookmark.getStatus())
                    .build();
        }
    }
}
