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

/**
 * A single resource bundle can exist multiple times, one for very locale. This class holds all locale specific resource
 * bundles for a specific resource bundle.
 * <p/>
 * Example:
 * org.capatect.bundle.resources.properties
 * org.capatect.bundle.resources_nl_NL.properties
 * org.capatect.bundle.resources_en_US.properties
 * <p/>
 * In the above example there is one resource bundle, resources.properties. There are three variants for this bundle: default, nl_NL and
 * en_US. THe RestaticResourceBundleContainer holds all of the above resource bundles, one for every locale.
 *
 * @author Jamie Craane
 */
public final class RestaticResourceBundleContainer {
    private final String name;

    public RestaticResourceBundleContainer(final String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
