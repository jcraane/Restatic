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

package org.capatect.restatic.core.configuration;

import org.capatect.restatic.core.discoverer.file.AntStylePatternFileNameFilter;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;

import static junit.framework.Assert.assertEquals;

/**
 * @author Jamie Craane
 */
public class ConfigurationTest {
    private static final File baseDir = new File(System.getProperty("basedir", "restatic-core"));

    @Test(expected = IllegalArgumentException.class)
    public void nullAliasPackage() {
        new Configuration(
                new File("target"),
                new HashSet<File>() {{
                    add(new File(baseDir, "src/test/resources"));
                }},
                AntStylePatternFileNameFilter.create("**/*.properties"),
                new HashSet<PackageAlias>() {{
                    add(new PackageAlias("org.capatect.test", "mypackage"));
                    add(new PackageAlias("test", "t"));
                }},
                false,
                "R"
        ).getAliasFor(null);
    }

    @Test
    public void getPackageAlias() {
        Configuration configuration = new Configuration(
                new File("target"),
                new HashSet<File>() {{
                    add(new File(baseDir, "src/test/resources"));
                }},
                AntStylePatternFileNameFilter.create("**/*.properties"),
                new HashSet<PackageAlias>() {{
                    add(new PackageAlias("org.capatect.test", "mypackage"));
                    add(new PackageAlias("test", "t"));
                }},
                false,
                "R"
        );

        assertEquals("mypackage", configuration.getAliasFor("org.capatect.test"));
        assertEquals("t", configuration.getAliasFor("test"));
        assertEquals("noalias", configuration.getAliasFor("noalias"));
        assertEquals("", configuration.getAliasFor(""));
    }
}
