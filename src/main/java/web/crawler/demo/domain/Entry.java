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
    Integer number;
    String title;
    Integer points;
    Integer comments;

    public int countWordsInTitle() {
        String cleanTitle = title.replaceAll("[^A-Za-z0-9\\s]", ""); // Ignore special, non-whitespace characters
        Pattern pattern = Pattern.compile("\\b\\w+\\b"); // Count whole words
        Matcher matcher = pattern.matcher(cleanTitle);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}
