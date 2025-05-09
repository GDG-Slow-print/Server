package com.site.slowprint.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jdk.jfr.Timestamp;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
public class User {

    @Id
    @Column(name = "user_id", nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "user_name", nullable = false, length = 50)
    private String name;

    @Column(name = "user_password", nullable = false, length = 100)
    private String passwordHash;

    @Column(name = "auth_token", length = 1000)
    private String authToken;

    // 총 마일리지: 사용자 활동에 따른 포인트 합산
    @Column(name = "total_mileage", nullable = false)
    private Long totalMileage;

    @Timestamp
    @Column(name = "user_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Timestamp
    @Column(name = "user_updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
