package org.capatect.restatic.core.configuration;

import org.apache.commons.lang.Validate;

import java.io.File;
import java.util.List;

/**
 * @author: Jamie Craane
 * <p/>
 * Represents the configuration for the Restatic core module. Contains all the configuration that is needed for the core module
 * to do its work. The configuration constists of:
 * <p/>
 * - sourceRootPaths: a list of source folder to scan for resource bundles.
 */
public final class Configuration {
    private List<File> sourceRootPaths;

    private Configuration(final ConfigurationBuilder builder) {
        this.sourceRootPaths = builder.sourceRootPaths;
    }

    /**
     * Root source folder to recursively look for resource bundles.
     *
     * @return The root folders configured for this configuration instance.
     */
    public List<File> getSourceRootPaths() {
        return sourceRootPaths;
    }

    /**
     * @author: Jamie Craane
     */
    public static class ConfigurationBuilder {
        private Configuration configuration;
        private List<File> sourceRootPaths;

        public ConfigurationBuilder addSourceRootPaths(final List<File> sourceRootPaths) {
            Validate.notNull(sourceRootPaths, "sourceRootPaths may not be null.");
            Validate.noNullElements(sourceRootPaths, "No sourceRootPath in sourceRootPaths may be null.");

            this.sourceRootPaths = sourceRootPaths;
            return this;
        }

        /**
         * Builds an instance of Configuration from the state of the builder.
         * @return A new Configuration instance.
         */
        public Configuration build() {
            return new Configuration(this);
        }
    }
}
