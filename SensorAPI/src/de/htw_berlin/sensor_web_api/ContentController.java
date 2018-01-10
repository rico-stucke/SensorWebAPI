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
        URL fileURL = this.getClass().getResource("userdata.html");
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
     * Method to handle POST requests
     *
     * @param req  the request object
     * @param resp the response object
     * @throws ServletException if something went wrong
     * @throws IOException      if something went wrong sending the response
     */
    private void handlePostRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String value = req.getParameter("value");
        String mimeType = req.getParameter("mime_type");
        String userId = req.getParameter("source_user_id");
        String validSince = req.getParameter("valid_since");
        String validUntil = req.getParameter("valid_until");
        String geometryType = req.getParameter("geometry_type");
        String geometryId = req.getParameter("geometry_id");

        if ( isValid(name) && isValid(value) && isValid(mimeType) && isValid(userId) &&
                isValid(validSince) && isValid(validUntil) && isValid(geometryType) &&
                isValid(geometryId) && validGeometries.contains(geometryType.toLowerCase())) {
            // TODO: - Commit received data to ohdm handler & return content_id in 200 response
            handleValidRequest(resp);
        } else {
            handleWrongRequest(resp);
        }
    }

    /**
     * Method to handle wrong or unknown requests
     *
     * @param resp the respsonse object
     * @throws IOException if something went wrong sending the response
     */
    private void handleWrongRequest(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.append("{ \"error\": { \"message\": \"Invalid request\"}}");
        writer.close();
    }

    /**
     * Method to handle valid requests
     * @param resp the response object
     * @throws IOException If something went wrong sending the response
     */
    private void handleValidRequest(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter writer = resp.getWriter();
        writer.append("{ \"success\"}");
        writer.close();
    }

    private boolean isValid(String param) {
        return param != null && param.length() > 0;
    }
}
