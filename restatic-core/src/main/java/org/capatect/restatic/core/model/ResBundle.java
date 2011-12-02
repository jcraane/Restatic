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

/**
 * @author Jamie Craane
 */
public final class ResBundle {
    private final String name;

    private ResBundle(final String name) {
        Validate.notEmpty(name, "The name may not be null.");

        this.name = name;
        // prevent instantiation from outside.
    }

    /**
     * @param path The package where the resource bundle resides, for example org.capatect.resources. An empty String if
     *             the resource bundle resides at the root package.
     * @param name The name of the resource bundle, for example resources.properties for the default locale
     *             or resources_nl_NL.properties for the nl_NL locale.
     * @return
     */
    public static ResBundle createAndConvertToJavaClassIdentifier(final String path, final String name) {
        Validate.notNull(path, "The path may not be null.");

        ResBundle bundle = new ResBundle(name);

        return bundle;
    }
}
