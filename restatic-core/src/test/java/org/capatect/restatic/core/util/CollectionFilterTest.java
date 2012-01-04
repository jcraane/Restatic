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

package org.capatect.restatic.core.util;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

/**
 * @author Jamie Craane
 */
public class CollectionFilterTest {
    @Test(expected = IllegalArgumentException.class)
    public void nullSet() {
        CollectionFilter.filter(null, new Predicate<Object>() {
            @Override
            public boolean apply(final Object type) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullPredicate() {
        CollectionFilter.filter(new HashSet<Object>(), null);
    }

    @Test
    public void filterSet() {
        Set<String> strings = new HashSet<String>() {{
            add("Test");
            add("Test2");
            add("Test");
        }};

        assertEquals(1, CollectionFilter.filter(strings, new Predicate<String>() {
            @Override
            public boolean apply(final String type) {
                return "Test".equals(type);
            }
        }).size());

        assertEquals(0, CollectionFilter.filter(strings, new Predicate<String>() {
            @Override
            public boolean apply(final String type) {
                return false;
            }
        }).size());
    }
}
