package com.site.slowprint.carbon.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarbonRequest {

    @NotEmpty(message = "이동수단은 필수 입력값입니다.")
    private String transportation;

    @NotEmpty(message = "출발지는 필수 입력값입니다.")
    private double distance;

}
