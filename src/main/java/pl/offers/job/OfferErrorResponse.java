package pl.offers.job;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class OfferErrorResponse {

    private String message;
    private String timestamp;
    private HttpStatus httpStatus;

}
