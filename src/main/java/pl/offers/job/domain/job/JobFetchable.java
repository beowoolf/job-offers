package pl.offers.job.domain.job;

import pl.offers.job.domain.job.dto.JobResponse;

import java.util.List;

public interface JobFetchable {

    List<JobResponse> fetchJobs();

}
