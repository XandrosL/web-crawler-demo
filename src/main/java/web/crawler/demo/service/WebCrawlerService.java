package web.crawler.demo.service;

import java.util.List;

import web.crawler.demo.domain.Entry;

public interface WebCrawlerService {

    List<Entry> getUnfilteredEntries(int numberOfEntries);

    List<Entry> getLongEntries(int numberOfEntries);

    List<Entry> getShortEntries(int numberOfEntries);
}