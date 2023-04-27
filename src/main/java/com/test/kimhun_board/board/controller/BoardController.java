package com.test.kimhun_board.board.controller;

import com.test.kimhun_board.board.dto.BoardListReponseDto;
import com.test.kimhun_board.board.dto.BoardRequestDto;
import com.test.kimhun_board.board.dto.BoardResponseDto;
import com.test.kimhun_board.board.entity.Board;
import com.test.kimhun_board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/boardList")
    public ResponseEntity<?> getNotices(
            @PageableDefault(size = 4, page = 1) Pageable pageable
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());

        Page<BoardListReponseDto> responseDto = boardService.getNotices(pageRequest);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> getNoticeDetail(@PathVariable Long boardId) {
        BoardResponseDto responseDto = boardService.getNoticeDetail(boardId);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/api/board")
    public ResponseEntity<?> createNotice(@RequestBody BoardRequestDto requestDto) {
        Board board = boardService.createNotice(requestDto);
        System.out.println(board);
        return ResponseEntity.ok().build();
    }
}
