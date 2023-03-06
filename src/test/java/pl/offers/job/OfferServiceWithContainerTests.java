package pl.offers.job;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Arrays;
import java.util.List;

import static com.mongodb.assertions.Assertions.assertNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

@SpringBootTest(classes = JobApplication.class)
@Testcontainers
@ActiveProfiles("container")
public class OfferServiceWithContainerTests implements SampleOfferDto, SampleOffer {

    @Container
    private static final MongoDBContainer DB_CONTAINER = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    static {
        DB_CONTAINER.start();
        System.setProperty("DB_PORT", String.valueOf(DB_CONTAINER.getFirstMappedPort()));
    }

    @Test
    void should_return_all_offers(@Autowired OfferService offerService) {
        //given
        List<OfferDto> offers = Arrays.asList(firstOfferDtoWithId(), secondOfferDtoWithId());
        //when
        final List<OfferDto> offersFromDatabase = offerService.getOffers();
        //then
        assertThat(offersFromDatabase).containsAll(offers);
    }

    @Test
    void should_return_offer_with_given_id(@Autowired OfferService offerService) {
        //given
        OfferDto offerDto = firstOfferDtoWithId();
        String offerId = "1";
        //when
        final OfferDto offerDtoFromDatabase = offerService.getOfferById(offerId);
        //then
        assertThat(offerDtoFromDatabase).isEqualTo(offerDto);
    }

    @Test
    void should_return_null_where_there_is_no_offer_with_given_id(@Autowired OfferService offerService) {
        //given
        String offerId = "500";
        //when
        OfferDto offerDto = offerService.getOfferById(offerId);
        //then
        assertNull(offerDto);
    }


    @Test
    void should_throw_exception_when_given_id_is_not_a_number(@Autowired OfferService offerService) {
        //given
        String offerId = "aaa";
        //when //then
        assertThrows(WrongArgumentException.class, () -> offerService.getOfferById(offerId));
    }

}
