package web.crawler.demo.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import web.crawler.demo.domain.Entry;
import web.crawler.demo.service.WebCrawlerService;
import web.crawler.demo.util.MockData;

@SpringBootTest
@AutoConfigureMockMvc
class WebCrawlerControllerTest {

    @MockitoBean
    private WebCrawlerService webCrawlerService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturn200WhenGettingUnfilteredEntries() throws Exception {
        Mockito.when(webCrawlerService.getUnfilteredEntries(anyInt()))
                .thenReturn(MockData.ENTRY_LIST_WITH_LONG_AND_SHORT_TITLES);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/entries"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertNotNull(result);
        String content = result.getResponse().getContentAsString();
        List<Entry> entries = objectMapper.readValue(content, new TypeReference<List<Entry>>() {
        });
        entries.forEach(validateEntry());
    }

    @Test
    void shouldReturn200WhenGettingLongEntries() throws Exception {
        Mockito.when(webCrawlerService.getLongEntries(anyInt()))
                .thenReturn(MockData.ENTRY_LIST_WITH_LONG_TITLES);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/entries")
                .param("filter", "long"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertNotNull(result);
        String content = result.getResponse().getContentAsString();
        List<Entry> entries = objectMapper.readValue(content, new TypeReference<List<Entry>>() {
        });
        entries.forEach(validateEntry());
    }

    @Test
    void shouldReturn200WhenGettingShortEntries() throws Exception {
        Mockito.when(webCrawlerService.getShortEntries(anyInt()))
                .thenReturn(MockData.ENTRY_LIST_WITH_SHORT_TITLES);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/entries")
                .param("filter", "short"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        assertNotNull(result);
        String content = result.getResponse().getContentAsString();
        List<Entry> entries = objectMapper.readValue(content, new TypeReference<List<Entry>>() {
        });
        entries.forEach(validateEntry());
    }

    private Consumer<? super Entry> validateEntry() {
        return entry -> {
            assertNotNull(entry);
            assertNotNull(entry.getNumber());
            assertNotNull(entry.getTitle());
            assertFalse(entry.getTitle().isEmpty());
            assertNotNull(entry.getPoints());
            assertNotNull(entry.getComments());
        };
    }
}