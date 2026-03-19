package web.crawler.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import web.crawler.demo.domain.Entry;
import web.crawler.demo.service.DocumentParser;
import web.crawler.demo.service.UsageDataService;
import web.crawler.demo.util.MockData;

@SpringBootTest
@Slf4j
class WebCrawlerServiceImplTest {

    @InjectMocks
    private WebCrawlerServiceImpl webCrawlerService;

    @Mock
    private DocumentParser documentParser;

    @Mock
    private UsageDataService usageDataService;

    @Test
    void shouldGetUnfilteredEntriesForAGivenInput() {
        when(documentParser.getEntries(5)).thenReturn(MockData.ENTRY_LIST_WITH_5_ITEMS);

        List<Entry> result = webCrawlerService.getEntries(null, 5);

        assertNotNull(result);
        assertEquals(5, result.size());

        when(documentParser.getEntries(10)).thenReturn(MockData.ENTRY_LIST_WITH_10_ITEMS);

        List<Entry> result2 = webCrawlerService.getEntries(null, 10);

        assertNotNull(result2);
        assertEquals(10, result2.size());

        when(documentParser.getEntries(null)).thenReturn(MockData.ENTRY_LIST_WITH_10_ITEMS);

        List<Entry> result3 = webCrawlerService.getEntries(null, null);

        assertNotNull(result3);
        assertEquals(10, result3.size());
    }

    @Test
    void shouldGetLongTitleEntriesForAGivenInput() {
        when(documentParser.getEntries(anyInt())).thenReturn(MockData.ENTRY_LIST_WITH_LONG_AND_SHORT_TITLES);

        List<Entry> result = webCrawlerService.getEntries("long", 5);

        assertNotNull(result);
        result.forEach(entry -> assertTrue(entry.countWordsInTitle() > 5,
                "Entry should have 6 or more words in title - It has " + entry.countWordsInTitle()
                        + " words; title: " + entry.getTitle()));
    }

    @Test
    void shouldFilterShortTitlesGivenValidInput() {
        when(documentParser.getEntries(anyInt())).thenReturn(MockData.ENTRY_LIST_WITH_LONG_AND_SHORT_TITLES);

        List<Entry> result = webCrawlerService.getEntries("short", 5);

        assertNotNull(result);
        result.forEach(entry -> assertTrue(entry.countWordsInTitle() <= 5,
                "Entry should have 5 or fewer words in title - It has " + entry.countWordsInTitle()
                        + " words; title: " + entry.getTitle()));
    }

    @Test
    void shouldOrderByNumberOfCommentsWhenFilteringLongEntries() {
        when(documentParser.getEntries(anyInt())).thenReturn(MockData.ENTRY_LIST_WITH_LONG_AND_SHORT_TITLES);

        List<Entry> result = webCrawlerService.getEntries("long", 5);

        assertNotNull(result);
        result.stream().reduce((e1, e2) -> {
            assertTrue(e1.getComments() >= e2.getComments(),
                    "Entries should be ordered by number of comments in descending order - Entry with "
                            + e1.getComments() + " comments should go after Entry with " + e2.getComments()
                            + " comments");
            return e2;
        });
    }

    @Test
    void shouldOrderByPointsWhenFilteringShortEntries() {
        when(documentParser.getEntries(anyInt())).thenReturn(MockData.ENTRY_LIST_WITH_LONG_AND_SHORT_TITLES);

        List<Entry> result = webCrawlerService.getEntries("short", 5);

        result.stream().reduce((e1, e2) -> {
            assertTrue(e1.getPoints() >= e2.getPoints(),
                    "Entries should be ordered by points in descending order - Entry with "
                            + e1.getPoints() + " points should go after Entry with " + e2.getPoints()
                            + " points");
            return e2;
        });
    }
}