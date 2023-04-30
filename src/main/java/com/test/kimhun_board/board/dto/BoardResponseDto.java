package com.test.kimhun_board.board.dto;

import com.test.kimhun_board.board.entity.Board;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardResponseDto {
    private Long boardId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private List<BoardResponseDto> relatedPosts;

    public static BoardResponseDto of(Board board,List<Board> relatedBoards) {
        List<BoardResponseDto> relatedPosts = relatedBoards.stream()
                .map(relatedBoard -> of(relatedBoard, Collections.EMPTY_LIST))
                .collect(Collectors.toList());
        return BoardResponseDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt())
                .relatedPosts(relatedPosts)
                .build();
    }
}
