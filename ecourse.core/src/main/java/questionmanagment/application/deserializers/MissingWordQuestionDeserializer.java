package questionmanagment.application.deserializers;

import com.google.gson.*;
import questionmanagment.domain.MissingWordOption;
import questionmanagment.domain.MissingWordQuestion;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MissingWordQuestionDeserializer implements JsonDeserializer<MissingWordQuestion> {

    @Override
    public MissingWordQuestion deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String question = jsonObject.get("question").getAsString();
        float penalty = jsonObject.get("penalty").getAsFloat();
        float quotation = jsonObject.get("quotation").getAsFloat();
        JsonArray optionsArray = jsonObject.getAsJsonArray("missingOptions");

        List<MissingWordOption> options = new ArrayList<>();
        for (JsonElement optionElement : optionsArray) {
            JsonObject optionObject = optionElement.getAsJsonObject();
            JsonArray optionAnswersArray = optionObject.getAsJsonArray("optionAnswers");
            List<String> optionAnswers = new ArrayList<>();
            for (JsonElement optionAnswerElement : optionAnswersArray) {
                optionAnswers.add(optionAnswerElement.getAsString());
            }
            String answer = optionObject.get("answer").getAsString();
            options.add(new MissingWordOption(optionAnswers, answer));
        }

        return new MissingWordQuestion(question, options, penalty, quotation);
    }
}
