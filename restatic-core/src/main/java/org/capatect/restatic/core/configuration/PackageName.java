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
package org.capatect.restatic.core.configuration;

import org.apache.commons.lang.Validate;

/**
 * Represents the name of a package.
 * <p/>
 * This class is immutable.
 *
 * TODO Remove, class is obsolete (merged with PackageAlias class).
 *
 * @author Jamie Craane
 */
public final class PackageName {
    private String name;

    public PackageName(final String name) {
        Validate.notEmpty(name, "The given name may not be null or empty.");

        this.name = name;
    }

    /**
     * @return The name of the package.
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PackageName aPackageName = (PackageName) o;

        if (name != null ? !name.equals(aPackageName.name) : aPackageName.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
