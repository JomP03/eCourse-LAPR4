package persistence;

import boardlogmanagement.repository.*;
import boardmanagement.repository.IBoardRepository;
import classmanagement.repository.ClassRepository;
import coursemanagement.repository.*;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.pubsub.impl.simplepersistent.repositories.EventConsumptionRepository;
import eapli.framework.infrastructure.pubsub.impl.simplepersistent.repositories.EventRecordRepository;
import ecourseusermanagement.repositories.IeCourseUserRepository;
import enrolledstudentmanagement.repository.EnrolledStudentRepository;
import enrollmentrequestmanagement.repository.EnrollmentRequestRepository;
import exammanagement.repository.ExamRepository;
import meetinginvitationmanagement.repository.IMeetingInvitationRepository;
import meetingmanagement.repository.IMeetingRepository;
import postitmanagement.repository.*;
import questionmanagment.repository.*;
import takenexammanagement.repository.TakenExamRepository;

public interface RepositoryFactory {

    /**
     *  repository will be created in auto transaction mode
     *
      * @return Repository of Exams
     */
    ExamRepository exams();

    /**
     * Factory method to create a transactional context to use in the repositories
     *
     * @return a new transactional context
     */
    TransactionalContext newTransactionalContext();

    /**
     * @param autoTx the transactional context to enroll
     *
     * @return a repository for users
     */
    UserRepository users(TransactionalContext autoTx);

    /**
     * repository will be created in auto transaction mode
     *
     * @return Repository of Courses
     */
    UserRepository users();

    /**
     * repository will be created in auto transaction mode
     *
     * @return Repository of Courses
     */
    CourseRepository courses();

    /**
     * repository will be created in auto transaction mode
     *
     * @return Repository of Enrollment Requests
     */
    EnrollmentRequestRepository enrollmentRequests();

    /**
     * repository will be created in auto transaction mode
     *
     * @return Repository of Enrolled Students
     */
    EnrolledStudentRepository enrolledStudents();

    /**
     * repository will be created in auto transaction mode
     *
     * @return Repository of ECourseUsers
     */
    IeCourseUserRepository eCourseUsers();

    /**
     * repository will be created in auto transaction mode
     *
     * @return Repository of Meetings
     */
    IMeetingRepository meetings();

    /**
     * Meeting invitations meeting invitation repository.
     *
     * @return the meeting invitation repository
     */
    IMeetingInvitationRepository meetingInvitations();

    ClassRepository classes();

    EventConsumptionRepository eventConsumption();

    EventRecordRepository eventRecord();


    /**
     * repository will be created in auto transaction mode
     * @return Repository of Boards
     */
    IBoardRepository boards();


    /**
     * repository will be created in auto transaction mode
     * @return Repository of Post-it
     */
    PostItRepository postIts();


    /**
     * repository will be created in auto transaction mode
     * @return Repository of Board Logs
     */
    BoardLogRepository boardLogs();

    /**
     * repository will be created in auto transaction mode
     *
     * @return Repository of Questions
     */
    QuestionRepository questions();


    /**
     * repository will be created in auto transaction mode
     *
     * @return Repository of Questions
     */
    TakenExamRepository takenExams();
}
