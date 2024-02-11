package pl.offers.job.domain.job;

import pl.offers.job.domain.job.dto.JobsResponse;

public class InMemoryFetcherTestImpl implements JobFetchable {

    JobsResponse listOfJobs;

    InMemoryFetcherTestImpl(JobsResponse listOfJobs) {
        this.listOfJobs = listOfJobs;
    }

    @Override
    public JobsResponse fetchJobs() {
        return listOfJobs;
    }

}
