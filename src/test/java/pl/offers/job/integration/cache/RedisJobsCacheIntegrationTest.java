package pl.offers.job.integration.cache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import pl.offers.job.domain.job.JobFacade;
import pl.offers.job.infrastructure.loginandregister.controller.dto.JwtResponseDto;
import pl.offers.job.integration.BaseIntegrationTest;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RedisJobsCacheIntegrationTest extends BaseIntegrationTest {

    @Container
    private static final GenericContainer<?> REDIS;

    static {
        REDIS = new GenericContainer<>("redis").withExposedPorts(6379);
        REDIS.start();
    }

    @SpyBean
    JobFacade jobFacade;
    @Autowired
    CacheManager cacheManager;

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.redis.port", () -> REDIS.getFirstMappedPort().toString());
        registry.add("spring.cache.type", () -> "redis");
        registry.add("spring.cache.redis.time-to-live", () -> "PT1S");
    }

    @Test
    public void should_save_jobs_to_cache_and_then_invalidate_by_time_to_live() throws Exception {
        // step 1: someUser was registered with somePassword
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
        registerAction.andExpect(status().isCreated());

        // step 2: login
        // given && when
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
        String jwtToken = jwtResponse.token();

        // step 3: should save to cache jobs request
        // given && when
        mockMvc.perform(get("/jobs")
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        verify(jobFacade, times(1)).findAllJobs();
        assertThat(cacheManager.getCacheNames().contains("jobs")).isTrue();

        // step 4: cache should be invalidated
        // given && when && then
        await()
                .atMost(Duration.ofSeconds(4))
                .pollInterval(Duration.ofSeconds(1))
                .untilAsserted(() -> {
                            mockMvc.perform(get("/jobs")
                                    .header("Authorization", "Bearer " + jwtToken)
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                            );
                            verify(jobFacade, atLeast(2)).findAllJobs();
                        }
                );
    }

}
