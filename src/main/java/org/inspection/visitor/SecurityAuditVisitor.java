
package org.inspection.visitor;

import org.inspection.element.*;
import java.util.ArrayList;
import java.util.List;

public class SecurityAuditVisitor implements ProjectVisitor {

    private final List<String> issues = new ArrayList<>();
    private int criticalIssues = 0;

    @Override
    public void visit(SourceCodeFile file) {
        if (file.hasHardcodedCredentials()) {
            issues.add("Hardcoded credentials in " + file.getName());
            criticalIssues++;
        }

        if (file.isHighRisk()) {
            issues.add("High-risk source file: " + file.getName());
            criticalIssues++;
        }
    }

    @Override
    public void visit(TestFile file) {
        // No security checks for test files
    }

    @Override
    public void visit(ConfigurationFile file) {
        if (file.isDebugEnabled()) {
            issues.add("Debug mode enabled in " + file.getName());
        }

        if (!file.isEncryptionEnabled()) {
            issues.add("Encryption disabled in " + file.getName());
            criticalIssues++;
        }

        if (file.hasExposedCredentials()) {
            issues.add("Exposed credentials in " + file.getName());
            criticalIssues++;
        }
    }

    @Override
    public void visit(DependencyFile file) {
        if (file.hasVulnerableDependencies()) {
            issues.add("Vulnerable dependencies in " + file.getName());
            criticalIssues++;
        }
    }

    @Override
    public void visit(Directory directory) {
        // Directory itself has no security issues
    }

    public List<String> getIssues() {
        return List.copyOf(issues);
    }

    public int getCriticalIssues() {
        return criticalIssues;
    }

    public int getSecurityScore() {
        return Math.max(0, 100 - criticalIssues * 20
                - (issues.size() - criticalIssues) * 5);
    }

    public void printReport() {
        System.out.println("=== SECURITY AUDIT ===");

        for (String issue : issues) {
            System.out.println("- " + issue);
        }

        System.out.println("Critical issues: " + criticalIssues);
        System.out.println("Security score: " + getSecurityScore());
    }
}
