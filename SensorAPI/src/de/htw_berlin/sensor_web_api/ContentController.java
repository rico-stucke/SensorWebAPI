package de.htw_berlin.sensor_web_api;

import de.htw_berlin.sensor_web_api.helper.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Controller for handling requests for /content
 *
 * @author Benny Lach
 */
public class ContentController extends HttpServlet {

    // List containing all valid identifier for a geometry
    private List<String> validGeometries  = new ArrayList<String>() {{
        add("point");
        add("line");
        add("polygon");
    }};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleGetRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            handlePostRequest(req, resp);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Method to handle GET requests
     * @param req the request object
     * @param resp the response object
     * @throws IOException if something went wrong opening the Printwriter
     */
    private void handleGetRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        URL fileURL = this.getClass().getResource("content.html");
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileURL.toURI()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        PrintWriter writer = resp.getWriter();



        while(scanner.hasNextLine()) {
            writer.write(scanner.nextLine());
        }
        writer.flush();
    }

    /**
     * Method to handle POST requests to create a Content object
     *
     * @param req  the request object
     * @param resp the response object
     * @throws Exception if something went wrong
     */
    private void handlePostRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        RequestType type = RequestType.fromString(req.getContentType());

        switch (type) {
            case Body:
                handleBodyRequest(req, resp);
                break;
            case Query:
                handleQueryRequest(req, resp);
                break;
            default:
                ResponseHelper.handleWrongRequest(resp);

        }
    }

    /**
     * Method to handle a post request with Content-Type: application/json
     *
     * @param req the request object
     * @param resp the response object
     * @throws IOException if something went wrong
     */
    private void handleBodyRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String bodyString = req.getReader().lines().collect(Collectors.joining());
        Map<String, String> jsonMap = JSONParser.parse(bodyString);

        if (jsonMap == null) {
            ResponseHelper.handleWrongRequest(resp);
        } else {
            String value = jsonMap.get("value");
            String userId = jsonMap.get("sourceUserId");
            String sensorId = jsonMap.get("sensorId");
            String validSince = jsonMap.get("validSince");
            String validUntil = jsonMap.get("validUntil");
            String geometryType = jsonMap.get("geometryType");
            String geometryId = jsonMap.get("geometryId");

            if ( isValid(value) && isValid(sensorId) && isValid(userId) &&
                    isValid(validSince) && isValid(validUntil) && isValid(geometryType) &&
                    isValid(geometryId) && validGeometries.contains(geometryType.toLowerCase())) {
                // TODO: - Commit received data to ohdm handler & return content_id in 201 response
                ResponseHelper.handleValidCreateRequest(resp, "42");
            } else {
                ResponseHelper.handleWrongRequest(resp);
            }
        }
    }

    /**
     * Method to handle POST requests with Content-Type: application/x-www-form-urlencoded
     *
     * @param req the request object
     * @param resp the response object
     * @throws IOException if something went wrong
     */
    private void handleQueryRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String value = req.getParameter("value");
        String userId = req.getParameter("sourceUserId");
        String sensorId = req.getParameter("sensorId");
        String validSince = req.getParameter("validSince");
        String validUntil = req.getParameter("validUntil");
        String geometryType = req.getParameter("geometryType");
        String geometryId = req.getParameter("geometryId");

        if ( isValid(value) && isValid(sensorId) && isValid(userId) &&
                isValid(validSince) && isValid(validUntil) && isValid(geometryType) &&
                isValid(geometryId) && validGeometries.contains(geometryType.toLowerCase())) {
            // TODO: - Commit received data to ohdm handler & return content_id in 201 response
            ResponseHelper.handleValidCreateRequest(resp, "42");
        } else {
            ResponseHelper.handleWrongRequest(resp);
        }
    }

    /**
     * Method to validate a given String
     * It just checks if the String is not null nor empty
     *
     * @param param The String to validate
     * @return true or false
     */
    private boolean isValid(String param) {
        return param != null && param.length() > 0;
    }
}
