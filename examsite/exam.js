

/**
 * Function to read the exam data from a JSON file
 * @param filename {string} - The name of the file to read
 * @returns {Promise<any>} - A promise that resolves with the exam data
 */
function fetchExamDataFromFile(filename) {
    return fetch(filename)
        .then(response => response.json())
        .catch(error => {
            console.error("Error reading exam file:", error);
        });
}


// Fetch the exam data from the JSON file and generate the interface
fetchExamDataFromFile("exam.json")
    .then(examData => {
        generateExamInterface(examData);
    });