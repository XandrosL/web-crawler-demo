package web.crawler.demo.service.helper;

import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import web.crawler.demo.domain.Entry;

/**
 * Helper methods to manipulate Entries according to business logic requirements.
 */
@Component
public class EntryStreamHelper {

    Stream<Entry> filterLongTitles(Stream<Entry> entryStream) {
        return entryStream.filter(entry -> entry.countWordsInTitle() > 4);
    }

    Stream<Entry> filterShortTitles(Stream<Entry> entryStream) {
        return entryStream.filter(entry -> entry.countWordsInTitle() <= 4);
    }

    Stream<Entry> orderByNumberOfComments(Stream<Entry> entryStream) {
        return entryStream.sorted((entry1, entry2) -> Integer.compare(entry2.getComments(), entry1.getComments()));
    }

    Stream<Entry> orderByPoints(Stream<Entry> entryStream) {
        return entryStream.sorted((entry1, entry2) -> Integer.compare(entry2.getPoints(), entry1.getPoints()));
    }
}
