package web.crawler.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import web.crawler.demo.domain.Entry;
import web.crawler.demo.domain.TitleFilter;
import web.crawler.demo.service.WebCrawlerService;

@RestController
@RequestMapping("/entries")
@RequiredArgsConstructor
public class WebCrawlerController {

    private final WebCrawlerService webCrawlerService;

    @GetMapping
    public ResponseEntity<List<Entry>> getEntries(
            @RequestParam(name = "limit", defaultValue = "30") int numberOfEntries,
            @RequestParam(name = "filter", defaultValue = "none") String filter) {

        TitleFilter titleFilter = TitleFilter.fromString(filter);
        return ResponseEntity.ok(webCrawlerService.getEntries(titleFilter, numberOfEntries));
    }
}