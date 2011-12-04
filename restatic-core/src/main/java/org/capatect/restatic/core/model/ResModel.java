package org.capatect.restatic.core.model;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ResModel abstraction for Restatic. This model is created by the ResourceBundleParser and handed over
 * to the ResourceClassGenerator which generates source files from the ResModel.
 *
 * @author Jamie Craane
 */
public final class ResModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResModel.class);
    private static final String PATH_SEPARATOR = File.separator;
    private static final String PACKAGE_SEPERATOR = ".";

    private final String rootClassName;
    private final Set<File> sourceRootPaths;
    private final List<ResBundle> bundles = new ArrayList<ResBundle>();

    private ResModel(final String rootClassName, final Set<File> sourceRootPaths) {
        LOGGER.trace("ResModel({})", rootClassName);

        Validate.notEmpty(rootClassName, "The rootClassName may not be null.");
        Validate.noNullElements(sourceRootPaths, "sourceRootPaths may not be null.");

        this.rootClassName = rootClassName;
        this.sourceRootPaths = Collections.unmodifiableSet(sourceRootPaths);
    }

    /**
     * Creates a new instance of a ResModel.
     *
     * @param rootClassName   The rootClassName which is used in source generation to name the top-level class in the hierarchy.
     * @param sourceRootPaths The sourceRootPaths where to look for resource bundles. This is needed to strip the source root paths
     *                        from the actual resource bundles path to determine the actual Java package of the
     *                        resource bundle.
     */
    public static ResModel create(final String rootClassName, final Set<File> sourceRootPaths) {
        return new ResModel(rootClassName, sourceRootPaths);
    }

    /**
     * @return The name of the root class to generate.
     */
    public String getRootClassName() {
        return rootClassName;
    }

    /**
     * Addes a resource bundle to the resource model.
     *
     * @param resourceBundle The resource bundle to add to the resource model.
     */
    public void addResourceBundle(final File resourceBundle) {
        String bundlePackage = extractResourceBundlePackage(resourceBundle);
        ResBundle resBundle = ResBundle.createAndConvertToJavaClassIdentifier(bundlePackage, resourceBundle.getName());
        bundles.add(resBundle);
    }

    private String extractResourceBundlePackage(final File resourceBundle) {
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
     * @return List of ResBundles belonging to this model.
     */
    public List<ResBundle> getBundles() {
        return Collections.unmodifiableList(bundles);
    }
}
