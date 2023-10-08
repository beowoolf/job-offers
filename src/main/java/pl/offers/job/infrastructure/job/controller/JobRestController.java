package pl.offers.job.infrastructure.job.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.offers.job.domain.job.JobFacade;
import pl.offers.job.domain.job.dto.JobRequestDto;
import pl.offers.job.domain.job.dto.JobResponseDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobRestController {

    private final JobFacade jobFacade;

    @GetMapping
    public ResponseEntity<List<JobResponseDto>> findAllOffers() {
        List<JobResponseDto> allOffers = jobFacade.findAllJobs();
        return ResponseEntity.ok(allOffers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDto> findOfferById(@PathVariable String id) {
        JobResponseDto offerById = jobFacade.findJobById(id);
        return ResponseEntity.ok(offerById);
    }

    @PostMapping
    public ResponseEntity<JobResponseDto> addOffer(@RequestBody @Valid JobRequestDto offerDto) {
        JobResponseDto offerResponseDto = jobFacade.saveJob(offerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(offerResponseDto);
    }

}
