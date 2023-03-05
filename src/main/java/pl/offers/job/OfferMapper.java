package pl.offers.job;

public class OfferMapper {

    public static OfferDto mapToOfferDto(Offer offer) {
        if (offer == null) {
            return null;
        }
        return OfferDto.builder()
                .id(offer.getId())
                .companyName(offer.getCompanyName())
                .jobPosition(offer.getJobPosition())
                .salary(offer.getSalary())
                .offerUrl(offer.getOfferUrl())
                .build();
    }

    public static Offer mapToOfferDao(OfferDto offerDto) {
        if (offerDto == null) {
            return null;
        }
        return Offer.builder()
                .id(offerDto.getId())
                .companyName(offerDto.getCompanyName())
                .jobPosition(offerDto.getJobPosition())
                .salary(offerDto.getSalary())
                .offerUrl(offerDto.getOfferUrl())
                .build();
    }

}
