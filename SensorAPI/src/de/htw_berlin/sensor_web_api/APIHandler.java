package de.htw_berlin.sensor_web_api;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

public class APIHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: Implement get request
        // i.e. : Show form to make the post request
        handleWrongRequest(response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handlePostRequest(req, resp);
    }

    /**
     * Method to handle POST requests
     * @param req the request object
     * @param resp the response object
     * @throws ServletException if something went wrong
     * @throws IOException if something went wrong sending the response
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
