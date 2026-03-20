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

    @Test
    void shouldCompareEntriesByNumberOfCommentsInDescendingOrder() {
        int result = MockData.ENTRY_1.compareByDescendingNumberOfComments(MockData.ENTRY_2);
        
        assertEquals(1, result);

        result = MockData.ENTRY_3.compareByDescendingNumberOfComments(MockData.ENTRY_4);
        
        assertEquals(-1, result);

        result = MockData.ENTRY_3.compareByDescendingNumberOfComments(MockData.ENTRY_3);
        
        assertEquals(0, result);
    }

    @Test
    void shouldCompareEntriesByPointsInDescendingOrder() {
        int result = MockData.ENTRY_1.compareByDescendingPoints(MockData.ENTRY_2);
        
        assertEquals(1, result);

        result = MockData.ENTRY_3.compareByDescendingPoints(MockData.ENTRY_4);
        
        assertEquals(-1, result);

        result = MockData.ENTRY_3.compareByDescendingPoints(MockData.ENTRY_3);
        
        assertEquals(0, result);
    }
}