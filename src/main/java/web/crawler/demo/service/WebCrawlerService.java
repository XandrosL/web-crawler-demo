package web.crawler.demo.service;

import java.util.List;

import web.crawler.demo.domain.Entry;
import web.crawler.demo.domain.TitleFilter;

public interface WebCrawlerService {

    List<Entry> getEntries(TitleFilter titleFilter, int numberOfEntries);
}