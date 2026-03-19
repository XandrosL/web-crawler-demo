package web.crawler.demo.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import web.crawler.demo.domain.TitleFilter;
import web.crawler.demo.repository.UsageDataRepository;
import web.crawler.demo.repository.entity.UsageData;
import web.crawler.demo.service.UsageDataService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsageDataServiceImpl implements UsageDataService {

    private final UsageDataRepository usageDataRepository;

    @Override
    @SuppressWarnings("null")
    public void saveUsageData(TitleFilter titleFilter, int desiredEntries, int foundEntries) {
        try {
            UsageData entity = UsageData.builder()
                    .timestamp(LocalDateTime.now())
                    .titleFilter(titleFilter)
                    .desiredEntries(desiredEntries)
                    .foundEntries(foundEntries)
                    .build();
            usageDataRepository.save(entity);
        } catch (Exception e) {
            log.error("Failed to save usage data: {}", e.getMessage());
        }
    }
}
