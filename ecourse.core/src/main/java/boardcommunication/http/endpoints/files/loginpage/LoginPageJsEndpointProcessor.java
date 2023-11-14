package boardcommunication.http.endpoints.files.loginpage;

import boardcommunication.http.HTTPmessage;
import boardcommunication.http.endpoints.EndpointProcessor;

public class LoginPageJsEndpointProcessor implements EndpointProcessor {

    private static final String LOGIN_PAGE_JS_PATH = "web/loginPage/loginPage.js";

    @Override
    public void processRequest(HTTPmessage request, HTTPmessage response) {
        response.setContentFromFile(LOGIN_PAGE_JS_PATH);
        response.setResponseStatus("200 OK");
    }
}
