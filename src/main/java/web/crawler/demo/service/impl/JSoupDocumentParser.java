package web.crawler.demo.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.crawler.demo.domain.Entry;
import web.crawler.demo.service.DocumentParser;
import web.crawler.demo.service.client.JSoupClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class JSoupDocumentParser implements DocumentParser {

    private static final String URL = "https://news.ycombinator.com/";

    private final JSoupClient jsoupClient;

    public List<Entry> getEntries(int numberOfEntries) {
        Document document = jsoupClient.getDocument(URL);
        log.info("Document charset: {}", document.outputSettings().charset());
        Elements rows = document.getElementById("bigbox").children().select("tr");

        // Partition rows into groups of 3, as each entry in the html consists of
        // a submission, a subline, and a spacer row.
        // Logic depends on elements within the Document being ordered.
        List<List<Element>> partitionedRows = Lists.partition(rows.asList(), 3);
        return partitionedRows.stream().limit(numberOfEntries).map(rowlist -> {
            Element submission = extractSubmission(rowlist);
            Element subline = extractSubline(rowlist);
            return Entry.builder()
                    .number(extractRank(submission))
                    .title(extractTitle(submission))
                    .points(extractScore(subline))
                    .comments(extractComments(subline))
                    .build();
        }).toList();
    }

    private Element extractSubmission(List<Element> elements) {
        return elements.stream().filter(element -> element.getElementsByClass("submission") != null).findFirst()
                .orElse(null);
    }

    private Element extractSubline(List<Element> elements) {
        return elements.stream().filter(element -> !element.getElementsByClass("subline").isEmpty()).findFirst()
                .orElse(null);
    }

    private Integer extractRank(Element submission) {
        return Optional.ofNullable(submission)
                .map(element -> element.getElementsByClass("rank"))
                .map(Elements::first)
                .map(Element::text)
                .map(text -> Integer.valueOf(text.replace(".", "")))
                .orElse(null);
    }

    private String extractTitle(Element submission) {
        return Optional.ofNullable(submission)
                .map(element -> element.getElementsByClass("titleline"))
                .map(Elements::first)
                .map(element -> element.select("a"))
                .map(Elements::first)
                .map(Element::text)
                .orElse(null);
    }

    private Integer extractScore(Element subline) {
        return Optional.ofNullable(subline)
                .map(element -> element.getElementsByClass("score"))
                .map(Elements::first)
                .map(Element::text)
                .map(extractDigits())
                .orElse(null);
    }

    private Integer extractComments(Element subline) {
        return Optional.ofNullable(subline)
                .map(element -> element.select("a:contains(comments)"))
                .map(Elements::first)
                .map(Element::text)
                .map(extractDigits())
                .orElse(null);
    }

    private Function<String, Integer> extractDigits() {
        return text -> {
            Matcher matcher = Pattern.compile("\\d+").matcher(text);
            if (matcher.find()) {
                return Integer.valueOf(matcher.group());
            } else {
                return null;
            }
        };
    }
}