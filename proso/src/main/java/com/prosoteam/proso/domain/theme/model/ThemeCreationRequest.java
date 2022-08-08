package com.prosoteam.proso.domain.theme.model;

import lombok.Data;

@Data
public class ThemeCreationRequest {
    private String themeTitle;
    private String themeIntroduce;
    private String themeImgUrl;
    private Long userId;



}
