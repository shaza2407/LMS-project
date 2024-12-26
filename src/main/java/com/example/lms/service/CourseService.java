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
    private QuestionRepository questionRepository;
    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;


    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
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


    public Question addQuestionToCourse(Long courseId, Question question) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        question.setCourse(course);
        return questionRepository.save(question);
    }


    // Add quiz to a course
    public Quiz addQuizToCourse(Long courseId, Quiz quiz, int numberOfQuestions) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        List<Question> questions = questionRepository.findByCourseId(courseId);
        if (questions.size() < numberOfQuestions) {
            throw new RuntimeException("Not enough questions available in the course");
        }
        Collections.shuffle(questions);
        List<Question> selectedQuestions = questions.subList(0, numberOfQuestions);
        quiz.setCourse(course);
        quiz.setQuestions(selectedQuestions);
        Quiz savedQuiz = quizRepository.save(quiz);
        selectedQuestions.forEach(question -> {
            question.setQuiz(savedQuiz);
            questionRepository.save(question);
        });
        return savedQuiz;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public List<Lesson> getAllLessons(Long courseId) {
        return lessonRepository.findByCourseId(courseId);
    }

    public List<Course> getCoursesByInstructorId(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }
    public List<Enrollment> getEnrolledCoursesByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }


}
