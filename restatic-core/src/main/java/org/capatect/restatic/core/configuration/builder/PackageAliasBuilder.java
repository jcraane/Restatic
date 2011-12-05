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
package org.capatect.restatic.core.configuration.builder;

import org.apache.commons.lang.Validate;
import org.capatect.restatic.core.configuration.PackageAlias;

/**
 * Construction builder class for PackageAlias classes. Part of the configuration expression builder.
 *
 * @author Jeroen Post
 */
public class PackageAliasBuilder {

    private ConfigurationBuilder parentBuilder;

    private String packageName;
    private String alias;

    public PackageAliasBuilder(final ConfigurationBuilder aParentBuilder) {
        Validate.notNull(aParentBuilder, "Parameter aParentBuilder is not allowed to be null");

        parentBuilder = aParentBuilder;
    }

    public PackageAlias getPackageAlias() {
        return new PackageAlias(packageName, alias);
    }

    public void setPackageName(final String aPackageName) {
        Validate.notEmpty(aPackageName, "Parameter aPackageName is not allowed to be empty");

        packageName = aPackageName;
    }

    public ConfigurationBuilder to(final String anAlias) {
        Validate.notEmpty(anAlias, "Parameter anAlias is not allowed to be empty");

        alias = anAlias;

        return parentBuilder;
    }
}
