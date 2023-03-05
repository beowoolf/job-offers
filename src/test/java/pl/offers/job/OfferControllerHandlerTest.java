package pl.offers.job;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OfferControllerHandlerTest {

    OfferControllerErrorHandler errorHandler = new OfferControllerErrorHandler();

    @Test
    public void should_return_offer_response_with_offerNotFoundException() {
        //given
        OfferErrorResponse expectedOfferResponse = new OfferErrorResponse("", returnNewDate(), HttpStatus.NOT_FOUND);
        //when
        OfferErrorResponse receivedOfferResponse = errorHandler.handleOfferNotFoundException(new OfferNotFoundException(""));
        //then
        assertThat(expectedOfferResponse).isEqualTo(receivedOfferResponse);
    }

    @Test
    public void should_return_offer_response_with_wrongArgumentException() {
        //given
        OfferErrorResponse expectedOfferResponse = new OfferErrorResponse("", returnNewDate(), HttpStatus.BAD_REQUEST);
        //when
        OfferErrorResponse receivedOfferResponse = errorHandler.handleWrongArgumentException(new WrongArgumentException(""));
        //then
        assertThat(expectedOfferResponse).isEqualTo(receivedOfferResponse);
    }

    private String returnNewDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

}
