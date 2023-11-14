package boardcommunication.http.endpoints.files.archivepage;

import boardcommunication.http.HTTPmessage;
import boardcommunication.http.endpoints.EndpointProcessor;

public class ArchivePageJsEndpointProcessor implements EndpointProcessor {

    private static final String ARCHIVE_PAGE_CSS_PATH = "web/archivedBoardPage/archived-board-page.js";

    @Override
    public void processRequest(HTTPmessage request, HTTPmessage response) {
        response.setResponseStatus("200 OK");
        response.setContentFromFile(ARCHIVE_PAGE_CSS_PATH);
    }
}

