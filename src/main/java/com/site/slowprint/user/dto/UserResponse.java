package com.site.slowprint.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private String email;

    private long totalMileage;

    private String token;
}
