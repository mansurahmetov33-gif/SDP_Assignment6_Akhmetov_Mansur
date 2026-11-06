
package org.inspection.element;

import org.inspection.visitor.ProjectVisitor;

public class ConfigurationFile implements ProjectElement {

    private final String name;
    private final boolean debugEnabled;
    private final boolean encryptionEnabled;
    private final boolean exposedCredentials;

    public ConfigurationFile(String name,
                             boolean debugEnabled,
                             boolean encryptionEnabled,
                             boolean exposedCredentials) {
        this.name = name;
        this.debugEnabled = debugEnabled;
        this.encryptionEnabled = encryptionEnabled;
        this.exposedCredentials = exposedCredentials;
    }

    public String getName() {
        return name;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public boolean isEncryptionEnabled() {
        return encryptionEnabled;
    }

    public boolean hasExposedCredentials() {
        return exposedCredentials;
    }

    @Override
    public void accept(ProjectVisitor visitor) {
        visitor.visit(this);
    }
}
