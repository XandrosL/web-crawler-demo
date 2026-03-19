package web.crawler.demo.domain;

public enum TitleFilter {
    LONG, SHORT, NONE;

    public static TitleFilter fromString(String filter) {
        try {
            return TitleFilter.valueOf(filter.toUpperCase());
        } catch (IllegalArgumentException e) {
            return NONE;
        }
    }
}