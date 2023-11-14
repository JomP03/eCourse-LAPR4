package boardcommunication.http.endpoints;

import boardcommunication.http.HTTPmessage;
import boardcommunication.http.endpoints.files.CommonsEndpointProcessor;
import boardcommunication.http.endpoints.files.archivepage.ArchivePageCssEndpointProcessor;
import boardcommunication.http.endpoints.files.archivepage.ArchivePageEndpointProcessor;
import boardcommunication.http.endpoints.files.archivepage.ArchivePageJsEndpointProcessor;
import boardcommunication.http.endpoints.files.loginpage.LoginPageCssEndpointProcessor;
import boardcommunication.http.endpoints.files.loginpage.LoginPageJsEndpointProcessor;
import boardcommunication.http.endpoints.files.myboardspage.MyBoardsPageCssEndpointProcessor;
import boardcommunication.http.endpoints.files.myboardspage.MyBoardsPageHTMLEndpointProcessor;
import boardcommunication.http.endpoints.files.myboardspage.MyBoardsPageJsEndpointProcessor;

public class EndpointProcessorFactory {

    private static final String SESSIONS_ENDPOINT = "sessions";
    private static final String BOARDS_ENDPOINT = "boards";
    private static final String BOARD_PERMISSIONS_ENDPOINT = "boardpermissions";
    private static final String ARCHIVE_BOARD_ENDPOINT = "archiveboard";
    private static final String POST_ITS_ENDPOINT = "postit";
    private static final String MY_BOARDS_PAGE_ENDPOINT = "myboards";
    private static final String ARCHIVE_PAGE_ENDPOINT = "archived";
    private static final String HISTORY_LOG_ENDPOINT = "log";
    private static final String SHARE_BOARD_ENDPOINT = "shareboard";
    private static final String MY_BOARDS_PAGE_CSS_ENDPOINT = "my-boards-page.css";
    private static final String MY_BOARDS_PAGE_JS_ENDPOINT = "my-boards-page.js";
    private static final String MY_BOARDS_BOARD_OPTION_IMAGE_ENDPOINT = "boardoptionimage";
    private static final String LOGIN_PAGE_CSS_ENDPOINT = "loginPage.css";
    private static final String LOGIN_PAGE_JS_ENDPOINT = "loginPage.js";
    private static final String ARCHIVE_PAGE_CSS_ENDPOINT = "archived-board-page.css";
    private static final String ARCHIVE_PAGE_JS_ENDPOINT = "archived-board-page.js";
    private static final String COMMONS_ENDPOINTS = "commons";

    private final HTTPmessage request;

    public EndpointProcessorFactory(HTTPmessage request) {
        this.request = request;
    }

    public EndpointProcessor getEndpointProcessor() {
        String[] uriFields = request.getURI().split("/");
        String endpoint = uriFields[1];

        if (endpoint.startsWith("_")) {
            return pageLoadEndpoints(uriFields);
        } else {
            switch (endpoint) {
                case SESSIONS_ENDPOINT:
                    return new SessionsEndpointProcessor();
                case BOARDS_ENDPOINT:
                    return new BoardsEndpointProcessor();
                case POST_ITS_ENDPOINT:
                    return new PostItsEndpointProcessor();
                case BOARD_PERMISSIONS_ENDPOINT:
                    return new BoardPermissionsProcessor();
                case ARCHIVE_BOARD_ENDPOINT:
                    return new ArchiveBoardEndpointProcessor();
                case HISTORY_LOG_ENDPOINT:
                    return new HistoryLogEndpointProcessor();
                case SHARE_BOARD_ENDPOINT:
                    return new ShareBoardEndpointProcessor();


                // ONLY FOR UI
                case LOGIN_PAGE_CSS_ENDPOINT:
                    return new LoginPageCssEndpointProcessor();
                case LOGIN_PAGE_JS_ENDPOINT:
                    return new LoginPageJsEndpointProcessor();
                case MY_BOARDS_PAGE_CSS_ENDPOINT:
                    return new MyBoardsPageCssEndpointProcessor();
                case MY_BOARDS_PAGE_JS_ENDPOINT:
                    return new MyBoardsPageJsEndpointProcessor();
                case ARCHIVE_PAGE_CSS_ENDPOINT:
                    return new ArchivePageCssEndpointProcessor();
                case ARCHIVE_PAGE_JS_ENDPOINT:
                    return new ArchivePageJsEndpointProcessor();
                case MY_BOARDS_BOARD_OPTION_IMAGE_ENDPOINT:
                    return new MyBoardsBoardOptionImageEndpointProcessor();
                case COMMONS_ENDPOINTS:
                    return new CommonsEndpointProcessor();
                // Adicionar nos refs do html os styles sempre com um endpoint associado antes (ver commons)
                default:
                    return null;
            }
        }
    }

    private EndpointProcessor pageLoadEndpoints(String[] uriFields) {
        // Remove the _ from the endpoint
        uriFields[1] = uriFields[1].substring(1);

        switch (uriFields[1]) {
            case MY_BOARDS_PAGE_ENDPOINT:
                return new MyBoardsPageHTMLEndpointProcessor();
            case BOARDS_ENDPOINT:
                return new BoardPageEndpointProcessor();
            case ARCHIVE_PAGE_ENDPOINT:
                return new ArchivePageEndpointProcessor();
            default:
                return null;
        }
    }
}
