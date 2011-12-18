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
import java.util.*;

/**
 * Represents a resource bundle which contains a Set of locales.
 * <p/>
 * Resource bundle validation:
 * Consider the following resource bundles:
 * <uL>
 * <li>resources.properties</li>
 * <li>resources_nl_NL.properties</li>
 * <li>resources.en_US.properties</li>
 * </uL>
 * <p/>
 * Some project standards may require that all resource bundles contain the same keys. If those stadards apply, the
 * resourceBundleValidationEnabled property of the Configuration object triggers validation. A ResBundle is considered valid
 * if the resource bundles all contain the same key/value pairs. Please note that this is actual valid from the Java language
 * so validation is disabled by default.
 *
 * @author Jamie Craane
 */
public final class ResBundle {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResBundle.class);

    private static final String PATH_SEPARATOR = File.separator;
    private static final String PACKAGE_SEPERATOR = ".";
    private static final String RESOURCE_BUNDLE_NAME_SEPARATOR = "_";
    private static final String LOCALE_SEPARATOR = "_";

    /**
     * Internal map which holds bundles by javaIdentifier. This is needed to determine if a bundle for a given identifier
     * exists. If so, the locale of the resource bundle is added to the existing bundle. Else a new bundle is created
     * and added to this map.
     */
    private static Map<String, ResBundle> bundles = new HashMap<String, ResBundle>();
    private static final String EXTENSION_SEPERATOR = ".";
    private static final String DEFAULT_PACKAGE = "";

    private final Map<ResLocale, ResLocale> locales = new HashMap<ResLocale, ResLocale>();
    private final String bundleClassName;
    private final String originalPathAndName;

    private ResBundle(final String name, final String originalPathAndResourceBundleName) {
        this.bundleClassName = name;
        this.originalPathAndName = originalPathAndResourceBundleName;
    }

    /**
     * @return The Java class name of the resource bundle this ResBundle is based upon. Can be used in source generation.
     */
    public String getBundleClassName() {
        return bundleClassName;
    }

    /**
     * Returns the locales for this bundle.
     *
     * @return The locales for this bundle.
     */
    public Set<ResLocale> getLocales() {
        return Collections.unmodifiableSet(new HashSet<ResLocale>(locales.values()));
    }

    /**
     * @param resourceBundle The package where the resource bundle resides, for example org.capatect.resources. An empty String if
     *                       the resource bundle resides at the root package.
     * @param configuration  The configuration to use when adding resource bundles. The parts from the confiuration that are needed
     *                       are: sourceDirectories and the package aliases.
     * @return
     */
    public static ResBundle createOrReturn(final File resourceBundle, final Configuration configuration) {
        Validate.notNull(resourceBundle, "The resourceBundle may not be null.");
        Validate.notNull(configuration, "The configuration may not be null.");

        String packageOnFileSystem = extractResourceBundlePackage(resourceBundle.getPath(), configuration.getSourceDirectories());
        String packageName = packageOnFileSystem.replaceAll(PATH_SEPARATOR, PACKAGE_SEPERATOR);
        String aliasPackage = configuration.getAliasFor(packageName);
        String javaClassIdentifier = ResourceBundleToJavaClassIdentifierConverter.convert(aliasPackage, resourceBundle.getName());

        ResBundle resBundle = getExistingOrCreateNew(javaClassIdentifier,
                getOriginalPathAndResourceBundleName(resourceBundle, packageOnFileSystem));

        addNewLocaleOrMergeKeysToExistingLocale(resourceBundle, resBundle);

        return resBundle;
    }

    private static void addNewLocaleOrMergeKeysToExistingLocale(final File resourceBundle, final ResBundle resBundle) {
        final ResLocale resLocale = ResLocale.createFromResourceBundle(resourceBundle);
        ResLocale localeFromBundle = resBundle.locales.get(resLocale);

        if (localeFromBundle == null) {
            resBundle.locales.put(resLocale, resLocale);
        } else {
            localeFromBundle.merge(resLocale);
        }
    }

    private static String getOriginalPathAndResourceBundleName(final File resourceBundle, final String packageOnFileSystem) {
        return packageOnFileSystem +
                PATH_SEPARATOR +
                resourceBundle.getName();
    }

    private static ResBundle getExistingOrCreateNew(final String javaClassIdentifier, final String packageOnFileSystem) {
        ResBundle resBundle = bundles.get(javaClassIdentifier);
        if (resBundle == null) {
            resBundle = new ResBundle(javaClassIdentifier, packageOnFileSystem);
            bundles.put(javaClassIdentifier, resBundle);
        }
        return resBundle;
    }

    private static String extractResourceBundlePackage(
            final String resourceBundlePath,
            final Set<File> sourceRootPaths) {
        String bundlePackage = resourceBundlePath.substring(0, resourceBundlePath.lastIndexOf(PATH_SEPARATOR));

        for (File sourceRootPath : sourceRootPaths) {
            if (bundlePackage.indexOf(sourceRootPath.getPath()) != -1) {
                if (bundlePackage.length() > sourceRootPath.getPath().length()) {
                    bundlePackage = bundlePackage.substring(sourceRootPath.getPath().length() + 1, bundlePackage.length());
                    break;
                } else {
                    bundlePackage = DEFAULT_PACKAGE;
                    break;
                }
            }
        }

        return bundlePackage;
    }

    /**
     * See class documentation.
     *
     * @return True if all locales contain the same amount of keys, false otherwise.
     */
    public boolean isValid() {
        ResLocale previousLocale = null;
        for (ResLocale locale : locales.values()) {
            if (previousLocale != null) {
                if (previousLocale.getKeys().size() != locale.getKeys().size()) {
                    return false;
                }
            }

            previousLocale = locale;
        }

        return true;
    }

    public String getOriginalPathAndName() {
        return originalPathAndName;
    }

    /**
     * Returns the total set of unique keys for this resource bundle and all locales in this resource bundle.
     *
     * @return All unique keys of all locales of this resource bundle.
     */
    public Set<ResKey> getAllUniqueKeysForLocales() {
        final Set<ResKey> keys = new HashSet<ResKey>();

        for (ResLocale locale : locales.values()) {
            for (ResKey resKey : locale.getKeys()) {
                keys.add(resKey);
            }
        }

        return keys;
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
            int dotIndex = name.indexOf(EXTENSION_SEPERATOR);
            if (dotIndex != -1) {
                name = name.substring(0, dotIndex);
            }
            return name;
        }

        private static String stripLocaleInformation(String name) {
            int underScoreIndex = name.indexOf(LOCALE_SEPARATOR);
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
