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
package org.capatect.restatic.plugin.maven;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.capatect.restatic.core.RestaticCore;
import org.capatect.restatic.core.RestaticCoreImpl;
import org.capatect.restatic.core.configuration.Configuration;
import org.capatect.restatic.core.configuration.builder.ConfigurationBuilder;
import org.capatect.restatic.core.discoverer.file.AntStylePatternFileNameFilter;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Simple mojo class to generate Restatic sources.
 * <p/>
 * Example plugin configuration:
 * <pre>
 *  <plugin>
 *      <groupId>org.capatect.restatic.plugin</groupId>
 *      <artifactId>restatic-maven-plugin</artifactId>
 *      <version>1.0.0</version>
 *      <configuration>
 *          <aliases>
 *              <org.capatect.example.project.package1>a</org.capatect.example.project.package1>
 *              <org.capatect.example.project.package2>b</org.capatect.example.project.package2>
 *          </aliases>
 *          <sourceDirectories>
 *              <sourceDirectory>${project.build.sourceDirectory}</sourceDirectory>
 *              <sourceDirectory>${project.basedir}/src/test/java</sourceDirectory>
 *         </sourceDirectories>
 *          <includes>
 *              <include>&#042;&#042;/test.properties</include>
 *              <include>&#042;&#042;/resource.properties</include>
 *          </includes>
 *          <outputDirectory>${project.basedir}/target/generated-source/restatic</outputDirectory>
 *          <rootClassName>Resources</rootClassName>
 *          <resourceBundleValidationEnabled>true</resourceBundleValidationEnabled>
 *      </configuration>
 *      <executions>
 *          <execution>
 *              <goals>
 *                  <goal>generate-restatic</goal>
 *              </goals>
 *          </execution>
 *      </executions>
 *  </plugin>
 * </pre>
 *
 * @author Jeroen Post
 * @goal generate-restatic
 * @phase generate-resources
 */
public class GenerateRestaticSourcesMojo extends AbstractMojo {

    /**
     * The Map with packages mapped to alias.
     *
     * @parameter
     */
    private Map<String, String> aliases = new HashMap<String, String>();

    /**
     * The default source directory as specified by Maven.
     *
     * @parameter default-value="${project.basedir}/src/main/resources"
     * @readonly
     */
    private File defaultSourceDirectory;

    /**
     * The resource bundle files to include.
     *
     * @parameter
     */
    private Set<String> includes = new HashSet<String>();

    /**
     * The directory where the Restatic source files are generated. Defaults to
     * ${project.basedir}/target/generated-source/restatic.
     *
     * @parameter default-value="${project.basedir}/target/generated-source/restatic"
     */
    private File outputDirectory;

    /**
     * Flag to indicate if resource bundle validation should be enabled.
     *
     * @parameter default-value="true"
     */
    private boolean resourceBundleValidationEnabled;

    /**
     * The name of the root class.
     *
     * @parameter default-value="R"
     */
    private String rootClassName;

    /**
     * The greeting to display. Defaults to ${project.build.sourceDirectory}.
     *
     * @parameter
     */
    private Set<File> sourceDirectories = new HashSet<File>();

    /**
     * The MavenProject.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject mavenProject;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Generating Restatic resources to output directory " + outputDirectory);

        // Validate the plugin configuration.
        validateConfiguration();

        // Create ConfigurationBuilder.
        final ConfigurationBuilder builder = new ConfigurationBuilder()
                .addFileFilter(AntStylePatternFileNameFilter.create(includes.toArray(new String[includes.size()])))
                .withFullyQualitiedRootClassName(rootClassName)
                .withResourceBundleValidationEnabled(resourceBundleValidationEnabled)
                .toOutputDirectory(outputDirectory);

        for (final Map.Entry<String, String> alias : aliases.entrySet()) {
            builder.aliasPackage(alias.getKey()).to(alias.getValue());
        }

        getLog().info(sourceDirectories.toString());
        if (sourceDirectories.isEmpty()) {
            builder.addSourceDirectory(defaultSourceDirectory);
        } else {
            for (final File sourceDirectory : sourceDirectories) {
                builder.addSourceDirectory(sourceDirectory);
            }
        }

        // Get Configuration from configuration builder.
        final Configuration configuration = builder.getConfiguration();
        getLog().info("Using configuration: " + configuration);

        // Generate sources using the RestaticCore.
        final RestaticCore core = new RestaticCoreImpl(configuration);
        core.run();

        // Add generated resources to project
        addResourceToProject();
    }

    private void addResourceToProject() {
        // Add generated resources to project
        final Resource resource = new Resource();
        resource.setDirectory(outputDirectory.getAbsolutePath());
        mavenProject.addResource(resource);

    }

    private void validateConfiguration() throws MojoExecutionException {
        // Validate aliases.
        for (final Map.Entry<String, String> alias : aliases.entrySet()) {
            if (StringUtils.isBlank(alias.getValue())) {
                throw new MojoExecutionException("Illegal alias found. Alias is not allowed to by empty");
            }
        }

        // Validate sources.
        if (sourceDirectories.isEmpty()) {
            if (!defaultSourceDirectory.isDirectory()) {
                throw new MojoExecutionException(MessageFormat.format("Illegal default source directory found. Source '{0}' is not a valid directory",
                        defaultSourceDirectory));
            }
        } else {
            for (final File sourceDirectory : sourceDirectories) {
                if (!sourceDirectory.isDirectory()) {
                    throw new MojoExecutionException(MessageFormat.format("Illegal source found. Source {0} is not a valid directory", sourceDirectory));
                }
            }
        }

        // Validate includes.
        for (final String include : includes) {
            if (StringUtils.isBlank(include)) {
                throw new MojoExecutionException("Illegal include found. Include is not allowed to be empty");
            }
        }

        // Validate rootClassName.
        if (StringUtils.isBlank(rootClassName)) {
            throw new MojoExecutionException("Illegal rootClassName found. Root class name is not allowed to be empty");
        }
    }
}
