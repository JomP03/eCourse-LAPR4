function getAnswers() {
    const answers = {
        multipleChoice: [],
        boolean: [],
        matching: [],
        numerical: [],
        shortAnswer: [],
        missingWord: []
    };

    // Retrieve user's answers for multiple choice questions
    getMultipleChoiceAnswers(answers);

    // Retrieve user's answers for boolean questions
    getBooleanAnswers(answers)

    // Retrieve user's answers for matching questions
    getMatchingAnswers(answers);

    // Retrieve user's answers for numerical questions
    getNumericalAnswers(answers);

    // Retrieve user's answers for short answer questions
    getShortAnswerAnswers(answers);

    // Retrieve user's answers for missing word questions
    getMissingWordAnswers(answers);

    return answers;
}


/**
 * Function to retrieve the user's answers for multiple choice questions
 * @param answers {Object} - The object containing the user's answers
 */
function getMultipleChoiceAnswers(answers) {
    const multipleChoiceContainers = document.querySelectorAll('[id^="multipleChoice_"]');
    multipleChoiceContainers.forEach(container => {
        const questionId = container.id;
        const position = questionId.split('_')[1];
        const questionText = container.querySelector('p').textContent;
        const selectedOption = container.querySelector('input[type="radio"]:checked');

        if (selectedOption) {
            const answer = {
                questionId,
                position,
                questionText,
                answer: selectedOption.value
            };
            answers.multipleChoice.push(answer);
        }
    });
}


/**
 * Function to retrieve the user's answers for boolean questions
 * @param answers {Object} - The object containing the user's answers
 */
function getBooleanAnswers(answers) {
    const booleanContainers = document.querySelectorAll('[id^="boolean_"]');
    booleanContainers.forEach(container => {
        const questionId = container.id;
        const position = questionId.split('_')[1];
        const questionText = container.querySelector('p').textContent;
        const selectedOption = container.querySelector('input[type="radio"]:checked');

        if (selectedOption) {
            const answer = {
                questionId,
                position,
                questionText,
                answer: selectedOption.value
            };
            answers.boolean.push(answer);
        }
    });
}



/**
 * Function to retrieve the user's answers for matching questions
 * @param answers {Object} - The object containing the user's answers
 */
function getMatchingAnswers(answers) {
    const matchingContainers = document.querySelectorAll('[id^="matching_"]');
    matchingContainers.forEach(container => {
        const questionId = container.id;
        const position = questionId.split('_')[1];
        const questionText = container.querySelector('p').textContent;
        const matchingOptions = container.querySelectorAll('.matching-option');

        const matches = Array.from(matchingOptions).map(matchingOption => {
            return {
                left: matchingOption.dataset.left,
                right: matchingOption.options[matchingOption.selectedIndex].value
            };
        });

        const answer = {
            questionId,
            position,
            questionText,
            matches
        };

        answers.matching.push(answer);
    });
}



/**
 * Function to retrieve the user's answers for numerical questions
 * @param answers {Object} - The object containing the user's answers
 */
function getNumericalAnswers(answers) {
    const numericalContainers = document.querySelectorAll('[id^="numerical_"]');
    numericalContainers.forEach(container => {
        const questionId = container.id;
        const position = questionId.split('_')[1];
        const questionText = container.querySelector('p').textContent;
        const input = container.querySelector('input[type="number"]');

        const answer = {
            questionId,
            position,
            questionText,
            answer: input.value
        };
        answers.numerical.push(answer);
    });
}


/**
 * Function to retrieve the user's answers for short answer questions
 * @param answers {Object} - The object containing the user's answers
 */
function getShortAnswerAnswers(answers) {
    const shortAnswerContainers = document.querySelectorAll('[id^="shortAnswer_"]');
    shortAnswerContainers.forEach(container => {
        const questionId = container.id;
        const position = questionId.split('_')[1];
        const questionText = container.querySelector('p').textContent;
        const input = container.querySelector('input[type="text"]');

        const answer = {
            questionId,
            position,
            questionText,
            answer: input.value
        };
        answers.shortAnswer.push(answer);
    });
}


/**
 * Function to retrieve the user's answers for missing word questions
 * @param answers {Object} - The object containing the user's answers
 */
function getMissingWordAnswers(answers) {
    const missingWordContainers = document.querySelectorAll('[id^="missingWord_"]');
    missingWordContainers.forEach(container => {
        const questionId = container.id;
        const position = questionId.split('_')[1];
        const questionText = container.querySelector('p').textContent;
        const selects = container.querySelectorAll('select');

        const answer = {
            questionId,
            position,
            questionText,
            answers: Array.from(selects).map(select => select.value)
        };
        answers.missingWord.push(answer);
    });
}

