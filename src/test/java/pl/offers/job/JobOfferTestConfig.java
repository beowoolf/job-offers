package pl.offers.job;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JobOfferTestConfig {

    private RestClient restClientImpl(String uri) {
        return new RestClient(uri, restTemplateImpl());
    }

    private RestTemplate restTemplateImpl() {
        return new RestTemplate();
    }

    public OfferClient remoteOfferClientTestImpl(String uri) {
        return new OfferClient(restClientImpl(uri));
    }

}
