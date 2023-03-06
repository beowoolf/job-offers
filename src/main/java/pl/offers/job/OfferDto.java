package pl.offers.job;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class OfferDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("companyName")
    private String companyName;
    @JsonProperty("jobPosition")
    private String jobPosition;
    @JsonProperty("salary")
    private String salary;
    @JsonProperty("offerUrl")
    private String offerUrl;

}
