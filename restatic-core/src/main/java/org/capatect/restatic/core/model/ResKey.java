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
import org.capatect.restatic.core.Util;

/**
 * Represents a key in a ResBundle.
 * <p/>
 * This class is immutable.
 *
 * @author Jamie Craane
 */
public final class ResKey {
    private final String name;
    private final String originalName;

    private ResKey(final String name, final String originalName) {
        this.name = name;
        this.originalName = originalName;
    }

    /**
     * Creates an instance of this class with the given key and converts the key to a Java constant identifier.
     *
     * @param key The key to use and convert. Usually comes from a resource bundle.
     * @return an instance of this class with the key converted to a Java constant identifier.
     */
    public static ResKey createAndConvertConstantIdentifier(final String key) {
        return new ResKey(KeyToJavaConstantIdentifierConverter.convert(key), key);
    }

    /**
     * @return The name of the key converted to a Java constant identifier ready to be used in sourcecode generation.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The original, uncoverted name, of the key as it was passed in the createAndConvertConstantIdentifier method.
     */
    public String getOriginalName() {
        return originalName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResKey resKey = (ResKey) o;

        if (name != null ? !name.equals(resKey.name) : resKey.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ResKey");
        sb.append("{name='").append(name).append('\'');
        sb.append(", originalName='").append(originalName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * Helper class for converting resource bundle keys to Java constant identifiers.
     *
     * @author Jamie Craane
     */
    static final class KeyToJavaConstantIdentifierConverter {
        private KeyToJavaConstantIdentifierConverter() {
            // Prevent instantiation.
        }

        /**
         * Converts the given resource bundle key to a valid Java constant identifier.
         * Replaces special character which are not valid in a Java constant identifier with an underscore (_).
         *
         * @param resourceBundleKey The key to convert to a valid Java constant identifier.
         * @return A valid Java constant identifier for the given key.
         */
        public static String convert(String resourceBundleKey) {
            Validate.notEmpty(resourceBundleKey, "The key may not be null or empty.");

            return Util.replaceInvalidJavaIdentifierCharsWithUnderscore(resourceBundleKey).toUpperCase();
        }
    }
}
