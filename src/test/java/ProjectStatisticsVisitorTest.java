
package org.inspection;

import org.inspection.element.*;
import org.inspection.visitor.ProjectStatisticsVisitor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectStatisticsVisitorTest {

    @Test
    void calculatesProjectStatistics() {
        Directory project = new Directory("project");

        project.add(new SourceCodeFile(
                "Main.java", 200, 12, false, false
        ));

        project.add(new SourceCodeFile(
                "Service.java", 150, 8, false, false
        ));

        project.add(new TestFile(
                "MainTest.java", 100, 5, "Main"
        ));

        project.add(new DependencyFile(
                "pom.xml",
                List.of("junit", "spring"),
                false, false, false
        ));

        ProjectStatisticsVisitor visitor =
                new ProjectStatisticsVisitor();

        project.accept(visitor);

        assertEquals(4, visitor.getTotalFiles());
        assertEquals(2, visitor.getSourceFiles());
        assertEquals(1, visitor.getTestFiles());
        assertEquals(5, visitor.getTotalTests());
        assertEquals(450, visitor.getLinesOfCode());
        assertEquals(2, visitor.getDependencies());
        assertEquals(10.0, visitor.getAverageComplexity());
        assertEquals(0.5, visitor.getTestSourceRatio());
    }

    @Test
    void handlesEmptyDirectory() {
        Directory empty = new Directory("empty");

        ProjectStatisticsVisitor visitor =
                new ProjectStatisticsVisitor();

        empty.accept(visitor);

        assertEquals(0, visitor.getTotalFiles());
        assertEquals(0, visitor.getAverageComplexity());
        assertEquals(0, visitor.getTestSourceRatio());
    }

    @Test
    void calculatesNestedDirectoryStatistics() {
        Directory root = new Directory("root");
        Directory src = new Directory("src");
        Directory services = new Directory("services");

        services.add(new SourceCodeFile(
                "Payment.java", 120, 6, false, false
        ));

        src.add(services);
        root.add(src);

        ProjectStatisticsVisitor visitor =
                new ProjectStatisticsVisitor();

        root.accept(visitor);

        assertEquals(1, visitor.getTotalFiles());
        assertEquals(120, visitor.getLinesOfCode());
        assertEquals(6.0, visitor.getAverageComplexity());
    }
}
