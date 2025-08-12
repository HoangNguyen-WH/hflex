package wu.huang.hflex.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import wu.huang.hflex.config.json.JacksonConfiguration;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Utility methods for JSON serialization and deserialization.
 * <p>
 * This helper wraps common conversions between Java objects and JSON strings.
 * All checked JSON processing exceptions are converted to {@link UncheckedIOException}
 * to simplify call sites.
 */
public class JSONHelper {

    /**
     * Serializes the given object to its JSON string representation.
     *
     * @param value the value to serialize; may be {@code null}
     * @return a JSON string representing {@code value}; for {@code null} input this is typically {@code "null"}
     * @throws UncheckedIOException if serialization fails
     */
    public static String obj2JsonStr(Object value) {
        try {
            return JacksonConfiguration.mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Deserializes the given JSON string into an instance of the specified class.
     *
     * @param json the JSON content; must not be {@code null}
     * @param type the target class to deserialize into; must not be {@code null}
     * @param <T>  the result type
     * @return an instance of {@code type} populated from {@code json}
     * @throws IllegalArgumentException if {@code json} is not valid for {@code type}
     * @throws UncheckedIOException     if deserialization fails
     */
    public static <T> T jsonStr2Obj(String json, Class<T> type) {
        try {
            return JacksonConfiguration.mapper.readValue(json, type);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Deserializes the given JSON string into a generic type using a {@link TypeReference}.
     * <p>
     * Use this method for collections or parameterized types, for example:
     * new TypeReference<List<MyDto>>() {}
     *
     * @param json    the JSON content; must not be {@code null}
     * @param typeRef the target type reference describing the generic type; must not be {@code null}
     * @param <T>     the result type
     * @return an instance of {@code T} populated from {@code json}
     * @throws IllegalArgumentException if {@code json} is not valid for {@code typeRef}
     * @throws UncheckedIOException     if deserialization fails
     */
    public static <T> T fromJson(String json, TypeReference<T> typeRef) {
        try {
            return JacksonConfiguration.mapper.readValue(json, typeRef);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
