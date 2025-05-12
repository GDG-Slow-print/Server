package com.site.slowprint.carbon.domain;

import com.site.slowprint.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;

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

    @Column(nullable = false)
    private String transportation;

    @Column(nullable = false)
    private double distance;

    @Column(nullable = false)
    private double savedCarbon;

    @Column(nullable = false)
    private double earnedMileage;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
