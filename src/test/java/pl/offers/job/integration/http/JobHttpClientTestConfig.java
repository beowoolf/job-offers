package pl.offers.job.integration.http;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import pl.offers.job.domain.job.JobFetchable;
import pl.offers.job.infrastructure.http.RestTemplateResponseErrorHandler;
import pl.offers.job.infrastructure.job.http.JobHttpClientConfig;

import java.time.Duration;

import static pl.offers.job.integration.BaseIntegrationTest.WIRE_MOCK_HOST;

public class JobHttpClientTestConfig extends JobHttpClientConfig {

    public JobFetchable remoteJobTestClient(int port, int connectionTimeout, int readTimeout) {
        RestTemplateResponseErrorHandler restTemplateResponseErrorHandler = new RestTemplateResponseErrorHandler();
        final RestTemplate restTemplate = new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
        return remoteJobClient(restTemplate, WIRE_MOCK_HOST, port);
    }

}
