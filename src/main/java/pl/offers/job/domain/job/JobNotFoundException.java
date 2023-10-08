package pl.offers.job.domain.job;

import lombok.Getter;

@Getter
public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException(String jobId) {
        super(String.format("Job with id %s not found", jobId));
    }

}
