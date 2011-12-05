/*
 *
 *  * Copyright 2002-2011 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.capatect.restatic.core.model;

import org.capatect.restatic.core.FileTestUtils;
import org.capatect.restatic.core.configuration.Configuration;
import org.capatect.restatic.core.configuration.builder.ConfigurationBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;

import static junit.framework.Assert.assertEquals;

/**
 * @author Jamie Craane
 */
public class ResBundleTest {
    private Configuration defaultConfiguration;
    private File rootPath;

    @Before
    public void setup() {
        rootPath = FileTestUtils.getRootPath("src/test/resbundle-test");
        defaultConfiguration = new ConfigurationBuilder().addSourceDirectory(rootPath).toOutputDirectory(new File("target/generated-sources")).getConfiguration();
    }

    @Test(expected = IllegalArgumentException.class)
    public void noNullResourceBundle() {
        ResBundle.createAndConvertToJavaClassIdentifier(null, new HashSet<File>() {{
            add(new File("test"));
        }});
    }

    @Test(expected = IllegalArgumentException.class)
    public void noNullSourceRootPaths() {
        ResBundle.createAndConvertToJavaClassIdentifier(new File("Test"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void noEmptySourceRootPaths() {
        ResBundle.createAndConvertToJavaClassIdentifier(new File("Test"), new HashSet<File>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void noNullSourceRootPathElements() {
        ResBundle.createAndConvertToJavaClassIdentifier(null, new HashSet<File>() {{
            add(null);
        }});
    }

    @Test
    public void createWithDefaultLocale() {
        File resourceBundle = new File(rootPath, "org/capatect/test/resources.properties");
        ResBundle resBundle = ResBundle.createAndConvertToJavaClassIdentifier(resourceBundle, defaultConfiguration.getSourceDirectories());
        assertEquals("OrgCapatectTestResources", resBundle.getBundleClassName());
    }

    @Test
    public void createWithLocale() {
        File resourceBundle = new File(rootPath, "org/capatect/test/resources_nl_NL.properties");
        ResBundle resBundle = ResBundle.createAndConvertToJavaClassIdentifier(resourceBundle, defaultConfiguration.getSourceDirectories());
        assertEquals("OrgCapatectTestResources", resBundle.getBundleClassName());
    }

    @Test
    @Ignore
    public void createWithDefaultPackage() {
        File resourceBundle = new File(rootPath, "default-package-resources.properties");
        ResBundle resBundle = ResBundle.createAndConvertToJavaClassIdentifier(resourceBundle, defaultConfiguration.getSourceDirectories());
        assertEquals("DefaultPackageResource", resBundle.getBundleClassName());
    }

    @Test
    @Ignore
    public void createWithPackageAlias() {
        // TODO: Add package aliases
        File resourceBundle = new File(rootPath, "org/capatect/test/resources.properties");
        ResBundle resBundle = ResBundle.createAndConvertToJavaClassIdentifier(resourceBundle, defaultConfiguration.getSourceDirectories());
        assertEquals("OrgCapatectResourcesResources", resBundle.getBundleClassName());
    }

    @Test
    public void convertResourceBundleToJavaClassIdentifier() {
        String className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("org.capatect.resources", "resources.properties");
        assertEquals("OrgCapatectResourcesResources", className);

        className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("", "resources.properties");
        assertEquals("Resources", className);

        className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("org.test", "resources.properties");
        assertEquals("OrgTestResources", className);

        className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("org.test", "resources_nl_NL.properties");
        assertEquals("OrgTestResources", className);

        className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("org.test", "resources_en.properties");
        assertEquals("OrgTestResources", className);

        // TODO: Fix this.
        className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("", "default-resources.properties");
        assertEquals("DefaultResources", className);
    }
}
