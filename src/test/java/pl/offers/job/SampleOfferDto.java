package pl.offers.job;

public interface SampleOfferDto {

    default OfferDto firstOfferDtoWithId() {
        return OfferDto.builder()
                .id(1L)
                .companyName("HARMAN Connected Services")
                .jobPosition("Junior Java SE Developer for Automotive")
                .salary("7k - 10k PLN")
                .offerUrl("https://nofluffjobs.com/pl/job/junior-java-se-developer-for-automotive-harman-connected-services-lodz-yafxatha")
                .build();
    }

    default OfferDto secondOfferDtoWithId() {
        return OfferDto.builder()
                .id(2L)
                .companyName("S2Innovation Sp. z o. o.")
                .jobPosition("Junior Remote Java Developer")
                .salary("4k - 8k PLN")
                .offerUrl("https://nofluffjobs.com/pl/job/junior-remote-java-developer-s2innovation-krakow-stddogtj")
                .build();
    }

    default OfferDto firstOfferDtoWithoutId() {
        return OfferDto.builder()

                .companyName("HARMAN Connected Services")
                .jobPosition("Junior Java SE Developer for Automotive")
                .salary("7k - 10k PLN")
                .offerUrl("https://nofluffjobs.com/pl/job/junior-java-se-developer-for-automotive-harman-connected-services-lodz-yafxatha")
                .build();
    }

}
