package com.test.kimhun_board.board.validator;

import com.test.kimhun_board.board.entity.Board;
import com.test.kimhun_board.board.repository.BoardRepository;

public class BoardValidator {

    public static Board validate(BoardRepository boardRepository, Long noticeId) {
        return boardRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }

}
