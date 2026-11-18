package com.auca.main;

import com.auca.abstracts.Person;
import com.auca.administrator.Administrator;
import com.auca.assessment.Assessment;
import com.auca.bookloan.BookLoan;
import com.auca.classroom.Classroom;
import com.auca.course.Course;
import com.auca.department.Department;
import com.auca.enrollment.Enrollment;
import com.auca.exceptions.*;
import com.auca.feepayment.FeePayment;
import com.auca.interfaces.Identifiable;
import com.auca.librarybook.LibraryBook;
import com.auca.student.Student;
import com.auca.teacher.Teacher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final List<Department> departments = new ArrayList<>();
    private static final List<Student> students = new ArrayList<>();
    private static final List<Teacher> teachers = new ArrayList<>();
    private static final List<Administrator> administrators = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();
    private static final List<Classroom> classrooms = new ArrayList<>();
    private static final List<Enrollment> enrollments = new ArrayList<>();
    private static final List<Assessment> assessments = new ArrayList<>();
    private static final List<FeePayment> feePayments = new ArrayList<>();
    private static final List<LibraryBook> libraryBooks = new ArrayList<>();
    private static final List<BookLoan> bookLoans = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("   AUCA_ID SCHOOL MANAGEMENT SYSTEM");
        System.out.println("   Console-based OOP Application");
        System.out.println("==============================================");

        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1":
                        registerDepartment();
                        break;
                    case "2":
                        registerStudent();
                        break;
                    case "3":
                        registerTeacher();
                        break;
                    case "4":
                        registerAdministrator();
                        break;
                    case "5":
                        registerCourse();
                        break;
                    case "6":
                        registerClassroom();
                        break;
                    case "7":
                        enrollStudent();
                        break;
                    case "8":
                        recordAssessment();
                        break;
                    case "9":
                        recordFeePayment();
                        break;
                    case "10":
                        registerLibraryBook();
                        break;
                    case "11":
                        borrowBook();
                        break;
                    case "12":
                        returnBook();
                        break;
                    case "13":
                        viewAllRecords();
                        break;
                    case "14":
                        searchById();
                        break;
                    case "15":
                        updateRecord();
                        break;
                    case "16":
                        showStudentAcademicSummary();
                        break;
                    case "17":
                        showPaymentHistory();
                        break;
                    case "18":
                        showAvailableBooks();
                        break;
                    case "19":
                        demonstratePolymorphism();
                        break;
                    case "0":
                        System.out.println("Thank you for using AUCA_ID. Goodbye!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please select 0-19.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            if (running) {
                System.out.println("\nPress ENTER to continue...");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n============== AUCA_ID SCHOOL MANAGEMENT ==============");
        System.out.println(" 1. Register Department       11. Borrow Library Book");
        System.out.println(" 2. Register Student          12. Return Library Book");
        System.out.println(" 3. Register Teacher          13. View All Records");
        System.out.println(" 4. Register Administrator    14. Search Record by ID");
        System.out.println(" 5. Register Course           15. Update Existing Record");
        System.out.println(" 6. Register Classroom        16. Student Academic Summary");
        System.out.println(" 7. Enroll Student            17. Student Payment History");
        System.out.println(" 8. Record Assessment         18. View Available Books");
        System.out.println(" 9. Record Fee Payment        19. Demonstrate Polymorphism");
        System.out.println("10. Register Library Book      0. Exit");
        System.out.print("Select an option: ");
    }

    // ==================== HELPER METHODS ====================

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("CANCEL") || input.equals("0")) return null;
            if (!input.isEmpty()) return input;
            System.out.println("! Value cannot be blank. Re-enter or type CANCEL.");
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("CANCEL") || input.equals("0")) return null;
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("! Invalid date. Use YYYY-MM-DD (example: 2004-05-16).");
            }
        }
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("CANCEL") || input.equals("0")) return -1;
            try {
                int val = Integer.parseInt(input);
                if (val >= min && val <= max) return val;
                System.out.println("! Value must be between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("! Please enter a valid integer.");
            }
        }
    }

    private static BigDecimal readMoney(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("CANCEL") || input.equals("0")) return null;
            try {
                BigDecimal val = new BigDecimal(input);
                if (val.compareTo(BigDecimal.ZERO) > 0) return val;
                System.out.println("! Amount must be strictly positive.");
            } catch (NumberFormatException e) {
                System.out.println("! Please enter a valid number (e.g. 1500.00).");
            }
        }
    }

    private static boolean idExists(String id) {
        for (Department d : departments) if (d.getId().equalsIgnoreCase(id)) return true;
        for (Student s : students) if (s.getId().equalsIgnoreCase(id) || s.getStudentNumber().equalsIgnoreCase(id)) return true;
        for (Teacher t : teachers) if (t.getId().equalsIgnoreCase(id) || t.getEmployeeNumber().equalsIgnoreCase(id)) return true;
        for (Administrator a : administrators) if (a.getId().equalsIgnoreCase(id) || a.getStaffNumber().equalsIgnoreCase(id)) return true;
        for (Course c : courses) if (c.getId().equalsIgnoreCase(id) || c.getCourseCode().equalsIgnoreCase(id)) return true;
        for (Classroom cl : classrooms) if (cl.getId().equalsIgnoreCase(id)) return true;
        for (Enrollment e : enrollments) if (e.getId().equalsIgnoreCase(id)) return true;
        for (Assessment a : assessments) if (a.getId().equalsIgnoreCase(id)) return true;
        for (FeePayment f : feePayments) if (f.getId().equalsIgnoreCase(id) || f.getReceiptNumber().equalsIgnoreCase(id)) return true;
        for (LibraryBook b : libraryBooks) if (b.getId().equalsIgnoreCase(id)) return true;
        for (BookLoan l : bookLoans) if (l.getId().equalsIgnoreCase(id)) return true;
        return false;
    }

    // ==================== REGISTRATION METHODS ====================

    private static void registerDepartment() {
        System.out.println("\n+------------------------------------------------------------+");
        System.out.println("| AUCA_ID  /  SCHOOL MANAGEMENT                              |");
        System.out.println("| DEPARTMENT REGISTRATION                                    |");
        System.out.println("+------------------------------------------------------------+");

        String id = readNonEmpty("Department ID [String | unique | 4-30]: ");
        if (id == null) return;
        if (idExists(id)) {
            System.out.println("! Department ID already exists.");
            return;
        }

        String name = readNonEmpty("Department Name [String | 2-60]: ");
        if (name == null) return;

        String code = readNonEmpty("Department Code [String]: ");
        if (code == null) return;

        String location = readNonEmpty("Office Location [String]: ");
        if (location == null) return;

        String email = readNonEmpty("Contact Email [String | email]: ");
        if (email == null) return;

        Teacher head = null;
        if (!teachers.isEmpty()) {
            System.out.println("Select Head Teacher (optional):");
            for (int i = 0; i < teachers.size(); i++) {
                System.out.println((i + 1) + ". " + teachers.get(i).getDisplayName());
            }
            System.out.println("0. Skip / No head teacher yet");
            int sel = readInt("Select: ", 0, teachers.size());
            if (sel > 0) head = teachers.get(sel - 1);
        }

        try {
            Department dept = new Department(id, name, code, location, email, head);
            dept.validate();
            departments.add(dept);
            System.out.println("+ Department " + id + " registered successfully.");
            dept.printDetails();
        } catch (ValidationException e) {
            System.out.println("! Validation failed: " + e.getMessage());
        }
    }

    private static void registerStudent() {
        System.out.println("\n+------------------------------------------------------------+");
        System.out.println("| AUCA_ID  /  SCHOOL MANAGEMENT                              |");
        System.out.println("| STUDENT REGISTRATION                     Step 1 of 4       |");
        System.out.println("+------------------------------------------------------------+");

        // Ensure department exists
        if (departments.isEmpty()) {
            System.out.println("No Department is registered.");
            System.out.println("1. Register Department now   0. Cancel");
            String ch = scanner.nextLine().trim();
            if (ch.equals("1")) {
                registerDepartment();
                if (departments.isEmpty()) return;
            } else return;
        }

        // Step 1 - Person
        String personId = readNonEmpty("01 Person ID [String | unique | 4-30]: ");
        if (personId == null) return;
        if (idExists(personId)) {
            System.out.println("! Person ID already exists.");
            return;
        }

        String firstName = readNonEmpty("02 First name [String | 2-60]: ");
        if (firstName == null) return;
        String lastName = readNonEmpty("03 Last name [String | 2-60]: ");
        if (lastName == null) return;
        String email = readNonEmpty("04 Email [String | unique | email]: ");
        if (email == null) return;
        String phone = readNonEmpty("05 Phone [String | 10-15 digits]: ");
        if (phone == null) return;
        LocalDate dob = readDate("06 Date of birth [LocalDate | YYYY-MM-DD]: ");
        if (dob == null) return;

        // Step 2 - Academic
        System.out.println("\n[STEP 2/4] ACADEMIC DETAILS");
        String memberNumber = readNonEmpty("07 Member number [String | unique | 4-30]: ");
        if (memberNumber == null) return;

        System.out.println("08 Department [select]:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + ". [" + departments.get(i).getId() + "] " + departments.get(i).getDepartmentName());
        }
        System.out.println((departments.size() + 1) + ". [+] Register a new Department");
        System.out.println("0. Cancel");
        int dsel = readInt("Select option: ", 0, departments.size() + 1);
        if (dsel == 0) return;
        if (dsel == departments.size() + 1) {
            registerDepartment();
            if (departments.isEmpty()) return;
            dsel = departments.size();
        }
        Department dept = departments.get(dsel - 1);

        LocalDate joinDate = readDate("09 Join date [LocalDate | YYYY-MM-DD]: ");
        if (joinDate == null) return;

        System.out.println("10 Academic status [1 ACTIVE / 2 INACTIVE]: ");
        int st = readInt("Select: ", 1, 2);
        if (st == -1) return;
        String academicStatus = st == 1 ? "ACTIVE" : "INACTIVE";

        String instEmail = readNonEmpty("11 Institutional email [String | unique | email]: ");
        if (instEmail == null) return;
        String advisor = readNonEmpty("12 Advisor name [String | 2-120]: ");
        if (advisor == null) return;

        // Step 3 - Student
        System.out.println("\n[STEP 3/4] STUDENT DETAILS");
        String studentNumber = readNonEmpty("13 Student number [String | unique | 4-30]: ");
        if (studentNumber == null) return;
        if (idExists(studentNumber)) {
            System.out.println("! Student number already exists.");
            return;
        }

        String program = readNonEmpty("14 Program name [String | 2-120]: ");
        if (program == null) return;
        LocalDate admission = readDate("15 Admission date [LocalDate | YYYY-MM-DD]: ");
        if (admission == null) return;
        int studyYear = readInt("16 Study year [int | 1-6]: ", 1, 6);
        if (studyYear == -1) return;
        String guardian = readNonEmpty("17 Guardian phone [String | 10-15 digits]: ");
        if (guardian == null) return;
        System.out.println("18 Scholarship [1 YES / 2 NO]: ");
        int sch = readInt("Select: ", 1, 2);
        if (sch == -1) return;
        boolean scholarship = sch == 1;

        // Review
        System.out.println("\n+---------------- REVIEW STUDENT ---------------------------+");
        System.out.println("| PERSON");
        System.out.println("| 01 Person ID          : " + personId);
        System.out.println("| 02 First name         : " + firstName);
        System.out.println("| 03 Last name          : " + lastName);
        System.out.println("| 04 Email              : " + email);
        System.out.println("| 05 Phone              : " + phone);
        System.out.println("| 06 Date of birth      : " + dob);
        System.out.println("| ACADEMIC MEMBER");
        System.out.println("| 07 Member number      : " + memberNumber);
        System.out.println("| 08 Department         : [" + dept.getId() + "] " + dept.getDepartmentName());
        System.out.println("| 09 Join date          : " + joinDate);
        System.out.println("| 10 Academic status    : " + academicStatus);
        System.out.println("| 11 Institutional email: " + instEmail);
        System.out.println("| 12 Advisor name       : " + advisor);
        System.out.println("| STUDENT");
        System.out.println("| 13 Student number     : " + studentNumber);
        System.out.println("| 14 Program name       : " + program);
        System.out.println("| 15 Admission date     : " + admission);
        System.out.println("| 16 Study year         : " + studyYear);
        System.out.println("| 17 Guardian phone     : " + guardian);
        System.out.println("| 18 Scholarship        : " + (scholarship ? "YES" : "NO"));
        System.out.println("+------------------------------------------------------------+");
        System.out.println("| [1] Save student  [2] Cancel                               |");
        System.out.println("+------------------------------------------------------------+");
        System.out.print("Select option: ");
        String conf = scanner.nextLine().trim();
        if (!conf.equals("1")) {
            System.out.println("Registration cancelled.");
            return;
        }

        try {
            Student s = new Student(personId, firstName, lastName, email, phone, dob,
                    memberNumber, dept, joinDate, academicStatus, instEmail, advisor,
                    studentNumber, program, admission, studyYear, guardian, scholarship);
            s.validate();
            students.add(s);
            System.out.println("+ Student " + studentNumber + " registered successfully.");
            s.printDetails();
        } catch (ValidationException e) {
            System.out.println("! Validation failed: " + e.getMessage());
        }
    }

    private static void registerTeacher() {
        System.out.println("\n------------- REGISTER TEACHER -------------");
        if (departments.isEmpty()) {
            System.out.println("No Department is registered.");
            System.out.println("1. Register Department now   0. Cancel");
            if (!scanner.nextLine().trim().equals("1")) return;
            registerDepartment();
            if (departments.isEmpty()) return;
        }

        String personId = readNonEmpty("Person ID [unique]: ");
        if (personId == null || idExists(personId)) {
            if (personId != null) System.out.println("! ID already exists.");
            return;
        }
        String firstName = readNonEmpty("First name: ");
        if (firstName == null) return;
        String lastName = readNonEmpty("Last name: ");
        if (lastName == null) return;
        String email = readNonEmpty("Email: ");
        if (email == null) return;
        String phone = readNonEmpty("Phone: ");
        if (phone == null) return;
        LocalDate dob = readDate("Date of birth (YYYY-MM-DD): ");
        if (dob == null) return;

        String memberNumber = readNonEmpty("Member number: ");
        if (memberNumber == null) return;

        System.out.println("Select Department:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + ". " + departments.get(i).getDisplayName());
        }
        int dsel = readInt("Select: ", 1, departments.size());
        if (dsel == -1) return;
        Department dept = departments.get(dsel - 1);

        LocalDate joinDate = readDate("Join date: ");
        if (joinDate == null) return;
        String academicStatus = "ACTIVE";
        String instEmail = readNonEmpty("Institutional email: ");
        if (instEmail == null) return;
        String advisor = readNonEmpty("Advisor name: ");
        if (advisor == null) return;

        String empNumber = readNonEmpty("Employee number [unique]: ");
        if (empNumber == null || idExists(empNumber)) {
            if (empNumber != null) System.out.println("! Employee number already exists.");
            return;
        }
        String specialization = readNonEmpty("Specialization: ");
        if (specialization == null) return;
        LocalDate hireDate = readDate("Hire date: ");
        if (hireDate == null) return;
        BigDecimal salary = readMoney("Salary: ");
        if (salary == null) return;
        String office = readNonEmpty("Office number: ");
        if (office == null) return;
        System.out.println("Employment type [1 FULL_TIME / 2 PART_TIME / 3 CONTRACT]: ");
        int et = readInt("Select: ", 1, 3);
        if (et == -1) return;
        String empType = et == 1 ? "FULL_TIME" : (et == 2 ? "PART_TIME" : "CONTRACT");

        try {
            Teacher t = new Teacher(personId, firstName, lastName, email, phone, dob,
                    memberNumber, dept, joinDate, academicStatus, instEmail, advisor,
                    empNumber, specialization, hireDate, salary, office, empType);
            t.validate();
            teachers.add(t);
            System.out.println("+ Teacher " + empNumber + " registered successfully.");
            t.printDetails();
        } catch (ValidationException e) {
            System.out.println("! Validation failed: " + e.getMessage());
        }
    }

    private static void registerAdministrator() {
        System.out.println("\n------------- REGISTER ADMINISTRATOR -------------");
        String personId = readNonEmpty("Person ID [unique]: ");
        if (personId == null || idExists(personId)) {
            if (personId != null) System.out.println("! ID already exists.");
            return;
        }
        String firstName = readNonEmpty("First name: ");
        if (firstName == null) return;
        String lastName = readNonEmpty("Last name: ");
        if (lastName == null) return;
        String email = readNonEmpty("Email: ");
        if (email == null) return;
        String phone = readNonEmpty("Phone: ");
        if (phone == null) return;
        LocalDate dob = readDate("Date of birth: ");
        if (dob == null) return;

        String staffNumber = readNonEmpty("Staff number [unique]: ");
        if (staffNumber == null || idExists(staffNumber)) {
            if (staffNumber != null) System.out.println("! Staff number already exists.");
            return;
        }
        String jobTitle = readNonEmpty("Job title: ");
        if (jobTitle == null) return;
        String office = readNonEmpty("Assigned office: ");
        if (office == null) return;
        LocalDate empDate = readDate("Employment date: ");
        if (empDate == null) return;
        int access = readInt("Access level [1-10]: ", 1, 10);
        if (access == -1) return;
        System.out.println("Active [1 YES / 2 NO]: ");
        int act = readInt("Select: ", 1, 2);
        if (act == -1) return;

        try {
            Administrator a = new Administrator(personId, firstName, lastName, email, phone, dob,
                    staffNumber, jobTitle, office, empDate, access, act == 1);
            a.validate();
            administrators.add(a);
            System.out.println("+ Administrator " + staffNumber + " registered successfully.");
            a.printDetails();
        } catch (ValidationException e) {
            System.out.println("! Validation failed: " + e.getMessage());
        }
    }

    private static void registerCourse() {
        System.out.println("\n------------- REGISTER COURSE -------------");
        if (departments.isEmpty() || teachers.isEmpty()) {
            System.out.println("Course requires Department and Teacher.");
            if (departments.isEmpty()) {
                System.out.println("1. Register Department now   0. Cancel");
                if (scanner.nextLine().trim().equals("1")) registerDepartment();
                else return;
            }
            if (teachers.isEmpty()) {
                System.out.println("1. Register Teacher now   0. Cancel");
                if (scanner.nextLine().trim().equals("1")) registerTeacher();
                else return;
            }
            if (departments.isEmpty() || teachers.isEmpty()) return;
        }

        String courseId = readNonEmpty("Course ID [unique]: ");
        if (courseId == null || idExists(courseId)) {
            if (courseId != null) System.out.println("! ID already exists.");
            return;
        }
        String code = readNonEmpty("Course code [unique]: ");
        if (code == null || idExists(code)) {
            if (code != null) System.out.println("! Code already exists.");
            return;
        }
        String title = readNonEmpty("Course title: ");
        if (title == null) return;
        int credits = readInt("Credit hours [1-6]: ", 1, 6);
        if (credits == -1) return;

        System.out.println("Select Department:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + ". " + departments.get(i).getDisplayName());
        }
        int dsel = readInt("Select: ", 1, departments.size());
        if (dsel == -1) return;
        Department dept = departments.get(dsel - 1);

        System.out.println("Select Teacher:");
        for (int i = 0; i < teachers.size(); i++) {
            System.out.println((i + 1) + ". " + teachers.get(i).getDisplayName());
        }
        int tsel = readInt("Select: ", 1, teachers.size());
        if (tsel == -1) return;
        Teacher teacher = teachers.get(tsel - 1);

        try {
            Course c = new Course(courseId, code, title, credits, dept, teacher);
            c.validate();
            courses.add(c);
            System.out.println("+ Course " + code + " registered successfully.");
            c.printDetails();
        } catch (ValidationException e) {
            System.out.println("! Validation failed: " + e.getMessage());
        }
    }

    private static void registerClassroom() {
        System.out.println("\n------------- REGISTER CLASSROOM -------------");
        String id = readNonEmpty("Classroom ID [unique]: ");
        if (id == null || idExists(id)) {
            if (id != null) System.out.println("! ID already exists.");
            return;
        }
        String building = readNonEmpty("Building name: ");
        if (building == null) return;
        String room = readNonEmpty("Room number: ");
        if (room == null) return;
        int capacity = readInt("Capacity [>0]: ", 1, 1000);
        if (capacity == -1) return;
        int floor = readInt("Floor number [>=0]: ", 0, 50);
        if (floor == -1) return;
        System.out.println("Available [1 YES / 2 NO]: ");
        int av = readInt("Select: ", 1, 2);
        if (av == -1) return;

        try {
            Classroom cl = new Classroom(id, building, room, capacity, floor, av == 1);
            cl.validate();
            classrooms.add(cl);
            System.out.println("+ Classroom " + id + " registered successfully.");
            cl.printDetails();
        } catch (ValidationException e) {
            System.out.println("! Validation failed: " + e.getMessage());
        }
    }

    private static void enrollStudent() {
        System.out.println("\n------------- ENROLL STUDENT -------------");
        if (students.isEmpty() || courses.isEmpty()) {
            System.out.println("Enrollment requires Student and Course.");
            if (students.isEmpty()) {
                System.out.println("1. Register Student now   0. Cancel");
                if (scanner.nextLine().trim().equals("1")) registerStudent();
                else return;
            }
            if (courses.isEmpty()) {
                System.out.println("1. Register Course now   0. Cancel");
                if (scanner.nextLine().trim().equals("1")) registerCourse();
                else return;
            }
            if (students.isEmpty() || courses.isEmpty()) return;
        }

        String enrId = readNonEmpty("Enrollment ID [unique]: ");
        if (enrId == null || idExists(enrId)) {
            if (enrId != null) System.out.println("! ID already exists.");
            return;
        }

        System.out.println("Select Student:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getRegistrationSummary());
        }
        int ssel = readInt("Select: ", 1, students.size());
        if (ssel == -1) return;
        Student student = students.get(ssel - 1);

        System.out.println("Select Course:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i).getRegistrationSummary());
        }
        int csel = readInt("Select: ", 1, courses.size());
        if (csel == -1) return;
        Course course = courses.get(csel - 1);

        String semester = readNonEmpty("Semester (e.g. 2026-1): ");
        if (semester == null) return;

        // Check duplicate active enrollment
        for (Enrollment e : enrollments) {
            if (e.getStudent().getId().equals(student.getId()) &&
                e.getCourse().getId().equals(course.getId()) &&
                e.getSemester().equalsIgnoreCase(semester) &&
                e.isActive()) {
                System.out.println("! Duplicate active enrollment for same student, course and semester.");
                return;
            }
        }

        LocalDate enrDate = readDate("Enrollment date: ");
        if (enrDate == null) return;

        try {
            Enrollment e = new Enrollment(enrId, student, course, enrDate, semester, "ACTIVE");
            e.validate();
            enrollments.add(e);
            System.out.println("+ Enrollment " + enrId + " registered successfully.");
            e.printDetails();
        } catch (ValidationException ex) {
            System.out.println("! Validation failed: " + ex.getMessage());
        }
    }

    private static void recordAssessment() {
        System.out.println("\n------------- RECORD ASSESSMENT -------------");
        if (enrollments.isEmpty()) {
            System.out.println("No Enrollment exists. Register enrollment first.");
            return;
        }

        String assId = readNonEmpty("Assessment ID [unique]: ");
        if (assId == null || idExists(assId)) {
            if (assId != null) System.out.println("! ID already exists.");
            return;
        }

        System.out.println("Select Enrollment (active only):");
        List<Enrollment> active = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.isActive()) {
                active.add(e);
                System.out.println(active.size() + ". " + e.getRegistrationSummary());
            }
        }
        if (active.isEmpty()) {
            System.out.println("No active enrollments available.");
            return;
        }
        int esel = readInt("Select: ", 1, active.size());
        if (esel == -1) return;
        Enrollment enr = active.get(esel - 1);

        System.out.println("Assessment type [1 QUIZ / 2 MIDTERM / 3 FINAL / 4 ASSIGNMENT]: ");
        int ty = readInt("Select: ", 1, 4);
        if (ty == -1) return;
        String type;
        switch (ty) {
            case 1:
                type = "QUIZ";
                break;
            case 2:
                type = "MIDTERM";
                break;
            case 3:
                type = "FINAL";
                break;
            default:
                type = "ASSIGNMENT";
                break;
        }

        System.out.print("Maximum score: ");
        double maxScore;
        try {
            maxScore = Double.parseDouble(scanner.nextLine().trim());
            if (maxScore <= 0) {
                System.out.println("! Maximum score must be > 0");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("! Invalid number.");
            return;
        }

        System.out.print("Obtained score: ");
        double obtScore;
        try {
            obtScore = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("! Invalid number.");
            return;
        }

        LocalDate assDate = readDate("Assessment date: ");
        if (assDate == null) return;

        try {
            Assessment a = new Assessment(assId, enr, type, maxScore, obtScore, assDate);
            a.validate();
            assessments.add(a);
            System.out.println("+ Assessment " + assId + " recorded successfully.");
            a.printDetails();
        } catch (ValidationException e) {
            System.out.println("! Validation failed: " + e.getMessage());
        }
    }

    private static void recordFeePayment() {
        System.out.println("\n------------- RECORD FEE PAYMENT -------------");
        if (students.isEmpty()) {
            System.out.println("No Student exists. Register a student first.");
            return;
        }

        String payId = readNonEmpty("Payment ID [unique]: ");
        if (payId == null || idExists(payId)) {
            if (payId != null) System.out.println("! ID already exists.");
            return;
        }

        System.out.println("Select Student:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getDisplayName());
        }
        int ssel = readInt("Select: ", 1, students.size());
        if (ssel == -1) return;
        Student student = students.get(ssel - 1);

        BigDecimal amount = readMoney("Amount: ");
        if (amount == null) return;
        LocalDate payDate = readDate("Payment date: ");
        if (payDate == null) return;

        System.out.println("Payment method [1 CASH / 2 BANK / 3 MOBILE / 4 CARD]: ");
        int pm = readInt("Select: ", 1, 4);
        if (pm == -1) return;
        String method;
        switch (pm) {
            case 1:
                method = "CASH";
                break;
            case 2:
                method = "BANK";
                break;
            case 3:
                method = "MOBILE";
                break;
            default:
                method = "CARD";
                break;
        }

        String receipt = readNonEmpty("Receipt number [unique]: ");
        if (receipt == null) return;
        for (FeePayment f : feePayments) {
            if (f.getReceiptNumber().equalsIgnoreCase(receipt)) {
                System.out.println("! Duplicate receipt number.");
                return;
            }
        }

        try {
            FeePayment fp = new FeePayment(payId, student, amount, payDate, method, receipt);
            fp.validate();
            feePayments.add(fp);
            System.out.println("+ Fee payment " + payId + " recorded successfully.");
            fp.printDetails();
        } catch (ValidationException e) {
            System.out.println("! Validation failed: " + e.getMessage());
        }
    }

    private static void registerLibraryBook() {
        System.out.println("\n------------- REGISTER LIBRARY BOOK -------------");
        String bookId = readNonEmpty("Book ID [unique]: ");
        if (bookId == null || idExists(bookId)) {
            if (bookId != null) System.out.println("! ID already exists.");
            return;
        }
        String isbn = readNonEmpty("ISBN: ");
        if (isbn == null) return;
        String title = readNonEmpty("Title: ");
        if (title == null) return;
        String author = readNonEmpty("Author: ");
        if (author == null) return;
        int total = readInt("Total copies [>=0]: ", 0, 1000);
        if (total == -1) return;
        int avail = readInt("Available copies [0-" + total + "]: ", 0, total);
        if (avail == -1) return;

        try {
            LibraryBook b = new LibraryBook(bookId, isbn, title, author, total, avail);
            b.validate();
            libraryBooks.add(b);
            System.out.println("+ Library book " + bookId + " registered successfully.");
            b.printDetails();
        } catch (ValidationException e) {
            System.out.println("! Validation failed: " + e.getMessage());
        }
    }

    private static void borrowBook() {
        System.out.println("\n------------- BORROW LIBRARY BOOK -------------");
        if (students.isEmpty() || libraryBooks.isEmpty()) {
            System.out.println("Borrow requires Student and LibraryBook.");
            return;
        }

        String loanId = readNonEmpty("Loan ID [unique]: ");
        if (loanId == null || idExists(loanId)) {
            if (loanId != null) System.out.println("! ID already exists.");
            return;
        }

        System.out.println("Select Student:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getDisplayName());
        }
        int ssel = readInt("Select: ", 1, students.size());
        if (ssel == -1) return;
        Student student = students.get(ssel - 1);

        System.out.println("Select Book (available only):");
        List<LibraryBook> available = new ArrayList<>();
        for (LibraryBook b : libraryBooks) {
            if (b.getAvailableCopies() > 0) {
                available.add(b);
                System.out.println(available.size() + ". " + b.getRegistrationSummary());
            }
        }
        if (available.isEmpty()) {
            System.out.println("No available books.");
            return;
        }
        int bsel = readInt("Select: ", 1, available.size());
        if (bsel == -1) return;
        LibraryBook book = available.get(bsel - 1);

        LocalDate borrowed = readDate("Borrowed date: ");
        if (borrowed == null) return;
        LocalDate due = readDate("Due date (must be after borrowed): ");
        if (due == null) return;

        try {
            BookLoan loan = new BookLoan(loanId, student, book, borrowed, due, null);
            loan.validate();
            // Update stock
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookLoans.add(loan);
            System.out.println("+ Book loan " + loanId + " created successfully. Available copies now: " + book.getAvailableCopies());
            loan.printDetails();
        } catch (ValidationException e) {
            System.out.println("! Validation failed: " + e.getMessage());
        }
    }

    private static void returnBook() {
        System.out.println("\n------------- RETURN LIBRARY BOOK -------------");
        List<BookLoan> activeLoans = new ArrayList<>();
        for (BookLoan l : bookLoans) {
            if (l.isActive()) {
                activeLoans.add(l);
                System.out.println(activeLoans.size() + ". " + l.getRegistrationSummary());
            }
        }
        if (activeLoans.isEmpty()) {
            System.out.println("No active loans to return.");
            return;
        }
        int sel = readInt("Select loan to return: ", 1, activeLoans.size());
        if (sel == -1) return;
        BookLoan loan = activeLoans.get(sel - 1);

        LocalDate retDate = readDate("Returned date: ");
        if (retDate == null) return;
        if (retDate.isBefore(loan.getBorrowedDate())) {
            System.out.println("! Returned date cannot be before borrowed date.");
            return;
        }

        loan.setReturnedDate(retDate);
        LibraryBook book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        System.out.println("+ Book returned successfully. Available copies now: " + book.getAvailableCopies());
        loan.printDetails();
    }

    // ==================== VIEW / SEARCH / REPORT ====================

    private static void viewAllRecords() {
        System.out.println("\n============= ALL RECORDS =============");
        System.out.println("\n--- DEPARTMENTS (" + departments.size() + ") ---");
        departments.forEach(d -> System.out.println(d.getRegistrationSummary()));
        System.out.println("\n--- STUDENTS (" + students.size() + ") ---");
        students.forEach(s -> System.out.println(s.getRegistrationSummary()));
        System.out.println("\n--- TEACHERS (" + teachers.size() + ") ---");
        teachers.forEach(t -> System.out.println(t.getRegistrationSummary()));
        System.out.println("\n--- ADMINISTRATORS (" + administrators.size() + ") ---");
        administrators.forEach(a -> System.out.println(a.getRegistrationSummary()));
        System.out.println("\n--- COURSES (" + courses.size() + ") ---");
        courses.forEach(c -> System.out.println(c.getRegistrationSummary()));
        System.out.println("\n--- CLASSROOMS (" + classrooms.size() + ") ---");
        classrooms.forEach(c -> System.out.println(c.getRegistrationSummary()));
        System.out.println("\n--- ENROLLMENTS (" + enrollments.size() + ") ---");
        enrollments.forEach(e -> System.out.println(e.getRegistrationSummary()));
        System.out.println("\n--- ASSESSMENTS (" + assessments.size() + ") ---");
        assessments.forEach(a -> System.out.println(a.getRegistrationSummary()));
        System.out.println("\n--- FEE PAYMENTS (" + feePayments.size() + ") ---");
        feePayments.forEach(f -> System.out.println(f.getRegistrationSummary()));
        System.out.println("\n--- LIBRARY BOOKS (" + libraryBooks.size() + ") ---");
        libraryBooks.forEach(b -> System.out.println(b.getRegistrationSummary()));
        System.out.println("\n--- BOOK LOANS (" + bookLoans.size() + ") ---");
        bookLoans.forEach(l -> System.out.println(l.getRegistrationSummary()));
    }

    private static void searchById() {
        System.out.print("Enter ID to search: ");
        String id = scanner.nextLine().trim();
        boolean found = false;
        for (Department d : departments) if (d.getId().equalsIgnoreCase(id)) { d.printDetails(); found = true; }
        for (Student s : students) if (s.getId().equalsIgnoreCase(id) || s.getStudentNumber().equalsIgnoreCase(id)) { s.printDetails(); found = true; }
        for (Teacher t : teachers) if (t.getId().equalsIgnoreCase(id) || t.getEmployeeNumber().equalsIgnoreCase(id)) { t.printDetails(); found = true; }
        for (Administrator a : administrators) if (a.getId().equalsIgnoreCase(id) || a.getStaffNumber().equalsIgnoreCase(id)) { a.printDetails(); found = true; }
        for (Course c : courses) if (c.getId().equalsIgnoreCase(id) || c.getCourseCode().equalsIgnoreCase(id)) { c.printDetails(); found = true; }
        for (Classroom cl : classrooms) if (cl.getId().equalsIgnoreCase(id)) { cl.printDetails(); found = true; }
        for (Enrollment e : enrollments) if (e.getId().equalsIgnoreCase(id)) { e.printDetails(); found = true; }
        for (Assessment a : assessments) if (a.getId().equalsIgnoreCase(id)) { a.printDetails(); found = true; }
        for (FeePayment f : feePayments) if (f.getId().equalsIgnoreCase(id) || f.getReceiptNumber().equalsIgnoreCase(id)) { f.printDetails(); found = true; }
        for (LibraryBook b : libraryBooks) if (b.getId().equalsIgnoreCase(id)) { b.printDetails(); found = true; }
        for (BookLoan l : bookLoans) if (l.getId().equalsIgnoreCase(id)) { l.printDetails(); found = true; }
        if (!found) System.out.println("No record found with ID: " + id);
    }

    private static void updateRecord() {
        System.out.println("Update is limited in this version. Use Search + manual edit via re-registration for complex cases.");
        System.out.println("You can update Classroom availability or Book stock via dedicated flows.");
        System.out.print("Enter Classroom ID to toggle availability (or blank to skip): ");
        String cid = scanner.nextLine().trim();
        if (!cid.isEmpty()) {
            for (Classroom cl : classrooms) {
                if (cl.getId().equalsIgnoreCase(cid)) {
                    cl.setAvailable(!cl.isAvailable());
                    System.out.println("Classroom availability toggled to: " + cl.isAvailable());
                    return;
                }
            }
            System.out.println("Classroom not found.");
        }
    }

    private static void showStudentAcademicSummary() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        System.out.println("Select Student:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getDisplayName());
        }
        int sel = readInt("Select: ", 1, students.size());
        if (sel == -1) return;
        Student s = students.get(sel - 1);
        System.out.println("\n=== Academic Summary for " + s.getDisplayName() + " ===");
        System.out.println(s.getRegistrationSummary());
        System.out.println("Role: " + s.getRoleDescription());
        System.out.println("Category: " + s.getAcademicCategory());
        System.out.println("\nEnrollments:");
        for (Enrollment e : enrollments) {
            if (e.getStudent().getId().equals(s.getId())) {
                System.out.println("  " + e.getRegistrationSummary());
            }
        }
        System.out.println("\nAssessments:");
        for (Assessment a : assessments) {
            if (a.getEnrollment().getStudent().getId().equals(s.getId())) {
                System.out.println("  " + a.getRegistrationSummary());
            }
        }
    }

    private static void showPaymentHistory() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        System.out.println("Select Student:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getDisplayName());
        }
        int sel = readInt("Select: ", 1, students.size());
        if (sel == -1) return;
        Student s = students.get(sel - 1);
        System.out.println("\n=== Payment History for " + s.getDisplayName() + " ===");
        boolean any = false;
        for (FeePayment f : feePayments) {
            if (f.getStudent().getId().equals(s.getId())) {
                System.out.println(f.getRegistrationSummary());
                any = true;
            }
        }
        if (!any) System.out.println("No payments recorded.");
    }

    private static void showAvailableBooks() {
        System.out.println("\n=== Available Library Books ===");
        boolean any = false;
        for (LibraryBook b : libraryBooks) {
            if (b.getAvailableCopies() > 0) {
                System.out.println(b.getRegistrationSummary());
                any = true;
            }
        }
        if (!any) System.out.println("No books currently available.");
    }

    private static void demonstratePolymorphism() {
        System.out.println("\n=== POLYMORPHISM DEMONSTRATION ===");
        System.out.println("\n1. List<Person> with Student, Teacher, Administrator:");
        List<Person> people = new ArrayList<>();
        if (!students.isEmpty()) people.add(students.get(0));
        if (!teachers.isEmpty()) people.add(teachers.get(0));
        if (!administrators.isEmpty()) people.add(administrators.get(0));

        if (people.isEmpty()) {
            System.out.println("  (Register some people first to see live examples)");
        } else {
            for (Person p : people) {
                System.out.println("  -> " + p.getDisplayName() + " | Role: " + p.getRoleDescription() +
                        " | Active: " + p.isActive());
            }
        }

        System.out.println("\n2. List<Identifiable> with mixed entities:");
        List<Identifiable> items = new ArrayList<>();
        if (!departments.isEmpty()) items.add(departments.get(0));
        if (!courses.isEmpty()) items.add(courses.get(0));
        if (!libraryBooks.isEmpty()) items.add(libraryBooks.get(0));
        if (!students.isEmpty()) items.add(students.get(0));

        if (items.isEmpty()) {
            System.out.println("  (Register some records first)");
        } else {
            for (Identifiable i : items) {
                System.out.println("  -> ID: " + i.getId() + " | Name: " + i.getDisplayName() +
                        " | Active: " + i.isActive());
            }
        }

        System.out.println("\n3. Why Person and AcademicMember cannot be instantiated:");
        System.out.println("   They are abstract classes. Abstract classes provide partial");
        System.out.println("   implementation and force subclasses to implement abstract methods");
        System.out.println("   (getRoleDescription / getAcademicCategory). Instantiating them");
        System.out.println("   directly would leave those methods undefined.");
        System.out.println("\n=== End of Polymorphism Demo ===");
    }
}
