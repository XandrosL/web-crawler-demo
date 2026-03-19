package web.crawler.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.crawler.demo.domain.Entry;
import web.crawler.demo.service.DocumentParser;
import web.crawler.demo.service.WebCrawlerService;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebCrawlerServiceImpl implements WebCrawlerService {

    private final DocumentParser documentParser;

    @Override
    public List<Entry> getUnfilteredEntries(int numberOfEntries) {
        return documentParser.getEntries(numberOfEntries);
    }

    @Override
    public List<Entry> getLongEntries(int numberOfEntries) {
        return documentParser.getEntries(numberOfEntries).stream()
                .filter(WebCrawlerServiceImpl::filterLongTitles)
                .sorted(WebCrawlerServiceImpl::orderByNumberOfComments)
                .toList();
    }

    @Override
    public List<Entry> getShortEntries(int numberOfEntries) {
        return documentParser.getEntries(numberOfEntries).stream()
                .filter(WebCrawlerServiceImpl::filterShortTitles)
                .sorted(WebCrawlerServiceImpl::orderByPoints)
                .toList();
    }

    private static boolean filterLongTitles(Entry entry) {
        return entry.countWordsInTitle() > 4;
    }

    private static boolean filterShortTitles(Entry entry) {
        return entry.countWordsInTitle() <= 4;
    }

    private static int orderByNumberOfComments(Entry entry1, Entry entry2) {
        return Integer.compare(entry2.getComments(), entry1.getComments());
    }

    private static int orderByPoints(Entry entry1, Entry entry2) {
        return Integer.compare(entry2.getPoints(), entry1.getPoints());
    }

}