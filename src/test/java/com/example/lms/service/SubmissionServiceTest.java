package com.example.lms.service;

import com.example.lms.model.Assessment;
import com.example.lms.model.AssessmentType;
import com.example.lms.model.Submission;
import com.example.lms.model.User;
import com.example.lms.repository.AssessmentRepository;
import com.example.lms.repository.SubmissionRepository;
import com.example.lms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubmissionServiceTest {

    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private AssessmentRepository assessmentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SubmissionService submissionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSubmissionsForAssessment_shouldReturnSubmissions() {
        Long assessmentId = 1L;
        List<Submission> mockSubmissions = List.of(new Submission(), new Submission());
        when(submissionRepository.findByAssessmentId(assessmentId)).thenReturn(mockSubmissions);
        List<Submission> submissions = submissionService.getSubmissionsForAssessment(assessmentId);
        assertEquals(mockSubmissions.size(), submissions.size());
        verify(submissionRepository, times(1)).findByAssessmentId(assessmentId);
    }

    @Test
    void gradeSubmission_shouldUpdateAndReturnSubmission() {
        Long submissionId = 1L;
        Double grade = 95.0;
        String feedback = "Great job!";
        Submission mockSubmission = new Submission();
        mockSubmission.setId(submissionId);
        when(submissionRepository.findById(submissionId)).thenReturn(Optional.of(mockSubmission));
        when(submissionRepository.save(any(Submission.class))).thenReturn(mockSubmission);
        Submission updatedSubmission = submissionService.gradeSubmission(submissionId, grade, feedback);
        assertEquals(grade, updatedSubmission.getGrade());
        assertEquals(feedback, updatedSubmission.getFeedback());
        verify(submissionRepository, times(1)).findById(submissionId);
        verify(submissionRepository, times(1)).save(mockSubmission);
    }

    @Test
    void submitAssignment_shouldSaveAndReturnSuccessMessage() throws Exception {
        Long studentId = 1L;
        Long assessmentId = 1L;
        String fileName = "testFile.txt";
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn(fileName);
        InputStream fileStream = new ByteArrayInputStream("content".getBytes());
        when(mockFile.getInputStream()).thenReturn(fileStream);
        Assessment mockAssessment = new Assessment();
        mockAssessment.setId(assessmentId);
        mockAssessment.setType(AssessmentType.ASSIGNMENT);
        User mockUser = new User();
        mockUser.setId(studentId);
        when(assessmentRepository.findById(assessmentId)).thenReturn(Optional.of(mockAssessment));
        when(userRepository.findById(String.valueOf(studentId))).thenReturn(Optional.of(mockUser));
        when(submissionRepository.save(any(Submission.class))).thenReturn(new Submission());
        String result = submissionService.submitAssignment(studentId, assessmentId, mockFile);
        assertEquals("Assignment submitted successfully!", result);
        Path expectedPath = Paths.get("uploads/" + fileName);
        assertTrue(Files.exists(expectedPath));
        verify(assessmentRepository, times(1)).findById(assessmentId);
        verify(userRepository, times(1)).findById(String.valueOf(studentId));
        verify(submissionRepository, times(1)).save(any(Submission.class));
        Files.deleteIfExists(expectedPath);
    }

    @Test
    void getSubmissionsByStudent_shouldReturnSubmissions() {
        Long studentId = 1L;
        List<Submission> mockSubmissions = List.of(new Submission(), new Submission());
        when(submissionRepository.findByStudentId(studentId)).thenReturn(mockSubmissions);
        List<Submission> submissions = submissionService.getSubmissionsByStudent(studentId);
        assertEquals(mockSubmissions.size(), submissions.size());
        verify(submissionRepository, times(1)).findByStudentId(studentId);
    }
}
