package pl.offers.job.domain.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;
import pl.offers.job.domain.job.dto.JobRequestDto;
import pl.offers.job.domain.job.dto.JobResponse;
import pl.offers.job.domain.job.dto.JobResponseDto;
import pl.offers.job.domain.job.dto.JobsResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

public class JobFacadeTest {
    public ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_fetch_from_jobs_from_remote_and_save_all_jobs_when_repository_is_empty() throws JsonProcessingException {
        // given
        JobFacade jobFacade = new JobFacadeTestConfiguration().jobFacadeForTests();
        assertThat(jobFacade.findAllJobs()).isEmpty();

        // when
        List<JobResponseDto> result = jobFacade.fetchAllJobsAndSaveAllIfNotExists();

        // then
        assertThat(result).hasSize(4);
    }

    @Test
    public void should_save_only_2_jobs_when_repository_had_4_added_with_job_urls() throws JsonProcessingException {
        // given
        String json1 = """
                {
                	"slug": "leverx-integration-architect-cig",
                	"title": "Integration Architect (CIG)",
                	"requiredSkills": [
                		"SAP"
                	],
                	"niceToHaveSkills": null,
                	"workplaceType": "hybrid",
                	"workingTime": "full_time",
                	"experienceLevel": "mid",
                	"employmentTypes": [
                		{
                			"to": null,
                			"from": null,
                			"type": "permanent",
                			"to_chf": null,
                			"to_eur": null,
                			"to_gbp": null,
                			"to_pln": null,
                			"to_usd": null,
                			"currency": "usd",
                			"from_chf": null,
                			"from_eur": null,
                			"from_gbp": null,
                			"from_pln": null,
                			"from_usd": null
                		}
                	],
                	"categoryId": 23,
                	"multilocation": [
                		{
                			"city": "Wroclaw",
                			"slug": "leverx-integration-architect-cig",
                			"street": "Pilsudskiego 69, 50-019",
                			"latitude": 51.100532600000001,
                			"longitude": 17.0311415
                		}
                	],
                	"city": "Wroclaw",
                	"street": "Pilsudskiego 69, 50-019",
                	"latitude": "51.1005326",
                	"longitude": "17.0311415",
                	"remoteInterview": true,
                	"companyName": "LeverX",
                	"companyLogoThumbUrl": "https://public.justjoin.it/offers/company_logos/thumb_x2/d7395b4e4eb5edd530f1137435bb0a91a63f9117.jpg?1700137000",
                	"publishedAt": "2024-01-31T09:00:00.000Z",
                	"openToHireUkrainians": true
                }""";
        JobResponse parsedJson11 = objectMapper.readValue(json1, new TypeReference<>() {
        });
        parsedJson11.setSlug(parsedJson11.getSlug() + "1");
        JobResponse parsedJson12 = objectMapper.readValue(json1, new TypeReference<>() {
        });
        parsedJson12.setSlug(parsedJson12.getSlug() + "2");
        JobResponse parsedJson13 = objectMapper.readValue(json1, new TypeReference<>() {
        });
        parsedJson13.setSlug(parsedJson13.getSlug() + "3");
        JobResponse parsedJson14 = objectMapper.readValue(json1, new TypeReference<>() {
        });
        parsedJson14.setSlug(parsedJson14.getSlug() + "4");
        JobResponse parsedJson15 = objectMapper.readValue(json1, new TypeReference<>() {
        });
        parsedJson15.setSlug(parsedJson15.getSlug() + "5");
        JobResponse parsedJson16 = objectMapper.readValue(json1, new TypeReference<>() {
        });
        parsedJson16.setSlug(parsedJson16.getSlug() + "6");
        String json2 = """
                {
                	"slug": "leverx-integration-architect-cig",
                	"title": "Integration Architect (CIG)",
                	"requiredSkills": [
                		"SAP"
                	],
                	"niceToHaveSkills": null,
                	"workplaceType": "hybrid",
                	"workingTime": "full_time",
                	"experienceLevel": "mid",
                	"employmentTypes": [
                		{
                			"to": null,
                			"from": null,
                			"type": "permanent",
                			"to_chf": null,
                			"to_eur": null,
                			"to_gbp": null,
                			"to_pln": null,
                			"to_usd": null,
                			"currency": "usd",
                			"from_chf": null,
                			"from_eur": null,
                			"from_gbp": null,
                			"from_pln": null,
                			"from_usd": null
                		}
                	],
                	"categoryId": 23,
                	"multilocation": [
                		{
                			"city": "Wroclaw",
                			"slug": "leverx-integration-architect-cig",
                			"street": "Pilsudskiego 69, 50-019",
                			"latitude": 51.100532600000001,
                			"longitude": 17.0311415
                		}
                	],
                	"city": "Wroclaw",
                	"street": "Pilsudskiego 69, 50-019",
                	"latitude": "51.1005326",
                	"longitude": "17.0311415",
                	"remoteInterview": true,
                	"companyName": "LeverX",
                	"companyLogoThumbUrl": "https://public.justjoin.it/offers/company_logos/thumb_x2/d7395b4e4eb5edd530f1137435bb0a91a63f9117.jpg?1700137000",
                	"publishedAt": "2024-01-31T09:00:00.000Z",
                	"openToHireUkrainians": true
                }""";
        JobRequestDto parsedJson21 = objectMapper.readValue(json2, new TypeReference<>() {
        });
        parsedJson21.setSlug(parsedJson21.getSlug() + "1");
        JobRequestDto parsedJson22 = objectMapper.readValue(json2, new TypeReference<>() {
        });
        parsedJson22.setSlug(parsedJson22.getSlug() + "2");
        JobRequestDto parsedJson23 = objectMapper.readValue(json2, new TypeReference<>() {
        });
        parsedJson23.setSlug(parsedJson23.getSlug() + "3");
        JobRequestDto parsedJson24 = objectMapper.readValue(json2, new TypeReference<>() {
        });
        parsedJson24.setSlug(parsedJson24.getSlug() + "4");
        JobFacade jobFacade = new JobFacadeTestConfiguration(new JobsResponse(
                true,
                List.of(
                        parsedJson11,
                        parsedJson12,
                        parsedJson13,
                        parsedJson14,
                        parsedJson15,
                        parsedJson16
                ))
        ).jobFacadeForTests();
        jobFacade.saveJob(parsedJson21);
        jobFacade.saveJob(parsedJson22);
        jobFacade.saveJob(parsedJson23);
        jobFacade.saveJob(parsedJson24);
        assertThat(jobFacade.findAllJobs()).hasSize(4);

        // when
        List<JobResponseDto> response = jobFacade.fetchAllJobsAndSaveAllIfNotExists();

        // then
        assertThat(List.of(
                        response.get(0).getUrl(),
                        response.get(1).getUrl()
                )
        ).containsExactlyInAnyOrder("https://justjoin.it/offers/" + parsedJson11.getSlug(), "https://justjoin.it/offers/" + parsedJson16.getSlug());
    }

    @Test
    public void should_save_4_jobs_when_there_are_no_jobs_in_database() throws JsonProcessingException {
        // given
        String json = """
                {
                	"slug": "leverx-integration-architect-cig",
                	"title": "Integration Architect (CIG)",
                	"requiredSkills": [
                		"SAP"
                	],
                	"niceToHaveSkills": null,
                	"workplaceType": "hybrid",
                	"workingTime": "full_time",
                	"experienceLevel": "mid",
                	"employmentTypes": [
                		{
                			"to": null,
                			"from": null,
                			"type": "permanent",
                			"to_chf": null,
                			"to_eur": null,
                			"to_gbp": null,
                			"to_pln": null,
                			"to_usd": null,
                			"currency": "usd",
                			"from_chf": null,
                			"from_eur": null,
                			"from_gbp": null,
                			"from_pln": null,
                			"from_usd": null
                		}
                	],
                	"categoryId": 23,
                	"multilocation": [
                		{
                			"city": "Wroclaw",
                			"slug": "leverx-integration-architect-cig",
                			"street": "Pilsudskiego 69, 50-019",
                			"latitude": 51.100532600000001,
                			"longitude": 17.0311415
                		}
                	],
                	"city": "Wroclaw",
                	"street": "Pilsudskiego 69, 50-019",
                	"latitude": "51.1005326",
                	"longitude": "17.0311415",
                	"remoteInterview": true,
                	"companyName": "LeverX",
                	"companyLogoThumbUrl": "https://public.justjoin.it/offers/company_logos/thumb_x2/d7395b4e4eb5edd530f1137435bb0a91a63f9117.jpg?1700137000",
                	"publishedAt": "2024-01-31T09:00:00.000Z",
                	"openToHireUkrainians": true
                }""";
        JobRequestDto parsedJson1 = objectMapper.readValue(json, new TypeReference<>() {
        });
        parsedJson1.setSlug(parsedJson1.getSlug() + "1");
        JobRequestDto parsedJson2 = objectMapper.readValue(json, new TypeReference<>() {
        });
        parsedJson2.setSlug(parsedJson2.getSlug() + "2");
        JobRequestDto parsedJson3 = objectMapper.readValue(json, new TypeReference<>() {
        });
        parsedJson3.setSlug(parsedJson3.getSlug() + "3");
        JobRequestDto parsedJson4 = objectMapper.readValue(json, new TypeReference<>() {
        });
        parsedJson4.setSlug(parsedJson4.getSlug() + "4");
        JobFacade jobFacade = new JobFacadeTestConfiguration(new JobsResponse(true, List.of())).jobFacadeForTests();

        // when
        jobFacade.saveJob(parsedJson1);
        jobFacade.saveJob(parsedJson2);
        jobFacade.saveJob(parsedJson3);
        jobFacade.saveJob(parsedJson4);

        // then
        assertThat(jobFacade.findAllJobs()).hasSize(4);
    }

    @Test
    public void should_find_job_by_id_when_job_was_saved() throws JsonProcessingException {
        // given
        String json = """
                {
                	"slug": "leverx-integration-architect-cig",
                	"title": "Integration Architect (CIG)",
                	"requiredSkills": [
                		"SAP"
                	],
                	"niceToHaveSkills": null,
                	"workplaceType": "hybrid",
                	"workingTime": "full_time",
                	"experienceLevel": "mid",
                	"employmentTypes": [
                		{
                			"to": null,
                			"from": null,
                			"type": "permanent",
                			"to_chf": null,
                			"to_eur": null,
                			"to_gbp": null,
                			"to_pln": null,
                			"to_usd": null,
                			"currency": "usd",
                			"from_chf": null,
                			"from_eur": null,
                			"from_gbp": null,
                			"from_pln": null,
                			"from_usd": null
                		}
                	],
                	"categoryId": 23,
                	"multilocation": [
                		{
                			"city": "Wroclaw",
                			"slug": "leverx-integration-architect-cig",
                			"street": "Pilsudskiego 69, 50-019",
                			"latitude": 51.100532600000001,
                			"longitude": 17.0311415
                		}
                	],
                	"city": "Wroclaw",
                	"street": "Pilsudskiego 69, 50-019",
                	"latitude": "51.1005326",
                	"longitude": "17.0311415",
                	"remoteInterview": true,
                	"companyName": "LeverX",
                	"companyLogoThumbUrl": "https://public.justjoin.it/offers/company_logos/thumb_x2/d7395b4e4eb5edd530f1137435bb0a91a63f9117.jpg?1700137000",
                	"publishedAt": "2024-01-31T09:00:00.000Z",
                	"openToHireUkrainians": true
                }""";
        JobRequestDto parsedJson = objectMapper.readValue(json, new TypeReference<>() {
        });
        JobFacade jobFacade = new JobFacadeTestConfiguration(new JobsResponse(true, List.of())).jobFacadeForTests();
        JobResponseDto jobResponseDto = jobFacade.saveJob(parsedJson);
        // when
        JobResponseDto jobById = jobFacade.findJobById(jobResponseDto.getId());

        // then
        assertThat(jobById).isEqualTo(JobResponseDto.builder()
                .id(jobById.getId())
                .url(jobResponseDto.getUrl())
                .title(jobResponseDto.getTitle())
                .city(jobResponseDto.getCity())
                .street(jobResponseDto.getStreet())
                .addressText(jobResponseDto.getCity())
                .countryCode(jobResponseDto.getCountryCode())
                .addressText(jobResponseDto.getAddressText())
                .markerIcon(jobResponseDto.getMarkerIcon())
                .workplaceType(jobResponseDto.getWorkplaceType())
                .companyName(jobResponseDto.getCompanyName())
                .companyUrl(jobResponseDto.getCompanyUrl())
                .companySize(jobResponseDto.getCompanySize())
                .experienceLevel(jobResponseDto.getExperienceLevel())
                .latitude(jobResponseDto.getLatitude())
                .longitude(jobResponseDto.getLongitude())
                .publishedAt(jobResponseDto.getPublishedAt())
                .remoteInterview(jobResponseDto.getRemoteInterview())
                .openToHireUkrainians(jobResponseDto.getOpenToHireUkrainians())
                .displayOffer(jobResponseDto.getDisplayOffer())
                .employmentTypes(jobResponseDto.getEmploymentTypes())
                .companyLogoUrl(jobResponseDto.getCompanyLogoUrl())
                .skills(jobResponseDto.getSkills())
                .remote(jobResponseDto.getRemote())
                .multilocation(jobResponseDto.getMultilocation())
                .wayOfApply(jobResponseDto.getWayOfApply()).build()
        );
    }

    @Test
    public void should_throw_not_found_exception_when_job_not_found() {
        // given
        JobFacade jobFacade = new JobFacadeTestConfiguration(new JobsResponse(true, List.of())).jobFacadeForTests();
        assertThat(jobFacade.findAllJobs()).isEmpty();

        // when
        Throwable thrown = catchThrowable(() -> jobFacade.findJobById("100"));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(JobNotFoundException.class)
                .hasMessage("Job with id 100 not found");
    }

    @Test
    public void should_throw_duplicate_key_exception_when_with_job_url_exists() throws JsonProcessingException {
        // given
        String json = """
                {
                	"slug": "leverx-integration-architect-cig",
                	"title": "Integration Architect (CIG)",
                	"requiredSkills": [
                		"SAP"
                	],
                	"niceToHaveSkills": null,
                	"workplaceType": "hybrid",
                	"workingTime": "full_time",
                	"experienceLevel": "mid",
                	"employmentTypes": [
                		{
                			"to": null,
                			"from": null,
                			"type": "permanent",
                			"to_chf": null,
                			"to_eur": null,
                			"to_gbp": null,
                			"to_pln": null,
                			"to_usd": null,
                			"currency": "usd",
                			"from_chf": null,
                			"from_eur": null,
                			"from_gbp": null,
                			"from_pln": null,
                			"from_usd": null
                		}
                	],
                	"categoryId": 23,
                	"multilocation": [
                		{
                			"city": "Wroclaw",
                			"slug": "leverx-integration-architect-cig",
                			"street": "Pilsudskiego 69, 50-019",
                			"latitude": 51.100532600000001,
                			"longitude": 17.0311415
                		}
                	],
                	"city": "Wroclaw",
                	"street": "Pilsudskiego 69, 50-019",
                	"latitude": "51.1005326",
                	"longitude": "17.0311415",
                	"remoteInterview": true,
                	"companyName": "LeverX",
                	"companyLogoThumbUrl": "https://public.justjoin.it/offers/company_logos/thumb_x2/d7395b4e4eb5edd530f1137435bb0a91a63f9117.jpg?1700137000",
                	"publishedAt": "2024-01-31T09:00:00.000Z",
                	"openToHireUkrainians": true
                }""";
        JobRequestDto parsedJson = objectMapper.readValue(json, new TypeReference<>() {
        });
        JobFacade jobFacade = new JobFacadeTestConfiguration(new JobsResponse(true, List.of())).jobFacadeForTests();
        JobResponseDto jobResponseDto = jobFacade.saveJob(parsedJson);
        String savedId = jobResponseDto.getId();
        assertThat(jobFacade.findJobById(savedId).getId()).isEqualTo(savedId);
        String json2 = """
                {
                 			"slug": "leverx-integration-architect-cig",
                 			"title": "Integration Architect (CIG)",
                 			"requiredSkills": [
                 				"SAP"
                 			],
                 			"niceToHaveSkills": null,
                 			"workplaceType": "hybrid",
                 			"workingTime": "full_time",
                 			"experienceLevel": "mid",
                 			"employmentTypes": [
                 				{
                 					"to": null,
                 					"from": null,
                 					"type": "permanent",
                 					"to_chf": null,
                 					"to_eur": null,
                 					"to_gbp": null,
                 					"to_pln": null,
                 					"to_usd": null,
                 					"currency": "usd",
                 					"from_chf": null,
                 					"from_eur": null,
                 					"from_gbp": null,
                 					"from_pln": null,
                 					"from_usd": null
                 				}
                 			],
                 			"categoryId": 23,
                 			"multilocation": [
                 				{
                 					"city": "Wroclaw",
                 					"slug": "leverx-integration-architect-cig",
                 					"street": "Pilsudskiego 69, 50-019",
                 					"latitude": 51.100532600000001,
                 					"longitude": 17.0311415
                 				}
                 			],
                 			"city": "Wroclaw",
                 			"street": "Pilsudskiego 69, 50-019",
                 			"latitude": "51.1005326",
                 			"longitude": "17.0311415",
                 			"remoteInterview": true,
                 			"companyName": "LeverX",
                 			"companyLogoThumbUrl": "https://public.justjoin.it/offers/company_logos/thumb_x2/d7395b4e4eb5edd530f1137435bb0a91a63f9117.jpg?1700137000",
                 			"publishedAt": "2024-01-31T09:00:00.000Z",
                 			"openToHireUkrainians": true
                 		}""";
        JobRequestDto parsedJson2 = objectMapper.readValue(json2, new TypeReference<>() {
        });
        // when
        Throwable thrown = catchThrowable(() -> jobFacade.saveJob(parsedJson2));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(DuplicateKeyException.class)
                .hasMessage("Job with slug [leverx-integration-architect-cig] already exists");
    }

}
