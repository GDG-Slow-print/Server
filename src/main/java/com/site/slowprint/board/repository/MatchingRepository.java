package com.site.slowprint.board.repository;

import com.site.slowprint.board.entity.Matching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {

    Page<Matching> findAll(Pageable pageable);

}
