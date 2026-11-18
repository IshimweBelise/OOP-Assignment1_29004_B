package com.auca.bookloan;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.librarybook.LibraryBook;
import com.auca.student.Student;

import java.time.LocalDate;

public class BookLoan implements Identifiable, Registrable {
    private String loanId;
    private Student student;
    private LibraryBook book;
    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    public BookLoan(String loanId, Student student, LibraryBook book,
                    LocalDate borrowedDate, LocalDate dueDate, LocalDate returnedDate) {
        this.loanId = loanId;
        this.student = student;
        this.book = book;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
    }

    public String getLoanId() { return loanId; }
    public void setLoanId(String loanId) { this.loanId = loanId; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public LibraryBook getBook() { return book; }
    public void setBook(LibraryBook book) { this.book = book; }

    public LocalDate getBorrowedDate() { return borrowedDate; }
    public void setBorrowedDate(LocalDate borrowedDate) { this.borrowedDate = borrowedDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnedDate() { return returnedDate; }
    public void setReturnedDate(LocalDate returnedDate) { this.returnedDate = returnedDate; }

    @Override
    public String getId() { return loanId; }

    @Override
    public String getDisplayName() {
        return (student != null ? student.getDisplayName() : "N/A") + " borrowed " +
                (book != null ? book.getTitle() : "N/A");
    }

    @Override
    public boolean isActive() {
        return returnedDate == null;
    }

    @Override
    public void validate() throws ValidationException {
        if (loanId == null || loanId.trim().isEmpty()) {
            throw new ValidationException("Loan ID cannot be blank");
        }
        if (student == null) {
            throw new ValidationException("Student is required");
        }
        if (book == null) {
            throw new ValidationException("Book is required");
        }
        if (borrowedDate == null) {
            throw new ValidationException("Borrowed date is required");
        }
        if (dueDate == null) {
            throw new ValidationException("Due date is required");
        }
        if (dueDate.isBefore(borrowedDate) || dueDate.isEqual(borrowedDate)) {
            throw new ValidationException("Due date must be after borrowed date");
        }
        if (returnedDate != null && returnedDate.isBefore(borrowedDate)) {
            throw new ValidationException("Returned date cannot be before borrowed date");
        }
    }

    @Override
    public void printDetails() {
        System.out.println(getRegistrationSummary());
    }

    @Override
    public String getRegistrationSummary() {
        return String.format("[%s] %s | Book: %s | Borrowed: %s | Due: %s | Returned: %s",
                loanId,
                student != null ? student.getDisplayName() : "N/A",
                book != null ? book.getTitle() : "N/A",
                borrowedDate, dueDate,
                returnedDate != null ? returnedDate.toString() : "Not returned");
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
