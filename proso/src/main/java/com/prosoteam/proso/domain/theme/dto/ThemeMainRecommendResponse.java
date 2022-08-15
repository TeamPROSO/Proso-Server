package com.prosoteam.proso.domain.theme.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder

public class ThemeMainRecommendResponse {
    private final String themeTitle;
    private final String themeImgUrl;
    private final Long themeId;
    private final String userName;
}
