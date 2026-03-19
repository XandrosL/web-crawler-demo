package web.crawler.demo.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum TitleFilter {
    NONE, LONG, SHORT;

    public static TitleFilter fromString(String filter) {
        try {
            if (filter == null) {
                return NONE;
            }
            return TitleFilter.valueOf(filter.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid filter value: {}. Defaulting to NONE.", filter);
            return NONE;
        }
    }
}