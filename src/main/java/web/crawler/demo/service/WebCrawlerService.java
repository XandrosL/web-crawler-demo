package web.crawler.demo.service;

import java.util.List;

import web.crawler.demo.domain.Entry;

public interface WebCrawlerService {

    List<Entry> getEntries(Integer limit, String filter);
}