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

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jamie Craane
 */
public class PackageNameTest {
    @Test
    public void create() {
        assertEquals("nl.test", new PackageName("nl.test").getName());
    }

    @Test
    public void equals() {
        PackageName packageName = new PackageName("nl.test");
        PackageName packageName2 = new PackageName("nl.test");
        assertTrue(packageName.equals(packageName2));
    }

    @Test
    public void noEquals() {
        PackageName packageName = new PackageName("nl.test");
        PackageName packageName2 = new PackageName("nl.test2");
        assertFalse(packageName.equals(packageName2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyName() {
        new PackageName("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullName() {
        new PackageName(null);
    }
}
