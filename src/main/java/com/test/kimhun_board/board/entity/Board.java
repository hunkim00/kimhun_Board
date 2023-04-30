package com.test.kimhun_board.board.entity;


import com.test.kimhun_board.board.dto.BoardRequestDto;
import com.test.kimhun_board.util.BaseEntity;
import com.test.kimhun_board.util.TokenFrequencyCounter;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;
    @OneToMany(mappedBy = "boardId")
    private List<Board> relatedBoard;
    @ElementCollection
    private List<String> contents;

    public static Board of(BoardRequestDto requestDto) {
        return Board.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();
    }

    public void setRelatedBoards(List<Board> relatedBoard) {
        this.relatedBoard = relatedBoard;
    }

    public int getTotalWordCount() {
        int count = 0;
        TokenFrequencyCounter tokenizer = new TokenFrequencyCounter();
        for (String content : contents) {
            List<String> tokens = tokenizer.tokenize(content)
                    .stream()
                    .map(Token::getMorph)
                    .collect(Collectors.toList());
            count += TokenFrequencyCounter.getFrequencyMap(tokens).values().stream().mapToInt(Integer::intValue).sum();
        }
        return count;

    }
}
