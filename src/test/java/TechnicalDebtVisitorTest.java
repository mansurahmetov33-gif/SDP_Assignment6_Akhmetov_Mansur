
package org.inspection;

import org.inspection.element.*;
import org.inspection.visitor.TechnicalDebtVisitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TechnicalDebtVisitorTest {

    @Test
    void detectsHighComplexityAndLargeFile() {
        SourceCodeFile file = new SourceCodeFile(
                "LargeService.java", 400, 15, false, false
        );

        TechnicalDebtVisitor visitor = new TechnicalDebtVisitor();
        file.accept(visitor);

        assertEquals(30, visitor.getDebtScore());
        assertEquals(3, visitor.getIssues().size());
    }

    @Test
    void detectsMissingTests() {
        Directory project = new Directory("project");

        project.add(new SourceCodeFile(
                "Service.java", 100, 5, false, false
        ));

        TechnicalDebtVisitor visitor = new TechnicalDebtVisitor();
        project.accept(visitor);

        assertEquals(15, visitor.getDebtScore());
        assertTrue(visitor.getIssues()
                .contains("Missing tests for Service"));
    }

    @Test
    void testedProjectHasNoDebt() {
        Directory project = new Directory("project");

        project.add(new SourceCodeFile(
                "Service.java", 100, 5, false, false
        ));

        project.add(new TestFile(
                "ServiceTest.java", 80, 5, "Service"
        ));

        TechnicalDebtVisitor visitor = new TechnicalDebtVisitor();
        project.accept(visitor);

        assertEquals(0, visitor.getDebtScore());
        assertTrue(visitor.getIssues().isEmpty());
    }
}
