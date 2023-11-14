/*
 * Copyright (c) 2013-2023 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package jpa;


import boardlogmanagement.repository.*;
import boardmanagement.repository.IBoardRepository;
import classmanagement.repository.ClassRepository;
import coursemanagement.repository.*;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.jpa.JpaAutoTxUserRepository;
import eapli.framework.infrastructure.pubsub.impl.simplepersistent.repositories.EventConsumptionRepository;
import eapli.framework.infrastructure.pubsub.impl.simplepersistent.repositories.EventRecordRepository;
import eapli.framework.infrastructure.pubsub.impl.simplepersistent.repositories.jpa.JpaAutoTxEventConsumptionRepository;
import eapli.framework.infrastructure.pubsub.impl.simplepersistent.repositories.jpa.JpaAutoTxEventRecordRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import enrolledstudentmanagement.repository.EnrolledStudentRepository;
import enrollmentrequestmanagement.repository.EnrollmentRequestRepository;
import ecourseusermanagement.repositories.IeCourseUserRepository;
import exammanagement.repository.ExamRepository;
import meetinginvitationmanagement.repository.IMeetingInvitationRepository;
import meetingmanagement.repository.IMeetingRepository;
import persistence.RepositoryFactory;
import postitmanagement.repository.*;
import questionmanagment.repository.*;
import takenexammanagement.repository.TakenExamRepository;

/**
 * The repository factory for JPA repositories.
 * <p>
 * This is the concrete factory in the Abstract Factory (GoF) pattern
 * </p>
 */
public class JpaRepositoryFactory implements RepositoryFactory {

	@Override
	public UserRepository users(final TransactionalContext autoTx) {
		return new JpaAutoTxUserRepository(autoTx);
	}
	@Override
	public UserRepository users() {
		return new JpaAutoTxUserRepository(appsettings.Application.settings().persistenceUnitName(),
				appsettings.Application.settings().extendedPersistenceProperties());
	}

	@Override
	public CourseRepository courses() {
		return new JpaCourseRepository();
	}

	@Override
	public EnrollmentRequestRepository enrollmentRequests() {
		return new JpaEnrollmentRequestRepository("id");
	}

	@Override
	public EnrolledStudentRepository enrolledStudents() {
		return new JpaEnrolledStudentRepository("id");
	}

	@Override
	public IeCourseUserRepository eCourseUsers() {
		return new JpaECourseUserRepository();
	}

	@Override
	public IMeetingRepository meetings() {
		return new JpaMeetingRepository("id");
	}

	@Override
	public IMeetingInvitationRepository meetingInvitations() {
		return new JpaMeetingInvitationRepository("id");
	}

	@Override
	public ClassRepository classes() {
		return new JpaClassRepository();
	}

	@Override
	public IBoardRepository boards() {
		return new JpaBoardRepository();
	}

	@Override
	public PostItRepository postIts() {
		return new JpaPostItRepository("id");
	}

	@Override
	public BoardLogRepository boardLogs() {
		return new JpaBoardLogRepository();
	}

	@Override
	public QuestionRepository questions() {
		return new JpaQuestionRepository();
	}

	@Override
	public ExamRepository exams() {
		return new JpaExamRepository();
	}

	@Override
	public TakenExamRepository takenExams() {
		return new JpaTakenExamRepository();
	}

	@Override
	public TransactionalContext newTransactionalContext() {
		return JpaAutoTxRepository.buildTransactionalContext(appsettings.Application.settings().persistenceUnitName(),
				appsettings.Application.settings().extendedPersistenceProperties());
	}

	@Override
	public EventConsumptionRepository eventConsumption() {
		return new JpaAutoTxEventConsumptionRepository(appsettings.Application.settings().persistenceUnitName(),
				appsettings.Application.settings().extendedPersistenceProperties());
	}

	@Override
	public EventRecordRepository eventRecord() {
		return new JpaAutoTxEventRecordRepository(appsettings.Application.settings().persistenceUnitName(),
				appsettings.Application.settings().extendedPersistenceProperties());
	}
}
