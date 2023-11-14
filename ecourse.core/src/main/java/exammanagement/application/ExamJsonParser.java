package exammanagement.application;

import boardcommunication.http.helpers.LocalDateAdapter;
import boardcommunication.http.helpers.LocalDateTimeAdapter;
import com.google.gson.*;
import exammanagement.domain.*;

import java.io.*;
import java.time.*;

public class ExamJsonParser implements ExamFileParser {

    @Override
    public File parse(Exam exam, String filepath) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(exam);

        // Write the JSON string to a file
        try (FileWriter fileWriter = new FileWriter(filepath)) {
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new File(filepath);
    }
}
