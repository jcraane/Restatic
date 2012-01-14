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
package org.capatect.restatic.core.configuration.builder;

import org.apache.commons.lang.Validate;
import org.capatect.restatic.core.configuration.Configuration;
import org.capatect.restatic.core.configuration.PackageAlias;
import org.capatect.restatic.core.discoverer.file.AntStylePatternFileNameFilter;
import org.capatect.restatic.core.discoverer.file.FileFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jeroen Post
 */
public class ConfigurationBuilder {

    /**
     * Constant indicating the default file filter.
     */
    private static final FileFilter DEFAULT_FILTER = AntStylePatternFileNameFilter.create("**/*.properties");

    /**
     * Constant indicating the default root class name.
     */
    private static final String DEFAULT_ROOT_CLASS_NAME = "R";


    private FileFilter fileFilter;

    private File outputDirectory;

    private final List<PackageAliasBuilder> packageAliasBuilders;

    private boolean resourceBundleValidationEnabled;

    private String fullyQualifiedGeneratedRootClassName;

    private final Set<File> sourceDirectories;

    /**
     * Creates a new ConfigurationBuilder instance.
     */
    public ConfigurationBuilder() {
        fileFilter = DEFAULT_FILTER;
        outputDirectory = null;
        packageAliasBuilders = new ArrayList<PackageAliasBuilder>();
        resourceBundleValidationEnabled = false;
        fullyQualifiedGeneratedRootClassName = DEFAULT_ROOT_CLASS_NAME;
        sourceDirectories = new HashSet<File>();
    }

    public ConfigurationBuilder addSourceDirectory(final File aSourceDirectory) {
        Validate.notNull(aSourceDirectory, "Parameter aSourceDirectory is not allowed to be null");

        Validate.isTrue(aSourceDirectory.isDirectory(),
                "Parameter aSourceDirectory " + aSourceDirectory + " must be a valid directory");

        sourceDirectories.add(aSourceDirectory);

        return this;
    }

    public ConfigurationBuilder addFileFilter(final FileFilter aFileFilter) {
        Validate.notNull(aFileFilter, "Parameter aFileFilter is not allowed to be null");

        fileFilter = aFileFilter;

        return this;
    }

    public PackageAliasBuilder aliasPackage(final String aPackageName) {
        Validate.notEmpty(aPackageName, "Parameter aPackageName is not allowed to be empty");

        final PackageAliasBuilder packageAliasBuilder = new PackageAliasBuilder(this);
        packageAliasBuilder.setPackageName(aPackageName);

        packageAliasBuilders.add(packageAliasBuilder);

        return packageAliasBuilder;
    }

    /**
     * Builds the Configuration object based on the information in the ConfigurationBuilder.
     *
     * @return A fully usable and valid Configuration object.
     */
    public Configuration getConfiguration() {
        return new Configuration(outputDirectory, sourceDirectories, fileFilter,
                createPackageAliases(packageAliasBuilders), resourceBundleValidationEnabled, fullyQualifiedGeneratedRootClassName);
    }

    public ConfigurationBuilder toOutputDirectory(final File anOutputDirectory) {
        Validate.notNull(anOutputDirectory, "Parameter anOutputDirectory is not allowed to be null");

        outputDirectory = anOutputDirectory;

        return this;
    }

    public ConfigurationBuilder withResourceBundleValidationEnabled(final boolean isResourceBundleValidationEnabled) {
        resourceBundleValidationEnabled = isResourceBundleValidationEnabled;

        return this;
    }

    public ConfigurationBuilder withFullyQualitiedRootClassName(final String aRootClassName) {
        Validate.notEmpty(aRootClassName, "Parameter aRootClassName is not allowed to be empty");

        this.fullyQualifiedGeneratedRootClassName = aRootClassName;

        return this;
    }

    /**
     * Creates a Set with PackageAlias objects from the given List of PackageAliasBuilders.
     *
     * @param aPackageAliasBuilders the List of PackageAliasBuilders.
     * @return a Set with PackageAlias objects.
     */
    private Set<PackageAlias> createPackageAliases(final List<PackageAliasBuilder> aPackageAliasBuilders) {
        final Set<PackageAlias> result = new HashSet<PackageAlias>();

        for (final PackageAliasBuilder builder : aPackageAliasBuilders) {
            result.add(builder.getPackageAlias());
        }

        return result;
    }
}