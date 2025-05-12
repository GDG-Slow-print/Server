package com.site.slowprint.carbon.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarbonRankResponse {

    private String email;

    private long totalMileage;

    private int rank;
}
