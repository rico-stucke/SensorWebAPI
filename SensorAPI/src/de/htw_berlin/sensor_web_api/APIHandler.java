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
import java.util.Scanner;

/**
 * Class to handle request for sending Sensor data
 *
 * @author Benny Lach
 */
public class APIHandler extends HttpServlet {
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
        String objectId = req.getParameter("object_id");

        if (name == null || value == null || mimeType == null || userId == null ||
                validSince == null || validUntil == null || objectId == null) {
            handleWrongRequest(resp);
        }
        super.doPost(req, resp);
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
}
