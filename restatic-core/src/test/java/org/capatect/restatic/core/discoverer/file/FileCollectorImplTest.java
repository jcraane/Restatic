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

import org.junit.Test;

import java.io.File;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author Jamie Craane
 */
public class FileCollectorImplTest {
    @Test
    public void findPropertiesFiles() {
        FileFilter filter = AntStylePatternFileNameFilter.create("**/*.properties");
        File baseDir = new File(System.getProperty("basedir", "restatic-core"));
        File rootPath = new File(baseDir, "src/test/resources");
        FileCollector fileCollector = FileCollectorImpl.createWithPathAndFilter(rootPath, filter);
        List<File> matchedFiles = fileCollector.collect();
        assertEquals(3, matchedFiles.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyRootPath() {
        FileCollectorImpl.createWithPathAndFilter(null, new DefaultFileFilter());
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNullFilter() {
        FileCollectorImpl.createWithPathAndFilter(new File(System.getProperty("basedir", "restatic-core")), null);
    }

    @Test
    public void findSpecificResourceBundle() {
        FileFilter filter = AntStylePatternFileNameFilter.create("org/capatect/restatic/discoverer/resources/bundleone/resources.properties");
        File baseDir = new File(System.getProperty("basedir", "restatic-core"));
        File rootPath = new File(baseDir, "src/test/resources");
        FileCollector fileCollector = FileCollectorImpl.createWithPathAndFilter(rootPath, filter);
        List<File> matchedFiles = fileCollector.collect();
        assertEquals(1, matchedFiles.size());
    }

    /**
     * Implementation of the FileFilter which only exists for testing purposes.
     * <p/>
     * This implementation does nothing.
     */
    private static class DefaultFileFilter implements FileFilter {

        @Override
        public boolean matches(final String name) {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
