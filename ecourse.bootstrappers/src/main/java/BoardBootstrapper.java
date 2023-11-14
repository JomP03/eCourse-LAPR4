import boardlogmanagement.application.BoardLogFactory;
import boardlogmanagement.application.BoardLogger;
import boardmanagement.domain.*;
import boardmanagement.repository.IBoardRepository;
import eapli.framework.actions.*;
import ecourseusermanagement.domain.ECourseUser;
import persistence.*;

import java.util.ArrayList;
import java.util.List;

public class BoardBootstrapper implements Action {

    private final IBoardRepository boardRepository = PersistenceContext.repositories().boards();

    @Override
    public boolean execute() {

        BoardLogger logger = new BoardLogger(new BoardLogFactory(), PersistenceContext.repositories().boardLogs());

        // ---- Get users ----
        ECourseUser teacher = PersistenceContext.repositories().eCourseUsers().findByEmail("johndoe@gmail.com").orElse(null);
        ECourseUser student = PersistenceContext.repositories().eCourseUsers().findByEmail("mariaferreira@gmail.com").orElse(null);
        ECourseUser manager = PersistenceContext.repositories().eCourseUsers().findByEmail("adamsmith@gmail.com").orElse(null);

        // ---- Get Permissions ----
        BoardPermission teacherPermission = new BoardPermission(teacher, BoardPermissionType.OWNER);
        BoardPermission studentPermission = new BoardPermission(student, BoardPermissionType.OWNER);
        BoardPermission managerPermission = new BoardPermission(manager, BoardPermissionType.OWNER);

        // ---- Rows for the boards ----
        List<BoardRow> boardRows = new ArrayList<>();
        boardRows.add(new BoardRow(new BoardLineTitle("High"), 0));
        boardRows.add(new BoardRow(new BoardLineTitle("Medium"), 1));
        boardRows.add(new BoardRow(new BoardLineTitle("Low"), 2));

        // ---- Columns for the boards ----
        List<BoardColumn> boardColumns = new ArrayList<>();
        boardColumns.add(new BoardColumn(new BoardLineTitle("To Do"), 0));
        boardColumns.add(new BoardColumn(new BoardLineTitle("In Progress"), 1));
        boardColumns.add(new BoardColumn(new BoardLineTitle("Done"), 2));

        // ---- More rows for the boards ----
        List<BoardRow> alsoBoardRows = new ArrayList<>();
        alsoBoardRows.add(new BoardRow(new BoardLineTitle("High"), 0));
        alsoBoardRows.add(new BoardRow(new BoardLineTitle("Medium"), 1));
        alsoBoardRows.add(new BoardRow(new BoardLineTitle("Low"), 2));
        alsoBoardRows.add(new BoardRow(new BoardLineTitle("Extra"), 3));

        // ---- More columns for the boards ----
        List<BoardColumn> alsoBoardColumns = new ArrayList<>();
        alsoBoardColumns.add(new BoardColumn(new BoardLineTitle("To Do"), 0));
        alsoBoardColumns.add(new BoardColumn(new BoardLineTitle("In Progress"), 1));
        alsoBoardColumns.add(new BoardColumn(new BoardLineTitle("Done"), 2));
        alsoBoardColumns.add(new BoardColumn(new BoardLineTitle("Extra"), 3));

        // ---- Create Boards ----
        Board board1 = new Board(teacherPermission,"StickySync", boardRows, boardColumns);
        Board board2 = new Board(studentPermission,"NoteHub", alsoBoardRows, boardColumns);
        Board board3 = new Board(managerPermission,"TaskBoard", boardRows, boardColumns);
        Board board4 = new Board(teacherPermission,"ScrumBoard", alsoBoardRows, boardColumns);
        Board board5 = new Board(studentPermission,"KanbanBoard", boardRows, boardColumns);
        Board board6 = new Board(managerPermission,"AgileBoard", boardRows, alsoBoardColumns);
        Board board7 = new Board(teacherPermission,"ePostItBoard", alsoBoardRows, alsoBoardColumns);
        Board board8 = new Board(studentPermission,"eStickyBoard", boardRows, boardColumns);
        Board board9 = new Board(managerPermission,"eTaskBoard", alsoBoardRows, alsoBoardColumns);
        Board board10 = new Board(teacherPermission,"PinBoard", boardRows, boardColumns);

        // ---- Save Boards ----
        board1 = boardRepository.save(board1);
        board2 = boardRepository.save(board2);
        board3 = boardRepository.save(board3);
        board4 = boardRepository.save(board4);
        board5 = boardRepository.save(board5);
        board6 = boardRepository.save(board6);
        board7 = boardRepository.save(board7);
        board8 = boardRepository.save(board8);
        board9 = boardRepository.save(board9);
        board10 = boardRepository.save(board10);

        logger.logBoardCreation(board1, teacher);
        logger.logBoardCreation(board2, student);
        logger.logBoardCreation(board3, manager);
        logger.logBoardCreation(board4, teacher);
        logger.logBoardCreation(board5, student);
        logger.logBoardCreation(board6, manager);
        logger.logBoardCreation(board7, teacher);
        logger.logBoardCreation(board8, student);
        logger.logBoardCreation(board9, manager);
        logger.logBoardCreation(board10, teacher);

        return true;
    }

}
