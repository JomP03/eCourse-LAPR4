package questionmanagment.application.deserializers;

import com.google.gson.*;
import questionmanagment.domain.*;

import java.lang.reflect.Type;

public class ShortAnswerQuestionDeserializer implements JsonDeserializer<ShortAnswerQuestion> {
    @Override
    public ShortAnswerQuestion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Extract the properties from the JSON object
        String question = jsonObject.get("question").getAsString();
        String shortAnswer = jsonObject.get("shortAnswer").getAsString();
        Float penalty = jsonObject.get("penalty").getAsFloat();
        Float quotation = jsonObject.get("quotation").getAsFloat();

        // Create a new instance of ShortAnswerQuestion using the extracted properties
        return new ShortAnswerQuestion(question, shortAnswer, penalty, quotation);
    }
}

