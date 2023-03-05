package pl.offers.job;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "offer-url")
public class OfferUrlConfig {

    private String host;
    private String port;

    public String getUrl() {
        return host + ":" + port;
    }

}
