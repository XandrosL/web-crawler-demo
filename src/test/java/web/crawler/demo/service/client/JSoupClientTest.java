package web.crawler.demo.service.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import web.crawler.demo.configuration.AppProperties;
import web.crawler.demo.exception.JSoupClientConnectException;

@SpringBootTest
class JSoupClientTest {

    @Autowired
    private JSoupClient jSoupClient;

    @Autowired
    private AppProperties appProperties;

    private Document mockDocument;

    @BeforeEach
    void setUp() {
        try {
            mockDocument = Jsoup.parse(
                    getClass().getClassLoader().getResourceAsStream("example-content.html"),
                    null,
                    appProperties.getTargetUrl());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldCacheResponseGivenRepeatedCalls() throws IOException {
        Connection mockConnection = Mockito.mock(Connection.class);
        when(mockConnection.get()).thenReturn(mockDocument);
        String targetUrl = appProperties.getTargetUrl();

        try (MockedStatic<Jsoup> jsoup = Mockito.mockStatic(Jsoup.class)) {
            jsoup.when(() -> Jsoup.connect(any()))
                    .thenReturn(mockConnection);

            Document firstCallResult = jSoupClient.getDocument(targetUrl);
            assertNotNull(firstCallResult);
            jsoup.verify(() -> Jsoup.connect(any()), times(1));

            Document secondCallResult = jSoupClient.getDocument(targetUrl);
            assertNotNull(secondCallResult);
            jsoup.verify(() -> Jsoup.connect(any()), times(1));
        }
    }

    @Test
    void shouldThrowCustomExceptionGivenIOException() throws IOException {
        Connection mockConnection = Mockito.mock(Connection.class);
        when(mockConnection.get()).thenThrow(new IOException("Connect Exception"));
        String targetUrl = appProperties.getTargetUrl();

        try (MockedStatic<Jsoup> jsoup = Mockito.mockStatic(Jsoup.class)) {
            jsoup.when(() -> Jsoup.connect(any()))
                    .thenReturn(mockConnection);

            assertThrows(JSoupClientConnectException.class, () -> jSoupClient.getDocument(targetUrl));
        }
    }
}