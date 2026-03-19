package web.crawler.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

import java.nio.file.Path;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import web.crawler.demo.domain.Entry;
import web.crawler.demo.service.client.JSoupClient;

@SpringBootTest
@Slf4j
class JSoupDocumentParserTest {

    @InjectMocks
    private JSoupDocumentParser webClient;
    
    @Mock
    private JSoupClient jSoupClient;

    @BeforeEach
    void setUp() {
        try {
            Document mockDocument = Jsoup.parse(
                    getClass().getClassLoader().getResourceAsStream("example-content.html"),
                    null,
                    "https://news.ycombinator.com/");
            Mockito.when(jSoupClient.getDocument(any())).thenReturn(mockDocument);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @ParameterizedTest
    @ValueSource(ints = {5, 10, 30})
    void shouldGetEntriesWhenConnectionIsSuccessful(int numberOfEntries) throws Exception {

        List<Entry> result = webClient.getEntries(numberOfEntries);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(numberOfEntries, result.size());
        Entry firstEntry = result.get(0);
        log.debug(new ObjectMapper().writeValueAsString(firstEntry));
        assertEquals(1, firstEntry.getNumber());
        assertEquals("Warranty Void If Regenerated", firstEntry.getTitle());
        assertEquals(49, firstEntry.getPoints());
        assertEquals(37, firstEntry.getComments());
    }

    @Test
    void shouldGetEntriesWhenCommentsAreNull() throws Exception {

        List<Entry> result = webClient.getEntries(28);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        Entry entryWithNoComments = result.get(27);
        log.debug(new ObjectMapper().writeValueAsString(entryWithNoComments));
        assertEquals(28, entryWithNoComments.getNumber());
        assertEquals("Explore 19th Century Scientific Correspondence", entryWithNoComments.getTitle());
        assertEquals(6, entryWithNoComments.getPoints());
        assertEquals(0, entryWithNoComments.getComments());
    }
}
