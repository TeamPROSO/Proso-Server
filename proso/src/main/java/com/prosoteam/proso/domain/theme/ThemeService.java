package com.prosoteam.proso.domain.theme;

import com.prosoteam.proso.domain.content.dto.RandomContentResponse;
import com.prosoteam.proso.domain.content.model.Content;
import com.prosoteam.proso.domain.theme.ThemeRepository.ThemeRepository;
import com.prosoteam.proso.domain.theme.dto.ThemeMainRecommendResponse;
import com.prosoteam.proso.domain.theme.dto.ThemeSearchResponse;
import com.prosoteam.proso.domain.theme.model.Theme;
import com.prosoteam.proso.domain.theme.dto.ThemeCreationRequest;
import com.prosoteam.proso.domain.user.entity.User;
import com.prosoteam.proso.domain.user.repository.UserRepository;
import com.prosoteam.proso.global.common.ErrorCode;
import com.prosoteam.proso.global.common.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final UserRepository userRepository;
    private final ThemeRepository themeRepository;


    //테마 생성
    public Theme createTheme(ThemeCreationRequest theme) {
        Optional<User> user = userRepository.findById(theme.getUserId());
        if (!user.isPresent()) {
            throw new BaseException(ErrorCode.USERS_EMPTY_USER_ID);
        }
        Theme themeToCreate = new Theme();
        BeanUtils.copyProperties(theme, themeToCreate);
        themeToCreate.setUser(user.get());
        return themeRepository.save(themeToCreate);
    }

    //테마아이디로 테마 조회
    @Transactional
    public ThemeSearchResponse readTheme(Long id){
        Optional<Theme> theme = themeRepository.findById(id);
        if(theme.isEmpty()){
            throw new BaseException(ErrorCode.THEME_USERS_EMPTY);
        }

        return ThemeSearchResponse.builder()
                .themeId(theme.get().getId())
                .themeTitle(theme.get().getThemeTitle())
                .themeIntroduce(theme.get().getThemeIntroduce())
                .themeImgUrl(theme.get().getThemeImgUrl())
                .userId(theme.get().getUser().getSocialId())
                .userName(theme.get().getUser().getUserName())
                .contentCount(theme.get().getCountOfContents())
                .build();
    }


    //테마 전체 조회
    @Transactional
    public List<ThemeSearchResponse> readThemes(){
        List<Theme> themes = themeRepository.findAll();
        if (themes.isEmpty()) {
            throw new BaseException(ErrorCode.THEME_USERS_EMPTY);
        }
        themes = themes.stream().distinct().collect(Collectors.toList());
        List<ThemeSearchResponse> newList = new ArrayList<>();
        themes.forEach(theme -> {
                    newList.add(
                            ThemeSearchResponse.builder()
                                    .themeId(theme.getId())
                                    .themeTitle(theme.getThemeTitle())
                                    .themeIntroduce(theme.getThemeIntroduce())
                                    .themeImgUrl(theme.getThemeImgUrl())
                                    .userId(theme.getUser().getSocialId())
                                    .userName(theme.getUser().getUserName())
                                    .contentCount(theme.getCountOfContents())
                                    .build()
                    );
                }
        );
        return newList;


    }


    //테마 수정
    public Theme updateTheme (Long id, ThemeCreationRequest request){
        Optional<Theme> optionalTheme = themeRepository.findById(id);
        if(!optionalTheme.isPresent()){
            throw new BaseException(ErrorCode.THEME_USERS_EMPTY);
        }

        Theme theme = optionalTheme.get();
        theme.setThemeTitle(request.getThemeTitle());
        theme.setThemeIntroduce(request.getThemeIntroduce());
        theme.setThemeImgUrl(request.getThemeImgUrl());
        return themeRepository.save(theme);
    }

    //테마 삭제
    public void deleteTheme(Long id) {
        themeRepository.deleteById(id);

    }

    /**
     * 테마 검색
     */
    @Transactional
    public List<ThemeSearchResponse> searchTheme(String keyword) {
        List<Theme> themes = themeRepository.findByThemeTitleContaining(keyword);
        themes.addAll(themeRepository.findByThemeIntroduceContaining(keyword));
        if (themes.isEmpty()) {
            throw new BaseException(ErrorCode.THEME_SEARCH_RESULT_EMPTY);
        }
        themes = themes.stream().distinct().collect(Collectors.toList());
        List<ThemeSearchResponse> newList = new ArrayList<>();
        themes.forEach(theme -> {
                    newList.add(
                            ThemeSearchResponse.builder()
                                    .themeId(theme.getId())
                                    .themeTitle(theme.getThemeTitle())
                                    .themeIntroduce(theme.getThemeIntroduce())
                                    .themeImgUrl(theme.getThemeImgUrl())
                                    .userId(theme.getUser().getSocialId())
                                    .userName(theme.getUser().getUserName())
                                    .build()
                    );
                }
        );
        return newList;
    }

    //테마메인 추천
    @Transactional
    public List<List> mainThemes(String keyword1, String keyword2){

        keyword1="카페";
        keyword2="맛집";



        List<Theme> themeList = themeRepository.findByThemeTitleContainingOrThemeIntroduceContaining(keyword1,keyword1);
        List<Theme> themeList2 = themeRepository.findByThemeTitleContainingOrThemeIntroduceContaining(keyword2,keyword2);

        Collections.shuffle(themeList);
        Collections.shuffle(themeList2);


        // 테마 메인 추천탭에서 슬라이드로 카페관련 테마 5개 랜덤 추천
        List<Theme> lists = themeList.subList(0,5);
        // 테마 메인 추천탭에서 슬라이드로 맛집관련 테마 5개 랜덤 추천
        List<Theme> lists2 = themeList2.subList(0,5);

        List<ThemeMainRecommendResponse> newList = new ArrayList<>();
        List<ThemeMainRecommendResponse> newList2 = new ArrayList<>();

        lists.forEach(theme -> {
            newList.add(
                    ThemeMainRecommendResponse.builder()
                            .themeTitle(theme.getThemeTitle())
                            .themeId(theme.getId())
                            .themeImgUrl(theme.getThemeImgUrl())
                            .userName(theme.getUser().getUserName())
                            .build()
            );
        });
        lists2.forEach(theme -> {
            newList2.add(
                    ThemeMainRecommendResponse.builder()
                            .themeTitle(theme.getThemeTitle())
                            .themeId(theme.getId())
                            .themeImgUrl(theme.getThemeImgUrl())
                            .userName(theme.getUser().getUserName())
                            .build()
            );
        });


        List<List> newList3 = new ArrayList<>();
        newList3.add(newList);
        newList3.add(newList2);
        return newList3;

    }
    
    //테마메인 맨위탭 여름!! 추천
    @Transactional
    public Object mainTopThemes(String keyword1){

        keyword1="여름";

        List<Theme> themeList = themeRepository.findByThemeTitleContainingOrThemeIntroduceContaining(keyword1,keyword1);
        List<ThemeMainRecommendResponse> newList = new ArrayList<>();

        themeList.forEach(theme -> {
            newList.add(
                    ThemeMainRecommendResponse.builder()
                            .themeTitle(theme.getThemeTitle())
                            .themeId(theme.getId())
                            .themeImgUrl(theme.getThemeImgUrl())
                            .userName(theme.getUser().getUserName())
                            .build()
            );
        });

        return newList.get(1);
    }
}
