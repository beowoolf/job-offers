package pl.offers.job.domain.offer;

import pl.offers.job.domain.offer.dto.JobOfferResponse;

import java.util.List;

public class InMemoryFetcherTestImpl implements OfferFetchable {

    List<JobOfferResponse> listOfOffers;

    InMemoryFetcherTestImpl(List<JobOfferResponse> listOfOffers) {
        this.listOfOffers = listOfOffers;
    }

    @Override
    public List<JobOfferResponse> fetchOffers() {
        return listOfOffers;
    }

}
