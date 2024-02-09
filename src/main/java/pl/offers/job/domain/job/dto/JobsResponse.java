package pl.offers.job.domain.job.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class JobsResponse {

    boolean success;
    List<JobResponse> list;

}
