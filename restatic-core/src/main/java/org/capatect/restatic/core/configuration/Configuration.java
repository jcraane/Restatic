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
 * <li>sourceRootPaths: a list of source folder to scan for resource bundles. There must be at least one sourcerootpath present.</li>
 * <li>FileFilter: the filefilter to use when searching for resourcebundles. Defaults to AntStylePatternFileNameFilter
 * with a pattern of *
 *//*.properties.</li>
 * <li>packageAliases: By default, generated classes from resource bundles have the camelcased package name
 * as class name. Sometimes this name is too long to be readable. The packageAliases can be used to use another
 * name for the generated classes than the package name.</li>
 * <li>resourceBundleValidationEnabled: if true validates the parsed resource bundles. When there are resource bundles
 * for different locales, validation tests if all keys are present in all resource bundles and if there are
 * no duplicate keys present.</li>
 * <li>rootClassName: the name of the generated rootclass which is generated. Defaults to R.</li>
 * </ul>
 * <p/>
 *
 * In instance of this class is obtained by using the ConfigurationBuilder which ensures a valid Configuration object is created.
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
    private final boolean resourceBundleValidationEnabled;
    private final String rootClassName;

    private Configuration(final ConfigurationBuilder builder) {
        this.sourceRootPaths = new ArrayList<File>(builder.sourceRootPaths);
        this.fileFilter = builder.fileFilter;
        this.packageAliases = builder.aliases;
        resourceBundleValidationEnabled = builder.resourceBundleValidationEnabled;
        this.rootClassName = builder.rootClassName;

        LOGGER.info("Created Configuration object with the following parameters: " + this);
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

    /**
     * @return The aliases for package names which are used as classnames in resource bundle generation.
     */
    public Map<PackageName, PackageAlias> getPackageAliases() {
        return Collections.unmodifiableMap(packageAliases);
    }

    /**
     * @return true if resource bundle validation is enabled, false otherwise.
     */
    public boolean isResourceBundleValidationEnabled() {
        return resourceBundleValidationEnabled;
    }

    public String getRootClassName() {
        return rootClassName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Configuration");
        sb.append("{fileFilter=").append(fileFilter);
        sb.append(", sourceRootPaths=").append(sourceRootPaths);
        sb.append(", packageAliases=").append(packageAliases);
        sb.append(", resourceBundleValidationEnabled=").append(resourceBundleValidationEnabled);
        sb.append(", rootClassName='").append(rootClassName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * @author Jamie Craane
     */
    public static final class ConfigurationBuilder implements AliasTo {
        private static final FileFilter DEFAULT_FILTER = AntStylePatternFileNameFilter.create("**/*.properties");
        private static final String DEFAULT_ROOT_CLASS_NAME = "R";

        private final List<File> sourceRootPaths = new ArrayList<File>();
        private FileFilter fileFilter = DEFAULT_FILTER;
        private Map<PackageName, PackageAlias> aliases = new HashMap<PackageName, PackageAlias>();
        private boolean resourceBundleValidationEnabled = false;
        private String rootClassName = DEFAULT_ROOT_CLASS_NAME;

        private PackageName lastAddedPackageName;

        /**
         * @param mandatorySourceRootPath The source paths to search for resource bundles.
         */
        public ConfigurationBuilder(final File mandatorySourceRootPath) {
            Validate.notNull(mandatorySourceRootPath, "mandatorySourceRootPath may not be null.");

            sourceRootPaths.add(mandatorySourceRootPath);
        }

        /**
         * Builds the configuration object based on the information in the ConfigurationBuilder.
         *
         * @return A fully usable and valid Configuration object.
         */
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

        public ConfigurationBuilder enableResourceBundleValidation() {
            resourceBundleValidationEnabled = true;
            return this;
        }

        public ConfigurationBuilder disableResourceBundleValidation() {
            resourceBundleValidationEnabled = false;
            return this;
        }

        public ConfigurationBuilder withRootClassName(final String rootClassName) {
            this.rootClassName = rootClassName;

            return this;
        }
    }

    static interface AliasTo {
        ConfigurationBuilder to(String name);
    }
}
