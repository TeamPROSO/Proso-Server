package com.prosoteam.proso.domain.theme.dto;

import lombok.Data;

@Data
public class ThemeCreationRequest {
    private String themeTitle;
    private String themeIntroduce;
    private String themeImgUrl;
    private Long userId;



}
