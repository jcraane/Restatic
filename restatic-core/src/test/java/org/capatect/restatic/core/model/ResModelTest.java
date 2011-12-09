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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

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
        assertEquals("OrgCapatectTestResources", resModel.getBundles().get(0).getBundleClassName());
    }
}
