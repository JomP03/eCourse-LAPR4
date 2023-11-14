package questionmanagment.application.deserializers;

import com.google.gson.*;
import questionmanagment.domain.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceQuestionDeserializer implements JsonDeserializer<MultipleChoiceQuestion> {
    @Override
    public MultipleChoiceQuestion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Extract the properties from the JSON object
        String question = jsonObject.get("question").getAsString();
        List<String> options = new ArrayList<>();
        JsonArray optionsArray = jsonObject.get("options").getAsJsonArray();
        for (JsonElement optionElement : optionsArray) {
            options.add(optionElement.getAsString());
        }
        String correctOption = jsonObject.get("correctOption").getAsString();
        Float penalty = jsonObject.get("penalty").getAsFloat();
        Float quotation = jsonObject.get("quotation").getAsFloat();

        // Create a new instance of MultipleChoiceQuestion using the extracted properties
        return new MultipleChoiceQuestion(question, options, correctOption, penalty, quotation);
    }
}

