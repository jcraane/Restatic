package org.capatect.restatic.core.discoverer;
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

import org.capatect.restatic.core.FileTestUtils;
import org.junit.Test;

import java.io.File;

/**
 * Tests the discovery of resource bundles in source trees. Since there is no practical way to emulate file systems in memory
 * with Java <= 6, we use the src/test/resources directory to store our resource bundles. The same packaging scheme for the
 * resources is used as for the testcases.
 *
 * @author Jamie Craane
 */
public class RecursiveResourceBundleDiscovererTest {
    private final String pathSeparator = File.pathSeparator;
    @Test
    public void findOneResourceBundle() {
        File path = new File(FileTestUtils.getSystemIndependentPath("restatic-core/src/main/resources"));
        ResourceBundleDiscoverer discoverer = new RecursiveResourceBundleDiscoverer(path);
//        List<ResourceBundle> bundles = discoverer.fetchResourceBundles();
//        assertEquals(1, bundles.size());

    }
}
