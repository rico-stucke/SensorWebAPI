package de.htw_berlin.sensor_web_api.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * JSON Parser
 *
 * @author Benny Lach
 */
public class JSONParser {

    /**
     * Method to parse a given json string to Map<String,String> Object if possible
     * @param input the input String
     * @return Mapped Object - will be null if something went wrong
     */
    public static Map<String,String> parse(String input) {
        if (input == null) { return null; }

        return JSONParser.convert(input);
    }

    /**
     * Method to remove unnecessary chars from the given json input string
     *
     * @param input json string
     */
    private static String reduce(String input) {
        input = input
                .replace("{", "")
                .replaceAll("}", "");

        return input;
    }

    /**
     * Method to convert a given String into a corresponding Map Representation
     *
     * @param input json String to convert
     * @return Map representation - will be null if something went wrong mapping input
     */
    private static Map<String, String> convert(String input) {
        input = JSONParser.reduce(input);

        HashMap<String, String> map = new HashMap<>();

        String[] pairs = input.split("\",\"");

        for (String pair : pairs) {
            String[] kv = pair.split("\":\"");
            if (kv.length != 2) {
                return null;
            }
            map.put(kv[0].replaceAll("\"", ""), kv[1].replaceAll("\"", ""));
        }
        return map;
    }
}
