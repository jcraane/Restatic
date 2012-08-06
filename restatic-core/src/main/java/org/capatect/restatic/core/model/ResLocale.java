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

import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Represents a locale in the ResModel.
 *
 * @author Jamie Craane
 */
public final class ResLocale {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResLocale.class);

    private static final String LOCALE_SEPARATOR = "_";
    private static final String EXTENSION_SEPERATOR = ".";

    public static final String DEFAULT_LOCALE = "";
    private static final String XML_EXTENSION = ".xml";

    private final String locale;

    private final Set<ResKey> keys = new HashSet<ResKey>();
    private boolean defaultLocale;

    private ResLocale(final String locale) {
        defaultLocale = StringUtils.isEmpty(locale);
        this.locale = defaultLocale ? DEFAULT_LOCALE : locale;
    }

    /**
     * Creates a new instance of ResLocale. Populate the keys of the created ResLocale with the keys
     * form the passed-in resource bundle. The keys are converted to valid Java constant identifiers.
     *
     * @param resourceBundle
     * @return
     */
    public static ResLocale createFromResourceBundle(final File resourceBundle) {
        Validate.notNull(resourceBundle, "The resourceBundle may not be null.");

        String localeInformation = extractLocale(resourceBundle.getName());
        ResLocale resLocale = new ResLocale(localeInformation);

        extractKeysFromResourceBundleAndAddToLocale(resourceBundle, resLocale);

        return resLocale;
    }

    private static void extractKeysFromResourceBundleAndAddToLocale(final File resourceBundle, final ResLocale resLocale) {
        Properties properties = loadProperties(resourceBundle);
        Set<Object> keySet = properties.keySet();
        for (final Object key : keySet) {
            resLocale.keys.add(ResKey.createAndConvertConstantIdentifier((String) key));
        }
    }

    private static Properties loadProperties(final File resourceBundle) {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(resourceBundle));
            Properties properties = new Properties();
            if (isXmlResourceBundle(resourceBundle)) {
                properties.loadFromXML(bis);
            } else {
                properties.load(bis);
            }
            return properties;
        } catch (FileNotFoundException e) {
            throw new ParseException(String.format("Parsing of %s failed.", resourceBundle.getAbsolutePath()), e, resourceBundle.getAbsolutePath());
        } catch (IOException e) {
            throw new ParseException(String.format("Parsing of %s failed.", resourceBundle.getAbsolutePath()), e, resourceBundle.getAbsolutePath());
        } finally {
            closeInputStream(bis);
        }
    }

    private static boolean isXmlResourceBundle(final File resourceBundle) {
        return resourceBundle.getName().endsWith(XML_EXTENSION);
    }

    private static void closeInputStream(final BufferedInputStream bis) {
        if (bis != null) {
            try {
                bis.close();
            } catch (IOException e) {
                // Ignored
            }
        }
    }

    /**
     * Extracts the locale information for the given resource bundle name.
     *
     * @param name Example: resources_nl_NL.properties where nl_NL is the locale information.
     * @return The locale information.
     */
    private static String extractLocale(final String name) {
        int localeIndex = name.indexOf(LOCALE_SEPARATOR);
        String locale = ResLocale.DEFAULT_LOCALE;
        if (localeIndex != -1) {
            locale = name.substring(localeIndex + 1, name.indexOf(EXTENSION_SEPERATOR));
        }

        return locale;
    }

    /**
     * @return The locale, for example nl_NL, en_US, en or an empty String for the default locale.
     */
    public String getLocale() {
        return locale;
    }

    /**
     * @return The keys beloning to this locale.
     */
    public Set<ResKey> getKeys() {
        return Collections.unmodifiableSet(keys);
    }

    /**
     * Merges the keys of the given locale with this locale if and only if the locales are the same.
     *
     * @param resLocaleToMerge The ResLocale where the keys are merged with the keys from this locale.
     * @throws IllegalArgumentException If the locales of the ResLocale to merge is not the same or resLocaleToMerge is null.
     * @throws IllegalStateException    If a key in resLocaleToMerge already exists in the list of keys of this locale.
     */
    public void mergeKeys(final ResLocale resLocaleToMerge) {
        Validate.notNull(resLocaleToMerge, "The resLocaleToMerge may not be null.");

        if (differentLocales(resLocaleToMerge)) {
            throw new IllegalArgumentException("The locale to merge does not have the same locale as the locale to merge to.");
        }

        for (final ResKey resKey : resLocaleToMerge.getKeys()) {
            if (this.keys.contains(resKey)) {
                throw new IllegalStateException(String.format("This locale already contains a key [%S].", resKey));
            }

            this.keys.add(resKey);
        }
    }

    private boolean differentLocales(final ResLocale resLocaleToMerge) {
        return this.defaultLocale != resLocaleToMerge.defaultLocale ||
                !this.locale.equals(resLocaleToMerge.locale);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResLocale resLocale = (ResLocale) o;

        if (defaultLocale != resLocale.defaultLocale) {
            return false;
        }
        return !(locale != null ? !locale.equals(resLocale.locale) : resLocale.locale != null);

    }

    @Override
    public int hashCode() {
        int result = locale != null ? locale.hashCode() : 0;
        result = 31 * result + (defaultLocale ? 1 : 0);
        return result;
    }

    public boolean isDefaultLocale() {
        return defaultLocale;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ResLocale");
        sb.append("{defaultLocale=").append(defaultLocale);
        sb.append(", locale='").append(locale).append('\'');
        sb.append(", keys=").append(keys);
        sb.append('}');
        return sb.toString();
    }
}
