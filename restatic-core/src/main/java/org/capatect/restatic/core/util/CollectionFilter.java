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

import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Jamie Craane
 */
public final class CollectionFilter {
    private CollectionFilter() {
        // Prevent instantiation.
    }

    /**
     * Filters the given target Set with the given predicate and puts the elements from the target Set that match the predicate
     * in the return Set.
     *
     * @param target    The Set to filter.
     * @param predicate The predicate to use for filtering the Set.
     * @param <T>       The elements from target for which Predicate#apply returns true.
     * @return
     */
    public static <T> List<T> filter(final Set<T> target, final Predicate<T> predicate) {
        Validate.notNull(target, "The target collection may not be null.");
        Validate.notNull(predicate, "The predicate may not be null.");

        final List<T> result = new ArrayList<T>();
        for (final T element : target) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }

        return result;
    }
}
