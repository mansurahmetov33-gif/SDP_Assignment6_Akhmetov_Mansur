
package org.inspection;

import org.inspection.element.*;
import org.inspection.visitor.SecurityAuditVisitor;
import org.inspection.visitor.ProjectStatisticsVisitor;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Directory project = new Directory("fintech-project");

        Directory src = new Directory("src");
        Directory tests = new Directory("tests");
        Directory config = new Directory("config");

        src.add(new SourceCodeFile(
                "PaymentService.java", 200, 12, true, false
        ));

        src.add(new SourceCodeFile(
                "UserService.java", 150, 8, false, false
        ));

        tests.add(new TestFile(
                "PaymentServiceTest.java", 100, 5,
                "PaymentService"
        ));

        config.add(new ConfigurationFile(
                "application.properties", true, false, false
        ));

        project.add(src);
        project.add(tests);
        project.add(config);

        project.add(new DependencyFile(
                "pom.xml",
                List.of("spring-boot", "postgresql"),
                true, false, false
        ));

        SecurityAuditVisitor security = new SecurityAuditVisitor();

        project.accept(security);

        security.printReport();

        System.out.println();

        ProjectStatisticsVisitor statistics =
                new ProjectStatisticsVisitor();

        project.accept(statistics);

        statistics.printReport();

    }
}
