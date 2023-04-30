package com.test.kimhun_board.board.controller;

import com.test.kimhun_board.board.dto.BoardListReponseDto;
import com.test.kimhun_board.board.dto.BoardRequestDto;
import com.test.kimhun_board.board.dto.BoardResponseDto;
import com.test.kimhun_board.board.entity.Board;
import com.test.kimhun_board.board.entity.RelatedBoard;
import com.test.kimhun_board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/boardList")
    public ResponseEntity<?> getBoards(
            @PageableDefault(size = 4, page = 1) Pageable pageable
    ) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize());

        Page<BoardListReponseDto> responseDto = boardService.getBoard(pageRequest);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<?> getBoardDetail(@PathVariable Long boardId) {
        BoardResponseDto responseDto = boardService.getBoardDetail(boardId);

        // 연관 게시글 출력
        List<BoardResponseDto> relatedPosts = responseDto.getRelatedPosts();
        if (relatedPosts != null && !relatedPosts.isEmpty()) {
            System.out.println("관련 게시글:");
            for (BoardResponseDto relatedBoard : relatedPosts) {
                System.out.println("- " + relatedBoard.getRelatedPosts());
            }
        }else {
            System.out.println("관련 게시글 없음");
        }
        return ResponseEntity.ok(relatedPosts);
    }

    @PostMapping("/api/board")
    public ResponseEntity<?> createBoard(@RequestBody BoardRequestDto requestDto) {
        Board board = boardService.createBoard(requestDto);
        System.out.println(board);
        return ResponseEntity.ok().build();
    }
}
