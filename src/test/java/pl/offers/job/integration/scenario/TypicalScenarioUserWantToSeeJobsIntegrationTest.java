package pl.offers.job.integration.scenario;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import pl.offers.job.domain.job.dto.JobRequestDto;
import pl.offers.job.domain.job.dto.JobResponseDto;
import pl.offers.job.domain.loginandregister.dto.RegistrationResultDto;
import pl.offers.job.infrastructure.job.scheduler.HttpJobsScheduler;
import pl.offers.job.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import pl.offers.job.integration.BaseIntegrationTest;
import pl.offers.job.integration.SampleJobResponse;

import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TypicalScenarioUserWantToSeeJobsIntegrationTest extends BaseIntegrationTest implements SampleJobResponse {

    public static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    public static final String APPLICATION_JSON_CONTENT_TYPE_VALUE = "application/json";
    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    @Autowired
    HttpJobsScheduler httpJobsScheduler;

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("job.http.client.config.uri", () -> WIRE_MOCK_HOST);
        registry.add("job.http.client.config.port", () -> wireMockServer.getPort());
    }

    @Test
    public void user_want_to_see_offers_but_have_to_be_logged_in_and_external_server_should_have_some_offers() throws Exception {
        // step 1: there are no offers in external HTTP server
        // given
        String externalEndpointUrl = "/api/offers";
        // when && then
        wireMockServer.stubFor(WireMock.get(externalEndpointUrl)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withBody(bodyWithZeroJobsJson())));


        // step 2: scheduler ran 1st time and made GET to external server and system added 0 offers to database
        // given && when
        List<JobResponseDto> newJobs = httpJobsScheduler.fetchAllJobsAndSaveAllIfNotExists();
        // then
        assertThat(newJobs).isEmpty();


        //step 3: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned UNAUTHORIZED(401)
        // given & when
        ResultActions failedLoginRequest = mockMvc.perform(post("/token")
                .content("""
                        {
                        "username": "someUser",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        failedLoginRequest
                .andExpect(status().isUnauthorized())
                .andExpect(content().json("""
                        {
                          "message": "Bad Credentials",
                          "status": "UNAUTHORIZED"
                        }
                        """.trim()));


        //step 4: user made GET /jobs with no jwt token and system returned UNAUTHORIZED(401)
        // given
        String jobsUrl = "/jobs";
        // when
        ResultActions failedGetJobsRequest = mockMvc.perform(get(jobsUrl)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        failedGetJobsRequest.andExpect(status().isForbidden());


        //step 5: user made POST /register with username=someUser, password=somePassword and system registered user with status CREATED(201)
        // given & when
        ResultActions registerAction = mockMvc.perform(post("/register")
                .content("""
                        {
                        "username": "someUser",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult registerActionResult = registerAction.andExpect(status().isCreated()).andReturn();
        String registerActionResultJson = registerActionResult.getResponse().getContentAsString();
        RegistrationResultDto registrationResultDto = objectMapper.readValue(registerActionResultJson, RegistrationResultDto.class);
        assertAll(
                () -> assertThat(registrationResultDto.username()).isEqualTo("someUser"),
                () -> assertThat(registrationResultDto.created()).isTrue(),
                () -> assertThat(registrationResultDto.id()).isNotNull()
        );


        //step 6: user tried to get JWT token by requesting POST /token with username=someUser, password=somePassword and system returned OK(200) and jwttoken=AAAA.BBBB.CCC
        // given & when
        ResultActions successLoginRequest = mockMvc.perform(post("/token")
                .content("""
                        {
                        "username": "someUser",
                        "password": "somePassword"
                        }
                        """.trim())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult mvcResult = successLoginRequest.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        JwtResponseDto jwtResponse = objectMapper.readValue(json, JwtResponseDto.class);
        String token = jwtResponse.token();
        assertAll(
                () -> assertThat(jwtResponse.username()).isEqualTo("someUser"),
                () -> assertThat(token).matches(Pattern.compile("^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=])+\\.?$"))
        );


        //step 7: user made GET /jobs with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 0 offers
        // given & when
        ResultActions perform = mockMvc.perform(get(jobsUrl)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult mvcResult2 = perform.andExpect(status().isOk()).andReturn();
        String jsonWithJobs = mvcResult2.getResponse().getContentAsString();
        List<JobResponseDto> offers = objectMapper.readValue(jsonWithJobs, new TypeReference<>() {
        });
        assertThat(offers).isEmpty();


        //step 8: there are 2 new offers in external HTTP server
        // given && when && then
        wireMockServer.stubFor(WireMock.get(externalEndpointUrl)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withBody(bodyWithTwoJobsJson())));


        //step 9: scheduler ran 2nd time and made GET to external server and system added 2 new offers with ids: 1000 and 2000 to database
        // given && when
        List<JobResponseDto> twoNewJobs = httpJobsScheduler.fetchAllJobsAndSaveAllIfNotExists();
        // then
        assertThat(twoNewJobs).hasSize(2);


        //step 10: user made GET /jobs with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 2 offers with ids: 1000 and 2000
        // given && when
        ResultActions performGetForTwoJobs = mockMvc.perform(get(jobsUrl)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult performGetForTwoJobsMvcResult = performGetForTwoJobs.andExpect(status().isOk()).andReturn();
        String jsonWithTwoJobs = performGetForTwoJobsMvcResult.getResponse().getContentAsString();
        List<JobResponseDto> twoJobs = objectMapper.readValue(jsonWithTwoJobs, new TypeReference<>() {
        });
        assertThat(twoJobs).hasSize(2);
        JobResponseDto expectedFirstJob = twoNewJobs.get(0);
        JobResponseDto expectedSecondJob = twoNewJobs.get(1);
        assertThat(twoJobs).containsExactlyInAnyOrder(
                new JobResponseDto(
                        expectedFirstJob.getId(),
                        expectedFirstJob.getUrl(),
                        expectedFirstJob.getTitle(),
                        expectedFirstJob.getStreet(),
                        expectedFirstJob.getCity(),
                        expectedFirstJob.getCountryCode(),
                        expectedFirstJob.getAddressText(),
                        expectedFirstJob.getMarkerIcon(),
                        expectedFirstJob.getWorkplaceType(),
                        expectedFirstJob.getCompanyName(),
                        expectedFirstJob.getCompanyUrl(),
                        expectedFirstJob.getCompanySize(),
                        expectedFirstJob.getExperienceLevel(),
                        expectedFirstJob.getLatitude(),
                        expectedFirstJob.getLongitude(),
                        expectedFirstJob.getPublishedAt(),
                        expectedFirstJob.getRemoteInterview(),
                        expectedFirstJob.getOpenToHireUkrainians(),
                        expectedFirstJob.getDisplayOffer(),
                        expectedFirstJob.getEmploymentTypes(),
                        expectedFirstJob.getCompanyLogoUrl(),
                        expectedFirstJob.getSkills(),
                        expectedFirstJob.getRemote(),
                        expectedFirstJob.getMultilocation(),
                        expectedFirstJob.getWayOfApply()
                ),
                new JobResponseDto(
                        expectedSecondJob.getId(),
                        expectedSecondJob.getUrl(),
                        expectedSecondJob.getTitle(),
                        expectedSecondJob.getStreet(),
                        expectedSecondJob.getCity(),
                        expectedSecondJob.getCountryCode(),
                        expectedSecondJob.getAddressText(),
                        expectedSecondJob.getMarkerIcon(),
                        expectedSecondJob.getWorkplaceType(),
                        expectedSecondJob.getCompanyName(),
                        expectedSecondJob.getCompanyUrl(),
                        expectedSecondJob.getCompanySize(),
                        expectedSecondJob.getExperienceLevel(),
                        expectedSecondJob.getLatitude(),
                        expectedSecondJob.getLongitude(),
                        expectedSecondJob.getPublishedAt(),
                        expectedSecondJob.getRemoteInterview(),
                        expectedSecondJob.getOpenToHireUkrainians(),
                        expectedSecondJob.getDisplayOffer(),
                        expectedSecondJob.getEmploymentTypes(),
                        expectedSecondJob.getCompanyLogoUrl(),
                        expectedSecondJob.getSkills(),
                        expectedSecondJob.getRemote(),
                        expectedSecondJob.getMultilocation(),
                        expectedSecondJob.getWayOfApply()
                )
        );


        //step 11: user made GET /jobs/9999 and system returned NOT_FOUND(404) with message “Job with id 9999 not found”
        // given
        // when
        ResultActions performGetJobsNotExisitingId = mockMvc.perform(get("/jobs/9999")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        performGetJobsNotExisitingId.andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                        "message":  "Job with id 9999 not found",
                        "status": "NOT_FOUND"
                        }
                        """.trim()));


        //step 12: user made GET /jobs/1000 and system returned OK(200) with offer
        // given
        String offerIdAddedToDatabase = expectedFirstJob.getId();
        // when
        ResultActions getJobById = mockMvc.perform(get("/jobs/" + offerIdAddedToDatabase)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        String singleJobByJobUrlJson = getJobById.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JobResponseDto singleJobByJobUrl = objectMapper.readValue(singleJobByJobUrlJson, JobResponseDto.class);
        assertThat(singleJobByJobUrl).isEqualTo(expectedFirstJob);


        //step 13: there are 2 new offers in external HTTP server
        // given && when && then
        wireMockServer.stubFor(WireMock.get(externalEndpointUrl)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_HEADER_KEY, APPLICATION_JSON_CONTENT_TYPE_VALUE)
                        .withBody(bodyWithFourJobsJson())));


        //step 14: scheduler ran 3rd time and made GET to external server and system added 2 new offers with ids: 3000 and 4000 to database
        // given && when
        List<JobResponseDto> nextTwoNewJobs = httpJobsScheduler.fetchAllJobsAndSaveAllIfNotExists();
        // then
        assertThat(nextTwoNewJobs).hasSize(2);


        //step 15: user made GET /jobs with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 4 offers with ids: 1000,2000, 3000 and 4000
        // given && when
        ResultActions performGetForFourJobs = mockMvc.perform(get(jobsUrl)
                .header("Authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult performGetForFourJobsMvcResult = performGetForFourJobs.andExpect(status().isOk()).andReturn();
        String jsonWithFourJobs = performGetForFourJobsMvcResult.getResponse().getContentAsString();
        List<JobResponseDto> fourJobs = objectMapper.readValue(jsonWithFourJobs, new TypeReference<>() {
        });
        assertThat(fourJobs).hasSize(4);
        JobResponseDto expectedThirdJob = nextTwoNewJobs.get(0);
        JobResponseDto expectedFourthJob = nextTwoNewJobs.get(1);
        assertThat(fourJobs).contains(
                new JobResponseDto(
                        expectedThirdJob.getId(),
                        expectedThirdJob.getUrl(),
                        expectedThirdJob.getTitle(),
                        expectedThirdJob.getStreet(),
                        expectedThirdJob.getCity(),
                        expectedThirdJob.getCountryCode(),
                        expectedThirdJob.getAddressText(),
                        expectedThirdJob.getMarkerIcon(),
                        expectedThirdJob.getWorkplaceType(),
                        expectedThirdJob.getCompanyName(),
                        expectedThirdJob.getCompanyUrl(),
                        expectedThirdJob.getCompanySize(),
                        expectedThirdJob.getExperienceLevel(),
                        expectedThirdJob.getLatitude(),
                        expectedThirdJob.getLongitude(),
                        expectedThirdJob.getPublishedAt(),
                        expectedThirdJob.getRemoteInterview(),
                        expectedThirdJob.getOpenToHireUkrainians(),
                        expectedThirdJob.getDisplayOffer(),
                        expectedThirdJob.getEmploymentTypes(),
                        expectedThirdJob.getCompanyLogoUrl(),
                        expectedThirdJob.getSkills(),
                        expectedThirdJob.getRemote(),
                        expectedThirdJob.getMultilocation(),
                        expectedThirdJob.getWayOfApply()
                ),
                new JobResponseDto(
                        expectedFourthJob.getId(),
                        expectedFourthJob.getUrl(),
                        expectedFourthJob.getTitle(),
                        expectedFourthJob.getStreet(),
                        expectedFourthJob.getCity(),
                        expectedFourthJob.getCountryCode(),
                        expectedFourthJob.getAddressText(),
                        expectedFourthJob.getMarkerIcon(),
                        expectedFourthJob.getWorkplaceType(),
                        expectedFourthJob.getCompanyName(),
                        expectedFourthJob.getCompanyUrl(),
                        expectedFourthJob.getCompanySize(),
                        expectedFourthJob.getExperienceLevel(),
                        expectedFourthJob.getLatitude(),
                        expectedFourthJob.getLongitude(),
                        expectedFourthJob.getPublishedAt(),
                        expectedFourthJob.getRemoteInterview(),
                        expectedFourthJob.getOpenToHireUkrainians(),
                        expectedFourthJob.getDisplayOffer(),
                        expectedFourthJob.getEmploymentTypes(),
                        expectedFourthJob.getCompanyLogoUrl(),
                        expectedFourthJob.getSkills(),
                        expectedFourthJob.getRemote(),
                        expectedFourthJob.getMultilocation(),
                        expectedFourthJob.getWayOfApply()
                ));


        //step 16: user made POST /jobs with header “Authorization: Bearer AAAA.BBBB.CCC” and offer as body and system returned CREATED(201) with saved offer
        // given
        String jobToCreateJson = """
                {
                    "title": "Backend Developer",
                    "street": "Centrum",
                    "city": "Wrocław",
                    "country_code": "PL",
                    "address_text": "Centrum, Wrocław",
                    "marker_icon": "go",
                    "workplace_type": "remote",
                    "company_name": "Gamesture Sp. z o.o.",
                    "company_url": "http://gamesture.com",
                    "company_size": "80",
                    "experience_level": "mid",
                    "latitude": "51.1078852",
                    "longitude": "17.0385376",
                    "published_at": "2023-04-19T11:00:14.909Z",
                    "remote_interview": true,
                    "open_to_hire_ukrainians": true,
                    "id": "http://gamesture.com/offers/backend-developer-wroclaw",
                    "display_offer": false,
                    "employment_types": [
                        {
                            "type": "permanent",
                            "salary": {
                                "from": 8000,
                                "to": 16000,
                                "currency": "pln"
                            }
                        }
                    ],
                    "company_logo_url": "https://bucket.justjoin.it/offers/company_logos/thumb/4d9a5369b294d3b34782e26178c82a78836d3073.png?1680604238",
                    "skills": [
                        {
                            "name": "Python",
                            "level": 1
                        },
                        {
                            "name": "SQL",
                            "level": 3
                        },
                        {
                            "name": "Golang",
                            "level": 3
                        }
                    ],
                    "remote": true,
                    "multilocation": [
                        {
                            "city": "Wrocław",
                            "street": "Centrum",
                            "slug": "gamesture-sp-z-o-o-backend-developer-wroclaw"
                        }
                    ],
                    "way_of_apply": "redirect"
                }
                """;
        // when
        ResultActions performPostJobsWithOneJob = mockMvc.perform(post(jobsUrl)
                .header("Authorization", "Bearer " + token)
                .content(jobToCreateJson)
                .accept(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        String createdJobJson = performPostJobsWithOneJob.andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JobRequestDto parsedJobToCreate = objectMapper.readValue(jobToCreateJson, JobRequestDto.class);
        JobResponseDto parsedCreatedJobJson = objectMapper.readValue(createdJobJson, JobResponseDto.class);
        String id = parsedCreatedJobJson.getId();
        assertAll(
                () -> assertThat(parsedCreatedJobJson.getAddressText()).isEqualTo(parsedJobToCreate.getAddressText()),
                () -> assertThat(parsedCreatedJobJson.getCity()).isEqualTo(parsedJobToCreate.getCity()),
                () -> assertThat(parsedCreatedJobJson.getCompanyLogoUrl()).isEqualTo(parsedJobToCreate.getCompanyLogoUrl()),
                () -> assertThat(parsedCreatedJobJson.getCompanyName()).isEqualTo(parsedJobToCreate.getCompanyName()),
                () -> assertThat(parsedCreatedJobJson.getCompanySize()).isEqualTo(parsedJobToCreate.getCompanySize()),
                () -> assertThat(parsedCreatedJobJson.getCompanyUrl()).isEqualTo(parsedJobToCreate.getCompanyUrl()),
                () -> assertThat(parsedCreatedJobJson.getCountryCode()).isEqualTo(parsedJobToCreate.getCountryCode()),
                () -> assertThat(parsedCreatedJobJson.getDisplayOffer()).isEqualTo(parsedJobToCreate.getDisplayOffer()),
                () -> assertThat(parsedCreatedJobJson.getEmploymentTypes()).isEqualTo(parsedJobToCreate.getEmploymentTypes()),
                () -> assertThat(parsedCreatedJobJson.getExperienceLevel()).isEqualTo(parsedJobToCreate.getExperienceLevel()),
                () -> assertThat(parsedCreatedJobJson.getLatitude()).isEqualTo(parsedJobToCreate.getLatitude()),
                () -> assertThat(parsedCreatedJobJson.getLongitude()).isEqualTo(parsedJobToCreate.getLongitude()),
                () -> assertThat(parsedCreatedJobJson.getMarkerIcon()).isEqualTo(parsedJobToCreate.getMarkerIcon()),
                () -> assertThat(parsedCreatedJobJson.getMultilocation()).isEqualTo(parsedJobToCreate.getMultilocation()),
                () -> assertThat(parsedCreatedJobJson.getOpenToHireUkrainians()).isEqualTo(parsedJobToCreate.getOpenToHireUkrainians()),
                () -> assertThat(parsedCreatedJobJson.getPublishedAt()).isEqualTo(parsedJobToCreate.getPublishedAt()),
                () -> assertThat(parsedCreatedJobJson.getRemote()).isEqualTo(parsedJobToCreate.getRemote()),
                () -> assertThat(parsedCreatedJobJson.getRemoteInterview()).isEqualTo(parsedJobToCreate.getRemoteInterview()),
                () -> assertThat(parsedCreatedJobJson.getSkills()).isEqualTo(parsedJobToCreate.getSkills()),
                () -> assertThat(parsedCreatedJobJson.getStreet()).isEqualTo(parsedJobToCreate.getStreet()),
                () -> assertThat(parsedCreatedJobJson.getTitle()).isEqualTo(parsedJobToCreate.getTitle()),
                () -> assertThat(parsedCreatedJobJson.getUrl()).isEqualTo(parsedJobToCreate.getId()),
                () -> assertThat(parsedCreatedJobJson.getWayOfApply()).isEqualTo(parsedJobToCreate.getWayOfApply()),
                () -> assertThat(parsedCreatedJobJson.getWorkplaceType()).isEqualTo(parsedJobToCreate.getWorkplaceType())
        );


        //step 17: user made GET /jobs with header “Authorization: Bearer AAAA.BBBB.CCC” and system returned OK(200) with 5 jobs
        // given & when
        ResultActions peformGetJobs = mockMvc.perform(get(jobsUrl)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        String oneJobJson = peformGetJobs.andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<JobResponseDto> parsedJsonWithOneJob = objectMapper.readValue(oneJobJson, new TypeReference<>() {
        });
        assertThat(parsedJsonWithOneJob).hasSize(5);
        assertThat(parsedJsonWithOneJob.stream().map(JobResponseDto::getId)).contains(id);
    }

}
