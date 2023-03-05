package pl.offers.job;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class OfferServiceTest implements SampleOffer {

    OfferRepository offerRepository = Mockito.mock(OfferRepository.class);
    OfferService offerService = new OfferService(offerRepository);

    @Test
    public void should_return_offer_list() {
        //given
        when(offerRepository.getAllOffers()).thenReturn(Collections.singletonList(returnOneOffer()));
        //when
        List<OfferDto> offers = offerService.getOffers();
        //then
        assertThat(offers.size()).isEqualTo(1);
    }

    @Test
    public void should_return_empty_list_when_there_is_no_offers() {
        //given
        when(offerRepository.getAllOffers()).thenReturn(Collections.emptyList());
        //when
        List<OfferDto> offers = offerService.getOffers();
        //then
        assertThat(offers.size()).isEqualTo(0);
    }

    @Test
    public void should_return_offer_with_given_offer_id() {
        //given
        when(offerRepository.getOfferById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(returnOneOffer()));
        //when
        OfferDto offer = offerService.getOfferById(String.valueOf(ArgumentMatchers.anyLong()));
        //then
        assertThat(offer).isInstanceOf(OfferDto.class).isNotNull();
    }

    @Test
    public void should_return_null_when_there_is_no_offer_with_given_id() {
        //given
        when(offerRepository.getOfferById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        //when
        OfferDto offer = offerService.getOfferById(String.valueOf(ArgumentMatchers.anyLong()));
        //then
        assertThat(offer).isNull();
    }

    @Test
    public void should_throw_WrongArgumentException_when_offer_id_is_not_a_number_in_get_offer_by_id_method() {
        //given //when //then
        assertThrows(WrongArgumentException.class, () -> offerService.getOfferById(""));
    }

    @Test
    public void should_return_1_when_offer_will_be_delete_with_given_id() {
        //given
        when(offerRepository.deleteOfferById(ArgumentMatchers.anyLong())).thenReturn(1);
        //when
        int result = offerService.deleteOfferById(String.valueOf(ArgumentMatchers.anyLong()));
        //then
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void return_offer_with_all_fields_not_null_when_offer_is_updating() {
        //given
        when(offerRepository.createOrUpdateOffer(returnOneOffer())).thenReturn(returnOneOffer());
        //when
        OfferDto offer = offerService.createOrUpdateOffer(OfferMapper.mapToOfferDto(returnOneOffer()));
        //then
        assertThat(offer).isNotNull().hasNoNullFieldsOrProperties();
    }

    @Test
    public void return_offer_without_id_when_offer_is_creating() {
        //given
        when(offerRepository.createOrUpdateOffer(returnOneOfferWithoutId())).thenReturn(returnOneOfferWithoutId());
        //when
        OfferDto offer = offerService.createOrUpdateOffer(OfferMapper.mapToOfferDto(returnOneOfferWithoutId()));
        //then
        assertThat(offer).isNotNull().hasFieldOrPropertyWithValue("id", null);
    }

    @Test
    public void should_throw_WrongArgumentException_when_offer_id_is_not_a_number_in_delete_offer_by_id_method() {
        //given //when //then
        assertThrows(WrongArgumentException.class, () -> offerService.deleteOfferById(""));
    }

}
