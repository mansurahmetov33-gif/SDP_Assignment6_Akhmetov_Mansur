
package org.inspection.visitor;

import org.inspection.element.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ComplianceVisitor implements ProjectVisitor {

    private final List<String> violations = new ArrayList<>();
    private final Set<String> sourceClasses = new HashSet<>();
    private final Set<String> testedClasses = new HashSet<>();

    @Override
    public void visit(SourceCodeFile file) {
        String className = file.getName().replaceFirst("\\.java$", "");
        sourceClasses.add(className);

        if (file.hasHardcodedCredentials()) {
            violations.add(
                    "Exposed credentials in " + file.getName()
            );
        }
    }

    @Override
    public void visit(TestFile file) {
        testedClasses.add(file.getTargetClass());
    }

    @Override
    public void visit(ConfigurationFile file) {
        if (file.hasExposedCredentials()) {
            violations.add(
                    "Exposed credentials in " + file.getName()
            );
        }
    }

    @Override
    public void visit(DependencyFile file) {
        if (file.hasMissingVersions()) {
            violations.add(
                    "Missing dependency versions in " + file.getName()
            );
        }

        if (file.hasProhibitedDependencies()) {
            violations.add(
                    "Prohibited dependencies in " + file.getName()
            );
        }
    }

    @Override
    public void visit(Directory directory) {
        // No direct compliance rules for directories
    }

    public List<String> getViolations() {
        List<String> result = new ArrayList<>(violations);

        for (String sourceClass : sourceClasses) {
            if (!testedClasses.contains(sourceClass)) {
                result.add("Missing tests for " + sourceClass);
            }
        }

        return result;
    }

    public boolean isCompliant() {
        return getViolations().isEmpty();
    }

    public void printReport() {
        System.out.println("=== COMPLIANCE REPORT ===");

        List<String> allViolations = getViolations();

        if (allViolations.isEmpty()) {
            System.out.println("No compliance violations found.");
        } else {
            for (String violation : allViolations) {
                System.out.println("- " + violation);
            }
        }

        System.out.println("FINAL RESULT: " +
                (isCompliant() ? "COMPLIANT" : "NON-COMPLIANT"));
    }
}
