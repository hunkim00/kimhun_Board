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

}
