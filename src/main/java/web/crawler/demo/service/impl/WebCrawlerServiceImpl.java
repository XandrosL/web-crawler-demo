package web.crawler.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.crawler.demo.domain.Entry;
import web.crawler.demo.domain.TitleFilter;
import web.crawler.demo.service.DocumentParser;
import web.crawler.demo.service.UsageDataService;
import web.crawler.demo.service.WebCrawlerService;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebCrawlerServiceImpl implements WebCrawlerService {

    private final DocumentParser documentParser;
    private final UsageDataService usageDataService;

    @Override
    public List<Entry> getEntries(Integer limit, String filter) {
        TitleFilter titleFilter = TitleFilter.fromString(filter);
        List<Entry> entries = documentParser.getEntries(limit);
        List<Entry> filteredEntries;
        switch (titleFilter) {
            case LONG:
                filteredEntries = entries.stream()
                        .filter(Entry::hasLongTitle)
                        .sorted(Entry::compareByDescendingNumberOfComments)
                        .toList();
                usageDataService.saveUsageData(limit, filter, titleFilter, filteredEntries.size());
                return filteredEntries;
            case SHORT:
                filteredEntries = entries.stream()
                        .filter(Entry::hasShortTitle)
                        .sorted(Entry::compareByDescendingPoints)
                        .toList();
                usageDataService.saveUsageData(limit, filter, titleFilter, filteredEntries.size());
                return filteredEntries;
            case NONE:
            default:
                usageDataService.saveUsageData(limit, filter, titleFilter, entries.size());
                return entries;
        }
    }
}