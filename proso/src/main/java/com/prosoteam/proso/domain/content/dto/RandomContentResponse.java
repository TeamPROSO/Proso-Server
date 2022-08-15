package com.prosoteam.proso.domain.content.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder

public class RandomContentResponse {
    private final String contentTitle;
    private final String contentContent;
    private final Long contentId;
    private final String themeTitle;
    private final String themeIntroduce;
    private final String themeImgUrl;
    private final Long themeId;
    private final Long userId;
    private final String userName;
}
