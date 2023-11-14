package boardcommunication.http.endpoints;

import boardcommunication.http.HTTPmessage;

public class BoardPageEndpointProcessor implements EndpointProcessor {

    private static final String BOARD_PAGE_PATH = "web/boardpage/boardpage.html";
    private static final String BOARD_PAGE_CSS_PATH = "web/boardpage/board-page.css";
    private static final String SIDEBAR_CSS_PATH = "web/boardpage/sidebar/sidebar.css";
    private static final String BOARD_CREATOR_JS_PATH = "web/boardpage/BoardCreator.js";
    private static final String BOARD_PAGE_JS_PATH = "web/boardpage/board-page.js";

    private static final String POST_IT_JS_PATH = "web/boardpage/post-it.js";

    @Override
    public void processRequest(HTTPmessage request, HTTPmessage response) {
        String[] uriFields = request.getURI().split("/");

        if (isHtmlRequest(uriFields)) {
            response.setContentFromFile(BOARD_PAGE_PATH);
            response.setResponseStatus("200 OK");
            return;
        }

        if (isSidebarRequest(uriFields)) {
            processSidebarRequest(uriFields[3], response);
            return;
        }

        switch (uriFields[2]) {
            case "board-page.css":
                response.setContentFromFile(BOARD_PAGE_CSS_PATH);
                response.setResponseStatus("200 OK");
                break;
            case "BoardCreator.js":
                response.setContentFromFile(BOARD_CREATOR_JS_PATH);
                response.setResponseStatus("200 OK");
                break;
            case "board-page.js":
                response.setContentFromFile(BOARD_PAGE_JS_PATH);
                response.setResponseStatus("200 OK");
                break;
            case "post-it.js":
                response.setContentFromFile(POST_IT_JS_PATH);
                response.setResponseStatus("200 OK");
                break;
            default:
                response.setContentFromString(
                        "<html><body><h1>404 File not found</h1></body></html>",
                        "text/html");
                response.setResponseStatus("404 Not Found");
                break;
        }

    }

    private boolean isHtmlRequest(String[] uriFields) {
        boolean isHTMLRequest;
        try {
            Integer.parseInt(uriFields[2]);
            isHTMLRequest = true;
        } catch (NumberFormatException e) {
            isHTMLRequest = false;
        }
        return isHTMLRequest;
    }

    private boolean isSidebarRequest(String[] uriFields) {
        return uriFields[2].equals("sidebar");
    }

    private void processSidebarRequest(String uriField, HTTPmessage response) {
        switch (uriField) {
            case "sidebar.css":
                response.setContentFromFile(SIDEBAR_CSS_PATH);
                response.setResponseStatus("200 OK");
                break;
            case "collapse-icon.png":
                response.setContentFromFile("web/boardpage/sidebar/collapse-icon.png");
                response.setResponseStatus("200 OK");
                break;
            case "boards-icon.png":
                response.setContentFromFile("web/boardpage/sidebar/boards-icon.png");
                response.setResponseStatus("200 OK");
                break;
            case "share-icon.png":
                response.setContentFromFile("web/boardpage/sidebar/share-icon.png");
                response.setResponseStatus("200 OK");
                break;
            case "history-icon.png":
                response.setContentFromFile("web/boardpage/sidebar/history-icon.png");
                response.setResponseStatus("200 OK");
                break;
            case "archive-icon.png":
                response.setContentFromFile("web/boardpage/sidebar/archive-icon.png");
                response.setResponseStatus("200 OK");
                break;
            case "choose-image.png":
                response.setContentFromFile("web/boardpage/sidebar/choose-image.png");
                response.setResponseStatus("200 OK");
                break;
            default:
                response.setContentFromString(
                        "<html><body><h1>404 File not found</h1></body></html>",
                        "text/html");
                response.setResponseStatus("404 Not Found");
        }
    }
}
