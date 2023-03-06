package pl.offers.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;

    public List<OfferDto> getOffers() {
        return offerRepository.findAll()
                .stream()
                .map(OfferMapper::mapToOfferDto)
                .collect(Collectors.toList());
    }

    public OfferDto getOfferById(String id) {
        try {
            Long offerId = Long.parseLong(id);
            return OfferMapper.mapToOfferDto(offerRepository.findById(offerId).orElse(null));
        } catch (NumberFormatException e) {
            log.error("Offer id isn't a number. Given id: {}.", id);
            throw new WrongArgumentException("Offer id isn't a number.");
        }
    }

    public OfferDto createOrUpdateOffer(OfferDto offerDto) {
        offerRepository.save(OfferMapper.mapToOfferDao(offerDto));
        return offerDto;
    }

    public int deleteOfferById(String id) {
        try {
            Long offerId = Long.parseLong(id);
            return offerRepository.deleteOfferById(offerId);
        } catch (NumberFormatException e) {
            log.error("Offer id isn't a number. Given offer id: {}.", id);
            throw new WrongArgumentException("Offer id isn't a number.");
        }
    }

}
