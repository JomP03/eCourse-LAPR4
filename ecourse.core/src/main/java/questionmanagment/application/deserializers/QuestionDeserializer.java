package questionmanagment.application.deserializers;

import com.google.gson.*;
import questionmanagment.domain.*;

import java.lang.reflect.*;

public class QuestionDeserializer implements JsonDeserializer<Question> {
    @Override
    public Question deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Extract the properties from the JSON object
        String questionType = jsonObject.get("questionType").getAsString();

        switch (questionType) {
            case "MULTIPLE_CHOICE":
                return new MultipleChoiceQuestionDeserializer().deserialize(json, typeOfT, context);
            case "SHORT_ANSWER":
                return new ShortAnswerQuestionDeserializer().deserialize(json, typeOfT, context);
            case "BOOLEAN":
                return new BooleanQuestionDeserializer().deserialize(json, typeOfT, context);
            case "MATCHING":
                return new MatchingQuestionDeserializer().deserialize(json, typeOfT, context);
            case "MISSING_WORD":
                return new MissingWordQuestionDeserializer().deserialize(json, typeOfT, context);
            case "NUMERICAL":
                return new NumericalQuestionDeserializer().deserialize(json, typeOfT, context);
            default:
                throw new JsonParseException("Invalid question type");
        }
    }
}

