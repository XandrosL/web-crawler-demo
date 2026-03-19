package web.crawler.demo.domain;

public enum TitleFilter {
    NONE, LONG, SHORT;

    public static TitleFilter fromString(String filter) {
        try {
            return TitleFilter.valueOf(filter.toUpperCase());
        } catch (IllegalArgumentException e) {
            return NONE;
        }
    }
}