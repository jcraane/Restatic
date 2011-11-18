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
import org.capatect.restatic.core.discoverer.file.AntStylePatternFileNameFilter;
import org.capatect.restatic.core.discoverer.file.FileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

    private final List<File> sourceRootPaths;
    private final FileFilter fileFilter;
    private final Map<PackageName, PackageAlias> packageAliases;

    private Configuration(final ConfigurationBuilder builder) {
        this.sourceRootPaths = new ArrayList<File>(builder.sourceRootPaths);
        this.fileFilter = builder.fileFilter;
        this.packageAliases = builder.aliases;
    }

    /**
     * Root source folder to recursively look for resource bundles.
     *
     * @return The root folders configured for this configuration instance.
     */
    public List<File> getSourceRootPaths() {
        return Collections.unmodifiableList(sourceRootPaths);
    }

    public FileFilter getFileFilter() {
        return fileFilter;
    }

    public Map<PackageName, PackageAlias> getPackageAliases() {
        return Collections.unmodifiableMap(packageAliases);
    }

    /**
     * @author Jamie Craane
     */
    public static final class ConfigurationBuilder implements AliasTo {
        private static final FileFilter DEFAULT_FILTER = AntStylePatternFileNameFilter.create("**/*.properties");

        private final List<File> sourceRootPaths = new ArrayList<File>();
        private FileFilter fileFilter;
        private Map<PackageName, PackageAlias> aliases = new HashMap<PackageName, PackageAlias>();

        private PackageName lastAddedPackageName;

        /**
         * @param mandatorySourceRootPath The source paths to search for resource bundles.
         */
        public ConfigurationBuilder(final File mandatorySourceRootPath) {
            Validate.notNull(mandatorySourceRootPath, "mandatorySourceRootPath may not be null.");

            sourceRootPaths.add(mandatorySourceRootPath);
        }

        public Configuration build() {
            return new Configuration(this);
        }

        public ConfigurationBuilder addSourceRootPath(final File soureRootPath) {
            Validate.notNull(soureRootPath, "sourceRootPath may not be null.");

            sourceRootPaths.add(soureRootPath);
            return this;
        }


        public ConfigurationBuilder addFileFilter(final FileFilter fileFilter) {
            Validate.notNull(fileFilter, "filters may not be null.");

            this.fileFilter = fileFilter;
            return this;
        }

        public AliasTo aliasPackage(final String packageName) {
            this.lastAddedPackageName = new PackageName(packageName);

            return this;
        }

        @Override
        public ConfigurationBuilder to(final String alias) {
            aliases.put(lastAddedPackageName, new PackageAlias(alias));

            return this;
        }
    }

    static interface AliasTo {
        ConfigurationBuilder to(String name);
    }
}
