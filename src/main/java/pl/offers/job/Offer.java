package pl.offers.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Offer {

    private Long id;
    private String companyName;
    private String jobPosition;
    private String salary;
    private String offerUrl;

}
