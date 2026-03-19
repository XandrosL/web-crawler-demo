package web.crawler.demo.service;

import web.crawler.demo.domain.TitleFilter;

public interface UsageDataService {

    void saveUsageData(TitleFilter titleFilter, int desiredEntries, int foundEntries);

}
