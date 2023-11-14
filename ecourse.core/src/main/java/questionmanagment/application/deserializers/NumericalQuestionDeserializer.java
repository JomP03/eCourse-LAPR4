package questionmanagment.application.deserializers;

import questionmanagment.domain.*;
import com.google.gson.*;
import java.lang.reflect.Type;

public class NumericalQuestionDeserializer implements JsonDeserializer<NumericalQuestion> {
    @Override
    public NumericalQuestion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Extract the properties from the JSON object
        String question = jsonObject.get("question").getAsString();
        String answer = jsonObject.get("numericalAnswer").getAsString();
        Float penalty = jsonObject.get("penalty").getAsFloat();
        Float quotation = jsonObject.get("quotation").getAsFloat();

        // Create a new instance of NumericalQuestion using the extracted properties
        return new NumericalQuestion(question, answer, penalty, quotation);
    }
}
