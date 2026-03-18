package web.crawler.demo.service.impl;

import java.io.IOException;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import web.crawler.demo.domain.Entry;
import web.crawler.demo.service.DocumentParser;

@Component
@Slf4j
public class JSoupDocumentParser implements DocumentParser {

    private static final String URL = "https://news.ycombinator.com/";

    public List<Entry> getEntries(int numberOfEntries) {
        try {
            Connection connection = Jsoup.connect(URL);
            connection.timeout(5000);
            Document doc = connection.get();
            Elements submissions = doc.getElementsByClass("submission");
            submissions.forEach(submission -> log.info("submissions: " + submissions.toString()));
        } catch (IOException ex) {
            log.error("Could not GET the given URL: " + ex.getLocalizedMessage(), ex.getCause());
        }
        return null; // TODO - finish implementation
    }
}