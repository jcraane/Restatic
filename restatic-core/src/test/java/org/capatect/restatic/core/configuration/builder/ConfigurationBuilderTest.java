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

import org.capatect.restatic.core.FileTestUtils;
import org.capatect.restatic.core.configuration.Configuration;
import org.capatect.restatic.core.discoverer.file.AntStylePatternFileNameFilter;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * @author Jamie Craane
 * @author Jeroen Post
 */
public class ConfigurationBuilderTest {

    private static final String rootClassName = "Abc";
    private static final File sourceDirectory = FileTestUtils.getRootPath("src/main/java");
    private static final File sourceDirectory2 = FileTestUtils.getRootPath("src/test/java");
    private static final File outputDirectory = FileTestUtils.getRootPath("target/generated-sources/restatic");

    @Test
    public void testDefaultFileFilter() {
        final Configuration configuration = new ConfigurationBuilder()
                .addSourceDirectory(sourceDirectory)
                .toOutputDirectory(outputDirectory)
                .getConfiguration();

        assertNotNull(configuration.getFileFilter());
        assertTrue(configuration.getFileFilter() instanceof AntStylePatternFileNameFilter);
        assertEquals(1, ((AntStylePatternFileNameFilter) configuration.getFileFilter()).getPatterns().size());
        assertEquals("**/*.properties", ((AntStylePatternFileNameFilter) configuration.getFileFilter()).getPatterns().get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullFileFilter() {
        new ConfigurationBuilder()
                .addSourceDirectory(sourceDirectory)
                .toOutputDirectory(outputDirectory)
                .addFileFilter(null)
                .getConfiguration();
    }

    @Test
    public void testFileFilter() {
        final Configuration configuration = new ConfigurationBuilder()
                .addSourceDirectory(sourceDirectory)
                .toOutputDirectory(outputDirectory)
                .addFileFilter(AntStylePatternFileNameFilter.create("**/Resources.properties", "**/Bundle.properties"))
                .getConfiguration();

        assertNotNull(configuration.getFileFilter());
        assertTrue(configuration.getFileFilter() instanceof AntStylePatternFileNameFilter);
        assertEquals(2, ((AntStylePatternFileNameFilter) configuration.getFileFilter()).getPatterns().size());
        assertEquals("**/Resources.properties", ((AntStylePatternFileNameFilter) configuration.getFileFilter()).getPatterns().get(0));
        assertEquals("**/Bundle.properties", ((AntStylePatternFileNameFilter) configuration.getFileFilter()).getPatterns().get(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoSourceDirectory() {
        new ConfigurationBuilder()
                .toOutputDirectory(outputDirectory)
                .getConfiguration();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullSourceDirectory() {
        new ConfigurationBuilder()
                .addSourceDirectory(null)
                .toOutputDirectory(outputDirectory)
                .addFileFilter(null)
                .getConfiguration();
    }

    @Test
    public void testAddOneSourceDirectory() {
        final Configuration configuration = new ConfigurationBuilder()
                .addSourceDirectory(sourceDirectory)
                .toOutputDirectory(outputDirectory)
                .getConfiguration();

        assertEquals(1, configuration.getSourceDirectories().size());
        assertTrue(configuration.getSourceDirectories().contains(sourceDirectory));
    }

    @Test
    public void testAddTwoSourceDirectory() {
        final Configuration configuration = new ConfigurationBuilder()
                .addSourceDirectory(sourceDirectory)
                .addSourceDirectory(sourceDirectory2)
                .toOutputDirectory(outputDirectory)
                .getConfiguration();

        assertEquals(2, configuration.getSourceDirectories().size());
        assertTrue(configuration.getSourceDirectories().contains(sourceDirectory));
        assertTrue(configuration.getSourceDirectories().contains(sourceDirectory2));
    }

    //    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableSourceRootPaths() {
//        new Configuration.ConfigurationBuilder(new File("/org/capatect/restatic/core/bundleone")).build().getSourceRootPaths().remove(0);
    }


    @Test
    public void addAliases() {
//        Configuration configuration = new Configuration.ConfigurationBuilder(new File("rootpath"))
//                .aliasPackage("org.capatect.restatic").to("S")
//                .aliasPackage("org.capatect.restatic.core").to("C").build();
//
//        assertEquals(2, configuration.getPackageAliases().size());
//        assertEquals("S", configuration.getPackageAliases().get(new PackageName("org.capatect.restatic")).getAlias());
//        assertEquals("C", configuration.getPackageAliases().get(new PackageName("org.capatect.restatic.core")).getAlias());
    }

    //    @Test(expected = IllegalArgumentException.class)
    public void buildWithNullPackage() {
//        new Configuration.ConfigurationBuilder(new File("path")).aliasPackage(null).to("C").build();
    }

    //    @Test(expected = IllegalArgumentException.class)
    public void buildWithEmptyPackage() {
//        new Configuration.ConfigurationBuilder(new File("path")).aliasPackage("").to("C").build();
    }

    //    @Test(expected = IllegalArgumentException.class)
    public void buildWithNullPackageAlias() {
//        new Configuration.ConfigurationBuilder(new File("path")).aliasPackage("org.capatext.restatic").to(null).build();
    }

    //    @Test(expected = IllegalArgumentException.class)
    public void buildWithEmptyPackageAlias() {
//        new Configuration.ConfigurationBuilder(new File("path")).aliasPackage("org.capatext.restatic").to("").build();
    }

    @Test
    public void enableResourceBundleValidation() {
//        Configuration configuration = new Configuration.ConfigurationBuilder(new File("path")).enableResourceBundleValidation().build();
//        assertTrue(configuration.isResourceBundleValidationEnabled());
    }

    @Test
    public void disableResourceBundleValidation() {
//        Configuration configuration = new Configuration.ConfigurationBuilder(new File("path")).disableResourceBundleValidation().build();
//        assertFalse(configuration.isResourceBundleValidationEnabled());
    }

    @Test
    public void defaultResourceBundleValidationIsFalse() {
//        Configuration configuration = new Configuration.ConfigurationBuilder(new File("path")).build();
//        assertFalse(configuration.isResourceBundleValidationEnabled());
    }

    @Test
    public void testDefaultRootClassName() {
        final Configuration configuration = new ConfigurationBuilder()
                .addSourceDirectory(sourceDirectory)
                .toOutputDirectory(outputDirectory)
                .getConfiguration();

        assertEquals("R", configuration.getFullyQualifiedGeneratedRootClassName());
    }

    @Test
    public void testRootClassName() {
        final Configuration configuration = new ConfigurationBuilder()
                .addSourceDirectory(sourceDirectory)
                .toOutputDirectory(outputDirectory)
                .withRootClassName(rootClassName)
                .getConfiguration();

        assertEquals(rootClassName, configuration.getFullyQualifiedGeneratedRootClassName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyRootClassName() {
        new ConfigurationBuilder()
                .addSourceDirectory(sourceDirectory)
                .toOutputDirectory(outputDirectory)
                .withRootClassName("")
                .getConfiguration();
    }
}
