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

package org.capatect.restatic.core.parser;

import org.apache.commons.lang.Validate;

/**
 * Package private helper class for converting resource bundle keys to constant Java identifiers.
 *
 * @author Jamie Craane
 */
final class KeyToJavaConstantIdentifierConverter {

    private static final String REPLACE_CHAR = "_";

    /**
     * Converts the given resource bundle key to a valid Java constant identifier.
     * Replaces special character which are not valid in a Java constant identifier with an underscore (_).
     *
     * @param resourceBundleKey The key to convert to a valid Java constant identifier.
     * @return A valid Java constant identifier for the given key.
     */
    String convert(String resourceBundleKey) {
        Validate.notEmpty(resourceBundleKey, "The key may not be null or empty.");

        resourceBundleKey = resourceBundleKey.replaceAll("[\\/\\.@#!`~$%^&*()+={}:;'><?,\\[\\]\"]+", REPLACE_CHAR);
        resourceBundleKey = resourceBundleKey.trim();

        return resourceBundleKey.toUpperCase();
    }
}
