package pl.offers.job.domain.job;

import pl.offers.job.domain.job.dto.JobsResponse;

public interface JobFetchable {

    JobsResponse fetchJobs();

}
