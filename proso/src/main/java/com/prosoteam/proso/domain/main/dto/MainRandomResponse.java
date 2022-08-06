package com.prosoteam.proso.domain.main.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class MainRandomResponse {
    private final String address_name;
    private final String category_group_code;
    private final String category_group_name;
    private final String distance;
    private final String id;
    private final String place_name;
    private final String place_url;
    private final String road_address_name;
    private final String x;
    private final String y;

}
