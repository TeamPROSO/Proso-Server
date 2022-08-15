package com.prosoteam.proso.domain.content.dto;

import lombok.Data;

@Data
public class ContentCreationRequest {
    private String contentTitle;
    private String contentContent;
    private Long themeId;
}
