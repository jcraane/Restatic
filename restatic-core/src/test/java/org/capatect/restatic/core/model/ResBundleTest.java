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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

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
    public void noNullConfiguration() {
        ResBundle.createOrReturn(new File("Test"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullResourceBundle() {
        ResBundle.createOrReturn(null, defaultConfiguration);
    }

    @Test
    public void createWithDefaultLocale() {
        File resourceBundle = new File(rootPath, "org/capatect/test/resources.properties");
        ResBundle resBundle = ResBundle.createOrReturn(resourceBundle, defaultConfiguration);
        assertEquals("OrgCapatectTestResources", resBundle.getBundleClassName());
    }

    @Test
    public void createWithLocale() {
        File resourceBundle = new File(rootPath, "org/capatect/test/resources_nl_NL.properties");
        ResBundle resBundle = ResBundle.createOrReturn(resourceBundle, defaultConfiguration);
        assertEquals("OrgCapatectTestResources", resBundle.getBundleClassName());
    }

    @Test
    @Ignore
    public void createWithDefaultPackage() {
        File resourceBundle = new File(rootPath, "default-package-resources.properties");
        ResBundle resBundle = ResBundle.createOrReturn(resourceBundle, defaultConfiguration);
        assertEquals("DefaultPackageResource", resBundle.getBundleClassName());
    }

    @Test
    public void createWithPackageAlias() {
        Configuration configurationWithAliases = new ConfigurationBuilder().addSourceDirectory(rootPath).
                toOutputDirectory(new File("test")).aliasPackage("org.capatect.test").to("test").getConfiguration();

        File resourceBundle = new File(rootPath, "org/capatect/test/resources.properties");
        ResBundle resBundle = ResBundle.createOrReturn(resourceBundle, configurationWithAliases);
        assertEquals("TestResources", resBundle.getBundleClassName());
    }

    @Test
    public void createBundleAndAddLocales() {
        File resourceBundle = new File(rootPath, "org/capatect/test/resources_nl_NL.properties");
        ResBundle resBundle = ResBundle.createOrReturn(resourceBundle, defaultConfiguration);
        assertEquals("OrgCapatectTestResources", resBundle.getBundleClassName());
        assertEquals(1, resBundle.getLocales().size());
        assertTrue(resBundle.getLocales().contains(new ResLocale("nl_NL")));

        resourceBundle = new File(rootPath, "org/capatect/test/resources_en_US.properties");
        resBundle = ResBundle.createOrReturn(resourceBundle, defaultConfiguration);
        assertEquals("OrgCapatectTestResources", resBundle.getBundleClassName());
        assertEquals(2, resBundle.getLocales().size());
        assertTrue(resBundle.getLocales().contains(new ResLocale("en_US")));
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

        className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("", "default-resources.properties");
        assertEquals("DefaultResources", className);
    }
}
