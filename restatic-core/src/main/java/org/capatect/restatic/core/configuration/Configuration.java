package org.capatect.restatic.core.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * Represents the configuration for the Restatic core module. Contains all the configuration that is needed for the
 * core module to do its work. The configuration constists of:
 * <ul>
 *     <li>sourceRootPaths: a list of source folder to scan for resource bundles.</li>
 *     <li>filters: the filters to use to search for resource bundles. Example: *.properties or just a list of resource
 *     bundles. Defaults to: *.properties</li>
 * </ul>
 *
 * This class is immutable.
 *
 * @author Jamie Craane
 */
public final class Configuration {
    private final List<File> sourceRootPaths;
    private final List<String> filters;

    private Configuration(final ConfigurationBuilder builder) {
        this.sourceRootPaths = new ArrayList<File>(builder.sourceRootPaths);
        this.filters = new ArrayList<String>(builder.filters);
    }

    /**
     * Root source folder to recursively look for resource bundles.
     *
     * @return The root folders configured for this configuration instance.
     */
    public List<File> getSourceRootPaths() {
        return Collections.unmodifiableList(sourceRootPaths);
    }

    /**
     * @return The filters to use to search for resource bundles.
     */
    public List<String> getFilters() {
        return Collections.unmodifiableList(filters);
    }

    /**
     * @author Jamie Craane
     */
    public static final class ConfigurationBuilder {
        private static final String DEFAULT_FILTER = "*.properties";

        private final List<File> sourceRootPaths;
        private List<String> filters = Arrays.asList(DEFAULT_FILTER);

        /**
         * @param sourceRootPaths The source paths to search for resource bundles.
         */
        public ConfigurationBuilder(final List<File> sourceRootPaths) {
            Validate.notNull(sourceRootPaths, "sourceRootPaths may not be null.");
            Validate.noNullElements(sourceRootPaths, "No sourceRootPath in sourceRootPaths may be null.");

            this.sourceRootPaths = sourceRootPaths;
        }

        public Configuration build() {
            return new Configuration(this);
        }

        public ConfigurationBuilder addFilters(final List<String> filters) {
            Validate.notNull(filters, "filters may not be null.");
            Validate.noNullElements(filters, "No filters in filters may be null.");

            this.filters = filters;
            return this;
        }
    }
}
