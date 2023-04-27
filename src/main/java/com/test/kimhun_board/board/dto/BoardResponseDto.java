package com.test.kimhun_board.board.dto;

import com.test.kimhun_board.board.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardResponseDto {
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public static BoardResponseDto of(Board board) {
        return BoardResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
