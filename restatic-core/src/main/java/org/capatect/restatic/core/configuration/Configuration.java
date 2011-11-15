package org.capatect.restatic.core.configuration;
/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.lang.Validate;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    // TODO: Change to FileFilter filter (one filter)
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
