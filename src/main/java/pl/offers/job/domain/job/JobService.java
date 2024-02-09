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
        List<Job> offers = filterNotExistingOffers(jobOffers);
        List<Job> uniqueOffers = filterUniqueUrls(offers);
        return jobRepository.saveAll(uniqueOffers);
    }

    private List<Job> fetchJobs() {
        return jobOfferFetcher.fetchJobs()
                .getList()
                .stream()
                .map(JobMapper::mapFromJobResponseToJob)
                .toList();
    }

    private List<Job> filterUniqueUrls(List<Job> jobs) {
        return jobs.stream()
                .collect(Collectors.toMap(Job::getUrl, job -> job, (existing, replacement) -> existing))
                .values()
                .stream()
                .collect(Collectors.toList());
    }

    private List<Job> filterNotExistingOffers(List<Job> jobOffers) {
        return jobOffers.stream()
                .filter(offerDto -> !offerDto.getUrl().isEmpty())
                .filter(offerDto -> !jobRepository.existsByUrl(offerDto.getUrl()))
                .collect(Collectors.toList());
    }

}
