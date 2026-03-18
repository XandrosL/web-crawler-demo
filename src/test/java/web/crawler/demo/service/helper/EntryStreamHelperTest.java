package web.crawler.demo.service.helper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import web.crawler.demo.domain.Entry;
import web.crawler.demo.service.helper.EntryStreamHelper;
import web.crawler.demo.util.MockData;

@SpringBootTest
class EntryStreamHelperTest {

    @Autowired
    private EntryStreamHelper entryStreamHelper;

    @Test
    void shouldFilterLongTitlesGivenValidInput() {
        Stream<Entry> result = entryStreamHelper.filterLongTitles(
                MockData.ENTRY_LIST_WITH_LONG_AND_SHORT_TITLES.stream());

        result.forEach(entry -> assertTrue(entry.countWordsInTitle() > 4,
                "Entry should have 5 or more words in title - It has " + entry.countWordsInTitle()
                        + " words; title: " + entry.getTitle()));
    }

    @Test
    void shouldFilterShortTitlesGivenValidInput() {
        Stream<Entry> result = entryStreamHelper.filterShortTitles(
                MockData.ENTRY_LIST_WITH_LONG_AND_SHORT_TITLES.stream());

        result.forEach(entry -> assertTrue(entry.countWordsInTitle() <= 4,
                "Entry should have 4 or fewer words in title - It has " + entry.countWordsInTitle()
                        + " words; title: " + entry.getTitle()));
    }

    @Test
    void shouldOrderByNumberOfCommentsGivenValidInput() {
        Stream<Entry> result = entryStreamHelper.orderByNumberOfComments(
                MockData.ENTRY_LIST_WITH_LONG_AND_SHORT_TITLES.stream());

        result.reduce((e1, e2) -> {
            assertTrue(e1.getComments() >= e2.getComments(),
                    "Entries should be ordered by number of comments in descending order - Entry with "
                            + e1.getComments() + " comments should go after Entry with " + e2.getComments()
                            + " comments");
            return e2;
        });
    }

    @Test
    void shouldOrderByPointsGivenValidInput() {
        Stream<Entry> result = entryStreamHelper.orderByPoints(
                MockData.ENTRY_LIST_WITH_LONG_AND_SHORT_TITLES.stream());

        result.reduce((e1, e2) -> {
            assertTrue(e1.getPoints() >= e2.getPoints(),
                    "Entries should be ordered by points in descending order - Entry with "
                            + e1.getPoints() + " points should go after Entry with " + e2.getPoints()
                            + " points");
            return e2;
        });
    }
}
