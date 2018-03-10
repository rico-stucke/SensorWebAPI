package de.htw_berlin.sensor_web_api;


import de.htw_berlin.sensor_web_api.database.DBUploader;
import de.htw_berlin.sensor_web_api.helper.JSONParser;
import de.htw_berlin.sensor_web_api.helper.RequestType;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Controller for handling requests for /sensor/{id}
 *
 * @author Benny Lach
 */
public class ContentUpdateController extends HttpServlet {

    private DBUploader db;

    /**
     * Public initializer
     */
    public ContentUpdateController() throws Exception {
        super();

        try {
            db = new DBUploader();

        } catch (Exception e) {
            System.out.println("Failed to hook up DBUploader instance: " + e.getMessage());
        }
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
     * Method to handle POST requests to update a Content object
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
            String validUntil = jsonMap.get("validUntil");
            String uri = req.getRequestURI();
            String id = getContentId(uri);

            if ( update(id, validUntil)) {
                ResponseHelper.handleValidUpdateRequest(resp);
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
        String validUntil = req.getParameter("validUntil");
        String uri = req.getRequestURI();
        String id = getContentId(uri);

        if ( update(id, validUntil)) {
            ResponseHelper.handleValidUpdateRequest(resp);
        } else {
            ResponseHelper.handleWrongRequest(resp);
        }
    }

    /**
     * Method to update an existing data set on the db
     *
     * @param id The id of the data set
     * @param newValidUntil the new validUntil date to use
     * @return Boolean to indicate success or fail
     */
    private  Boolean update(String id, String newValidUntil) {
        if (!isValidId(id) || !isValid(newValidUntil)) {
            return false;
        }

        // TODO: - Commit received data to ohdm handler & return result
        // There is no implementation for updating a data set in the underlying DBUploader class
        return true;
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

    /**
     * Method to get the contentId form the given uri
     *
     * @param uri the uri
     * @return The parsed contentId, is null if not valid
     */
    private String getContentId(String uri) {

        String[] seperated = uri.split("/");
        // This should never happen, but hey - why not ...
        if (seperated.length == 0 ) {
            return null;
        }
        return seperated[seperated.length-1];
    }

    private Boolean isValidId(String input) {
        return Pattern.matches("\\d.", input);
    }
}