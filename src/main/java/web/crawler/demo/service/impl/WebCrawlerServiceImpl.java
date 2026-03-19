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
    public List<Entry> getEntries(TitleFilter titleFilter, int numberOfEntries) {
        List<Entry> entries = documentParser.getEntries(numberOfEntries);
        usageDataService.saveUsageData(titleFilter, numberOfEntries, entries.size());
        switch (titleFilter) {
            case LONG:
                return entries.stream()
                        .filter(WebCrawlerServiceImpl::filterLongTitles)
                        .sorted(WebCrawlerServiceImpl::orderByNumberOfComments)
                        .toList();
            case SHORT:
                return entries.stream()
                        .filter(WebCrawlerServiceImpl::filterShortTitles)
                        .sorted(WebCrawlerServiceImpl::orderByPoints)
                        .toList();
            case NONE:
            default:
                return entries;
        }
    }

    private static boolean filterLongTitles(Entry entry) {
        return entry.countWordsInTitle() > 5;
    }

    private static boolean filterShortTitles(Entry entry) {
        return entry.countWordsInTitle() <= 5;
    }

    private static int orderByNumberOfComments(Entry entry1, Entry entry2) {
        return Integer.compare(entry2.getComments(), entry1.getComments());
    }

    private static int orderByPoints(Entry entry1, Entry entry2) {
        return Integer.compare(entry2.getPoints(), entry1.getPoints());
    }
}