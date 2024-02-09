package pl.offers.job.integration.api;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.offers.job.infrastructure.api.ApiValidationErrorDto;
import pl.offers.job.integration.BaseIntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiValidationFailedIntegrationTest extends BaseIntegrationTest {

    @Test
    @WithMockUser
    public void should_return_400_bad_request_and_validation_message_when_empty_and_null_in_offer_save_request() throws Exception {
        // given & when
        ResultActions perform = mockMvc.perform(post("/offers")
                .content("""
                        {
                        "companyName": "",
                        "position": "",
                        "salary": ""
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );
        // then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto result = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(result.messages()).containsExactlyInAnyOrder(
                "companyName must not be empty",
                "position must not be empty",
                "salary must not be empty",
                "offerUrl must not be null",
                "offerUrl must not be empty");
    }

    @Test
    @WithMockUser
    public void should_return_400_bad_request_and_validation_message_when_empty_and_null_in_job_save_request() throws Exception {
        // given
        String jobToCreateJson = """
                {
                 	"success": true,
                 	"list": [
                 		{
                 			"slug": "",
                 			"title": "",
                 			"requiredSkills": [
                 				""
                 			],
                 			"workplaceType": "",
                 			"workingTime": "",
                 			"experienceLevel": "",
                 			"employmentTypes": [
                 				{
                 					"type": "",
                 					"currency": ""
                 				}
                 			],
                 			"multilocation": [
                 				{
                 					"city": "",
                 					"slug": "",
                 					"street": ""
                 				}
                 			],
                 			"city": "",
                 			"street": "",
                 			"latitude": "",
                 			"longitude": "",
                 			"companyName": "",
                 			"companyLogoThumbUrl": "",
                 			"publishedAt": ""
                 		}
                    ]
                }
                """;
        // when
        ResultActions perform = mockMvc.perform(post("/jobs")
                .content(jobToCreateJson)
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );
        // then
        MvcResult mvcResult = perform.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ApiValidationErrorDto result = objectMapper.readValue(json, ApiValidationErrorDto.class);
        assertThat(result.messages()).containsExactlyInAnyOrder(
                "title must not be empty",
                "title must not be null",
                "companyName must not be empty",
                "companyName must not be null",
                "latitude must not be empty",
                "latitude must not be null",
                "longitude must not be empty",
                "longitude must not be null",
                "experienceLevel must not be empty",
                "experienceLevel must not be null",
                "publishedAt must not be empty",
                "publishedAt must not be null",
                "requiredSkills must not be null",
                "id must not be empty",
                "id must not be null",
                "city must not be empty",
                "remoteInterview must not be null",
                "openToHireUkrainians must not be null",
                "companyLogoThumbUrl must not be empty",
                "companyLogoThumbUrl must not be null",
                "workplaceType must not be null",
                "city must not be null",
                "street must not be empty",
                "street must not be null",
                "workplaceType must not be empty",
                "workingTime must not be empty",
                "workingTime must not be null");
    }

}
