package web.crawler.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.crawler.demo.domain.Entry;
import web.crawler.demo.service.DocumentParser;
import web.crawler.demo.service.WebCrawlerService;
import web.crawler.demo.service.helper.EntryStreamHelper;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebCrawlerServiceImpl implements WebCrawlerService {

    private final DocumentParser documentParser;
    private final EntryStreamHelper entryStreamHelper;

    @Override
    public List<Entry> getUnfilteredEntries(int numberOfEntries) {
        return documentParser.getEntries(numberOfEntries);
    }

    @Override
    public List<Entry> getLongEntries(int numberOfEntries) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLongEntries'");
    }

    @Override
    public List<Entry> getShortEntries(int numberOfEntries) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getShortEntries'");
    }

}