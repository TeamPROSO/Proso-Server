package com.prosoteam.proso.domain.main.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCurrentRequest {
    private String x; //x좌표는 경도
    private String y; //y좌표는 위도
}
