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

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


    public static RelatedBoard of(Board board, int relatedCount) {

        return RelatedBoard.builder()
                .board(board)
                .relatedCount(relatedCount)
                .build();
    }
}
