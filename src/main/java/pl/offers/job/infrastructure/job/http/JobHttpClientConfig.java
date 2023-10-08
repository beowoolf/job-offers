package pl.offers.job.infrastructure.job.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.offers.job.domain.job.JobFetchable;

@Configuration
public class JobHttpClientConfig {

    @Bean
    public JobFetchable remoteJobClient(RestTemplate restTemplate,
                                        @Value("${job.http.client.config.uri:http://example.com}") String uri,
                                        @Value("${job.http.client.config.port:5057}") int port) {
        return new JobHttpClient(restTemplate, uri, port);
    }

}
