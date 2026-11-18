package com.auca.abstracts;

import com.auca.department.Department;
import com.auca.exceptions.ValidationException;

import java.time.LocalDate;

public abstract class AcademicMember extends Person {
    protected String memberNumber;
    protected Department department;
    protected LocalDate joinDate;
    protected String academicStatus;
    protected String institutionalEmail;
    protected String advisorName;

    public AcademicMember(String personId, String firstName, String lastName, String email,
                          String phoneNumber, LocalDate dateOfBirth,
                          String memberNumber, Department department, LocalDate joinDate,
                          String academicStatus, String institutionalEmail, String advisorName) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);
        this.memberNumber = memberNumber;
        this.department = department;
        this.joinDate = joinDate;
        this.academicStatus = academicStatus;
        this.institutionalEmail = institutionalEmail;
        this.advisorName = advisorName;
    }

    public String getMemberNumber() { return memberNumber; }
    public void setMemberNumber(String memberNumber) { this.memberNumber = memberNumber; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    public String getAcademicStatus() { return academicStatus; }
    public void setAcademicStatus(String academicStatus) { this.academicStatus = academicStatus; }

    public String getInstitutionalEmail() { return institutionalEmail; }
    public void setInstitutionalEmail(String institutionalEmail) { this.institutionalEmail = institutionalEmail; }

    public String getAdvisorName() { return advisorName; }
    public void setAdvisorName(String advisorName) { this.advisorName = advisorName; }

    public abstract String getAcademicCategory();

    @Override
    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(academicStatus);
    }

    protected void validateAcademicFields() throws ValidationException {
        validatePersonFields();

        if (memberNumber == null || memberNumber.trim().isEmpty()) {
            throw new ValidationException("Member number cannot be blank");
        }
        String mn = memberNumber.trim();
        if (mn.length() < MIN_ID_LENGTH || mn.length() > MAX_ID_LENGTH) {
            throw new ValidationException("Member number length must be between " + MIN_ID_LENGTH + " and " + MAX_ID_LENGTH);
        }

        if (department == null) {
            throw new ValidationException("Department is required");
        }

        if (joinDate == null) {
            throw new ValidationException("Join date is required");
        }
        if (joinDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Join date cannot be in the future");
        }

        if (academicStatus == null || academicStatus.trim().isEmpty()) {
            throw new ValidationException("Academic status is required");
        }
        String status = academicStatus.trim().toUpperCase();
        if (!status.equals("ACTIVE") && !status.equals("INACTIVE")) {
            throw new ValidationException("Academic status must be ACTIVE or INACTIVE");
        }

        if (institutionalEmail == null || institutionalEmail.trim().isEmpty() || institutionalEmail.length() > MAX_EMAIL_LENGTH) {
            throw new ValidationException("Institutional email is required and max " + MAX_EMAIL_LENGTH + " chars");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!java.util.regex.Pattern.matches(emailRegex, institutionalEmail.trim())) {
            throw new ValidationException("Invalid institutional email format");
        }

        if (advisorName == null || advisorName.trim().length() < MIN_NAME_LENGTH || advisorName.trim().length() > 120) {
            throw new ValidationException("Advisor name must be " + MIN_NAME_LENGTH + "-120 characters");
        }
    }

    @Override
    public String toString() {
        return "AcademicMember{" +
                "memberNumber='" + memberNumber + '\'' +
                ", department=" + (department != null ? department.getDepartmentName() : "null") +
                ", joinDate=" + joinDate +
                ", academicStatus='" + academicStatus + '\'' +
                ", institutionalEmail='" + institutionalEmail + '\'' +
                ", advisorName='" + advisorName + '\'' +
                "} " + super.toString();
    }
}
