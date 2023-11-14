/**
 * Generates a .txt file with the exam answers
 * @param answers The answers to the exam
 */
function generateExamFile(answers) {
    let fileContent = '';

    // Generate the file content based on the answers
    for (const questionType in answers) {
        const questionAnswers = answers[questionType];
        questionAnswers.forEach(answer => {
            fileContent += `${questionType.toUpperCase()}:${answer.questionText}\n`;

            if (questionType === 'matching') {
                fileContent += `MATCHES:${answer.matches.map(match => `${match.left}-${match.right}`).join(' , ')}\n`;
            } else if (questionType === 'missingWord') {
                answer.answers.forEach((missingWord, index) => {
                    fileContent += `ANSW:${missingWord}\n`;
                });
            } else {
                if (answer.answer === '') {
                    //alert('ESCREVER ALGUMA COISA OH BURRO!');
                    //console.log('ESCREVER ALGUMA COISA OH BURRO!');
                }
                fileContent += `ANSW:${answer.answer}\n`;
            }

            fileContent += `POSITION:${answer.position}\n`;

            fileContent += '\n';
        });
    }

    // Create a file blob with the content
    const fileBlob = new Blob([fileContent], { type: 'text/plain' });

    // Generate a download link for the file
    const downloadLink = document.createElement('a');
    downloadLink.href = URL.createObjectURL(fileBlob);
    downloadLink.download = 'exam_answers.txt';

    // Trigger the download
    downloadLink.click();
}
