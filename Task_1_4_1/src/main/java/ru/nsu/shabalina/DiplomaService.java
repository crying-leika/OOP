package ru.nsu.shabalina;

import java.util.List;

public class DiplomaService {
    private final StudentTranscript transcript;

    public DiplomaService(StudentTranscript transcript) {
        this.transcript = transcript;
    }

    public String predictDiplomaType() {
        if (transcript.canGetHonorsDiploma()) {
            return "Красный диплом (с отличием)";
        }
        return "Синий диплом";
    }

    public String getDiplomaRecommendations() {
        StringBuilder recommendations = new StringBuilder();

        if (!transcript.canGetHonorsDiploma()) {
            recommendations.append("Для получения красного диплома необходимо:\n");

            List<AcademicPerformance> finalSemester = transcript.getFinalSemesterRecords();
            long excellentCount = finalSemester.stream()
                    .filter(AcademicPerformance::isExcellent)
                    .count();
            double currentPercentage = (excellentCount * 100.0) / finalSemester.size();

            if (currentPercentage < 75) {
                recommendations.append(String.format(
                        "- Увеличить количество отличных оценок с %.1f%% до 75%%\n",
                        currentPercentage));
            }

            boolean hasSatisfactory = transcript.getRecords().stream()
                    .anyMatch(r -> r.isSatisfactory() &&
                            (r.getAssessmentType() == AssessmentType.FINAL_EXAM ||
                                    r.getAssessmentType() == AssessmentType.GRADED_CREDIT));

            if (hasSatisfactory) {
                recommendations.append("- Улучшить удовлетворительные оценки по экзаменам и диф. зачетам\n");
            }

            boolean hasExcellentThesis = finalSemester.stream()
                    .filter(r -> r.getAssessmentType() == AssessmentType.THESIS_DEFENSE)
                    .anyMatch(AcademicPerformance::isExcellent);

            if (!hasExcellentThesis) {
                recommendations.append("- Получить отлично за дипломную работу\n");
            }
        }

        return recommendations.toString().isEmpty()
                ? "Все условия для красного диплома выполнены!"
                : recommendations.toString();
    }
}