package pl.offers.job;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "offer-url")
public class OfferUrlConfig {

    private String host;
    private String port;

    public String getUrl() {
        return host + ":" + port;
    }

}
