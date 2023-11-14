package boardcommunication.http;

import boardcommunication.http.endpoints.EndpointProcessor;
import boardcommunication.http.endpoints.EndpointProcessorFactory;
import usermanagement.domain.SessionManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HttpRequestHandler implements Runnable {
    private final Socket httpSocket;
    private DataOutputStream output;
    private DataInputStream input;
    static private final String LOGIN_PAGE_PATH = "web/loginPage/loginPage.html";

    public HttpRequestHandler(Socket httpSocket) {
        this.httpSocket = httpSocket;
    }

    @Override
    public void run() {
        try {
            output = new DataOutputStream(httpSocket.getOutputStream());
            input = new DataInputStream(httpSocket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Error on data streams creation");
        }

        try {
            HTTPmessage request = new HTTPmessage(input);
            HTTPmessage response = new HTTPmessage();

            System.out.println(request.getURI() + " " + request.getMethod());
            if (isNewAccess(request)) {
                launchClientWebPage(response);
            } else {
                handleProperRequest(request, response);
            }

            // Send the response
            response.send(output);

        } catch (IOException e) {
            System.out.println("A problem occurred while processing the request" + e.getMessage());
        }
    }

    private boolean isNewAccess(HTTPmessage request) {
        return request.getMethod().equals("GET") &&
                request.getURI().equals("/");
    }

    private void launchClientWebPage(HTTPmessage response) {
        if (response.setContentFromFile(LOGIN_PAGE_PATH)) {
            response.setResponseStatus("200 Ok");
        } else {
            response.setContentFromString(
                    "<html><body><h1>404 File not found</h1></body></html>",
                    "text/html");
            response.setResponseStatus("404 Not Found");
        }
    }

    private void handleProperRequest(HTTPmessage request, HTTPmessage response) {
        // Get the correct endpoint processor
        EndpointProcessorFactory endpointProcessorFactory = new EndpointProcessorFactory(request);
        EndpointProcessor endpointProcessor = endpointProcessorFactory.getEndpointProcessor();

        // Check if the endpoint processor is null
        if (endpointProcessor == null) {
            response.setContentFromString(
                    "No Endpoint found for the specified URI",
                    "text");
            response.setResponseStatus("404 Not Found");
            return;
        }

        // Process the request
        endpointProcessor.processRequest(request, response);
    }
}
