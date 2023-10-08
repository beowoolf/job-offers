package pl.offers.job.integration.scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import pl.offers.job.JobApplication;
import pl.offers.job.domain.job.JobFetchable;
import pl.offers.job.integration.BaseIntegrationTest;

import java.time.Duration;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest(classes = JobApplication.class, properties = "scheduling.enabled=true")
public class HttpJobsSchedulerTest extends BaseIntegrationTest {

    @SpyBean
    JobFetchable remoteJobClient;

    @Test
    public void should_run_http_client_jobs_fetching_exactly_given_times() {
        await().
                atMost(Duration.ofSeconds(2))
                .untilAsserted(() -> verify(remoteJobClient, times(1)).fetchJobs());
    }

}
