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
import org.capatect.restatic.core.util.CollectionFilter;
import org.capatect.restatic.core.util.Predicate;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * @author Jamie Craane
 */
public class ResModelTest {
    private Configuration defaultConfiguration;
    private File rootPath;

    @Before
    public void setup() {
        rootPath = FileTestUtils.getRootPath("src/test/resmodel-test");
        defaultConfiguration = new ConfigurationBuilder()
                .addSourceDirectory(rootPath)
                .toOutputDirectory(FileTestUtils.getRootPath("target/generated-sources/restatic"))
                .getConfiguration();
    }

    @Test
    public void create() {
        ResModel resModel = ResModel.create(defaultConfiguration);
        assertNotNull(resModel);
        assertEquals("R", resModel.getRootClassName());
        assertEquals("", resModel.getRootClassPackage());
        assertFalse(resModel.isNotDefaultPackage());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullConfiguration() {
        ResModel.create(null);
    }

    @Test
    public void addOneResourceBundleWithOneLocale() {
        File resourceBundle = new File(rootPath, "org/capatect/test/resources.properties");

        ResModel resModel = ResModel.create(defaultConfiguration);
        resModel.addResourceBundle(resourceBundle);
        assertEquals(1, resModel.getBundles().size());
        List<ResBundle> filteredBundles = CollectionFilter.filter(resModel.getBundles(), new Predicate<ResBundle>() {
            @Override
            public boolean apply(final ResBundle type) {
                return type.getBundleClassName().equals("OrgCapatectTestResources");
            }
        });
        assertTrue(filteredBundles.size() == 1);
        assertTrue(resModel.isValid());
    }

    @Test
    public void addBundlesWhichAliasToSameNameAndPackage() {
        Configuration configuration = new ConfigurationBuilder()
                .addSourceDirectory(rootPath)
                .toOutputDirectory(FileTestUtils.getRootPath("target/generated-sources/restatic"))
                .aliasPackage("org.capatect.test").to("test")
                .aliasPackage("org.capatect.test2").to("test")
                .getConfiguration();

        ResModel resModel = ResModel.create(configuration);
        resModel.addResourceBundle(new File(rootPath, "org/capatect/test/resources.properties"));
        resModel.addResourceBundle(new File(rootPath, "org/capatect/test2/resources.properties"));

        assertEquals(1, resModel.getBundles().size());
        List<ResBundle> filteredBundles = CollectionFilter.filter(resModel.getBundles(), new Predicate<ResBundle>() {
            @Override
            public boolean apply(final ResBundle type) {
                return type.getBundleClassName().equals("TestResources");
            }
        });
        assertTrue(filteredBundles.size() == 1);
        assertEquals(5, filteredBundles.get(0).getAllUniqueKeysForLocales().size());
    }

    @Test
    public void createResModelWithFullyQualifiedClassName() {
        Configuration configuration = new ConfigurationBuilder()
                .addSourceDirectory(rootPath)
                .withFullyQualitiedRootClassName("org.capatect.R")
                .toOutputDirectory(FileTestUtils.getRootPath("target/generated-sources/restatic"))
                .getConfiguration();

        ResModel resModel = ResModel.create(configuration);
        assertEquals("R", resModel.getRootClassName());
        assertEquals("org.capatect", resModel.getRootClassPackage());
        assertTrue(resModel.isNotDefaultPackage());
    }

    @Test
    public void isValid() {
        ResModel resModel = ResModel.create(defaultConfiguration);
        resModel.addResourceBundle(new File(rootPath, "org/capatect/test/invalid.properties"));
        resModel.addResourceBundle(new File(rootPath, "org/capatect/test/invalid_en_US.properties"));
        assertFalse(resModel.isValid());
    }
}
