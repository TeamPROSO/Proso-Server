package com.prosoteam.proso.domain.theme.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class ThemeSearchResponse {
    private final String themeTitle;
    private final String themeIntroduce;
    private final String themeImgUrl;
    private final Long themeId;
    private final Long userId;
    private final String userName;
    private final Integer contentCount;
}
