package boardcommunication.http.endpoints;

import boardcommunication.http.HTTPmessage;
import boardcommunication.http.helpers.LocalDateAdapter;
import boardcommunication.http.helpers.LocalDateTimeAdapter;
import boardcommunication.http.helpers.RequestUserProvider;
import boardlogmanagement.application.BoardLogProvider;
import boardmanagement.domain.Board;
import boardlogmanagement.domain.BoardLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ecourseusermanagement.domain.ECourseUser;
import persistence.PersistenceContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class HistoryLogEndpointProcessor implements EndpointProcessor{


    private HTTPmessage request;

    private HTTPmessage response;

    private final BoardLogProvider logProvider = new BoardLogProvider(PersistenceContext.repositories().boardLogs());

    private final RequestUserProvider requestUserProvider = new RequestUserProvider();

    @Override
    public void processRequest(HTTPmessage request, HTTPmessage response) {
        this.request = request;
        this.response = response;

        redirectAccordingToMethod();
    }

    private void redirectAccordingToMethod() {
        switch (request.getMethod()) {
            case "GET":
                getHistoryLog();
                break;
            default:
                response.setResponseStatus("404 Not Found");
                response.setContentFromString("Endpoint not found", "text");
                break;
        }
    }

    private void getHistoryLog() {

        Optional<ECourseUser> user = requestUserProvider.getUserFromRequest(request);

        if (user.isPresent()) {
            String[] uriFields = request.getURI().split("/");

            // Get the logs where the history was requested
            List<BoardLog> logs = (List<BoardLog>) logProvider.retrieveBoardLogsByBoardID(Long.parseLong(uriFields[2]));

            if(logs != null && !logs.isEmpty()){

                // Convert the post-it to JSON
                Gson toGson = new GsonBuilder()
                        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                        .setPrettyPrinting()
                        .create();

                String jsonString = toGson.toJson(logs);

                response.setResponseStatus("200 OK");
                response.setContentFromString(jsonString, "application/json");

            } else{
                response.setResponseStatus("404 Not Found");
                response.setContentFromString("Logs not found", "text");
            }

        } else {
            response.setResponseStatus("401 Unauthorized");
            response.setContentFromString("No user logged in", "text");
        }

    }
}
