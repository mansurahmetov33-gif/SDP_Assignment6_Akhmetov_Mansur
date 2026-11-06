
package org.inspection;

import org.inspection.element.*;
import org.inspection.visitor.ComplianceVisitor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ComplianceVisitorTest {

    @Test
    void detectsMissingTests() {
        Directory project = new Directory("project");

        project.add(new SourceCodeFile(
                "PaymentService.java", 200, 8, false, false
        ));

        ComplianceVisitor visitor = new ComplianceVisitor();
        project.accept(visitor);

        assertFalse(visitor.isCompliant());
        assertTrue(visitor.getViolations()
                .contains("Missing tests for PaymentService"));
    }

    @Test
    void detectsDependencyViolations() {
        DependencyFile file = new DependencyFile(
                "pom.xml",
                List.of("unsafe-library"),
                false, true, true
        );

        ComplianceVisitor visitor = new ComplianceVisitor();
        file.accept(visitor);

        assertEquals(2, visitor.getViolations().size());
        assertFalse(visitor.isCompliant());
    }

    @Test
    void compliantProjectPasses() {
        Directory project = new Directory("good-project");

        project.add(new SourceCodeFile(
                "Service.java", 100, 5, false, false
        ));

        project.add(new TestFile(
                "ServiceTest.java", 80, 4, "Service"
        ));

        project.add(new DependencyFile(
                "pom.xml",
                List.of("junit"),
                false, false, false
        ));

        ComplianceVisitor visitor = new ComplianceVisitor();
        project.accept(visitor);

        assertTrue(visitor.isCompliant());
        assertTrue(visitor.getViolations().isEmpty());
    }

    @Test
    void detectsExposedCredentials() {
        ConfigurationFile config = new ConfigurationFile(
                "application.properties", false, true, true
        );

        ComplianceVisitor visitor = new ComplianceVisitor();
        config.accept(visitor);

        assertFalse(visitor.isCompliant());
        assertTrue(visitor.getViolations().get(0)
                .contains("Exposed credentials"));
    }
}
