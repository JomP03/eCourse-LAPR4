package boardcommunication.http.endpoints.files.myboardspage;

import boardcommunication.http.HTTPmessage;
import boardcommunication.http.endpoints.EndpointProcessor;

public class MyBoardsPageJsEndpointProcessor implements EndpointProcessor {


    private static final String MY_BOARDS_PAGE_JS_PATH = "web/myboardspage/my-boards-page.js";

    @Override
    public void processRequest(HTTPmessage request, HTTPmessage response) {
        response.setContentFromFile(MY_BOARDS_PAGE_JS_PATH);
        response.setResponseStatus("200 OK");
    }
}
