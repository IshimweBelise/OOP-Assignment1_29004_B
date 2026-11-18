# AUCA_ID – School Management System

Console-based OOP Java application fulfilling the AUCA Practical Assignment requirements.

## Project Structure

```
AUCA_ID/
├── src/com/auca/
│   ├── interfaces/     → Identifiable, Registrable
│   ├── abstracts/      → Person, AcademicMember
│   ├── exceptions/     → 5 custom exception classes
│   ├── main/           → Main (entry point + menu)
│   ├── student/
│   ├── teacher/
│   ├── administrator/
│   ├── department/
│   ├── course/
│   ├── classroom/
│   ├── enrollment/
│   ├── assessment/
│   ├── feepayment/
│   ├── librarybook/
│   └── bookloan/
├── bin/                → compiled .class files
└── dist/AUCA_ID.jar
```

## How to Compile & Run

```bash
cd AUCA_ID
javac -d bin -sourcepath src $(find src -name "*.java")
java -cp bin com.auca.main.Main
```

Or run the JAR (after creating a manifest if desired):

```bash
java -cp bin com.auca.main.Main
```

## Features Implemented

- Exact package structure and 15 domain types (2 interfaces + 2 abstracts + 11 concretes)
- Encapsulation, multilevel & hierarchical inheritance
- Interfaces, abstract classes, polymorphism
- Object relationships with object references (not IDs as text)
- Full validation + custom exceptions
- Smart registration forms with prerequisite handling
- Single shared Scanner
- Menu with all required options (0–19)
- Book borrow/return with stock updates
- Polymorphism demonstration

## Notes for Demonstration

1. Register a Department first.
2. Then register Teacher / Student (they require a Department).
3. Register Course (requires Department + Teacher).
4. Enroll, assess, pay fees, manage library books.
5. Option 19 shows runtime polymorphism.
6. Invalid data is rejected with clear messages; the application never crashes.
