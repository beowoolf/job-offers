package pl.offers.job;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;

import java.util.Arrays;
import java.util.List;

@ChangeLog
public class DatabaseChangeLog {

    @ChangeSet(order = "001", id = "seedDatabase", author = "PW")
    public void seedDatabase(OfferRepository offerRepository) {
        List<Offer> offers = Arrays.asList(
                Offer.builder()
                        .id(1L)
                        .companyName("HARMAN Connected Services")
                        .jobPosition("Junior Java SE Developer for Automotive")
                        .salary("7k - 10k PLN")
                        .offerUrl("https://nofluffjobs.com/pl/job/junior-java-se-developer-for-automotive-harman-connected-services-lodz-yafxatha")
                        .build(),
                Offer.builder()
                        .id(2L)
                        .companyName("S2Innovation Sp. z o. o.")
                        .jobPosition("Junior Remote Java Developer")
                        .salary("4k - 8k PLN")
                        .offerUrl("https://nofluffjobs.com/pl/job/junior-remote-java-developer-s2innovation-krakow-stddogtj")
                        .build());
        offerRepository.insert(offers);
    }

}
