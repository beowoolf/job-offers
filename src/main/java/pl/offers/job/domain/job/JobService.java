package pl.offers.job.domain.job;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JobService {

    private final JobFetchable jobOfferFetcher;
    private final JobRepository jobRepository;

    List<Job> fetchAllJobsAndSaveAllIfNotExists() {
        List<Job> jobOffers = fetchJobs();
        final List<Job> offers = filterNotExistingOffers(jobOffers);
        return jobRepository.saveAll(offers);
    }

    private List<Job> fetchJobs() {
        return jobOfferFetcher.fetchJobs()
                .stream()
                .map(JobMapper::mapFromJobResponseToJob)
                .toList();
    }

    private List<Job> filterNotExistingOffers(List<Job> jobOffers) {
        return jobOffers.stream()
                .filter(offerDto -> !offerDto.getUrl().isEmpty())
                .filter(offerDto -> !jobRepository.existsByUrl(offerDto.getUrl()))
                .collect(Collectors.toList());
    }

}
