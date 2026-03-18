package web.crawler.demo.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JSoupDocumentParserTest {

    @Autowired
    private JSoupDocumentParser webClient;

    @Test
    void test() { // TODO - finish implementation
        webClient.getEntries(10);
    }
}
