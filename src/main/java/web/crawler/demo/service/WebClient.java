package web.crawler.demo.service;

import java.util.List;

import web.crawler.demo.service.dto.Entries;

public interface WebClient {

    List<Entries> getEntries(int numberOfEntries);
}