package com.site.slowprint.carbon.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CarbonResponse {

    private double savedCarbon;

    private double earnedMileage;

}
