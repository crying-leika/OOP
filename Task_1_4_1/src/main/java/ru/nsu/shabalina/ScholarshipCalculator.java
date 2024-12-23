package ru.nsu.shabalina;

public class ScholarshipCalculator {
    private final StudentTranscript transcript;
    private final double baseScholarshipAmount;

    public ScholarshipCalculator(StudentTranscript transcript, double baseScholarshipAmount) {
        this.transcript = transcript;
        this.baseScholarshipAmount = baseScholarshipAmount;
    }

    public double calculateCurrentScholarship() {
        if (transcript.isEligibleForIncreasedScholarship()) {
            return baseScholarshipAmount * 1.5;
        }
        return baseScholarshipAmount;
    }

    public boolean isEligibleForScholarship() {
        int currentSemester = transcript.getCurrentSemester();
        long failedCourses = transcript.getRecords().stream()
                .filter(r -> r.getSemester() == currentSemester)
                .filter(r -> r.getGrade() < 3)
                .count();
        return failedCourses == 0;
    }
}