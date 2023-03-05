package pl.offers.job;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JobOfferTestConfig {

    private RestClient restClient(String uri) {
        return new RestClient(uri, restTemplate());
    }

    private RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public OfferClient remoteOfferClientTest(String uri) {
        return new OfferClient(restClient(uri));
    }

}
