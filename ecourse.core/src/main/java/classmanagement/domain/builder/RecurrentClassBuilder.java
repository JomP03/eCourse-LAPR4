package classmanagement.domain.builder;

import classmanagement.domain.ClassTitle;
import classmanagement.domain.ExtraClass;
import classmanagement.domain.RecurrentClass;
import classmanagement.domain.RecurrentClassWeekDay;
import classmanagement.domain.service.ClassScheduler;
import classmanagement.repository.ClassRepository;
import coursemanagement.domain.Course;
import ecourseusermanagement.domain.ECourseUser;

import java.util.Optional;

public class RecurrentClassBuilder {

        private final ClassRepository classRepository;

        private Course course;

        private String classTitle;

        private Integer classDuration;

        private String recurrentClassTime;

        private RecurrentClassWeekDay recurrentClassWeekDay;

        private ECourseUser teacher;

        /**
         * Instantiates a new Recurrent class builder.
         *
         * @param classRepository the class repository
         */
        public RecurrentClassBuilder(ClassRepository classRepository) {

            // check if the class repository is null
            if(classRepository == null)
                throw new IllegalArgumentException("The classRepository cannot be null.");

            this.classRepository = classRepository;
        }

        /**
         * With course recurrent class builder.
         *
         * @param course the course
         * @return the recurrent class builder
         */
        public RecurrentClassBuilder withCourse(Course course) {
            if (course == null)
                throw new IllegalArgumentException("The course cannot be null.");
            this.course = course;
            return this;
        }

        /**
         * With class title recurrent class builder.
         *
         * @param classTitle the class title
         * @return the recurrent class builder
         */
        public RecurrentClassBuilder withClassTitle(String classTitle) {
            if (classTitle == null || classTitle.isBlank())
                throw new IllegalArgumentException("The class title cannot be null or empty.");
            this.classTitle = classTitle;
            return this;
        }

        /**
         * With class duration recurrent class builder.
         *
         * @param classDuration the class duration
         * @return the recurrent class builder
         */
        public RecurrentClassBuilder withClassDuration(Integer classDuration) {
            if (classDuration == null || classDuration <= 0)
                throw new IllegalArgumentException("The class duration cannot be null or less than 0.");
            this.classDuration = classDuration;
            return this;
        }

        /**
         * With recurrent class time recurrent class builder.
         *
         * @param recurrentClassTime the recurrent class time
         * @return the recurrent class builder
         */
        public RecurrentClassBuilder withRecurrentClassTime(String recurrentClassTime) {
            if (recurrentClassTime == null || recurrentClassTime.isBlank())
                throw new IllegalArgumentException("The recurrent class time cannot be null or empty.");
            if(recurrentClassTime.split(":").length != 2)
                throw new IllegalArgumentException("The recurrent class time is not valid.");
            this.recurrentClassTime = recurrentClassTime;
            return this;
        }

        /**
         * With recurrent class week day recurrent class builder.
         *
         * @param recurrentClassWeekDay the recurrent class week day
         * @return the recurrent class builder
         */
        public RecurrentClassBuilder withRecurrentClassWeekDay(RecurrentClassWeekDay recurrentClassWeekDay) {
            if (recurrentClassWeekDay == null)
                throw new IllegalArgumentException("The recurrent class week day cannot be null.");
            this.recurrentClassWeekDay = recurrentClassWeekDay;
            return this;
        }

        /**
         * With teacher recurrent class builder.
         *
         * @param teacher the teacher
         * @return the recurrent class builder
         */
        public RecurrentClassBuilder withTeacher(ECourseUser teacher) {
            if (teacher == null)
                throw new IllegalArgumentException("The teacher cannot be null.");
            this.teacher = teacher;
            return this;
        }

        /**
         * Build recurrent class.
         * Checks if the class title is unique
         *
         * @return the recurrent class
         */
        public RecurrentClass build() {

            RecurrentClass recurrentClass = classRepository.findRecurrentClassByTitle(this.classTitle);
            ExtraClass extraClass = classRepository.findExtraClassByTitle(this.classTitle);
            if (recurrentClass != null || extraClass != null)
                throw new IllegalArgumentException("Class title must be unique");

            return new RecurrentClass(classTitle, classDuration, recurrentClassTime, recurrentClassWeekDay,course,teacher);
        }

}
