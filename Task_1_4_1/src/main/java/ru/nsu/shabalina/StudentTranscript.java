package ru.nsu.shabalina;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentTranscript {
    private final List<AcademicPerformance> records;
    private final boolean isFeePaying;
    private final String studentName;
    private final String studentId;

    public StudentTranscript(String studentName, String studentId, boolean isFeePaying) {
        this.records = new ArrayList<>();
        this.isFeePaying = isFeePaying;
        this.studentName = studentName;
        this.studentId = studentId;
    }

    public void addRecord(AcademicPerformance record) {
        records.add(record);
    }

    public List<AcademicPerformance> getRecords() {
        return new ArrayList<>(records);
    }

    public double calculateAverageGrade() {
        if (records.isEmpty()) {
            return 0.0;
        }
        return records.stream()
                .mapToInt(AcademicPerformance::getGrade)
                .average()
                .orElse(0.0);
    }

    public boolean canTransferToBudget() {
        if (!isFeePaying) return false;

        List<AcademicPerformance> recentSemesters = getRecentSemestersRecords(2);
        return recentSemesters.stream()
                .noneMatch(r -> r.isSatisfactory() &&
                        (r.getAssessmentType() == AssessmentType.FINAL_EXAM ||
                                r.getAssessmentType() == AssessmentType.GRADED_CREDIT));
    }

    public boolean canGetHonorsDiploma() {
        List<AcademicPerformance> finalSemesterRecords = getFinalSemesterRecords();
        if (finalSemesterRecords.isEmpty()) return false;

        long excellentCount = finalSemesterRecords.stream()
                .filter(AcademicPerformance::isExcellent)
                .count();
        double excellentPercentage = (excellentCount * 100.0) / finalSemesterRecords.size();

        boolean noSatisfactoryGrades = records.stream()
                .noneMatch(r -> r.isSatisfactory() &&
                        (r.getAssessmentType() == AssessmentType.FINAL_EXAM ||
                                r.getAssessmentType() == AssessmentType.GRADED_CREDIT));

        boolean excellentThesis = finalSemesterRecords.stream()
                .filter(r -> r.getAssessmentType() == AssessmentType.THESIS_DEFENSE)
                .anyMatch(AcademicPerformance::isExcellent);

        return excellentPercentage >= 75 && noSatisfactoryGrades && excellentThesis;
    }

    public boolean isEligibleForIncreasedScholarship() {
        int currentSemester = getCurrentSemester();
        return records.stream()
                .filter(r -> r.getSemester() == currentSemester)
                .allMatch(AcademicPerformance::isExcellent);
    }

    private List<AcademicPerformance> getRecentSemestersRecords(int semesterCount) {
        int currentSemester = getCurrentSemester();
        return records.stream()
                .filter(r -> r.getSemester() >= currentSemester - semesterCount + 1)
                .collect(Collectors.toList());
    }

    public List<AcademicPerformance> getFinalSemesterRecords() {
        int currentSemester = getCurrentSemester();
        return records.stream()
                .filter(r -> r.getSemester() == currentSemester)
                .collect(Collectors.toList());
    }

    private int getCurrentSemester() {
        return records.stream()
                .mapToInt(AcademicPerformance::getSemester)
                .max()
                .orElse(0);
    }

    public String getStudentName() { return studentName; }
    public String getStudentId() { return studentId; }
    public boolean isFeePaying() { return isFeePaying; }
}