package pl.offers.job;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class OfferClientTests {

    @Test
    public void should_return_offers_list() {
        //given
        RestClient restClient = Mockito.mock(RestClient.class);
        when(restClient.callGetMethod(ArgumentMatchers.<ParameterizedTypeReference<List<JobOfferDto>>>any()))
                .thenReturn(new ResponseEntity<>(Collections.singletonList(new JobOfferDto()), HttpStatus.ACCEPTED));
        OfferClient offerClient = new OfferClient(restClient);
        //when
        List<JobOfferDto> offers = offerClient.getOffers();
        //then
        assertThat(offers.size()).isEqualTo(1);
    }

    @Test
    public void should_throw_OfferNotFoundException_when_return_list_is_empty() {
        //given
        RestClient restClient = Mockito.mock(RestClient.class);
        when(restClient.callGetMethod(ArgumentMatchers.<ParameterizedTypeReference<List<JobOfferDto>>>any()))
                .thenReturn(new ResponseEntity<>(Collections.emptyList(), HttpStatus.ACCEPTED));
        OfferClient offerClient = new OfferClient(restClient);
        //when //then
        assertThrows(JobOfferNotFoundException.class, offerClient::getOffers);
    }

}
