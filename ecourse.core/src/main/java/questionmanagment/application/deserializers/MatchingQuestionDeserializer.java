package questionmanagment.application.deserializers;

import com.google.gson.*;
import questionmanagment.domain.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MatchingQuestionDeserializer implements JsonDeserializer<MatchingQuestion> {
    @Override
    public MatchingQuestion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Extract the properties from the JSON object
        String question = jsonObject.get("question").getAsString();
        Float penalty = jsonObject.get("penalty").getAsFloat();
        Float quotation = jsonObject.get("quotation").getAsFloat();
        List<String> leftOptions = context.deserialize(jsonObject.get("leftOptions"), ArrayList.class);
        List<String> rightOptions = context.deserialize(jsonObject.get("rightOptions"), ArrayList.class);

        // Create a new instance of MatchingQuestion using the extracted properties
        return new MatchingQuestion(question, penalty, quotation, leftOptions, rightOptions);
    }
}

