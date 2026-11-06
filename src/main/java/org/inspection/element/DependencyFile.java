
package org.inspection.element;

import org.inspection.visitor.ProjectVisitor;

import java.util.List;

public class DependencyFile implements ProjectElement {

    private final String name;
    private final List<String> dependencies;
    private final boolean vulnerableDependencies;
    private final boolean missingVersions;
    private final boolean prohibitedDependencies;

    public DependencyFile(String name,
                          List<String> dependencies,
                          boolean vulnerableDependencies,
                          boolean missingVersions,
                          boolean prohibitedDependencies) {
        this.name = name;
        this.dependencies = List.copyOf(dependencies);
        this.vulnerableDependencies = vulnerableDependencies;
        this.missingVersions = missingVersions;
        this.prohibitedDependencies = prohibitedDependencies;
    }

    public String getName() {
        return name;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public boolean hasVulnerableDependencies() {
        return vulnerableDependencies;
    }

    public boolean hasMissingVersions() {
        return missingVersions;
    }

    public boolean hasProhibitedDependencies() {
        return prohibitedDependencies;
    }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }
}
