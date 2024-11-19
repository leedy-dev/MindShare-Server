package com.mindshare.cms.domain.board.repository;

import com.mindshare.cms.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
