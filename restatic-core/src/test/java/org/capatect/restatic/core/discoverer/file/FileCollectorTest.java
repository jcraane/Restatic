package org.capatect.restatic.core.discoverer.file;
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

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author Jamie Craane
 */
public class FileCollectorTest {
    @Ignore
    @Test
    public void findFilesPropertiesFiles() {
        FileFilter filter = AntStylePatternFileNameFilter.create("*.properties");
        File baseDir = new File(System.getProperty("basedir", "restatic-core"));
        File path = new File(baseDir, "src/test/resources");
        FileCollector fileCollector = new FileCollector(path, filter);
        List<File> matchedFiles = fileCollector.collect();
        assertEquals(2, matchedFiles.size());
    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void emptyFirstFilter() {

    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void emptyAdditionalFilters() {

    }

    @Ignore
    @Test
    public void findSpecificResourceBundle() {
//        FileFilter filter = new AntStylePatternFileNameFilter();
//        File baseDir = new File(System.getProperty("basedir", "restatic-core"));
//        File path = new File(baseDir, "src/test/resources");
//        FileCollector fileCollector = new FileCollector(path, filter);
//        List<File> matchedFiles = fileCollector.collect();
//        assertEquals(2, matchedFiles.size());
    }
}
