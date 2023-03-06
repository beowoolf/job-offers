package pl.offers.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/offers")
    public ResponseEntity<List<OfferDto>> getOffers() {
        List<OfferDto> offers = offerService.getOffers();
        if (offers.isEmpty()) {
            log.error("List of offers is empty.");
            throw new OfferNotFoundException("There are no offers");
        }
        log.info("Return {} offers.", offers.size());
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<OfferDto> getOfferById(@PathVariable String id) {
        OfferDto offer = offerService.getOfferById(id);
        if (offer == null) {
            log.error("Offer with id {} not found.", id);
            throw new OfferNotFoundException("There is no offer with id: " + id);
        }
        log.info("Return offer with id {}. ", id);
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

    @PostMapping("/offers")
    public ResponseEntity<OfferDto> createOrUpdateOffer(@RequestBody OfferDto offerDto) {
        if (offerDto.getId() != null) {
            log.info("Update offer with id {}. ", offerDto.getId());
            return new ResponseEntity<>(offerService.createOrUpdateOffer(offerDto), HttpStatus.OK);
        }
        log.info("Added new offer");
        return new ResponseEntity<>(offerService.createOrUpdateOffer(offerDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/offers/{id}")
    public ResponseEntity<OfferDto> deleteOffer(@PathVariable String id) {
        if (offerService.deleteOfferById(id) > 0) {
            log.info("Delete offer with id {}.", id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        log.info("Offer with id {} do not exists.", id);
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
