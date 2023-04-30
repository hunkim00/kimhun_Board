package com.test.kimhun_board.board.service;

import com.test.kimhun_board.board.dto.BoardListReponseDto;
import com.test.kimhun_board.board.dto.BoardRequestDto;
import com.test.kimhun_board.board.dto.BoardResponseDto;
import com.test.kimhun_board.board.entity.Board;
import com.test.kimhun_board.board.repository.BoardRepository;
import com.test.kimhun_board.board.validator.BoardValidator;
import com.test.kimhun_board.util.TokenFrequencyCounter;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final TokenFrequencyCounter tokenFrequencyCounter;

    public Page<BoardListReponseDto> getBoard(PageRequest pageRequest) {
        Page<Board> pages = boardRepository.findByOrderByCreatedAtDesc(pageRequest);

        List<BoardListReponseDto> responseDtos = pages.stream()
                .map(BoardListReponseDto::of)
                .collect(Collectors.toList());

        return new PageImpl<>(responseDtos, pages.getPageable(), pages.getTotalElements());
    }

    public BoardResponseDto getBoardDetail(Long boardId) {
        Board Board = BoardValidator.validate(boardRepository, boardId);
        return BoardResponseDto.of(Board);
    }

    @Transactional
    public Board createBoard(BoardRequestDto requestDto) {
        Board board = Board.of(requestDto);

        boardRepository.save(board);

        List<Board> relatedBoards = findRelatedBoards(board);
        if (!relatedBoards.isEmpty()) {
            board.setRelatedBoards(relatedBoards);
            boardRepository.save(board);
        }

        return board;
    }

    private List<Board> findRelatedBoards(Board board) {
        List<Token> tokens = tokenFrequencyCounter.tokenize(board.getContent());
        Set<String> excludedWords = getExcludedWords(tokens);

        // 게시글이 작성된 날짜 이후의 게시글만 대상으로 함
        List<Board> recentBoards = boardRepository.findAllByCreatedAt(board.getCreatedAt());

        // 게시글과 연관된 게시글 찾기
        Map<Board, Integer> boardFrequency = new HashMap<>();
        for (Board recentBoard : recentBoards) {
            int frequency = 0;
            List<Token> recentTokens = tokenFrequencyCounter.tokenize(recentBoard.getContent());

            // 단어 빈도수 계산
            for (Token token : tokens) {
                if (excludedWords.contains(token.getMorph())) {
                    continue;
                }
                frequency += Collections.frequency(recentTokens, token);
            }

            boardFrequency.put(recentBoard, frequency);
        }
        // 연관 게시글 필터링
        List<Board> relatedBoards = boardFrequency.entrySet()
                .stream()
                .filter(entry -> {
                    Board recentBoard = entry.getKey();
                    int frequency = entry.getValue();
                    int totalCount = recentBoard.getTotalWordCount();
                    int threshold = (int) Math.ceil(totalCount * 0.4);
                    return frequency >= threshold && totalCount >= 2;
                })
                .sorted((entry1, entry2) -> {
                            // 연관도 높은 순서로 정렬
                            int frequency1 = entry1.getValue();
                            int frequency2 = entry2.getValue();
                    return Integer.compare(frequency2, frequency1);
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return relatedBoards;
    }


    private Set<String> getExcludedWords(List<Token> tokens) {
        // 불용어 리스트
        Set<String> excludedWords = new HashSet<>();
        excludedWords.add("은");
        excludedWords.add("는");
        excludedWords.add("이");
        excludedWords.add("가");
        excludedWords.add("을");
        excludedWords.add("를");
        excludedWords.add("으로");
        excludedWords.add("에서");
        excludedWords.add("에게");
        excludedWords.add("에게서");
        excludedWords.add("와");
        excludedWords.add("과");
        excludedWords.add("의");
        excludedWords.add("에");
        excludedWords.add("저");
        excludedWords.add("그");
        excludedWords.add("것");
        excludedWords.add("이다");
        excludedWords.add("입니다");
        excludedWords.add("그리고");
        excludedWords.add("하지만");
        excludedWords.add("그런데");
        excludedWords.add("그래서");
        excludedWords.add("또는");
        excludedWords.add("그러나");
        excludedWords.add("그러면");
        excludedWords.add("따라");
        excludedWords.add("이런");
        excludedWords.add("저런");
        excludedWords.add("어떤");
        excludedWords.add("몇몇");
        excludedWords.add("모두");
        excludedWords.add("수많은");
        excludedWords.add("많은");
        excludedWords.add("여러");
        excludedWords.add("다양한");
        excludedWords.add("그렇다면");
        excludedWords.add("그렇지만");
        excludedWords.add("결국");
        excludedWords.add("이러한");
        excludedWords.add("저러한");
        excludedWords.add("이것");
        excludedWords.add("저것");
        excludedWords.add("그것");
        excludedWords.add("따라서");
        excludedWords.add("이때");
        excludedWords.add("그때");
        excludedWords.add("저때");
        excludedWords.add("그런");
        excludedWords.add("이러");
        excludedWords.add("저러");
        excludedWords.add("이런것");
        excludedWords.add("저런것");
        excludedWords.add("그런것");
        excludedWords.add("이러한것");
        excludedWords.add("저러한것");
        excludedWords.add("그러한것");
        excludedWords.add("이러니");
        excludedWords.add("저러니");
        excludedWords.add("그러니");
        excludedWords.add("그러므로");
        excludedWords.add("그러한데");
        excludedWords.add("그러니까");
        excludedWords.add("그런가");
        excludedWords.add("이러면");
        excludedWords.add("저러면");
        excludedWords.add("그러면서");
        excludedWords.add("그러한지");
        excludedWords.add("이러한지");
        excludedWords.add("저러한지");

        return excludedWords;
    }

}
