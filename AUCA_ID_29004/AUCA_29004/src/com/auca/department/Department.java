package com.auca.department;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.teacher.Teacher;

public class Department implements Identifiable, Registrable {
    private String departmentId;
    private String departmentName;
    private String departmentCode;
    private String officeLocation;
    private String contactEmail;
    private Teacher headTeacher;

    public Department(String departmentId, String departmentName, String departmentCode,
                      String officeLocation, String contactEmail, Teacher headTeacher) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.officeLocation = officeLocation;
        this.contactEmail = contactEmail;
        this.headTeacher = headTeacher;
    }

    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }

    public String getOfficeLocation() { return officeLocation; }
    public void setOfficeLocation(String officeLocation) { this.officeLocation = officeLocation; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public Teacher getHeadTeacher() { return headTeacher; }
    public void setHeadTeacher(Teacher headTeacher) { this.headTeacher = headTeacher; }

    @Override
    public String getId() {
        return departmentId;
    }

    @Override
    public String getDisplayName() {
        return departmentName + " (" + departmentCode + ")";
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {
        if (departmentId == null || departmentId.trim().isEmpty()) {
            throw new ValidationException("Department ID cannot be blank");
        }
        if (departmentId.trim().length() < MIN_ID_LENGTH || departmentId.trim().length() > MAX_ID_LENGTH) {
            throw new ValidationException("Department ID length invalid");
        }
        if (departmentName == null || departmentName.trim().length() < MIN_NAME_LENGTH || departmentName.trim().length() > MAX_NAME_LENGTH) {
            throw new ValidationException("Department name must be " + MIN_NAME_LENGTH + "-" + MAX_NAME_LENGTH + " chars");
        }
        if (departmentCode == null || departmentCode.trim().isEmpty()) {
            throw new ValidationException("Department code is required");
        }
        if (officeLocation == null || officeLocation.trim().isEmpty()) {
            throw new ValidationException("Office location is required");
        }
        if (contactEmail == null || contactEmail.trim().isEmpty() || contactEmail.length() > MAX_EMAIL_LENGTH) {
            throw new ValidationException("Contact email invalid");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!java.util.regex.Pattern.matches(emailRegex, contactEmail.trim())) {
            throw new ValidationException("Invalid contact email format");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return String.format("[%s] %s | Code: %s | Location: %s | Email: %s | Head: %s",
                departmentId, departmentName, departmentCode, officeLocation, contactEmail,
                headTeacher != null ? headTeacher.getDisplayName() : "Not assigned");
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
