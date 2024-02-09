package pl.offers.job.domain.job.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class EmploymentType implements Serializable {

    @JsonProperty("to")
    int to;
    @JsonProperty("from")
    int from;
    @JsonProperty("type")
    String type;
    @JsonProperty("to_chf")
    double to_chf;
    @JsonProperty("to_eur")
    Object to_eur;
    @JsonProperty("to_gbp")
    double to_gbp;
    @JsonProperty("to_pln")
    Object to_pln;
    @JsonProperty("to_usd")
    Object to_usd;
    @JsonProperty("currency")
    String currency;
    @JsonProperty("from_chf")
    double from_chf;
    @JsonProperty("from_eur")
    Object from_eur;
    @JsonProperty("from_gbp")
    double from_gbp;
    @JsonProperty("from_pln")
    Object from_pln;
    @JsonProperty("from_usd")
    Object from_usd;

}
