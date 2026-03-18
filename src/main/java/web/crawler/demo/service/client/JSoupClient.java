package web.crawler.demo.service.client;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import web.crawler.demo.exception.DocumentParserException;

@Component
public class JSoupClient {

    public Document getDocument(String url) {
        try {
            Connection connection = Jsoup.connect(url);
            connection.timeout(5000);
            return connection.get();
        } catch (IOException ex) {
            throw new DocumentParserException(
                    "Could not GET the given URL: " + url + " - Error message is: " + ex.getLocalizedMessage(),
                    ex.getCause());
        }
    }
}
