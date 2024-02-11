package pl.offers.job.infrastructure.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        final HttpStatusCode statusCode = httpResponse.getStatusCode();
        if (statusCode.is5xxServerError()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while using http client");
        } else if (statusCode.is4xxClientError()) {
            if (statusCode.isSameCodeAs(HttpStatusCode.valueOf(404))) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            } else if (statusCode.isSameCodeAs(HttpStatusCode.valueOf(401))) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }
    }

}
