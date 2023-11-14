package boardcommunication.http.endpoints.files.myboardspage;

import boardcommunication.http.HTTPmessage;
import boardcommunication.http.endpoints.EndpointProcessor;

public class MyBoardsPageCssEndpointProcessor implements EndpointProcessor {

    private static final String MY_BOARDS_PAGE_CSS_PATH = "web/myboardspage/my-boards-page.css";

    @Override
    public void processRequest(HTTPmessage request, HTTPmessage response) {
        response.setContentFromFile(MY_BOARDS_PAGE_CSS_PATH);
        response.setResponseStatus("200 OK");
    }
}
