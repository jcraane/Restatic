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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jamie Craane
 */
public final class ResBundle {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResBundle.class);

    private final List<ResLocale> locales = new ArrayList<ResLocale>();
    private String bundleClassName;

    private ResBundle(final String name) {
        this.bundleClassName = name;
    }

    /**
     * @param packageName The package where the resource bundle resides, for example org.capatect.resources. An empty String if
     *                    the resource bundle resides at the root package.
     * @param name        The name of the resource bundle, for example resources.properties for the default locale
     *                    or resources_nl_NL.properties for the nl_NL locale.
     * @return
     */
    public static ResBundle createAndConvertToJavaClassIdentifier(final String packageName, final String name) {
        Validate.notEmpty(name, "The name may not be null.");
        Validate.notNull(packageName, "The packageName may not be null.");

        String javaClassIdentifier = ResourceBundleToJavaClassIdentifierConverter.convert(packageName, name);
        ResBundle bundle = new ResBundle(javaClassIdentifier);

        return bundle;
    }

    /**
     * @return The Java class name of the resource bundle this ResBundle is based upon. Can be used in source generation.
     */
    public String getBundleClassName() {
        return bundleClassName;
    }

    /**
     * Helper class which converts a resource bundle and path to a Java class identifier which is used
     * in source generation.
     */
    static class ResourceBundleToJavaClassIdentifierConverter {
        /**
         * See class description.
         *
         * @param packageName The relative packageName of the resource bundle based on the package where the resource bundle resides.
         *                    Example: org.capatect.resources.
         * @param name        The name of the resource bundle, for example resources.properties or resources_nl_NL.properties.
         * @return A Java class name based on the packageName and name of the resource bundle.
         */
        public static String convert(String packageName, String name) {
            LOGGER.trace("convert({}, {})", packageName, name);

            StringBuilder nameBuilder = new StringBuilder(32);
            String[] pathParts = packageName.split("\\.");
            for (String pathPart : pathParts) {
                pathPart = capitalizeFirstLetter(pathPart);
                nameBuilder.append(pathPart);
            }

            name = stripLocaleInformationAndExtension(name);
            return nameBuilder.append(capitalizeFirstLetter(name)).toString();
        }

        private static String stripLocaleInformationAndExtension(String name) {
            name = stripLocaleInformation(name);
            name = stripExtension(name);
            return name;
        }

        private static String stripExtension(String name) {
            int dotIndex = name.indexOf(".");
            if (dotIndex != -1) {
                name = name.substring(0, dotIndex);
            }
            return name;
        }

        private static String stripLocaleInformation(String name) {
            int underScoreIndex = name.indexOf("_");
            if (underScoreIndex != -1) {
                name = name.substring(0, underScoreIndex);
            }
            return name;
        }

        private static String capitalizeFirstLetter(String pathPart) {
            if (StringUtils.isEmpty(pathPart)) {
                return pathPart;
            }

            return Character.toUpperCase(pathPart.charAt(0)) + pathPart.substring(1);
        }
    }
}
