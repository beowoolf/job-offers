package pl.offers.job.integration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import pl.offers.job.integration.BaseIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JobUrlDuplicateErrorIntegrationTest extends BaseIntegrationTest {

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

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
                    "id": "backend-developer-wroclaw",
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
