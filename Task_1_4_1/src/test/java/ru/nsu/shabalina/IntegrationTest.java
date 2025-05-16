package ru.nsu.shabalina;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IntegrationTest {
    @Test
    void testFullStudentWorkflow() throws InvalidRecordException {
        // Создаем зачетку для платного студента
        StudentTranscript transcript = new StudentTranscript("Смирнов Алексей", "112233", true);

        // Добавляем успешные оценки
        transcript.addRecord(new AcademicPerformance("Математика", 5, AssessmentType.FINAL_EXAM, 7));
        transcript.addRecord(new AcademicPerformance("Физика", 5, AssessmentType.GRADED_CREDIT, 7));
        transcript.addRecord(new AcademicPerformance("Программирование", 5, AssessmentType.FINAL_EXAM, 8));
        transcript.addRecord(new AcademicPerformance("Диплом", 5, AssessmentType.THESIS_DEFENSE, 8));

        // Проверяем функциональность
        assertEquals(5.0, transcript.calculateAverageGrade(), 0.01);
        assertTrue(transcript.canTransferToBudget());
        assertTrue(transcript.canGetHonorsDiploma());
        assertTrue(transcript.isEligibleForIncreasedScholarship());

        // Проверяем сервис стипендии
        ScholarshipCalculator sc = new ScholarshipCalculator(transcript, 2000.0);
        assertEquals(3000.0, sc.calculateCurrentScholarship(), 0.01);
        assertTrue(sc.isEligibleForScholarship());

        // Проверяем сервис диплома
        DiplomaService ds = new DiplomaService(transcript);
        assertEquals("Красный диплом (с отличием)", ds.predictDiplomaType());
        assertEquals("Все условия для красного диплома выполнены!", ds.getDiplomaRecommendations());
    }
}
