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

import org.capatect.restatic.core.discoverer.file.AntStylePatternFileNameFilter;
import org.capatect.restatic.core.discoverer.file.FileFilter;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Jamie Craane
 */
public class ConfigurationBuilderTest {
    @Test
    public void defaultFileFilter() {
        Configuration configuration = new Configuration.ConfigurationBuilder(new File("test")).build();
        assertNotNull(configuration.getFileFilter());
        assertTrue(configuration.getFileFilter() instanceof AntStylePatternFileNameFilter);
        assertEquals(1, ((AntStylePatternFileNameFilter) configuration.getFileFilter()).getPatterns().size());
        assertEquals("**/*.properties", ((AntStylePatternFileNameFilter) configuration.getFileFilter()).getPatterns().get(0));
    }

    @Test
    public void buildWithSourceRootPaths() {
        Configuration configuration = new Configuration.ConfigurationBuilder(new File("/org/capatect/restatic/core/bundletwo")).
                addSourceRootPath(new File("/org/capatect/restatic/core/bundletwo")).build();
        assertNotNull(configuration);
        assertNotNull(configuration.getSourceRootPaths());
        assertEquals(2, configuration.getSourceRootPaths().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithNullSourceRootPaths() {
        new Configuration.ConfigurationBuilder(null).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithNullSourceRootPath() {
        new Configuration.ConfigurationBuilder(null).build();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableSourceRootPaths() {
        new Configuration.ConfigurationBuilder(new File("/org/capatect/restatic/core/bundleone")).build().getSourceRootPaths().remove(0);
    }

    @Test
    public void buildWithFilter() {
        FileFilter fileFilter = AntStylePatternFileNameFilter.create("**/*.properties");
        Configuration configuration = new Configuration.ConfigurationBuilder(new File("/org/capatect/restatic/core/bundleone")).
                addFileFilter(fileFilter).build();
        assertNotNull(configuration.getFileFilter());
        assertTrue(fileFilter == configuration.getFileFilter());
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithNullFilters() {
        new Configuration.ConfigurationBuilder(new File("/org/capatect/restatic/core/bundleone")).addFileFilter(null).build();
    }

    @Test
    public void addAliases() {
        Configuration configuration = new Configuration.ConfigurationBuilder(new File("rootpath"))
                .aliasPackage("org.capatect.restatic").to("S")
                .aliasPackage("org.capatect.restatic.core").to("C").build();

        assertEquals(2, configuration.getPackageAliases().size());
        assertEquals("S", configuration.getPackageAliases().get(new PackageName("org.capatect.restatic")).getAlias());
        assertEquals("C", configuration.getPackageAliases().get(new PackageName("org.capatect.restatic.core")).getAlias());
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithNullPackage() {
        new Configuration.ConfigurationBuilder(new File("path")).aliasPackage(null).to("C").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithEmptyPackage() {
        new Configuration.ConfigurationBuilder(new File("path")).aliasPackage("").to("C").build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithNullPackageAlias() {
        new Configuration.ConfigurationBuilder(new File("path")).aliasPackage("org.capatext.restatic").to(null).build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildWithEmptyPackageAlias() {
        new Configuration.ConfigurationBuilder(new File("path")).aliasPackage("org.capatext.restatic").to("").build();
    }

    @Test
    public void enableResourceBundleValidation() {
        Configuration configuration = new Configuration.ConfigurationBuilder(new File("path")).enableResourceBundleValidation().build();
        assertTrue(configuration.isResourceBundleValidationEnabled());
    }

    @Test
    public void disableResourceBundleValidation() {
        Configuration configuration = new Configuration.ConfigurationBuilder(new File("path")).disableResourceBundleValidation().build();
        assertFalse(configuration.isResourceBundleValidationEnabled());
    }

    @Test
    public void defaultResourceBundleValidationIsFalse() {
        Configuration configuration = new Configuration.ConfigurationBuilder(new File("path")).build();
        assertFalse(configuration.isResourceBundleValidationEnabled());
    }

    @Test
    public void defaultRootClassName() {
        Configuration configuration = new Configuration.ConfigurationBuilder(new File("path")).build();
        assertEquals("R", configuration.getRootClassName());
    }

    @Test
    public void rootClassName() {
        Configuration configuration = new Configuration.ConfigurationBuilder(new File("path")).withRootClassName("RootClass").build();
        assertEquals("RootClass", configuration.getRootClassName());
    }
}
