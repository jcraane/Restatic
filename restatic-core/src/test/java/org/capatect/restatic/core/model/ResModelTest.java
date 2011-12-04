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
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

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
        defaultConfiguration = new Configuration.ConfigurationBuilder(rootPath).build();
    }

    @Test
    public void create() {
        ResModel resModel = ResModel.create(defaultConfiguration.getRootClassName(), defaultConfiguration.getSourceRootPaths());
        assertNotNull(resModel);
        assertEquals("R", resModel.getRootClassName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullRootClassName() {
        ResModel.create(null, defaultConfiguration.getSourceRootPaths());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmptyRootClassName() {
        ResModel.create("", defaultConfiguration.getSourceRootPaths());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullSourceRootPath() {
        ResModel.create("R", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNullSourceRootPathElement() {
        ResModel.create("R", new ArrayList<File>() {{
            add(null);
        }});
    }

    @Test
    public void addOneResourceBundleWithOneLocale() {
        File resourceBundle = new File(rootPath, "org/capatect/test/resources.properties");

        ResModel resModel = ResModel.create(defaultConfiguration.getRootClassName(), defaultConfiguration.getSourceRootPaths());
        resModel.addResourceBundle(resourceBundle);
        assertEquals(1, resModel.getBundles().size());
        assertEquals("OrgCapatectTestResources", resModel.getBundles().get(0).getBundleClassName());
    }
}
