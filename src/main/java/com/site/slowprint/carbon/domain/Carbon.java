package com.site.slowprint.carbon.domain;

import com.site.slowprint.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;

@Entity
@Table(name = "carbon")
@Getter
@AllArgsConstructor
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Carbon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email", nullable = false)
    private User user;

    @Column(name = "transportation_mode",nullable = false)
    private String transportation;

    @Column(name = "distance_km",nullable = false)
    private double distance;

    @Column(name="saved_Carbon",nullable = false)
    private double savedCarbon;

    @Column(name="earned_Mileage",nullable = false)
    private double earnedMileage;

    @CreationTimestamp
    @Column(name = "carbon_created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
