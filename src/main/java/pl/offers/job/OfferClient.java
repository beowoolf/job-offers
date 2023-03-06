package pl.offers.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class OfferClient implements RemoteOfferClient {

    private final RestClient restClient;

    public List<JobOfferDto> getOffers() {
        List<JobOfferDto> offers;
        try {
            ResponseEntity<List<JobOfferDto>> responseEntity = restClient.callGetMethod(new ParameterizedTypeReference<List<JobOfferDto>>() {
            });
            offers = responseEntity.getBody();
            if (offers != null && offers.isEmpty()) {
                log.error("Return empty offer list.");
                throw new JobOfferNotFoundException("There are no offers");
            }
        } catch (RestClientException e) {
            log.error("Throw RestClient exception with message {}.", e.getMessage());
            throw new HttpClientException(e.getMessage());
        }
        log.info("Return list with {} offers.", offers.size());
        return offers;
    }

}
