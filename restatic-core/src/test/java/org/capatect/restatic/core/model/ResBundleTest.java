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
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Jamie Craane
 */
public class ResBundleTest {
    private Configuration defaultConfiguration;
    private File rootPath;

    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        Field bundles = ResBundle.class.getDeclaredField("bundles");
        bundles.setAccessible(true);
        bundles.set(null, new HashMap<String, ResBundle>());

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
        assertEquals("ORG_CAPATECT_TEST_RESOURCES", resBundle.getBundleClassName());
        String separator = File.separator;
        assertEquals("org" + separator + "capatect" + separator + "test" + separator + "resources.properties",
                resBundle.getOriginalPathAndName());
        assertTrue(resBundle.isValid());
    }

    @Test
    public void createWithLocale() {
        File resourceBundle = new File(rootPath, "org/capatect/test/resources_nl_NL.properties");
        ResBundle resBundle = ResBundle.createOrReturn(resourceBundle, defaultConfiguration);
        assertEquals("ORG_CAPATECT_TEST_RESOURCES", resBundle.getBundleClassName());
        assertTrue(resBundle.isValid());
    }

    @Test
    public void createWithDefaultPackage() {
        File resourceBundle = new File(rootPath, "default-package-resources.properties");
        ResBundle resBundle = ResBundle.createOrReturn(resourceBundle, defaultConfiguration);
        assertEquals("DEFAULT_PACKAGE_RESOURCES", resBundle.getBundleClassName());
        assertTrue(resBundle.isValid());
    }

    @Test
    public void createWithPackageAlias() {
        Configuration configurationWithAliases = new ConfigurationBuilder().addSourceDirectory(rootPath).
                toOutputDirectory(new File("test")).aliasPackage("org.capatect.test").to("test").getConfiguration();

        File resourceBundle = new File(rootPath, "org/capatect/test/resources.properties");
        ResBundle resBundle = ResBundle.createOrReturn(resourceBundle, configurationWithAliases);
        assertEquals("TEST_RESOURCES", resBundle.getBundleClassName());
        assertTrue(resBundle.isValid());
    }

    @Test
    public void sameAliasForDifferentResourceBundles() {
        Configuration configurationWithAliases = new ConfigurationBuilder().addSourceDirectory(rootPath).
                toOutputDirectory(new File("test")).
                aliasPackage("org.capatect.test").to("test").
                aliasPackage("org.capatect.test2").to("test").getConfiguration();

        File resourceBundle = new File(rootPath, "org/capatect/test/resources.properties");
        ResBundle resBundle = ResBundle.createOrReturn(resourceBundle, configurationWithAliases);
        resourceBundle = new File(rootPath, "org/capatect/test2/resources.properties");
        resBundle = ResBundle.createOrReturn(resourceBundle, configurationWithAliases);

        assertEquals("TEST_RESOURCES", resBundle.getBundleClassName());
        assertEquals(4, resBundle.getAllUniqueKeysForLocales().size());
    }

    @Test
    public void createBundleAndAddLocales() {
        File resourceBundle = new File(rootPath, "org/capatect/test/locale_nl_NL.properties");
        ResBundle resBundle = ResBundle.createOrReturn(resourceBundle, defaultConfiguration);
        assertEquals("ORG_CAPATECT_TEST_LOCALE", resBundle.getBundleClassName());
        assertEquals(1, resBundle.getLocales().size());

        resourceBundle = new File(rootPath, "org/capatect/test/locale_en_US.properties");
        resBundle = ResBundle.createOrReturn(resourceBundle, defaultConfiguration);
        assertEquals("ORG_CAPATECT_TEST_LOCALE", resBundle.getBundleClassName());
        assertEquals(2, resBundle.getLocales().size());
    }

    @Test
    public void getAllUniqueKeysFromLocales() {
        File resourceBundle = new File(rootPath, "org/capatect/test/locale_nl_NL.properties");
        ResBundle resBundle = ResBundle.createOrReturn(resourceBundle, defaultConfiguration);
        assertEquals("ORG_CAPATECT_TEST_LOCALE", resBundle.getBundleClassName());
        assertEquals(1, resBundle.getLocales().size());

        resourceBundle = new File(rootPath, "org/capatect/test/locale_en_US.properties");
        resBundle = ResBundle.createOrReturn(resourceBundle, defaultConfiguration);
        assertEquals("ORG_CAPATECT_TEST_LOCALE", resBundle.getBundleClassName());
        assertEquals(2, resBundle.getLocales().size());

        assertEquals(3, resBundle.getAllUniqueKeysForLocales().size());
    }

    @Test
    public void isValid() {
        File resourceBundle = new File(rootPath, "org/capatect/test/invalid.properties");
        ResBundle resBundle = ResBundle.createOrReturn(resourceBundle, defaultConfiguration);
        resourceBundle = new File(rootPath, "org/capatect/test/invalid_en_US.properties");
        resBundle = ResBundle.createOrReturn(resourceBundle, defaultConfiguration);

        assertFalse(resBundle.isValid());
    }

    @Test(expected = IllegalStateException.class)
    public void addSameResourceBundleTwice() {
        File resourceBundle = new File(rootPath, "org/capatect/test/resources_nl_NL.properties");
        ResBundle resBundle = ResBundle.createOrReturn(resourceBundle, defaultConfiguration);
        resBundle = ResBundle.createOrReturn(resourceBundle, defaultConfiguration);
    }

    @Test
    public void convertResourceBundleToJavaClassIdentifier() {
        String className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("org.capatect.resources", "resources.properties");
        assertEquals("ORG_CAPATECT_RESOURCES_RESOURCES", className);

        className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("", "resources.properties");
        assertEquals("RESOURCES", className);

        className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("org.test", "resources.properties");
        assertEquals("ORG_TEST_RESOURCES", className);

        className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("org.test", "resources_nl_NL.properties");
        assertEquals("ORG_TEST_RESOURCES", className);

        className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("org.test", "resources_en.properties");
        assertEquals("ORG_TEST_RESOURCES", className);

        className = ResBundle.ResourceBundleToJavaClassIdentifierConverter.convert("", "default-resources.properties");
        assertEquals("DEFAULT_RESOURCES", className);
    }
}
