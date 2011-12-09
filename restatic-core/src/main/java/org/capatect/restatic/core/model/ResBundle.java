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
import org.capatect.restatic.core.Util;
import org.capatect.restatic.core.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a resource bundle which contains a Set of locales.
 *
 * @author Jamie Craane
 */
public final class ResBundle {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResBundle.class);

    private static final String PATH_SEPARATOR = File.separator;
    private static final String PACKAGE_SEPERATOR = ".";
    private static final String RESOURCE_BUNDLE_NAME_SEPARATOR = "_";

    private final Set<ResLocale> locales = new HashSet<ResLocale>();
    private final String bundleClassName;

    private ResBundle(final String name) {
        this.bundleClassName = name;
    }

    /**
     * @param resourceBundle The package where the resource bundle resides, for example org.capatect.resources. An empty String if
     *                       the resource bundle resides at the root package.
     * @param configuration  The configuration to use when adding resource bundles. The parts from the confiuration that are needed
     *                       are: sourceDirectories and the package aliases.
     * @return
     */
    public static ResBundle createAndConvertToJavaClassIdentifier(final File resourceBundle, final Configuration configuration) {
        Validate.notNull(resourceBundle, "The resourceBundle may not be null.");
        Validate.notNull(configuration, "The configuration may not be null.");

        String packageName = extractResourceBundlePackage(
                resourceBundle,
                configuration.getSourceDirectories());

        // TODO: The locale should be taken into account (or default locale if none specified).

        String aliasPackage = configuration.getAliasFor(packageName);
        String javaClassIdentifier = ResourceBundleToJavaClassIdentifierConverter.convert(aliasPackage, resourceBundle.getName());
        ResBundle bundle = new ResBundle(javaClassIdentifier);

        return bundle;
    }

    private static String extractResourceBundlePackage(
            final File resourceBundle,
            final Set<File> sourceRootPaths) {
        String resourceBundlePath = resourceBundle.getPath();
        String bundlePackage = resourceBundlePath.substring(0, resourceBundlePath.lastIndexOf(PATH_SEPARATOR));

        for (File sourceRootPath : sourceRootPaths) {
            if (bundlePackage.indexOf(sourceRootPath.getPath()) != -1) {
                bundlePackage = bundlePackage.substring(sourceRootPath.getPath().length() + 1, bundlePackage.length());
                break;
            }
        }

        bundlePackage = bundlePackage.replaceAll(PATH_SEPARATOR, PACKAGE_SEPERATOR);
        return bundlePackage;
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
         * Converts the specified packageName and resourceBundleFileName to a valid Java class identifier. The resourceBundleFileName is
         * stripped form any characters that are not valid in a Java classname.
         *
         * @param packageName            The relative packageName of the resource bundle based on the package where the resource bundle resides.
         *                               Example: org.capatect.resources.
         * @param resourceBundleFileName The resourceBundleFileName of the resource bundle, for example resources.properties or resources_nl_NL.properties.
         * @return A Java class resourceBundleFileName based on the packageName and resourceBundleFileName of the resource bundle.
         */
        public static String convert(String packageName, String resourceBundleFileName) {
            LOGGER.trace("convert({}, {})", packageName, resourceBundleFileName);

            StringBuilder nameBuilder = new StringBuilder(32);
            String[] pathParts = packageName.split("\\.");
            for (String pathPart : pathParts) {
                pathPart = capitalizeFirstLetter(pathPart);
                nameBuilder.append(pathPart);
            }

            final String nameWithoutExtensionAndLocale = stripLocaleInformationAndExtension(resourceBundleFileName);
            final String validJavaIdentifierName = Util.replaceInvalidJavaIdentifierCharsWithUnderscore(nameWithoutExtensionAndLocale);
            final String className = capitalizeNameParts(validJavaIdentifierName);

            String resourceBundleClassName = nameBuilder.append(className).toString();
            LOGGER.trace("Resourcebundle classname: {}", resourceBundleClassName);
            return resourceBundleClassName;
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

        private static String capitalizeNameParts(String name) {
            String[] parts = name.split(RESOURCE_BUNDLE_NAME_SEPARATOR);

            StringBuilder nameBuilder = new StringBuilder(32);
            for (String part : parts) {
                nameBuilder.append(capitalizeFirstLetter(part));
            }

            return nameBuilder.toString();
        }

        private static String capitalizeFirstLetter(String text) {
            if (StringUtils.isEmpty(text)) {
                return text;
            }

            return Character.toUpperCase(text.charAt(0)) + text.substring(1);
        }
    }
}
