package pl.offers.job;

public interface SampleOffer {

    default Offer firstOfferWithId() {
        return Offer.builder()
                .id(1L)
                .companyName("HARMAN Connected Services")
                .jobPosition("Junior Java SE Developer for Automotive")
                .salary("7k - 10k PLN")
                .offerUrl("https://nofluffjobs.com/pl/job/junior-java-se-developer-for-automotive-harman-connected-services-lodz-yafxatha")
                .build();
    }

    default Offer secondOfferWithId() {
        return Offer.builder()
                .id(2L)
                .companyName("S2Innovation Sp. z o. o.")
                .jobPosition("Junior Remote Java Developer")
                .salary("4k - 8k PLN")
                .offerUrl("https://nofluffjobs.com/pl/job/junior-remote-java-developer-s2innovation-krakow-stddogtj")
                .build();
    }

    default Offer returnFirstOfferWithoutId() {
        return Offer.builder()
                .id(null)
                .companyName("HARMAN Connected Services")
                .jobPosition("Junior Java SE Developer for Automotive")
                .salary("7k - 10k PLN")
                .offerUrl("https://nofluffjobs.com/pl/job/junior-java-se-developer-for-automotive-harman-connected-services-lodz-yafxatha")
                .build();
    }

}
