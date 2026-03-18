package web.crawler.demo.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import web.crawler.demo.util.MockData;

@SpringBootTest
class EntryTest {

    @Test
    void shouldCountWordsInTitleCorrectlyGivenDashes() {
        int result = MockData.ENTRY_WITH_5_WORDS.countWordsInTitle();
        
        assertEquals(5, result);
    }

    @Test
    void shouldCountWordsInTitleCorrectlyGivenColons() {
        int result = MockData.ENTRY_WITH_12_WORDS.countWordsInTitle();
        
        assertEquals(12, result);
    }

    @Test
    void shouldCountWordsInTitleCorrectlyGivenQuotationMarksAndNumbers() {
        int result = MockData.ENTRY_WITH_10_WORDS.countWordsInTitle();
        
        assertEquals(10, result);
    }
}