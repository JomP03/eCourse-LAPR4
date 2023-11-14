package boardcommunication.http.endpoints.files.myboardspage;

import boardcommunication.http.HTTPmessage;
import boardcommunication.http.endpoints.EndpointProcessor;

public class MyBoardsPageHTMLEndpointProcessor implements EndpointProcessor {

    private static final String MY_BOARDS_PAGE_PATH = "web/myboardspage/myboardspage.html";
    @Override
    public void processRequest(HTTPmessage request, HTTPmessage response) {
        response.setContentFromFile(MY_BOARDS_PAGE_PATH);
        response.setResponseStatus("200 OK");
    }
}
