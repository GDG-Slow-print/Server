package com.site.slowprint.carbon.repository;

import com.site.slowprint.carbon.domain.Carbon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarbonRepository extends JpaRepository<Carbon, Long> {

    // 사용자 이메일로 탄소 절감량 조회
    Optional<Carbon> findByEmail(String email);

    // 사용자 이메일로 탄소 절감량 존재 여부 확인
    boolean existsByEmail(String email);

}
