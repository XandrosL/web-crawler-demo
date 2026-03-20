package web.crawler.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import web.crawler.demo.configuration.AppProperties;
import web.crawler.demo.domain.Entry;
import web.crawler.demo.service.client.JSoupClient;

@SpringBootTest
@Slf4j
class JSoupDocumentParserTest {

    @Autowired
    private JSoupDocumentParser webClient;

    @MockitoBean
    private JSoupClient jSoupClient;

    @Autowired
    private AppProperties appProperties;

    @BeforeEach
    void setUp() {
        try {
            Document mockDocument = Jsoup.parse(
                    getClass().getClassLoader().getResourceAsStream("example-content.html"),
                    null,
                    appProperties.getTargetUrl());
            Mockito.when(jSoupClient.getDocument(any())).thenReturn(mockDocument);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = { 5, 10, 30 })
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

    @Test
    void shouldGetEntriesWhenInputIsHigherThan30() throws Exception {

        List<Entry> result = webClient.getEntries(31);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(30, result.size());
        Entry firstEntry = result.get(0);
        log.debug(new ObjectMapper().writeValueAsString(firstEntry));
        assertEquals(1, firstEntry.getNumber());
        assertEquals("Warranty Void If Regenerated", firstEntry.getTitle());
        assertEquals(49, firstEntry.getPoints());
        assertEquals(37, firstEntry.getComments());
    }

    @Test
    void shouldGetEntriesWhenInputIsNull() throws Exception {

        List<Entry> result = webClient.getEntries(null);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(30, result.size());
        Entry firstEntry = result.get(14);
        log.info(new ObjectMapper().writeValueAsString(firstEntry));
        assertEquals(15, firstEntry.getNumber());
        assertEquals("Machine Payments Protocol (MPP)", firstEntry.getTitle());
        assertEquals(120, firstEntry.getPoints());
        assertEquals(64, firstEntry.getComments());
    }

    @Test
    void shouldGetEntriesWhenInputIsNegative() throws Exception {

        List<Entry> result = webClient.getEntries(-5);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(30, result.size());
        Entry firstEntry = result.get(14);
        log.info(new ObjectMapper().writeValueAsString(firstEntry));
        assertEquals(15, firstEntry.getNumber());
        assertEquals("Machine Payments Protocol (MPP)", firstEntry.getTitle());
        assertEquals(120, firstEntry.getPoints());
        assertEquals(64, firstEntry.getComments());
    }

}
