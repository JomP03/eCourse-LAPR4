package boardcommunication.http.endpoints;

import auth.SessionToken;
import boardcommunication.http.dto.LoginRequest;
import boardcommunication.http.HTTPmessage;
import com.google.gson.Gson;
import ecourseusermanagement.domain.ECourseUser;
import usermanagement.domain.SessionManager;

import java.util.Optional;

public class SessionsEndpointProcessor implements EndpointProcessor {

    private final SessionManager sessionManager = SessionManager.getInstance();
    private HTTPmessage request;
    private HTTPmessage response;

    @Override
    public void processRequest(HTTPmessage request, HTTPmessage response) {
        this.request = request;
        this.response = response;

        redirectAccordingToMethod();
    }

    private void redirectAccordingToMethod() {
        switch (request.getMethod()) {
            case "GET":
                if (request.getURI().contains("id"))
                    getUserId();
                else
                    getUsername();
                break;
            case "POST":
                postLoginAttempt();
                break;
            default:
                response.setResponseStatus("404 Not Found");
                response.setContentFromString("Endpoint method not found (" + request.getURI()+ ")", "text");
                break;
        }
    }

    private void getUserId() {
        SessionToken sessionToken = new SessionToken(request.getAuthorization());
        Optional<ECourseUser> user = sessionManager.getLoggedInUser(sessionToken);
        if (user.isPresent()) {
            response.setResponseStatus("200 OK");
            response.setContentFromString(user.get().identity().toString(), "text");
        } else {
            response.setResponseStatus("404 Not Found");
            response.setContentFromString("User not found", "text");
        }
    }

    private void postLoginAttempt() {
        // Get the request content
        String requestContent = request.getContentAsString();

        // Transform the json into a java object
        Gson gson = new Gson();
        LoginRequest loginRequest = gson.fromJson(requestContent, LoginRequest.class);

        // Perform the login
        Optional<SessionToken> sessionToken = sessionManager.login(loginRequest.username, loginRequest.password);

        // Check if the login was successful
        if (sessionToken.isPresent()) {
            response.setResponseStatus("200 OK");
            response.setContentFromString(sessionToken.get().toString(), "text");
        } else {
            response.setResponseStatus("401 Unauthorized");
            response.setContentFromString("Invalid username or password", "text");
        }
    }

    private void getUsername() {
        SessionToken sessionToken = new SessionToken(request.getAuthorization());
        Optional<ECourseUser> user = sessionManager.getLoggedInUser(sessionToken);
        if (user.isPresent()) {
            response.setResponseStatus("200 OK");
            response.setContentFromString(user.get().name(), "text");
        } else {
            response.setResponseStatus("404 Not Found");
            response.setContentFromString("User not found", "text");
        }
    }
}
