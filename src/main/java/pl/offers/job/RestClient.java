package pl.offers.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@AllArgsConstructor
@Data
public class RestClient {

    private String url;
    private RestTemplate restTemplate;

    public <T> ResponseEntity<List<T>> callGetMethod(ParameterizedTypeReference<List<T>> responseType) {
        return restTemplate.exchange(url, HttpMethod.GET, null, responseType);
    }

}
