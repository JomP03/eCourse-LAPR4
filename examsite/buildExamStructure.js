/**
 * Creates the HTML structure for the exam based on the exam data
 * @param examData
 */
function generateExamInterface(examData) {
    const examContainer = document.getElementById("exam-container");

    // Generate the exam title and header
    const titleElement = document.createElement("h1");
    titleElement.classList.add("exam_title");
    titleElement.textContent = examData.title;
    examContainer.appendChild(titleElement);

    const headerElement = document.createElement("p");
    headerElement.textContent = examData.header.description;
    examContainer.appendChild(headerElement);

    let sectionIndex = 0;
    // Generate the sections and questions
    examData.sections.forEach(section => {

        const sectionElement = document.createElement("div");
        sectionIndex++;

        const sectionTitle = document.createElement("h2");
        sectionTitle.textContent = 'Section ' + sectionIndex + ' - ' + section.description;
        sectionElement.appendChild(sectionTitle);

        section.questions.forEach((question, questionIndex) => {
            let questionElement;
            const position = `SEC${sectionIndex}-${questionIndex + 1}`;

            // Generate each question based on its type
            switch (question.questionType) {
                case "MULTIPLE_CHOICE":
                    questionElement = generateMultipleChoiceQuestion(question, position);
                    break;
                case "MATCHING":
                    questionElement = generateMatchingQuestion(question, position);
                    break;
                case "BOOLEAN":
                    questionElement = generateBooleanQuestion(question, position);
                    break;
                case "NUMERICAL":
                    questionElement = generateNumericalQuestion(question, position);
                    break;
                case "SHORT_ANSWER":
                    questionElement = generateShortAnswerQuestion(question, position);
                    break;
                case "MISSING_WORD":
                    questionElement = generateMissingWordQuestion(question, position);
                    break;
                default:
                    // Handle unknown question types or provide a default structure
                    questionElement = document.createElement("p");
                    questionElement.textContent = "Unknown question type";
                    break;
            }

            sectionElement.appendChild(questionElement);
        });

        const lineBreak = document.createElement("br");
        examContainer.appendChild(lineBreak);

        examContainer.appendChild(sectionElement);
    });

    const lineBreak = document.createElement("br");
    examContainer.appendChild(lineBreak);
    examContainer.appendChild(lineBreak);

    // Generate the submit button
    const submitButton = document.createElement("button");
    submitButton.textContent = "Submit";
    submitButton.classList.add("submit_button");
    examContainer.appendChild(submitButton);

    // Add event listener to the submit button
    submitButton.addEventListener('click', function () {
        const answers = getAnswers();
        generateExamFile(answers);

        console.log("Exam submitted");
    });
}

/**
 * Function to generate the structure of a multiple choice question
 * @param question {Object} - The question object
 * @param position {String} - The position of the question in the exam
 * @returns {HTMLDivElement} - The question element
 */
function generateMultipleChoiceQuestion(question, position) {
    const questionElement = document.createElement("div");
    questionElement.classList.add("question");
    questionElement.id = `multipleChoice_${position}`;

    let index = position.split('-');
    const questionText = document.createElement("p");
    questionText.textContent = index[1] + '. ' + question.question;
    questionElement.appendChild(questionText);

    const optionsContainer = document.createElement("div");

    // Add default option for no selection
    const defaultOptionLabel = document.createElement("label");
    const defaultOptionInput = document.createElement("input");
    defaultOptionInput.type = "radio";
    defaultOptionInput.value = ""; // or null
    defaultOptionInput.name = `question_${position}`; // Use the same name as other options
    defaultOptionLabel.appendChild(defaultOptionInput);

    // Randomize the order of the options
    const shuffledOptions = shuffle(question.options);

    // Add the options to the question
    shuffledOptions.forEach((option, index) => {
        const optionLabel = document.createElement("label");
        const optionInput = document.createElement("input");
        optionInput.type = "radio";
        optionInput.value = option;
        optionInput.name = `question_${position}`; // Use the same name as other options
        optionLabel.appendChild(optionInput);
        optionLabel.appendChild(document.createTextNode(option));
        optionsContainer.appendChild(optionLabel);
    });

    questionElement.appendChild(optionsContainer);

    return questionElement;
}

function shuffle(array) {
    let currentIndex = array.length,  randomIndex;

    // While there remain elements to shuffle.
    while (currentIndex !== 0) {

        // Pick a remaining element.
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex--;

        // And swap it with the current element.
        [array[currentIndex], array[randomIndex]] = [
            array[randomIndex], array[currentIndex]];
    }

    return array;
}



/**
 * Function to generate the structure of a matching question
 * @param question {Object} - The question object
 * @param position {String} - The index of the question
 * @returns {HTMLDivElement} - The question element
 */
function generateMatchingQuestion(question, position) {
    const questionElement = document.createElement("div");
    questionElement.classList.add("question");
    questionElement.id = `matching_${position}`;

    const index = position.split("-");

    const questionText = document.createElement("p");
    questionText.textContent = index[1] + ". " + question.question;
    questionElement.appendChild(questionText);

    const optionsContainer = document.createElement("div");

    const leftOptions = question.leftOptions;
    const rightOptions = question.rightOptions;

    leftOptions.forEach((leftOption, index) => {
        const optionElement = document.createElement("div");

        const optionText = document.createElement("span");
        optionText.textContent = leftOption.trim();

        alignSelectComponent(optionText);

        const select = document.createElement("select");
        select.className = "matching-option";
        select.dataset.left = leftOption.trim();

        // Add the default option "-"
        const defaultOption = document.createElement("option");
        defaultOption.value = "-";
        defaultOption.text = "-";
        select.appendChild(defaultOption);

        rightOptions.forEach((rightOption) => {
            const optionElement = document.createElement("option");
            optionElement.value = rightOption.trim();
            optionElement.text = rightOption.trim();
            select.appendChild(optionElement);
        });

        optionElement.appendChild(optionText);
        optionElement.appendChild(select);
        optionsContainer.appendChild(optionElement);
    });

    questionElement.appendChild(optionsContainer);

    return questionElement;
}



function alignSelectComponent(optionText) {
    let textLength = 20 - optionText.textContent.length;

    addSpaces(optionText, textLength*2);
}


function addSpaces(optionText, textLength) {
    for (let i = 0; i < textLength; i++) {
        optionText.textContent += "\u00A0";
    }
}


/**
 * Function to generate the structure of a boolean question
 * @param question {Object} - The question object
 * @param position {String} - The index of the question
 * @returns {HTMLDivElement} - The question element
 */
function generateBooleanQuestion(question, position) {
    const questionElement = document.createElement("div");
    // Connection to css - add class name to the question element
    questionElement.classList.add("question");
    questionElement.id = `boolean_${position}`;

    let index = position.split('-');
    const questionText = document.createElement("p");
    questionText.textContent =  index[1] + '. ' + question.question;
    questionElement.appendChild(questionText);

    const trueLabel = document.createElement("label");
    const trueInput = document.createElement("input");
    trueInput.type = "radio";
    trueInput.name = `boolean_${questionElement.id}`; // Unique name attribute for each question
    trueInput.value = "true";
    trueLabel.appendChild(trueInput);
    trueLabel.appendChild(document.createTextNode("True"));
    questionElement.appendChild(trueLabel);

    const falseLabel = document.createElement("label");
    const falseInput = document.createElement("input");
    falseInput.type = "radio";
    falseInput.name = `boolean_${questionElement.id}`; // Unique name attribute for each question
    falseInput.value = "false";
    falseLabel.appendChild(falseInput);
    falseLabel.appendChild(document.createTextNode("False"));
    questionElement.appendChild(falseLabel);

    return questionElement;
}


/**
 * Function to generate the structure of a numerical question
 * @param question {Object} - The question object
 * @param position {String} - The index of the question
 * @returns {HTMLDivElement} - The question element
 */
function generateNumericalQuestion(question, position) {
    const questionElement = document.createElement("div");
    // Connection to css - add class name to the question element
    questionElement.classList.add("question");
    questionElement.id = `numerical_${position}`;

    let index = position.split('-');
    const questionText = document.createElement("p");
    questionText.textContent = index[1] + '. ' + question.question;
    questionElement.appendChild(questionText);

    const input = document.createElement("input");
    input.type = "number";
    input.name = question.name;
    questionElement.appendChild(input);

    return questionElement;
}


/**
 * Function to generate the structure of a short answer question
 * @param question {Object} - The question object
 * @param position {String} - The index of the question
 * @returns {HTMLDivElement} - The question element
 */
function generateShortAnswerQuestion(question, position) {
    const questionElement = document.createElement("div");
    // Connection to css - add class name to the question element
    questionElement.classList.add("question");
    questionElement.id = `shortAnswer_${position}`;

    let index = position.split('-');
    const questionText = document.createElement("p");
    questionText.textContent = index[1] + '. ' + question.question;
    questionElement.appendChild(questionText);

    const input = document.createElement("input");
    input.type = "text";
    input.name = question.name;
    questionElement.appendChild(input);

    return questionElement;
}


/**
 * Function to generate the structure of a missing word question
 * @param question {Object} - The question object
 * @param position
 * @returns {HTMLDivElement} - The question element
 */
function generateMissingWordQuestion(question, position) {
    const questionElement = document.createElement("div");
    questionElement.classList.add("question");
    questionElement.id = `missingWord_${position}`;

    let index = position.split("-");
    const questionText = document.createElement("p");
    questionText.textContent = index[1] + ". ";

    const questionParts = question.question.split("[[n]]");
    questionParts.forEach((part, index) => {
        questionText.appendChild(document.createTextNode(part));
        if (index < questionParts.length - 1) {
            const select = document.createElement("select");
            select.name = question.name + index;

            // Add the default option "-"
            const defaultOption = document.createElement("option");
            defaultOption.value = "-";
            defaultOption.text = "-";
            select.appendChild(defaultOption);

            // Add the option answers to the dropdown list
            question.missingOptions[index].optionAnswers.forEach((option) => {
                const optionElement = document.createElement("option");
                optionElement.value = option;
                optionElement.text = option;
                select.appendChild(optionElement);
            });

            questionText.appendChild(select);
            console.log(questionText);
        }
    });

    questionElement.appendChild(questionText);

    return questionElement;
}
