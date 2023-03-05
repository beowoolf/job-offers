package pl.offers.job;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.util.SocketUtils;

import java.util.Arrays;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class OfferClientIntegrationTests {

    int port = SocketUtils.findAvailableTcpPort();
    String uri = "http://localhost:" + port + "/offers";
    RemoteOfferClient remoteOfferClient = new JobOfferTestConfig().remoteOfferClientTestImpl(uri);

    WireMockServer wireMockServer;

    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(options().port(port));
        wireMockServer.start();
        WireMock.configureFor(port);
    }

    @AfterEach
    void stop() {
        wireMockServer.stop();
    }

    @Test
    public void should_return_offerDTO_list() throws JSONException {
        //given
        WireMock.stubFor(WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())

                        .withHeader("Content-Type", "application/json")
                        .withBody(bodyWithJsons())));
        //then
        then(remoteOfferClient.getOffers()).containsExactlyInAnyOrderElementsOf(Arrays.asList(firstOfferDto(), secondOfferDto()));
    }

    @Test
    public void should_throw_HttpClientException_when_API_return_404_error() {
        //given
        WireMock.stubFor((WireMock.get("/offers")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())
                        .withHeader("Content-Type", "application/json"))));
        //then
        assertThrows(HttpClientException.class, remoteOfferClient::getOffers);
    }

    @Test
    public void should_throw_OfferNotFoundException_when_API_return_empty_list() {
        //given
        WireMock.stubFor((WireMock.get("/offers").willReturn(WireMock.aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", "application/json").withBody(bodyWithEmptyJSONArray()))));
        //then
        assertThrows(OfferNotFoundException.class, remoteOfferClient::getOffers);
    }

    @Test
    public void should_throw_HttpClientException_when_is_wrong_endpoint() {
        //given
        WireMock.stubFor((WireMock.get("/test")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value()))));
        //then
        assertThrows(HttpClientException.class, remoteOfferClient::getOffers);
    }

    private String bodyWithJsons() throws JSONException {

        JSONArray bodyJSONArray = new JSONArray();

        bodyJSONArray.put(
                new JSONObject().put("company", "S2Innovation Sp. z o. o.")
                        .put("title", "Junior Remote Java Developer").
                        put("salary", "4k - 8k PLN")
                        .put("offerUrl", "https://nofluffjobs.com/pl/job/junior-remote-java-developer-s2innovation-krakow-stddogtj"));

        bodyJSONArray.put(
                new JSONObject().put("company", "HARMAN Connected Services")
                        .put("title", "Junior Java SE Developer for Automotive").
                        put("salary", "7k - 10k PLN")
                        .put("offerUrl", "https://nofluffjobs.com/pl/job/junior-java-se-developer-for-automotive-harman-connected-services-lodz-yafxatha"));

        return bodyJSONArray.toString();
    }

    private String bodyWithEmptyJSONArray() {
        return "[]";
    }

    private OfferDto firstOfferDto() {
        return OfferDto.builder()
                .companyName("S2Innovation Sp. z o. o.")
                .jobPosition("Junior Remote Java Developer")
                .salary("4k - 8k PLN")
                .offerUrl("https://nofluffjobs.com/pl/job/junior-remote-java-developer-s2innovation-krakow-stddogtj")
                .build();
    }

    private OfferDto secondOfferDto() {
        return OfferDto.builder()
                .companyName("HARMAN Connected Services")
                .jobPosition("Junior Java SE Developer for Automotive")
                .salary("7k - 10k PLN")
                .offerUrl("https://nofluffjobs.com/pl/job/junior-java-se-developer-for-automotive-harman-connected-services-lodz-yafxatha")
                .build();
    }

}
