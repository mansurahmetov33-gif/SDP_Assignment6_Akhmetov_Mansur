# SDP Assignment 6 — Smart Code Inspection & Compliance Engine

**Student:** Mansur Akhmetov  
**Course:** Software Design Patterns  
**Design Pattern:** Visitor  
**Language:** Java 17+  
**Build Tool:** Maven  
**Testing:** JUnit 5

## 1. Project Description

This project is a Smart Code Inspection & Compliance Engine implemented using the Visitor Design Pattern.

The main purpose of this application is to analyze different elements of a software project and generate reports about security, statistics, code quality, compliance, and technical debt.

The Visitor Pattern allows us to separate project elements from the operations performed on them.

The application uses a sample fintech project containing source code files, test files, configuration files, dependency files, and directories.

## 2. Project Structure

```text
src/
├── main/java/org/inspection/
│   ├── element/
│   │   ├── ProjectElement.java
│   │   ├── SourceCodeFile.java
│   │   ├── TestFile.java
│   │   ├── ConfigurationFile.java
│   │   ├── DependencyFile.java
│   │   └── Directory.java
│   │
│   ├── visitor/
│   │   ├── ProjectVisitor.java
│   │   ├── SecurityAuditVisitor.java
│   │   ├── ProjectStatisticsVisitor.java
│   │   ├── QualityGateVisitor.java
│   │   ├── ComplianceVisitor.java
│   │   └── TechnicalDebtVisitor.java
│   │
│   └── Main.java
│
└── test/java/org/inspection/
    ├── SecurityAuditVisitorTest.java
    ├── ProjectStatisticsVisitorTest.java
    ├── QualityGateVisitorTest.java
    ├── ComplianceVisitorTest.java
    └── TechnicalDebtVisitorTest.java

docs/
├── class-diagram.puml
└── sequence-diagram.puml
```

## 3. Implemented Visitors

### SecurityAuditVisitor

Checks security problems such as hardcoded credentials, vulnerable dependencies, insecure configurations, and high-risk source files.

It generates a list of issues and calculates a security score.

### ProjectStatisticsVisitor

Calculates general project statistics:

- Total number of files
- Number of source and test files
- Lines of code
- Number of tests
- Number of dependencies
- Average complexity
- Test/source ratio

### QualityGateVisitor

Checks whether the project is ready for release.

The conditions are:

- Average complexity ≤ 10
- Test/source ratio ≥ 0.40
- Critical security issues = 0

If one condition fails, the final result is FAILED.

### ComplianceVisitor

Checks development rules such as exposed credentials, missing dependency versions, missing tests for source files, and prohibited dependencies.

### TechnicalDebtVisitor

This is my additional Visitor.

It calculates a technical debt score based on code complexity, large source files, missing tests, vulnerable dependencies, and debug configuration.

It was added without modifying the existing element classes.

## 4. How to Run

Requirements:

- Java 17 or higher
- Maven

Run the application:

```bash
mvn compile
mvn exec:java -Dexec.mainClass="org.inspection.Main"
```

Alternatively, run the `Main` class directly from IntelliJ IDEA.

Run all unit tests:

```bash
mvn clean test
```

## 5. Testing

The project includes 18 JUnit 5 test methods.

The tests cover security detection, statistics calculations, Quality Gate PASS and FAIL cases, compliance violations, nested directories, empty directories, and technical debt calculation.

## 6. UML Diagrams

The project contains two UML diagrams:

**Class Diagram:** Shows the ProjectElement and ProjectVisitor interfaces, concrete elements, concrete visitors, and their relationships.

**Sequence Diagram:** Shows how a SourceCodeFile accepts a SecurityAuditVisitor and calls the corresponding visit() method.

The diagrams are stored in the `docs` directory.

## 7. Visitor Pattern Questions

### 1. What problem does Visitor solve in your project?

Visitor allows us to perform different operations on project elements without adding all operations directly into the element classes.

For example, SourceCodeFile does not need separate methods for security checking, statistics, compliance, and technical debt. These operations are implemented in separate Visitor classes.

### 2. Where are Element and Visitor?

ProjectElement is the main Element interface. It is implemented by SourceCodeFile, TestFile, ConfigurationFile, DependencyFile, and Directory.

ProjectVisitor is the Visitor interface. It is implemented by SecurityAuditVisitor, ProjectStatisticsVisitor, QualityGateVisitor, ComplianceVisitor, and TechnicalDebtVisitor.

### 3. What is double dispatch?

Double dispatch means that the executed operation depends on both the element type and the visitor type.

First, the element receives a Visitor through accept(). Then it calls visitor.visit(this).

For example:

```java
payment.accept(securityVisitor);
```

Inside SourceCodeFile:

```java
@Override
public void accept(ProjectVisitor visitor) {
    visitor.visit(this);
}
```

This calls visit(SourceCodeFile file) in SecurityAuditVisitor.

### 4. Why not put all operations directly inside the Element classes?

Because the element classes would become too large and have too many responsibilities.

Every time we add a new operation, we would need to modify multiple element classes.

Visitor keeps the element data and the inspection operations separate, making the code easier to maintain.

### 5. Is it easier to add a new Visitor or a new Element? Why?

It is easier to add a new Visitor.

We can create a new class implementing ProjectVisitor without changing existing element classes.

For example, TechnicalDebtVisitor was added separately.

Adding a new Element is harder because we must update the ProjectVisitor interface and all existing Visitor implementations.

### 6. When would Visitor be a bad choice?

Visitor is not a good choice when the element hierarchy changes frequently.

Every new element type requires changes to the Visitor interface and all concrete Visitors.

It can also add unnecessary complexity to very small projects.

### 7. How is Visitor different from Strategy?

Strategy is used to choose between different algorithms for the same task.

Visitor is used to perform different operations on multiple types of objects.

For example, Strategy could select a route calculation algorithm, while Visitor can inspect source files, test files, and configuration files using different operations.

### 8. Which SOLID principles can you identify?

**Single Responsibility Principle (SRP):** Each Visitor is responsible for one type of inspection.

**Open/Closed Principle (OCP):** New operations can be added through new Visitors without modifying existing element classes. However, adding new element types requires changes to Visitors.

**Dependency Inversion Principle (DIP):** Elements interact with the ProjectVisitor interface instead of depending on concrete Visitor implementations.

## 8. Visitor Limitation Challenge

### What happens if we add DatabaseMigrationFile?

If we add a new element called DatabaseMigrationFile, we need to make several changes.

First, create a new class implementing ProjectElement:

```java
public class DatabaseMigrationFile implements ProjectElement {

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }
}
```

Then add a new method to ProjectVisitor:

```java
void visit(DatabaseMigrationFile file);
```

After that, every existing Visitor must implement the new visit() method:

- SecurityAuditVisitor
- ProjectStatisticsVisitor
- QualityGateVisitor
- ComplianceVisitor
- TechnicalDebtVisitor

Otherwise, the concrete Visitor classes will not compile.

This demonstrates the main limitation of Visitor: adding new operations is easy, but adding new element types requires modifying existing Visitor implementations.

## 9. Conclusion

In this assignment, I implemented the Visitor Design Pattern to build a code inspection system.

The project demonstrates how different operations can be separated from the objects they process.

I also implemented unit tests, UML diagrams, and an additional TechnicalDebtVisitor.

The main advantage of Visitor is that new inspection operations can be added without modifying existing project elements. Its main limitation is that adding new element types requires changes to all Visitors.