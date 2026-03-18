package web.crawler.demo.service;

import java.util.List;

import web.crawler.demo.domain.Entries;

public interface WebCrawlerService {

    List<Entries> getUnfilteredEntries(int numberOfEntries);

    List<Entries> getLongEntries(int numberOfEntries);

    List<Entries> getShortEntries(int numberOfEntries);
}