
package org.inspection.visitor;

import org.inspection.element.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TechnicalDebtVisitor implements ProjectVisitor {

    private final List<String> issues = new ArrayList<>();
    private final Set<String> sourceClasses = new HashSet<>();
    private final Set<String> testedClasses = new HashSet<>();

    private int debtScore = 0;

    @Override
    public void visit(SourceCodeFile file) {
        String className = file.getName().replaceFirst("\\.java$", "");
        sourceClasses.add(className);

        if (file.getComplexity() > 10) {
            issues.add("High complexity in " + file.getName());
            debtScore += 10;
        }

        if (file.getLinesOfCode() > 300) {
            issues.add("Large source file: " + file.getName());
            debtScore += 5;
        }
    }

    @Override
    public void visit(TestFile file) {
        testedClasses.add(file.getTargetClass());
    }

    @Override
    public void visit(ConfigurationFile file) {
        if (file.isDebugEnabled()) {
            issues.add("Debug mode enabled in " + file.getName());
            debtScore += 5;
        }
    }

    @Override
    public void visit(DependencyFile file) {
        if (file.hasVulnerableDependencies()) {
            issues.add("Vulnerable dependencies in " + file.getName());
            debtScore += 20;
        }
    }

    @Override
    public void visit(Directory directory) {
        // Directories do not add technical debt
    }

    public List<String> getIssues() {
        List<String> result = new ArrayList<>(issues);

        for (String sourceClass : sourceClasses) {
            if (!testedClasses.contains(sourceClass)) {
                result.add("Missing tests for " + sourceClass);
            }
        }

        return result;
    }

    public int getDebtScore() {
        int total = debtScore;

        for (String sourceClass : sourceClasses) {
            if (!testedClasses.contains(sourceClass)) {
                total += 15;
            }
        }

        return total;
    }

    public void printReport() {
        System.out.println("=== TECHNICAL DEBT REPORT ===");

        for (String issue : getIssues()) {
            System.out.println("- " + issue);
        }

        System.out.println("Total technical debt score: "
                + getDebtScore());
    }
}
