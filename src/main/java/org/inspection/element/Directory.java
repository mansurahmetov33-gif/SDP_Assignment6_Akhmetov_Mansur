
package org.inspection.element;

import org.inspection.visitor.ProjectVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Directory implements ProjectElement {

    private final String name;
    private final List<ProjectElement> children;

    public Directory(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void add(ProjectElement element) {
        children.add(element);
    }

    public List<ProjectElement> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);

        for (ProjectElement child : children) {
            child.accept(visitor);
        }
    }
}
