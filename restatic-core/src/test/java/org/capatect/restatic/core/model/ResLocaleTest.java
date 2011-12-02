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

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Jamie Craane
 */
public class ResLocaleTest {
    @Test(expected = IllegalArgumentException.class)
    public void createNullLocale() {
        new ResLocale(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createEmptyLocale() {
        new ResLocale("");
    }

    @Test
    public void create() {
        ResLocale resLocale = new ResLocale("nl_NL");
        assertEquals("nl_NL", resLocale.getLocale());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullKey() {
        ResLocale resLocale = new ResLocale("nl_NL");
        resLocale.addKey(null);
    }

    @Test
    public void addKeys() {
        ResLocale resLocale = new ResLocale("nl_NL");
        resLocale.addKey(ResKey.createAndConvertConstantIdentifier("key"));
        resLocale.addKey(ResKey.createAndConvertConstantIdentifier("key1"));
        resLocale.addKey(ResKey.createAndConvertConstantIdentifier("key2"));
        assertEquals(3, resLocale.getKeys().size());
    }

    @Test
    public void addDuplicateKey() {
        ResLocale resLocale = new ResLocale("nl_NL");
        resLocale.addKey(ResKey.createAndConvertConstantIdentifier("key"));
        resLocale.addKey(ResKey.createAndConvertConstantIdentifier("key"));
        assertEquals(1, resLocale.getKeys().size());
    }
}
