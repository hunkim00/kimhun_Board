package com.test.kimhun_board.board.repository;


import com.test.kimhun_board.board.entity.RelatedBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelatedBoardRepository extends JpaRepository<RelatedBoard, Long> {
    List<RelatedBoard> findByBoardId(Long boardId);


}
