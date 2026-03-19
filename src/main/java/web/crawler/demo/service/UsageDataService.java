package web.crawler.demo.service;

import web.crawler.demo.domain.TitleFilter;

public interface UsageDataService {

    void saveUsageData(Integer inputLimit, String inputFilter,
            TitleFilter appliedFilter, Integer resultCount);

}
