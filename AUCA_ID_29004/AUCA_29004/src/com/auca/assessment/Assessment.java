package com.auca.assessment;

import com.auca.enrollment.Enrollment;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;

import java.time.LocalDate;

public class Assessment implements Identifiable, Registrable {
    private String assessmentId;
    private Enrollment enrollment;
    private String assessmentType;
    private double maximumScore;
    private double obtainedScore;
    private LocalDate assessmentDate;

    public Assessment(String assessmentId, Enrollment enrollment, String assessmentType,
                      double maximumScore, double obtainedScore, LocalDate assessmentDate) {
        this.assessmentId = assessmentId;
        this.enrollment = enrollment;
        this.assessmentType = assessmentType;
        this.maximumScore = maximumScore;
        this.obtainedScore = obtainedScore;
        this.assessmentDate = assessmentDate;
    }

    public String getAssessmentId() { return assessmentId; }
    public void setAssessmentId(String assessmentId) { this.assessmentId = assessmentId; }

    public Enrollment getEnrollment() { return enrollment; }
    public void setEnrollment(Enrollment enrollment) { this.enrollment = enrollment; }

    public String getAssessmentType() { return assessmentType; }
    public void setAssessmentType(String assessmentType) { this.assessmentType = assessmentType; }

    public double getMaximumScore() { return maximumScore; }
    public void setMaximumScore(double maximumScore) { this.maximumScore = maximumScore; }

    public double getObtainedScore() { return obtainedScore; }
    public void setObtainedScore(double obtainedScore) { this.obtainedScore = obtainedScore; }

    public LocalDate getAssessmentDate() { return assessmentDate; }
    public void setAssessmentDate(LocalDate assessmentDate) { this.assessmentDate = assessmentDate; }

    @Override
    public String getId() { return assessmentId; }

    @Override
    public String getDisplayName() {
        return assessmentType + " for " + (enrollment != null ? enrollment.getDisplayName() : "N/A");
    }

    @Override
    public boolean isActive() { return true; }

    @Override
    public void validate() throws ValidationException {
        if (assessmentId == null || assessmentId.trim().isEmpty()) {
            throw new ValidationException("Assessment ID cannot be blank");
        }
        if (enrollment == null) {
            throw new ValidationException("Enrollment is required");
        }
        if (!enrollment.isActive()) {
            throw new ValidationException("Can only assess an active enrollment");
        }
        if (assessmentType == null || assessmentType.trim().isEmpty()) {
            throw new ValidationException("Assessment type is required");
        }
        String type = assessmentType.trim().toUpperCase();
        if (!type.equals("QUIZ") && !type.equals("MIDTERM") && !type.equals("FINAL") && !type.equals("ASSIGNMENT")) {
            throw new ValidationException("Type must be QUIZ, MIDTERM, FINAL or ASSIGNMENT");
        }
        if (maximumScore <= 0) {
            throw new ValidationException("Maximum score must be greater than zero");
        }
        if (obtainedScore < 0 || obtainedScore > maximumScore) {
            throw new ValidationException("Obtained score must be between 0 and maximum score");
        }
        if (assessmentDate == null) {
            throw new ValidationException("Assessment date is required");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return String.format("[%s] %s | Score: %.1f/%.1f | Date: %s | Enrollment: %s",
                assessmentId, assessmentType, obtainedScore, maximumScore, assessmentDate,
                enrollment != null ? enrollment.getId() : "N/A");
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
