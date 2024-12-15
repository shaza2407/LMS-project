package com.example.lms.service;

import com.example.lms.model.Course;
import com.example.lms.repository.InMemoryCourseRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CourseServiceTest {

    @Test
    public void testCreateAndRetrieveCourse() {
        InMemoryCourseRepository repository = new InMemoryCourseRepository();
        CourseService service = new CourseService(repository);

        Course course = new Course(1L, "Java Programming", "Learn the basics of Java");
        service.createCourse(course);

        List<Course> courses = service.getAllCourses();
        assertEquals(1, courses.size());
        assertEquals("Java Programming", courses.get(0).getTitle());
        assertEquals("Learn the basics of Java", courses.get(0).getDescription());
    }

    @Test
    public void testGetCourseById() {
        InMemoryCourseRepository repository = new InMemoryCourseRepository();
        CourseService service = new CourseService(repository);

        Course course1 = new Course(1L, "Java Programming", "Learn the basics of Java");
        Course course2 = new Course(2L, "Python Programming", "Learn Python from scratch");
        service.createCourse(course1);
        service.createCourse(course2);

        Course retrievedCourse = service.getCourseById(2L);
        assertNotNull(retrievedCourse);
        assertEquals("Python Programming", retrievedCourse.getTitle());
    }

    @Test
    public void testDeleteCourse() {
        InMemoryCourseRepository repository = new InMemoryCourseRepository();
        CourseService service = new CourseService(repository);

        Course course = new Course(1L, "Java Programming", "Learn the basics of Java");
        service.createCourse(course);

        service.deleteCourse(1L);
        assertTrue(service.getAllCourses().isEmpty());
    }
}
