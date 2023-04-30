package com.test.kimhun_board.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RelatedBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relatedId;

    @Column
    private String relatedWord;

    @Column
    private int relatedCount;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "related_board_id")
    private Long relatedBoardId;

    public static RelatedBoard of(Long boardId, Long relatedBoardId ,int relatedCount) {

        return RelatedBoard.builder()
                .boardId(boardId)
                .relatedBoardId(relatedBoardId)
                .relatedCount(relatedCount)
                .build();
    }
}
