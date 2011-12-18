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
package org.capatect.restatic.core.configuration;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.capatect.restatic.core.discoverer.file.FileFilter;
import org.capatect.restatic.core.util.CollectionFilter;
import org.capatect.restatic.core.util.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Represents the configuration for the Restatic core module. Contains all the configuration that is needed for the
 * core module to do its work. The configuration consists of:
 * <ul>
 * <li>sourceDirectories: a list of source folders to scan for resource bundles. There must be at least one source root path present.</li>
 * <li>FileFilter: the file filter to use when searching for resource bundles. Defaults to AntStylePatternFileNameFilter
 * with a pattern of *
 *//*.properties.</li>
 * <li>packageAliases: By default, generated classes from resource bundles have the camel cased package name
 * as class name. Sometimes this name is too long to be readable. The packageAliases can be used to use another
 * name for the generated classes than the package name.</li>
 * <li>resourceBundleValidationEnabled: if true validates the parsed resource bundles. When there are resource bundles
 * for different locales, validation tests if all keys are present in all resource bundles and if there are
 * no duplicate keys present.</li>
 * <li>rootClassName: the name of the generated root class which is generated. Defaults to R.</li>
 * </ul>
 * <p/>
 *
 * In instance of this class is obtained by using the ConfigurationBuilder which ensures a valid Configuration object is created.
 *
 * This class is immutable.
 *
 * @author Jamie Craane
 * @author Jeroen Post
 */
public final class Configuration {
    /**
     * The logger instance for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);


    private final FileFilter fileFilter;
    private final File outputDirectory;
    private final Set<PackageAlias> packageAliases;
    private final boolean resourceBundleValidationEnabled;
    private final String rootClassName;
    private final Set<File> sourceDirectories;

    /**
     * Creates a new Configuration instance. Instead of using this constructor consider using the ConfigurationBuilder
     * class.
     *
     * @param anOutputDirectory  the output directory where the restatic sources are generated.
     * @param aSourceDirectories set with source directories.
     * @param aFileFilter        the file filter.
     * @param aPackageAliases    the set with package aliases.
     * @param anIsResourceBundleValidationEnabled
     *                           boolean flag to indicate if resource bundles should be validated.
     * @param aRootClassName     the name of the restatic root class.
     */
    public Configuration(final File anOutputDirectory, final Set<File> aSourceDirectories, final FileFilter aFileFilter,
                         final Set<PackageAlias> aPackageAliases, final boolean anIsResourceBundleValidationEnabled,
                         final String aRootClassName) {

        // Validate parameters.
        Validate.notNull(anOutputDirectory, "Parameter anOutputDirectory is not allowed to be null");
        Validate.notEmpty(aSourceDirectories, "Parameter aSourceDirectories is not allowed to be empty");

        for (File sourceDirectory : aSourceDirectories) {
            Validate.isTrue(sourceDirectory.isDirectory(), "Parameter sourceDirectories must contain valid directories");
        }

        Validate.notNull(aFileFilter, "Parameter aFileFilter is not allowed to be null");
        Validate.notNull(aPackageAliases, "Parameter aPackageAliases is not allowed to be null");
        Validate.notEmpty(aRootClassName, "Parameter aRootClassName is not allowed to be empty");

        outputDirectory = anOutputDirectory;
        sourceDirectories = aSourceDirectories;
        fileFilter = aFileFilter;
        packageAliases = aPackageAliases;
        resourceBundleValidationEnabled = anIsResourceBundleValidationEnabled;
        rootClassName = aRootClassName;
    }

    public FileFilter getFileFilter() {
        return fileFilter;
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * @return The aliases for package names which are used as class names in resource bundle generation.
     */
    public Set<PackageAlias> getPackageAliases() {
        return Collections.unmodifiableSet(packageAliases);
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

    /**
     * Root source folder to recursively look for resource bundles.
     *
     * @return The root folders configured for this configuration instance.
     */
    public Set<File> getSourceDirectories() {
        return Collections.unmodifiableSet(sourceDirectories);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("fileFilter", fileFilter)
                .append("outputDirectory", outputDirectory)
                .append("packageAliases", packageAliases)
                .append("resourceBundleValidationEnabled", resourceBundleValidationEnabled)
                .append("rootClassName", rootClassName)
                .append("sourceDirectories", sourceDirectories)
                .toString();
    }

    /**
     * Returns the alias for the given package.
     *
     * @return The alias for the given package. If no alias is found the passed-in package is returned.
     */
    public String getAliasFor(final String javaPackage) {
        Validate.notNull(javaPackage, "The javaPackage may not be null.");

        Predicate<PackageAlias> packagePredicate = new Predicate<PackageAlias>() {
            @Override
            public boolean apply(final PackageAlias type) {
                return javaPackage.equalsIgnoreCase(type.getPackageName());
            }

            ;
        };

        String alias = getAliasOrPackage(javaPackage, packagePredicate);
        LOGGER.debug("Returning alias {} for package {}.", javaPackage, alias);
        return alias;
    }

    private String getAliasOrPackage(final String javaPackage, final Predicate<PackageAlias> packagePredicate) {
        List<PackageAlias> aliases = CollectionFilter.filter(packageAliases, packagePredicate);
        String alias = javaPackage;
        if (aliases.isEmpty()) {
            LOGGER.debug("No alias found for {}, returning {}.", javaPackage, alias);
        } else {
            alias = aliases.get(0).getAlias();
        }

        return alias;
    }
}
