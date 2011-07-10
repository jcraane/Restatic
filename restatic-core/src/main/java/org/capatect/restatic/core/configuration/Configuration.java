package org.capatect.restatic.core.configuration;

import org.apache.commons.lang.Validate;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * @author: Jamie Craane
 */
public final class Configuration {
    private final List<File> sourceRootPaths;

    public Configuration(final List<File> sourceRootPaths) {
        Validate.notNull(sourceRootPaths, "sourceRootPaths may not be null.");
        Validate.noNullElements(sourceRootPaths, "none of the sourceRootPaths may be null.");

        this.sourceRootPaths = Collections.unmodifiableList(sourceRootPaths);
    }

    /**
     * Root source folder to recursively look for resource bundles.
     * @return The root folders configured for this configuration instance.
     */
    public List<File> getSourceRootPaths() {
        return sourceRootPaths;
    }
}
