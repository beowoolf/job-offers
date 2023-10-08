package pl.offers.job.domain.job;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobFacadeConfiguration {

    @Bean
    JobFacade jobFacade(JobFetchable jobFetchable, JobRepository repository) {
        JobService offerService = new JobService(jobFetchable, repository);
        return new JobFacade(repository, offerService);
    }

}
