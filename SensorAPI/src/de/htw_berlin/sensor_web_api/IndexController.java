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

public class IndexController  extends HttpServlet {

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

    /**
     * Method to handle GET requests
     * @param req the request object
     * @param resp the response object
     * @throws IOException if something went wrong opening the Printwriter
     */
    private void handleGetRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        URL fileURL = this.getClass().getResource("index.html");
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileURL.toURI()));
        } catch (URISyntaxException e) {
            e.getMessage();
        }

        PrintWriter writer = resp.getWriter();

        while(scanner.hasNextLine()) {
            writer.write(scanner.nextLine());
        }
        writer.flush();
    }
}
