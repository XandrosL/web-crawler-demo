package web.crawler.demo.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TitleFilterTest {

    @ParameterizedTest
    @ValueSource(strings = { "long", "LONG", "Long" })
    void shouldMapCorrectlyGivenVariableCase(String input) {
        TitleFilter result = TitleFilter.fromString(input);
        assert (result == TitleFilter.LONG);
    }

    @Test
    void shouldDefaultToNoneGivenNullInput() {
        TitleFilter result = TitleFilter.fromString(null);
        assert (result == TitleFilter.NONE);
    }

    @Test
    void shouldDefaultToNoneGivenInvalidInput() {
        TitleFilter result = TitleFilter.fromString("invalid");
        assert (result == TitleFilter.NONE);
    }
}
