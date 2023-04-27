package com.test.kimhun_board.board.service;

import com.test.kimhun_board.board.dto.BoardListReponseDto;
import com.test.kimhun_board.board.dto.BoardRequestDto;
import com.test.kimhun_board.board.dto.BoardResponseDto;
import com.test.kimhun_board.board.entity.Board;
import com.test.kimhun_board.board.repository.BoardRepository;
import com.test.kimhun_board.board.validator.BoardValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Page<BoardListReponseDto> getNotices(PageRequest pageRequest) {
        Page<Board> pages = boardRepository.findByOrderByCreatedAtDesc(pageRequest);

        List<BoardListReponseDto> responseDtos = pages.stream()
                .map(BoardListReponseDto::of)
                .collect(Collectors.toList());

        return new PageImpl<>(responseDtos, pages.getPageable(), pages.getTotalElements());
    }

    public BoardResponseDto getNoticeDetail(Long noticeId) {
        Board Board = BoardValidator.validate(boardRepository, noticeId);
        return BoardResponseDto.of(Board);
    }

    @Transactional
    public Board createNotice(BoardRequestDto requestDto) {
        Board board = Board.of(requestDto);
        System.out.println(board);
        boardRepository.save(board);
        return board;
    }
}
