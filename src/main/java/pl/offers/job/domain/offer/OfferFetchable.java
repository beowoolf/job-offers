package pl.offers.job.domain.offer;

import pl.offers.job.domain.offer.dto.JobOfferResponse;

import java.util.List;

public interface OfferFetchable {

    List<JobOfferResponse> fetchOffers();

}
