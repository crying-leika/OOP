package ru.nsu.shabalina;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentTranscriptTest {
    private StudentTranscript transcript;

    @BeforeEach
    void setUp() {
        transcript = new StudentTranscript("Иванов Иван", "123456", true);
    }

    @Test
    void testEmptyTranscript() {
        assertEquals(0.0, transcript.calculateAverageGrade());
        assertEquals(0, transcript.getRecords().size());
        assertEquals("Иванов Иван", transcript.getStudentName());
        assertEquals("123456", transcript.getStudentId());
        assertTrue(transcript.isFeePaying());
    }

    @Test
    void testAddRecords() throws InvalidRecordException {
        transcript.addRecord(new AcademicPerformance("Математика", 5, AssessmentType.FINAL_EXAM, 1));
        transcript.addRecord(new AcademicPerformance("Физика", 4, AssessmentType.GRADED_CREDIT, 1));

        assertEquals(2, transcript.getRecords().size());
        assertEquals(4.5, transcript.calculateAverageGrade(), 0.001);
    }

    @Test
    void testCalculateAverageGrade() throws InvalidRecordException {
        transcript.addRecord(new AcademicPerformance("Математика", 5, AssessmentType.FINAL_EXAM, 1));
        transcript.addRecord(new AcademicPerformance("Физика", 4, AssessmentType.GRADED_CREDIT, 1));
        transcript.addRecord(new AcademicPerformance("Химия", 3, AssessmentType.FINAL_EXAM, 2));
        transcript.addRecord(new AcademicPerformance("Физкультура", 5, AssessmentType.CREDIT, 2));

        assertEquals(4.25, transcript.calculateAverageGrade(), 0.001);
    }

    @Test
    void testScholarshipEligibility() throws InvalidRecordException {
        // Все отлично в текущем семестре
        transcript.addRecord(new AcademicPerformance("Математика", 5, AssessmentType.FINAL_EXAM, 3));
        transcript.addRecord(new AcademicPerformance("Физика", 5, AssessmentType.GRADED_CREDIT, 3));
        assertTrue(transcript.isEligibleForIncreasedScholarship());

        // Одна четверка в текущем семестре
        transcript.addRecord(new AcademicPerformance("Химия", 4, AssessmentType.FINAL_EXAM, 3));
        assertFalse(transcript.isEligibleForIncreasedScholarship());
    }

    @Test
    void testTransferToBudget() throws InvalidRecordException {
        // Нет троек за последние 2 семестра
        transcript.addRecord(new AcademicPerformance("Математика", 5, AssessmentType.FINAL_EXAM, 7));
        transcript.addRecord(new AcademicPerformance("Физика", 4, AssessmentType.GRADED_CREDIT, 7));
        transcript.addRecord(new AcademicPerformance("Информатика", 5, AssessmentType.FINAL_EXAM, 8));
        assertTrue(transcript.canTransferToBudget());

        // Есть тройка в последнем семестре
        transcript.addRecord(new AcademicPerformance("История", 3, AssessmentType.FINAL_EXAM, 8));
        assertFalse(transcript.canTransferToBudget());
    }

    @Test
    void testHonorsDiploma() throws InvalidRecordException {
        // Условия для красного диплома
        transcript.addRecord(new AcademicPerformance("Математика", 5, AssessmentType.FINAL_EXAM, 8));
        transcript.addRecord(new AcademicPerformance("Физика", 5, AssessmentType.GRADED_CREDIT, 8));
        transcript.addRecord(new AcademicPerformance("Диплом", 5, AssessmentType.THESIS_DEFENSE, 8));
        assertTrue(transcript.canGetHonorsDiploma());

        // Не хватает отличных оценок (менее 75%)
        transcript.addRecord(new AcademicPerformance("История", 4, AssessmentType.FINAL_EXAM, 8));
        assertFalse(transcript.canGetHonorsDiploma());
    }
}

