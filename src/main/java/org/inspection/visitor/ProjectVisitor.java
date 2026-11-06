
package org.inspection.visitor;

import org.inspection.element.*;

public interface ProjectVisitor {

    void visit(SourceCodeFile file);

    void visit(TestFile file);

    void visit(ConfigurationFile file);

    void visit(DependencyFile file);

    void visit(Directory directory);
}
