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

package org.capatect.restatic.core;

/**
 * Contains helper methods.
 *
 * @author Jamie Craane
 */
final public class Util {
    private static final String REPLACE_CHAR = "_";

    private Util() {
        // Prevent instantiation.
    }

    /**
     * Replaces all characters in the given text which are invalid in Java identifiers and
     * replaces those occurences with an underscore (_).
     *
     * @param text The text to replace the invalid characters for Java identifiers with an underscore.
     * @return The text with the invalid characters for Java identifiers replaced with an underscore.
     */
    public static String replaceInvalidJavaIdentifierCharsWithUnderscore(String text) {
        text = text.replaceAll("[\\/\\.@#!`~$%^&*()+={}:;'><?,\\[\\]\"-]+", REPLACE_CHAR);
        return text.trim();
    }
}
