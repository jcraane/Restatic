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
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Jamie Craane
 */
public class ResLocaleTest {
    private File rootPath;

    @Before
    public void setup() {
        rootPath = FileTestUtils.getRootPath("src/test/reslocale-test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullResourceBundle() {
        ResLocale.createFromResourceBundle(null);
    }

    @Test
    public void createWithDefaultLocale() {
        File resourceBundle = new File(rootPath, "org/capatect/test/resources.properties");
        ResLocale resLocale = ResLocale.createFromResourceBundle(resourceBundle);
        assertEquals("", resLocale.getLocale());
        assertTrue(resLocale.isDefaultLocale());
        assertEquals(3, resLocale.getKeys().size());
        assertTrue(resLocale.getKeys().contains(ResKey.createAndConvertConstantIdentifier("button.label")));
        assertTrue(resLocale.getKeys().contains(ResKey.createAndConvertConstantIdentifier("person_lastname")));
        assertTrue(resLocale.getKeys().contains(ResKey.createAndConvertConstantIdentifier("person_firstname")));
    }

    @Test
    public void createWithLocale() {
        File resourceBundle = new File(rootPath, "org/capatect/test/resources_nl_NL.properties");
        ResLocale resLocale = ResLocale.createFromResourceBundle(resourceBundle);
        assertEquals("nl_NL", resLocale.getLocale());
        assertFalse(resLocale.isDefaultLocale());
        assertEquals(2, resLocale.getKeys().size());
        assertTrue(resLocale.getKeys().contains(ResKey.createAndConvertConstantIdentifier("person_firstname")));
        assertTrue(resLocale.getKeys().contains(ResKey.createAndConvertConstantIdentifier("button.label")));
    }

    @Test
    public void mergeLocales() {
        ResLocale resLocale = ResLocale.createFromResourceBundle(new File(rootPath, "org/capatect/test/resources.properties"));
        ResLocale resLocaleToMerge = ResLocale.createFromResourceBundle(new File(rootPath, "org/capatect/test2/resources.properties"));

        resLocale.mergeKeys(resLocaleToMerge);
        assertEquals(5, resLocale.getKeys().size());
        assertTrue(resLocale.getKeys().contains(ResKey.createAndConvertConstantIdentifier("button.label")));
        assertTrue(resLocale.getKeys().contains(ResKey.createAndConvertConstantIdentifier("person.lastname")));
        assertTrue(resLocale.getKeys().contains(ResKey.createAndConvertConstantIdentifier("person.firstname")));
        assertTrue(resLocale.getKeys().contains(ResKey.createAndConvertConstantIdentifier("application.version")));
        assertTrue(resLocale.getKeys().contains(ResKey.createAndConvertConstantIdentifier("application.name")));
    }

    @Test(expected = IllegalStateException.class)
    public void mergeDuplicateKeys() {
        ResLocale resLocale = ResLocale.createFromResourceBundle(new File(rootPath, "org/capatect/test3/resources.properties"));
        ResLocale resLocaleToMerge = ResLocale.createFromResourceBundle(new File(rootPath, "org/capatect/test2/resources.properties"));
        resLocale.mergeKeys(resLocaleToMerge);
    }

    @Test(expected = IllegalArgumentException.class)
    public void localeToMergeIsNull() {
        File resourceBundle = new File(rootPath, "org/capatect/test/resources.properties");
        ResLocale resLocale = ResLocale.createFromResourceBundle(resourceBundle);
        resLocale.mergeKeys(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void mergeLocalesInvalidLocale() {
        File resourceBundle = new File(rootPath, "org/capatect/test/resources.properties");
        ResLocale resLocale = ResLocale.createFromResourceBundle(resourceBundle);

        resourceBundle = new File(rootPath, "org/capatect/test/resources_nl_NL.properties");
        ResLocale resLocaleToMerge = ResLocale.createFromResourceBundle(resourceBundle);
        resLocale.mergeKeys(resLocaleToMerge);
    }
}
