package ru.nsu.shabalina;

public class AcademicPerformance {
    private final String courseName;
    private final int grade;
    private final AssessmentType assessmentType;
    private final int semester;

    public AcademicPerformance(String courseName, int grade,
                               AssessmentType assessmentType, int semester)
            throws InvalidRecordException {
        validateCourseName(courseName);
        validateGrade(grade);
        validateAssessmentType(assessmentType);
        validateSemester(semester);

        this.courseName = courseName;
        this.grade = grade;
        this.assessmentType = assessmentType;
        this.semester = semester;
    }

    public String getCourseName() { return courseName; }
    public int getGrade() { return grade; }
    public AssessmentType getAssessmentType() { return assessmentType; }
    public int getSemester() { return semester; }

    public boolean isExcellent() { return grade == 5; }
    public boolean isGood() { return grade == 4; }
    public boolean isSatisfactory() { return grade == 3; }

    private void validateCourseName(String name) throws InvalidRecordException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidRecordException("Название курса не может быть пустым");
        }
    }

    private void validateGrade(int grade) throws InvalidRecordException {
        if (grade < 2 || grade > 5) {
            throw new InvalidRecordException("Оценка должна быть от 2 до 5");
        }
    }

    private void validateAssessmentType(AssessmentType type) throws InvalidRecordException {
        if (type == null) {
            throw new InvalidRecordException("Тип оценки не может быть null");
        }
    }

    private void validateSemester(int semester) throws InvalidRecordException {
        if (semester < 1 || semester > 8) {
            throw new InvalidRecordException("Семестр должен быть от 1 до 8");
        }
    }
}

