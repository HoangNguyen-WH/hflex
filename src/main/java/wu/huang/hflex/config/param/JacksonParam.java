package wu.huang.hflex.config.param;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jackson")
public class JacksonParam {
    private static String DATE_FORMAT;
    private static String DATE_TIME_FORMAT;

    private String dateFormat;
    private String dateTimeFormat;

    @PostConstruct
    public void init() {
        DATE_FORMAT = this.dateFormat;
        DATE_TIME_FORMAT = this.dateTimeFormat;
    }

    public static String getDateFormat() {
        return DATE_FORMAT;
    }

    public static String getDateTimeFormat() {
        return DATE_TIME_FORMAT;
    }
}
