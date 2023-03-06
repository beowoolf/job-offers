package pl.offers.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("jobOffers")
public class Offer {

    @Id
    private Long id;

    @Field("company_name")
    private String companyName;

    @Field("job_position")
    private String jobPosition;

    @Field("salary")
    private String salary;

    @Field("offer_url")
    private String offerUrl;

}
