package pl.offers.job;

import org.springframework.data.repository.Repository;

import java.util.*;

public interface OfferRepository extends Repository<Offer, Long> {

    int deleteOfferById(Long id);

    Optional<Offer> findById(Long id);

    List<Offer> findAll();

    Offer save(Offer offer);

    List<Offer> insert(List<Offer> offers);

}
