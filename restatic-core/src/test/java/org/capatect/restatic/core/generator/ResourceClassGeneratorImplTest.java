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

package org.capatect.restatic.core.generator;

import org.capatect.restatic.core.FileTestUtils;
import org.capatect.restatic.core.configuration.Configuration;
import org.capatect.restatic.core.configuration.builder.ConfigurationBuilder;
import org.capatect.restatic.core.model.ResModel;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * @author Jamie Craane
 */
public class ResourceClassGeneratorImplTest {
    private Configuration defaultConfiguration;
    private File rootPath;

    @Before
    public void setup() {
        rootPath = FileTestUtils.getRootPath("src/test/generator-test");
        defaultConfiguration = new ConfigurationBuilder()
                .addSourceDirectory(rootPath)
                .toOutputDirectory(FileTestUtils.getRootPath("target/generated-sources/restatic"))
                .aliasPackage("org.capatect.test2").to("test")
                .aliasPackage("org.capatect.test3").to("test")
                .getConfiguration();
    }

    @Test
    public void addOneResourceBundleWithOneLocale() {
        ResModel resModel = ResModel.create(defaultConfiguration);

        resModel.addResourceBundle(new File(rootPath, "org/capatect/test/resources.properties"));
        resModel.addResourceBundle(new File(rootPath, "org/capatect/test/labels.properties"));
        // TODO: Resourcebundle komt nog twee keer voor, The List in ResModel should actually be a Set/Map depending if we want to get indivudual bundles as weel instead of only preserve uniqueness.
        resModel.addResourceBundle(new File(rootPath, "org/capatect/test2/version.properties"));
        resModel.addResourceBundle(new File(rootPath, "org/capatect/test3/version.properties"));
        resModel.addResourceBundle(new File(rootPath, "org/capatect/test3/another-bundle.properties"));

        ResourceClassGenerator generator = new ResourceClassGeneratorImpl();
        generator.generate(FileTestUtils.getRootPath("target/generated-testcode"), resModel);
    }

}
