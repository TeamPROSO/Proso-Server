package com.prosoteam.proso.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class UserThemeResponse {
    private final Long themeId;
    private final String themeTitle;
    private final String themeIntroduce;
    private final String themeUrl;
}
