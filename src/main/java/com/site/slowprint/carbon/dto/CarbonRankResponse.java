package com.site.slowprint.carbon.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarbonRankResponse {

    private int rank;

    private String username;

    private long totalMileage;

}
