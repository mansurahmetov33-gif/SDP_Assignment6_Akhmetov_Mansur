
package org.inspection;

import org.inspection.element.*;
import org.inspection.visitor.QualityGateVisitor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QualityGateVisitorTest {

    @Test
    void qualityGatePassesForGoodProject() {
        Directory project = new Directory("good-project");

        project.add(new SourceCodeFile(
                "Service.java", 150, 8, false, false
        ));

        project.add(new SourceCodeFile(
                "Controller.java", 100, 6, false, false
        ));

        project.add(new TestFile(
                "ServiceTest.java", 80, 5, "Service"
        ));

        QualityGateVisitor visitor = new QualityGateVisitor();
        project.accept(visitor);

        assertTrue(visitor.isSecurityPassed());
        assertTrue(visitor.isComplexityPassed());
        assertTrue(visitor.isTestsPassed());
        assertTrue(visitor.isPassed());
    }

    @Test
    void qualityGateFailsForBadProject() {
        Directory project = new Directory("bad-project");

        project.add(new SourceCodeFile(
                "RiskyService.java", 300, 18, true, true
        ));

        QualityGateVisitor visitor = new QualityGateVisitor();
        project.accept(visitor);

        assertFalse(visitor.isSecurityPassed());
        assertFalse(visitor.isComplexityPassed());
        assertFalse(visitor.isTestsPassed());
        assertFalse(visitor.isPassed());
    }

    @Test
    void qualityGateHandlesEmptyProject() {
        Directory project = new Directory("empty-project");

        QualityGateVisitor visitor = new QualityGateVisitor();
        project.accept(visitor);

        assertTrue(visitor.isSecurityPassed());
        assertFalse(visitor.isComplexityPassed());
        assertFalse(visitor.isTestsPassed());
        assertFalse(visitor.isPassed());
    }
}
