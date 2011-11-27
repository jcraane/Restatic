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
 * Represents an alias for a package.
 * <p/>
 * This class is immutable.
 *
 * @author Jamie Craane
 */
public final class PackageAlias {
    private final String alias;

    public PackageAlias(final String alias) {
        Validate.notEmpty(alias, "The given alias may not be null or empty.");

        this.alias = alias;
    }

    /**
     * @return The alias of the package.
     */
    public String getAlias() {
        return alias;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PackageAlias that = (PackageAlias) o;

        if (alias != null ? !alias.equals(that.alias) : that.alias != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return alias != null ? alias.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PackageAlias");
        sb.append("{alias='").append(alias).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
