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

public class OfferUrlDuplicateErrorIntegrationTest extends BaseIntegrationTest {

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", DATABASE_CONTAINER::getReplicaSetUrl);
    }

    @Test
    @WithMockUser
    public void should_return_409_conflict_when_added_second_offer_with_same_offer_url() throws Exception {
        // step 1
        // given && when
        ResultActions perform = mockMvc.perform(post("/offers")
                .content("""
                        {
                        "companyName": "someCompany",
                        "position": "somePosition",
                        "salary": "7 000 - 9 000 PLN",
                        "offerUrl": "https://newoffers.pl/offer/1234"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );
        // then
        perform.andExpect(status().isCreated());


        // step 2
        // given && when
        ResultActions perform1 = mockMvc.perform(post("/offers")
                .content("""
                        {
                        "companyName": "someCompany",
                        "position": "somePosition",
                        "salary": "7 000 - 9 000 PLN",
                        "offerUrl": "https://newoffers.pl/offer/1234"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
        );
        // then
        perform1.andExpect(status().isConflict());
    }

}
