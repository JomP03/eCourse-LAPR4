package boardcommunication.http.endpoints.files;

import boardcommunication.http.HTTPmessage;
import boardcommunication.http.endpoints.EndpointProcessor;

public class CommonsEndpointProcessor implements EndpointProcessor {

    private static final String BASE_GRADIENT_CSS_PATH = "web/commons/base-gradient.css";
    private static final String BASE_CLEAN_CSS_ENDPOINT = "web/commons/base-clean.css";
    private static final String HEADER_CSS_ENDPOINT = "web/commons/header.css";
    private static final String HEADER_JS_ENDPOINT = "web/commons/commons.js";

    @Override
    public void processRequest(HTTPmessage request, HTTPmessage response) {
        String[] uriFields = request.getURI().split("/");
        String endpoint = uriFields[2];

        if (isNotificationSoundRequest(uriFields[2])) {
            sendNotificationSound(uriFields[3], response);
            return;
        }

        switch (endpoint) {
            case "base-clean.css":
                response.setContentFromFile(BASE_CLEAN_CSS_ENDPOINT);
                response.setResponseStatus("200 OK");
                break;
            case "base-gradient.css":
                response.setContentFromFile(BASE_GRADIENT_CSS_PATH);
                response.setResponseStatus("200 OK");
                break;
            case "header.css":
                response.setContentFromFile(HEADER_CSS_ENDPOINT);
                response.setResponseStatus("200 OK");
                break;
            case "commons.js":
                response.setContentFromFile(HEADER_JS_ENDPOINT);
                response.setResponseStatus("200 OK");
                break;
            default:
                response.setResponseStatus("404 Not Found");
                break;
        }
    }

    private void sendNotificationSound(String sound, HTTPmessage response) {
        System.out.println("Notification sound request " + sound);
        switch (sound) {

            case "success_sound.mp3":
                response.setContentFromFile("web/commons/sounds/success_sound.mp3");
                response.setResponseStatus("200 OK");
                break;

            case "fail_sound.mp3":
                response.setContentFromFile("web/commons/sounds/fail_sound.mp3");
                response.setResponseStatus("200 OK");
                break;

            case "info_sound.mp3":
                response.setContentFromFile("web/commons/sounds/info_sound.mp3");
                response.setResponseStatus("200 OK");
                break;

            default:
                System.out.println("Notification sound request" + sound);
                break;
        }
    }


    private boolean isNotificationSoundRequest(String endpoint) {
        return endpoint.equals("sound");
    }
}
