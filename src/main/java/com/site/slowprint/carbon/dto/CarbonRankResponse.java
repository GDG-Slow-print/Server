package com.site.slowprint.carbon.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarbonRankResponse {

    private String username;

    private long totalMileage;

    private int rank;
}
