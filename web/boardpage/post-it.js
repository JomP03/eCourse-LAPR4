/**
 * Global variables to be updated each time a new post-it creation is started
 */
let postItRow;
let postItColumn;


/**
 * Global variable to store the post-it id
 */
let postItCreated = false;


/**
 * Function to update the global variable "postItCreated"
 */
function updateCreatedStateToTrue() {
    postItCreated = true;
}


/**
 * Function to update the global variable "postItCreated"
 */
function updateCreatedStateToFalse() {
    postItCreated = false;
}


/**
 * Global variable that stores the user permissions
 *
 */
let canEdit = false;


/**
 * Function to update the global variable "canEdit" with the user permissions
 */
function userPermissionsRequest() {
    return new Promise((resolve, reject) => {
        const userPermissionsRequest = new XMLHttpRequest();

        userPermissionsRequest.onload = function () {
            if (userPermissionsRequest.status === 200) {
                const canEdit = JSON.parse(userPermissionsRequest.responseText);
                resolve(canEdit);
            } else {
                reject(new Error('Request failed'));
            }
        };

        const boardId = window.location.href.split('/').pop();

        userPermissionsRequest.open('POST', `/postit/prm`, true);
        userPermissionsRequest.setRequestHeader('Content-Type', 'application/json');

        const token = getTokenCookie();
        if (token) {
            userPermissionsRequest.setRequestHeader('Authorization', token);
        }

        userPermissionsRequest.send(boardId);
    });
}


/**
 * Functions to show and hide the loading overlay
 */
function showLoadingOverlay() {
    document.getElementById('loading-overlay').style.display = 'flex';
}

function hideLoadingOverlay() {
    document.getElementById('loading-overlay').style.display = 'none';
}


const overlayForm = document.getElementById('overlay-form');


/**
 * Disable the overlay whenever the user clicks outside the post-it form
 */
overlayForm.onclick = function () {
    // Reset the form
    document.getElementById('post-it-form').reset();
    document.getElementById('content-area').textContent = '';

    // Hide the form and overlay
    document.querySelector('#post-it-form').style.display = 'none';
    deactivateFormOverlay();
};


function activateFormOverlay() {
    overlayForm.style.display = 'block';
}


function deactivateFormOverlay() {
    overlayForm.style.display = 'none';
}


/**
 * This function is responsible for creating each cell the respective button to add a post-it and
 * add behavior to both cells and buttons.
 * @param cell
 * @param row
 * @param column
 */
function createCellButtons(cell, row, column) {
    // Create the cell button and assign it both the class name
    let cellButton = document.createElement('button');
    cellButton.className = 'cell-button';
    cellButton.textContent = '+';

    // Append to the respective cell
    cell.appendChild(cellButton);

    // Add event listener to button for mouseover
    cell.addEventListener('mouseover', function (event) {
        if (cell.querySelector('textarea') === null && cell.querySelector('img') === null) {
            cellButton.style.display = 'block';
        }
    });

    // Add event listener to button for mouseout
    cell.addEventListener('mouseout', function (event) {
        cellButton.style.display = 'none';
    });

    // Add event listener to button for click
    cellButton.addEventListener('click', function (event) {
        // Call overlay function "on()"
        activateFormOverlay();

        document.getElementById('color-picker').value = '#FFFF84';
        document.querySelector('#post-it-form').style.display = 'block';

        // Update the global variables
        postItRow = row;
        postItColumn = column;
    });
}


/**
 * This constant is set to define the file choosers to be watched
 * @type {HTMLElement}
 */
const file = document.getElementById('file-chooser');
file.addEventListener('change', function (event) {
    fileListener(event, 'create');
});


const editImageFile = document.getElementById('editImage');
editImageFile.addEventListener('change', function (event) {
    fileListener(event, 'edit');
});


/**
 * This is the function that is responsible for uploading the image to imgur and placing a link inside the post it forms.
 * @param ev
 * @param type {string} - This is the type of the file chooser that is being listened to. It can be either 'create' or 'edit'
 */
function fileListener(ev, type) {
    let content;
    if (type === 'edit') {
        content = document.getElementById(`post-it-image_${postItRow}_${postItColumn}`);
    } else {
        content = document.getElementById('content-area');
    }

    ev.preventDefault();

    const formData = new FormData();
    formData.append('image', ev.target.files[0]);

    const request = new XMLHttpRequest();
    request.open('POST', 'https://api.imgur.com/3/image/');
    request.setRequestHeader("Authorization", "Client-ID b95c3e9307e7704");

    request.onreadystatechange = function () {
        if (request.readyState === XMLHttpRequest.DONE) {
            if (request.status === 200) {
                const response = JSON.parse(request.responseText);
                if (type === 'edit') {
                    const image = document.getElementById(`post-it-area-image_${postItRow}_${postItColumn}`);
                    image.setAttribute('src', response.data.link);
                } else {
                    content.value = response.data.link;
                }

                displayNotification("success", "Image loaded successfully!");

            } else {
                displayNotification("failure", "Error loading image!");
            }
        }
    };

    request.send(formData);
}


// Overlay code
/**
 * Call this if you want the user to not be able to select anything on the page
 * except for elements in higher z-indexes than the overlay
 */
function on() {
    document.getElementById("overlay").style.display = "block";
}


function off() {
    document.getElementById("overlay").style.display = "none";
}


/**
 * This function is responsible for creating the post-it inside the respective cell.
 * @param row
 * @param column
 * @param content
 * @param color
 * @param postItOnwer
 */
function createPostIt(row, column, content, color, postItOnwer) {
    // Create post-it
    let postIt = document.createElement('div');
    postIt.className = 'post-it';
    postIt.id = `post-it_${row}_${column}`;
    postIt.owner = postItOnwer;

    // style the post-it
    postIt.style.backgroundColor = color;
    postIt.style.transform = rotatePostIt();

    // Parent cell
    let parentCell = document.getElementById(`cell_${row}_${column}`);
    parentCell.appendChild(postIt);

    if ((/\.(jpe?g|png)$/i).test(content)) {
        // Place the content in the post-it and add it onclick()
        placePostItImage(row, column, content, color);
    } else {
        placePostItText(row, column, content, color);
    }

    // Display the creat notification
    if (postItCreated) {
        displayNotification("success", "Post-it created successfully!");
        updateCreatedStateToFalse();
    }

    // Update the variable
    hideLoadingOverlay();
}


function deletePostIt(row, column) {
    const postIt = document.getElementById(`post-it_${row}_${column}`);

    // Delete the post-it from both the visual board and the boardPostIts array
    if (postIt !== null) {
        postIt.remove();

        boardPostIts[row][column] = null;
    }
}


/**
 * This function is responsible for creating the post-it inside the respective cell using the information from
 * the form.
 * @param event
 */
function createPostItThroughForm(event) {
    // Prevent the default behavior of the form
    event.preventDefault();

    const postItContent = document.getElementById('content-area').value;
    const postItColor = document.getElementById('color-picker').value;

    // Loading animation
    showLoadingOverlay();

    createPostItRequest(postItRow, postItColumn, postItContent, postItColor);

    // Reset the form
    document.getElementById('post-it-form').reset();
    document.getElementById('content-area').textContent = '';

    // Hide the form and overlay
    document.querySelector('#post-it-form').style.display = 'none';
    deactivateFormOverlay();
}


function createPostItRequest(row, column, content, color) {
    // Make a request to the server to get the board post-its
    const postItCreationRequest = new XMLHttpRequest();

    // Define the behavior of the request when it loads
    postItCreationRequest.onload = function () {
        if (postItCreationRequest.status === 200) {
            // createPostIt(row, column, content, color);
            updateCreatedStateToTrue();
        } else {
            hideLoadingOverlay();
            displayNotification("failure", postItCreationRequest.responseText);
        }
    }

    // Get the board ID from the URL
    const boardId = window.location.href.split('/').pop();

    // Open the request
    postItCreationRequest.open('POST', `/postit/crt`, true);
    postItCreationRequest.setRequestHeader("Content-Type", "application/json");

    // Set the authorization header
    const token = getTokenCookie();
    if (token) {
        postItCreationRequest.setRequestHeader('Authorization', token);
    }

    const data = {
        row: row,
        column: column,
        content: content,
        color: color,
        boardId: boardId
    };

    // Send the request
    postItCreationRequest.send(JSON.stringify(data));
}


/**
 * Function that will communicate with the server to apply the changes made to the post-it
 * @param data - data to be sent to the server
 */
function changePostItRequest(data) {
    event.preventDefault();

    const request = new XMLHttpRequest();

    request.onload = function () {
        if (request.status === 200) {
            displayNotification("success", "Post-it updated successfully!");

            // Enables Post It
            disablePostIt(postItRow, postItColumn, false);

        } else if (request.status === 400) {
            displayNotification('failure', request.responseText);

            // Enables Post It
            disablePostIt(postItRow, postItColumn, false);
        } else {
            displayNotification("failure", "Server is overloaded! Please try again!");
            disablePostIt(postItRow, postItColumn, false);
        }
    }

    request.open("POST", `/postit/chg`, true);
    request.setRequestHeader("Content-Type", "application/json");

    const token = getTokenCookie();
    if (token) {
        request.setRequestHeader('Authorization', token);
    }

    request.send(JSON.stringify(data));
}


function undoPostItChangeRequest(row, column) {
    // Make a request to the server to get the board post-its
    const postItUndoRequest = new XMLHttpRequest();

    // Define the behavior of the request when it loads
    postItUndoRequest.onload = function () {
        if (postItUndoRequest.status === 200) {
            displayNotification("success", "Post-it undo successful!");
            disablePostIt(row, column, false);
        } else if (postItUndoRequest.status === 400) {
            displayNotification("failure", postItUndoRequest.responseText);
            disablePostIt(row, column, false);
        } else {
            displayNotification("failure", "Server is overloaded! Please try again!");
            disablePostIt(row, column, false);
        }
    }

    // Get the board ID from the URL
    const boardId = window.location.href.split('/').pop();

    // Open the request
    postItUndoRequest.open('POST', `/postit/uno`, true);
    postItUndoRequest.setRequestHeader("Content-Type", "application/json");

    // Set the authorization header
    const token = getTokenCookie();
    if (token) {
        postItUndoRequest.setRequestHeader('Authorization', token);
    }

    const data = {
        boardId: boardId,
        row: row,
        column: column
    };

    // Send the request
    postItUndoRequest.send(JSON.stringify(data));
}


// Obtain the button that will allow the user to get back the previous text inside the post-it
const undoButton = document.querySelector('.post-it-undo-button');


// Obtain the button that will allow the user to edit the text inside the post-it
const editTextPostIt = document.querySelector('.post-it-editTextArea-button');


// Obtain label for the input button
const editImageLabel = document.querySelector('.post-it-editImage-button');


// Obtain the input component that will allow the user to edit the image inside the post-it
const editImagePostIt = document.getElementById('editImage');


// Obtain the button to save the changed text inside the post-it
const saveButton = document.querySelector('.post-it-save-button');


// Obtain the button to cancel the changed text inside the post-it
const cancelButton = document.querySelector('.post-it-cancel-button');


// Obtain the Row Text Area
const rowTextArea = document.getElementById('row-area');


// Obtain the Column Text Area
const columnTextArea = document.getElementById('column-area');


// Obtain the Color Picker Area
const colorArea = document.getElementById('color-area');


// Undo Button Listener
undoButton.addEventListener('click', function (event) {
    event.preventDefault();

    // Disables Post-It
    disablePostIt(postItRow, postItColumn, true);

    // Exits the Window
    addExitButtonListener(event);

    // Send the data to the server
    undoPostItChangeRequest(postItRow, postItColumn);
});


// EditText Button Listener
editTextPostIt.addEventListener('click', function (event) {
    event.preventDefault();

    // Enable the row and column text area
    disableRowColumnColorArea(false);

    // Obtain the text area
    const areaText = document.getElementById(`post-it-area-text_${postItRow}_${postItColumn}`);
    areaText.readOnly = false;
    areaText.focus();

    // Hide and Display the buttons
    displayButtons(false, true);

    saveButton.style.display = 'inline-block';
    cancelButton.style.display = 'inline-block';
});


// EditImage Button Listener
editImageLabel.addEventListener('click', function (event) {

    if (!editImagePostIt.disabled) {
        // Enable the row and column text area
        disableRowColumnColorArea(false);

        // Hide and Display the buttons
        displayButtons(false, false);

        saveButton.style.display = 'inline-block';
        cancelButton.style.display = 'inline-block';
    }

});


// Will keep track of the success of the request
let success;


// Save Button Listener
saveButton.addEventListener('click', function (event) {
    // Get the board ID from the URL
    const boardId = window.location.href.split('/').pop();

    // Create data structure to send to the server
    const data = {
        boardId: boardId,
        oldRow: postItRow,
        oldColumn: postItColumn
    }

    // Update color
    const postItTopArea = document.getElementById(`post-it-top-area`);
    postItTopArea.style.backgroundColor = colorArea.value;

    // Save changed content
    let isText = false;
    let postItContent;
    let postItArea;
    if (postItTopArea.querySelector('textarea') !== null) {
        isText = true;
        postItContent = document.getElementById(`text_${postItRow}_${postItColumn}`);
        postItArea = document.getElementById(`post-it-area-text_${postItRow}_${postItColumn}`);

        // Adding the content to the post-it to the data structure
        data.oldContent = postItContent.value;
        data.newContent = postItArea.value;

        // Display the buttons
        displayButtons(true, false);

    } else {
        postItContent = document.getElementById(`post-it-image_${postItRow}_${postItColumn}`);
        postItArea = document.getElementById(`post-it-area-image_${postItRow}_${postItColumn}`);

        // Adding the content to the post-it to the data structure
        data.oldContent = postItContent.getAttribute('src');
        data.newContent = postItArea.getAttribute('src');

        // Display the buttons
        displayButtons(true, false);
    }

    // Obtain current post-it
    const currentPostIt = document.getElementById(`post-it_${postItRow}_${postItColumn}`);

    // Adding the position to the data structure
    data.newRow = rowTextArea.value;
    data.newColumn = columnTextArea.value;
    data.color = colorArea.value;

    // Disable the row and column text area
    disableRowColumnColorArea(true);

    // Hide the buttons
    saveButton.style.display = 'none';
    cancelButton.style.display = 'none';

    // Disables Post-It
    disablePostIt(postItRow, postItColumn, true);

    // Exits the Window
    addExitButtonListener(event);

    // Send the data to the server
    changePostItRequest(data, isText, currentPostIt, postItContent, colorArea.value);
});


// Cancel Button Listener
cancelButton.addEventListener('click', function (event) {
    // Cancel changed position
    rowTextArea.value = postItRow;
    columnTextArea.value = postItColumn;

    // Cancel changed color
    const postItTopArea = document.getElementById(`post-it-top-area`);
    colorArea.value = rgbToHex(postItTopArea.style.backgroundColor);

    // Cancel changed content
    let postItContent;
    let postItArea;
    if (postItTopArea.querySelector('textarea') !== null) {
        postItContent = document.getElementById(`text_${postItRow}_${postItColumn}`);
        postItArea = document.getElementById(`post-it-area-text_${postItRow}_${postItColumn}`);
        postItArea.value = postItContent.value;

        // Display the buttons
        displayButtons(true, true);

        // Disable the text area
        postItArea.readOnly = true;
    } else {
        postItContent = document.getElementById(`post-it-image_${postItRow}_${postItColumn}`);
        postItArea = document.getElementById(`post-it-area-image_${postItRow}_${postItColumn}`);
        postItArea.setAttribute('src', postItContent.getAttribute('src'));

        // Display the buttons
        displayButtons(true, false);
    }

    // Disable the row and column text area
    disableRowColumnColorArea(true);

    // Hide and Display the buttons
    saveButton.style.display = 'none';
    cancelButton.style.display = 'none';

});


function displayButtons(display, text) {
    if (display) {
        undoButton.style.display = 'inline-block';

        if (text)
            editTextPostIt.style.display = 'inline-block';
        else
            editImageLabel.style.display = 'inline-block';

    } else {
        undoButton.style.display = 'none';

        if (text)
            editTextPostIt.style.display = 'none';
        else
            editImageLabel.style.display = 'none';
    }
}


/**
 * Disables or enables the Edit, Undo and Delete buttons.
 */
function enableEditButtons(enable) {
    if (enable) {
        undoButton.disabled = false;
        editTextPostIt.disabled = false;
        editImageLabel.disabled = false;
        editImagePostIt.disabled = false;

        undoButton.classList.remove('post-it-area-disable-buttons');
        editTextPostIt.classList.remove('post-it-area-disable-buttons');
        editImageLabel.classList.remove('post-it-area-disable-buttons');

    } else {
        undoButton.disabled = true;
        editTextPostIt.disabled = true;
        editImagePostIt.disabled = true;

        undoButton.classList.add('post-it-area-disable-buttons');
        editTextPostIt.classList.add('post-it-area-disable-buttons');
        editImageLabel.classList.add('post-it-area-disable-buttons');
        editImageLabel.style.cursor = 'not-allowed';
    }
}


/**
 * Disables or enables the row and column text area.
 * @param disable {boolean} - True to disable, false to enable.
 */
function disableRowColumnColorArea(disable) {
    if (disable) {
        rowTextArea.disabled = true;
        rowTextArea.classList.add('disabled');
        columnTextArea.disabled = true;
        columnTextArea.classList.add('disabled');
        colorArea.disabled = true;
        colorArea.classList.add('disabled');
    } else {
        rowTextArea.disabled = false;
        rowTextArea.classList.remove('disabled');
        columnTextArea.disabled = false;
        columnTextArea.classList.remove('disabled');
        colorArea.disabled = false;
        colorArea.classList.remove('disabled');
    }
}


function disablePostIt(row, column, disable) {
    const postIt = document.getElementById(`post-it_${row}_${column}`);
    if (disable) {
        postIt.classList.add('div-disabled');
    } else {
        postIt.classList.remove('div-disabled');
    }
}


/**
 * Constants throughout the file.
 */
const postItArea = document.getElementById('post-it-area');
const postItTopArea = document.getElementById('post-it-top-area');


/**
 * This function is responsible for placing the content inside the post-it
 * and assign its behavior when pressed.
 * @param row {number} - The row of the post-it.
 * @param column {number} - The column of the post-it.
 * @param postItContent {string} - The content of the post-it.
 * @param color {string} - The color of the post-it.
 */
function placePostItImage(row, column, postItContent, color) {
    // Create the image inside html
    let postItImage = document.createElement('img');

    // Define image
    postItImage.setAttribute('src', postItContent);
    postItImage.setAttribute('alt', 'Image not found');

    // Define both css class name and id
    postItImage.className = 'post-it-content post-it-image';
    postItImage.id = `post-it-image_${row}_${column}`;

    // Retrieve the post-it from cell
    const postIt = document.getElementById(`post-it_${row}_${column}`);

    postIt.appendChild(postItImage);

    // Prevent default behavior of the image when "clicked"
    postItImage.addEventListener('click', function (event) {
        event.preventDefault();

        // Call overlay function "on()"
        on();

        // Update the global variables
        postItRow = postIt.id.split('_')[1];
        postItColumn = postIt.id.split('_')[2];

        // Display the image in full size, inside post-it area
        postItArea.style.display = 'block';

        postItTopArea.style.backgroundColor = postIt.style.backgroundColor;

        // Create the image
        let areaImage = document.createElement('img');

        // Define image
        areaImage.setAttribute('src', postItImage.getAttribute('src'));
        areaImage.setAttribute('alt', 'Image not found');

        // Define both css class name and id
        areaImage.className = 'post-it-area-content post-it-area-image';
        areaImage.id = `post-it-area-image_${postItRow}_${postItColumn}`;


        // Display the buttons according to the user permissions
        displayButtons(true, false);
        if (canEdit)
            isPostItOnwer(postIt);
        else
            displayButtons(false, false);


        editTextPostIt.style.display = 'none';

        postItTopArea.appendChild(areaImage);


        // Update row and column
        disableRowColumnColorArea(true);
        colorArea.value = rgbToHex(postIt.style.backgroundColor);
        rowTextArea.value = postItRow;
        columnTextArea.value = postItColumn;
    });
}


/**
 * This function is responsible for placing the content inside the post-it
 * and assign its behavior when pressed.
 * @param row {number} - The row of the post-it.
 * @param column {number} - The column of the post-it.
 * @param postItContent {string} - The content of the post-it.
 * @param color {string} - The color of the post-it.
 */
function placePostItText(row, column, postItContent, color) {
    // Create the text inside html
    let postItText = document.createElement('textarea');

    // Define text
    postItText.value = postItContent;
    postItText.readOnly = true;

    // Define css class name and id
    postItText.className = 'post-it-content post-it-text';
    postItText.id = `text_${row}_${column}`;

    // Retrieve the post-it from cell
    const postIt = document.getElementById(`post-it_${row}_${column}`);

    // Define the post-it text color
    const postItColorRGB = hexToRgb(color);
    const middleGray = {r: 128, g: 128, b: 128};

    if (compareColors(postItColorRGB, middleGray) === -1) {
        postItText.style.color = 'white';
    } else {
        postItText.style.color = 'black';
    }

    postIt.appendChild(postItText);

    // Prevent default behavior of the textarea when "clicked"
    postItText.addEventListener('click', function (event) {
        event.preventDefault();

        // Call overlay function "on()"
        on();

        // Update the global variables
        postItRow = postIt.id.split('_')[1];
        postItColumn = postIt.id.split('_')[2];

        // Display the textarea in full size, inside post-it area
        postItArea.style.display = 'block';

        postItTopArea.style.backgroundColor = postIt.style.backgroundColor;

        // Create the text
        let textArea = document.createElement('textarea');

        // Define the content
        textArea.value = postItText.value;
        textArea.style.color = postItText.style.color;

        // Define css class name and id
        textArea.className = 'post-it-area-content post-it-area-text';
        textArea.id = `post-it-area-text_${postItRow}_${postItColumn}`;

        postItTopArea.appendChild(textArea);

        // Display the buttons according to the user permissions
        displayButtons(true, true);
        if (canEdit)
            isPostItOnwer(postIt);
        else
            displayButtons(false, true);

        editImageLabel.style.display = 'none';

        // Update row and column
        disableRowColumnColorArea(true);
        colorArea.value = rgbToHex(postIt.style.backgroundColor);
        rowTextArea.value = postItRow;
        columnTextArea.value = postItColumn;

        // Add event listener to the textarea for click
        textArea.addEventListener('click', function (event) {
            event.preventDefault();
        });

        textArea.readOnly = true;
    });
}


/**
 * Function to add click event listener to the post-it exit button
 */
function addExitButtonListener(event) {
    event.preventDefault();

    // Hide the post-it area
    const postItArea = document.getElementById('post-it-area');
    postItArea.style.display = 'none';

    off();

    // Reset the post-it area
    const postItTopArea = document.getElementById('post-it-top-area');
    postItTopArea.innerHTML = '';

    // Hide Buttons
    saveButton.style.display = 'none';
    cancelButton.style.display = 'none';

    // Disable row and column text areas
    disableRowColumnColorArea(true);
}


/**
 * Functions to add "fun" to the post-it
 */
let inc = 0;


function rotatePostIt() {
    let randomRotate = ["rotate(3deg)", "rotate(1deg)", "rotate(-1deg)", "rotate(-3deg)",
        "rotate(2deg)", "rotate(-2deg)", "rotate(4deg)", "rotate(-4deg)",
        "rotate(1deg)", "rotate(2deg)"];

    if (inc > randomRotate.length - 1) {
        inc = 0;
    }

    return randomRotate[inc++];
}

// Function to convert hexadecimal color to RGB format
function hexToRgb(hex) {
    // Remove the '#' character if present
    hex = hex.replace("#", "");

    // Extract the red, green, and blue components
    const r = parseInt(hex.substring(0, 2), 16);
    const g = parseInt(hex.substring(2, 4), 16);
    const b = parseInt(hex.substring(4, 6), 16);

    // Return the RGB color as an object
    return {r, g, b};
}


// Function to compare two colors
function compareColors(color1, color2) {
    // Calculate the brightness of color1
    const brightness1 = (color1.r * 299 + color1.g * 587 + color1.b * 114) / 1000;

    // Calculate the brightness of color2
    const brightness2 = (color2.r * 299 + color2.g * 587 + color2.b * 114) / 1000;

    // Compare the brightness
    if (brightness1 < brightness2) {
        // color1 is darker than color2
        return -1;
    }

    // color1 is lighter than color2
    return 1;
}


function rgbToHex(rgb) {
    // Get the individual RGB components
    const components = rgb.match(/\d+/g);

    // Convert each component to hexadecimal
    const hexComponents = components.map(component => {
        const hex = parseInt(component).toString(16);
        return hex.length === 1 ? "0" + hex : hex; // Add leading zero if needed
    });

    // Join the hexadecimal components
    return "#" + hexComponents.join("");
}