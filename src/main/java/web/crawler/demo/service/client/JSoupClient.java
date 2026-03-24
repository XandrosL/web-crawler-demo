package web.crawler.demo.service.client;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import web.crawler.demo.exception.JSoupClientConnectException;

@Component
public class JSoupClient {

    @Cacheable("jsoupDocuments")
    public Document getDocument(String url) {
        try {
            Connection connection = Jsoup.connect(url);
            connection.timeout(5000);
            return connection.get();
        } catch (IOException ex) {
            throw new JSoupClientConnectException(
                    "Could not GET the given URL: " + url + " - Cause is: " + ex.getCause()
                            + " - Please contact your administrator.",
                    ex.getCause());
        }
    }
}
