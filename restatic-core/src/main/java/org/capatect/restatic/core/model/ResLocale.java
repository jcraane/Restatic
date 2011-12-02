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

import org.apache.commons.lang.Validate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Jamie Craane
 */
public final class ResLocale {
    private final String locale;

    private final Set<ResKey> keys = new HashSet<ResKey>();

    public ResLocale(final String locale) {
        Validate.notEmpty(locale, "The locale may not be null or empty.");

        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }

    /**
     * Adds a key to this locale.
     *
     * @param key The key to add. When the key already exists, the existing key is replaced.
     */
    public void addKey(final ResKey key) {
        Validate.notNull(key, "The key may not be null.");

        keys.add(key);
    }

    /**
     * @return The keys beloning to this locale.
     */
    public Set<ResKey> getKeys() {
        return Collections.unmodifiableSet(keys);
    }
}
