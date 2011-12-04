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

package org.capatect.restatic.core.parser;

import org.capatect.restatic.core.FileTestUtils;
import org.capatect.restatic.core.configuration.Configuration;
import org.capatect.restatic.core.discoverer.file.FileCollector;
import org.capatect.restatic.core.model.ResModel;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertNotNull;

/**
 * @author Jamie Craane
 */
public class ResourceBundleParserImplTest {
    @Test
    public void parseProperiesResourceBundles() {
        File rootPath = FileTestUtils.getRootPath("src/test/parse-test");
        Configuration configuration = new Configuration.ConfigurationBuilder(rootPath).build();
        ResourceBundleParser parser = new ResourceBundleParserImpl(configuration);

        FileCollector fileCollector = FileCollector.createWithPathAndFilter(rootPath, configuration.getFileFilter());
        ResModel resModel = parser.parse(fileCollector.collect());
        assertNotNull(resModel);
    }

    @Test
    public void parseXmlResourceBundles() {

    }

    @Test
    public void validateResourceModel() {

    }
}
