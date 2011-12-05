/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.capatect.restatic.core.configuration;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Represents the alias of a package. This class is immutable.
 *
 * @author Jamie Craane
 * @author Jeroen Post
 */
public final class PackageAlias {
    private final String alias;
    private final String packageName;

    public PackageAlias(final String aPackageName, final String anAlias) {
        Validate.notEmpty(aPackageName, "Parameter aPackageName is not allowed to be empty");
        Validate.notEmpty(anAlias, "Parameter anAlias is not allowed to be empty");

        packageName = aPackageName;
        alias = anAlias;
    }

    /**
     * @return The alias of the package.
     */
    public String getAlias() {
        return alias;
    }

    public String getPackageName() {
        return packageName;
    }

    @Override
    public boolean equals(final Object anObject) {
        if (this == anObject) {
            return true;
        }

        if (anObject == null || getClass() != anObject.getClass()) {
            return false;
        }

        final PackageAlias other = (PackageAlias) anObject;

        return new EqualsBuilder()
                .append(packageName, other.packageName)
                .append(alias, other.alias)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(packageName)
                .append(alias)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("packageName", packageName)
                .append("alias", alias)
                .toString();
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
