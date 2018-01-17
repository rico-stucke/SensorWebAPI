package de.htw_berlin.sensor_web_api.helper;

/**
 * Enum representing the Content-Type for a given request
 *
 * @author Benny Lach
 */
public enum RequestType {
    Body, Query, Undefined;

    private final static String BODY_ID = "application/json";
    private final static String QUERY_ID = "application/x-www-form-urlencoded";

    public static RequestType fromString(String source) {
        if (source == null) {
            return RequestType.Undefined;
        }
        if (source.contains(RequestType.BODY_ID)) {
            return RequestType.Body;
        }
        if (source.contains(RequestType.QUERY_ID)) {
            return RequestType.Query;
        }
        return RequestType.Undefined;
    }
}
