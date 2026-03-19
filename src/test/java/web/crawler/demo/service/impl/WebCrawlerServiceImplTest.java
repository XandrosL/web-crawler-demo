package web.crawler.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import web.crawler.demo.domain.Entry;
import web.crawler.demo.service.DocumentParser;
import web.crawler.demo.service.helper.EntryStreamHelper;
import web.crawler.demo.util.MockData;

@SpringBootTest
@Slf4j
class WebCrawlerServiceImplTest {

    @InjectMocks
    private WebCrawlerServiceImpl webCrawlerService;

    @Mock
    private DocumentParser documentParser;

    @Mock
    private EntryStreamHelper entryStreamHelper;

    @Test
    void shouldGetUnfilteredEntriesForAGivenInput() {
        Mockito.when(documentParser.getEntries(5)).thenReturn(MockData.ENTRY_LIST_WITH_5_ITEMS);

        List<Entry> result = webCrawlerService.getUnfilteredEntries(5);

        assertNotNull(result);
        assertEquals(5, result.size());

        Mockito.when(documentParser.getEntries(10)).thenReturn(MockData.ENTRY_LIST_WITH_10_ITEMS);

        List<Entry> result2 = webCrawlerService.getUnfilteredEntries(10);

        assertNotNull(result2);
        assertEquals(10, result2.size());

        List<Entry> result3 = webCrawlerService.getUnfilteredEntries(0);

        assertNotNull(result3);
        assertEquals(0, result3.size());
    }
}