function toggleSidebar() {
    let sidebar = document.querySelector('.sidebar');
    let buttonTexts = document.querySelectorAll('.button-text');

    sidebar.classList.toggle('sidebar-collapsed');

    // Check if the sidebar is collapsed and hide the texts
    if (sidebar.classList.contains('sidebar-collapsed')) {
        buttonTexts.forEach(function (buttonText) {
            buttonText.style.display = 'none';
        });
    } else {
        // Show the texts after a timeout
        setTimeout(function () {
            buttonTexts.forEach(function (buttonText) {
                buttonText.style.display = 'inline-block';
            });
        }, 250); // Adjust the timeout delay as needed
    }
}

function loadBoard() {
    // Make an AJAX request to fetch the latest version of the board
    const boardRequest = new XMLHttpRequest();

    boardRequest.onload = function () {
        if (boardRequest.status === 200) {
            const board = JSON.parse(boardRequest.responseText);

            if (board.boardState === "ARCHIVED") {
                // Redirect to archived board page
                window.location.href = window.location.origin + '/_archived';
            }

            // Check if the board is already created
            const boardContainer = document.querySelector('.board-container');
            if (!boardContainer) {
                const boardCreator = new BoardCreator(
                    board.columns.length,
                    board.rows.length,
                    board.columns,
                    board.rows,
                    board.id,
                    board.boardTitle.boardTitle
                )

                boardCreator.createBoard();

            } else {
                loadUpdatedPostIts();
            }
        }
    }

    // Get the board ID from the URL
    const boardId = window.location.href.split('/').pop();

    // Open the request
    boardRequest.open('GET', `/boards/${boardId}`, true);

    // Set the authorization header
    const token = getTokenCookie();
    if (token) {
        boardRequest.setRequestHeader('Authorization', token);
    }

    boardRequest.send();
}

/**
 * Global variables to keep track of the size of the board
 */
let numberRows;
let numberColumns;

/**
 * Function to define the number of rows and columns of the board
 */
function defineBoardSize(rows, columns) {
    numberRows = rows;
    numberColumns = columns;
}

/**
 * Global variable to keep track of the post-its on the board
 * @type {*[]}
 */
let boardPostIts = [];


/**
 * Function to initialize the boardPostIts array
 */
function initializeBoardPostIts() {
    for (let row = 0; row < numberRows; row++) {
        let rowArray = [];

        for (let column = 0; column < numberColumns; column++) {
            rowArray.push(null);
        }

        boardPostIts.push(rowArray);
    }
}

/**
 * Function to define the row and column of the post-it
 */
function addPostIt(row, column, postIt) {
    if (boardPostIts[row][column] !== null) {
        return;
    }

    boardPostIts[row][column] = postIt;

    createPostIt(row, column, postIt.postItContent.content, postIt.postItColor.color, postIt.boardPostItCreator.id);
}

/**
 * Function to check if a post-it exists in a given row and column
 * @param row
 * @param column
 * @returns {boolean}
 */
function hasPostIt(row, column) {
    return boardPostIts[row][column] !== null;
}

function postItTimestamp(row, column) {
    return boardPostIts[row][column].postItTimestamp.timestamp;
}

/**
 * Function to load the updated post-its.
 * Only those who were edited will be updated.
 */
function loadUpdatedPostIts() {

    // Make an AJAX request to fetch the latest version of the post-its
    const updatedPostItsRequest = new XMLHttpRequest();

    updatedPostItsRequest.onload = function () {
        if (updatedPostItsRequest.status === 200) {
            const postItArray = JSON.parse(updatedPostItsRequest.responseText);

            postItArray.forEach(postIt => {
                // Post-it row and column
                const postItRow = postIt.boardCell.row.boardLineNumber;
                const postItColumn = postIt.boardCell.column.boardLineNumber;


                // Edit the post-it
                if (hasPostIt(postItRow, postItColumn)) {

                    if (postItTimestamp(postItRow, postItColumn) !== postIt.postItTimestamp.timestamp) {
                        // Remove the post-it if it was edited
                        deletePostIt(postItRow, postItColumn);
                        addPostIt(postItRow, postItColumn, postIt);
                    }

                } else {

                    const postItCoordinates = postIt.previousCoordinates.split('_');

                    if (postItCoordinates[0] !== postItRow || postItCoordinates[1] !== postItColumn) {
                        deletePostIt(postItCoordinates[0], postItCoordinates[1]);
                    }

                    // Add the post-it
                    addPostIt(postItRow, postItColumn, postIt);
                }
            });
        }
    }

    // Get the board ID from the URL
    const boardId = window.location.href.split('/').pop();

    // Open the request
    updatedPostItsRequest.open('POST', `/postit/upt`, true);
    updatedPostItsRequest.setRequestHeader("Content-Type", "application/json");

    // Set the authorization header
    const token = getTokenCookie();
    if (token) {
        updatedPostItsRequest.setRequestHeader('Authorization', token);
    }

    // Send the request
    updatedPostItsRequest.send(boardId);
}

function loadBoardPage() {
    if (checkIfLoggedUser()) {
        loadBoard();
        setTimeout(loadBoardPage, 1500);
    }
}

function checkIfUserIsTheOwner() {
    // Make an XMLHttpRequest to check if the user is the owner of the board
    const isOwnerRequest = new XMLHttpRequest();

    isOwnerRequest.onload = function () {
        if (isOwnerRequest.status === 200) {
            createOwnerSidebarButtons();
        }
    }

    // Get the board ID from the URL
    const boardId = window.location.href.split('/').pop();

    // Open the request
    isOwnerRequest.open('GET', `/boardpermissions/${boardId}/isOwner`, true);

    // Set the authorization header
    const token = getTokenCookie();
    if (token) {
        isOwnerRequest.setRequestHeader('Authorization', token);
    }

    isOwnerRequest.send();
}

function createOwnerSidebarButtons() {
    const sidebarContainer = document.querySelector('.sidebar-content');

    // Create the first div
    const shareDiv = createSidebarButton('sidebar/share-icon.png', 'ShareIcon', 'Share Board');
    shareDiv.addEventListener('click', showAvailableUsers)
    sidebarContainer.appendChild(shareDiv);

    // Create the third div
    const archiveDiv = createSidebarButton('sidebar/archive-icon.png', 'ArchiveIcon', 'Archive Board');
    archiveDiv.addEventListener('click', archiveBoard)
    sidebarContainer.appendChild(archiveDiv);
}

function createSidebarButton(iconSrc, altText, buttonText) {
    // Create the main div element
    const div = document.createElement('div');
    div.className = 'sidebar-button';

    // Create the image element
    const img = document.createElement('img');
    img.className = 'button-icon';
    img.src = iconSrc;
    img.alt = altText;

    // Create the span element
    const span = document.createElement('span');
    span.className = 'button-text';
    span.textContent = buttonText;

    // Append the image and span elements to the main div element
    div.appendChild(img);
    div.appendChild(span);

    // Return the created div element
    return div;
}

function checkIfLoggedUser() {
    const token = getTokenCookie();
    if (!token) {
        // Redirect to login page
        window.location.href = window.location.origin;
        return false;
    } else {
        setUserName()
        return true;
    }
}

function boardsSidebarButtonAction() {
    // Go to my boards page
    window.location.href = window.location.origin + '/_myboards';
}

function archiveBoard() {
    event.preventDefault();

    const confirmationModal = document.getElementById('confirmationModal');
    const confirmArchiveBtn = document.getElementById('confirmArchiveBtn');
    const cancelArchiveBtn = document.getElementById('cancelArchiveBtn');

    // Show the confirmation modal
    confirmationModal.style.display = 'block';

    // Handle click on the "Archive" button
    confirmArchiveBtn.onclick = function () {
        confirmationModal.style.display = 'none';
        performArchiveAction(); // Call the function to archive the board
    };

    // Handle click on the "Cancel" button or outside the modal
    cancelArchiveBtn.onclick = function () {
        confirmationModal.style.display = 'none';
    };
}

function performArchiveAction() {
    const archiveRequest = new XMLHttpRequest();

    archiveRequest.onload = function () {
        if (archiveRequest.status === 200) {
            window.location.href = window.location.origin + '/_archived';
        } else {
            alert("Board cannot be archived");
        }
    };

    // Get the board ID from the URL
    const boardId = window.location.href.split('/').pop();

    archiveRequest.open('POST', '/archiveboard', true);

    const token = getTokenCookie();
    // Set the authorization header
    if (token) {
        archiveRequest.setRequestHeader('Authorization', token);
    }

    // Send the request
    archiveRequest.send(boardId);
}


/* BOARD HISTORY */


function loadHistoryHeader(container) {
    const header = document.createElement('div');
    header.className = 'board-history-header';

    /* CLOSE BUTTON */

    const closeButton = document.createElement('button');
    closeButton.className = 'history-close-button';
    closeButton.innerText = 'X';

    closeButton.addEventListener('click', function () {
        document.body.removeChild(container);
        off();
    });

    header.appendChild(closeButton);


    /* TITLE */

    const title = document.createElement('h1');
    title.className = 'history-title';
    title.innerText = 'Board History';

    header.appendChild(title);

    container.appendChild(header);

}

function getLogDateText(element) {

    // Split the string by the "T" character
    const [date, time] = element.split("T");

    // Extract the hours, minutes, and seconds from the time string
    const [hours, minutes, seconds] = time.split(":");

    // Remove the fractional part of the seconds value
    const wholeSeconds = seconds.split(".")[0];

    // Create a new string with the desired format
    return `${date} ${hours}:${minutes}:${wholeSeconds}`;

}

function createHistoryTableHeader(div, table) {

    // Create the table header
    const headerRow = document.createElement('tr');

    const headerColumn1 = document.createElement('th');
    headerColumn1.className = 'history-header-title';
    headerColumn1.innerText = 'Action';


    const headerColumn2 = document.createElement('th');
    headerColumn2.className = 'history-header-title';
    headerColumn2.innerText = 'Date';

    const headerColumn3 = document.createElement('th');
    headerColumn3.className = 'history-header-title';
    headerColumn3.innerText = 'User';

    headerRow.appendChild(headerColumn1);
    headerRow.appendChild(headerColumn2);
    headerRow.appendChild(headerColumn3);

    table.appendChild(headerRow);
}

function logPostItCreation(element, table) {

    // Create post it row
    const row = document.createElement('tr');
    row.value = element.id;

    const column = document.createElement('td');

    const icon = document.createElement('i');
    icon.className = 'material-icons-outlined';
    icon.textContent = 'post_add';

    const label = document.createElement('span');
    label.className = 'history-label';
    label.innerText = 'Created a Post-it';


    column.appendChild(icon);
    column.appendChild(label);


    const column2 = document.createElement('td');
    column2.innerText = getLogDateText(element.timestamp.timestamp);


    const column3 = document.createElement('td');
    column3.innerText = element.user.email;

    row.appendChild(column);
    row.appendChild(column2);
    row.appendChild(column3);

    table.appendChild(row);

}

function loadChangeHeader(container, overlay) {
    const header = document.createElement('div');
    header.className = 'change-header';

    /* CLOSE BUTTON */

    const closeButton = document.createElement('button');
    closeButton.className = 'change-close-button';
    closeButton.innerText = 'X';

    closeButton.addEventListener('click', function () {
        document.body.removeChild(container);
        document.body.removeChild(overlay);

    });

    header.appendChild(closeButton);



    container.appendChild(header);

}

function loadContentDifference(element, content) {

    const cdif = document.createElement('div');
    cdif.className = 'content-difference';
    cdif.style.display = 'block';

    // create the old content wrapper
    const oldContentWrapper = document.createElement('div');
    oldContentWrapper.className = 'change-wrapper';
    cdif.appendChild(oldContentWrapper);

    // create the old content label
    const oldContentLabel = document.createElement('span');
    oldContentLabel.className = 'content-label';
    oldContentLabel.innerText = 'Old content:';
    oldContentWrapper.appendChild(oldContentLabel);

    // create the old content textarea
    const oldContentBox = document.createElement('textarea');
    oldContentBox.className = 'content-box';
    oldContentBox.value = element.oldContent;
    oldContentBox.readOnly = true;
    oldContentWrapper.appendChild(oldContentBox);

    // create the new content wrapper
    const newContentWrapper = document.createElement('div');
    newContentWrapper.className = 'change-wrapper';
    cdif.appendChild(newContentWrapper);

    // create the new content label
    const newContentLabel = document.createElement('span');
    newContentLabel.className = 'content-label';
    newContentLabel.innerText = 'New content:';
    newContentWrapper.appendChild(newContentLabel);

    // create the new content textarea
    const newContentBox = document.createElement('textarea');
    newContentBox.className = 'content-box';
    newContentBox.value = element.newContent;
    newContentBox.readOnly = true;
    newContentWrapper.appendChild(newContentBox);


    content.appendChild(cdif);
}


function loadPositionDifference(element, content) {

    const pdif = document.createElement('div');
    pdif.className = 'position-difference';
    pdif.style.display = 'inline-block';

    // create the old position wrapper
    const oldPositionWrapper = document.createElement('div');
    oldPositionWrapper.className = 'change-wrapper';


    const label1 = document.createElement('span');
    label1.className = 'change-label';

    // create the old column label
    const oldPositionLabel = document.createElement('span');
    oldPositionLabel.className = 'position-label';
    oldPositionLabel.innerText = 'Old Column: ';
    label1.appendChild(oldPositionLabel);

    const oldColumnNumber = document.createElement('span');
    oldColumnNumber.className = 'position-number';
    oldColumnNumber.innerText = element.oldColumn;
    label1.appendChild(oldColumnNumber);

    oldPositionWrapper.appendChild(label1);

    const label2 = document.createElement('span');
    label2.className = 'change-label';

    // create the old row label
    const oldRowLabel = document.createElement('span');
    oldRowLabel.className = 'position-label';
    oldRowLabel.innerText = '\n\nOld Row: ';
    label2.appendChild(oldRowLabel);

    const oldRowNumber = document.createElement('span');
    oldRowNumber.className = 'position-number';
    oldRowNumber.innerText = element.oldRow;
    label2.appendChild(oldRowNumber);

    oldPositionWrapper.appendChild(label2);

    pdif.appendChild(oldPositionWrapper);


    // create the new position wrapper
    const newPositionWrapper = document.createElement('div');
    newPositionWrapper.className = 'change-wrapper';

    const label3 = document.createElement('span');
    label3.className = 'change-label';

    // create the new column label
    const newColumnLabel = document.createElement('span');
    newColumnLabel.className = 'position-label';
    newColumnLabel.innerText = 'New Column: ';
    label3.appendChild(newColumnLabel);

    const newColumnNumber = document.createElement('span');
    newColumnNumber.className = 'position-number';
    newColumnNumber.innerText = element.newColumn;
    label3.appendChild(newColumnNumber);

    newPositionWrapper.appendChild(label3);

    const label4 = document.createElement('span');
    label4.className = 'change-label';

    // create the new row label
    const newRowLabel = document.createElement('span');
    newRowLabel.className = 'position-label';
    newRowLabel.innerText = '\n\nNew Row: ';
    label4.appendChild(newRowLabel);

    const newRowNumber = document.createElement('span');
    newRowNumber.className = 'position-number';
    newRowNumber.innerText = element.newRow;
    label4.appendChild(newRowNumber);

    newPositionWrapper.appendChild(label4);

    pdif.appendChild(newPositionWrapper);

    content.appendChild(pdif);

}

function loadChangeContent(element, container) {
    const content = document.createElement('div');
    content.className = 'change-content-container';

    loadContentDifference(element, content);
    loadPositionDifference(element, content);

    container.appendChild(content);
}

function showChangeBox(element){


    // create the overlay element
    const overlay = document.createElement('div');
    overlay.style.position = 'fixed';
    overlay.style.top = '0';
    overlay.style.left = '0';
    overlay.style.width = '100%';
    overlay.style.height = '100%';
    overlay.style.backgroundColor = 'rgba(0, 0, 0, 0.5)';
    overlay.style.zIndex = '9998';
    document.body.appendChild(overlay);



    const container = document.createElement('div');
    container.className = 'change-box-container';
    // add your custom code here to set the size, position, and content of the container
    document.body.appendChild(container);

    loadChangeHeader(container, overlay);

    loadChangeContent(element, container);




}

function logPostItUpdate(element, table) {

    // Update a post it row
    const row = document.createElement('tr');
    row.value = element.id;

    const column = document.createElement('td');

    const icon = document.createElement('i');
    icon.className = 'material-icons-outlined';
    icon.textContent = 'edit';

    const label = document.createElement('a');
    label.className = 'history-label-update';
    label.innerText = 'Edited a Post-it';

    label.addEventListener('click', function(event) {
        // prevent the default behavior of the anchor tag
        event.preventDefault();
        // show the change that was made
        showChangeBox(element);
    });


    column.appendChild(icon);
    column.appendChild(label);


    const column2 = document.createElement('td');
    column2.innerText = getLogDateText(element.timestamp.timestamp);


    const column3 = document.createElement('td');
    column3.innerText = element.user.email;

    row.appendChild(column);
    row.appendChild(column2);
    row.appendChild(column3);

    table.appendChild(row);
}

function logPostItUndo(element, table) {

    // Undo action row
    const row = document.createElement('tr');
    row.value = element.id;

    const column = document.createElement('td');

    const icon = document.createElement('i');
    icon.className = 'material-icons-outlined';
    icon.textContent = 'restore';

    const label = document.createElement('span');
    label.className = 'history-label';
    label.innerText = 'Undo a Post-it';


    column.appendChild(icon);
    column.appendChild(label);


    const column2 = document.createElement('td');
    column2.innerText = getLogDateText(element.timestamp.timestamp);


    const column3 = document.createElement('td');
    column3.innerText = element.user.email;

    row.appendChild(column);
    row.appendChild(column2);
    row.appendChild(column3);

    table.appendChild(row);

}

function logPostItDeletion(element, table) {

    // Delete a post it row
    const row = document.createElement('tr');
    row.value = element.id;

    const column = document.createElement('td');

    const icon = document.createElement('i');
    icon.className = 'material-icons-outlined';
    icon.textContent = 'delete';

    const label = document.createElement('span');
    label.className = 'history-label';
    label.innerText = 'Deleted a Post-it';


    column.appendChild(icon);
    column.appendChild(label);


    const column2 = document.createElement('td');
    column2.innerText = getLogDateText(element.timestamp.timestamp);


    const column3 = document.createElement('td');
    column3.innerText = element.user.email;

    row.appendChild(column);
    row.appendChild(column2);
    row.appendChild(column3);

    table.appendChild(row);
}

function logBoardCreation(element, table) {
    // Create post it row
    const row = document.createElement('tr');
    row.value = element.id;

    const column = document.createElement('td');

    const icon = document.createElement('i');
    icon.className = 'material-icons-outlined';
    icon.textContent = 'dashboard_customize';

    const label = document.createElement('span');
    label.className = 'history-label';
    label.innerText = 'Created the Board';


    column.appendChild(icon);
    column.appendChild(label);


    const column2 = document.createElement('td');
    column2.innerText = getLogDateText(element.timestamp.timestamp);


    const column3 = document.createElement('td');
    column3.innerText = element.user.email;

    row.appendChild(column);
    row.appendChild(column2);
    row.appendChild(column3);

    table.appendChild(row);
}

function sortHistoryTable(table) {
    // Create sort buttons
    const ths = table.querySelectorAll('th'); // select all th elements in the table
    let currentColumnIndex = 1; // default sort by second column
    let currentSortOrder = 'descending'; // default sort order is descending

    ths.forEach((th, columnIndex) => {
        const indicatorIcon = document.createElement('i');
        indicatorIcon.className = 'material-icons-outlined sorting-icon';

        th.addEventListener('click', () => {
            if (columnIndex === currentColumnIndex) {
                currentSortOrder = currentSortOrder === 'ascending' ? 'descending' : 'ascending';
            } else {
                currentColumnIndex = columnIndex;
                currentSortOrder = 'ascending';
            }

            removeSortingIcons();

            const sortIndicator = currentSortOrder === 'ascending' ? 'arrow_drop_up' : 'arrow_drop_down';
            indicatorIcon.textContent = sortIndicator;
            th.appendChild(indicatorIcon);

            sortTable();
        });
    });

    function removeSortingIcons() {
        // Remove sorting indicator icons from all headers
        ths.forEach(header => {
            const existingIcon = header.querySelector('.sorting-icon');
            if (existingIcon) {
                header.removeChild(existingIcon);
            }
        });
    }

    function sortTable() {
        const rows = Array.from(table.querySelectorAll('tr')).slice(1); // select all tr elements in the table except for the first one (the header row)
        rows.sort((a, b) => {
            let aValue, bValue;
            if (currentColumnIndex === 0) {
                // Sorting based on inner text of the first column
                aValue = a.children[currentColumnIndex].querySelector('span').textContent;
                bValue = b.children[currentColumnIndex].querySelector('span').textContent;
            } else {
                // Sorting based on other columns' text content
                aValue = a.children[currentColumnIndex].textContent;
                bValue = b.children[currentColumnIndex].textContent;
            }

            return currentSortOrder === 'ascending' ? aValue.localeCompare(bValue) : bValue.localeCompare(aValue);
        });
        rows.forEach(row => table.appendChild(row));
    }

    removeSortingIcons(); // remove any existing sorting icons
    sortTable(); // initial sort
}

function createHistoryTable(history, container) {

    const div = document.createElement('div');
    div.className = 'board-history-table-container';

    // Create a table element
    const table = document.createElement('table');
    table.className = 'board-history-table';
    table.id = 'historyTable';

    createHistoryTableHeader(div, table);

    history.forEach(element => {
        switch (element.operation) {
            case 'CREATE_POSTIT':
                logPostItCreation(element, table);
                break;
            case 'UPDATE_POSTIT':
                logPostItUpdate(element, table);
                break;
            case 'UNDO_POSTIT':
                logPostItUndo(element, table);
                break;
            case 'DELETE_POSTIT':
                logPostItDeletion(element, table);
                break;
            case 'CREATE_BOARD':
                logBoardCreation(element, table);
                break;
            default:
                console.log('Log type not recognized');
                break;
        }
    });

    sortHistoryTable(table);


    div.appendChild(table);

    container.appendChild(div);

}

function loadHistoryTable(container) {

    // Make an AJAX request to fetch the latest version of the history log
    const historyRequest = new XMLHttpRequest();

    historyRequest.onload = function () {
        if (historyRequest.status === 200) {
            const history = JSON.parse(historyRequest.responseText);

            // Get the current table size if exist
            let table = document.getElementById("historyTable");

            if (table) {
                const existingOptions = Array.from(table.children);

                if (history.length > table.rows.length - 1) {
                    // Check if there are new logs
                    history.forEach(element => {
                        // Check if the log is already in the table
                        const existingOption = existingOptions.find(option => {
                            return option.value === element.id;
                        });

                        if (!existingOption) {

                            displayNotification('info', 'Log updated!');

                            switch (element.operation) {
                                case 'CREATE_POSTIT':
                                    logPostItCreation(element, table);
                                    break;
                                case 'UPDATE_POSTIT':
                                    logPostItUpdate(element, table);
                                    break;
                                case 'UNDO_POSTIT':
                                    logPostItUndo(element, table);
                                    break;
                                case 'DELETE_POSTIT':
                                    logPostItDeletion(element, table);
                                    break;
                                case 'CREATE_BOARD':
                                    logBoardCreation(element, table);
                                    break;
                                default:
                                    console.log('Log type not recognized');
                                    break;
                            }

                        }

                    });

                    sortHistoryTable(table);
                }
            } else {
                createHistoryTable(history, container);
            }


        }
    }

    // Get the board ID from the URL
    const boardId = window.location.href.split('/').pop();

    historyRequest.open('GET', `/log/${boardId}`, true);

    // Set the authorization header
    const token = getTokenCookie();
    if (token) {
        historyRequest.setRequestHeader('Authorization', token);
    }

    historyRequest.send();

}

function loadBoardHistory() {

    on();

    // Create a container element
    const container = document.createElement('div');
    container.id = 'board-history-container';
    container.className = 'board-history-container';

    loadHistoryHeader(container);

    loadHistoryTable(container);


    // Append the container to the document body
    document.body.appendChild(container);

    keepUpdatingHistory();
}

function keepUpdatingHistory() {

    if (checkIfLoggedUser()) {
        let container = document.getElementById("board-history-container");
        if (container) {
            loadHistoryTable(container);

            setTimeout(keepUpdatingHistory, 3000);
        }
    }

}

// Function that manages the html elements already created regarding the "share board" feature
function showAvailableUsers() {
    event.preventDefault();

    const shareContent = document.getElementById("shareBoardModal");
    shareContent.style.display = "block";

    // Get the modal element
    const modal = document.getElementById('shareBoardModal');

// Get the exit button element
    const exitButton = document.querySelector('.exitButton');

// Function to open the modal
    function openModal() {
        modal.style.display = 'block';
    }

// Function to close the modal
    function closeModal() {
        modal.style.display = 'none';
        resetInputs(modal);
    }

    // Reset the inputs when clicking outside the modal or when the modal loses focus
    modal.addEventListener('click', function (event) {
        if (event.target === modal) {
            closeModal();
        }
    });
    modal.addEventListener('blur', closeModal);

// Add click event listener to the exit button
    exitButton.addEventListener('click', closeModal);

// Add click event listener to the modal content to prevent closing when clicking inside
    modal.querySelector('.shareModal-content').addEventListener('click', function (event) {
        event.stopPropagation();
    });

// Add click event listener to the modal element to close when clicking outside the modal
    modal.addEventListener('click', closeModal);

// Open the modal
    openModal();

    updateGetUsersToShareBoardWithRequest();

    // Get the search input element
    const searchInput = document.getElementById("searchInput");

    // Add input event listener to trigger filtering
    searchInput.addEventListener("input", filterUserList);

}

// Function to filter the search when writing in the searchbox
function filterUserList() {
    const searchInput = document.getElementById("searchInput");
    const filter = searchInput.value.toLowerCase();
    const userList = document.getElementById("userList");
    const userItems = userList.getElementsByTagName("li");

    Array.from(userItems).forEach(function (userItem) {
        const userEmail = userItem.getElementsByTagName("span")[0];
        const emailText = userEmail.textContent.toLowerCase();
        if (emailText.indexOf(filter) > -1) {
            userItem.style.display = "";
        } else {
            userItem.style.display = "none";
        }
    });
}

// Reset the inputs when closing the modal
function resetInputs(modal) {
    const searchInput = document.getElementById("searchInput");
    searchInput.value = ""; // Reset the search input value

    // Reset the checkboxes
    const checkboxes = modal.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach(function (checkbox) {
        checkbox.checked = false;
        checkbox.disabled = false;
    });

    // Show all user items
    const userItems = modal.querySelectorAll('#userList li');
    userItems.forEach(function (userItem) {
        userItem.style.display = "";
    });
}

// Variable to control the number of users in the list to make sure it gets updated in real time
let currentNumberOfUsers = 0;

// Function that sends the request and receives the response to get the users to share a board with.
function availableUsers() {
    const getUsersRequest = new XMLHttpRequest();

    getUsersRequest.onload = function () {
        if (getUsersRequest.status === 200) {
            const users = JSON.parse(getUsersRequest.responseText);
            const userList = document.getElementById('userList');
            const existingUsers = Array.from(userList.children);

            if (users.length > currentNumberOfUsers) {
                users.forEach(user => {
                    const existingUser = existingUsers.find(option => {
                        return option.value === user.id;
                    });

                    if (!existingUser) {
                        createUsersList(userList, user);
                        currentNumberOfUsers++;
                    }
                });
            } else if (users.length < currentNumberOfUsers) {
                existingUsers.forEach(option => {
                    const existingUser = users.find(user => {
                        return user.id === option.value;
                    });

                    if (!existingUser) {
                        option.remove();
                        currentNumberOfUsers--;
                    }
                });
            }
        }
    }


// Get the board ID from the URL
    const boardId = window.location.href.split('/').pop();

// Open the request
    getUsersRequest.open('GET', `/shareboard/${boardId}`, true);

// Set the authorization header
    const token = getTokenCookie();
    if (token) {
        getUsersRequest.setRequestHeader('Authorization', token);
    }

    getUsersRequest.send();
}

// Function to create the html elements for the users list
function createUsersList(userList, user) {
    const userItem = document.createElement('li');

    userItem.id = user.id;

    const userEmail = document.createElement('span');

    userEmail.textContent = user.email;

    userItem.appendChild(userEmail);

    const readLabel = document.createElement('label');

    userItem.appendChild(readLabel);

    const readInput = document.createElement('input');

    readInput.type = 'checkbox';

    readInput.id = user.id + '-read';

    readLabel.appendChild(readInput);

    const writeLabel = document.createElement('label');

    userItem.appendChild(writeLabel);

    const writeInput = document.createElement('input');

    writeInput.type = 'checkbox';

    writeInput.id = user.id + '-write';

    writeInput.onclick = function () {
        checkRead(readInput.id);
    }

    writeLabel.appendChild(writeInput);

    userList.appendChild(userItem);
}

// Function to check the read option when the write option is checked
function checkRead(elementID) {
    const read = document.getElementById(elementID);

    if (event.target.checked === false) {
        read.disabled = false;
        read.checked = false;
    } else {
        read.checked = true;
        read.disabled = true;
    }
}

// Function to repeatedly send a request to get the list of users to share the board with
function updateGetUsersToShareBoardWithRequest() {
    if (checkIfLoggedUser()) {
        // Get the board container to make sure the request is not sent while the container isn't visible
        let shareBoardContainer = document.getElementById("shareBoardModal");
        if (shareBoardContainer.style.display === "block") {
            availableUsers();

            setTimeout(updateGetUsersToShareBoardWithRequest, 5000);
        }
    }
}

function shareBoard() {
    event.preventDefault();

    const shareBoardRequest = new XMLHttpRequest();

    shareBoardRequest.onload = function () {
        if (shareBoardRequest.status === 200) {
            // Close the modal
            const modal = document.getElementById('shareBoardModal');
            modal.style.display = 'none';
            resetInputs(modal);
            displayNotification('success', 'Board shared successfully');
        } else {
            displayNotification('failure', 'Error sharing board');
        }
    }

    shareBoardRequest.open('POST', "/shareboard", true);
    shareBoardRequest.setRequestHeader('Content-Type', 'application/json');

    const token = getTokenCookie();
    if (token) {
        shareBoardRequest.setRequestHeader('Authorization', token);
    }

    // Iterate every user item and check if it's checked whether it's read or write and create a json to send in the request
    const userList = document.getElementById('userList');
    const userItems = userList.getElementsByTagName('li');
    const users = [];

    Array.from(userItems).forEach(function (userItem) {
        const readInput = userItem.getElementsByTagName('input')[0];
        const writeInput = userItem.getElementsByTagName('input')[1];

        if (readInput.checked || writeInput.checked) {
            const user = {
                userId: userItem.id,
                read: readInput.checked,
                write: writeInput.checked
            }
            users.push(user);
        }
    });

    const boardId = window.location.href.split('/').pop();

    const body = {
        boardId: boardId,
        users: users
    }

    shareBoardRequest.send(JSON.stringify(body));
}
