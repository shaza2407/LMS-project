package com.example.lms.service;

import com.example.lms.model.*;
import com.example.lms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private AssessmentRepository assignmentRepository;

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizRepository quizRepository;

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Lesson getLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        Optional<Course> existingCourse = courseRepository.findById(id);
        if (existingCourse.isPresent()) {
            Course course = existingCourse.get();
            course.setTitle(updatedCourse.getTitle());
            course.setDescription(updatedCourse.getDescription());
            course.setDuration(updatedCourse.getDuration());
            return courseRepository.save(course);
        }
        throw new RuntimeException("Course not found");
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public Lesson addLessonToCourse(Long courseId, Lesson lesson) {
        Optional<Course> existingCourse = courseRepository.findById(courseId);
        if (existingCourse.isPresent()) {
            Course course = existingCourse.get();
            lesson.setCourse(course);
            course.getLessons().add(lesson);
            courseRepository.save(course);
            return lessonRepository.save(lesson);
        }
        throw new RuntimeException("Course not found");
    }

    public Assessment addAssignmentToCourse(Long courseId, Assessment assignment) {
        Optional<Course> existingCourse = courseRepository.findById(courseId);
        if (existingCourse.isPresent()) {
            Course course = existingCourse.get();
            assignment.setCourse(course);
            course.getAssignments().add(assignment);
            courseRepository.save(course);
            return assignmentRepository.save(assignment);
        }
        throw new RuntimeException("Course not found");
    }

    public Question addQuestionToCourse(Long courseId, Question question) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        question.setCourse(course); // Set the course relationship
        return questionRepository.save(question); // Save and return the question
    }

    // Add quiz to a course

    public Quiz addQuizToCourse(Long courseId, Quiz quiz, int numberOfQuestions) {
        // Fetch the course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Fetch all questions associated with the course
        List<Question> questions = questionRepository.findByCourseId(courseId);
        if (questions.size() < numberOfQuestions) {
            throw new RuntimeException("Not enough questions available in the course");
        }

        // Shuffle and select random questions
        Collections.shuffle(questions);
        List<Question> selectedQuestions = questions.subList(0, numberOfQuestions);

        // Associate selected questions with the quiz
        quiz.setCourse(course);
        quiz.setQuestions(selectedQuestions);

        // Save the quiz
        Quiz savedQuiz = quizRepository.save(quiz);

        // Update the questions with the quiz relationship
        selectedQuestions.forEach(question -> {
            question.setQuiz(savedQuiz);
            questionRepository.save(question);
        });

        return savedQuiz;
    }

//    public Question addQuestionToCourse(Long courseId, Question question) {
//        Optional<Course> existingCourse = courseRepository.findById(courseId);
//        if (existingCourse.isPresent()) {
//            Course course = existingCourse.get();
//            question.setCourse(course);
//            course.getQuestionBanks().add(question);
//            courseRepository.save(course);
//            return questionBankRepository.save(question);
//        }
//        throw new RuntimeException("Course not found");
//    }
//
//    public Quiz addQuizToCourse(Long courseId, Quiz quiz) {
//        Optional<Course> existingCourse = courseRepository.findById(courseId);
//        if (existingCourse.isPresent()) {
//            Course course = existingCourse.get();
//            quiz.setCourse(course);
//            course.getQuizzes().add(quiz);
//            courseRepository.save(course);
//            return quizRepository.save(quiz);
//        }
//        throw new RuntimeException("Course not found");
//    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Lesson> getAllLessons(Long courseId) {
        return lessonRepository.findByCourseId(courseId);
    }
}
