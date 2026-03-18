package web.crawler.demo.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "hackerNews", url = "https://news.ycombinator.com/")
public interface HackerNewsFeignClient {

    @GetMapping("/")
    String getHtmlContent();
}
