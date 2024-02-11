package pl.offers.job.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import pl.offers.job.integration.BaseIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JobUrlDuplicateErrorIntegrationTest extends BaseIntegrationTest {

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    @WithMockUser
    public void should_return_409_conflict_when_added_second_job_with_same_job_url() throws Exception {
        // step 1
        // given
        String jobToCreateJson = """
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
                 		}
                """;
        // when
        ResultActions perform = mockMvc.perform(post("/jobs")
                .content(jobToCreateJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );
        // then
        perform.andExpect(status().isCreated());


        // step 2
        // given && when
        ResultActions perform1 = mockMvc.perform(post("/jobs")
                .content(jobToCreateJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );
        // then
        perform1.andExpect(status().isConflict());
    }

}
