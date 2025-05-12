package com.site.slowprint.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {


    @Id
    @Column(name = "user_id", nullable = false, unique = true, length = 50)
    private String email;


    @Column(name = "user_password", nullable = false, length = 100)
    private String password;


    @Setter
    @Column(name = "auth_token", length = 1000)
    private String authToken;


    @Column(name = "total_mileage", nullable = false)
    private int totalMileage = 0;


    @CreationTimestamp
    @Column(name = "user_created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    @Column(name = "user_updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void updateTotalMileage(double earnedMileage) {
        this.totalMileage += earnedMileage;
    }
}
