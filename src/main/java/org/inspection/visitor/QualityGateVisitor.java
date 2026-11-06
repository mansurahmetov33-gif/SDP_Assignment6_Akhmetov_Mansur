
package org.inspection.visitor;

import org.inspection.element.*;

public class QualityGateVisitor implements ProjectVisitor {

    private final ProjectStatisticsVisitor statistics =
            new ProjectStatisticsVisitor();

    private final SecurityAuditVisitor security =
            new SecurityAuditVisitor();

    @Override
    public void visit(SourceCodeFile file) {
        statistics.visit(file);
        security.visit(file);
    }

    @Override
    public void visit(TestFile file) {
        statistics.visit(file);
        security.visit(file);
    }

    @Override
    public void visit(ConfigurationFile file) {
        statistics.visit(file);
        security.visit(file);
    }

    @Override
    public void visit(DependencyFile file) {
        statistics.visit(file);
        security.visit(file);
    }

    @Override
    public void visit(Directory directory) {
        statistics.visit(directory);
        security.visit(directory);
    }

    public boolean isSecurityPassed() {
        return security.getCriticalIssues() == 0;
    }

    public boolean isComplexityPassed() {
        return statistics.getSourceFiles() > 0
                && statistics.getAverageComplexity() <= 10;
    }

    public boolean isTestsPassed() {
        return statistics.getSourceFiles() > 0
                && statistics.getTestSourceRatio() >= 0.40;
    }

    public boolean isPassed() {
        return isSecurityPassed()
                && isComplexityPassed()
                && isTestsPassed();
    }

    public void printReport() {
        System.out.println("=== QUALITY GATE ===");

        System.out.println("Security: " +
                (isSecurityPassed() ? "PASS" : "FAIL"));

        System.out.println("Complexity: " +
                (isComplexityPassed() ? "PASS" : "FAIL"));

        System.out.println("Tests: " +
                (isTestsPassed() ? "PASS" : "FAIL"));

        System.out.println();
        System.out.println("FINAL RESULT: " +
                (isPassed() ? "PASSED" : "FAILED"));
    }
}
