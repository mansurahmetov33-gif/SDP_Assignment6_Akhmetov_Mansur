
package org.inspection.element;

import org.inspection.visitor.ProjectVisitor;

public interface ProjectElement {
    void accept(ProjectVisitor visitor);
}
