//package com.example.lms.config;
//
//import com.example.lms.repository.*;
//import com.example.lms.service.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class AppConfig {
//
//    @Bean
//    public UserRepository userRepository() {
//        return new UserRepository();
//    }
//
//    @Bean
//    public CourseRepository courseRepository() {
//        return new InMemoryCourseRepository();
//    }
//
//    @Bean
//    public AssessmentRepository assessmentRepository() {
//        return new InMemoryAssessmentRepository();
//    }
//
//    @Bean
//    public UserService userService(UserRepository userRepository) {
//        return new UserService(userRepository);
//    }
//
//    @Bean
//    public CourseService courseService(CourseRepository courseRepository) {
//        return new CourseService(courseRepository);
//    }
//
//    @Bean
//    public AssessmentService assessmentService(AssessmentRepository assessmentRepository) {
//        return new AssessmentService(assessmentRepository);
//    }
//}
