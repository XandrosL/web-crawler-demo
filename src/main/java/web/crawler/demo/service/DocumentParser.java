package web.crawler.demo.service;

import java.util.List;

import web.crawler.demo.domain.Entry;

public interface DocumentParser {

    List<Entry> getEntries(Integer limit);
}