package com.test.kimhun_board.util;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Component
public class TokenFrequencyCounter {

    public static Map<String, Integer> getFrequencyMap(List<String> tokens) {
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String token : tokens) {
            if (frequencyMap.containsKey(token)) {
                frequencyMap.put(token, frequencyMap.get(token) + 1);
            } else {
                frequencyMap.put(token, 1);
            }
        }

        return frequencyMap;
    }

    public List<Token> tokenize(String text) {
        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
        List<Token> tokens = komoran.analyze(text).getTokenList();
        return tokens;
    }

    public int frequency(List<Token> tokens, Token token) {
        int result = 0;
        if (token == null) {
            for (Token t : tokens)
                if (t == null)
                    result++;
        } else {
            for (Token t : tokens)
                if (tokenEquals(token, t))
                    result++;
        }
        return result;
    }

    public boolean tokenEquals(Token token1, Token token2) {
        if (token1.getBeginIndex() != token2.getBeginIndex()) {
            return false;
        } else if (token1.getEndIndex() != token2.getEndIndex()) {
            return false;
        } else if (!token1.getMorph().equals(token2.getMorph())) {
            return false;
        } else if (!token1.getPos().equals(token2.getPos())) {
            return false;
        }
        return true;
    }
}
