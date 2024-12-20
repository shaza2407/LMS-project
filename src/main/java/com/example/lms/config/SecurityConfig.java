//package com.example.lms.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/enrollments/**").hasRole("STUDENT")
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic(); // Or use JWT for token-based authentication
//
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/attendance/generate-otp/**").hasRole("INSTRUCTOR")
//                .antMatchers("/api/attendance/attend/**").hasRole("STUDENT")
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();
//
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/courses/{courseId}/enroll").hasRole("STUDENT")
//                .antMatchers(HttpMethod.POST, "/api/courses/{courseId}/lessons/{lessonId}/attend").hasRole("STUDENT")
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();
//
//        http.authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/instructor/**").hasRole("INSTRUCTOR")
//                .antMatchers(HttpMethod.GET, "/api/instructor/**").hasRole("INSTRUCTOR")
//                .antMatchers(HttpMethod.GET, "/api/student/**").hasRole("STUDENT")
//                .antMatchers(HttpMethod.POST, "/api/student/**").hasRole("STUDENT")
//                .anyRequest().authenticated();
//
//    }
//}
