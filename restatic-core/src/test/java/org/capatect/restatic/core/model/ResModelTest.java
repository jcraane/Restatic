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

import org.capatect.restatic.core.configuration.Configuration;
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

    @Before
    public void setup() {
        defaultConfiguration = new Configuration.ConfigurationBuilder(new File("test")).build();
    }

    @Test
    public void create() {
        ResModel resModel = ResModel.create(defaultConfiguration.getRootClassName());
        assertNotNull(resModel);
        assertEquals("R", resModel.getRootClassName());
    }

    @Test
    public void modelWithOneResourceBundle() {
        ResModel resModel = ResModel.create(defaultConfiguration.getRootClassName());
//        RestaticResourceBundleContainer restaticResourceBundleContainer = new RestaticResourceBundleContainer();
//        resModel.addResourceBundle("OrgCapatectBundle", "Resources", restaticResourceBundleContainer);
    }
}
