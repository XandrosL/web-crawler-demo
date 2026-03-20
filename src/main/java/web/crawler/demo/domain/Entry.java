package web.crawler.demo.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Entry {

    private static final Pattern SPECIAL_NON_WHITESPACE_CHARS_PATTERN = Pattern.compile("[^A-Za-z0-9\\s]");
    private static final Pattern SPACED_WORDS_PATTERN = Pattern.compile("\\b\\w+\\b");

    Integer number;
    String title;
    Integer points;
    Integer comments;

    public int countWordsInTitle() {
        String cleanTitle = SPECIAL_NON_WHITESPACE_CHARS_PATTERN.matcher(title).replaceAll("");
        Matcher matcher = SPACED_WORDS_PATTERN.matcher(cleanTitle);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    public boolean hasLongTitle() {
        return countWordsInTitle() > 5;
    }

    public boolean hasShortTitle() {
        return countWordsInTitle() <= 5;
    }

    public int compareByDescendingNumberOfComments(Entry other) {
        return Integer.compare(other.getComments(), this.getComments());
    }

    public int compareByDescendingPoints(Entry other) {
        return Integer.compare(other.getPoints(), this.getPoints());
    }
}
