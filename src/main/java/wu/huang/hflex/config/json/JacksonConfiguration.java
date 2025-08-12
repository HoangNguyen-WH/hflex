package wu.huang.hflex.config.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperBuilder;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.blackbird.BlackbirdModule;
import wu.huang.hflex.config.param.JacksonParam;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class JacksonConfiguration {
    public static final ObjectMapper mapper = defaultMapper();

    private static final String dateTimePattern = JacksonParam.getDateTimeFormat();
    private static final String datePattern = JacksonParam.getDateFormat();

    private static ObjectMapper defaultMapper() {
        MapperBuilder<?, ?> mapperBuilder = JsonMapper.builder();
        baseConfig(mapperBuilder);
        var objectMapper = mapperBuilder.build();

        //Registry
        objectMapper.registerModule(moduleDateTime());
        objectMapper.registerModule(bigDecimalModule());
        // enhance performance due to call JVM functions instead of reflection
        objectMapper.registerModule(new BlackbirdModule());

        //Other configs
        //Include field with null value
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        return objectMapper;
    }

    private static void baseConfig(MapperBuilder<?, ?> builder) {
        builder
                // Do not throw an error when extra/unknown fields are present
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                // Serialize dates in ISO format instead of epoch number
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                // Accept a single value as if it were an array
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                // Parse floating-point numbers as BigDecimal (avoid precision loss)
                .configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
    }

    private static com.fasterxml.jackson.databind.Module moduleDateTime() {
        JavaTimeModule module = new JavaTimeModule();

        //Format
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

        module.addSerializer(java.time.LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        module.addDeserializer(java.time.LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));

        module.addSerializer(java.time.LocalDate.class, new LocalDateSerializer(dateFormatter));
        module.addDeserializer(java.time.LocalDate.class, new LocalDateDeserializer(dateFormatter));

        return module;
    }

    private static Module bigDecimalModule() {
        SimpleModule num = new SimpleModule();
        num.addSerializer(BigDecimal.class, ToStringSerializer.instance);
        return num;
    }
}
