package pl.offers.job.infrastructure.offer.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.offers.job.domain.offer.OfferFetchable;

@Configuration
public class OfferHttpClientConfig {

    @Bean
    public OfferFetchable remoteOfferClient(RestTemplate restTemplate,
                                            @Value("${offer.http.client.config.uri:http://example.com}") String uri,
                                            @Value("${offer.http.client.config.port:5057}") int port) {
        return new OfferHttpClient(restTemplate, uri, port);
    }

}
