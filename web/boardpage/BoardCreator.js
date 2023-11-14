/**
 * Global variable that lets us know if the board is being loaded for the first time
 * @type {boolean}
 */
let isFirstLoad = true;

class BoardCreator {
    constructor(nColumns, nRows, columnNames, rowNames, boardId, title) {
        this.nColumns = nColumns;
        this.nRows = nRows;
        this.columnNames = columnNames;
        this.rowNames = rowNames;
        this.boardId = boardId;
        this.title = title;
    }

    createBoardContent() {
        // Define the title of the page
        document.title = this.title;

        // Create the board scrollable container
        let boardScrollContainer = document.createElement('div');
        boardScrollContainer.className = 'board-scrl-container';

        // Append the board scrollable container to the board container
        let boardContainer = document.querySelector('.board-container');
        boardContainer.appendChild(boardScrollContainer);

        // Create the board
        let board = document.createElement('div');
        board.className = 'board';
        board.setAttribute('data-board-id', this.boardId);

        // Append a preloader to the board
        let loadingOverlay = document.createElement('div');
        loadingOverlay.id = 'loading-overlay';
        board.appendChild(loadingOverlay);

        let loadingContent = document.createElement('div');
        loadingContent.id = 'loading-overlay-content';
        loadingOverlay.appendChild(loadingContent);

        // Create the top-left corner cell
        let corner = document.createElement('div');
        corner.className = 'cell column-title row-title';
        corner.setAttribute('data-cell-id', '0_0');
        board.appendChild(corner);

        // Create column titles
        for (let j = 0; j < this.nColumns; j++) {
            let columnTitle = document.createElement('div');
            columnTitle.className = 'cell column-title';
            columnTitle.textContent = this.columnNames[j].boardLineTitle.boardLineTitle;
            columnTitle.setAttribute('data-cell-id', `0_${j}`);
            board.appendChild(columnTitle);
        }

        // Create cells and row titles
        for (let row = 0; row < this.nRows; row++) {
            // Create row title
            let rowTitle = document.createElement('div');
            rowTitle.className = 'row-title';
            rowTitle.textContent = this.rowNames[row].boardLineTitle.boardLineTitle;
            rowTitle.setAttribute('data-cell-id', `${row}_0`);
            board.appendChild(rowTitle);

            // Create cells for each column
            for (let column = 0; column < this.nColumns; column++) {
                // Create cell
                let cell = document.createElement('div');
                cell.className = 'cell';
                cell.id = `cell_${row}_${column}`;

                // Verify if the user has write permissions
                if (canEdit === true) {
                    // Call the function to create cell buttons
                    createCellButtons(cell, row, column);
                }

                board.appendChild(cell);
            }
        }

        // Define the size of the board
        defineBoardSize(this.nRows, this.nColumns);

        // Initialize the board post-its
        initializeBoardPostIts();

        // Set CSS grid-template-columns property
        board.style.gridTemplateColumns = `auto repeat(${this.nColumns}, 1fr)`;

        // Append the board to the container
        boardScrollContainer.appendChild(board);
    }

    loadPostIts() {
        // Make a request to the server to get the board post-its
        const postItCreationRequest = new XMLHttpRequest();

        // Define the behavior of the request when it loads
        postItCreationRequest.onload = function () {
            if (postItCreationRequest.status === 200) {
                const postItArray = JSON.parse(postItCreationRequest.responseText);

                postItArray.forEach(postIt => {
                    addPostIt(postIt.boardCell.row.boardLineNumber, postIt.boardCell.column.boardLineNumber,
                        postIt);
                });

                // Hide the preloader after the first time the board is loaded
                if (isFirstLoad) {
                    hidePreloader();
                    isFirstLoad = false;
                }
            }
        }

        // Get the board ID from the URL
        const boardId = window.location.href.split('/').pop();

        // Open the request
        postItCreationRequest.open('POST', `/postit/find`, true);
        postItCreationRequest.setRequestHeader("Content-Type", "application/json");

        // Set the authorization header
        const token = getTokenCookie();
        if (token) {
            postItCreationRequest.setRequestHeader('Authorization', token);
        }

        // Send the request
        postItCreationRequest.send(boardId);

    }

    createBoardHeader() {
        const boardHeader = document.createElement('div');
        boardHeader.className = 'board-header';

        const boardTitle = document.createElement('h1');
        boardTitle.className = 'board-title';
        boardTitle.textContent = this.title;

        boardHeader.appendChild(boardTitle);

        const boardContainer = document.querySelector('.board-container');
        boardContainer.appendChild(boardHeader);
    }

    createBoardContainer() {
        const boardContainer = document.createElement('div');
        boardContainer.className = 'board-container';

        // Append to main container
        const mainContainer = document.querySelector('.main-container');
        mainContainer.appendChild(boardContainer);
    }

    async createBoard() {
        try {
            canEdit = await userPermissionsRequest();
            this.createBoardContainer();
            this.createBoardHeader();
            this.createBoardContent();
            this.loadPostIts();
        } catch (error) {
            console.error('Error:', error);
        }
    }

}