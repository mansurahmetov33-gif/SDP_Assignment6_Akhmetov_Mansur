
package org.inspection.visitor;

import org.inspection.element.*;

public class ProjectStatisticsVisitor implements ProjectVisitor {

    private int totalFiles = 0;
    private int sourceFiles = 0;
    private int testFiles = 0;
    private int totalTests = 0;
    private int linesOfCode = 0;
    private int dependencies = 0;
    private int totalComplexity = 0;

    @Override
    public void visit(SourceCodeFile file) {
        totalFiles++;
        sourceFiles++;
        linesOfCode += file.getLinesOfCode();
        totalComplexity += file.getComplexity();
    }

    @Override
    public void visit(TestFile file) {
        totalFiles++;
        testFiles++;
        totalTests += file.getTestCount();
        linesOfCode += file.getLinesOfCode();
    }

    @Override
    public void visit(ConfigurationFile file) {
        totalFiles++;
    }

    @Override
    public void visit(DependencyFile file) {
        totalFiles++;
        dependencies += file.getDependencies().size();
    }

    @Override
    public void visit(Directory directory) {
        // Directories are not counted as files
    }

    public int getTotalFiles() {
        return totalFiles;
    }

    public int getSourceFiles() {
        return sourceFiles;
    }

    public int getTestFiles() {
        return testFiles;
    }

    public int getTotalTests() {
        return totalTests;
    }

    public int getLinesOfCode() {
        return linesOfCode;
    }

    public int getDependencies() {
        return dependencies;
    }

    public double getAverageComplexity() {
        if (sourceFiles == 0) {
            return 0;
        }

        return (double) totalComplexity / sourceFiles;
    }

    public double getTestSourceRatio() {
        if (sourceFiles == 0) {
            return 0;
        }

        return (double) testFiles / sourceFiles;
    }

    public void printReport() {
        System.out.println("=== PROJECT STATISTICS ===");
        System.out.println("Total files: " + totalFiles);
        System.out.println("Source files: " + sourceFiles);
        System.out.println("Test files: " + testFiles);
        System.out.println("Total tests: " + totalTests);
        System.out.println("Lines of code: " + linesOfCode);
        System.out.println("Dependencies: " + dependencies);
        System.out.println("Average complexity: " + getAverageComplexity());
        System.out.println("Test/source ratio: " + getTestSourceRatio());
    }
}
