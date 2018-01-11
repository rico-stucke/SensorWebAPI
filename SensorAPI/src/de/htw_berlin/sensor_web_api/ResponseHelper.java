package de.htw_berlin.sensor_web_api;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseHelper {

    /**
     * Method to handle wrong or unknown requests
     *
     * @param resp the respsonse object
     * @throws IOException if something went wrong sending the response
     */
    static void handleWrongRequest(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.append("{\"error\": {\"message\": \"Invalid request\"}}");
        writer.close();
    }

    /**
     * Method to handle valid requests
     * @param resp the response object
     * @throws IOException If something went wrong sending the response
     */
    static void handleValidRequest(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.append("{\"success\"}");
        writer.close();
    }
}
