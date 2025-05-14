package com.site.slowprint.board.repository;

import com.site.slowprint.board.entity.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    Page<Match> findAll(Pageable pageable);

}
