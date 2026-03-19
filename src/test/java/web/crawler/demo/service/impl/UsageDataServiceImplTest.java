package web.crawler.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import web.crawler.demo.domain.TitleFilter;
import web.crawler.demo.repository.UsageDataRepository;
import web.crawler.demo.repository.entity.UsageData;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class UsageDataServiceImplTest {

    @InjectMocks
    private UsageDataServiceImpl usageDataService;

    @Mock
    private UsageDataRepository usageDataRepository;

    @Test
    @SuppressWarnings("null")
    void shouldSaveUsageDataInRepositoryGivenValidInput() {
        when(usageDataRepository.save(any(UsageData.class)))
                .thenReturn(new UsageData());

        usageDataService.saveUsageData(TitleFilter.NONE, 10, 10);

        verify(usageDataRepository, times(1)).save(any(UsageData.class));
    }

    @Test
    @SuppressWarnings("null")
    void shouldLogErrorGivenDatabaseError(CapturedOutput output) {
        when(usageDataRepository.save(any(UsageData.class)))
                .thenThrow(new RuntimeException("Database error"));

        usageDataService.saveUsageData(TitleFilter.NONE, 10, 10);

        verify(usageDataRepository, times(1)).save(any(UsageData.class));
        assertTrue(output.toString().contains("ERROR"));
    }
}
