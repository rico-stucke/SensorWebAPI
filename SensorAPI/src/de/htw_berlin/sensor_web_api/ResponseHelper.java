package de.htw_berlin.sensor_web_api;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Helper for sending Responses
 *
 * @author Benny Lach
 */
public class ResponseHelper {

    /**
     * Method to handle wrong or unknown requests
     *
     * @param resp the response object
     * @throws Exception if the response object is null or something went wrong sending the response
     */
    static void handleWrongRequest(HttpServletResponse resp) throws Exception {
        if (resp == null) {
            throw new Exception("The HttpServletResponse object must not be null!");
        }

        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.append("{\"error\": {\"message\": \"Invalid request\"}}");
        writer.close();
    }

    /**
     * Method to handle a valid create request
     *
     * @param resp the response object
     * @param id the id of the created object
     * @throws Exception if one param is null or something went wrong sending the response
     */
    static void handleValidCreateRequest(HttpServletResponse resp, String id) throws Exception {
        if (id == null || resp == null) {
            throw new Exception("One or more parameters are not valid");
        }
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.append("{\"id\": " + id + "}");
        writer.close();
    }

    /**
     * Method to handle valid requests
     *
     * @param resp the response object
     * @throws Exception if the response object is null or something went wrong sending the response
     */
    static void handleValidUpdateRequest(HttpServletResponse resp) throws Exception {
        if (resp == null) {
            throw new Exception("The HttpServletResponse object must not be null!");
        }

        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
