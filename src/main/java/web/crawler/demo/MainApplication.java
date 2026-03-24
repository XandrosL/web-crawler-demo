package web.crawler.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

import web.crawler.demo.configuration.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties({ AppProperties.class })
@ComponentScan(basePackages = "web.crawler.demo")
@EnableCaching
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}