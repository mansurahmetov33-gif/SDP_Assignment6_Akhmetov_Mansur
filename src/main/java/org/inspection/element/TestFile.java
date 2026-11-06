
package org.inspection.element;

import org.inspection.visitor.ProjectVisitor;

public class TestFile implements ProjectElement {

    private final String name;
    private final int linesOfCode;
    private final int testCount;
    private final String targetClass;

    public TestFile(String name, int linesOfCode,
                    int testCount, String targetClass) {
        this.name = name;
        this.linesOfCode = linesOfCode;
        this.testCount = testCount;
        this.targetClass = targetClass;
    }

    public String getName() {
        return name;
    }

    public int getLinesOfCode() {
        return linesOfCode;
    }

    public int getTestCount() {
        return testCount;
    }

    public String getTargetClass() {
        return targetClass;
    }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }
}
