package postitmanagement.application;

import boardmanagement.domain.*;
import eapli.framework.validations.*;
import postitmanagement.domain.PostIt;
import postitmanagement.repository.*;

import java.util.List;
import java.util.Optional;

public class PostItProvider implements IPostItProvider {


    private final PostItRepository postItRepo;


    public PostItProvider(PostItRepository postItRepo) {
        Preconditions.noneNull(postItRepo);
        this.postItRepo = postItRepo;
    }


    @Override
    public Optional<PostIt> providePostItByCell(BoardCell cell) {
        return postItRepo.findByBoardCell(cell);
    }


    @Override
    public boolean hasPostIt(BoardCell cell) {
        return postItRepo.findByBoardCell(cell).isPresent();
    }


    @Override
    public List<PostIt> boardPostIts(Iterable<BoardCell> boardCells) {
        return postItRepo.findBoardPostIts(boardCells);
    }


}
