package pl.offers.job;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class JobOffersConfig {

    private final String getOfferUrlEndpoint = "/offers";

    @Bean
    protected OfferUrlConfig offerUrlConfig() {
        return new OfferUrlConfig();
    }

    @Bean
    protected RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    protected RestClient restClient() {
        return new RestClient(offerUrlConfig().getUrl() + getOfferUrlEndpoint, restTemplate());
    }

}
