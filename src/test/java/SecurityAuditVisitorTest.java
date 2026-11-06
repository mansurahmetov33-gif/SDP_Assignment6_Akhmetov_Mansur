
package org.inspection;

import org.inspection.element.*;
import org.inspection.visitor.SecurityAuditVisitor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityAuditVisitorTest {

    @Test
    void detectsHardcodedCredentials() {
        SourceCodeFile file = new SourceCodeFile(
                "PaymentService.java", 200, 12, true, false
        );

        SecurityAuditVisitor visitor = new SecurityAuditVisitor();
        file.accept(visitor);

        assertEquals(1, visitor.getCriticalIssues());
        assertEquals(80, visitor.getSecurityScore());
    }

    @Test
    void detectsVulnerableDependencies() {
        DependencyFile file = new DependencyFile(
                "pom.xml",
                List.of("old-library"),
                true, false, false
        );

        SecurityAuditVisitor visitor = new SecurityAuditVisitor();
        file.accept(visitor);

        assertEquals(1, visitor.getCriticalIssues());
        assertTrue(visitor.getIssues().get(0)
                .contains("Vulnerable dependencies"));
    }

    @Test
    void safeFileHasNoIssues() {
        SourceCodeFile file = new SourceCodeFile(
                "SafeService.java", 100, 5, false, false
        );

        SecurityAuditVisitor visitor = new SecurityAuditVisitor();
        file.accept(visitor);

        assertEquals(0, visitor.getCriticalIssues());
        assertTrue(visitor.getIssues().isEmpty());
        assertEquals(100, visitor.getSecurityScore());
    }

    @Test
    void checksNestedDirectories() {
        Directory root = new Directory("root");
        Directory src = new Directory("src");

        src.add(new SourceCodeFile(
                "Risky.java", 100, 15, true, true
        ));

        root.add(src);

        SecurityAuditVisitor visitor = new SecurityAuditVisitor();
        root.accept(visitor);

        assertEquals(2, visitor.getCriticalIssues());
        assertEquals(2, visitor.getIssues().size());
    }

    @Test
    void emptyDirectoryHasNoIssues() {
        Directory empty = new Directory("empty");

        SecurityAuditVisitor visitor = new SecurityAuditVisitor();
        empty.accept(visitor);

        assertTrue(visitor.getIssues().isEmpty());
        assertEquals(100, visitor.getSecurityScore());
    }
}
