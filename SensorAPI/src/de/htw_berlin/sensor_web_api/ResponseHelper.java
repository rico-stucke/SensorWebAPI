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
     * Method to handle wrong or unknown requests with default error message
     *
     * @param resp the response object
     * @throws Exception if the response object is null or something went wrong sending the response
     */
    static void handleWrongRequest(HttpServletResponse resp) throws Exception {
        handleWrongRequest(resp, "{\"error\": {\"message\": \"Invalid request\"}}");
    }

    /**
     * Method to handle wrong or unknown requests with custom error message
     *
     * @param resp the response objsct
     * @param msg the message to send
     * @throws Exception if the response object | msg is null or something went wrong sending the response
     */
    static void handleWrongRequest(HttpServletResponse resp, String msg) throws Exception {
        if (resp == null || msg == null) {
            throw new Exception("The HttpServletResponse object or msg String must not be null!");
        }

        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.append(msg);
        writer.close();
    }

    /**
     * Method to handle internal Server Error
     *
     * @param resp the response object
     * @throws Exception if the response object is null or something went wrong sending the response
     */
    static void handleInternalError(HttpServletResponse resp) throws Exception {
        if (resp == null) {
            throw new Exception("The HttpServletResponse object must not be null!");
        }

        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.append("{\"error\": {\"message\": \"Internal Server Error\"}}");
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
