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

import org.apache.commons.lang.Validate;
import org.capatect.restatic.core.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * ResModel abstraction for Restatic. This model is created by the ResourceBundleParser and handed over
 * to the ResourceClassGenerator which generates source files from the ResModel.
 *
 * @author Jamie Craane
 */
public final class ResModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResModel.class);

    private String rootClassName;
    private String rootClassPackage;
    private final Configuration configuration;
    private final Set<ResBundle> bundles = new HashSet<ResBundle>();
    private boolean defaultPackage = true;

    private ResModel(final Configuration configuration) {
        Validate.notNull(configuration, "configuration may not be null.");

        this.configuration = configuration;
        this.rootClassName = extractClassName(configuration.getFullyQualifiedGeneratedRootClassName());
        this.rootClassPackage = extractPackage(configuration.getFullyQualifiedGeneratedRootClassName());
    }

    private String extractPackage(final String fullyQualitiefRootClass) {
        if (fullyQualitiefRootClass.contains(".")) {
            return fullyQualitiefRootClass.substring(0, fullyQualitiefRootClass.lastIndexOf("."));
        }

        this.defaultPackage = false;
        return "";
    }

    private String extractClassName(final String fullyQualitiefRootClass) {
        if (fullyQualitiefRootClass.contains(".")) {
            return fullyQualitiefRootClass.substring(fullyQualitiefRootClass.lastIndexOf(".") + 1, fullyQualitiefRootClass.length());
        }

        return fullyQualitiefRootClass;
    }

    /**
     * Creates a new instance of a ResModel.
     *
     * @param configuration The Configuration object which holds the configuration used in resource bundle parsing.
     */
    public static ResModel create(final Configuration configuration) {
        LOGGER.trace("Create new ResModel with configuration [{}].", configuration);
        return new ResModel(configuration);
    }

    /**
     * @return List of ValidationResult, one for each resource bundle that has errors.
     */
    public List<ValidationResult> getValidationResults() {
        List<ValidationResult> result = new ArrayList<ValidationResult>();
        for (final ResBundle bundle : bundles) {
            if (bundle.getValidationResult().hasErrors()) {
                result.add(bundle.getValidationResult());
            }
        }

        return Collections.unmodifiableList(result);
    }

    /**
     * @return True if all resource bundles contains locales with the same number of keys, false otherwise.
     */
    public boolean isValid() {
        return getValidationResults().size() == 0;
    }

    /**
     * Addes a resource bundle to the resource model.
     *
     * @param resourceBundle The resource bundle to add to the resource model.
     */
    public void addResourceBundle(final File resourceBundle) {
        ResBundle resBundle = ResBundle.createOrReturn(resourceBundle, configuration);
        bundles.add(resBundle);
    }

    /**
     * @return List of ResBundles belonging to this model.
     */
    public Set<ResBundle> getBundles() {
        return Collections.unmodifiableSet(bundles);
    }

    public String getRootClassName() {
        return this.rootClassName;
    }

    public String getRootClassPackage() {
        return this.rootClassPackage;
    }

    public boolean isNotDefaultPackage() {
        return defaultPackage;
    }
}
