package com.programmers.jpaboard.board.repository;

import com.programmers.jpaboard.board.domian.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
