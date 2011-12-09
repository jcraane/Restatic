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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ResModel abstraction for Restatic. This model is created by the ResourceBundleParser and handed over
 * to the ResourceClassGenerator which generates source files from the ResModel.
 *
 * @author Jamie Craane
 */
public final class ResModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResModel.class);

    private final String rootClassName;
    private final Configuration configuration;
    private final List<ResBundle> bundles = new ArrayList<ResBundle>();

    private ResModel(final Configuration configuration) {
        Validate.notNull(configuration, "configuration may not be null.");

        this.rootClassName = configuration.getRootClassName();
        this.configuration = configuration;
    }

    /**
     * Creates a new instance of a ResModel.
     *
     * @param configuration The Configuration object which holds the configuration used in resource bundle parsing.
     */
    public static ResModel create(final Configuration configuration) {
        return new ResModel(configuration);
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
        ResBundle resBundle = ResBundle.createOrReturn(resourceBundle, configuration);
        bundles.add(resBundle);
    }

    /**
     * @return List of ResBundles belonging to this model.
     */
    public List<ResBundle> getBundles() {
        return Collections.unmodifiableList(bundles);
    }
}
