package de.htw_berlin.sensor_web_api;

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
import java.util.Scanner;

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
        handlePostRequest(req, resp);
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
     * @throws ServletException if something went wrong
     * @throws IOException      if something went wrong sending the response
     */
    private void handlePostRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            // TODO: - Commit received data to ohdm handler & return content_id in 200 response
            ResponseHelper.handleValidRequest(resp);
        } else {
            ResponseHelper.handleWrongRequest(resp);
        }
    }

    /**
     * Method to valid a given String
     * It just checks if the String is not null nor empty
     *
     * @param param The String to validate
     * @return true or false
     */
    private boolean isValid(String param) {
        return param != null && param.length() > 0;
    }
}
