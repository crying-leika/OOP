package ru.nsu.shabalina;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScholarshipCalculatorTest {
    private StudentTranscript transcript;
    private ScholarshipCalculator calculator;

    @BeforeEach
    void setUp() {
        transcript = new StudentTranscript("Петров Петр", "654321", false);
        calculator = new ScholarshipCalculator(transcript, 1500.0);
    }

    @Test
    void testBaseScholarship() throws InvalidRecordException {
        transcript.addRecord(new AcademicPerformance("Физика", 4, AssessmentType.FINAL_EXAM, 3));
        assertEquals(1500.0, calculator.calculateCurrentScholarship(), 0.01);
    }

    @Test
    void testIncreasedScholarship() throws InvalidRecordException {
        transcript.addRecord(new AcademicPerformance("Математика", 5, AssessmentType.FINAL_EXAM, 4));
        transcript.addRecord(new AcademicPerformance("Программирование", 5, AssessmentType.QUIZ, 4));
        assertEquals(2250.0, calculator.calculateCurrentScholarship(), 0.01);
    }

    @Test
    void testNoScholarshipForFailedCourses() throws InvalidRecordException {
        transcript.addRecord(new AcademicPerformance("Физика", 2, AssessmentType.FINAL_EXAM, 2));
        assertFalse(calculator.isEligibleForScholarship());
    }

    @Test
    void testEligibilityWithDifferentGrades() throws InvalidRecordException {
        transcript.addRecord(new AcademicPerformance("Математика", 5, AssessmentType.FINAL_EXAM, 3));
        assertTrue(calculator.isEligibleForScholarship());

        transcript.addRecord(new AcademicPerformance("Физика", 3, AssessmentType.GRADED_CREDIT, 3));
        assertTrue(calculator.isEligibleForScholarship());

        transcript.addRecord(new AcademicPerformance("Химия", 2, AssessmentType.FINAL_EXAM, 3));
        assertFalse(calculator.isEligibleForScholarship());
    }
}
