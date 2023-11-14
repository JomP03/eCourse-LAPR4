package questionmanagment.application.deserializers;

import com.google.gson.*;
import questionmanagment.domain.*;

import java.lang.reflect.Type;

public class BooleanQuestionDeserializer implements JsonDeserializer<BooleanQuestion> {
    @Override
    public BooleanQuestion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Extract the properties from the JSON object
        String question = jsonObject.get("question").getAsString();
        Boolean correctAnswer = jsonObject.get("booleanAnswer").getAsBoolean();
        Float penalty = jsonObject.get("penalty").getAsFloat();
        Float quotation = jsonObject.get("quotation").getAsFloat();

        // Return the deserialized BooleanQuestion object
        return new BooleanQuestion(question, correctAnswer, penalty, quotation);
    }
}

