package ecourseusermanagement.domain.ecourseuserbuilders;

import ecourseusermanagement.domain.MechanographicNumberAssigner;
import persistence.PersistenceContext;



public class ECourseUserBuilderFactory implements IeCourseUserBuilderFactory {

    @Override
    public StudentBuilder createStudentBuilder() {
        return new StudentBuilder(new MechanographicNumberAssigner(PersistenceContext.repositories().eCourseUsers()));
    }

    @Override
    public TeacherBuilder createTeacherBuilder() {
        return new TeacherBuilder();
    }

    @Override
    public ManagerBuilder createManagerBuilder() {
        return new ManagerBuilder();
    }
}

