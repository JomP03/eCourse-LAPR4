function createBoardOption(title, imageSrc, id) {
    // Create the button element
    const boardOption = document.createElement('a');
    boardOption.className = 'board-option';
    boardOption.href = '/_boards/' + id;

    // Create the board content container
    const boardOptionContent = document.createElement('div');
    boardOptionContent.className = 'board-content';

    // Create the board option title
    const boardTitle = document.createElement('h3');
    boardTitle.className = 'board-option-title';
    boardTitle.textContent = title;

    // Create the board image
    const boardImage = document.createElement('img');
    boardImage.className = 'board-image';
    boardImage.src = imageSrc;
    boardImage.alt = 'Board ' + id + ' Image';

    // Add the board id to the board option
    boardOption.value = id;

    // Append the title and image to the board content container
    boardOptionContent.appendChild(boardTitle);
    boardOptionContent.appendChild(boardImage);

    // Append the board content to the button
    boardOption.appendChild(boardOptionContent);

    // Append the button to the boards container
    const boardsContainer = document.querySelector('.boards-container');
    // Append the board option just before the last one
    boardsContainer.insertBefore(boardOption, boardsContainer.lastElementChild);
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

let isFirstLoad = true;
let currentNumberOfBoards = 0;

function loadBoards() {
    const boardContainer = document.getElementById('boards-container');

    // Make an AJAX request to fetch the latest boards
    const boardsRequest = new XMLHttpRequest();

    boardsRequest.onload = function () {
        if (boardsRequest.status === 200) {
            const boards = JSON.parse(boardsRequest.responseText);
            const existingOptions = Array.from(boardContainer.children);

            if (boards.length > currentNumberOfBoards) {
                // Check if there are new boards
                boards.forEach(board => {
                    // Check if the board is already in the page
                    const existingOption = existingOptions.find(option => {
                        return option.value === board.id;
                    });

                    if (!existingOption) {
                        // Generate a random number between 0 and 12
                        const randomImageNumber = Math.floor(Math.random() * 13);
                        // Create the board option
                        createBoardOption(board.boardTitle.boardTitle, `/boardoptionimage/${randomImageNumber}`, board.id);
                        currentNumberOfBoards++;
                        if (!isFirstLoad) {
                            displayNotification('info', `Board ${board.boardTitle.boardTitle} is now available!`)
                        }
                    }
                });

            } else if (boards.length < currentNumberOfBoards) {
                // Check if there are boards that are deleted
                existingOptions.forEach(option => {
                    // Check if the board is still in the page
                    const existingBoard = boards.find(board => {
                        return board.id === option.value;
                    });

                    if (!existingBoard) {
                        // Remove the board option
                        option.remove();
                        // Get the board title
                        const boardTitle = option.querySelector('.board-option-title').textContent;
                        // Display a notification saying that the board X was archived
                        displayNotification('info', `Board ${boardTitle} was archived!`);
                        currentNumberOfBoards--;
                    }
                });
            }

            // Hide the preloader if it is the first load
            if (isFirstLoad) {
                hidePreloader();
                // Create the "board option" to create a new board
                const boardsContainer = document.querySelector('.boards-container');
                const createBoardOption = createBoardOptionDiv();
                // Insert in the last position
                boardsContainer.appendChild(createBoardOption);
                isFirstLoad = false;
            }
        } else {
            displayNotification('failure', 'Failed to load boards!');
        }
    }

    boardsRequest.ontimeout = function () {
        displayNotification('failure', 'Failed to load boards! Timeout exceeded!');
    }

    boardsRequest.open('GET', '/boards', true);

    const token = getTokenCookie();
    if (token) {
        boardsRequest.setRequestHeader('Authorization', token);
    }

    boardsRequest.send();
}

function reloadMyBoardsPage() {
    // Check if there is a logged user, if not redirect to login page
    if (checkIfLoggedUser()) {
        // Load the boards
        loadBoards();
        // Check for changes in the boards every 6 seconds
        setTimeout(reloadMyBoardsPage, 3000);
    }
}

function loadMyBoardsPage() {
    isFirstLoad = true;
    reloadMyBoardsPage();
}


// Create Board Option
let modalContainer;

function showCreateBoardModal() {
    //get main container
    const mainContainer = document.getElementById("boards-container");
    // Check if modal container already exists
    if (!modalContainer) {
        modalContainer = document.createElement("div");
        modalContainer.classList.add("modal");
        mainContainer.appendChild(modalContainer);

        const modalContent = document.createElement("div");
        modalContent.classList.add("modal-content");
        modalContainer.appendChild(modalContent);

        const closeButton = document.createElement("span");
        closeButton.classList.add("closeButton");
        closeButton.innerHTML = "&times;";
        modalContent.appendChild(closeButton);

        const title = document.createElement("h2");
        title.textContent = "Create Board";
        modalContent.appendChild(title);

        const titleLabel = document.createElement("label");
        titleLabel.setAttribute("for", "titleInput");
        titleLabel.textContent = "Title:";
        modalContent.appendChild(titleLabel);

        const titleInput = document.createElement("input");
        titleInput.setAttribute("type", "text");
        titleInput.setAttribute("id", "titleInput");
        modalContent.appendChild(titleInput);

        const rowsLabel = document.createElement("label");
        rowsLabel.setAttribute("for", "rowsInput");
        rowsLabel.textContent = "Number of Rows:";
        modalContent.appendChild(rowsLabel);

        const rowsInput = document.createElement("input");
        rowsInput.setAttribute("type", "number");
        rowsInput.setAttribute("id", "rowsInput");
        rowsInput.setAttribute("min", "1");
        rowsInput.setAttribute("max", "20");
        modalContent.appendChild(rowsInput);

        const columnsLabel = document.createElement("label");
        columnsLabel.setAttribute("for", "columnsInput");
        columnsLabel.textContent = "Number of Columns:";
        modalContent.appendChild(columnsLabel);

        const columnsInput = document.createElement("input");
        columnsInput.setAttribute("type", "number");
        columnsInput.setAttribute("id", "columnsInput");
        columnsInput.setAttribute("min", "1");
        columnsInput.setAttribute("max", "10");
        modalContent.appendChild(columnsInput);

        const nextButton = document.createElement("button");
        nextButton.textContent = "Next";
        nextButton.addEventListener("click", handleNextButtonClick);
        modalContent.appendChild(nextButton);

        modalContainer.addEventListener("click", function (event) {
            if (event.target === modalContainer) {
                closeModal();
            }
        });

        closeButton.addEventListener("click", closeModal);
        modalContainer.addEventListener("keydown", handleKeyPress); // Attach keydown event to modal container
    }

    openModal();
}

function handleKeyPress(event) {
    if (event.key === "Enter") {
        handleNextButtonClick();
    }
}


function openModal() {
    modalContainer.style.display = "flex";
}

function closeModal() {
    modalContainer.style.display = "none";
    resetInputs();
}

function resetInputs() {
    const titleInput = document.getElementById("titleInput");
    const rowsInput = document.getElementById("rowsInput");
    const columnsInput = document.getElementById("columnsInput");
    const rowTitleInputs = document.querySelectorAll(".row-title-input");
    const columnTitleInputs = document.querySelectorAll(".column-title-input");

    titleInput.value = "";
    rowsInput.value = "";
    columnsInput.value = "";

    rowTitleInputs.forEach(input => {
        input.value = "";
    });

    columnTitleInputs.forEach(input => {
        input.value = "";
    });
}

function displayPreLoaderInNextModal() {
    const nextModalContent = document.querySelector(".next-modal");
    const preLoader = document.createElement("div");
    // add id to preloader
    preLoader.setAttribute("id", "preloaderNextModal");
    preLoader.classList.add("preloader");
    nextModalContent.appendChild(preLoader);
    const loader = document.createElement("div");
    loader.classList.add("loader");
    preLoader.appendChild(loader);
}

function showNextModal(title, numRows, numColumns) {
    const mainContainer = document.getElementById("boards-container");
    const nextModalContainer = document.createElement("div");
    nextModalContainer.classList.add("next-modal");
    mainContainer.appendChild(nextModalContainer);

    const nextModalContent = document.createElement("div");
    nextModalContent.classList.add("next-modal-content");
    nextModalContainer.appendChild(nextModalContent);


    const backButton = document.createElement("button");
    backButton.classList.add("backButton");
    backButton.setAttribute("id", "backButton");
    backButton.addEventListener("click", function () {
        mainContainer.removeChild(nextModalContainer);
        openModal();
    });
    nextModalContent.appendChild(backButton);

    const icon = document.createElement('i');
    icon.className = 'material-icons-outlined';
    icon.textContent = 'arrow_back';
    backButton.appendChild(icon);

    const titleElement = document.createElement("h2");
    titleElement.textContent = title;
    nextModalContent.appendChild(titleElement);

    const columnsContainer = document.createElement("div");
    columnsContainer.classList.add("columns-container");
    nextModalContent.appendChild(columnsContainer);

    const rowTitlesColumn = document.createElement("div");
    rowTitlesColumn.classList.add("column");
    columnsContainer.appendChild(rowTitlesColumn);

    const rowTitlesLabel = document.createElement("label");
    rowTitlesLabel.classList.add("column-title");
    rowTitlesLabel.textContent = "Row Titles";
    rowTitlesColumn.appendChild(rowTitlesLabel);

    const columnTitlesColumn = document.createElement("div");
    columnTitlesColumn.classList.add("column");
    columnsContainer.appendChild(columnTitlesColumn);

    const columnTitlesLabel = document.createElement("label");
    columnTitlesLabel.classList.add("column-title");
    columnTitlesLabel.textContent = "Column Titles";
    columnTitlesColumn.appendChild(columnTitlesLabel);

    for (let i = 0; i < numRows; i++) {
        const rowTextBox = document.createElement("input");
        rowTextBox.setAttribute("type", "text");
        rowTextBox.classList.add("text-box");
        rowTextBox.setAttribute("id", "rowTextBox" + i);
        rowTextBox.placeholder = "Row " + (i + 1);
        rowTitlesColumn.appendChild(rowTextBox);
    }

    for (let i = 0; i < numColumns; i++) {
        const columnTextBox = document.createElement("input");
        columnTextBox.setAttribute("type", "text");
        columnTextBox.classList.add("text-box");
        columnTextBox.setAttribute("id", "columnTextBox" + i);
        columnTextBox.placeholder = "Column " + (i + 1);
        columnTitlesColumn.appendChild(columnTextBox);
    }

    const createButton = document.createElement("button");
    createButton.textContent = "Create";
    createButton.classList.add("create-button");
    backButton.setAttribute("id", "createButton");
    createButton.addEventListener("click", function () {
        createBoard();
        displayPreLoaderInNextModal();
    });
    nextModalContent.appendChild(createButton);

    nextModalContainer.addEventListener("click", function (event) {
        if (event.target === nextModalContainer) {
            showConfirmationBox();
        }
    });

    const closeButton = document.createElement("span");
    closeButton.classList.add("closeButton");
    closeButton.innerHTML = "&times;";
    nextModalContent.appendChild(closeButton);
    closeButton.addEventListener("click", function () {
        showConfirmationBox();
    });

    function showConfirmationBox() {
        const confirmationModal = document.getElementById("confirmationModal");
        const cancelCreationBtn = document.getElementById("cancelCreationBtn");
        const continueCreationBtn = document.getElementById("continueCreationBtn");

        // Hide the nextModal
        nextModalContainer.style.display = "none";

        // Show the confirmation modal
        confirmationModal.style.display = "flex";

        // Handle click on the "Yes" button
        cancelCreationBtn.onclick = function () {
            mainContainer.removeChild(nextModalContainer);
            closeModal();
            confirmationModal.style.display = "none";
        };

        // Handle click on the "No" button
        continueCreationBtn.onclick = function () {
            confirmationModal.style.display = "none";
            nextModalContainer.style.display = "flex";
        };

        // Handle click outside the modal
        window.onclick = function (event) {
            if (
                event.target === confirmationModal ||
                event.target === nextModalContainer
            ) {
                showConfirmationBox();
            }
        };
    }
}

function handleNextButtonClick() {
    const titleInput = document.getElementById("titleInput");
    const rowsInput = document.getElementById("rowsInput");
    const columnsInput = document.getElementById("columnsInput");

    const title = titleInput.value;
    const numRows = parseInt(rowsInput.value);
    const numColumns = parseInt(columnsInput.value);

    if (title && numRows && numColumns) {
        if ((numRows < 1 || numRows > 20) && (numColumns < 1 || numColumns > 10) && title.length > 23) {
            displayNotification("failure", "Invalid Number of Rows! (1-20)");
            displayNotification("failure", "Invalid Number of Columns! (1-10)");
            displayNotification("failure", "Title must have 23 maximum characters!");
            return;
        } else if ((numRows < 1 || numRows > 20) && (numColumns < 1 || numColumns > 10)) {
            displayNotification("failure", "Rows (1-20), Columns (1-10) characters!");
            return;
        } else if (numRows < 1 || numRows > 20) {
            displayNotification("failure", "Invalid Number of Rows! (1-20)");
            return;
        } else if (numColumns < 1 || numColumns > 10) {
            displayNotification("failure", "Invalid Number of Columns! (1-10)");
            return;
        } else if (title.length < 1 || title.length > 23) {
            displayNotification("failure", "Title must have 23 maximum characters!");
            return;
        }
        modalContainer.style.display = "none";
        showNextModal(title, numRows, numColumns);
    } else {
        displayNotification("failure", "All fields are required!");
    }
}

function removePreloaderFromNextModal() {
    const nextModalContent = document.querySelector(".next-modal");
    const preLoader = document.getElementById("preloaderNextModal");
    nextModalContent.removeChild(preLoader);
}

function createBoard() {
    const createBoardRequest = new XMLHttpRequest();

    createBoardRequest.onload = function () {
        if (createBoardRequest.status === 200) {
            const boardId = JSON.parse(createBoardRequest.response);
            window.location.href = `/_boards/${boardId}`;
        } else if (createBoardRequest.status === 400) {
            removePreloaderFromNextModal()
            displayNotification("failure", createBoardRequest.response);
        } else if (createBoardRequest.status === 500) {
            removePreloaderFromNextModal()
            displayNotification("failure", "Sorry! Please try again later");
        }
    }

    createBoardRequest.open("POST", "/boards", true);
    createBoardRequest.setRequestHeader("Content-Type", "application/json");

    const token = getTokenCookie();
    if (token) {
        createBoardRequest.setRequestHeader('Authorization', token);
    }

    const titleInput = document.getElementById("titleInput");
    const numRowsInput = document.getElementById("rowsInput");
    const numColumnsInput = document.getElementById("columnsInput");

    const numRows = parseInt(numRowsInput.value);
    const numColumns = parseInt(numColumnsInput.value);

    // Arrays to store the inputted info for the board
    const rows = [];
    const columns = [];

    for (let i = 0; i < numRows; i++) {
        const rowTextBox = document.getElementById("rowTextBox" + i);
        rows.push(rowTextBox.value);
    }

    for (let i = 0; i < numColumns; i++) {
        const columnTextBox = document.getElementById("columnTextBox" + i);
        columns.push(columnTextBox.value);
    }

    //  JSON object that contains the board inputted info
    const boardInfo = {
        boardTitle: titleInput.value,
        boardRows: rows,
        boardColumns: columns
    };

    createBoardRequest.send(JSON.stringify(boardInfo));
}

function createBoardOptionDiv() {
    // Create elements
    const div = document.createElement('div');
    div.className = 'board-option create-option';
    div.onclick = showCreateBoardModal;

    const a = document.createElement('a');
    a.className = 'board-content';

    const h3 = document.createElement('h3');
    h3.className = 'board-option-title';
    h3.textContent = 'Create Board';

    const button = document.createElement('button');
    button.className = 'plus-button';
    button.onclick = showCreateBoardModal;

    const i = document.createElement('i');
    i.className = 'fas fa-plus';

    // Build the structure
    button.appendChild(i);
    a.appendChild(h3);
    a.appendChild(button);
    div.appendChild(a);

    return div;
}
