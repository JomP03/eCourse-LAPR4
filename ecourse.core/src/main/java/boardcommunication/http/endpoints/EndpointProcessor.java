package boardcommunication.http.endpoints;

import boardcommunication.http.HTTPmessage;

/**
 * Interface for endpoint processors.
 * An endpoint processor is a class that processes a request and returns a response.
 */
public interface EndpointProcessor {

    /**
     * Process the request and return a response.
     *
     * @param request  the request
     * @param response the response
     */
    void processRequest(HTTPmessage request, HTTPmessage response);
}
