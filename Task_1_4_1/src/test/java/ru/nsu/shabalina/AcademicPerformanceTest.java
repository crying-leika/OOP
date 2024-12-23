package ru.nsu.shabalina;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AcademicPerformanceTest {
    @Test
    void testValidCreation() throws InvalidRecordException {
        AcademicPerformance record = new AcademicPerformance(
                "Математика", 5, AssessmentType.FINAL_EXAM, 1);
        assertEquals("Математика", record.getCourseName());
        assertEquals(5, record.getGrade());
        assertEquals(AssessmentType.FINAL_EXAM, record.getAssessmentType());
        assertEquals(1, record.getSemester());
    }

    @Test
    void testInvalidCourseName() {
        assertThrows(InvalidRecordException.class, () ->
                new AcademicPerformance(null, 5, AssessmentType.FINAL_EXAM, 1));
        assertThrows(InvalidRecordException.class, () ->
                new AcademicPerformance("", 5, AssessmentType.FINAL_EXAM, 1));
        assertThrows(InvalidRecordException.class, () ->
                new AcademicPerformance("   ", 5, AssessmentType.FINAL_EXAM, 1));
    }

    @Test
    void testInvalidGrade() {
        assertThrows(InvalidRecordException.class, () ->
                new AcademicPerformance("Физика", 1, AssessmentType.GRADED_CREDIT, 2));
        assertThrows(InvalidRecordException.class, () ->
                new AcademicPerformance("Химия", 6, AssessmentType.HOMEWORK, 3));
        assertThrows(InvalidRecordException.class, () ->
                new AcademicPerformance("Информатика", 0, AssessmentType.QUIZ, 4));
    }

    @Test
    void testInvalidAssessmentType() {
        assertThrows(InvalidRecordException.class, () ->
                new AcademicPerformance("ИИ", 5, null, 4));
    }

    @Test
    void testInvalidSemester() {
        assertThrows(InvalidRecordException.class, () ->
                new AcademicPerformance("Английский", 4, AssessmentType.QUIZ, 0));
        assertThrows(InvalidRecordException.class, () ->
                new AcademicPerformance("История", 2, AssessmentType.MIDTERM, 9));
        assertThrows(InvalidRecordException.class, () ->
                new AcademicPerformance("Физкультура", 3, AssessmentType.CREDIT, -1));
    }

    @Test
    void testGradeChecks() throws InvalidRecordException {
        AcademicPerformance excellent = new AcademicPerformance("Мат. анализ", 5, AssessmentType.FINAL_EXAM, 2);
        AcademicPerformance good = new AcademicPerformance("Алгебра", 4, AssessmentType.GRADED_CREDIT, 1);
        AcademicPerformance satisfactory = new AcademicPerformance("Философия", 3, AssessmentType.FINAL_EXAM, 3);
        AcademicPerformance poor = new AcademicPerformance("Физика", 2, AssessmentType.FINAL_EXAM, 2);

        assertTrue(excellent.isExcellent());
        assertFalse(good.isExcellent());
        assertFalse(satisfactory.isExcellent());
        assertFalse(poor.isExcellent());

        assertFalse(excellent.isSatisfactory());
        assertFalse(good.isSatisfactory());
        assertTrue(satisfactory.isSatisfactory());
        assertFalse(poor.isSatisfactory());
    }
}