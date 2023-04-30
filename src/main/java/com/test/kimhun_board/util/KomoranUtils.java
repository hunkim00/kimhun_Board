package com.test.kimhun_board.util;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Komoran 형태소 분석 사용 Util
public class KomoranUtils {
        private static final Logger log = LoggerFactory.getLogger(KomoranUtils.class);
        private KomoranUtils(){}

        public static Komoran getInstance() {
            return KomoranInstance.instance;
        }

        private static class KomoranInstance {
            public static final Komoran instance = new Komoran(DEFAULT_MODEL.FULL);
        }
}
