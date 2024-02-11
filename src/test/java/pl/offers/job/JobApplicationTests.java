package pl.offers.job;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import pl.offers.job.integration.BaseIntegrationTest;

class JobApplicationTests extends BaseIntegrationTest {

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("job.http.client.config.uri", () -> WIRE_MOCK_HOST);
        registry.add("job.http.client.config.port", () -> wireMockServer.getPort());
    }

    @Test
    void contextLoads() {
    }

}
