package pl.offers.job.domain.job;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import pl.offers.job.domain.job.dto.JobRequestDto;
import pl.offers.job.domain.job.dto.JobResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JobFacade {

    private final JobRepository jobRepository;
    private final JobService jobService;

    @Cacheable(cacheNames = "jobs")
    public List<JobResponseDto> findAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(JobMapper::mapFromJobToJobResponseDto)
                .collect(Collectors.toList());
    }

    public List<JobResponseDto> fetchAllJobsAndSaveAllIfNotExists() {
        return jobService.fetchAllJobsAndSaveAllIfNotExists()
                .stream()
                .map(JobMapper::mapFromJobToJobResponseDto)
                .toList();
    }

    public JobResponseDto findJobById(String id) {
        return jobRepository.findById(id)
                .map(JobMapper::mapFromJobToJobResponseDto)
                .orElseThrow(() -> new JobNotFoundException(id));
    }

    public JobResponseDto saveJob(JobRequestDto jobDto) {
        final Job job = JobMapper.mapFromJobDtoToJob(jobDto);
        final Job save = jobRepository.save(job);
        return JobMapper.mapFromJobToJobResponseDto(save);
    }

}
