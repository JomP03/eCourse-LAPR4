package boardcommunication.http.endpoints;

import boardcommunication.http.HTTPmessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyBoardsBoardOptionImageEndpointProcessor implements EndpointProcessor {

    private static final String IMAGES_PATH = "ecourse.core/src/main/java/boardmanagement/images/";

    private static final List<String> IMAGES_AVAILABLE =
            new ArrayList<>(
                    Arrays.asList("option1", "option2", "option3",
                            "option4", "option5", "option6",
                            "option7", "option8"));

    @Override
    public void processRequest(HTTPmessage request, HTTPmessage response) {
        // Super random image selection
        int randomIndex = (int) (Math.random() * IMAGES_AVAILABLE.size());
        String imageName = IMAGES_AVAILABLE.get(randomIndex);

        // Set the full path to the image
        String imagePath = IMAGES_PATH + imageName + ".png";

        // Set the response
        response.setResponseStatus("200 OK");
        response.setContentFromFile(imagePath);
    }
}
