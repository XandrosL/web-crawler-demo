package web.crawler.demo.service;

import java.util.List;

import web.crawler.demo.domain.Entry;

public interface WebClient {

    List<Entry> getEntries(int numberOfEntries);
}