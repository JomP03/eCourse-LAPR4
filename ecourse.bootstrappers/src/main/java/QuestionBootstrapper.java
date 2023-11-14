import eapli.framework.actions.Action;
import exammanagement.application.service.*;
import persistence.PersistenceContext;
import questionmanagment.application.*;
import questionmanagment.application.addquestions.*;
import questionmanagment.domain.*;
import questionmanagment.domain.factory.QuestionFactory;

import java.util.*;

public class QuestionBootstrapper implements Action {

    private final AddMultipleChoiceQuestionController addMultipleChoiceQuestionController =
            new AddMultipleChoiceQuestionController(
                    PersistenceContext.repositories().questions(),
                    new QuestionFactory(
                            new QuestionValidator(new ExamHandler(PersistenceContext.repositories().exams())),
                            new QuestionTxtGenerator()));

    private final AddShortAnswerQuestionController addShortAnswerQuestionController =
            new AddShortAnswerQuestionController(
                    PersistenceContext.repositories().questions(),
                    new QuestionFactory(
                            new QuestionValidator(new ExamHandler(PersistenceContext.repositories().exams())),
                            new QuestionTxtGenerator()));
    private final AddMatchingQuestionController addMatchingQuestionController =
            new AddMatchingQuestionController(
                    PersistenceContext.repositories().questions(),
                    new QuestionFactory(
                            new QuestionValidator(new ExamHandler(PersistenceContext.repositories().exams())),
                            new QuestionTxtGenerator()));

    private final AddNumericalQuestionController addNumericalQuestionController =
            new AddNumericalQuestionController(
                    PersistenceContext.repositories().questions(),
                    new QuestionFactory(
                            new QuestionValidator(new ExamHandler(PersistenceContext.repositories().exams())),
                            new QuestionTxtGenerator()));

    private final AddMissingWordQuestionController addMissingWordQuestionController =
            new AddMissingWordQuestionController(
                    PersistenceContext.repositories().questions(),
                    new QuestionFactory(
                            new QuestionValidator(new ExamHandler(PersistenceContext.repositories().exams())),
                            new QuestionTxtGenerator()));

    private final AddBooleanQuestionController addBooleanQuestionController =
            new AddBooleanQuestionController(
                    PersistenceContext.repositories().questions(),
                    new QuestionFactory(
                            new QuestionValidator(new ExamHandler(PersistenceContext.repositories().exams())),
                            new QuestionTxtGenerator()));



    @Override
    public boolean execute() {

        // ---- Matching Questions ----
        addMultipleChoiceQuestionController.addQuestion("What is the capital of Portugal?",
                10f, 10f,
                List.of("Lisbon", "Porto", "Coimbra", "Faro"), "Lisbon");

        addMultipleChoiceQuestionController.addQuestion("What is the capital of Spain?",
                10f, 10f,
                List.of("Madrid", "Barcelona", "Seville", "Valencia"), "Madrid");

        addMultipleChoiceQuestionController.addQuestion("What is the capital of France?",
                10f, 10f,
                List.of("Paris", "Marseille", "Lyon", "Toulouse"), "Paris");

        addMultipleChoiceQuestionController.addQuestion("What is the capital of Germany?",
                10f, 10f,
                List.of("Berlin", "Hamburg", "Munich", "Cologne"), "Berlin");

        addMultipleChoiceQuestionController.addQuestion("What is the capital of Italy?",
                10f, 10f,
                List.of("Rome", "Milan", "Naples", "Turin"), "Rome");

        addMultipleChoiceQuestionController.addQuestion("What is the capital of the United Kingdom?",
                10f, 10f,
                List.of("London", "Manchester", "Liverpool", "Birmingham"), "London");

        addMultipleChoiceQuestionController.addQuestion("What is the capital of the Netherlands?",
                10f, 10f,
                List.of("Amsterdam", "Rotterdam", "The Hague", "Utrecht"), "Amsterdam");

        addMultipleChoiceQuestionController.addQuestion("What is the capital of Belgium?",
                10f, 10f,
                List.of("Brussels", "Antwerp", "Ghent", "Charleroi"), "Brussels");

        addMultipleChoiceQuestionController.addQuestion("What is the capital of Switzerland?",
                10f, 10f,
                List.of("Bern", "Zurich", "Geneva", "Basel"), "Bern");

        addMultipleChoiceQuestionController.addQuestion("What is the capital of Austria?",
                10f, 10f,
                List.of("Vienna", "Graz", "Linz", "Salzburg"), "Vienna");


        // ---- Short Answer ----
        addShortAnswerQuestionController.addQuestion("Which country has the largest population in the world?",
                10f, 10f, "China");

        addShortAnswerQuestionController.addQuestion("Which country has the largest area in the world?",
                10f, 10f, "Russia");

        addShortAnswerQuestionController.addQuestion("Which country has the largest economy in the world?",
                10f, 10f, "USA");

        addShortAnswerQuestionController.addQuestion("Which country has the largest population in Europe?",
                10f, 10f, "Germany");

        addShortAnswerQuestionController.addQuestion("Which country has the largest area in Europe?",
                10f, 10f, "Russia");

        addShortAnswerQuestionController.addQuestion("Which country has the largest economy in Europe?",
                10f, 10f, "Germany");

        addShortAnswerQuestionController.addQuestion("Which country has the most milionares",
                10f, 10f, "USA");

        addShortAnswerQuestionController.addQuestion("Which country has the most billionaires",
                10f, 10f, "USA");

        addShortAnswerQuestionController.addQuestion("Which country has the most Nobel prizes",
                10f, 10f, "USA");

        addShortAnswerQuestionController.addQuestion("Which country has the most Olympic medals",
                10f, 10f, "USA");


        // ---- Matching Question ----
        addMatchingQuestionController.addQuestion("Match the capital city with its corresponding country",
                10f, 10f,
                List.of("Tokyo", "Beijing", "Seoul", "Buenos Aires", "Cairo"),
                List.of("Japan", "China", "South Korea", "Argentina", "Egypt"));

        addMatchingQuestionController.addQuestion("Match the following football clubes " +
                        "with their respective league",
                10f, 10f,
                List.of("FC Porto", "Manchester City", "Juventus", "Bayern Munich", "Paris Saint-Germain"),
                List.of("Liga Nos", "Premier League", "Serie A", "Bundesliga", "Ligue UberEats"));

        addMatchingQuestionController.addQuestion("Match the following car brands " +
                        "with their respective models",
                10f, 10f,
                List.of("Audi", "Honda", "Volkswagen", "Renault", "Volkswagen"),
                List.of("A3", "Civic", "Golf", "Clio", "Polo"));

        addMatchingQuestionController.addQuestion("Match the following supercarcar brands " +
                        "with their respective models",
        10f, 10f,
                List.of("Lamborghini", "Ferrari", "McLaren", "Bugatti", "Ford"),
                List.of("Aventador", "458", "Speedtail", "Chiron", "GT"));

        addMatchingQuestionController.addQuestion("Match the element with its chemical symbol",
                10f, 10f,
                List.of("Hydrogen", "Helium", "Carbon", "Nitrogen", "Oxygen"),
                List.of("H", "He", "C", "N", "O"));

        addMatchingQuestionController.addQuestion("Match the country with its national animal",
                10f, 10f,
                List.of("Australia", "Canada", "India", "South Africa"),
                List.of("Kangaroo", "Beaver", "Tiger", "Springbok"));

        addMatchingQuestionController.addQuestion("For what are these people know for?",
                10f, 10f,
                List.of("Albert Einstein", "Isaac Newton", "Charles Darwin", "Marie Curie", "Stephen Hawking"),
                List.of("Relativity", "Gravity", "Evolution", "Radioactivity", "Black Holes"));

        addMatchingQuestionController.addQuestion("Match the movie quote with the film",
                10f, 10f,
                List.of("May the force be with you", "Here's looking at you kid", "I'll be back",
                        "You can't handle the truth", "I see dead people"),
                List.of("Star Wars", "Casablanca", "The Terminator", "A Few Good Men", "The Sixth Sense"));


        // ---- Numerical Question ----
        addNumericalQuestionController.addQuestion("Solve for x -> 2x + 5 = 13",
                10f, 10f, "4");

        addNumericalQuestionController.addQuestion("Calculate the area of a circle with a radius of 6",
                12f, 12f, "113.04");

        addNumericalQuestionController.addQuestion("What is the value of π (pi) " +
                        "rounded to two decimal places?",
                8f, 8f, "3.14");

        addNumericalQuestionController.addQuestion("Evaluate the expression -> √(5^2) - 3 × 4",
                9f, 9f, "7");

        addNumericalQuestionController.addQuestion("Solve the equation -> 5x + 2 = 0",
                11f, 11f, "2.5");

        addNumericalQuestionController.addQuestion("Calculate the volume of a cube with side length 7",
                10f, 10f, "343");

        addNumericalQuestionController.addQuestion("Find the value of x -> log(x) = 2",
                9f, 9f, "100");

        addNumericalQuestionController.addQuestion("Solve the equation -> 2x - 1 = 0",
                10f, 10f, "-0.5");

        addNumericalQuestionController.addQuestion("Calculate the perimeter of a rectangle with " +
                        "length 12 and width 8",
                11f, 11f, "40");

        addNumericalQuestionController.addQuestion("Evaluate the expression -> 3! + 5 - 2^2",
                8f, 8f, "28");



        // ---- Missing Word Question ----
        addMissingWordQuestionController.addQuestion("Portugal is [[n]] and is located in [[n]].",
                1.5f, 0.5f, Arrays.asList(
                        new MissingWordOption(Arrays.asList("wonderful", "stunning", "beautiful"), "beautiful"),
                        new MissingWordOption(Arrays.asList("Europe", "Africa", "North America"), "Europe")
                ));

        addMissingWordQuestionController.addQuestion("The [[n]] is longest movie ever made",
                2.0f, 0.8f, List.of(new MissingWordOption(
                        Arrays.asList("The Cure for Insomnia",
                                "The Godfather", "The Irishman"), "The Cure for Insomnia")));

        addMissingWordQuestionController.addQuestion("The capital city of France is [[n]].",
                1.8f, 0.6f, List.of(
                        new MissingWordOption(Arrays.asList("Moscow", "Tokyo", "Paris"), "Paris")));

        addMissingWordQuestionController.addQuestion("An apple a day keeps the [[n]] away." +
                        "Having [[n]] of the dark is a common phobia.",
                1.7f, 0.5f, Arrays.asList(
                        new MissingWordOption(Arrays.asList("doctor", "dentist", "police"), "doctor"),
                        new MissingWordOption(Arrays.asList("happiness", "anger", "fear"), "fear" )
                ));

        addMissingWordQuestionController.addQuestion("The [[n]] is the largest organ in the human body.",
                2.2f, 0.7f, List.of(
                        new MissingWordOption(Arrays.asList("brain", "heart", "skin"), "skin")));

        addMissingWordQuestionController.addQuestion("The Great Wall of China is over [[n]] kilometers long" +
                        ". And it's located in [[n]].",
                2.5f, 0.9f, Arrays.asList(
                        new MissingWordOption(Arrays.asList("21,000", "5,000", "30,000"), "21,000"),
                        new MissingWordOption(Arrays.asList("India", "Brazil", "China"), "China")
                ));

        addMissingWordQuestionController.addQuestion("The currency of Japan is the [[n]].",
                1.9f, 0.6f, List.of(
                        new MissingWordOption(Arrays.asList("yen", "euro", "pound"), "yen")));

        addMissingWordQuestionController.addQuestion("The planet [[n]] is known as the 'Red Planet'." +
                        " And the planet [[n]] is the closest planet to the Sun.",
                2.1f, 0.8f, Arrays.asList(
                        new MissingWordOption(Arrays.asList("Mars", "Jupiter", "Saturn"), "Mars"),
                        new MissingWordOption(Arrays.asList("Neptune", "Mercury", "Pluto"), "Mercury")
                ));

        addMissingWordQuestionController.addQuestion("The [[n]] is the largest animal in the world.",
                2.3f, 0.7f, List.of(new MissingWordOption(
                        Arrays.asList("blue whale", "elephant", "giraffe"), "blue whale")));



                                // ---- Boolean Questions ----
        addBooleanQuestionController.addQuestion("Did the man went to the moon?",
                10f, 10f, true);

        addBooleanQuestionController.addQuestion("Is the 'Hammer Principle' a good principle?",
                50f, 50f, true);

        addBooleanQuestionController.addQuestion("Is 'Guga' taller than 1,60m?",
                10f, 10f, false);

        addBooleanQuestionController.addQuestion("Is water boiling point 100 degrees Celsius?",
                8f, 8f, true);

        addBooleanQuestionController.addQuestion("Is the Earth the third planet from the Sun?",
                7f, 7f, true);

        addBooleanQuestionController.addQuestion("Is the Atlantic Ocean the largest ocean on Earth?",
                6f, 6f, false);

        addBooleanQuestionController.addQuestion("Is the Amazon River the longest river in the world?",
                8.5f, 8.5f, true);

        addBooleanQuestionController.addQuestion("Is Mount Everest the tallest mountain in the world?",
                9.5f, 9.5f, true);

        addBooleanQuestionController.addQuestion("Is soccer the most popular sport globally?",
                7.5f, 7.5f, true);

        addBooleanQuestionController.addQuestion("Is the Great Wall of China visible from space?",
                6.5f, 6.5f, false);

        addBooleanQuestionController.addQuestion("Is the Eiffel Tower located in London?",
                9f, 9f, false);

        addBooleanQuestionController.addQuestion("Is the currency symbol for the British Pound £?",
                8f, 8f, true);



        return true;
    }
}
