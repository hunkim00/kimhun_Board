package com.test.kimhun_board.board.repository;

import com.test.kimhun_board.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByOrderByCreatedAtDesc(PageRequest pageRequest);

    @Query("SELECT b FROM Board b WHERE b.boardId = :boardId AND b.createdAt > :createdAt ORDER BY b.createdAt DESC")
    List<Board> findAllByBoardId(@Param("boardId") Long boardId, @Param("createdAt") LocalDateTime createdAt);

    List<Board> findAllByBoardId(Long boardId);
}
