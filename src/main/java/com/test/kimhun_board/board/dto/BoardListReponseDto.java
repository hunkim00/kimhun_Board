package com.test.kimhun_board.board.dto;

import com.test.kimhun_board.board.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardListReponseDto {
    private Long noticeId;
    private String title;
    private LocalDateTime createdAt;

    public static BoardListReponseDto of(Board board) {
        return BoardListReponseDto.builder()
                .noticeId(board.getBoardId())
                .title(board.getTitle())
                .createdAt(board.getCreatedAt())
                .build();
    }
}
