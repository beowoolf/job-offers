package pl.offers.job;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@ControllerAdvice
public class OfferControllerErrorHandler {

    @ExceptionHandler(value = OfferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    protected OfferErrorResponse handleOfferNotFoundException(OfferNotFoundException e) {
        return new OfferErrorResponse(e.getMessage(), returnNewDate(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = WrongArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    protected OfferErrorResponse handleWrongArgumentException(WrongArgumentException e) {
        return new OfferErrorResponse(e.getMessage(), returnNewDate(), HttpStatus.BAD_REQUEST);
    }

    private String returnNewDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

}
