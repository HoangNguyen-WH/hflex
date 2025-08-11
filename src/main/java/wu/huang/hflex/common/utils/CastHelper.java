package wu.huang.hflex.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CastHelper {
    private static final Logger logger = LoggerFactory.getLogger(CastHelper.class);

    public static String castObject2String(Object obj) {
        try {
            if (obj == null) {
                return "";
            }
            if (obj instanceof String) {
                return (String) obj;
            }
            return obj.toString();
        } catch (Exception e) {
            logger.error("castObject2Long exception", e);
            return "";
        }

    }

    /**
     * Converts an object to a Long object.
     * Handles various number formats and string representations.
     *
     * @param obj Object to convert
     * @return Long value, or null if conversion fails
     * @implNote Supports:
     * - Direct Long casting
     * - Number interface conversion
     * - String parsing
     */
    public static Long castObject2Long(Object obj) {
        try {
            if (obj == null) return null;
            if (obj instanceof Long) return (Long) obj;
            if (obj instanceof Number) return ((Number) obj).longValue();
            return Long.parseLong(obj.toString());
        } catch (Exception e) {
            logger.error("castObject2Long exception", e);
            return null;
        }
    }

    /**
     * Converts an object to an Integer.
     * Handles various number formats and string representations.
     *
     * @param obj Object to convert
     * @return Integer value, or null if conversion fails
     * @implNote Supports:
     * - Direct Integer casting
     * - Number interface conversion
     * - String parsing
     */
    public static Integer castObject2Integer(Object obj) {
        try {
            if (obj == null) return null;
            if (obj instanceof Integer) return (Integer) obj;
            if (obj instanceof Number) return ((Number) obj).intValue();
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            logger.error("castObject2Integer exception", e);
            return null;
        }
    }

    /**
     * Converts an object to a Boolean value with safe fallback handling.
     * <p>
     * This method attempts to convert various input types to Boolean using the following logic:
     * 1. Returns false for null input
     * 2. Direct casting for Boolean objects
     * 3. String parsing for other object types using {@link Boolean#parseBoolean}
     *
     * @param obj The object to convert to Boolean
     * @return Boolean value:
     *         - false for null input
     *         - the actual boolean value for Boolean objects
     *         - parsed boolean value from object's string representation
     *         - false if any exception occurs during conversion
     * @implNote The method uses {@link Boolean#parseBoolean} which returns:
     *          - true only for the string "true" (case-insensitive)
     *          - false for all other non-null strings
     */
    public static Boolean castObject2Boolean(Object obj) {
        try {
            if (obj == null) return false;
            if (obj instanceof Boolean) return (Boolean) obj;

            return Boolean.parseBoolean(obj.toString());
        } catch (Exception e) {
            logger.error("castObject2Boolean exception", e);
            return false;
        }
    }

    /**
     * Converts an object to a BigDecimal.
     * Handles various number formats and string representations.
     *
     * @param obj Object to convert
     * @return BigDecimal value, or null if conversion fails
     * @implNote Supports:
     * - Direct BigDecimal casting
     * - Number interface conversion to double
     * - String parsing
     */
    public static BigDecimal castObjectToBigDecimal(Object obj) {
        if (obj == null) return null;

        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        } else if (obj instanceof Number) {
            return BigDecimal.valueOf(((Number) obj).doubleValue());
        } else if (obj instanceof String) {
            try {
                return new BigDecimal((String) obj);
            } catch (NumberFormatException e) {
                logger.error("castObjectToBigDecimal exception", e);
                return null;
            }
        }

        return null;
    }

    /**
     * Converts an object to a List of a specified type.
     * Handles both arrays and List objects.
     *
     * @param obj   Object to convert (an array or a List)
     * @param clazz Class type for the list
     */
    public static <T> List<T> castObject2List(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        if (obj == null) return result;

        try {
            if (obj.getClass().isArray()) {
                int arrayLength = Array.getLength(obj);
                for (int i = 0; i < arrayLength; i++) {
                    T t = clazz.cast(Array.get(obj, i));
                    result.add(t);
                }
            } else if (obj instanceof List<?>) {
                for (Object element : (List<?>) obj) {
                    result.add(convertToTargetType(element, clazz));
                }
            }
        } catch (Exception e) {
            logger.error("castObject2List exception", e);
        }

        return result;
    }

    /**
     * Converts an arbitrary object to the specified target type.
     * This method supports flexible conversion between compatible types,
     * including String, Number (Integer, Long, Double, BigDecimal, BigInteger), and Boolean.
     *
     * <p>Examples:
     * <ul>
     *   <li>"123" (String) → Long, Integer, BigDecimal, etc.</li>
     *   <li>123 (Integer) → Long, Double, BigDecimal</li>
     *   <li>"true" (String) → Boolean</li>
     * </ul>
     *
     * @param value the input object to be converted
     * @param clazz the target type class
     * @param <T>   the target type
     * @return the converted value as type T
     * @throws IllegalArgumentException if conversion fails or type is unsupported
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertToTargetType(Object value, Class<T> clazz) {
        if (value == null) return null;

        if (clazz.isInstance(value)) {
            return (T) value;
        }

        String str = value.toString();

        try {
            if (clazz == String.class) {
                return (T) str;

            } else if (clazz == Long.class) {
                if (value instanceof Number) return (T) Long.valueOf(((Number) value).longValue());
                return (T) Long.valueOf(str);

            } else if (clazz == Integer.class) {
                if (value instanceof Number) return (T) Integer.valueOf(((Number) value).intValue());
                return (T) Integer.valueOf(str);

            } else if (clazz == Double.class) {
                if (value instanceof Number) return (T) Double.valueOf(((Number) value).doubleValue());
                return (T) Double.valueOf(str);

            } else if (clazz == BigDecimal.class) {
                if (value instanceof Number) return (T) BigDecimal.valueOf(((Number) value).doubleValue());
                return (T) new BigDecimal(str);

            } else if (clazz == BigInteger.class) {
                if (value instanceof Number) return (T) BigInteger.valueOf(((Number) value).longValue());
                return (T) new BigInteger(str);

            } else if (clazz == Boolean.class) {
                if (value instanceof Boolean) return (T) value;
                return (T) Boolean.valueOf(str);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert value '" + value + "' to " + clazz.getSimpleName(), e);
        }

        throw new UnsupportedOperationException("Unsupported conversion to type: " + clazz.getName());
    }
}
