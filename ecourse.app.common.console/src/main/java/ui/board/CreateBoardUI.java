package ui.board;

import appsettings.Application;
import boardlogmanagement.application.BoardLogFactory;
import boardlogmanagement.application.BoardLogger;
import boardmanagement.application.CreateBoardController;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.domain.ECourseUser;
import persistence.PersistenceContext;
import ui.components.AbstractUI;

import ui.components.Console;
import ui.components.Sleeper;

import java.util.ArrayList;
import java.util.List;

public class CreateBoardUI extends AbstractUI {

    private final CreateBoardController createBoardController =
            new CreateBoardController(PersistenceContext.repositories().boards(),
                    new UserSessionService(PersistenceContext.repositories().eCourseUsers()),
                    new BoardLogger(new BoardLogFactory(), PersistenceContext.repositories().boardLogs()));


    private static final Integer MAX_NUM_OF_ROWS = Application.settings().getMaxNumberOfRows();

    private static final Integer MAX_NUM_OF_COLUMNS = Application.settings().getMaxNumberOfColumns();

    @Override
    protected boolean doShow() {
        System.out.println("Please provide the board title\n");

        String boardTitle = Console.readLine("Board Title: ");

        while (!createBoardController.validateBoardTitle(boardTitle)) {
            infoMessage("The board title is too long. Please try again.");
            boardTitle = Console.readLine("Board Title: ");
        }

        int numberOfRows = Console.readPositiveInteger("Number of Rows: ");

        while (numberOfRows > MAX_NUM_OF_ROWS) {
            infoMessage("The number of rows must be less than the max value defined in the properties file.");

            numberOfRows = Console.readPositiveInteger("Number of Rows: ");
        }
        System.out.println();

        int numberOfColumns = Console.readPositiveInteger("Number of Columns: ");

        while (numberOfColumns > MAX_NUM_OF_COLUMNS) {
            infoMessage("The number of columns must be less than the max value defined in the properties file.");

            numberOfColumns = Console.readPositiveInteger("Number of Columns: ");
        }

        System.out.println();

        List<String> rows = fillRows(numberOfRows);

        List<String> columns = fillColumns(numberOfColumns);

        try {
            ECourseUser user = createBoardController.getLoggedUser();
            createBoardController.createBoard(boardTitle, rows, columns, user);
        } catch (IllegalArgumentException e) {
            errorMessage(e.getMessage());
            Sleeper.sleep(1300);
            return false;
        }

        successMessage("Board created successfully!");

        System.out.println();

        infoMessage("The Board Configuration is:");

        System.out.println();

        System.out.print("    ");
        for (String column : columns) {
            System.out.printf("%-20s", column);
        }
        System.out.println();

        for (String row : rows) {

            System.out.print(row + "   ");

            for (int i = 0; i < columns.size(); i++) {
                System.out.printf("%-20s", "-");
            }
            System.out.println();
        }

        System.out.println();

        Sleeper.sleep(1500);

        return false;
    }

    @Override
    public String headline() {
        return "Create Board";
    }

    /**
     * Fill the rows of the board with the user inputs
     *
     * @param numOfRows - Number of rows to be filled
     * @return List of BoardRows
     */
    public List<String> fillRows(int numOfRows) {

        List<String> rows = new ArrayList<>();

        for (int i = 0; i < numOfRows; i++) {
            System.out.printf("Please provide the row %d title\n", i + 1);

            String row = Console.readLine("Row Title: ");
            System.out.println();

            if (createBoardController.validateBoardLineTitle(row)) {
                rows.add(row);
            } else {
                infoMessage("The row title is too long. Please try again.");
                i--;
            }
        }
        return rows;
    }

    /**
     * Fill the columns of the board with the user inputs
     *
     * @param numOfColumns - Number of columns to be filled
     * @return List of BoardColumns
     */
    public List<String> fillColumns(int numOfColumns) {

        List<String> columns = new ArrayList<>();

        for (int i = 0; i < numOfColumns; i++) {
            System.out.printf("Please provide the column %d title\n", i + 1);

            String column = Console.readLine("Column Title: ");
            System.out.println();

            if (createBoardController.validateBoardLineTitle(column)) {
                columns.add(column);
            } else {
                infoMessage("The column title is too long. Please try again.");
                i--;
            }
        }
        return columns;
    }

}
