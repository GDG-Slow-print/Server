package com.site.slowprint.user.repository;

import com.site.slowprint.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // 사용자 이메일로 사용자 정보 조회
    Optional<User> findByEmail(String email);

    // 이메일 중복 체크
    boolean existsByEmail(String email);

    // 사용자 이메일로 사용자 정보 삭제
    void deleteByEmail(String email);
}
