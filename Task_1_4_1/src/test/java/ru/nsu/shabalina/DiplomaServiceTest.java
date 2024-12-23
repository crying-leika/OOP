package ru.nsu.shabalina;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiplomaServiceTest {
    private StudentTranscript transcript;
    private DiplomaService diplomaService;

    @BeforeEach
    void setUp() {
        transcript = new StudentTranscript("Сидорова Анна", "789012", false);
        diplomaService = new DiplomaService(transcript);
    }

    @Test
    void testHonorsDiplomaPrediction() throws InvalidRecordException {
        transcript.addRecord(new AcademicPerformance("Алгебра", 5, AssessmentType.FINAL_EXAM, 8));
        transcript.addRecord(new AcademicPerformance("Геометрия", 5, AssessmentType.GRADED_CREDIT, 8));
        transcript.addRecord(new AcademicPerformance("Диплом", 5, AssessmentType.THESIS_DEFENSE, 8));

        assertEquals("Красный диплом (с отличием)", diplomaService.predictDiplomaType());
    }

    @Test
    void testRegularDiplomaPrediction() throws InvalidRecordException {
        transcript.addRecord(new AcademicPerformance("Физика", 4, AssessmentType.FINAL_EXAM, 8));
        transcript.addRecord(new AcademicPerformance("Химия", 3, AssessmentType.GRADED_CREDIT, 8));
        transcript.addRecord(new AcademicPerformance("Диплом", 5, AssessmentType.THESIS_DEFENSE, 8));

        assertEquals("Синий диплом", diplomaService.predictDiplomaType());
    }

    @Test
    void testDiplomaRecommendations() throws InvalidRecordException {
        // Тест с нехваткой отличных оценок
        transcript.addRecord(new AcademicPerformance("Программирование", 4, AssessmentType.FINAL_EXAM, 8));
        transcript.addRecord(new AcademicPerformance("Алгоритмы", 4, AssessmentType.GRADED_CREDIT, 8));

        String recommendations = diplomaService.getDiplomaRecommendations();
        assertTrue(recommendations.contains("Увеличить количество отличных оценок"));
        assertFalse(recommendations.contains("Улучшить удовлетворительные оценки"));

        // Тест с удовлетворительными оценками
        transcript.addRecord(new AcademicPerformance("Базы данных", 3, AssessmentType.FINAL_EXAM, 7));
        recommendations = diplomaService.getDiplomaRecommendations();
        assertTrue(recommendations.contains("Улучшить удовлетворительные оценки"));

        // Тест с неотличной защитой диплома
        transcript = new StudentTranscript("Тестовый Студент", "000000", false);
        diplomaService = new DiplomaService(transcript);
        transcript.addRecord(new AcademicPerformance("Диплом", 4, AssessmentType.THESIS_DEFENSE, 8));
        recommendations = diplomaService.getDiplomaRecommendations();
        assertTrue(recommendations.contains("Получить отлично за дипломную работу"));
    }

    @Test
    void testEmptyTranscriptRecommendations() {
        String recommendations = diplomaService.getDiplomaRecommendations();
        assertTrue(recommendations.contains("Для получения красного диплома необходимо"));
    }
}

