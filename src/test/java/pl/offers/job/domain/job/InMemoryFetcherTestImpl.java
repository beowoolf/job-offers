package pl.offers.job.domain.job;

import pl.offers.job.domain.job.dto.JobResponse;

import java.util.List;

public class InMemoryFetcherTestImpl implements JobFetchable {

    List<JobResponse> listOfJobs;

    InMemoryFetcherTestImpl(List<JobResponse> listOfJobs) {
        this.listOfJobs = listOfJobs;
    }

    @Override
    public List<JobResponse> fetchJobs() {
        return listOfJobs;
    }

}
