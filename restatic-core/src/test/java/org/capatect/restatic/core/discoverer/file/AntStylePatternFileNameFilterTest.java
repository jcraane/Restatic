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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Jamie Craane
 */
public class AntStylePatternFileNameFilterTest {
    @Test(expected = IllegalArgumentException.class)
    public void createFilterNullArgumentsNotAllowed() {
        AntStylePatternFileNameFilter.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createFilterNullElementsNotAllowed() {
        AntStylePatternFileNameFilter.create(null, null);
    }

    @Test
    public void matchesExactlyOnePropertyFiles() {
        FileFilter filter = AntStylePatternFileNameFilter.create("resources.properties");
        assertTrue(filter.matches("resources.properties"));
        assertFalse(filter.matches("resources.xml"));
    }

    @Test
    public void matchExactDirectoryAndWildCardFile() {
        FileFilter filter = AntStylePatternFileNameFilter.create("org/test/*.properties");
        assertTrue(filter.matches("org/test/resources.properties"));
        assertTrue(filter.matches("org/test/test.properties"));
        assertFalse(filter.matches("org/test/resources.xml"));
    }

    @Test
    public void matchAntStyleWildCardDirectory() {
        FileFilter filter = AntStylePatternFileNameFilter.create("**/*.properties");
        assertTrue(filter.matches("org/test/resources.properties"));
        assertTrue(filter.matches("resources.properties"));
        assertTrue(filter.matches("nl/company/test/bundle.properties"));
        assertFalse(filter.matches("bundle.xml"));
        assertFalse(filter.matches("org/test/bundle.xml"));
    }

    @Test
    public void matchAntStyleWildCardMultipleDirectories() {
        FileFilter filter = AntStylePatternFileNameFilter.create("org/test/**/*.properties");
        assertTrue(filter.matches("org/test/resources.properties"));
        assertTrue(filter.matches("org/test/application/resources.properties"));
        assertTrue(filter.matches("org/test/application/test/resources.properties"));
        assertFalse(filter.matches("resources.properties"));
        assertFalse(filter.matches("bundle.xml"));
        assertFalse(filter.matches("org/test/bundle.xml"));
        assertFalse(filter.matches("nl/company/test/bundle.properties"));

    }
}
