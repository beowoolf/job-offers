package pl.offers.job;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.List;

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
                throw new JobOfferNotFoundException("There are no offers");
            }
        } catch (RestClientException e) {
            throw new HttpClientException(e.getMessage());
        }
        return offers;
    }

}
