package boardcommunication.http.endpoints.files.loginpage;

import boardcommunication.http.HTTPmessage;
import boardcommunication.http.endpoints.EndpointProcessor;

public class LoginPageCssEndpointProcessor implements EndpointProcessor {

    private static final String LOGIN_PAGE_CSS_PATH = "web/loginPage/loginPage.css";

    @Override
    public void processRequest(HTTPmessage request, HTTPmessage response) {
        response.setContentFromFile(LOGIN_PAGE_CSS_PATH);
        response.setResponseStatus("200 OK");
    }
}
