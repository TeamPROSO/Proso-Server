package com.prosoteam.proso.domain.user.service;

import com.prosoteam.proso.domain.bookmark.model.BookmarkStatus;
import com.prosoteam.proso.domain.bookmark.repository.BookmarkRepository;
import com.prosoteam.proso.domain.theme.ThemeRepository.ThemeRepository;
import com.prosoteam.proso.domain.theme.model.Theme;
import com.prosoteam.proso.domain.user.dto.ExtraUserRequest;
import com.prosoteam.proso.domain.user.dto.UserResponse;
import com.prosoteam.proso.domain.user.dto.UserThemeResponse;
import com.prosoteam.proso.domain.user.entity.User;
import com.prosoteam.proso.domain.user.repository.UserRepository;
import com.prosoteam.proso.global.common.ErrorCode;
import com.prosoteam.proso.global.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;
    private final BookmarkRepository bookmarkRepository;

    public UserResponse findBySocialId(Long socialId) {
        Optional<User> user = userRepository.findBySocialId(socialId);
        if (user.isEmpty()) {
            throw new BaseException(ErrorCode.USERS_EMPTY_USER_ID);
        }
        return UserResponse.builder()
                .socialId(socialId)
                .userName(user.get().getUserName())
                .profileImgUrl(user.get().getProfileImgUrl())
                .build();
    }

    public String getUserRole(Long socialId) {
        Optional<User> user = userRepository.findBySocialId(socialId);
        if (user.isEmpty()) {
            throw new BaseException(ErrorCode.USERS_EMPTY_USER_ID);
        }
        return user.get().getRole();
    }

    public UserResponse addExtraUserInfo(Long socialId, ExtraUserRequest extraUserRequest){
        Optional<User> user= userRepository.findBySocialId(socialId);
        if (user.isEmpty()) {
            throw new BaseException(ErrorCode.USERS_EMPTY_USER_ID);
        }
        String userName= extraUserRequest.getUserName();
        String profileImgUrl= extraUserRequest.getProfileImgUrl();
        log.info("바꾸려는 닉네임 : "+userName);
        log.info("바꾸려는 프사 : "+profileImgUrl);

        if (userName==null){
            user.get().updateProfileImgUrl(profileImgUrl);
        }else if(profileImgUrl==null){
            user.get().updateUserName(userName);
        }else{
            user.get().updateBoth(userName,profileImgUrl);
        }
        log.info("업데이트 성공: "+ user.get().getSocialId());
        userRepository.flush();

        return UserResponse.builder()
                .socialId(socialId)
                .userName(user.get().getUserName())
                .profileImgUrl(user.get().getProfileImgUrl())
                .build();
    }

    /**
     * 작성한 테마 조회
     */
    public List<UserThemeResponse> getMyTheme(Long socialId) {
        Optional<User> user = userRepository.findBySocialId(socialId);
        List<UserThemeResponse> themeResponse = new ArrayList<>();
        if (user.isEmpty()) {
            throw new BaseException(ErrorCode.USERS_EMPTY_USER_ID);
        }
        List<Theme> themeList = themeRepository.getThemeList(socialId);
        if (themeList.isEmpty()) {
            throw new BaseException(ErrorCode.THEME_USERS_EMPTY);
        }
        themeList.forEach(theme -> {
            themeResponse.add(
                    UserThemeResponse.builder()
                            .themeId(theme.getId())
                            .themeTitle(theme.getThemeTitle())
                            .themeIntroduce(theme.getThemeIntroduce())
                            .themeUrl(theme.getThemeImgUrl())
                            .build());
        });
        return themeResponse;
    }

    /**
     * 북마크한 테마 조회
     */
    public List<UserThemeResponse> getMyBookmarkTheme(Long socialId) {
        Optional<User> user = userRepository.findBySocialId(socialId);
        List<UserThemeResponse> themeResponse = new ArrayList<>();
        if (user.isEmpty()) {
            throw new BaseException(ErrorCode.USERS_EMPTY_USER_ID);
        }
        List<Theme> themeList = bookmarkRepository.getThemeList(socialId, BookmarkStatus.ON);
        if (themeList.isEmpty()) {
            throw new BaseException(ErrorCode.BOOKMARK_USERS_EMPTY);
        }
        themeList.forEach(theme -> {
            themeResponse.add(
                    UserThemeResponse.builder()
                            .themeId(theme.getId())
                            .themeTitle(theme.getThemeTitle())
                            .themeIntroduce(theme.getThemeIntroduce())
                            .themeUrl(theme.getThemeImgUrl())
                            .build());
        });
        return themeResponse;
    }
}
