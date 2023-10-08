package pl.offers.job.domain.job;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {

    boolean existsByUrl(String offerUrl);

}
