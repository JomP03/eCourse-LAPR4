package exammanagement.application.deserializers;

import com.google.gson.*;
import com.google.gson.reflect.*;
import coursemanagement.domain.*;
import ecourseusermanagement.domain.*;
import exammanagement.domain.*;
import java.lang.reflect.*;
import java.util.*;

public class FormativeExamDeserializer implements JsonDeserializer<Exam> {

    @Override
    public Exam deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Extract the necessary values from the JSON object
        List<ExamSection> sections = context.deserialize(jsonObject.get("sections"), new TypeToken<List<ExamSection>>(){}.getType());
        ExamHeader header = context.deserialize(jsonObject.get("header"), ExamHeader.class);
        String title = jsonObject.get("title").getAsString();
        ECourseUser creator = context.deserialize(jsonObject.get("creator"), ECourseUser.class);
        Course course = context.deserialize(jsonObject.get("course"), Course.class);

        // Create and return the Exam object using the desired constructor
        return new FormativeExam(sections, header, title, creator, course);
    }
}

