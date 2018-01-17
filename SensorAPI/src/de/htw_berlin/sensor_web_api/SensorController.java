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
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Controller for handling requests for /sensor
 *
 * @author Benny Lach
 */
public class SensorController extends HttpServlet {
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
        URL fileURL = this.getClass().getResource("sensor.html");
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
     * Method to handle POST requests to create a Sensor object
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
            String name =  jsonMap.get("name");
            String mimeType = jsonMap.get("mimeType");
            String userId = jsonMap.get("sourceUserId");

            if ( isValid(name) && isValid(mimeType) && isValid(userId)) {
                // TODO: - Commit received data to ohdm handler & return content_id in 200 response
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
        String name = req.getParameter("name");
        String mimeType = req.getParameter("mimeType");
        String userId = req.getParameter("sourceUserId");

        if ( isValid(name) && isValid(mimeType) && isValid(userId)) {
            // TODO: - Commit received data to ohdm handler & return content_id in 200 response
            ResponseHelper.handleValidRequest(resp);
        } else {
            ResponseHelper.handleWrongRequest(resp);
        }
    }

    /**
     * Method to valid a given String - it only checks if it's not null nor empty
     *
     * @param param The String to validate
     * @return true or false
     */
    private boolean isValid(String param) {
        return param != null && param.length() > 0;
    }
}
