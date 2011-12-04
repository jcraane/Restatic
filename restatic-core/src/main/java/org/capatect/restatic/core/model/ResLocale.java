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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a locale in the ResModel.
 *
 * @author Jamie Craane
 */
public final class ResLocale {
    public static final String DEFAULT_LOCALE = "";

    private final String locale;

    private final Set<ResKey> keys = new HashSet<ResKey>();
    private boolean defaultLocale;

    public ResLocale(String locale) {
        if (locale == null) {
            locale = DEFAULT_LOCALE;
        }

        defaultLocale = StringUtils.isEmpty(locale);
        this.locale = locale;
    }

    /**
     * @return The locale, for example nl_NL, en_US, en or an empty String for the default locale.
     */
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

    public boolean isDefaultLocale() {
        return defaultLocale;
    }
}
