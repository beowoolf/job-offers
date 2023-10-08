package pl.offers.job.infrastructure.offer.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.offers.job.domain.offer.OfferFacade;
import pl.offers.job.domain.offer.dto.OfferResponseDto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class HttpOffersScheduler {

    private static final String STARTED_OFFERS_FETCHING_MESSAGE = "Started offers fetching {}";
    private static final String STOPPED_OFFERS_FETCHING_MESSAGE = "Stopped offers fetching {}";
    private static final String ADDED_NEW_OFFERS_MESSAGE = "Added new {} offers";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final OfferFacade offerFacade;

    @Scheduled(fixedDelayString = "${http.offers.scheduler.request.delay}")
    public List<OfferResponseDto> fetchAllOffersAndSaveAllIfNotExists() {
        log.info(STARTED_OFFERS_FETCHING_MESSAGE, dateFormat.format(new Date()));
        final List<OfferResponseDto> addedOffers = offerFacade.fetchAllOffersAndSaveAllIfNotExists();
        log.info(ADDED_NEW_OFFERS_MESSAGE, addedOffers.size());
        log.info(STOPPED_OFFERS_FETCHING_MESSAGE, dateFormat.format(new Date()));
        return addedOffers;
    }

}
