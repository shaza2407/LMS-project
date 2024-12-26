package com.example.lms.service;

import com.example.lms.model.*;
import com.example.lms.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCourseById_ShouldReturnCourse_WhenCourseExists() {
        Long courseId = 1L;
        Course course = new Course();
        course.setId(courseId);
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        Course result = courseService.getCourseById(courseId);
        assertNotNull(result);
        assertEquals(courseId, result.getId());
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void getCourseById_ShouldThrowException_WhenCourseNotFound() {
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> courseService.getCourseById(courseId));
        assertEquals("Course not found", exception.getMessage());
    }

    @Test
    void createCourse_ShouldSaveAndReturnCourse() {
        Course course = new Course();
        course.setTitle("New Course");
        when(courseRepository.save(course)).thenReturn(course);
        Course result = courseService.createCourse(course);
        assertNotNull(result);
        assertEquals("New Course", result.getTitle());
        verify(courseRepository, times(1)).save(course);
    }

    @Test
    void updateCourse_ShouldUpdateAndReturnCourse_WhenCourseExists() {
        Long courseId = 1L;
        Course existingCourse = new Course();
        existingCourse.setId(courseId);
        existingCourse.setTitle("Old Title");
        Course updatedCourse = new Course();
        updatedCourse.setTitle("Updated Title");
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(existingCourse)).thenReturn(existingCourse);
        Course result = courseService.updateCourse(courseId, updatedCourse);
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(courseRepository, times(1)).save(existingCourse);
    }

    @Test
    void updateCourse_ShouldThrowException_WhenCourseNotFound() {
        Long courseId = 1L;
        Course updatedCourse = new Course();
        updatedCourse.setTitle("Updated Title");
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> courseService.updateCourse(courseId, updatedCourse));
        assertEquals("Course not found", exception.getMessage());
    }

    @Test
    void addLessonToCourse_ShouldAddLesson_WhenCourseExists() {
        Long courseId = 1L;
        Course course = new Course();
        course.setId(courseId);
        course.setLessons(new ArrayList<>());
        Lesson lesson = new Lesson();
        lesson.setTitle("New Lesson");
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);
        Lesson result = courseService.addLessonToCourse(courseId, lesson);
        assertNotNull(result);
        assertEquals("New Lesson", result.getTitle());
        assertEquals(1, course.getLessons().size());
        assertSame(course, result.getCourse()); // Ensure the lesson is linked to the course
        verify(lessonRepository, times(1)).save(lesson);
        verify(courseRepository, times(1)).save(course); // Verify course save
    }


    @Test
    void addLessonToCourse_ShouldThrowException_WhenCourseNotFound() {
        Long courseId = 1L;
        Lesson lesson = new Lesson();
        lesson.setTitle("New Lesson");
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> courseService.addLessonToCourse(courseId, lesson));
        assertEquals("Course not found", exception.getMessage());
    }

    @Test
    void addQuizToCourse_ShouldAddQuiz_WhenSufficientQuestionsExist() {
        Long courseId = 1L;
        int numberOfQuestions = 3;
        Course course = new Course();
        course.setId(courseId);
        Quiz quiz = new Quiz();
        quiz.setTitle("Sample Quiz");
        List<Question> questions = Arrays.asList(new Question(), new Question(), new Question(), new Question());
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(questionRepository.findByCourseId(courseId)).thenReturn(questions);
        when(quizRepository.save(quiz)).thenReturn(quiz);
        Quiz result = courseService.addQuizToCourse(courseId, quiz, numberOfQuestions);
        assertNotNull(result);
        assertEquals("Sample Quiz", result.getTitle());
        assertEquals(numberOfQuestions, result.getQuestions().size());
        verify(quizRepository, times(1)).save(quiz);
    }

    @Test
    void addQuizToCourse_ShouldThrowException_WhenInsufficientQuestions() {
        Long courseId = 1L;
        int numberOfQuestions = 5;
        Course course = new Course();
        course.setId(courseId);
        Quiz quiz = new Quiz();
        quiz.setTitle("Sample Quiz");
        List<Question> questions = Arrays.asList(new Question(), new Question());
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(questionRepository.findByCourseId(courseId)).thenReturn(questions);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> courseService.addQuizToCourse(courseId, quiz, numberOfQuestions));
        assertEquals("Not enough questions available in the course", exception.getMessage());
    }

    @Test
    void deleteCourse_ShouldDeleteCourse_WhenCourseExists() {
        Long courseId = 1L;
        doNothing().when(courseRepository).deleteById(courseId);
        courseService.deleteCourse(courseId);
        verify(courseRepository, times(1)).deleteById(courseId);
    }

    @Test
    public void testDeserialization() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = "{ \"title\": \"batee5\", \"description\": \"how to cut batee5\", \"duration\": 8 }";

        Course course = mapper.readValue(json, Course.class);
        assertEquals("batee5", course.getTitle());
        assertEquals("how to cut batee5", course.getDescription());
        assertEquals(8, course.getDuration());
    }

}
