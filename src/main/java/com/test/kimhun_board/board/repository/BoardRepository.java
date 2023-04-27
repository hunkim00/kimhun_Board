package com.test.kimhun_board.board.repository;

import com.test.kimhun_board.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByOrderByCreatedAtDesc(PageRequest pageRequest);

    Optional<Board> findByBoardId(Long boardId);

}
